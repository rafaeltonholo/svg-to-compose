package dev.tonholo.s2c.gradle.dsl

import dev.tonholo.s2c.AppDefaults
import dev.tonholo.s2c.gradle.dsl.parser.IconParserConfiguration
import dev.tonholo.s2c.gradle.dsl.source.SourceConfiguration
import dev.tonholo.s2c.gradle.internal.parser.IconParserConfigurationImpl
import dev.tonholo.s2c.gradle.internal.provider.setIfNotPresent
import org.gradle.api.Action
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.kotlin.dsl.property
import org.gradle.work.Incremental
import javax.inject.Inject

abstract class ProcessorConfiguration @Inject constructor(private val objectFactory: ObjectFactory) :
    SourceConfiguration {
    // Gradle does not inherit annotations from interfaces. These overrides exist solely
    // to attach @Internal so Gradle doesn't treat Named/Configuration properties as task inputs.
    @get:Internal
    abstract override val name: String

    @get:Internal
    override val parentName: String = "svgToCompose.processor"

    @get:Internal
    override val fullName: String
        get() = super.fullName

    @get:Internal
    override val visibleName: String
        get() = super.visibleName

    @get:Incremental
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    internal val origin: DirectoryProperty = objectFactory.directoryProperty()

    @get:Input
    internal val destinationPackage: Property<String> = objectFactory.property()

    @get:Input
    internal val recursive: Property<Boolean> = objectFactory.property<Boolean>()

    @get:Input
    internal val maxDepth: Property<Int> = objectFactory.property<Int>()

    @get:Input
    internal val optimize: Property<Boolean> = objectFactory.property<Boolean>()

    // Gradle's @Nested traverses into the Property value to visit its @Input annotations.
    // The convention is set at declaration time, so the value is always present when Gradle inspects it.
    @get:Nested
    internal val iconConfiguration: Property<IconParserConfigurationImpl> = objectFactory
        .property<IconParserConfigurationImpl>()
        .convention(
            IconParserConfigurationImpl(
                objectFactory = objectFactory,
                parentName = fullName,
            ),
        )

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

    override fun toString(): String = buildString {
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
