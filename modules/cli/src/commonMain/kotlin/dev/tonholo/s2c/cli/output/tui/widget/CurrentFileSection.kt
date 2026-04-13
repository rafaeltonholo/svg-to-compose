package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyles
import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.table.verticalLayout
import com.github.ajalt.mordant.widgets.Text
import dev.tonholo.s2c.cli.output.tui.state.CurrentFileState
import dev.tonholo.s2c.output.ConversionPhase

private const val CHECKMARK = "v"
private const val IN_PROGRESS = "*"
private const val PENDING = "o"

internal fun currentFileSection(state: CurrentFileState): Widget {
    val phases = if (state.optimizationEnabled) {
        ConversionPhase.entries
    } else {
        ConversionPhase.entries.filter { it != ConversionPhase.Optimizing }
    }

    val phaseIndicators = phases.joinToString(separator = "  ") { phase ->
        val indicator = when {
            phase in state.completedPhases -> TextColors.green(CHECKMARK)
            phase == state.currentPhase -> TextColors.yellow(IN_PROGRESS)
            else -> TextColors.gray(PENDING)
        }
        "$indicator ${phase.name}"
    }

    return verticalLayout {
        cell(Text(TextStyles.bold("Current") + "  ${state.fileName}"))
        cell(Text("         $phaseIndicators"))
    }
}
