package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.widgets.Text
import dev.tonholo.s2c.cli.output.tui.state.ProgressState

internal fun summaryCountersSection(state: ProgressState): Widget {
    val inProgress = state.total - state.completed - state.failed - state.pending
    val line = buildString {
        append(" ${TextColors.green("v")} ${state.completed} succeeded")
        append("    ${TextColors.red("x")} ${state.failed} failed")
        append("    ${TextColors.yellow("*")} $inProgress in progress")
        append("    ${TextColors.gray("o")} ${state.pending} pending")
    }
    return Text(line)
}
