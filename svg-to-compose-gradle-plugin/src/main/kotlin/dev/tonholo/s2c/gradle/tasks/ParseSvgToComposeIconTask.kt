package dev.tonholo.s2c.gradle.tasks

import com.android.build.gradle.BaseExtension
import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.error.MissingDependencyException
import dev.tonholo.s2c.error.OptimizationException
import dev.tonholo.s2c.error.ParserException
import dev.tonholo.s2c.gradle.dsl.IconVisibility
import dev.tonholo.s2c.gradle.dsl.ProcessorConfiguration
import dev.tonholo.s2c.gradle.dsl.SvgToComposeExtension
import dev.tonholo.s2c.gradle.internal.cache.CacheManager
import dev.tonholo.s2c.gradle.internal.inject.DependencyModule
import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.parser.ParserConfig
import okio.Path
import okio.Path.Companion.toOkioPath
import org.gradle.api.DefaultTask
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
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

    init {
        group = "svg-to-compose"
        description = "Parse svg or avg to compose icons"
        outputs.upToDateWhen { false }
    }

    @get:Internal
    lateinit var configurations: NamedDomainObjectContainer<ProcessorConfiguration>

    @get:Internal
    var isKmp: Boolean = false

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
    fun run() {
        cacheManager.initialize(configurations.asMap)
        val errors = mutableMapOf<Path, Exception>()
        configurations.forEach { configuration ->
            val filesToProcess = findFilesToProcess(configuration)

            if (filesToProcess.isEmpty()) {
                logger.output("No files to process for configuration '${configuration.name}'")
                return@forEach
            } else {
                logger.debug("Files eligible for processing: ${filesToProcess.map { it.name }}")
            }

            val iconConfiguration = configuration.iconConfiguration.get()
            val parent = configuration.origin.get().asFile.toOkioPath()
            val recursive = configuration.recursive.get()
            filesToProcess
                .chunked(size = 5)
                .map { it.stream() }
                .forEach { files ->
                    files.parallel().forEach { path ->
                        val output = requireNotNull(outputDirectories[configuration.name])
                            .toOkioPath()
                            .let { output ->
                                if (recursive && path.parent != parent) {
                                    (output / path.relativeTo(parent)).parent
                                } else {
                                    null
                                } ?: output
                            }
                        val destinationPackage = configuration.destinationPackage.get().let { pkg ->
                            pkg + if (recursive && path.parent != parent) {
                                ".${path.relativeTo(parent).parent?.segments?.joinToString(".")}"
                            } else {
                                ""
                            }
                        }
                        try {
                            processor.run(
                                path = path.toFile().absolutePath,
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
                                    iconNameMapper = iconConfiguration.mapIconNameTo.orNull,
                                    kmpPreview = isKmp,
                                    silent = false,
                                    keepTempFolder = true,
                                    parallel = true,
                                ),
                                recursive = false, // recursive search is handled by the plugin.
                                maxDepth = configuration.maxDepth.get(),
                            )
                        } catch (e: ExitProgramException) {
                            errors += path to e
                        } catch (e: ParserException) {
                            errors += path to e
                        } catch (e: MissingDependencyException) {
                            errors += path to e
                        } catch (e: OptimizationException) {
                            errors += path to e
                        }
                    }
                }
        }
        if (errors.isNotEmpty()) {
            errors.forEach { (path, exception) ->
                cacheManager.removeFromCache(path)
                logger.error(message = "Failed to parse $path.", throwable = exception)
            }
        }
        logger.debug("Finished processing files. Creating cache...")
        cacheManager.saveCache()
        logger.debug("Cache created.")
        processor.dispose()
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
