package dev.tonholo.s2c.gradle.internal.service

import dev.tonholo.s2c.Processor

/**
 * Static bridge for sharing [Processor.Factory] with `noIsolation()` workers.
 *
 * Safe because: `noIsolation()` runs in the same JVM; the task sets the factory
 * before submitting workers and clears it after `await()`; workers only read;
 * each worker creates its own [Processor] instance via [Processor.Factory.create].
 */
internal object S2cWorkerBridge {
    @Volatile
    var processorFactory: Processor.Factory? = null
}
