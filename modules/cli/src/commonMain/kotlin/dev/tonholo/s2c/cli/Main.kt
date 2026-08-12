package dev.tonholo.s2c.cli

import com.github.ajalt.clikt.command.main
import dev.tonholo.s2c.cli.inject.CliGraph
import dev.tonholo.s2c.cli.output.report.InvocationCommand
import dev.tonholo.s2c.cli.output.report.formatCommandLine
import dev.tonholo.s2c.cli.runtime.CliConfig
import dev.zacsweers.metro.createGraphFactory
import kotlinx.coroutines.runBlocking

/**
 * CLI entry point.
 *
 * Creates the [CliGraph] with a default [CliConfig] and delegates
 * to Clikt for argument parsing and command execution. The [CliConfig]
 * flags are updated by [dev.tonholo.s2c.cli.runtime.Client.run] once
 * the arguments have been parsed.
 */
fun main(args: Array<String>) = runBlocking {
    val graph = createGraphFactory<CliGraph.Factory>().create(
        config = CliConfig(),
        command = InvocationCommand(value = formatCommandLine(args = args.toList())),
    )
    graph.client.main(args)
}
