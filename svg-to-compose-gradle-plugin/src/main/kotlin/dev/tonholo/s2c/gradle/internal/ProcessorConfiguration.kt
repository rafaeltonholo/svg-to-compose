package dev.tonholo.s2c.gradle.internal

import dev.tonholo.s2c.AppDefaults
import dev.tonholo.s2c.gradle.dsl.parser.IconParserConfiguration
import dev.tonholo.s2c.gradle.dsl.source.SourceConfiguration
import dev.tonholo.s2c.gradle.internal.parser.IconParserConfigurationImpl
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.property
import java.security.MessageDigest
import javax.inject.Inject
import kotlin.experimental.and

abstract class ProcessorConfiguration @Inject constructor(
    project: Project,
) : SourceConfiguration {
    override val parentName: String = "svgToCompose.processor"
    internal val origin: DirectoryProperty = project.objects.directoryProperty()
    internal val destinationPackage: Property<String> = project.objects.property()
    internal val recursive: Property<Boolean> = project
        .objects
        .property<Boolean>()
        .convention(AppDefaults.RECURSIVE)
    internal val maxDepth: Property<Int> = project
        .objects
        .property<Int>()
        .convention(AppDefaults.MAX_RECURSIVE_DEPTH)
    internal val optimize: Property<Boolean> = project
        .objects
        .property<Boolean>()
        .convention(AppDefaults.OPTIMIZE)

    internal val iconConfiguration: Property<IconParserConfigurationImpl> by lazy {
        project
            .objects
            .property<IconParserConfigurationImpl>()
            .convention(IconParserConfigurationImpl(project, fullName))
    }

    override fun from(directory: Directory) {
        origin.set(directory)
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
                    errors.add("${configurationName()}: The specified origin should exist.")
                }

                if (origin.isDirectory.not()) {
                    errors.add("${configurationName()}: The specified origin should be a directory.")
                }
            } ?: errors.add("${configurationName()}: Origin cannot be null.")

        if (destinationPackage.isPresent.not() || destinationPackage.get().isBlank()) {
            errors.add("${configurationName()}: Destination package cannot be empty.")
        }

        return errors
    }

    internal fun calculateHash(): String {
        val digest = MessageDigest.getInstance("SHA-256")
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
        }.also { println(it) }
        return digest.digest(raw.toByteArray()).joinToString("") { "%02x".format(it and 0xff.toByte()) }
    }
}
