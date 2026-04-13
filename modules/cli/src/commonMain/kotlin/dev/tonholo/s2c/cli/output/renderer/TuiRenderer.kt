package dev.tonholo.s2c.cli.output.renderer

import com.github.ajalt.mordant.input.KeyboardEvent
import com.github.ajalt.mordant.terminal.Terminal
import dev.tonholo.s2c.cli.output.tui.animation.AnimationController
import dev.tonholo.s2c.cli.output.tui.layout.ProgressBarLayouts
import dev.tonholo.s2c.cli.output.tui.reducer.reduceCurrentFile
import dev.tonholo.s2c.cli.output.tui.reducer.reduceHeader
import dev.tonholo.s2c.cli.output.tui.reducer.reduceProgress
import dev.tonholo.s2c.cli.output.tui.reducer.reduceRecentFiles
import dev.tonholo.s2c.cli.output.tui.state.TuiState
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.OutputRenderer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal class TuiRenderer(terminal: Terminal) : OutputRenderer {
    private val state = MutableStateFlow(TuiState())
    private val controller = AnimationController(
        terminal = terminal,
        layouts = ProgressBarLayouts(terminal),
        state = state,
    )

    override fun onEvent(event: ConversionEvent) {
        state.update { current ->
            val optimizationEnabled = current.header.config?.optimizationEnabled ?: true
            current
                .withHeader { reduceHeader(state = it, event = event) }
                .withProgress { reduceProgress(state = it, event = event) }
                .withCurrentFile {
                    reduceCurrentFile(
                        state = it,
                        event = event,
                        optimizationEnabled = optimizationEnabled,
                    )
                }
                .withRecentFiles { reduceRecentFiles(state = it, event = event) }
        }
        state.value.progress?.let { controller.sync(it) }
    }

    internal fun handleKeyEvent(event: KeyboardEvent) {
        when {
            event.key == "h" -> state.update { it.withHeader { h -> h.copy(expanded = !h.expanded) } }
            event.ctrl && event.key == "c" -> stop()
        }
    }

    suspend fun run() = controller.run()

    fun stop() = controller.stop()
}
