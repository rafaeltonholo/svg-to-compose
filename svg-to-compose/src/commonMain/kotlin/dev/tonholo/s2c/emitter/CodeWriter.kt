package dev.tonholo.s2c.emitter

/**
 * An indentation-aware string builder for generating formatted Kotlin code.
 *
 * [CodeWriter] tracks the current indentation level and applies it
 * consistently when writing lines. It uses the [FormatConfig] to determine
 * indent characters and final newline behavior.
 *
 * @property formatConfig The formatting configuration to use.
 */
class CodeWriter(
    private val formatConfig: FormatConfig = FormatConfig(),
) {
    private val buffer = StringBuilder()
    private var currentIndentLevel: Int = 0

    /**
     * The current indentation string based on the indent level and config.
     */
    private val currentIndent: String
        get() = formatConfig.indentUnit.repeat(currentIndentLevel)

    /**
     * Increases the indentation level by one and executes [block],
     * then decreases it back.
     *
     * @param block The code to execute at the increased indent level.
     * @return This [CodeWriter] for chaining.
     */
    inline fun indented(block: CodeWriter.() -> Unit): CodeWriter {
        indent()
        try {
            block()
        } finally {
            dedent()
        }
        return this
    }

    /**
     * Increases the current indentation level by one.
     */
    fun indent() {
        currentIndentLevel++
    }

    /**
     * Decreases the current indentation level by one.
     *
     * @throws IllegalStateException if the indent level is already zero.
     */
    fun dedent() {
        check(currentIndentLevel > 0) { "Cannot dedent below zero." }
        currentIndentLevel--
    }

    /**
     * Writes a line with the current indentation followed by a newline.
     *
     * @param text The text to write.
     * @return This [CodeWriter] for chaining.
     */
    fun writeLine(text: String = ""): CodeWriter {
        if (text.isEmpty()) {
            buffer.appendLine()
        } else {
            buffer.append(currentIndent)
            buffer.appendLine(text)
        }
        return this
    }

    /**
     * Writes text at the current indentation without appending a newline.
     *
     * @param text The text to write.
     * @return This [CodeWriter] for chaining.
     */
    fun write(text: String): CodeWriter {
        buffer.append(currentIndent)
        buffer.append(text)
        return this
    }

    /**
     * Appends raw text without any indentation.
     *
     * @param text The text to append.
     * @return This [CodeWriter] for chaining.
     */
    fun writeRaw(text: String): CodeWriter {
        buffer.append(text)
        return this
    }

    /**
     * Returns the generated code as a string, optionally appending
     * a final newline per [FormatConfig.insertFinalNewline].
     */
    override fun toString(): String {
        val result = buffer.toString()
        return if (formatConfig.insertFinalNewline && result.isNotEmpty() && !result.endsWith("\n")) {
            "$result\n"
        } else {
            result
        }
    }

    /**
     * Resets the writer, clearing all buffered content and
     * resetting the indent level to zero.
     */
    fun reset() {
        buffer.clear()
        currentIndentLevel = 0
    }
}
