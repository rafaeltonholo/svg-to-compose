package dev.tonholo.s2c.emitter.template.lexer

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TemplateLexerTest {
    @Test
    fun `empty input yields empty list`() {
        val tokens = TemplateLexer.tokenize("")
        assertTrue(tokens.isEmpty())
    }

    @Test
    fun `plain text yields single literal`() {
        val tokens = TemplateLexer.tokenize("hello world")
        assertEquals(listOf(TemplateToken.Literal("hello world")), tokens)
    }

    @Test
    fun `single placeholder`() {
        val tokens = TemplateLexer.tokenize($$"${icon:name}")
        assertEquals(listOf(TemplateToken.Placeholder("icon", "name")), tokens)
    }

    @Test
    fun `placeholder surrounded by text`() {
        val tokens = TemplateLexer.tokenize($$"val ${icon:name}: ImageVector")
        assertEquals(
            listOf(
                TemplateToken.Literal("val "),
                TemplateToken.Placeholder("icon", "name"),
                TemplateToken.Literal(": ImageVector"),
            ),
            tokens,
        )
    }

    @Test
    fun `multiple placeholders`() {
        val tokens = TemplateLexer.tokenize($$"${icon:name} = ${icon:width}")
        assertEquals(
            listOf(
                TemplateToken.Placeholder("icon", "name"),
                TemplateToken.Literal(" = "),
                TemplateToken.Placeholder("icon", "width"),
            ),
            tokens,
        )
    }

    @Test
    fun `adjacent placeholders`() {
        val tokens = TemplateLexer.tokenize($$"${icon:name}${icon:width}")
        assertEquals(
            listOf(
                TemplateToken.Placeholder("icon", "name"),
                TemplateToken.Placeholder("icon", "width"),
            ),
            tokens,
        )
    }

    @Test
    fun `all valid namespaces`() {
        for (ns in listOf("icon", "path", "group", "template", "def")) {
            val tokens = TemplateLexer.tokenize($$"${$$ns:key}")
            assertEquals(listOf(TemplateToken.Placeholder(ns, "key")), tokens)
        }
    }

    @Test
    fun `unknown namespace treated as literal`() {
        val tokens = TemplateLexer.tokenize($$"${foo:bar}")
        assertEquals(listOf(TemplateToken.Literal($$"${foo:bar}")), tokens)
    }

    @Test
    fun `incomplete placeholder no closing brace treated as literal`() {
        val tokens = TemplateLexer.tokenize($$"${icon:name")
        assertEquals(listOf(TemplateToken.Literal($$"${icon:name")), tokens)
    }

    @Test
    fun `invalid key starting with digit treated as literal`() {
        val tokens = TemplateLexer.tokenize($$"${icon:123}")
        assertEquals(listOf(TemplateToken.Literal($$"${icon:123}")), tokens)
    }

    @Test
    fun `empty key treated as literal`() {
        val tokens = TemplateLexer.tokenize($$"${icon:}")
        assertEquals(listOf(TemplateToken.Literal($$"${icon:}")), tokens)
    }

    @Test
    fun `key with underscores and dots`() {
        val tokens = TemplateLexer.tokenize($$"${path:fill_alpha}")
        assertEquals(listOf(TemplateToken.Placeholder("path", "fill_alpha")), tokens)

        val tokens2 = TemplateLexer.tokenize($$"${icon:some.nested.key}")
        assertEquals(listOf(TemplateToken.Placeholder("icon", "some.nested.key")), tokens2)
    }

    @Test
    fun `dollar sign without brace is literal`() {
        val tokens = TemplateLexer.tokenize("price is $10")
        assertEquals(listOf(TemplateToken.Literal("price is $10")), tokens)
    }

    @Test
    fun `placeholder at start and end`() {
        val tokens = TemplateLexer.tokenize($$"${icon:name} text ${def:builder}")
        assertEquals(
            listOf(
                TemplateToken.Placeholder("icon", "name"),
                TemplateToken.Literal(" text "),
                TemplateToken.Placeholder("def", "builder"),
            ),
            tokens,
        )
    }
}
