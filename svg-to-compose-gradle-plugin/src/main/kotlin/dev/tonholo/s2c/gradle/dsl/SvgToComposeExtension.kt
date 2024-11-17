package dev.tonholo.s2c.gradle.dsl

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.tasks.Internal

abstract class SvgToComposeExtension {
    @get:Internal
    abstract val configurations: NamedDomainObjectContainer<ProcessorConfiguration>

    fun processor(action: Action<NamedDomainObjectContainer<ProcessorConfiguration>>) {
        action.execute(configurations)
    }

    fun NamedDomainObjectContainer<ProcessorConfiguration>.common(action: Action<ProcessorConfiguration>) {
        create("common") {
            action.execute(this)
        }
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

    private val NamedDomainObjectContainer<ProcessorConfiguration>.commonOrNull: ProcessorConfiguration?
        get() = findByName("common")
}
