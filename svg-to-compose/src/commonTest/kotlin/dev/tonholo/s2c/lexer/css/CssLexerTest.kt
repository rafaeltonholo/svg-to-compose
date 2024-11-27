package dev.tonholo.s2c.lexer.css

import dev.tonholo.s2c.lexer.Token
import kotlin.test.Test
import kotlin.test.assertEquals

class CssLexerTest {
    @Test
    fun `create tokens for a class css rule`() {
        val input = """
            |.my-rule {
            |    background: #f0f;
            |    color: #000;
            |}
            """.trimMargin()

        val expected = listOf(
            Token(kind = CssTokenKind.Dot, startOffset = 0, endOffset = 1),
            Token(kind = CssTokenKind.Identifier, startOffset = 1, endOffset = 8),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 8, endOffset = 9),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 9, endOffset = 10),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 10, endOffset = 15),
            Token(kind = CssTokenKind.Identifier, startOffset = 15, endOffset = 25),
            Token(kind = CssTokenKind.Colon, startOffset = 25, endOffset = 26),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 26, endOffset = 27),
            Token(kind = CssTokenKind.Hash, startOffset = 27, endOffset = 28),
            Token(kind = CssTokenKind.HexDigit, startOffset = 28, endOffset = 31),
            Token(kind = CssTokenKind.Semicolon, startOffset = 31, endOffset = 32),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 32, endOffset = 37),
            Token(kind = CssTokenKind.Identifier, startOffset = 37, endOffset = 42),
            Token(kind = CssTokenKind.Colon, startOffset = 42, endOffset = 43),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 43, endOffset = 44),
            Token(kind = CssTokenKind.Hash, startOffset = 44, endOffset = 45),
            Token(kind = CssTokenKind.HexDigit, startOffset = 45, endOffset = 48),
            Token(kind = CssTokenKind.Semicolon, startOffset = 48, endOffset = 49),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 49, endOffset = 50),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 50, endOffset = 51),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 51, endOffset = 51),
        )

        val lexer = CssLexer()
        val actual = lexer.tokenize(input).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `create tokens for an id css rule`() {
        val input = """
            |#my-rule {
            |    background: #f0f;
            |    color: #000;
            |}
            """.trimMargin()

        val expected = listOf(
            Token(kind = CssTokenKind.Hash, startOffset = 0, endOffset = 1),
            Token(kind = CssTokenKind.Identifier, startOffset = 1, endOffset = 8),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 8, endOffset = 9),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 9, endOffset = 10),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 10, endOffset = 15),
            Token(kind = CssTokenKind.Identifier, startOffset = 15, endOffset = 25),
            Token(kind = CssTokenKind.Colon, startOffset = 25, endOffset = 26),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 26, endOffset = 27),
            Token(kind = CssTokenKind.Hash, startOffset = 27, endOffset = 28),
            Token(kind = CssTokenKind.HexDigit, startOffset = 28, endOffset = 31),
            Token(kind = CssTokenKind.Semicolon, startOffset = 31, endOffset = 32),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 32, endOffset = 37),
            Token(kind = CssTokenKind.Identifier, startOffset = 37, endOffset = 42),
            Token(kind = CssTokenKind.Colon, startOffset = 42, endOffset = 43),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 43, endOffset = 44),
            Token(kind = CssTokenKind.Hash, startOffset = 44, endOffset = 45),
            Token(kind = CssTokenKind.HexDigit, startOffset = 45, endOffset = 48),
            Token(kind = CssTokenKind.Semicolon, startOffset = 48, endOffset = 49),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 49, endOffset = 50),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 50, endOffset = 51),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 51, endOffset = 51),
        )

        val lexer = CssLexer()
        val actual = lexer.tokenize(input).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `create tokens for css style with multiple rules`() {
        val input = """
            |.my-rule {
            |    background: #f0f;
            |    color: #000;
            |}
            |
            |#my-rule {
            |    background: #0ff;
            |    margin-bottom: 1px;
            |}
            """.trimMargin()

        val expected = listOf(
            Token(kind = CssTokenKind.Dot, startOffset = 0, endOffset = 1),
            Token(kind = CssTokenKind.Identifier, startOffset = 1, endOffset = 8),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 8, endOffset = 9),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 9, endOffset = 10),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 10, endOffset = 15),
            Token(kind = CssTokenKind.Identifier, startOffset = 15, endOffset = 25),
            Token(kind = CssTokenKind.Colon, startOffset = 25, endOffset = 26),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 26, endOffset = 27),
            Token(kind = CssTokenKind.Hash, startOffset = 27, endOffset = 28),
            Token(kind = CssTokenKind.HexDigit, startOffset = 28, endOffset = 31),
            Token(kind = CssTokenKind.Semicolon, startOffset = 31, endOffset = 32),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 32, endOffset = 37),
            Token(kind = CssTokenKind.Identifier, startOffset = 37, endOffset = 42),
            Token(kind = CssTokenKind.Colon, startOffset = 42, endOffset = 43),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 43, endOffset = 44),
            Token(kind = CssTokenKind.Hash, startOffset = 44, endOffset = 45),
            Token(kind = CssTokenKind.HexDigit, startOffset = 45, endOffset = 48),
            Token(kind = CssTokenKind.Semicolon, startOffset = 48, endOffset = 49),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 49, endOffset = 50),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 50, endOffset = 51),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 51, endOffset = 53),

            Token(kind = CssTokenKind.Hash, startOffset = 53, endOffset = 54),
            Token(kind = CssTokenKind.Identifier, startOffset = 54, endOffset = 61),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 61, endOffset = 62),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 62, endOffset = 63),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 63, endOffset = 68),
            Token(kind = CssTokenKind.Identifier, startOffset = 68, endOffset = 78),
            Token(kind = CssTokenKind.Colon, startOffset = 78, endOffset = 79),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 79, endOffset = 80),
            Token(kind = CssTokenKind.Hash, startOffset = 80, endOffset = 81),
            Token(kind = CssTokenKind.HexDigit, startOffset = 81, endOffset = 84),
            Token(kind = CssTokenKind.Semicolon, startOffset = 84, endOffset = 85),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 85, endOffset = 90),
            Token(kind = CssTokenKind.Identifier, startOffset = 90, endOffset = 103),
            Token(kind = CssTokenKind.Colon, startOffset = 103, endOffset = 104),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 104, endOffset = 105),
            Token(kind = CssTokenKind.Number, startOffset = 105, endOffset = 106),
            Token(kind = CssTokenKind.Identifier, startOffset = 106, endOffset = 108),
            Token(kind = CssTokenKind.Semicolon, startOffset = 108, endOffset = 109),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 109, endOffset = 110),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 110, endOffset = 111),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 111, endOffset = 111),
        )

        val lexer = CssLexer()
        val actual = lexer.tokenize(input).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `create tokens for css with multiple selectors rule`() {
        val input = """
            |#my-rule, .my-class {
            |    clip-path: url(#my-clip-path);
            |}
            """.trimMargin()

        val expected = listOf(
            Token(kind = CssTokenKind.Hash, startOffset = 0, endOffset = 1),
            Token(kind = CssTokenKind.Identifier, startOffset = 1, endOffset = 8),
            Token(kind = CssTokenKind.Comma, startOffset = 8, endOffset = 9),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 9, endOffset = 10),
            Token(kind = CssTokenKind.Dot, startOffset = 10, endOffset = 11),
            Token(kind = CssTokenKind.Identifier, startOffset = 11, endOffset = 19),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 19, endOffset = 20),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 20, endOffset = 21),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 21, endOffset = 26),
            Token(kind = CssTokenKind.Identifier, startOffset = 26, endOffset = 35),
            Token(kind = CssTokenKind.Colon, startOffset = 35, endOffset = 36),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 36, endOffset = 37),
            Token(kind = CssTokenKind.StartUrl, startOffset = 37, endOffset = 41),
            Token(kind = CssTokenKind.Hash, startOffset = 41, endOffset = 42),
            Token(kind = CssTokenKind.Identifier, startOffset = 42, endOffset = 54),
            Token(kind = CssTokenKind.EndUrl, startOffset = 54, endOffset = 55),
            Token(kind = CssTokenKind.Semicolon, startOffset = 55, endOffset = 56),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 56, endOffset = 57),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 57, endOffset = 58),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 58, endOffset = 58),
        )

        val lexer = CssLexer()
        val actual = lexer.tokenize(input).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `create tokens for rule with empty url`() {
        val input = """
            |div {
            |    background-image: url();
            |}
            """.trimMargin()

        val expected = listOf(
            Token(kind = CssTokenKind.Identifier, startOffset = 0, endOffset = 3),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 3, endOffset = 4),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 4, endOffset = 5),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 5, endOffset = 10),
            Token(kind = CssTokenKind.Identifier, startOffset = 10, endOffset = 26),
            Token(kind = CssTokenKind.Colon, startOffset = 26, endOffset = 27),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 27, endOffset = 28),
            Token(kind = CssTokenKind.StartUrl, startOffset = 28, endOffset = 32),
            Token(kind = CssTokenKind.EndUrl, startOffset = 32, endOffset = 33),
            Token(kind = CssTokenKind.Semicolon, startOffset = 33, endOffset = 34),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 34, endOffset = 35),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 35, endOffset = 36),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 36, endOffset = 36),
        )

        val lexer = CssLexer()
        val actual = lexer.tokenize(input).toList()
        assertEquals(expected, actual)
    }
}
