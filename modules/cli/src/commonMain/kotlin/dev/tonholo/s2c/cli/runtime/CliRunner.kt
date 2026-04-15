package dev.tonholo.s2c.cli.runtime

import com.github.ajalt.mordant.input.coroutines.receiveKeyEventsFlow
import com.github.ajalt.mordant.input.isCtrlC
import com.github.ajalt.mordant.platform.MultiplatformSystem.exitProcess
import com.github.ajalt.mordant.terminal.Terminal
import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.SvgToComposeContext
import dev.tonholo.s2c.cli.inject.coroutine.DefaultDispatcher
import dev.tonholo.s2c.cli.inject.coroutine.IoDispatcher
import dev.tonholo.s2c.cli.output.renderer.PlainTextRenderer
import dev.tonholo.s2c.cli.output.renderer.TuiRenderer
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.output.RunStats
import dev.tonholo.s2c.parser.IconMapperFn
import dev.tonholo.s2c.updateConfig
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch

private const val SIGINT_EXIT_CODE = 130

@Inject
internal class CliRunner(
    private val processorFactory: Processor.Factory,
    private val terminal: Terminal,
    private val context: SvgToComposeContext,
    @param:IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    @param:DefaultDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend fun run(config: RunConfig, mapIconNameTo: IconMapperFn) {
        val processor = processorFactory.create(tempDirectory = null)
        try {
            val useTui = terminal.terminalInfo.interactive && !config.noTui

            if (useTui) {
                runWithTui(
                    processor = processor,
                    config = config,
                    mapIconNameTo = mapIconNameTo,
                )
            } else {
                runWithoutTui(processor = processor, config = config, mapIconNameTo = mapIconNameTo)
            }
        } finally {
            processor.dispose()
        }
    }

    private suspend fun runWithTui(processor: Processor, config: RunConfig, mapIconNameTo: IconMapperFn) {
        val previousSilent = context.configSnapshot.silent
        // Silence logger when TUI is active. The TUI renders all progress
        // info from ConversionEvents; logger output would break Mordant's
        // in-place animation redraw.
        context.updateConfig<CliConfig> { it.copy(silent = true) }

        val renderer = TuiRenderer(terminal = terminal)
        var failedCount = 0
        val scope = CoroutineScope(SupervisorJob() + defaultDispatcher)

        try {
            scope.launch { renderer.run() }

            with(renderer) { scope.launchInputHandler() }

            val processorDeferred = with(renderer) {
                scope.asyncRunAsFlow(
                    processor = processor,
                    config = config,
                    mapIconNameTo = mapIconNameTo,
                    onCompleted = { stats ->
                        failedCount = stats.failed
                    },
                )
            }

            processorDeferred.await()
        } finally {
            scope.cancel()
            renderer.stop()
            context.updateConfig<CliConfig> { it.copy(silent = previousSilent) }
        }

        if (failedCount > 0) {
            throw ExitProgramException(
                errorCode = ErrorCode.FailedToParseIconError,
                message = "Failure to parse ($failedCount) file(s). See TUI output for details.",
            )
        }
    }

    /**
     * Launches a coroutine that listens for keyboard events via
     * Mordant's [receiveKeyEventsFlow]. The flow handles raw mode
     * setup/teardown internally and integrates with Mordant's
     * signal handling.
     *
     * On Ctrl+C the TUI is stopped and the process exits immediately
     * with code 130 (128 + SIGINT). A graceful coroutine cancellation
     * is not possible here because [Processor.run] blocks the thread
     * on native I/O and cannot be interrupted cooperatively.
     */
    context(renderer: TuiRenderer)
    private fun CoroutineScope.launchInputHandler(): Job = launch {
        val parent = this
        terminal.receiveKeyEventsFlow()
            .takeWhile { event ->
                if (event.isCtrlC) {
                    renderer.stop()
                    exitProcess(SIGINT_EXIT_CODE)
                }
                true
            }
            .onCompletion {
                parent.cancel()
            }
            .collect { event ->
                renderer.handleKeyEvent(event)
            }
    }

    context(renderer: TuiRenderer)
    private fun CoroutineScope.asyncRunAsFlow(
        processor: Processor,
        config: RunConfig,
        mapIconNameTo: IconMapperFn,
        onCompleted: (RunStats) -> Unit,
    ): Deferred<Unit> = async(context = ioDispatcher) {
        processor.runAsFlow(
            path = config.inputPath,
            output = config.outputPath,
            config = config.parserConfig,
            recursive = config.recursive,
            maxDepth = config.recursiveDepth,
            mapIconName = mapIconNameTo,
        ).collect { event ->
            renderer.onEvent(event)
            if (event is ConversionEvent.RunCompleted) {
                onCompleted(event.stats)
            }
        }
    }

    private fun runWithoutTui(processor: Processor, config: RunConfig, mapIconNameTo: IconMapperFn) {
        val isSilent = context.configSnapshot.silent
        val renderer = PlainTextRenderer()
        var failedCount = 0
        processor.run(
            path = config.inputPath,
            output = config.outputPath,
            config = config.parserConfig,
            recursive = config.recursive,
            maxDepth = config.recursiveDepth,
            mapIconName = mapIconNameTo,
            onEvent = { event ->
                if (!isSilent) {
                    renderer.onEvent(event)
                }
                if (event is ConversionEvent.RunCompleted) {
                    failedCount = event.stats.failed
                }
            },
        )

        if (failedCount > 0) {
            throw ExitProgramException(
                errorCode = ErrorCode.FailedToParseIconError,
                message = "Failure to parse ($failedCount) file(s). See output for details.",
            )
        }
    }
}
