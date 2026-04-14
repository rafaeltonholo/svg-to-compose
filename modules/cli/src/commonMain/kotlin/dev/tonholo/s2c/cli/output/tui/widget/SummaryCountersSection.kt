package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.widgets.Text
import dev.tonholo.s2c.cli.output.tui.state.ProgressState

internal fun summaryCountersSection(state: ProgressState): Widget {
    val inProgress = state.total - state.completed - state.failed - state.pending
    val line = buildString {
        append(" ${TuiIcons.success} ${state.completed} succeeded")
        append("    ${TuiIcons.failure} ${state.failed} failed")
        append("    ${TuiIcons.inProgress} $inProgress in progress")
        append("    ${TuiIcons.pending} ${state.pending} pending")
    }
    return Text(line)
}
