package dev.tonholo.s2c.cli.inject

import com.github.ajalt.mordant.terminal.Terminal
import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.cli.inject.coroutine.DispatchersBinding
import dev.tonholo.s2c.cli.inject.coroutine.IoDispatcher
import dev.tonholo.s2c.cli.inject.coroutine.MainDispatcher
import dev.tonholo.s2c.cli.output.LineWriter
import dev.tonholo.s2c.cli.output.report.InvocationCommand
import dev.tonholo.s2c.cli.runtime.CliConfig
import dev.tonholo.s2c.cli.runtime.CliRunner
import dev.tonholo.s2c.inject.FileDispatcherBindings
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import kotlinx.coroutines.CoroutineDispatcher
import okio.FileSystem

/**
 * The production CLI wiring from [CliBindings] with the environment replaced
 * by test doubles: an in-memory file system, a recording terminal, a
 * collecting [LineWriter] and test dispatchers.
 */
@DependencyGraph(
    scope = AppScope::class,
    additionalScopes = [CliScope::class],
    excludes = [FileDispatcherBindings::class, DispatchersBinding::class],
)
internal interface CliTestGraph : CliBindings {
    val runner: CliRunner

    val processorFactory: Processor.Factory

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(
            @Provides config: CliConfig,
            @Provides command: InvocationCommand,
            @Provides fileSystem: FileSystem,
            @Provides terminal: Terminal,
            @Provides lineWriter: LineWriter,
            @Provides @IoDispatcher ioDispatcher: CoroutineDispatcher,
            @Provides @MainDispatcher mainDispatcher: CoroutineDispatcher,
        ): CliTestGraph
    }
}
