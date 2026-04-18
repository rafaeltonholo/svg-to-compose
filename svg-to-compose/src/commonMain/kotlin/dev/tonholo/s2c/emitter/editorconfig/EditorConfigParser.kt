package dev.tonholo.s2c.emitter.editorconfig

import dev.tonholo.s2c.emitter.FormatConfig
import dev.tonholo.s2c.emitter.IndentStyle

/**
 * A lightweight `.editorconfig` INI parser that extracts formatting properties
 * relevant to Kotlin files.
 *
 * Supports the following properties within `[*.kt]` or `[*]` sections:
 * - `indent_size`
 * - `indent_style`
 * - `max_line_length`
 * - `insert_final_newline`
 *
 * Multiple `.editorconfig` files can be merged (child overrides parent) by
 * calling [parse] on each file's content and combining the results with
 * [merge].
 */
internal object EditorConfigParser {
    private const val COMMENT_HASH = '#'
    private const val COMMENT_SEMICOLON = ';'
    private const val SECTION_OPEN = '['
    private const val SECTION_CLOSE = ']'

    private const val PROP_INDENT_SIZE = "indent_size"
    private const val PROP_INDENT_STYLE = "indent_style"
    private const val PROP_MAX_LINE_LENGTH = "max_line_length"
    private const val PROP_INSERT_FINAL_NEWLINE = "insert_final_newline"

    /**
     * Parsed properties from a single `.editorconfig` file.
     *
     * All fields are nullable — `null` means the property was not specified.
     *
     * @property isRoot Whether this `.editorconfig` declares `root = true`.
     */
    data class ParsedConfig(
        val isRoot: Boolean = false,
        val indentSize: Int? = null,
        val indentStyle: IndentStyle? = null,
        val maxLineLength: Int? = null,
        val insertFinalNewline: Boolean? = null,
    )

    /**
     * Parses the text content of an `.editorconfig` file and returns properties
     * applicable to Kotlin (`*.kt`) files.
     *
     * Section matching follows EditorConfig conventions:
     * - `[*]` matches all files (global section)
     * - `[*.kt]` matches Kotlin files specifically
     * - `[*.{kt,kts}]` matches Kotlin files via brace expansion
     *
     * Properties from more-specific sections override global ones.
     */
    fun parse(content: String): ParsedConfig {
        var isRoot = false
        var currentSectionApplies = false
        var isGlobalSection = true // before any section header
        val props = mutableMapOf<String, String>()

        for (rawLine in content.lineSequence()) {
            val line = rawLine.trim()
            if (line.isEmpty() || line[0] == COMMENT_HASH || line[0] == COMMENT_SEMICOLON) {
                continue
            }

            if (line[0] == SECTION_OPEN) {
                isGlobalSection = false
                val sectionEnd = line.indexOf(SECTION_CLOSE)
                if (sectionEnd > 0) {
                    val pattern = line.substring(1, sectionEnd).trim()
                    currentSectionApplies = matchesKotlinFiles(pattern)
                }
                continue
            }

            val (key, value) = parseKeyValue(line) ?: continue

            when {
                isGlobalSection -> isRoot = isRoot || (key == "root" && value == "true")
                currentSectionApplies -> props[key] = value
            }
        }

        return ParsedConfig(
            isRoot = isRoot,
            indentSize = props[PROP_INDENT_SIZE]?.toIntOrNull(),
            indentStyle = parseIndentStyle(props[PROP_INDENT_STYLE]),
            maxLineLength = parseMaxLineLength(props[PROP_MAX_LINE_LENGTH]),
            insertFinalNewline = props[PROP_INSERT_FINAL_NEWLINE]?.toBooleanStrictOrNull(),
        )
    }

    private fun parseKeyValue(line: String): Pair<String, String>? {
        val eqIndex = line.indexOf('=')
        if (eqIndex < 0) return null
        val key = line.substring(0, eqIndex).trim().lowercase()
        val value = line.substring(eqIndex + 1).trim().lowercase()
        return key to value
    }

    private fun parseIndentStyle(value: String?): IndentStyle? = when (value) {
        "space" -> IndentStyle.SPACE
        "tab" -> IndentStyle.TAB
        else -> null
    }

    private fun parseMaxLineLength(value: String?): Int? = when (value) {
        null -> null
        "off" -> Int.MAX_VALUE
        else -> value.toIntOrNull()
    }

    /**
     * Merges a child config on top of a parent config.
     * Child values override parent values where non-null.
     */
    fun merge(parent: ParsedConfig, child: ParsedConfig): ParsedConfig = ParsedConfig(
        isRoot = child.isRoot || parent.isRoot,
        indentSize = child.indentSize ?: parent.indentSize,
        indentStyle = child.indentStyle ?: parent.indentStyle,
        maxLineLength = child.maxLineLength ?: parent.maxLineLength,
        insertFinalNewline = child.insertFinalNewline ?: parent.insertFinalNewline,
    )

    /**
     * Converts parsed properties to a [FormatConfig], using defaults for
     * any unspecified values.
     */
    fun ParsedConfig.toFormatConfig(defaults: FormatConfig = FormatConfig()): FormatConfig =
        FormatConfig(
            indentSize = indentSize ?: defaults.indentSize,
            maxLineLength = maxLineLength ?: defaults.maxLineLength,
            indentStyle = indentStyle ?: defaults.indentStyle,
            insertFinalNewline = insertFinalNewline ?: defaults.insertFinalNewline,
        )

    /**
     * Checks whether a section pattern applies to `*.kt` files.
     */
    private fun matchesKotlinFiles(pattern: String): Boolean = when {
        pattern == "*" -> true

        pattern == "*.kt" -> true

        pattern.startsWith("*.{") && pattern.endsWith("}") -> {
            val extensions = pattern.substring(3, pattern.length - 1).split(",").map { it.trim() }
            "kt" in extensions
        }

        else -> false
    }
}
