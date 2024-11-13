package dev.tonholo.s2c.gradle.tasks

import com.android.build.gradle.BaseExtension
import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.gradle.dsl.SvgToComposeExtension
import dev.tonholo.s2c.gradle.internal.ProcessorConfiguration
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
import java.nio.file.Path
import java.security.MessageDigest
import java.util.Properties
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
            val destination = configuration.destinationPackage.replace(".", "/")
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

    private val cacheFile by lazy { project.layout.buildDirectory.file("${GENERATED_FOLDER}/s2c.cache").get().asFile }
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

            val files = configuration.origin.asFile.listFiles { file ->
                val isNotExcluded = configuration.exclude?.let(file.name::matches)?.not() ?: true
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

            filesToProcess.forEach { file ->
                processor.run(
                    path = file.absolutePath,
                    output = requireNotNull(outputDirectories[key]).absolutePath,
                    config = ParserConfig(
                        pkg = configuration.destinationPackage,
                        optimize = configuration.optimize,
                        minified = configuration.minified,
                        theme = configuration.theme,
                        receiverType = configuration.receiverType,
                        addToMaterial = configuration.addToMaterial,
                        noPreview = configuration.noPreview,
                        makeInternal = configuration.makeInternal,
                        exclude = configuration.exclude,
                        iconNameMapper = configuration.iconNameMapper,
                        kmpPreview = isKmp,
                        silent = false,
                    ),
                    recursive = configuration.recursive,
                    maxDepth = configuration.maxDepth,
                )
            }
        }
        println("Finished processing files. Creating cache...")
        saveCache()
        println("Cache created.")
    }

    private fun calculateFileHash(file: File): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val fileBytes = file.readBytes()
        val hashBytes = digest.digest(fileBytes)
        return hashBytes.joinToString(separator = "") { byte -> "%02x".format(byte and 0xff.toByte()) }
    }

    private fun saveCache() {
        println("Saving cache...")
        val properties = Properties()
        fileHashMap.forEach { (path, hash) ->
            println("path = $path, hash = $hash")
            properties[path.toString()] = hash
        }
        FileOutputStream(cacheFile).use { properties.store(it, null) }
    }

    private fun loadCache() {
        if (cacheFile.exists() && fileHashMap.isEmpty()) {
            val properties = Properties()
            FileInputStream(cacheFile).use { properties.load(it) }
            properties.forEach { key, value ->
                val path = Path.of(key as String)
                val hash = value as String
                fileHashMap[path] = hash
            }
        }
    }

    internal fun removeCacheIfExists() {
        if (cacheFile.exists()) {
            cacheFile.delete()
        }
    }
}

context(Project)
fun TaskContainer.registerParseSvgToComposeIconTask(extension: SvgToComposeExtension){
    val kmpExtension = extensions.findByType<KotlinMultiplatformExtension>()
    val task = tasks.register("parseSvgToComposeIcon", ParseSvgToComposeIconTask::class.java) {
        val errors = extension.validate()
        println(errors)
        check(errors.isEmpty()) {
            "Errors: $errors"
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
