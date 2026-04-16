package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.widgets.Text
import dev.tonholo.s2c.cli.output.tui.state.ProgressState

internal fun summaryCountersSection(state: ProgressState): Widget {
    // Derived value: can transiently exceed `total` under parallel event delivery
    // (e.g. FileCompleted arrives before FileStarted for a sibling file). Clamp
    // to zero so the display never reads "-1 in progress".
    val inProgress = (state.total - state.completed - state.failed - state.pending).coerceAtLeast(minimumValue = 0L)
    val line = buildString {
        append(" ${TuiIcons.success} ${state.completed} succeeded")
        append("    ${TuiIcons.failure} ${state.failed} failed")
        append("    ${TuiIcons.inProgress} $inProgress in progress")
        append("    ${TuiIcons.pending} ${state.pending} pending")
    }
    return Text(line)
}
