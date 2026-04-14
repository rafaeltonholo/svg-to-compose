package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyles
import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.table.verticalLayout
import com.github.ajalt.mordant.widgets.Text
import dev.tonholo.s2c.cli.output.tui.state.RecentFilesState
import dev.tonholo.s2c.output.FileResult

internal fun recentFilesSection(state: RecentFilesState): Widget = verticalLayout {
    cell(Text(TextStyles.bold("Recent")))
    for (entry in state.entries) {
        val isSuccess = entry.result is FileResult.Success
        val icon = if (isSuccess) TuiIcons.success else TuiIcons.failure
        val duration = if (isSuccess) {
            "${entry.duration.inWholeMilliseconds}ms"
        } else {
            TextColors.red("FAILED")
        }
        val line = " $icon ${entry.fileName}  $duration"
        cell(Text(line))
    }
}
