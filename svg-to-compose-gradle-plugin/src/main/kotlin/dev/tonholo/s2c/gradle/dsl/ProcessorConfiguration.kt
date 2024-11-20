package dev.tonholo.s2c.gradle.dsl

import dev.tonholo.s2c.AppDefaults
import dev.tonholo.s2c.gradle.dsl.parser.IconParserConfiguration
import dev.tonholo.s2c.gradle.dsl.source.SourceConfiguration
import dev.tonholo.s2c.gradle.internal.parser.IconParserConfigurationImpl
import dev.tonholo.s2c.gradle.internal.provider.setIfNotPresent
import okio.ByteString.Companion.toByteString
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

abstract class ProcessorConfiguration @Inject constructor(
    project: Project,
) : SourceConfiguration {
    override val parentName: String = "svgToCompose.processor"
    internal val origin: DirectoryProperty = project.objects.directoryProperty()
    internal val destinationPackage: Property<String> = project.objects.property()
    internal val recursive: Property<Boolean> = project
        .objects
        .property<Boolean>()
    internal val maxDepth: Property<Int> = project
        .objects
        .property<Int>()
    internal val optimize: Property<Boolean> = project
        .objects
        .property<Boolean>()

    internal val iconConfiguration: Property<IconParserConfigurationImpl> by lazy {
        project
            .objects
            .property<IconParserConfigurationImpl>()
            .convention(IconParserConfigurationImpl(project, fullName))
    }

    override fun from(origin: Directory) {
        this.origin.set(origin)
    }

    override fun destinationPackage(fullPackage: String) {
        destinationPackage.set(fullPackage)
    }

    override fun recursive() {
        recursive.set(true)
    }

    override fun maxDepth(depth: Int) {
        maxDepth.set(depth)
    }

    override fun optimize(enabled: Boolean) {
        optimize.set(enabled)
    }

    override fun icons(configure: Action<IconParserConfiguration>) {
        configure.execute(iconConfiguration.get())
    }

    override fun validate(): List<String> {
        val errors = mutableListOf<String>()
        val iconErrors = iconConfiguration.get().validate()
        if (iconErrors.isNotEmpty()) {
            errors.addAll(iconErrors)
        }

        origin
            .orNull
            ?.asFile
            ?.let { origin ->
                if (origin.exists().not()) {
                    errors.add("${configurationName()}: The specified icons origin should exist.")
                }

                if (origin.isDirectory.not()) {
                    errors.add("${configurationName()}: The specified icons origin path should be a directory.")
                }
            }
            ?: errors.add(
                "${configurationName()}: The icons origin cannot be null. Have you missed calling from(\"origin\")?",
            )

        if (destinationPackage.orNull.isNullOrBlank()) {
            errors.add("${configurationName()}: Destination package cannot be empty.")
        }

        return errors
    }

    internal fun calculateHash(): String {
        val raw = buildString {
            append(origin.get())
            append("|")
            append(destinationPackage.get())
            append("|")
            append(recursive.get())
            append("|")
            append(maxDepth.get())
            append("|")
            append(iconConfiguration.get().calculateHash())
        }
        return raw.toByteArray().toByteString().sha256().hex()
    }

    fun merge(common: ProcessorConfiguration) {
        origin.setIfNotPresent(common.origin)
        destinationPackage.setIfNotPresent(common.destinationPackage)
        recursive.setIfNotPresent(common.recursive, defaultValue = AppDefaults.RECURSIVE)
        maxDepth.setIfNotPresent(common.maxDepth, defaultValue = AppDefaults.MAX_RECURSIVE_DEPTH)
        optimize.setIfNotPresent(common.optimize, defaultValue = AppDefaults.OPTIMIZE)
        common.iconConfiguration.orNull?.let { commonIconConfig ->
            iconConfiguration
                .get()
                .merge(commonIconConfig)
        }
    }

    override fun toString(): String {
        return buildString {
            appendLine("ProcessorConfiguration(")
            appendLine("  name='$name', ")
            appendLine("  iconConfiguration=${iconConfiguration.orNull}, ")
            appendLine("  optimize=${optimize.orNull}, ")
            appendLine("  maxDepth=${maxDepth.orNull}, ")
            appendLine("  recursive=${recursive.orNull}, ")
            appendLine("  destinationPackage=${destinationPackage.orNull}, ")
            appendLine("  origin=${origin.orNull}, ")
            append(")")
        }
    }
}
