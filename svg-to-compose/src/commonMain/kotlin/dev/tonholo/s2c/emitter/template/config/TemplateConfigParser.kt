package dev.tonholo.s2c.emitter.template.config

import com.akuleshov7.ktoml.Toml
import com.akuleshov7.ktoml.TomlInputConfig
import dev.tonholo.s2c.emitter.template.TemplateConstants.Namespace
import kotlinx.serialization.serializer

/**
 * Parses and validates `s2c.template.toml` content into a [TemplateEmitterConfig].
 */
object TemplateConfigParser {
    private val toml = Toml(
        inputConfig = TomlInputConfig(
            ignoreUnknownNames = true,
        ),
    )

    /**
     * Deserializes TOML content into a [TemplateEmitterConfig].
     *
     * Empty table sections (both `[...]` and `[[...]]`) that contain only
     * comments or blank lines are stripped before parsing so that placeholder
     * scaffolding does not cause ktoml deserialization errors.
     *
     * @param content The raw TOML string.
     * @return The parsed configuration.
     * @throws IllegalArgumentException if validation fails.
     * @throws kotlinx.serialization.SerializationException if the TOML is malformed.
     */
    fun parse(content: String): TemplateEmitterConfig {
        val sanitized = stripEmptySections(content)
        val config = toml.decodeFromString(serializer<TemplateEmitterConfig>(), sanitized)
        validate(config)
        return config
    }

    /**
     * Removes table headers (`[...]` and `[[...]]`) that have no key-value
     * pairs before the next header or end-of-file. This allows placeholder
     * scaffolding (e.g. empty `[fragments]` or `[[definitions.color_mapping]]`
     * followed only by comments) to be safely ignored, working around a ktoml
     * bug that crashes on empty map sections.
     */
    internal fun stripEmptySections(content: String): String {
        val lines = content.lines()
        val result = mutableListOf<String>()
        var i = 0
        while (i < lines.size) {
            if (isTableHeader(lines[i])) {
                i = processSectionBlock(lines, i, result)
            } else {
                result.add(lines[i])
                i++
            }
        }
        return result.joinToString("\n")
    }

    /**
     * Processes a TOML section starting at [start]. Collects the header and
     * any trailing comments/blanks, then either keeps or drops the block
     * depending on whether it contains key-value content.
     *
     * @return The next line index to process.
     */
    private fun processSectionBlock(lines: List<String>, start: Int, result: MutableList<String>): Int {
        val block = mutableListOf(lines[start])
        var j = start + 1
        while (j < lines.size && isCommentOrBlank(lines[j])) {
            block.add(lines[j])
            j++
        }
        val isEmpty = j >= lines.size || isTableHeader(lines[j])
        if (!isEmpty) {
            result.addAll(block)
        }
        return j
    }

    private fun isTableHeader(line: String): Boolean = TABLE_HEADER_PATTERN.matches(line.trim())

    private fun isCommentOrBlank(line: String): Boolean {
        val trimmed = line.trim()
        return trimmed.isEmpty() || trimmed.startsWith("#")
    }

    private val TABLE_HEADER_PATTERN = Regex("""^\[{1,2}[^\]]+\]{1,2}$""")

    private fun validate(config: TemplateEmitterConfig) {
        val definedImportKeys = config.definitions.imports.keys

        // The escape on `}` is required for JS platform.
        @Suppress("RegExpRedundantEscape")
        val defPattern = Regex("""\$\{${Namespace.DEFINITIONS}:([a-z][a-z0-9_.]*)\}""")

        val allTemplateTexts = buildList {
            config.templates.fileHeader?.let(::add)
            config.templates.iconTemplate?.let(::add)
            config.templates.preview?.template?.let(::add)
            config.fragments.values.forEach(::add)
        }

        for (text in allTemplateTexts) {
            for (match in defPattern.findAll(text)) {
                val key = match.groupValues[1]
                require(key in definedImportKeys) {
                    $$"Template references undefined import key '${def:$$key}'. " +
                        $$"Available keys: $${definedImportKeys.joinToString()}"
                }
            }
        }
    }
}
