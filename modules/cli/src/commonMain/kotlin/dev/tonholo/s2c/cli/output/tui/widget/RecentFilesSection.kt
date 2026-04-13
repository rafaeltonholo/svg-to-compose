package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.table.verticalLayout
import com.github.ajalt.mordant.widgets.Text
import dev.tonholo.s2c.cli.output.tui.state.RecentFilesState
import dev.tonholo.s2c.output.FileResult

private const val SUCCESS_ICON = "v"
private const val FAILURE_ICON = "x"

internal fun recentFilesSection(state: RecentFilesState): Widget = verticalLayout {
    cell(Text(com.github.ajalt.mordant.rendering.TextStyles.bold("Recent")))
    for (entry in state.entries) {
        val isSuccess = entry.result is FileResult.Success
        val icon = if (isSuccess) TextColors.green(SUCCESS_ICON) else TextColors.red(FAILURE_ICON)
        val duration = if (isSuccess) {
            "${entry.duration.inWholeMilliseconds}ms"
        } else {
            TextColors.red("FAILED")
        }
        val line = " $icon ${entry.fileName}  $duration"
        cell(Text(line))
    }
}
