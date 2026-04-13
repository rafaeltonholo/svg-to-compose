package dev.tonholo.s2c.cli.inject

import com.github.ajalt.mordant.terminal.Terminal
import dev.tonholo.s2c.cli.logger.CliLogger
import dev.tonholo.s2c.cli.runtime.CliConfig
import dev.tonholo.s2c.cli.runtime.Client
import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.runtime.S2cConfig
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import okio.FileSystem
import okio.SYSTEM

/**
 * Root dependency graph for the CLI application.
 *
 * Receives core S2C bindings (e.g. [dev.tonholo.s2c.io.FileManager],
 * content parsers) via the contributed [dev.tonholo.s2c.inject.SvgToComposeBindings]
 * container and adds CLI-specific bindings for [FileSystem], [Logger],
 * and the [CliConfig]-to-[S2cConfig] binding.
 *
 * Created once per CLI invocation via [Factory.create].
 */
@DependencyGraph(
    scope = AppScope::class,
    additionalScopes = [CliScope::class],
)
internal interface CliGraph {
    /** The Clikt command entry point, fully injected with all dependencies. */
    val client: Client

    @Binds
    val CliLogger.logger: Logger

    /** Binds the concrete [CliConfig] to the [S2cConfig] interface. */
    @Binds
    val CliConfig.bindsConfig: S2cConfig

    /** Provides the native system [FileSystem] for file I/O operations. */
    @Provides
    fun provideFileSystem(): FileSystem = FileSystem.SYSTEM

    /** Provides a [Terminal] instance for TUI rendering and raw input handling. */
    @Provides
    fun provideTerminal(): Terminal = Terminal()

    /**
     * Factory for creating the [CliGraph].
     */
    @DependencyGraph.Factory
    fun interface Factory {
        /**
         * Creates a new [CliGraph] instance.
         *
         * @param config The mutable [CliConfig] instance that will be updated
         *  by [Client] once CLI flags are parsed.
         */
        fun create(@Provides config: CliConfig): CliGraph
    }
}

internal abstract class CliScope
