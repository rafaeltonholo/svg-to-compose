package dev.tonholo.s2c.cli.runtime

import dev.tonholo.s2c.runtime.S2cConfig

/**
 * Mutable [S2cConfig] implementation for the CLI.
 *
 * Created with all-false defaults and injected into the DI graph
 * before Clikt parses arguments. [Client.run] updates the flags
 * once the parsed values are available; every component that
 * holds a reference to this instance (e.g. the [dev.tonholo.s2c.logger.Logger] implementation)
 * observes the updated values immediately.
 */
internal data class CliConfig(
    override val debug: Boolean = false,
    override val verbose: Boolean = false,
    override val silent: Boolean = false,
    override val stackTrace: Boolean = false,
    override val parallel: Int = PARALLEL_DISABLED,
) : S2cConfig {
    companion object {
        const val PARALLEL_DISABLED = 0
        const val PARALLEL_CPU_CORES = -1
        const val PARALLEL_ENABLED_DEFAULT_RUNNERS_SIZE = 10
    }
}
