package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyles
import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.widgets.Panel
import com.github.ajalt.mordant.widgets.Text
import com.github.ajalt.mordant.widgets.withPadding
import dev.tonholo.s2c.cli.output.tui.state.UpdateNotificationState

private const val PANEL_HORIZONTAL_PADDING = 2
private const val UPGRADE_COMMAND = "s2c --upgrade"

/**
 * Builds a TUI widget that notifies the user about an available update.
 *
 * When [UpdateNotificationState.isWrapper] is `true`, the widget suggests
 * running `s2c --upgrade`. Otherwise, it shows a download link.
 */
internal fun updateNotificationSection(state: UpdateNotificationState): Widget {
    val content = buildString {
        appendLine(
            TextStyles.bold("Update available: ") +
                TextColors.red(state.currentVersion) +
                " -> " +
                TextColors.green(state.latestVersion),
        )
        if (state.isWrapper) {
            appendLine("Run '${TextStyles.bold(UPGRADE_COMMAND)}' to update")
        } else {
            appendLine("Download:")
        }
        append(TextColors.cyan(state.releaseUrl))
    }

    return Panel(
        content = Text(content),
        title = Text(TextColors.yellow("Update")),
    ).withPadding {
        horizontal = PANEL_HORIZONTAL_PADDING
    }
}
