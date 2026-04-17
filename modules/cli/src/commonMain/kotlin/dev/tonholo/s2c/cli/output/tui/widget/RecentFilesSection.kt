package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyles
import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.table.verticalLayout
import com.github.ajalt.mordant.widgets.Text
import dev.tonholo.s2c.cli.output.tui.state.RecentFilesState
import dev.tonholo.s2c.output.FileResult

/**
 * Fixed-width column for the right-aligned duration / FAILED label so the recent-files log
 * lines up even when names have different lengths.
 */
private const val DURATION_COLUMN_WIDTH = 8

/**
 * Row prefix is `" <icon> "` (3 cells) plus the gap between name and duration (2 cells);
 * subtract both when sizing the file name.
 */
private const val RECENT_ROW_FIXED_OVERHEAD = 3 + 2

/** Minimum legible file name width before truncation collapses to an ellipsis. */
private const val MIN_RECENT_FILENAME_WIDTH = 8

internal fun recentFilesSection(state: RecentFilesState, contentWidth: Int): Widget = verticalLayout {
    cell(Text(TextStyles.bold("Recent")))
    val available = contentWidth - RECENT_ROW_FIXED_OVERHEAD - DURATION_COLUMN_WIDTH
    val nameBudget = available.coerceAtLeast(MIN_RECENT_FILENAME_WIDTH)
    for (entry in state.entries) {
        val isSuccess = entry.result is FileResult.Success
        val icon = if (isSuccess) TuiIcons.success else TuiIcons.failure
        val rawDuration = if (isSuccess) "${entry.duration.inWholeMilliseconds}ms" else "FAILED"
        val truncatedName = truncateWithEllipsis(text = entry.fileName, maxWidth = nameBudget)
        val padding = (nameBudget - truncatedName.length).coerceAtLeast(0)
        val paddedName = truncatedName + " ".repeat(padding)
        val durationCell = rawDuration.padStart(DURATION_COLUMN_WIDTH)
        val colouredDuration = if (isSuccess) durationCell else TextColors.red(durationCell)
        cell(Text(" $icon $paddedName  $colouredDuration"))
    }
    repeat(state.maxEntries - state.entries.size) {
        cell(Text(" "))
    }
}
