package dev.tonholo.s2c.gradle.tasks

import com.android.build.api.variant.AndroidComponentsExtension
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.extensions.pascalCase
import dev.tonholo.s2c.gradle.dsl.IconVisibility
import dev.tonholo.s2c.gradle.dsl.ProcessorConfiguration
import dev.tonholo.s2c.gradle.dsl.SvgToComposeExtension
import dev.tonholo.s2c.gradle.internal.cache.PersistentOutputRegistry
import dev.tonholo.s2c.gradle.internal.inject.GradlePluginGraph
import dev.tonholo.s2c.gradle.internal.logger.setLogLevel
import dev.tonholo.s2c.gradle.internal.parser.IconParserConfigurationImpl
import dev.tonholo.s2c.gradle.internal.service.S2cWorkerBridge
import dev.tonholo.s2c.gradle.tasks.worker.IconParsingWorkAction
import dev.tonholo.s2c.gradle.tasks.worker.IconParsingWorkActionResult.Status
import dev.tonholo.s2c.gradle.tasks.worker.toResult
import dev.tonholo.s2c.inject.createS2cGraph
import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.logger.Logger
import dev.zacsweers.metro.createGraphFactory
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toOkioPath
import okio.Path.Companion.toPath
import org.gradle.api.DefaultTask
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.ProjectLayout
import org.gradle.api.logging.LogLevel
import org.gradle.api.logging.Logging
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.LocalState
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.withType
import org.gradle.work.ChangeType
import org.gradle.work.InputChanges
import org.gradle.workers.WorkQueue
import org.gradle.workers.WorkerExecutor
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompileTool
import java.io.File
import java.util.Properties
import java.util.UUID
import javax.inject.Inject
import dev.tonholo.s2c.gradle.internal.logger.Logger as createGradleLogger

internal const val GENERATED_FOLDER = "generated/svgToCompose"
internal const val WORKER_RESULTS_FOLDER = "$GENERATED_FOLDER/worker-results"
internal const val PERSISTENT_REGISTRY_PATH = "$GENERATED_FOLDER/persistent-output-registry.properties"
internal const val COMMON_CONFIGURATION_NAME = "common"

