package dev.tonholo.s2c.gradle.dsl

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
     * Configure the maximum number of simultaneous icon-processing threads.
     *
     * When set to 0 or 1, parallel processing is effectively disabled; values greater than 1
     * allow multiple icons to be processed concurrently up to the specified limit.
     *
     * @param parallelism The maximum number of threads to use for processing icons.
     * @throws IllegalArgumentException if [parallelism] is negative.
     */
    @Suppress("UnusedReceiverParameter")
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