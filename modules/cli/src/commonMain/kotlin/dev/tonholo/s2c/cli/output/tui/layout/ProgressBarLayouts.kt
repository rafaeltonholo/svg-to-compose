package dev.tonholo.s2c.cli.output.tui.layout

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.widgets.progress.ProgressLayoutDefinition
import com.github.ajalt.mordant.widgets.progress.completed
import com.github.ajalt.mordant.widgets.progress.percentage
import com.github.ajalt.mordant.widgets.progress.progressBar
import com.github.ajalt.mordant.widgets.progress.progressBarLayout
import com.github.ajalt.mordant.widgets.progress.speed
import com.github.ajalt.mordant.widgets.progress.text
import com.github.ajalt.mordant.widgets.progress.timeElapsed
import com.github.ajalt.mordant.widgets.progress.timeRemaining
import kotlin.math.min

// s2c brand colors from logo hexagon faces
private val S2C_GREEN = TextColors.rgb("#37bf6e")
private val S2C_AMBER = TextColors.rgb("#ffb13b")
private val LABEL = TextColors.gray

internal class ProgressBarLayouts(terminal: Terminal) {
    private val barLayout = progressBarLayout(alignColumns = false, spacing = 2) {
        text(LABEL("Progress:"))
        progressBar(
            width = min(80, terminal.size.width),
            completeStyle = S2C_GREEN,
            indeterminateStyle = S2C_GREEN,
        )
        completed(style = S2C_AMBER)
        percentage(style = S2C_AMBER)
    }

    private val statsLayout = progressBarLayout(alignColumns = false, spacing = 1) {
        text(LABEL("Elapsed:"))
        timeElapsed()
        text(LABEL("ETA:"))
        timeRemaining(prefix = "")
        text(LABEL("Speed:"))
        speed(" icons/s")
    }

    val all: List<ProgressLayoutDefinition<Unit>>
        get() = listOf(barLayout, statsLayout)
}
