package dev.tonholo.s2c.cli.output.tui.widget

private const val ELLIPSIS = "\u2026"

/**
 * Truncates [text] to fit within [maxWidth] columns, appending an ellipsis
 * when truncation occurs. Returns an empty string when [maxWidth] is not
 * positive.
 */
internal fun truncateWithEllipsis(text: String, maxWidth: Int): String = when {
    maxWidth <= 0 -> ""
    text.length <= maxWidth -> text
    maxWidth == 1 -> ELLIPSIS
    else -> text.take(maxWidth - 1) + ELLIPSIS
}
