package dev.tonholo.s2c.website.domain.model.playground.template

/**
 * Validation error for a specific line.
 *
 * @property line 1-based line number.
 * @property message Human-readable description of the error.
 */
data class TemplateValidationError(val line: Int, val message: String)
