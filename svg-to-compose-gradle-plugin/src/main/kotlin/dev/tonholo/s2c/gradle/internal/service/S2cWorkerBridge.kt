package dev.tonholo.s2c.gradle.internal.service

import dev.tonholo.s2c.Processor
import java.util.concurrent.ConcurrentHashMap

/**
 * Static bridge for sharing [Processor.Factory] instances with `noIsolation()` workers.
 *
 * Each task execution registers its factory under a unique token so that concurrent
 * multi-project builds never interfere with each other. Workers look up the factory
 * by the token passed via [IconParsingParameters.bridgeToken].
 */
internal object S2cWorkerBridge {
    private val registry = ConcurrentHashMap<String, Processor.Factory>()

    fun register(token: String, factory: Processor.Factory) {
        registry[token] = factory
    }

    fun get(token: String): Processor.Factory = requireNotNull(registry[token]) {
        "No Processor.Factory registered for token '$token'. " +
            "Ensure the task calls S2cWorkerBridge.register() before submitting workers."
    }

    fun unregister(token: String) {
        registry.remove(token)
    }
}
