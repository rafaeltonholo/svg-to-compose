package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.table.verticalLayout
import com.github.ajalt.mordant.widgets.Text
import com.github.ajalt.mordant.widgets.progress.MultiProgressBarWidgetMaker
import com.github.ajalt.mordant.widgets.progress.ProgressBarMakerRow
import com.github.ajalt.mordant.widgets.progress.ProgressBarWidgetMaker
import com.github.ajalt.mordant.widgets.withPadding
import dev.tonholo.s2c.cli.output.tui.state.TuiMode
import dev.tonholo.s2c.cli.output.tui.state.TuiState

private const val HORIZONTAL_PADDING = 2
private const val VERTICAL_PADDING = 2

/**
 * Custom [ProgressBarWidgetMaker] that composes the TUI header
 * section above the Mordant progress bar.
 *
 * Mordant calls [build] on every animation frame, so the header
 * is re-read from the supplier each time the terminal redraws.
 * [terminalWidth] is a lambda so the layout adapts when the user
 * resizes the terminal mid-run.
 */
internal class DashboardWidgetMaker(
    private val state: () -> TuiState,
    private val barRowCount: Int,
    private val terminalWidth: () -> Int,
) : ProgressBarWidgetMaker {
    override fun build(rows: List<ProgressBarMakerRow<*>>): Widget {
        val currentState = state()
        val contentWidth = terminalWidth() - HORIZONTAL_PADDING * 2
        val body = when (currentState.mode) {
            TuiMode.Single -> singleFileLayout(state = currentState, contentWidth = contentWidth)

            TuiMode.Batch -> batchLayout(
                state = currentState,
                rows = rows,
                contentWidth = contentWidth,
            )
        }
        return body.withPadding {
            vertical = VERTICAL_PADDING
            horizontal = HORIZONTAL_PADDING
        }
    }

    private fun batchLayout(
        state: TuiState,
        rows: List<ProgressBarMakerRow<*>>,
        contentWidth: Int,
    ): Widget {
        val barRows = rows.take(barRowCount)
        val statsRows = rows.drop(barRowCount)
        val progressWidget = MultiProgressBarWidgetMaker.build(barRows)
        val statsWidget = MultiProgressBarWidgetMaker.build(statsRows)
        return verticalLayout {
            cell(headerSection(state = state, contentWidth = contentWidth))
            cell(progressWidget)
            cell(currentFilesSection(files = state.currentFiles, contentWidth = contentWidth))
            cell(
                recentFilesSection(
                    state = state.recentFiles,
                    contentWidth = contentWidth,
                ).withPadding { top = 1 },
            )
            val progress = state.progress
            if (progress != null) {
                cell(summaryCountersSection(state = progress))
            } else {
                cell(Text(" "))
            }
            cell(statsWidget.withPadding { top = 1 })
            state.updateNotification?.let { notification ->
                cell(updateNotificationSection(state = notification))
            }
        }
    }
}
