package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyles
import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.table.verticalLayout
import com.github.ajalt.mordant.widgets.Text
import dev.tonholo.s2c.cli.output.tui.state.RecentFileEntry
import dev.tonholo.s2c.cli.output.tui.state.RecentFilesState
import dev.tonholo.s2c.output.FileResult

private const val DURATION_COLUMN_WIDTH = 8
private const val ROW_PREFIX_AND_GAP = 5
private const val MIN_NAME_WIDTH = 8

internal fun recentFilesSection(state: RecentFilesState, contentWidth: Int): Widget =
    verticalLayout {
        cell(Text(TextStyles.bold("Recent")))
        val nameBudget = (contentWidth - DURATION_COLUMN_WIDTH - ROW_PREFIX_AND_GAP)
            .coerceAtLeast(MIN_NAME_WIDTH)
        for (entry in state.entries) {
            cell(recentFileRow(entry = entry, nameBudget = nameBudget))
        }
        repeat((state.maxEntries - state.entries.size).coerceAtLeast(0)) {
            cell(Text(" "))
        }
    }

private fun recentFileRow(entry: RecentFileEntry, nameBudget: Int): Widget {
    val isSuccess = entry.result is FileResult.Success
    val icon = if (isSuccess) TuiIcons.success else TuiIcons.failure
    val name = truncateWithEllipsis(text = entry.fileName, maxWidth = nameBudget).padEnd(nameBudget)
    val duration = durationCell(entry = entry, isSuccess = isSuccess)
    return Text(" $icon $name  $duration")
}

private fun durationCell(entry: RecentFileEntry, isSuccess: Boolean): String {
    val raw = if (isSuccess) "${entry.duration.inWholeMilliseconds}ms" else "FAILED"
    val cell = raw.take(DURATION_COLUMN_WIDTH).padStart(DURATION_COLUMN_WIDTH)
    return if (isSuccess) cell else TextColors.red(cell)
}
