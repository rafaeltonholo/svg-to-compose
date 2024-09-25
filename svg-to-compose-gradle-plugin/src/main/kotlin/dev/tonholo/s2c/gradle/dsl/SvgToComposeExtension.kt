package dev.tonholo.s2c.gradle.dsl

import dev.tonholo.s2c.gradle.internal.ProcessorConfiguration
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.tasks.Internal

abstract class SvgToComposeExtension {
    @get:Internal
    abstract val configurations: NamedDomainObjectContainer<ProcessorConfiguration>

    fun processor(action: Action<NamedDomainObjectContainer<ProcessorConfiguration>>) {
        action.execute(configurations)
    }

    internal fun validate(): List<String> = configurations.flatMap { it.validate() }
}
