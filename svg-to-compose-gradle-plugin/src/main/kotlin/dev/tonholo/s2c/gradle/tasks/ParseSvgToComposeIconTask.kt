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

abstract class ParseSvgToComposeIconTask : DefaultTask() {
    init {
        group = "svg-to-compose"
        description = "Parse svg or avg to compose icons"
    }

    @get:Internal
    lateinit var configurations: NamedDomainObjectContainer<ProcessorConfiguration>

    @get:Internal
    var isKmp: Boolean = false

    @get:Internal
    val outputDirectories: Map<String, File>
        get() = configurations.asMap.mapValues { (_, configuration) ->
            val destination = configuration.destinationPackage.replace(".", "/")
            project.layout.buildDirectory.dir(
                buildString {
                    append("generated/svgToCompose/")
                    append(
                        if (isKmp) {
                            "commonMain/kotlin/"
                        } else {
                            "main/kotlin/"
                        },
                    )
                    append(destination)
                },
            ).get().asFile
        }

    @get:OutputDirectory
    val sourceDirectory: File
        get() = project.layout.buildDirectory.dir(
            buildString {
                append("generated/svgToCompose/")
                append(
                    if (isKmp) {
                        "commonMain/"
                    } else {
                        "main/"
                    },
                )
                append("kotlin/")
            },
        ).get().asFile

    private val processor = Processor(
        fileSystem = FileSystem.SYSTEM,
        iconWriter = IconWriter(FileSystem.SYSTEM),
        tempFileWriter = TempFileWriter(FileSystem.SYSTEM),
    )

    @TaskAction
    fun run() {
        configurations.asMap.forEach { (key, configuration) ->
            println("key = $key, value = $configuration")
            processor.run(
                path = configuration.origin.asFile.absolutePath,
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
                ),
                recursive = configuration.recursive,
                maxDepth = configuration.maxDepth,
            )
        }
    }
}

context(Project)
fun TaskContainer.registerParseSvgToComposeIconTask(extension: SvgToComposeExtension) {
    println("Registering task")
    val kmpExtension = extensions.findByType<KotlinMultiplatformExtension>()
    val task = tasks.register("parseSvgToComposeIcon", ParseSvgToComposeIconTask::class.java) {
        println("Configuring task")
        println("extension = $extension")
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
