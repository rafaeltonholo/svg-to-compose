package dev.tonholo.s2c.emitter

import kotlin.test.Test
import kotlin.test.assertFailsWith

class FormatConfigTest {
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
