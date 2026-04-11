package dev.tonholo.s2c.gradle.internal

import dev.tonholo.s2c.runtime.S2cConfig
import org.gradle.api.logging.Logger

/**
 * [S2cConfig] implementation for the Gradle plugin.
 *
 * Derives all flags from the Gradle [Logger]'s current log level
 * so that S2C debug output follows `--debug` / `--info` Gradle flags.
 */
internal class GradleS2cConfig(logger: Logger) : S2cConfig {
    override val debug: Boolean = logger.isDebugEnabled
    override val verbose: Boolean = logger.isInfoEnabled

    // Plugin runs inside Gradle; console output is handled by Gradle logging.
    override val silent: Boolean = true
    override val stackTrace: Boolean = logger.isDebugEnabled
}
