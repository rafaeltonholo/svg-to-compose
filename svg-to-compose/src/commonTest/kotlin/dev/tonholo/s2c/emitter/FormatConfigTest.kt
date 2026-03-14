package dev.tonholo.s2c.emitter

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class FormatConfigTest {
    @Test
    fun `Overrides applyTo replaces non-null fields`() {
        val base = FormatConfig(indentSize = 4, indentStyle = IndentStyle.SPACE)
        val overrides = FormatConfig.Overrides(indentSize = 2)
        val result = overrides.applyTo(base)
        assertEquals(2, result.indentSize)
        assertEquals(IndentStyle.SPACE, result.indentStyle)
    }

    @Test
    fun `Overrides applyTo preserves base when all fields null`() {
        val base = FormatConfig(indentSize = 4, indentStyle = IndentStyle.TAB, maxLineLength = 80)
        val overrides = FormatConfig.Overrides()
        val result = overrides.applyTo(base)
        assertEquals(base, result)
    }

    @Test
    fun `negative indentSize throws`() {
        assertFailsWith<IllegalArgumentException> {
            FormatConfig(indentSize = -1)
        }
    }

    @Test
    fun `zero maxLineLength throws`() {
        assertFailsWith<IllegalArgumentException> {
            FormatConfig(maxLineLength = 0)
        }
    }

    @Test
    fun `negative maxLineLength throws`() {
        assertFailsWith<IllegalArgumentException> {
            FormatConfig(maxLineLength = -1)
        }
    }

    @Test
    fun `zero indentSize is valid`() {
        FormatConfig(indentSize = 0)
    }

    @Test
    fun `maxLineLength of one is valid`() {
        FormatConfig(maxLineLength = 1)
    }
}
