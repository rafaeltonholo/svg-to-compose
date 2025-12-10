package dev.tonholo.s2c.gradle.tasks

import com.android.build.gradle.BaseExtension
import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.extensions.pascalCase
import dev.tonholo.s2c.gradle.dsl.IconVisibility
import dev.tonholo.s2c.gradle.dsl.ProcessorConfiguration
import dev.tonholo.s2c.gradle.dsl.SvgToComposeExtension
import dev.tonholo.s2c.gradle.internal.cache.CacheManager
import dev.tonholo.s2c.gradle.internal.inject.DependencyModule
import dev.tonholo.s2c.gradle.internal.logger.setLogLevel
import dev.tonholo.s2c.gradle.internal.parser.IconParserConfigurationImpl
import dev.tonholo.s2c.gradle.tasks.worker.IconParsingWorkAction
import dev.tonholo.s2c.gradle.tasks.worker.IconParsingWorkActionResult.Status
import dev.tonholo.s2c.gradle.tasks.worker.toResult
import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.logger.Logger
import okio.Path
import okio.Path.Companion.toOkioPath
import okio.Path.Companion.toPath
import org.gradle.api.DefaultTask
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.file.ProjectLayout
import org.gradle.api.invocation.Gradle
import org.gradle.api.logging.LogLevel
import org.gradle.api.logging.Logging
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ProviderFactory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.impldep.kotlinx.serialization.Transient
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.withType
import org.gradle.workers.WorkQueue
import org.gradle.workers.WorkerExecutor
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File
import java.util.Properties
import javax.inject.Inject

internal const val GENERATED_FOLDER = "generated/svgToCompose"
internal const val WORKER_RESULTS_FOLDER = "$GENERATED_FOLDER/worker-results"

