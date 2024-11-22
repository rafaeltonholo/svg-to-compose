package dev.tonholo.s2c.gradle.tasks

import com.android.build.gradle.BaseExtension
import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.error.ParserException
import dev.tonholo.s2c.gradle.dsl.IconVisibility
import dev.tonholo.s2c.gradle.dsl.ProcessorConfiguration
import dev.tonholo.s2c.gradle.dsl.SvgToComposeExtension
import dev.tonholo.s2c.gradle.internal.cache.CacheManager
import dev.tonholo.s2c.gradle.internal.inject.DependencyModule
import dev.tonholo.s2c.gradle.internal.logger.setLogLevel
import dev.tonholo.s2c.gradle.internal.parser.IconParserConfigurationImpl
import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.parser.ParserConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import okio.Path
import okio.Path.Companion.toOkioPath
import org.gradle.api.DefaultTask
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import java.io.File
import javax.inject.Inject

internal const val GENERATED_FOLDER = "generated/svgToCompose"

internal abstract class ParseSvgToComposeIconTask @Inject constructor(
    objectFactory: ObjectFactory,
) : DefaultTask() {
    private val dependencies: DependencyModule = objectFactory
        .property(DependencyModule::class.java)
        .convention(DependencyModule(project))
        .get()
    private val logger: Logger = dependencies.get()
    private val processor: Processor = dependencies.get()
    private val fileManager: FileManager by lazy { dependencies.get() }
    private val cacheManager: CacheManager by lazy { dependencies.get() }
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val logLevel: LogLevel by lazy { project.gradle.startParameter.logLevel }

    init {
        group = "svg-to-compose"
        description = "Parse svg or avg to compose icons"
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
                configuration.name to project.objects.directoryProperty().convention(
                    project.layout.buildDirectory.dir(
                        buildString {
                            append(GENERATED_FOLDER)
                            append(
                                if (isKmp) {
                                    "/commonMain/kotlin/"
                                } else {
                                    "/main/kotlin/"
                                },
                            )
                            append(destination)
                        },
                    ),
                ).get().asFile
            }

    @get:OutputDirectory
    val sourceDirectory: File
        get() = project.objects.directoryProperty().convention(
            project.layout.buildDirectory.dir(
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
    fun run() = runBlocking {
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

    private suspend fun processFiles(
        filesToProcess: List<Path>,
        configuration: ProcessorConfiguration,
        recursive: Boolean,
        parent: Path,
        iconConfiguration: IconParserConfigurationImpl,
        errors: MutableMap<Path, Throwable>,
        outputFiles: MutableMap<Path, Path>
    ) {
        if (maxParallelExecutions > 1) {
            filesToProcess
                .chunked(size = maxParallelExecutions)
                .forEach { files ->
                    logger.debug("Processing ${files.size} files")
                    val operations = files.map { path ->
                        scope.async {
                            path.process(configuration, recursive, parent, iconConfiguration, errors)
                        }
                    }
                    val processedFiles = operations.awaitAll()
                    logger.debug("End processing ${files.size} files.")
                    outputFiles.putAll(processedFiles.filterNotNull())
                }
        } else {
            val processedFiles = filesToProcess.map { files ->
                files.process(
                    configuration,
                    recursive,
                    parent,
                    iconConfiguration,
                    errors,
                )
            }
            outputFiles.putAll(processedFiles.filterNotNull())
        }
    }

    internal fun dispose() {
        if (scope.isActive) {
            scope.cancel()
        }
    }

    private fun Path.process(
        configuration: ProcessorConfiguration,
        recursive: Boolean,
        parent: Path,
        iconConfiguration: IconParserConfigurationImpl,
        errors: MutableMap<Path, Throwable>,
    ): Pair<Path, Path>? {
        logger.debug("Enqueued $path to parse.")
        val output = buildOutput(configuration, recursive, this, parent)
        val destinationPackage =
            buildDestinationPackage(configuration, recursive, this, parent)
        return try {
            logger.debug("Parsing $path.")
            processor.run(
                path = toFile().absolutePath,
                output = output.toFile().absolutePath,
                config = ParserConfig(
                    pkg = destinationPackage,
                    optimize = configuration.optimize.get(),
                    minified = iconConfiguration.minified.get(),
                    theme = iconConfiguration.theme.get(),
                    receiverType = iconConfiguration.receiverType.orNull,
                    addToMaterial = iconConfiguration.addToMaterialIcons.get(),
                    noPreview = iconConfiguration.noPreview.get(),
                    makeInternal = iconConfiguration.iconVisibility.get() == IconVisibility.Internal,
                    exclude = iconConfiguration.exclude.orNull,
                    kmpPreview = isKmp,
                    silent = true, // TODO: remove when logger migration is done.
                    keepTempFolder = true,
                ),
                recursive = false, // recursive search is handled by the plugin.
                maxDepth = configuration.maxDepth.get(),
                mapIconName = iconConfiguration.mapIconNameTo.orNull,
            ).singleOrNull()?.let { this to it }
        } catch (e: ExitProgramException) {
            errors += this to requireNotNull(e.cause)
            null
        } catch (e: ParserException) {
            errors += this to e
            null
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
        doLast { dispose() }
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
        val sourceSet = (project.extensions.findByName("android") as BaseExtension)
            .sourceSets
            .first { it.name == "main" }
            .kotlin

        sourceSet.srcDirs(outputDirectories)
    }
}
