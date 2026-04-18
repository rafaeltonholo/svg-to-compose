package dev.tonholo.s2c.cli.output.renderer

import com.github.ajalt.mordant.input.KeyboardEvent
import com.github.ajalt.mordant.terminal.Terminal
import dev.tonholo.s2c.cli.output.tui.animation.AnimationController
import dev.tonholo.s2c.cli.output.tui.layout.ProgressBarLayouts
import dev.tonholo.s2c.cli.output.tui.reducer.reduceCurrentFiles
import dev.tonholo.s2c.cli.output.tui.reducer.reduceHeader
import dev.tonholo.s2c.cli.output.tui.reducer.reduceMode
import dev.tonholo.s2c.cli.output.tui.reducer.reduceProgress
import dev.tonholo.s2c.cli.output.tui.reducer.reduceRecentFiles
import dev.tonholo.s2c.cli.output.tui.reducer.reduceSingleFileCompletion
import dev.tonholo.s2c.cli.output.tui.reducer.reduceUpdateNotification
import dev.tonholo.s2c.cli.output.tui.state.TuiMode
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
                .withMode { reduceMode(state = it, event = event) }
                .withHeader { reduceHeader(state = it, event = event) }
                .withProgress { reduceProgress(state = it, event = event) }
                .withCurrentFiles {
                    reduceCurrentFiles(
                        state = it,
                        event = event,
                        optimizationEnabled = optimizationEnabled,
                    )
                }
                .withRecentFiles { reduceRecentFiles(state = it, event = event) }
                .withUpdateNotification { reduceUpdateNotification(state = it, event = event) }
                .let { updated ->
                    updated.withSingleFileCompletion {
                        reduceSingleFileCompletion(state = it, mode = updated.mode, event = event)
                    }
                }
        }
        state.value.progress?.let { controller.sync(it) }
    }

    internal fun handleKeyEvent(event: KeyboardEvent) {
        when {
            event.key == "h" && state.value.mode == TuiMode.Batch ->
                state.update { it.withHeader { h -> h.copy(expanded = !h.expanded) } }

            event.ctrl && event.key == "c" -> stop()
        }
    }

    internal fun snapshotState(): TuiState = state.value

    suspend fun run() = controller.run()

    fun stop() = controller.stop()
}
