package dev.tonholo.s2c.cli.output.tui.layout

import com.github.ajalt.mordant.rendering.TextAlign
import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyle
import com.github.ajalt.mordant.rendering.Whitespace
import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.widgets.Text
import com.github.ajalt.mordant.widgets.progress.ProgressBarDefinition
import com.github.ajalt.mordant.widgets.progress.ProgressLayoutScope
import com.github.ajalt.mordant.widgets.progress.calculateTimeRemaining
import com.github.ajalt.mordant.widgets.progress.completed
import com.github.ajalt.mordant.widgets.progress.isFinished
import com.github.ajalt.mordant.widgets.progress.isRunning
import com.github.ajalt.mordant.widgets.progress.percentage
import com.github.ajalt.mordant.widgets.progress.progressBar
import com.github.ajalt.mordant.widgets.progress.progressBarLayout
import com.github.ajalt.mordant.widgets.progress.speed
import com.github.ajalt.mordant.widgets.progress.text
import com.github.ajalt.mordant.widgets.progress.timeElapsed
import kotlin.math.min
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private const val MAX_PROGRESS_BAR_WIDTH = 80

// s2c brand colors from logo hexagon faces
private val S2C_GREEN = TextColors.rgb("#37bf6e")
private val S2C_AMBER = TextColors.rgb("#ffb13b")
private val LABEL = TextColors.gray

internal class ProgressBarLayouts(terminal: Terminal) {
    private val barLayout = progressBarLayout(alignColumns = false, spacing = 1) {
        text(LABEL("Progress:"))
        progressBar(
            width = min(MAX_PROGRESS_BAR_WIDTH, terminal.size.width),
            completeStyle = S2C_GREEN,
            indeterminateStyle = S2C_GREEN,
        )
        completed(style = S2C_AMBER)
        percentage(style = S2C_AMBER)
    }

    private val statsLayout = progressBarLayout(alignColumns = false, align = TextAlign.LEFT) {
        text(LABEL("Elapsed:"))
        timeElapsed()
        text(LABEL("ETA:"))
        customTimeRemaining()
        text(LABEL("Throughput:"))
        speed(" icons/s")
    }

    val all: List<ProgressBarDefinition<Unit>>
        get() = listOf(barLayout, statsLayout)

    private fun ProgressLayoutScope<*>.customTimeRemaining() = cell {
        val eta = if (isRunning) calculateTimeRemaining(false) else null
        val prefix = if (isFinished || !isRunning) "" else "~"
        val style = TextStyle()
        val maxEta = 35_999.seconds // 9:59:59
        val duration = eta?.coerceAtMost(maxEta)
        Text(style(prefix + renderDuration(duration)), whitespace = Whitespace.PRE)
    }

    private fun renderDuration(duration: Duration?): String {
        if (duration == null || duration < Duration.ZERO) {
            return "-:--:--"
        }
        return duration.toComponents { h, m, s, _ ->
            "$h:${m.toString().padStart(2, '0')}:${s.toString().padStart(2, '0')}"
        }
    }
}
