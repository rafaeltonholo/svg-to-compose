package dev.tonholo.s2c.website.validator.playground.template

import dev.tonholo.s2c.website.domain.model.playground.template.TemplateValidationError

/**
 * Token-level TOML validator for the s2c template schema.
 *
 * Tokenizes each line within its section context and validates against the
 * known schema. Returns all errors at once. Uses simple string operations
 * instead of regex for clarity and performance.
 */
object TemplateTomlValidator {
    private val knownHeaders = setOf(
        "definitions",
        "definitions.receiver",
        "definitions.imports",
        "definitions.color_mapping",
        "templates",
        "templates.preview",
        "fragments",
    )

    private val fixedKeySections: Map<String, Set<String>> = mapOf(
        "definitions.receiver" to setOf("name", "package"),
        "definitions.color_mapping" to setOf("name", "import", "value"),
        "templates" to setOf("file_header", "icon_template"),
        "templates.preview" to setOf("template"),
    )

    private val freeKeySections = setOf(
        "definitions.imports",
        "fragments",
    )

    private val parentOnlySections = setOf(
        "definitions",
    )

    private const val TRIPLE_QUOTE = "\"\"\""
    private const val COMMENT_MARKER = '#'
    private const val BASIC_STRING_QUOTE = '"'
    private const val LITERAL_STRING_QUOTE = '\''
    private const val ESCAPE = '\\'

    fun validate(toml: String): List<TemplateValidationError> {
        val lines = toml.lines()
        val errors = mutableListOf<TemplateValidationError>()
        val state = ValidationState()

        for ((index, rawLine) in lines.withIndex()) {
            val lineNumber = index + 1
            val trimmed = rawLine.trim()
            processLine(trimmed, lineNumber, state, errors)
        }

        return errors
    }

    private class ValidationState {
        var currentSection: String? = null
        var inMultiLineString: Boolean = false
    }

    private fun processLine(
        trimmed: String,
        lineNumber: Int,
        state: ValidationState,
        errors: MutableList<TemplateValidationError>,
    ) {
        when {
            state.inMultiLineString -> handleMultilineClose(trimmed, lineNumber, state, errors)
            trimmed.isEmpty() || trimmed.startsWith("#") -> Unit
            trimmed.startsWith("[") -> validateSectionHeader(trimmed, lineNumber, state, errors)
            trimmed.indexOf('=') > 0 -> validateKeyValue(trimmed, lineNumber, state, errors)
            else -> addUnexpectedContentError(lineNumber, state.currentSection, errors)
        }
    }

    private fun handleMultilineClose(
        trimmed: String,
        lineNumber: Int,
        state: ValidationState,
        errors: MutableList<TemplateValidationError>,
    ) {
        if (!trimmed.contains(TRIPLE_QUOTE)) return
        state.inMultiLineString = false
        val afterClose = trimmed.substringAfter(TRIPLE_QUOTE).trim()
        if (afterClose.isNotEmpty() && !afterClose.startsWith("#")) {
            errors.add(
                TemplateValidationError(
                    lineNumber,
                    "Unexpected content after closing \"\"\".",
                ),
            )
        }
    }

    private fun validateSectionHeader(
        trimmed: String,
        lineNumber: Int,
        state: ValidationState,
        errors: MutableList<TemplateValidationError>,
    ) {
        val sectionName = parseSectionHeader(trimmed)
        if (sectionName != null && sectionName in knownHeaders) {
            state.currentSection = sectionName
        } else {
            val display = sectionName ?: trimmed
            errors.add(TemplateValidationError(lineNumber, "Unknown section [$display]."))
        }
    }

    private fun validateKeyValue(
        trimmed: String,
        lineNumber: Int,
        state: ValidationState,
        errors: MutableList<TemplateValidationError>,
    ) {
        val equalsIndex = trimmed.indexOf('=')
        val key = trimmed.substring(0, equalsIndex).trim()
        val value = trimmed.substring(equalsIndex + 1).trim()

        if (!isValidIdentifier(key)) {
            errors.add(TemplateValidationError(lineNumber, "Invalid key '$key'."))
            return
        }

        val currentSection = state.currentSection
        if (currentSection == null) {
            errors.add(TemplateValidationError(lineNumber, "Key '$key' outside any section."))
            return
        }
        if (!isValidKey(currentSection, key)) {
            errors.add(TemplateValidationError(lineNumber, invalidKeyMessage(key, currentSection)))
            return
        }

        if (value.startsWith(TRIPLE_QUOTE)) {
            val afterOpen = value.substring(TRIPLE_QUOTE.length)
            if (!afterOpen.contains(TRIPLE_QUOTE)) {
                state.inMultiLineString = true
            }
        }
    }

    private fun addUnexpectedContentError(
        lineNumber: Int,
        currentSection: String?,
        errors: MutableList<TemplateValidationError>,
    ) {
        if (currentSection == null) {
            errors.add(TemplateValidationError(lineNumber, "Unexpected content outside any section."))
        } else {
            errors.add(TemplateValidationError(lineNumber, "Invalid syntax in [$currentSection]."))
        }
    }

    /**
     * Parses a section header line like `[definitions.receiver]` or `[[definitions.color_mapping]]`
     * and returns the section name, or null if the format is invalid.
     */
    private fun parseSectionHeader(line: String): String? {
        val header = stripTrailingComment(line)
        val isArray = header.startsWith("[[")
        val openBrackets = if (isArray) 2 else 1
        val closeBrackets = if (isArray) "]]" else "]"

        if (!header.endsWith(closeBrackets)) return null

        val inner = header.substring(openBrackets, header.length - openBrackets).trim()
        return inner.ifEmpty { null }
    }

    /**
     * Removes a `#` comment from the end of [line], ignoring any `#` that sits
     * inside a basic (`"`) or literal (`'`) TOML string.
     */
    private fun stripTrailingComment(line: String): String {
        var index = 0
        var openQuote: Char? = null
        while (index < line.length) {
            val char = line[index]
            when {
                openQuote == BASIC_STRING_QUOTE && char == ESCAPE -> index++
                openQuote != null -> if (char == openQuote) openQuote = null
                char == BASIC_STRING_QUOTE || char == LITERAL_STRING_QUOTE -> openQuote = char
                char == COMMENT_MARKER -> return line.substring(0, index).trimEnd()
            }
            index++
        }
        return line
    }

    /**
     * Checks if a string is a valid TOML key identifier:
     * starts with letter or underscore, contains only letters, digits, underscores.
     */
    private fun isValidIdentifier(key: String): Boolean {
        if (key.isEmpty()) return false
        val first = key[0]
        if (first != '_' && !first.isLetter()) return false
        for (c in key) {
            if (c != '_' && c != '-' && !c.isLetterOrDigit()) return false
        }
        return true
    }

    private fun isValidKey(section: String, key: String): Boolean {
        if (section in parentOnlySections) return false
        if (section in freeKeySections) return true
        val allowedKeys = fixedKeySections[section] ?: return true
        return key in allowedKeys
    }

    private fun invalidKeyMessage(key: String, section: String): String {
        if (section in parentOnlySections) {
            return "Key '$key' not allowed directly in [$section]. Use a sub-section."
        }
        val allowedKeys = fixedKeySections[section]
        return if (allowedKeys != null) {
            "Key '$key' not valid in [$section]. Expected: ${allowedKeys.joinToString()}."
        } else {
            "Key '$key' not valid in [$section]."
        }
    }
}
