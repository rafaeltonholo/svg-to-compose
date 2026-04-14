package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.TextColors

/**
 * Shared styled icons used across TUI widget sections.
 */
internal object TuiIcons {
    val success = TextColors.green("\u2714")
    val failure = TextColors.red("\u2718")
    val inProgress = TextColors.yellow("\u25CB")
    val pending = TextColors.gray("\u25CB")
}
