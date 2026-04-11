package dev.tonholo.s2c.cli.runtime

import com.github.ajalt.mordant.input.enterRawModeOrNull
import com.github.ajalt.mordant.input.isCtrlC
import com.github.ajalt.mordant.terminal.Terminal
import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.SvgToComposeContext
import dev.tonholo.s2c.cli.inject.coroutine.IoDispatcher
import dev.tonholo.s2c.cli.output.renderer.TuiRenderer
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.parser.IconMapperFn
import dev.tonholo.s2c.updateConfig
import dev.zacsweers.metro.Inject
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Inject
internal class CliRunner(
    private val processorFactory: Processor.Factory,
    private val terminal: Terminal,
    private val context: SvgToComposeContext,
    @param:IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
) {
    fun run(config: RunConfig, mapIconNameTo: IconMapperFn) {
        val processor = processorFactory.create(tempDirectory = null)
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
    }

    private fun runWithTui(processor: Processor, config: RunConfig, mapIconNameTo: IconMapperFn) {
        // Silence logger when TUI is active. The TUI renders all progress
        // info from ConversionEvents; logger output would break Mordant's
        // in-place animation redraw.
        context.updateConfig<CliConfig> { it.copy(silent = true) }

        val renderer = TuiRenderer(terminal = terminal)

        runBlocking {
            val renderJob = launch { renderer.run() }

            val inputJob = launch(context = ioDispatcher) {
                val rawMode = terminal.enterRawModeOrNull() ?: return@launch
                rawMode.use { scope ->
                    while (isActive) {
                        val event = scope.readKeyOrNull(timeout = INPUT_POLL_INTERVAL) ?: continue
                        if (event.key == "h") {
                            renderer.toggleHeader()
                        }
                        if (event.isCtrlC) break
                    }
                }
            }

            val processorJob = launch(context = ioDispatcher) {
                processor.runAsFlow(
                    path = config.inputPath,
                    output = config.outputPath,
                    config = config.parserConfig,
                    recursive = config.recursive,
                    maxDepth = config.recursiveDepth,
                    mapIconName = mapIconNameTo,
                ).collect { event -> renderer.onEvent(event) }
            }

            processorJob.join()
            renderer.stop()
            inputJob.cancel()
            renderJob.cancel()
        }
    }

    private fun runWithoutTui(processor: Processor, config: RunConfig, mapIconNameTo: IconMapperFn) {
        processor.run(
            path = config.inputPath,
            output = config.outputPath,
            config = config.parserConfig,
            recursive = config.recursive,
            maxDepth = config.recursiveDepth,
            mapIconName = mapIconNameTo,
        )
    }

    companion object {
        private val INPUT_POLL_INTERVAL = 100.milliseconds
    }
}
