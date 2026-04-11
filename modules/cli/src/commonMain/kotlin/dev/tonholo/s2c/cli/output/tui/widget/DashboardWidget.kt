package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.table.verticalLayout
import dev.tonholo.s2c.cli.output.tui.state.TuiState

internal fun dashboardWidget(state: TuiState, terminalWidth: Int): Widget = verticalLayout {
    cell(headerSection(state = state.header))
    cell(progressSection(state = state.progress, terminalWidth = terminalWidth))
}
