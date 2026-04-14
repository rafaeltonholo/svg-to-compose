package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.TextStyles
import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.table.horizontalLayout
import com.github.ajalt.mordant.table.verticalLayout
import com.github.ajalt.mordant.widgets.Spinner
import com.github.ajalt.mordant.widgets.Text
import dev.tonholo.s2c.cli.output.tui.state.CurrentFileState
import dev.tonholo.s2c.output.ConversionPhase

internal fun currentFileSection(state: CurrentFileState): Widget {
    val phases = if (state.optimizationEnabled) {
        ConversionPhase.entries
    } else {
        ConversionPhase.entries.filter { it != ConversionPhase.Optimizing }
    }

    val phaseRow = horizontalLayout {
        for (phase in phases) {
            when {
                phase in state.completedPhases -> cell(Text("${TuiIcons.success} ${phase.name}  "))

                phase == state.currentPhase -> {
                    cell(Spinner.Dots())
                    cell(Text(" ${phase.name}  "))
                }

                else -> cell(Text("${TuiIcons.pending} ${phase.name}  "))
            }
        }
    }

    return verticalLayout {
        cell(Text(TextStyles.bold("Current") + "  ${state.fileName}"))
        cell(
            horizontalLayout {
            cell(Text("         "))
            cell(phaseRow)
        }
        )
    }
}
