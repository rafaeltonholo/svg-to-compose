package dev.tonholo.s2c.cli.output.tui.animation

import com.github.ajalt.mordant.animation.progress.MultiProgressBarAnimation
import com.github.ajalt.mordant.terminal.Terminal
import dev.tonholo.s2c.cli.output.tui.layout.ProgressBarLayouts
import dev.tonholo.s2c.cli.output.tui.state.ProgressState
import dev.tonholo.s2c.cli.output.tui.state.TuiState
import dev.tonholo.s2c.cli.output.tui.widget.DashboardWidgetMaker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlin.time.Duration.Companion.milliseconds

private const val REFRESH_INTERVAL_MS = 33L

internal class AnimationController(
    terminal: Terminal,
    layouts: ProgressBarLayouts,
    private val state: StateFlow<TuiState>,
) {
    private val animation = MultiProgressBarAnimation(
        terminal = terminal,
        maker = DashboardWidgetMaker(state = { state.value }),
    )

    private val tasks = layouts.all.map { layout ->
        animation.addTask(
            definition = layout,
            context = Unit,
            start = false,
        )
    }

    private var started = false

    fun sync(progress: ProgressState) {
        for (task in tasks) {
            if (!started) {
                task.reset {
                    total = progress.total
                    completed = progress.completed
                }
            } else {
                task.update {
                    total = progress.total
                    completed = progress.completed
                }
            }
        }
        started = true
    }

    suspend fun run() {
        while (true) {
            animation.refresh()
            delay(REFRESH_INTERVAL_MS.milliseconds)
        }
    }

    fun stop() {
        animation.refresh(refreshAll = true)
        animation.stop()
    }
}
