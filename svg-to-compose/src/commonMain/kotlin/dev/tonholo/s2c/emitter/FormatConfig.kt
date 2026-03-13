package dev.tonholo.s2c.emitter

/**
 * Style of indentation used in generated code.
 */
enum class IndentStyle {
    /** Indent using space characters. */
    SPACE,

    /** Indent using tab characters. */
    TAB,
}

/**
 * Configuration for code formatting in emitted output.
 *
 * @property indentSize The number of space characters per indent level.
 *   Only applies when [indentStyle] is [IndentStyle.SPACE]; ignored for [IndentStyle.TAB].
 * @property maxLineLength The maximum line length before wrapping.
 * @property indentStyle The character style used for indentation.
 * @property insertFinalNewline Whether to insert a trailing newline at end of file.
 */
data class FormatConfig(
    val indentSize: Int = 4,
    val maxLineLength: Int = 120,
    val indentStyle: IndentStyle = IndentStyle.SPACE,
    val insertFinalNewline: Boolean = true,
) {
    /**
     * Returns the string representation of a single indent level.
     */
    val indentUnit: String
        get() = when (indentStyle) {
            IndentStyle.SPACE -> " ".repeat(indentSize)
            IndentStyle.TAB -> "\t"
        }
}
