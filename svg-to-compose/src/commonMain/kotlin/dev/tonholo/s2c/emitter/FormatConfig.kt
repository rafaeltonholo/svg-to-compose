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
 * @property indentSize The number of indent characters per level.
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
    init {
        require(indentSize >= 0) { "indentSize must be >= 0, was $indentSize" }
        require(maxLineLength > 0) { "maxLineLength must be > 0, was $maxLineLength" }
    }

    /**
     * Returns the string representation of a single indent level.
     */
    val indentUnit: String
        get() = when (indentStyle) {
            IndentStyle.SPACE -> " ".repeat(indentSize)
            IndentStyle.TAB -> "\t"
        }

    /**
     * Partial formatting overrides with nullable fields.
     *
     * Non-null values take precedence when merged onto a resolved [FormatConfig]
     * via [applyTo].
     */
    data class Overrides(
        val indentSize: Int? = null,
        val indentStyle: IndentStyle? = null,
        val maxLineLength: Int? = null,
        val insertFinalNewline: Boolean? = null,
    ) {
        /**
         * Applies these overrides onto a base [FormatConfig], returning
         * a new config where non-null override values replace the base values.
         */
        fun applyTo(base: FormatConfig): FormatConfig = FormatConfig(
            indentSize = indentSize ?: base.indentSize,
            maxLineLength = maxLineLength ?: base.maxLineLength,
            indentStyle = indentStyle ?: base.indentStyle,
            insertFinalNewline = insertFinalNewline ?: base.insertFinalNewline,
        )
    }
}
