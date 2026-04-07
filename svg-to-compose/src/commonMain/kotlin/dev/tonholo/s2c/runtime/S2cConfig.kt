package dev.tonholo.s2c.runtime

import com.rsicarelli.fakt.Fake

/**
 * Runtime configuration for the S2C processing system.
 *
 * Consumers of the core library provide their own implementation to
 * control logging verbosity, debug output, and error reporting behaviour.
 * The implementation is injected via the dependency graph so that all
 * internal components observe the same configuration.
 */
@Fake
interface S2cConfig {
    val debug: Boolean
    val verbose: Boolean
    val silent: Boolean
    val stackTrace: Boolean
}
