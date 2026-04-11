package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyles
import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.table.verticalLayout
import com.github.ajalt.mordant.widgets.Panel
import com.github.ajalt.mordant.widgets.Text
import dev.tonholo.s2c.cli.output.tui.state.HeaderState

internal fun headerSection(state: HeaderState): Widget {
    val versionLine = Text(
        TextStyles.bold("svg-to-compose") + " " + TextColors.cyan("v${state.version}"),
    )

    if (!state.expanded) {
        return collapsedHeader(state = state, versionLine = versionLine)
    }

    return expandedHeader(state = state, versionLine = versionLine)
}

private fun collapsedHeader(state: HeaderState, versionLine: Widget): Widget {
    val config = state.config ?: return versionLine
    val summary = Text(
        buildString {
            append("  input: ${config.inputPath}")
            append("  output: ${config.outputPath}")
            append("  files: ${state.totalFiles}")
            append("  optimize: ${if (config.optimizationEnabled) "on" else "off"}")
        },
    )
    val hint = Text(TextColors.gray("Press [h] to expand header details"))

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
        appendLine("  Input       ${config.inputPath}")
        appendLine("  Output      ${config.outputPath}")
        appendLine("  Package     ${config.packageName}")
        appendLine("  Optimize    ${if (config.optimizationEnabled) "on" else "off"}")
        append("  Recursive   ${if (config.recursive) "on" else "off"}")
        appendLine()
        append("  Files       ${state.totalFiles} found")
    }

    val panel = Panel(
        content = Text(configLines),
        title = Text("Configuration"),
    )

    return verticalLayout {
        cell(versionLine)
        cell(hint)
        cell(panel)
    }
}
