package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.TextColors

/**
 * Shared styled icons used across TUI widget sections.
 *
 * [inProgress] uses a filled circle (U+25CF) and [pending] uses a hollow
 * circle (U+25CB) so the two remain visually distinguishable in no-color
 * terminals (e.g. piped output or NO_COLOR=1), where the colour modifier
 * has no effect.
 */
internal object TuiIcons {
    val success = TextColors.green("\u2714")
    val failure = TextColors.red("\u2718")
    val inProgress = TextColors.yellow("\u25CF")
    val pending = TextColors.gray("\u25CB")
}
