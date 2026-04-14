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

internal fun currentFilesSection(files: LinkedHashMap<String, CurrentFileState>): Widget {
    val entries = files.values.toList()
    val visible = entries.take(MAX_VISIBLE_FILES)
    val overflow = entries.size - visible.size

    return verticalLayout {
        cell(Text(TextStyles.bold("Processing")))
        for (fileState in visible) {
            cell(currentFileRow(fileState))
        }
        if (overflow > 0) {
            cell(Text("  +$overflow more..."))
        }
        // Pad to stable height: header + MAX_VISIBLE_FILES + overflow line
        val renderedLines = visible.size + (if (overflow > 0) 1 else 0)
        val totalSlots = MAX_VISIBLE_FILES + 1
        repeat(totalSlots - renderedLines) {
            cell(Text(" "))
        }
    }
}

private fun currentFileRow(state: CurrentFileState): Widget {
    val phases = if (state.optimizationEnabled) {
        ConversionPhase.entries
    } else {
        ConversionPhase.entries.filter { it != ConversionPhase.Optimizing }
    }

    return horizontalLayout {
        cell(Text(" "))
        cell(Spinner.Dots())
        cell(Text(" ${state.fileName}  "))
        for (phase in phases) {
            when {
                phase in state.completedPhases -> cell(Text("${TuiIcons.success} ${phase.name}  "))
                phase == state.currentPhase -> cell(Text("${TuiIcons.inProgress} ${phase.name}  "))
                else -> cell(Text("${TuiIcons.pending} ${phase.name}  "))
            }
        }
    }
}