internal abstract class ParseSvgToComposeIconTask @Inject constructor(
    private val objectFactory: ObjectFactory,
    providerFactory: ProviderFactory,
    private val projectLayout: ProjectLayout,
    private val gradle: Gradle,
) : DefaultTask() {
    @Transient
    private val dependencies: DependencyModule = objectFactory
        .property(DependencyModule::class.java)
        .convention(
            DependencyModule(
                objectFactory = objectFactory,
                providerFactory = providerFactory,
                logger = Logging.getLogger("ParseSvgToComposeIconTask"),
                buildDirectory = projectLayout.buildDirectory,
            ),
        )
        .get()

    @Transient
    private val logger: Logger = dependencies.get()
    private val processor: Processor = dependencies.get()
    private val fileManager: FileManager by lazy { dependencies.get() }
    private val cacheManager: CacheManager by lazy { dependencies.get() }

    @get:Inject
    protected abstract val workerExecutor: WorkerExecutor

    @Transient
    private val logLevel: LogLevel by lazy { gradle.startParameter.logLevel }

    init {
        group = "svg-to-compose"
        description = "Parse svg or avg to compose icons"
        // Keep Gradle task cache conservative; internal CacheManager ensures correctness.
        outputs.upToDateWhen { false }
    }

    @get:Internal
    internal lateinit var configurations: NamedDomainObjectContainer<ProcessorConfiguration>

    @get:Internal
    internal var maxParallelExecutions: Int = 0

    @get:Internal
    internal var isKmp: Boolean = false

    @get:Internal
    @set:org.gradle.api.tasks.options.Option(option = "silent", description = "Run icon parsing without outputs.")
    internal var silent: Boolean = false

    private val outputDirectories: Map<String, File>
        get() = configurations
            .associate { configuration ->
                val destination = configuration.destinationPackage.get().replace(".", "/")
                val outputFolder =
                    if (configuration.iconConfiguration.orNull?.isCodeGenerationPersistent?.orNull == true) {
                        projectLayout.projectDirectory.dir(
                            buildString {
                                append(
                                    if (isKmp) {
                                        "src/commonMain/kotlin"
                                    } else {
                                        "src/main/kotlin"
                                    },
                                )
                            },
                        ).asFile.toOkioPath()
                    } else {
                        sourceDirectory.toOkioPath()
                    }
                val configurationDestination = outputFolder / destination

                configuration.name to configurationDestination.toFile()
            }

    @get:OutputDirectory
    val sourceDirectory: File
        get() = objectFactory.directoryProperty().convention(
            projectLayout.buildDirectory.dir(
                buildString {
                    append(GENERATED_FOLDER)
                    append(
                        if (isKmp) {
                            "/commonMain"
                        } else {
                            "/main"
                        },
                    )
                    append("/kotlin/")
                },
            )
        ).get().asFile

    @TaskAction
    fun run() {
        try {
            logger.setLogLevel(if (silent) LogLevel.QUIET else logLevel)
            cacheManager.initialize(configurations.asMap)
            val errors = mutableMapOf<Path, Throwable>()
            val outputFiles = mutableMapOf<Path, Path>()
            configurations.forEach { configuration ->
                val filesToProcess = findFilesToProcess(configuration)

                if (filesToProcess.isEmpty()) {
                    logger.info("No files to process for configuration '${configuration.name}'")
                    return@forEach
                } else {
                    logger.info("Files eligible for processing: ${filesToProcess.map { it.name }}")
                }

                val iconConfiguration = configuration.iconConfiguration.get()
                val parent = configuration.origin.get().asFile.toOkioPath()
                val recursive = configuration.recursive.get()
                logger.debug("Processing ${filesToProcess.size} files")
                processFiles(filesToProcess, configuration, recursive, parent, iconConfiguration, errors, outputFiles)
                logger.debug("End processing ${filesToProcess.size} files.")
            }

            projectLayout.buildDirectory.dir(WORKER_RESULTS_FOLDER).get().asFile.deleteRecursively()

            if (errors.isNotEmpty()) {
                errors.forEach { (path, _) ->
                    logger.debug("Removing $path from cache")
                    cacheManager.removeFromCache(path)
                }
            }
            logger.debug("Finished processing files. Creating cache...")
            cacheManager.saveCache(outputFiles)
            logger.debug("Cache created.")
            if (errors.isNotEmpty()) {
                throw ExitProgramException(
                    errorCode = ErrorCode.GradlePluginError,
                    "Failed to parse ${errors.size} icons. See logs for more details",
                    causes = errors.values.toTypedArray(),
                )
            }
        } finally {
            processor.dispose()
        }
    }

    private fun processFiles(
        filesToProcess: List<Path>,
        configuration: ProcessorConfiguration,
        recursive: Boolean,
        parent: Path,
        iconConfiguration: IconParserConfigurationImpl,
        errors: MutableMap<Path, Throwable>,
        outputFiles: MutableMap<Path, Path>
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
        val chunkSize = if (maxParallelExecutions > 1) maxParallelExecutions else 1
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
                    queue.submit(path, iconConfiguration, output, destinationPackage, configuration, resultFile)
                }
                queue.await()
            }

        val expectedResultCount = filesToProcess.size
        val actualResultFiles = resultsDir.listFiles()?.size ?: 0
        if (actualResultFiles != expectedResultCount) {
            logger.warn(
                "Expected $expectedResultCount result files but found $actualResultFiles. " +
                    "Some workers may have failed. Please see the logs for further investigation."
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
        resultFile: File
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
            kmpPreview.set(isKmp)
            resultFilePath.set(resultFile.absolutePath)
        }
    }

    private fun buildOutput(
        configuration: ProcessorConfiguration,
        recursive: Boolean,
        path: Path,
        parent: Path
    ): Path = requireNotNull(outputDirectories[configuration.name])
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
        parent: Path
    ): String = configuration.destinationPackage.get().let { pkg ->
        pkg + if (recursive && path.parent != parent) {
            ".${path.relativeTo(parent).parent?.segments?.joinToString(".")}"
        } else {
            ""
        }
    }

    private fun findFilesToProcess(configuration: ProcessorConfiguration): List<Path> {
        val iconConfiguration = configuration.iconConfiguration.get()

        val files = fileManager.findFilesToProcess(
            from = configuration.origin.get().asFile.toOkioPath(),
            recursive = configuration.recursive.get(),
            maxDepth = configuration.maxDepth.orNull,
            exclude = iconConfiguration.exclude.orNull,
        )

        val filesToProcess = files.filter(cacheManager::hasCacheChanged)
        cacheManager.removeDeletedFilesFromCache()
        return filesToProcess
    }
}

internal fun Project.registerParseSvgToComposeIconTask(
    extension: SvgToComposeExtension,
) {
    val kmpExtension = extensions.findByType<KotlinMultiplatformExtension>()
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

        isKmp = kmpExtension != null
        configurations = extension.configurations
        maxParallelExecutions = extension.maxParallelExecutions.convention(0).get()
    }

    // Register this task as a dependency of KotlinCompile
    tasks.withType<KotlinCompile> {
        dependsOn(task)
    }

    val outputDirectories = task.map { it.sourceDirectory }

    if (kmpExtension != null) {
        val sourceSet = kmpExtension.targets
            .first { it.platformType == KotlinPlatformType.common }
            .compilations
            .first { it.platformType == KotlinPlatformType.common }
            .defaultSourceSet
            .kotlin

        sourceSet.srcDirs(outputDirectories)
    } else {
        val androidExtension = project.extensions.findByName("android") as? BaseExtension
        requireNotNull(androidExtension) {
            "Android plugin must be applied to use this plugin in Android projects"
        }
        val sourceSet = androidExtension
            .sourceSets
            .first { it.name == "main" }
            .kotlin

        sourceSet.srcDirs(outputDirectories)
    }
}
