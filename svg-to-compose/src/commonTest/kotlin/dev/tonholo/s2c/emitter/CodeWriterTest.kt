package dev.tonholo.s2c.emitter

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CodeWriterTest {
    @Test
    fun `writeLine with no indentation`() {
        val writer = CodeWriter()
        writer.writeLine("val x = 1")
        assertEquals("val x = 1\n", writer.toString())
    }

    @Test
    fun `writeLine with single indent level`() {
        val writer = CodeWriter()
        writer.indent()
        writer.writeLine("val x = 1")
        assertEquals("    val x = 1\n", writer.toString())
    }

    @Test
    fun `writeLine with nested indentation`() {
        val writer = CodeWriter()
        writer.writeLine("fun main() {")
        writer.indented {
            writeLine("val x = 1")
            writeLine("if (x > 0) {")
            indented {
                writeLine("println(x)")
            }
            writeLine("}")
        }
        writer.writeLine("}")

        val expected = """
            |fun main() {
            |    val x = 1
            |    if (x > 0) {
            |        println(x)
            |    }
            |}
            |
        """.trimMargin()
        assertEquals(expected, writer.toString())
    }

    @Test
    fun `empty writeLine produces blank line`() {
        val writer = CodeWriter()
        writer.writeLine("line1")
        writer.writeLine()
        writer.writeLine("line2")
        assertEquals("line1\n\nline2\n", writer.toString())
    }

    @Test
    fun `write does not append newline`() {
        val writer = CodeWriter()
        writer.write("val x")
        writer.writeRaw(" = 1")
        assertEquals("val x = 1\n", writer.toString())
    }

    @Test
    fun `writeRaw does not add indentation`() {
        val writer = CodeWriter()
        writer.indent()
        writer.writeRaw("raw text")
        assertEquals("raw text\n", writer.toString())
    }

    @Test
    fun `dedent below zero throws`() {
        val writer = CodeWriter()
        assertFailsWith<IllegalStateException> {
            writer.dedent()
        }
    }

    @Test
    fun `custom indent size`() {
        val writer = CodeWriter(FormatConfig(indentSize = 2))
        writer.indent()
        writer.writeLine("x")
        assertEquals("  x\n", writer.toString())
    }

    @Test
    fun `tab indent style`() {
        val writer = CodeWriter(FormatConfig(indentStyle = IndentStyle.TAB))
        writer.indent()
        writer.writeLine("x")
        assertEquals("\tx\n", writer.toString())
    }

    @Test
    fun `insertFinalNewline false does not add trailing newline`() {
        val writer = CodeWriter(FormatConfig(insertFinalNewline = false))
        writer.writeLine("x")
        assertEquals("x\n", writer.toString())
    }

    @Test
    fun `insertFinalNewline true adds trailing newline when missing`() {
        val writer = CodeWriter(FormatConfig(insertFinalNewline = true))
        writer.write("x")
        assertEquals("x\n", writer.toString())
    }

    @Test
    fun `reset clears buffer and indent`() {
        val writer = CodeWriter()
        writer.indent()
        writer.writeLine("something")
        writer.reset()
        writer.writeLine("fresh")
        assertEquals("fresh\n", writer.toString())
    }

    @Test
    fun `indented block restores indent level`() {
        val writer = CodeWriter()
        writer.writeLine("before")
        writer.indented {
            writeLine("inside")
        }
        writer.writeLine("after")
        assertEquals("before\n    inside\nafter\n", writer.toString())
    }

    @Test
    fun `multiple indent and dedent operations`() {
        val writer = CodeWriter(FormatConfig(indentSize = 2))
        writer.writeLine("level0")
        writer.indent()
        writer.writeLine("level1")
        writer.indent()
        writer.writeLine("level2")
        writer.dedent()
        writer.writeLine("level1again")
        writer.dedent()
        writer.writeLine("level0again")

        val expected = """
            |level0
            |  level1
            |    level2
            |  level1again
            |level0again
            |
        """.trimMargin()
        assertEquals(expected, writer.toString())
    }

    @Test
    fun `empty writer with insertFinalNewline returns empty string`() {
        val writer = CodeWriter(FormatConfig(insertFinalNewline = true))
        assertEquals("", writer.toString())
    }

    @Test
    fun `indented restores indent level when block throws`() {
        val writer = CodeWriter()
        writer.writeLine("before")
        assertFailsWith<RuntimeException> {
            writer.indented {
                writeLine("inside")
                throw RuntimeException("boom")
            }
        }
        writer.writeLine("after")
        assertEquals("before\n    inside\nafter\n", writer.toString())
    }
}
