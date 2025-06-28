package dev.tonholo.s2c.gradle.dsl

import dev.tonholo.s2c.annotations.ExperimentalParallelProcessing
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Internal

abstract class SvgToComposeExtension {
    @get:Internal
    abstract val configurations: NamedDomainObjectContainer<ProcessorConfiguration>

    @get:Internal
    abstract val maxParallelExecutions: Property<Int>

    /**
     * Configures individual processor instances.
     *
     * @param action An action to configure the processors.
     *               This action receives a container of processor configurations,
     *               allowing you to define and customize each processor.
     */
    fun processor(action: Action<NamedDomainObjectContainer<ProcessorConfiguration>>) {
        action.execute(configurations)
    }

    /**
     * Defines a common configuration that applies to all processors.
     *
     * This block allows you to set default values for properties shared by all
     * processors. Individual processor configurations can override these common
     * settings if needed.
     *
     * @param action An action to configure the common properties.
     */
    fun NamedDomainObjectContainer<ProcessorConfiguration>.common(action: Action<ProcessorConfiguration>) {
        create("common") {
            action.execute(this)
        }
    }

    /**
     * Enables experimental parallel processing of SVG/XML icons.
     *
     * When enabled, icons are processed in parallel, limited by the specified [parallelism] level.
     * This can significantly speed up the conversion process, especially for large projects with
     * many icons.
     *
     * @param parallelism The maximum number of threads to use for parallel processing.
     *                     A value of 0 or 1 disables parallel processing.
     */
    @Suppress("UnusedReceiverParameter")
    @ExperimentalParallelProcessing
    fun NamedDomainObjectContainer<ProcessorConfiguration>.useParallelism(parallelism: Int) {
        require(parallelism >= 0) { "Parallelism must be non-negative, got: $parallelism" }
        maxParallelExecutions.set(parallelism)
    }

    internal fun validate(): List<String> = configurations.flatMap { config ->
        if (config == configurations.commonOrNull) {
            emptyList()
        } else {
            config.validate()
        }
    }

    internal fun applyCommonIfDefined() {
        configurations.commonOrNull?.let { common ->
            configurations.remove(common)
            configurations.configureEach {
                if (this != common) {
                    merge(common)
                }
            }
        }
    }

    override fun toString(): String {
        return "SvgToComposeExtension(" +
            "configurations=[${configurations.joinToString { it.toString() }}], " +
            "maxParallelExecutions=${maxParallelExecutions.get()}" +
            ")"
    }

    private val NamedDomainObjectContainer<ProcessorConfiguration>.commonOrNull: ProcessorConfiguration?
        get() = findByName("common")
}
