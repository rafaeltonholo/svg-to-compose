package dev.tonholo.s2c.website.validator.playground.template

import dev.tonholo.s2c.website.domain.model.playground.template.TemplateValidationError
import kotlin.test.Test
import kotlin.test.assertEquals

class TemplateTomlValidatorTest {
    @Test
    fun `given section header with trailing comment - when validate is called - then no error is reported`() {
        val toml = "[templates] # section comment"

        val errors = TemplateTomlValidator.validate(toml)

        assertEquals(expected = emptyList(), actual = errors)
    }

    @Test
    fun `given section header with comment and no space - when validate is called - then no error is reported`() {
        val toml = "[templates]# section comment"

        val errors = TemplateTomlValidator.validate(toml)

        assertEquals(expected = emptyList(), actual = errors)
    }

    @Test
    fun `given array section header with trailing comment - when validate is called - then no error is reported`() {
        val toml = "[[definitions.color_mapping]] # array comment"

        val errors = TemplateTomlValidator.validate(toml)

        assertEquals(expected = emptyList(), actual = errors)
    }

    @Test
    fun `given commented header followed by keys - when validate is called - then keys are validated in section`() {
        val toml = """
            [templates] # section comment
            file_header = ""
            unknown_key = ""
        """.trimIndent()

        val errors = TemplateTomlValidator.validate(toml)

        assertEquals(
            expected = listOf(
                TemplateValidationError(
                    line = 3,
                    message = "Key 'unknown_key' not valid in [templates]. Expected: file_header, icon_template.",
                ),
            ),
            actual = errors,
        )
    }

    @Test
    fun `given double quoted key with hash - when validate is called - then the hash is kept in the section name`() {
        val toml = """["templates # not a comment"] # real comment"""

        val errors = TemplateTomlValidator.validate(toml)

        assertEquals(
            expected = listOf(
                TemplateValidationError(
                    line = 1,
                    message = """Unknown section ["templates # not a comment"].""",
                ),
            ),
            actual = errors,
        )
    }

    @Test
    fun `given single quoted key with hash - when validate is called - then the hash is kept in the section name`() {
        val toml = "['templates # not a comment'] # real comment"

        val errors = TemplateTomlValidator.validate(toml)

        assertEquals(
            expected = listOf(
                TemplateValidationError(
                    line = 1,
                    message = "Unknown section ['templates # not a comment'].",
                ),
            ),
            actual = errors,
        )
    }

    @Test
    fun `given escaped quote before hash - when validate is called - then the hash is kept in the section name`() {
        val toml = """["templates\" # not a comment"] # real comment"""

        val errors = TemplateTomlValidator.validate(toml)

        assertEquals(
            expected = listOf(
                TemplateValidationError(
                    line = 1,
                    message = """Unknown section ["templates\" # not a comment"].""",
                ),
            ),
            actual = errors,
        )
    }

    @Test
    fun `given value containing a hash inside quotes - when validate is called - then no error is reported`() {
        val toml = """
            [fragments]
            color = "#FF0000"
        """.trimIndent()

        val errors = TemplateTomlValidator.validate(toml)

        assertEquals(expected = emptyList(), actual = errors)
    }

    @Test
    fun `given unknown section with trailing comment - when validate is called - then only the name is reported`() {
        val toml = "[unknown] # section comment"

        val errors = TemplateTomlValidator.validate(toml)

        assertEquals(
            expected = listOf(TemplateValidationError(line = 1, message = "Unknown section [unknown].")),
            actual = errors,
        )
    }

    @Test
    fun `given header without closing bracket - when validate is called - then the raw line is reported`() {
        val toml = "[templates # section comment"

        val errors = TemplateTomlValidator.validate(toml)

        assertEquals(
            expected = listOf(
                TemplateValidationError(line = 1, message = "Unknown section [[templates # section comment]."),
            ),
            actual = errors,
        )
    }

    @Test
    fun `given full line comment - when validate is called - then no error is reported`() {
        val toml = "# a standalone comment"

        val errors = TemplateTomlValidator.validate(toml)

        assertEquals(expected = emptyList(), actual = errors)
    }
}
