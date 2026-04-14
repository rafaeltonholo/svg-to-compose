package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.table.verticalLayout
import com.github.ajalt.mordant.widgets.Text
import com.github.ajalt.mordant.widgets.progress.MultiProgressBarWidgetMaker
import com.github.ajalt.mordant.widgets.progress.ProgressBarMakerRow
import com.github.ajalt.mordant.widgets.progress.ProgressBarWidgetMaker
import com.github.ajalt.mordant.widgets.withPadding
import dev.tonholo.s2c.cli.output.tui.state.ProgressState
import dev.tonholo.s2c.cli.output.tui.state.TuiState

private const val HORIZONTAL_PADDING = 2
private const val VERTICAL_PADDING = 2

/**
 * Custom [ProgressBarWidgetMaker] that composes the TUI header
 * section above the Mordant progress bar.
 *
 * Mordant calls [build] on every animation frame, so the header
 * is re-read from the supplier each time the terminal redraws.
 */
internal class DashboardWidgetMaker(
    private val state: () -> TuiState,
    private val barRowCount: Int,
    private val terminalWidth: Int,
) : ProgressBarWidgetMaker {
    override fun build(rows: List<ProgressBarMakerRow<*>>): Widget {
        val currentState = state()
        val barRows = rows.take(barRowCount)
        val statsRows = rows.drop(barRowCount)
        val progressWidget = MultiProgressBarWidgetMaker.build(barRows)
        val statsWidget = MultiProgressBarWidgetMaker.build(statsRows)
        return verticalLayout {
            val contentWidth = terminalWidth - HORIZONTAL_PADDING * 2
            cell(headerSection(state = currentState, contentWidth = contentWidth))
            cell(progressWidget)
            cell(currentFilesSection(files = currentState.currentFiles))
            cell(
                recentFilesSection(state = currentState.recentFiles).withPadding { top = 1 },
            )
            val progress = currentState.progress
            if (progress != null) {
                cell(summaryCountersSection(state = progress))
            } else {
                cell(Text(" "))
            }
            cell(statsWidget.withPadding { top = 1 })
        }.withPadding {
            vertical = VERTICAL_PADDING
            horizontal = HORIZONTAL_PADDING
        }
    }
}