@CacheableTask
internal abstract class ParseSvgToComposeIconTask @Inject constructor(private val projectLayout: ProjectLayout) :
    DefaultTask() {
    /**
     * Creates and configures the GradlePluginGraph used by the task.
     *
     * The returned graph is initialized with the plugin's S2C graph and the project's build directory.
     *
     * @return The configured GradlePluginGraph instance.
     */
    private fun createGraph(): GradlePluginGraph {
        val s2cGraph = createS2cGraph(
            logger = createGradleLogger(Logging.getLogger("ParseSvgToComposeIconTask")),
            fileSystem = FileSystem.SYSTEM,
        )
        return createGraphFactory<GradlePluginGraph.Factory>().create(
            svgToComposeGraph = s2cGraph,
            buildDirectory = projectLayout.buildDirectory,
        )
    }

    @get:Inject
    protected abstract val workerExecutor: WorkerExecutor

    @get:Internal
    internal abstract val logLevel: Property<LogLevel>

    init {
        group = "svg-to-compose"
        description = "Parse svg or avg to compose icons"
    }

    @get:Internal
    internal lateinit var configurations: NamedDomainObjectContainer<ProcessorConfiguration>

    /**
     * Exposes only user-defined (non-common) configurations for Gradle's
     * [Nested] input tracking. The "common" entry is a merge template whose
     * properties are intentionally left unset, so it must not be validated
     * by Gradle's task-input checks.
     */
    @get:Nested
    internal val activeConfigurations: List<ProcessorConfiguration>
        get() = configurations.filter { it.name != COMMON_CONFIGURATION_NAME }

    @get:Input
    internal val configurationNames: List<String>
        get() = configurations.map { it.name }.sorted()

    @get:Input
    internal abstract val maxParallelExecutions: Property<Int>

    @get:Input
    internal abstract val kmp: Property<Boolean>

    @get:Internal
    @set:org.gradle.api.tasks.options.Option(option = "silent", description = "Run icon parsing without outputs.")
    internal var silent: Boolean = false

    private val outputDirectories: Map<String, File>
        get() = configurations
            .filter { it.name != COMMON_CONFIGURATION_NAME }
            .associate { configuration ->
                val destination = configuration.destinationPackage.get().replace(".", "/")
                val outputFolder =
                    if (configuration.iconConfiguration.orNull?.isCodeGenerationPersistent?.orNull == true) {
                        projectLayout.projectDirectory.dir(
                            buildString {
                                append(
                                    if (kmp.get()) {
                                        "src/commonMain/kotlin"
                                    } else {
                                        "src/main/kotlin"
                                    },
                                )
                            },
                        ).asFile.toOkioPath()
                    } else {
                        sourceDirectory.get().asFile.toOkioPath()
                    }
                val configurationDestination = outputFolder / destination

                configuration.name to configurationDestination.toFile()
            }

    @get:OutputDirectory
    abstract val sourceDirectory: DirectoryProperty

    @get:LocalState
    val persistentOutputRegistryFile: File
        get() = projectLayout.buildDirectory
            .file(PERSISTENT_REGISTRY_PATH)
            .get()
            .asFile

    /**
     * Executes the task: parses configured SVG/AVG files into Compose icons using
     * Gradle's [InputChanges] API for incremental processing.
     *
     * For non-incremental builds, all matching source files are processed. For incremental
     * builds, only added, modified, or removed files are handled. Persistent-mode outputs
     * that were manually deleted are detected and re-generated.
     *
     * @param inputChanges Gradle-provided change information for incremental builds.
     * @throws ExitProgramException if one or more icons fail to parse; the exception
     * contains the underlying causes.
     */
    @TaskAction
    fun run(inputChanges: InputChanges) {
        val graph = createGraph()
        val logger = graph.logger
        val fileManager = graph.fileManager
        val registry = graph.persistentOutputRegistry
        val bridgeToken = UUID.randomUUID().toString()
        // Processor is created to manage task-level temp directory lifecycle.
        // Workers create their own isolated processors via S2cWorkerBridge.
        val processor = graph.processorFactory.create(temporaryDir.toOkioPath())
        S2cWorkerBridge.register(bridgeToken, graph.processorFactory)
        try {
            logger.setLogLevel(if (silent) LogLevel.QUIET else logLevel.get())
            val activeConfigurations = configurations.filter { it.name != COMMON_CONFIGURATION_NAME }
            val errors = mutableMapOf<Path, Throwable>()
            val outputFiles = mutableMapOf<Path, Path>()

            activeConfigurations.forEach { configuration ->
                processConfiguration(
                    configuration = configuration,
                    inputChanges = inputChanges,
                    registry = registry,
                    fileManager = fileManager,
                    errors = errors,
                    outputFiles = outputFiles,
                    bridgeToken = bridgeToken,
                    logger = logger,
                )
            }

            projectLayout.buildDirectory.dir(WORKER_RESULTS_FOLDER).get().asFile.deleteRecursively()
            updatePersistentOutputRegistry(activeConfigurations, outputFiles, registry)
            registry.save()

            if (errors.isNotEmpty()) {
                throw ExitProgramException(
                    errorCode = ErrorCode.GradlePluginError,
                    "Failed to parse ${errors.size} icons. See logs for more details",
                    causes = errors.values.toTypedArray(),
                )
            }
        } finally {
            S2cWorkerBridge.unregister(bridgeToken)
            processor.dispose()
        }
    }

    private fun processConfiguration(
        configuration: ProcessorConfiguration,
        inputChanges: InputChanges,
        registry: PersistentOutputRegistry,
        fileManager: FileManager,
        errors: MutableMap<Path, Throwable>,
        outputFiles: MutableMap<Path, Path>,
        bridgeToken: String,
        logger: Logger,
    ) {
        val iconConfiguration = configuration.iconConfiguration.get()
        val isPersistent = iconConfiguration.isCodeGenerationPersistent.orNull == true
        val parent = configuration.origin.get().asFile.toOkioPath()
        val recursive = configuration.recursive.get()

        val (filesToProcess, filesToRemove) = resolveFileChanges(
            configuration = configuration,
            inputChanges = inputChanges,
            isPersistent = isPersistent,
            registry = registry,
            fileManager = fileManager,
            logger = logger,
        )

        // Handle removed files — delete corresponding outputs for both persistent and non-persistent modes.
        // Gradle's @OutputDirectory only handles full directory cleanup on non-incremental builds;
        // individual stale outputs must be removed explicitly during incremental builds.
        filesToRemove.forEach { removedPath ->
            if (isPersistent) {
                val outputPath = registry.getOutput(removedPath.toString()) ?: return@forEach
                logger.output("Deleted origin file detected. Deleting generated file $outputPath.")
                fileManager.delete(outputPath.toPath())
                registry.remove(removedPath.toString())
            } else {
                val outputDir = requireNotNull(outputDirectories[configuration.name]).toOkioPath()
                val baseName = removedPath.name.substringBeforeLast('.')
                val outputFile = outputDir / "${baseName.pascalCase()}.kt"
                if (fileManager.exists(outputFile)) {
                    logger.output("Deleted origin file detected. Deleting generated file $outputFile.")
                    fileManager.delete(outputFile)
                }
            }
        }

        if (filesToProcess.isEmpty()) {
            logger.info("No files to process for configuration '${configuration.name}'")
            return
        }

        logger.info("Files eligible for processing: ${filesToProcess.map { it.name }}")
        logger.debug("Processing ${filesToProcess.size} files")
        processFiles(
            filesToProcess,
            configuration,
            recursive,
            parent,
            iconConfiguration,
            errors,
            outputFiles,
            bridgeToken,
            logger,
        )
        logger.debug("End processing ${filesToProcess.size} files.")
    }

    private fun resolveFileChanges(
        configuration: ProcessorConfiguration,
        inputChanges: InputChanges,
        isPersistent: Boolean,
        registry: PersistentOutputRegistry,
        fileManager: FileManager,
        logger: Logger,
    ): Pair<List<Path>, List<Path>> {
        if (!inputChanges.isIncremental) {
            logger.info("Non-incremental build for configuration '${configuration.name}'")
            return findAllFiles(configuration, fileManager) to emptyList()
        }

        val added = mutableListOf<Path>()
        val removed = mutableListOf<Path>()
        inputChanges.getFileChanges(configuration.origin).forEach { change ->
            val ext = change.file.extension.lowercase()
            if (ext != "svg" && ext != "xml") return@forEach
            val path = change.file.toOkioPath()
            when (change.changeType) {
                ChangeType.ADDED, ChangeType.MODIFIED -> added.add(path)
                ChangeType.REMOVED -> removed.add(path)
            }
        }
        // Check for persistent outputs that were manually deleted
        if (isPersistent) {
            added.addAll(findMissingPersistentOutputs(configuration, registry, fileManager))
        }
        return added.distinct() to removed
    }

    private fun updatePersistentOutputRegistry(
        activeConfigurations: List<ProcessorConfiguration>,
        outputFiles: Map<Path, Path>,
        registry: PersistentOutputRegistry,
    ) {
        outputFiles.forEach { (origin, output) ->
            val configForOrigin = activeConfigurations.firstOrNull { config ->
                val configOrigin = config.origin.get().asFile.toOkioPath()
                origin.toString().startsWith(configOrigin.toString() + "/") ||
                    origin == configOrigin
            }
            val isPersistent = configForOrigin?.iconConfiguration?.orNull
                ?.isCodeGenerationPersistent?.orNull == true
            if (isPersistent) {
                registry.register(origin.toString(), output.toString())
            }
        }
    }

    private fun processFiles(
        filesToProcess: List<Path>,
        configuration: ProcessorConfiguration,
        recursive: Boolean,
        parent: Path,
        iconConfiguration: IconParserConfigurationImpl,
        errors: MutableMap<Path, Throwable>,
        outputFiles: MutableMap<Path, Path>,
        bridgeToken: String,
        logger: Logger,
    ) {
        // Prepare worker queue
        var queue: WorkQueue

        // Directory to store per-work-item result files
        val resultsDir = projectLayout
            .buildDirectory
            .dir("$WORKER_RESULTS_FOLDER/${configuration.name}")
            .get()
            .asFile
        if (!resultsDir.exists()) resultsDir.mkdirs()

        // Concurrency is controlled by chunking based on maxParallelExecutions and Gradle's max workers.
        val chunkSize = if (maxParallelExecutions.get() > 1) maxParallelExecutions.get() else 1
        filesToProcess
            .chunked(chunkSize)
            .forEachIndexed { chunkIndex, chunk ->
                queue = workerExecutor.noIsolation()
                chunk.forEachIndexed { index, path ->
                    val globalIndex = chunkIndex * chunkSize + index
                    val output = buildOutput(configuration, recursive, path, parent)
                    val destinationPackage = buildDestinationPackage(configuration, recursive, path, parent)
                    val resultFile = File(resultsDir, "result-$globalIndex.properties")
                    // Pre-compute file output to avoid passing non-serializable icon name mapper to workers
                    queue.submit(
                        path,
                        iconConfiguration,
                        output,
                        destinationPackage,
                        configuration,
                        resultFile,
                        bridgeToken,
                    )
                }
                queue.await()
            }

        val expectedResultCount = filesToProcess.size
        val actualResultFiles = resultsDir.listFiles()?.size ?: 0
        if (actualResultFiles != expectedResultCount) {
            logger.warn(
                "Expected $expectedResultCount result files but found $actualResultFiles. " +
                    "Some workers may have failed. Please see the logs for further investigation.",
            )
        }

        // Collect results
        resultsDir.listFiles()?.forEach { file ->
            val props = Properties()
            file.inputStream().use { props.load(it) }
            val result = props.toResult()
            val origin = result.origin.toPath()
            when (result.status) {
                Status.Ok -> {
                    val output = result.output.toPath()
                    outputFiles[origin] = output
                }

                Status.Error -> {
                    val errorMessage = result.message
                    errors[origin] = RuntimeException(errorMessage)
                }

                Status.Unknown -> {
                    errors[origin] = RuntimeException("Unexpected error while processing $file")
                }
            }
        }
        resultsDir.deleteRecursively()
    }

    private fun WorkQueue.submit(
        path: Path,
        iconConfiguration: IconParserConfigurationImpl,
        output: Path,
        destinationPackage: String,
        configuration: ProcessorConfiguration,
        resultFile: File,
        bridgeToken: String,
    ) {
        val baseName = path.name.substringBeforeLast('.')
        val mappedName = iconConfiguration.mapIconNameTo.orNull?.invoke(baseName) ?: baseName
        val finalFile = (output / "${mappedName.pascalCase()}.kt").toFile()
        submit(IconParsingWorkAction::class.java) {
            inputFilePath.set(path.toFile().absolutePath)
            outputDirPath.set(finalFile.absolutePath)
            this.destinationPackage.set(destinationPackage)
            this.recursive.set(false) // recursive handled at plugin level
            maxDepth.set(configuration.maxDepth.get())
            optimize.set(configuration.optimize.get())
            minified.set(iconConfiguration.minified.get())
            theme.set(iconConfiguration.theme.get())
            receiverType.set(iconConfiguration.receiverType.orNull)
            addToMaterial.set(iconConfiguration.addToMaterialIcons.get())
            noPreview.set(iconConfiguration.noPreview.get())
            makeInternal.set(iconConfiguration.iconVisibility.get() == IconVisibility.Internal)
            excludePattern.set(iconConfiguration.exclude.orNull?.pattern)
            kmpPreview.set(kmp.get())
            resultFilePath.set(resultFile.absolutePath)
            this.bridgeToken.set(bridgeToken)
            tempDirPath.set(temporaryDir.resolve("worker-${path.name.hashCode()}").absolutePath)
        }
    }

    private fun buildOutput(configuration: ProcessorConfiguration, recursive: Boolean, path: Path, parent: Path): Path =
        requireNotNull(outputDirectories[configuration.name])
            .toOkioPath()
            .let { output ->
                if (recursive && path.parent != parent) {
                    (output / path.relativeTo(parent)).parent
                } else {
                    null
                } ?: output
            }

    private fun buildDestinationPackage(
        configuration: ProcessorConfiguration,
        recursive: Boolean,
        path: Path,
        parent: Path,
    ): String = configuration.destinationPackage.get().let { pkg ->
        pkg + if (recursive && path.parent != parent) {
            ".${path.relativeTo(parent).parent?.segments?.joinToString(".")}"
        } else {
            ""
        }
    }

    private fun findAllFiles(configuration: ProcessorConfiguration, fileManager: FileManager): List<Path> {
        val iconConfiguration = configuration.iconConfiguration.get()
        return fileManager.findFilesToProcess(
            from = configuration.origin.get().asFile.toOkioPath(),
            recursive = configuration.recursive.get(),
            maxDepth = configuration.maxDepth.orNull,
            exclude = iconConfiguration.exclude.orNull,
        )
    }

    private fun findMissingPersistentOutputs(
        configuration: ProcessorConfiguration,
        registry: PersistentOutputRegistry,
        fileManager: FileManager,
    ): List<Path> {
        val configOrigin = configuration.origin.get().asFile.toOkioPath()
        return registry.allEntries()
            .filter { (origin, output) ->
                origin.startsWith(configOrigin.toString() + "/") &&
                    fileManager.exists(origin.toPath()) &&
                    !fileManager.exists(output.toPath())
            }
            .map { (origin, _) -> origin.toPath() }
    }
}

