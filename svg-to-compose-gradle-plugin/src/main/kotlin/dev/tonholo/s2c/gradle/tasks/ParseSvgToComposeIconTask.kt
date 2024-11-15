package dev.tonholo.s2c.gradle.tasks

import com.android.build.gradle.BaseExtension
import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.gradle.cache.CacheData
import dev.tonholo.s2c.gradle.dsl.IconVisibility
import dev.tonholo.s2c.gradle.dsl.SvgToComposeExtension
import dev.tonholo.s2c.gradle.dsl.ProcessorConfiguration
import dev.tonholo.s2c.io.IconWriter
import dev.tonholo.s2c.io.TempFileWriter
import dev.tonholo.s2c.parser.ParserConfig
import okio.FileSystem
import org.gradle.api.DefaultTask
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskContainer
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.nio.file.Path
import java.security.MessageDigest
import kotlin.experimental.and

private const val GENERATED_FOLDER = "generated/svgToCompose"

abstract class ParseSvgToComposeIconTask : DefaultTask() {
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
        get() = configurations.asMap.mapValues { (_, configuration) ->
            val destination = configuration.destinationPackage.get().replace(".", "/")
            project.objects.directoryProperty().convention(
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

    private val cacheFile by lazy { project.layout.buildDirectory.file("${GENERATED_FOLDER}/cache.bin").get().asFile }
    private val fileHashMap = mutableMapOf<Path, String>()
    private val processor = Processor(
        fileSystem = FileSystem.SYSTEM,
        iconWriter = IconWriter(FileSystem.SYSTEM),
        tempFileWriter = TempFileWriter(FileSystem.SYSTEM),
    )

    @TaskAction
    fun run() {
        loadCache()
        configurations.asMap.forEach { (key, configuration) ->
            println("key = $key, value = $configuration")
            val iconConfiguration = configuration.iconConfiguration.get()
            val files = configuration.origin.get().asFile.listFiles { file ->
                val isNotExcluded = iconConfiguration.exclude.orNull?.let(file.name::matches)?.not() ?: true
                isNotExcluded && (file.extension == "svg" || file.extension == "xml")
            } ?: emptyArray()

            val filesToProcess = files.filter { file ->
                val path = file.toPath()
                val currentHash = calculateFileHash(file)
                val previousHash = fileHashMap[path]
                val hasChanged = previousHash != currentHash
                if (hasChanged) {
                    fileHashMap[path] = currentHash
                }
                hasChanged
            }

            val deletedFiles = fileHashMap.keys.filter { path -> path.toFile().exists().not() }
            deletedFiles.forEach { file ->
                fileHashMap.remove(file)
                // TODO: delete generated file.
            }

            if (filesToProcess.isEmpty()) {
                println("No files to process for configuration $key")
                return@forEach
            } else {
                println("Files eligible for processing: ${filesToProcess.map { it.name }}")
            }

            filesToProcess
                .chunked(5)
                .map { it.stream() }
                .forEach { files ->
                    files.parallel().forEach { file ->
                        processor.run(
                            path = file.absolutePath,
                            output = requireNotNull(outputDirectories[key]).absolutePath,
                            config = ParserConfig(
                                pkg = configuration.destinationPackage.get(),
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
                            ),
                            recursive = false, // TODO: recursive should be handled by plugin.
                            maxDepth = configuration.maxDepth.get(),
                        )
                    }
                }
        }
        println("Finished processing files. Creating cache...")
        saveCache()
        println("Cache created.")
        processor.dispose()
    }

    private fun calculateFileHash(file: File): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val fileBytes = file.readBytes()
        val hashBytes = digest.digest(fileBytes)
        return hashBytes.joinToString(separator = "") { byte -> "%02x".format(byte and 0xff.toByte()) }
    }

    private fun saveCache() {
        println("Saving cache...")
        println("Generating extension configuration hashes")
        val configurations = configurations.asMap.mapValues { (_, configuration) ->
            configuration.calculateHash()
        }
        println("Generating file hashes")
        val files = fileHashMap.mapKeys { (path, _) -> path.toString() }
        println("Building Cache Data")
        val cacheData = CacheData(
            files = files,
            extensionConfiguration = configurations,
        )
        println("Writing cache file")
        try {
            ObjectOutputStream(FileOutputStream(cacheFile))
                .use {
                    it.writeObject(cacheData)
                }
            println("Cache file written")
        } catch (e: IOException) {
            println("Error writing cache file: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun loadCache() {
        if (cacheFile.exists()) {
            val cacheData = try {
                ObjectInputStream(FileInputStream(cacheFile)).use {
                    it.readObject() as CacheData
                }.also { println("Cache file loaded") }
            } catch (e: IOException) {
                println("Error loading cache file: ${e.message}")
                e.printStackTrace()
                null
            }
            cacheData?.validate()
        }
    }

    private fun removeCacheIfExists() {
        if (cacheFile.exists()) {
            cacheFile.delete()
        }
    }

    private fun CacheData.validate() {
        if (fileHashMap.isEmpty()) {
            fileHashMap.putAll(files.mapKeys { (path, _) -> Path.of(path) })
        } else {
            // validate that the files are the same
        }

        configurations.asMap.forEach { (key, configuration) ->
            val cachedConfig = extensionConfiguration[key]
            if (cachedConfig != null && cachedConfig != configuration.calculateHash()) {
                fileHashMap.clear()
                removeCacheIfExists()
            }
        }
    }
}

fun TaskContainer.registerParseSvgToComposeIconTask(project: Project, extension: SvgToComposeExtension) =
    with(project) {
        val kmpExtension = extensions.findByType<KotlinMultiplatformExtension>()
        val task = tasks.register("parseSvgToComposeIcon", ParseSvgToComposeIconTask::class.java) {
            val errors = extension.validate()
            println("errors = $errors")
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
