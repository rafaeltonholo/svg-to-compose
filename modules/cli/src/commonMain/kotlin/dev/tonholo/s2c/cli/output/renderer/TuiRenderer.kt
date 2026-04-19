package dev.tonholo.s2c.cli.output.renderer

import com.github.ajalt.mordant.input.KeyboardEvent
import com.github.ajalt.mordant.terminal.Terminal
import dev.tonholo.s2c.cli.output.tui.animation.AnimationController
import dev.tonholo.s2c.cli.output.tui.layout.ProgressBarLayouts
import dev.tonholo.s2c.cli.output.tui.reducer.reduceCompletion
import dev.tonholo.s2c.cli.output.tui.reducer.reduceCurrentFiles
import dev.tonholo.s2c.cli.output.tui.reducer.reduceHeader
import dev.tonholo.s2c.cli.output.tui.reducer.reduceMode
import dev.tonholo.s2c.cli.output.tui.reducer.reduceProgress
import dev.tonholo.s2c.cli.output.tui.reducer.reduceRecentFiles
import dev.tonholo.s2c.cli.output.tui.reducer.reduceSingleFileCompletion
import dev.tonholo.s2c.cli.output.tui.reducer.reduceUpdateNotification
import dev.tonholo.s2c.cli.output.tui.state.TuiMode
import dev.tonholo.s2c.cli.output.tui.state.TuiState
import dev.tonholo.s2c.cli.output.tui.widget.buildCompletionSummary
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.OutputRenderer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal class TuiRenderer(
    private val terminal: Terminal,
    private val stackTraceEnabled: Boolean = false,
) : OutputRenderer {
    private val state = MutableStateFlow(TuiState())
    private val controller = AnimationController(
        terminal = terminal,
        layouts = ProgressBarLayouts(terminal),
        state = state,
    )

    private var completionPrinted = false

    override fun onEvent(event: ConversionEvent) {
        state.update { current ->
            val nextMode = reduceMode(state = current.mode, event = event)
            val optimizationEnabled = current.header.config?.optimizationEnabled ?: true
            current
                .withMode { nextMode }
                .withHeader { reduceHeader(state = it, event = event) }
                .withProgress { reduceProgress(state = it, event = event) }
                .withCurrentFiles {
                    reduceCurrentFiles(
                        state = it,
                        event = event,
                        mode = nextMode,
                        optimizationEnabled = optimizationEnabled,
                    )
                }
                .withRecentFiles { reduceRecentFiles(state = it, event = event) }
                .withUpdateNotification { reduceUpdateNotification(state = it, event = event) }
                .withSingleFileCompletion {
                    reduceSingleFileCompletion(state = it, mode = nextMode, event = event)
                }
                .withCompletion { reduceCompletion(state = it, event = event) }
        }
        state.value.progress?.let { controller.sync(it) }
        if (event is ConversionEvent.RunCompleted) {
            finalizeCompletion()
        }
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

    /**
     * Stops the live animation and prints the final completion summary as
     * static text. Called once, when [ConversionEvent.RunCompleted] arrives.
     * Guarded by [completionPrinted] so accidental double-dispatch cannot
     * produce a duplicated summary.
     */
    private fun finalizeCompletion() {
        if (completionPrinted) return
        completionPrinted = true
        controller.stop()
        terminal.println(
            buildCompletionSummary(
                state = state.value.completion,
                stackTraceEnabled = stackTraceEnabled,
            ),
        )
    }
}
