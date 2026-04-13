package dev.tonholo.s2c.cli.output.renderer

import com.github.ajalt.mordant.animation.progress.MultiProgressBarAnimation
import com.github.ajalt.mordant.animation.progress.advance
import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.widgets.progress.completed
import com.github.ajalt.mordant.widgets.progress.percentage
import com.github.ajalt.mordant.widgets.progress.progressBar
import com.github.ajalt.mordant.widgets.progress.progressBarLayout
import com.github.ajalt.mordant.widgets.progress.speed
import com.github.ajalt.mordant.widgets.progress.text
import com.github.ajalt.mordant.widgets.progress.timeElapsed
import com.github.ajalt.mordant.widgets.progress.timeRemaining
import dev.tonholo.s2c.cli.output.tui.reducer.reduceHeader
import dev.tonholo.s2c.cli.output.tui.state.HeaderState
import dev.tonholo.s2c.cli.output.tui.widget.DashboardWidgetMaker
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.OutputRenderer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

private const val REFRESH_INTERVAL_MS = 33L

// s2c brand colors from logo hexagon faces
private val S2C_GREEN = TextColors.rgb("#37bf6e")
private val S2C_AMBER = TextColors.rgb("#ffb13b")
private val LABEL = TextColors.gray

internal class TuiRenderer(private val terminal: Terminal) : OutputRenderer {
    private val headerState = MutableStateFlow(HeaderState())

    private val barLayout = progressBarLayout(alignColumns = false, spacing = 2) {
        text(LABEL("Progress:"))
        progressBar(completeStyle = S2C_GREEN)
        completed(style = S2C_AMBER)
        percentage(style = S2C_AMBER)
    }

    private val statsLayout = progressBarLayout(alignColumns = false, spacing = 2) {
        text(LABEL("Elapsed:"))
        timeElapsed(compact = true)
        text(LABEL("ETA:"))
        timeRemaining()
        text(LABEL("Speed:"))
        speed("icons/s")
    }

    private val animation = MultiProgressBarAnimation(
        terminal = terminal,
        maker = DashboardWidgetMaker(headerState = { headerState.value }),
    )

    private val barTask = animation.addTask(
        definition = barLayout,
        context = Unit,
        start = false,
    )

    private val statsTask = animation.addTask(
        definition = statsLayout,
        context = Unit,
        start = false,
    )

    override fun onEvent(event: ConversionEvent) {
        headerState.update { current -> reduceHeader(state = current, event = event) }
        when (event) {
            is ConversionEvent.RunStarted -> {
                val total = event.totalFiles.toLong()
                barTask.reset { this.total = total; completed = 0 }
                statsTask.reset { this.total = total; completed = 0 }
            }

            is ConversionEvent.FileCompleted -> {
                barTask.advance()
                statsTask.advance()
            }

            is ConversionEvent.FileStarted,
            is ConversionEvent.FileStepChanged,
            is ConversionEvent.RunCompleted,
            is ConversionEvent.UpdateAvailable,
            -> {}
        }
    }

    fun toggleHeader() {
        headerState.update { current -> current.copy(expanded = !current.expanded) }
    }

    suspend fun run() {
        while (true) {
            animation.refresh()
            delay(REFRESH_INTERVAL_MS)
        }
    }

    fun stop() {
        animation.refresh(refreshAll = true)
        animation.stop()
    }
}
