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
        val isArray = line.startsWith("[[")
        val openBrackets = if (isArray) 2 else 1
        val closeBrackets = if (isArray) "]]" else "]"

        if (!line.endsWith(closeBrackets)) return null

        val inner = line.substring(openBrackets, line.length - openBrackets).trim()
        return inner.ifEmpty { null }
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
            if (c != '_' && !c.isLetterOrDigit()) return false
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
