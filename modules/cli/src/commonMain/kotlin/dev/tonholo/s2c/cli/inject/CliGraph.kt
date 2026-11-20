package dev.tonholo.s2c.cli.inject

import com.github.ajalt.mordant.terminal.Terminal
import dev.tonholo.s2c.cli.output.LineWriter
import dev.tonholo.s2c.cli.output.report.InvocationCommand
import dev.tonholo.s2c.cli.runtime.CliConfig
import dev.tonholo.s2c.cli.runtime.Client
import dev.tonholo.s2c.inject.FileDispatcherBindings
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import okio.FileSystem
import okio.SYSTEM

/**
 * Root dependency graph for the CLI application.
 *
 * Receives core S2C bindings (e.g. [dev.tonholo.s2c.io.FileManager],
 * content parsers) via the contributed [dev.tonholo.s2c.inject.SvgToComposeBindings]
 * container, the CLI bindings from [CliBindings], and adds the process
 * environment: [FileSystem], [Terminal] and [LineWriter].
 *
 * Created once per CLI invocation via [Factory.create].
 */
@DependencyGraph(
    scope = AppScope::class,
    additionalScopes = [CliScope::class],
    excludes = [FileDispatcherBindings::class],
)
internal interface CliGraph : CliBindings {
    /** Provides the native system [FileSystem] for file I/O operations. */
    @Provides
    fun provideFileSystem(): FileSystem = FileSystem.SYSTEM

    /** Provides a [Terminal] instance for TUI rendering and raw input handling. */
    @Provides
    fun provideTerminal(): Terminal = Terminal()

    /** Provides the stdout sink used by the logger and the line-based renderers. */
    @Provides
    fun provideLineWriter(): LineWriter = LineWriter { line -> println(line) }

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
         * @param command The command line the user executed, captured from the
         *  raw process arguments before Clikt parses them.
         */
        fun create(
            @Provides config: CliConfig,
            @Provides command: InvocationCommand,
        ): CliGraph
    }
}

internal abstract class CliScope
