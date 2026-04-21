package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.TextAlign
import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyles
import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.table.horizontalLayout
import com.github.ajalt.mordant.table.verticalLayout
import com.github.ajalt.mordant.widgets.HorizontalRule
import com.github.ajalt.mordant.widgets.Text
import dev.tonholo.s2c.cli.output.tui.state.CurrentFileState
import dev.tonholo.s2c.cli.output.tui.state.SingleFileCompletion
import dev.tonholo.s2c.cli.output.tui.state.TuiState
import dev.tonholo.s2c.output.ConversionPhase

private const val RULE_CHARACTER = "-"
private const val PATH_LABEL_WIDTH = 7
private const val BLANK_LINE = " "
private const val FAILURE_PREFIX_PLAIN_WIDTH = "x Failed: ".length + " - ".length

/**
 * Builds the simplified layout used when converting a single file.
 *
 * The layout renders: version line, input/output paths, a horizontal
 * rule, the phase step row, another rule, and a terminal completion
 * line (blank until conversion finishes).
 */
internal fun singleFileLayout(state: TuiState, contentWidth: Int): Widget = verticalLayout {
    align = TextAlign.LEFT
    cell(versionLine(state = state))
    cell(pathLine(label = "input:", path = state.header.config?.inputPath ?: "", contentWidth = contentWidth))
    cell(pathLine(label = "output:", path = state.header.config?.outputPath ?: "", contentWidth = contentWidth))
    cell(HorizontalRule(ruleCharacter = RULE_CHARACTER))
    cell(singleFilePhaseRow(state = state))
    cell(HorizontalRule(ruleCharacter = RULE_CHARACTER))
    cell(Text(BLANK_LINE))
    cell(completionLine(completion = state.singleFileCompletion, contentWidth = contentWidth))
}

private fun versionLine(state: TuiState): Widget {
    val version = state.header.version
    val suffix = if (version.isNotEmpty()) " " + TextColors.cyan("v$version") else ""
    return Text(TextStyles.bold("svg-to-compose") + suffix)
}

private fun pathLine(
    label: String,
    path: String,
    contentWidth: Int,
): Widget {
    val paddedLabel = label.padEnd(PATH_LABEL_WIDTH)
    val budget = (contentWidth - PATH_LABEL_WIDTH - 1).coerceAtLeast(minimumValue = 1)
    val truncated = truncateWithEllipsis(text = path, maxWidth = budget)
    return Text("$paddedLabel $truncated")
}

private fun singleFilePhaseRow(state: TuiState): Widget {
    val fileState = state.currentFiles.values.firstOrNull()
    val optimizationEnabled = fileState?.optimizationEnabled
        ?: state.header.config?.optimizationEnabled
        ?: true
    val phases = if (optimizationEnabled) {
        ConversionPhase.entries
    } else {
        ConversionPhase.entries.filter { it != ConversionPhase.Optimizing }
    }
    val completion = state.singleFileCompletion
    return horizontalLayout {
        cell(Text(BLANK_LINE))
        for (phase in phases) {
            val icon = iconForPhase(phase = phase, fileState = fileState, completion = completion)
            cell(Text("$icon ${phase.name}  "))
        }
    }
}

private fun iconForPhase(
    phase: ConversionPhase,
    fileState: CurrentFileState?,
    completion: SingleFileCompletion?,
): String = when {
    fileState == null -> TuiIcons.pending
    phase in fileState.completedPhases -> TuiIcons.success
    completion is SingleFileCompletion.Failure && phase == fileState.currentPhase -> TuiIcons.failure
    phase == fileState.currentPhase -> TuiIcons.inProgress
    else -> TuiIcons.pending
}

private fun completionLine(completion: SingleFileCompletion?, contentWidth: Int): Widget =
    when (completion) {
        null -> Text(BLANK_LINE)

        is SingleFileCompletion.Success -> Text(
            "${TuiIcons.success} ${TextColors.green("Done")} (${completion.elapsedMs}ms)",
        )

        is SingleFileCompletion.Failure -> {
            val prefix = "${TuiIcons.failure} ${TextColors.red("Failed")}: ${completion.errorCode.name} - "
            val plainPrefixWidth = FAILURE_PREFIX_PLAIN_WIDTH + completion.errorCode.name.length
            val budget = (contentWidth - plainPrefixWidth).coerceAtLeast(minimumValue = 1)
            Text(prefix + truncateWithEllipsis(text = completion.message, maxWidth = budget))
        }
    }
