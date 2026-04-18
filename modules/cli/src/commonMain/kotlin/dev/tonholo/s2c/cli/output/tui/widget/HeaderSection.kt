package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyles
import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.table.verticalLayout
import com.github.ajalt.mordant.widgets.Panel
import com.github.ajalt.mordant.widgets.Text
import com.github.ajalt.mordant.widgets.withPadding
import dev.tonholo.s2c.cli.output.tui.state.HeaderState
import dev.tonholo.s2c.cli.output.tui.state.TuiState

internal fun headerSection(state: TuiState, contentWidth: Int): Widget {
    val versionLine = Text(
        TextStyles.bold("svg-to-compose") + " " + TextColors.cyan("v${state.header.version}"),
    )

    val headerState = state.header
    if (!headerState.expanded) {
        return collapsedHeader(state = headerState, versionLine = versionLine, contentWidth = contentWidth)
    }

    return expandedHeader(state = headerState, versionLine = versionLine)
}

private const val SUMMARY_INNER_PADDING = 1

private fun collapsedHeader(
    state: HeaderState,
    versionLine: Widget,
    contentWidth: Int,
): Widget {
    val config = state.config
    val summary = if (config != null) {
        val maxTextWidth = contentWidth - SUMMARY_INNER_PADDING * 2
        val summaryStr = buildString {
            append("input: ${config.inputPath}")
            append("  output: ${config.outputPath}")
            append("  files: ${state.totalFiles}")
            append("  optimize: ${if (config.optimizationEnabled) "on" else "off"}")
        }
        Text(truncateWithEllipsis(text = summaryStr, maxWidth = maxTextWidth)).withPadding {
            horizontal = SUMMARY_INNER_PADDING
        }
    } else {
        Text(" ")
    }
    val hint = if (config != null) {
        Text(TextColors.gray("Press [h] to expand header details"))
    } else {
        Text(" ")
    }

    return verticalLayout {
        cell(versionLine)
        cell(summary)
        cell(hint)
    }
}

private fun expandedHeader(state: HeaderState, versionLine: Widget): Widget {
    val config = state.config ?: return versionLine
    val hint = Text(TextColors.gray("Press [h] to collapse header details"))

    val configLines = buildString {
        appendLine("Input       ${config.inputPath}")
        appendLine("Output      ${config.outputPath}")
        appendLine("Package     ${config.packageName}")
        appendLine("Optimize    ${if (config.optimizationEnabled) "on" else "off"}")
        append("Recursive   ${if (config.recursive) "on" else "off"}")
        appendLine()
        append("Files       ${state.totalFiles} found")
    }

    val panel = Panel(
        content = Text(configLines),
        title = Text("Configuration"),
    ).withPadding {
        horizontal = 2
    }

    return verticalLayout {
        cell(versionLine)
        cell(hint)
        cell(panel)
    }
}
