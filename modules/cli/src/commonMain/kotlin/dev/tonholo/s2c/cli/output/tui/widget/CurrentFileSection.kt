package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.TextStyles
import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.table.horizontalLayout
import com.github.ajalt.mordant.table.verticalLayout
import com.github.ajalt.mordant.widgets.Spinner
import com.github.ajalt.mordant.widgets.Text
import dev.tonholo.s2c.cli.output.tui.state.CurrentFileState
import dev.tonholo.s2c.output.ConversionPhase

private const val MAX_VISIBLE_FILES = 5

/**
 * The reserved column size for the file name.
 *
 * A single row renders `  <spinner> <filename>  ` followed by one cell per phase (`<icon> <phase-name>  `).
 * The longest phase name is "Optimizing" (10 chars) + icon (1) + separator (3) = ~14 cells per phase.
 * With 4 phases that reserve ~56 cells for status. Everything else is the file name.
 */
private const val FILENAME_RESERVED_COLUMNS = 56

/**
 * The minimum legible file name width before truncation collapses to an ellipsis.
 *
 * Floor for the file name column. Below this, truncation produces unreadable output;
 * let the row overflow gracefully instead.
 */
private const val MIN_FILENAME_WIDTH = 8

internal fun currentFilesSection(files: Map<String, CurrentFileState>, contentWidth: Int): Widget {
    val entries = files.values.toList()
    val visible = entries.take(MAX_VISIBLE_FILES)
    val overflow = entries.size - visible.size
    val fileNameBudget = (contentWidth - FILENAME_RESERVED_COLUMNS).coerceAtLeast(MIN_FILENAME_WIDTH)

    return verticalLayout {
        cell(Text(TextStyles.bold("Processing")))
        for (fileState in visible) {
            cell(currentFileRow(state = fileState, fileNameBudget = fileNameBudget))
        }
        if (overflow > 0) {
            cell(Text("  +$overflow more..."))
        }
        // Pad to stable height: MAX_VISIBLE_FILES file rows + 1 overflow indicator slot.
        // The bold "Processing" header is always rendered and is not part of totalSlots.
        val renderedLines = visible.size + (if (overflow > 0) 1 else 0)
        val totalSlots = MAX_VISIBLE_FILES + 1
        repeat(totalSlots - renderedLines) {
            cell(Text(" "))
        }
    }
}

private fun currentFileRow(state: CurrentFileState, fileNameBudget: Int): Widget {
    val phases = if (state.optimizationEnabled) {
        ConversionPhase.entries
    } else {
        ConversionPhase.entries.filter { it != ConversionPhase.Optimizing }
    }
    val truncatedName = truncateWithEllipsis(text = state.fileName, maxWidth = fileNameBudget)

    return horizontalLayout {
        cell(Text(" "))
        cell(Spinner.Dots())
        cell(Text(" $truncatedName  "))
        for (phase in phases) {
            when {
                phase in state.completedPhases -> cell(Text("${TuiIcons.success} ${phase.name}  "))
                phase == state.currentPhase -> cell(Text("${TuiIcons.inProgress} ${phase.name}  "))
                else -> cell(Text("${TuiIcons.pending} ${phase.name}  "))
            }
        }
    }
}
