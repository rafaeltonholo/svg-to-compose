package dev.tonholo.s2c.emitter.editorconfig

import dev.tonholo.s2c.emitter.FormatConfig
import dev.tonholo.s2c.emitter.IndentStyle
import dev.tonholo.s2c.emitter.editorconfig.EditorConfigParser.toFormatConfig
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class EditorConfigParserTest {
    @Test
    fun `parse empty content returns defaults`() {
        val result = EditorConfigParser.parse("")
        assertFalse(result.isRoot)
        assertNull(result.indentSize)
        assertNull(result.indentStyle)
        assertNull(result.maxLineLength)
        assertNull(result.insertFinalNewline)
    }

    @Test
    fun `parse root = true`() {
        val content = "root = true"
        val result = EditorConfigParser.parse(content)
        assertTrue(result.isRoot)
    }

    @Test
    fun `parse ignores preamble properties other than root`() {
        val content = """
            root = true
            indent_size = 2
            indent_style = tab
        """.trimIndent()
        val result = EditorConfigParser.parse(content)
        assertTrue(result.isRoot)
        assertNull(result.indentSize)
        assertNull(result.indentStyle)
    }

    @Test
    fun `parse global section applies to kotlin files`() {
        val content = """
            [*]
            indent_size = 2
            indent_style = tab
        """.trimIndent()
        val result = EditorConfigParser.parse(content)
        assertEquals(2, result.indentSize)
        assertEquals(IndentStyle.TAB, result.indentStyle)
    }

    @Test
    fun `parse kotlin-specific section`() {
        val content = """
            [*.kt]
            indent_size = 4
            indent_style = space
            max_line_length = 100
            insert_final_newline = true
        """.trimIndent()
        val result = EditorConfigParser.parse(content)
        assertEquals(4, result.indentSize)
        assertEquals(IndentStyle.SPACE, result.indentStyle)
        assertEquals(100, result.maxLineLength)
        assertTrue(result.insertFinalNewline!!)
    }

    @Test
    fun `parse brace expansion matching kt`() {
        val content = """
            [*.{kt,kts}]
            indent_size = 3
        """.trimIndent()
        val result = EditorConfigParser.parse(content)
        assertEquals(3, result.indentSize)
    }

    @Test
    fun `parse ignores non-matching sections`() {
        val content = """
            [*.java]
            indent_size = 8

            [*.kt]
            indent_size = 4
        """.trimIndent()
        val result = EditorConfigParser.parse(content)
        assertEquals(4, result.indentSize)
    }

    @Test
    fun `parse kotlin section overrides global`() {
        val content = """
            [*]
            indent_size = 2
            indent_style = tab

            [*.kt]
            indent_size = 4
        """.trimIndent()
        val result = EditorConfigParser.parse(content)
        assertEquals(4, result.indentSize)
        assertEquals(IndentStyle.TAB, result.indentStyle)
    }

    @Test
    fun `parse ignores comments`() {
        val content = """
            # This is a comment
            ; This is also a comment
            [*.kt]
            indent_size = 4
        """.trimIndent()
        val result = EditorConfigParser.parse(content)
        assertEquals(4, result.indentSize)
    }

    @Test
    fun `parse max_line_length off`() {
        val content = """
            [*.kt]
            max_line_length = off
        """.trimIndent()
        val result = EditorConfigParser.parse(content)
        assertEquals(Int.MAX_VALUE, result.maxLineLength)
    }

    @Test
    fun `parse insert_final_newline false`() {
        val content = """
            [*.kt]
            insert_final_newline = false
        """.trimIndent()
        val result = EditorConfigParser.parse(content)
        assertFalse(result.insertFinalNewline!!)
    }

    @Test
    fun `parse handles whitespace around equals`() {
        val content = """
            [*.kt]
            indent_size  =  4
            indent_style =space
        """.trimIndent()
        val result = EditorConfigParser.parse(content)
        assertEquals(4, result.indentSize)
        assertEquals(IndentStyle.SPACE, result.indentStyle)
    }

    @Test
    fun `parse ignores invalid indent_size`() {
        val content = """
            [*.kt]
            indent_size = abc
        """.trimIndent()
        val result = EditorConfigParser.parse(content)
        assertNull(result.indentSize)
    }

    @Test
    fun `merge child overrides parent`() {
        val parent = EditorConfigParser.ParsedConfig(
            indentSize = 2,
            indentStyle = IndentStyle.TAB,
            maxLineLength = 120,
        )
        val child = EditorConfigParser.ParsedConfig(
            indentSize = 4,
        )
        val merged = EditorConfigParser.merge(parent, child)
        assertEquals(4, merged.indentSize)
        assertEquals(IndentStyle.TAB, merged.indentStyle)
        assertEquals(120, merged.maxLineLength)
    }

    @Test
    fun `merge parent fills gaps in child`() {
        val parent = EditorConfigParser.ParsedConfig(
            indentSize = 2,
            maxLineLength = 80,
        )
        val child = EditorConfigParser.ParsedConfig()
        val merged = EditorConfigParser.merge(parent, child)
        assertEquals(2, merged.indentSize)
        assertEquals(80, merged.maxLineLength)
    }

    @Test
    fun `toFormatConfig uses defaults for unset values`() {
        val parsed = EditorConfigParser.ParsedConfig(indentSize = 2)
        val defaults = FormatConfig(
            indentSize = 4,
            maxLineLength = 100,
            indentStyle = IndentStyle.TAB,
            insertFinalNewline = false,
        )
        val config = parsed.toFormatConfig(defaults)
        assertEquals(2, config.indentSize)
        assertEquals(100, config.maxLineLength)
        assertEquals(IndentStyle.TAB, config.indentStyle)
        assertFalse(config.insertFinalNewline)
    }

    @Test
    fun `toFormatConfig with all values set`() {
        val parsed = EditorConfigParser.ParsedConfig(
            indentSize = 3,
            indentStyle = IndentStyle.SPACE,
            maxLineLength = 140,
            insertFinalNewline = true,
        )
        val config = parsed.toFormatConfig()
        assertEquals(3, config.indentSize)
        assertEquals(IndentStyle.SPACE, config.indentStyle)
        assertEquals(140, config.maxLineLength)
        assertTrue(config.insertFinalNewline)
    }
}