/**
 * Registers the "parseSvgToComposeIcon" Gradle task and wires generated sources into the project's Kotlin source sets.
 *
 * Validates the provided extension and fails the build if configuration errors are found. Creates a task
 * configured from the extension (including max parallelism, log level and source directory convention)
 * and makes Kotlin compilation tasks depend on it. If the project is Kotlin Multiplatform, adds the
 * generated directory to the `commonMain` Kotlin source set; otherwise, waits for the Android plugin and
 * adds the generated directory to the Android `main` source set.
 *
 * @param extension The plugin extension containing configuration for parsing SVGs to Compose icons.
 */
internal fun Project.registerParseSvgToComposeIconTask(extension: SvgToComposeExtension) {
    val kmpExtension = extensions.findByType<KotlinMultiplatformExtension>()
    val outputSourceDir = layout.buildDirectory.dir(
        buildString {
            append(GENERATED_FOLDER)
            append(
                if (kmpExtension != null) {
                    "/commonMain"
                } else {
                    "/main"
                },
            )
            append("/kotlin/")
        },
    )

    val task = tasks.register(
        "parseSvgToComposeIcon",
        ParseSvgToComposeIconTask::class.java,
    ) {
        extension.applyCommonIfDefined()
        val errors = extension.validate()
        logger.debug("errors = {}", errors)
        check(errors.isEmpty()) {
            buildString {
                appendLine("Found ${errors.size} configuration errors. See next lines for more details.")
                append(errors.joinToString("\n") { "\t- $it" })
            }
        }

        kmp.set(kmpExtension != null)
        configurations = extension.configurations
        maxParallelExecutions.set(extension.maxParallelExecutions.convention(0))
        logLevel.set(project.gradle.startParameter.logLevel)
        sourceDirectory.set(outputSourceDir)
    }

    // Register this task as a dependency of all Kotlin compile tasks (JVM, JS, Native)
    tasks.withType<AbstractKotlinCompileTool<*>> {
        dependsOn(task)
    }

    when {
        kmpExtension != null -> {
            val sourceSet = kmpExtension.targets
                .first { it.platformType == KotlinPlatformType.common }
                .compilations
                .first { it.platformType == KotlinPlatformType.common }
                .defaultSourceSet
                .kotlin

            sourceSet.srcDirs(outputSourceDir)
        }

        else -> addToAndroidSourceSet(task)
    }
}

/**
 * Registers the task's generated source directory with each Android variant
 * via the [AndroidComponentsExtension] variant API. This keeps resolution
 * fully lazy — [layout.buildDirectory][org.gradle.api.file.ProjectLayout.getBuildDirectory]
 * overrides applied after plugin evaluation are respected.
 *
 * Works with both AGP 8 and AGP 9.
 */
private fun Project.addToAndroidSourceSet(task: TaskProvider<ParseSvgToComposeIconTask>) {
    val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)
    androidComponents.onVariants { variant ->
        variant.sources.kotlin?.addGeneratedSourceDirectory(
            task,
            ParseSvgToComposeIconTask::sourceDirectory,
        )
    }
}
