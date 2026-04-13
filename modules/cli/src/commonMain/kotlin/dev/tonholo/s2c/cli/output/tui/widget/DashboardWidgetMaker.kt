package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.table.verticalLayout
import com.github.ajalt.mordant.widgets.progress.MultiProgressBarWidgetMaker
import com.github.ajalt.mordant.widgets.progress.ProgressBarMakerRow
import com.github.ajalt.mordant.widgets.progress.ProgressBarWidgetMaker
import com.github.ajalt.mordant.widgets.withPadding
import dev.tonholo.s2c.cli.output.tui.state.HeaderState

private const val HORIZONTAL_PADDING = 1

/**
 * Custom [ProgressBarWidgetMaker] that composes the TUI header
 * section above the Mordant progress bar.
 *
 * Mordant calls [build] on every animation frame, so the header
 * is re-read from the supplier each time the terminal redraws.
 */
internal class DashboardWidgetMaker(
    private val headerState: () -> HeaderState,
) : ProgressBarWidgetMaker {
    override fun build(rows: List<ProgressBarMakerRow<*>>): Widget {
        val progressWidget = MultiProgressBarWidgetMaker.build(rows)
        return verticalLayout {
            cell(headerSection(state = headerState()))
            cell(progressWidget)
        }.withPadding {
            left = HORIZONTAL_PADDING
            right = HORIZONTAL_PADDING
        }
    }
}
