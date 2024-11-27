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

        val expected = sequenceOf(
            Token(type = CssTokenTypes.Dot, startOffset = 0, endOffset = 1),
            Token(type = CssTokenTypes.Identifier, startOffset = 1, endOffset = 8),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 8, endOffset = 9),
            Token(type = CssTokenTypes.OpenCurlyBrace, startOffset = 9, endOffset = 10),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 10, endOffset = 15),
            Token(type = CssTokenTypes.Identifier, startOffset = 15, endOffset = 25),
            Token(type = CssTokenTypes.Colon, startOffset = 25, endOffset = 26),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 26, endOffset = 27),
            Token(type = CssTokenTypes.Hash, startOffset = 27, endOffset = 28),
            Token(type = CssTokenTypes.HexDigit, startOffset = 28, endOffset = 31),
            Token(type = CssTokenTypes.Semicolon, startOffset = 31, endOffset = 32),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 32, endOffset = 37),
            Token(type = CssTokenTypes.Identifier, startOffset = 37, endOffset = 42),
            Token(type = CssTokenTypes.Colon, startOffset = 42, endOffset = 43),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 43, endOffset = 44),
            Token(type = CssTokenTypes.Hash, startOffset = 44, endOffset = 45),
            Token(type = CssTokenTypes.HexDigit, startOffset = 45, endOffset = 48),
            Token(type = CssTokenTypes.Semicolon, startOffset = 48, endOffset = 49),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 49, endOffset = 50),
            Token(type = CssTokenTypes.CloseCurlyBrace, startOffset = 50, endOffset = 51),
            Token(type = CssTokenTypes.EndOfFile, startOffset = 51, endOffset = 51),
        ).toList()

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

        val expected = sequenceOf(
            Token(type = CssTokenTypes.Hash, startOffset = 0, endOffset = 1),
            Token(type = CssTokenTypes.Identifier, startOffset = 1, endOffset = 8),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 8, endOffset = 9),
            Token(type = CssTokenTypes.OpenCurlyBrace, startOffset = 9, endOffset = 10),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 10, endOffset = 15),
            Token(type = CssTokenTypes.Identifier, startOffset = 15, endOffset = 25),
            Token(type = CssTokenTypes.Colon, startOffset = 25, endOffset = 26),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 26, endOffset = 27),
            Token(type = CssTokenTypes.Hash, startOffset = 27, endOffset = 28),
            Token(type = CssTokenTypes.HexDigit, startOffset = 28, endOffset = 31),
            Token(type = CssTokenTypes.Semicolon, startOffset = 31, endOffset = 32),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 32, endOffset = 37),
            Token(type = CssTokenTypes.Identifier, startOffset = 37, endOffset = 42),
            Token(type = CssTokenTypes.Colon, startOffset = 42, endOffset = 43),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 43, endOffset = 44),
            Token(type = CssTokenTypes.Hash, startOffset = 44, endOffset = 45),
            Token(type = CssTokenTypes.HexDigit, startOffset = 45, endOffset = 48),
            Token(type = CssTokenTypes.Semicolon, startOffset = 48, endOffset = 49),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 49, endOffset = 50),
            Token(type = CssTokenTypes.CloseCurlyBrace, startOffset = 50, endOffset = 51),
            Token(type = CssTokenTypes.EndOfFile, startOffset = 51, endOffset = 51),
        ).toList()

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

        val expected = sequenceOf(
            Token(type = CssTokenTypes.Dot, startOffset = 0, endOffset = 1),
            Token(type = CssTokenTypes.Identifier, startOffset = 1, endOffset = 8),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 8, endOffset = 9),
            Token(type = CssTokenTypes.OpenCurlyBrace, startOffset = 9, endOffset = 10),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 10, endOffset = 15),
            Token(type = CssTokenTypes.Identifier, startOffset = 15, endOffset = 25),
            Token(type = CssTokenTypes.Colon, startOffset = 25, endOffset = 26),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 26, endOffset = 27),
            Token(type = CssTokenTypes.Hash, startOffset = 27, endOffset = 28),
            Token(type = CssTokenTypes.HexDigit, startOffset = 28, endOffset = 31),
            Token(type = CssTokenTypes.Semicolon, startOffset = 31, endOffset = 32),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 32, endOffset = 37),
            Token(type = CssTokenTypes.Identifier, startOffset = 37, endOffset = 42),
            Token(type = CssTokenTypes.Colon, startOffset = 42, endOffset = 43),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 43, endOffset = 44),
            Token(type = CssTokenTypes.Hash, startOffset = 44, endOffset = 45),
            Token(type = CssTokenTypes.HexDigit, startOffset = 45, endOffset = 48),
            Token(type = CssTokenTypes.Semicolon, startOffset = 48, endOffset = 49),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 49, endOffset = 50),
            Token(type = CssTokenTypes.CloseCurlyBrace, startOffset = 50, endOffset = 51),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 51, endOffset = 53),

            Token(type = CssTokenTypes.Hash, startOffset = 53, endOffset = 54),
            Token(type = CssTokenTypes.Identifier, startOffset = 54, endOffset = 61),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 61, endOffset = 62),
            Token(type = CssTokenTypes.OpenCurlyBrace, startOffset = 62, endOffset = 63),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 63, endOffset = 68),
            Token(type = CssTokenTypes.Identifier, startOffset = 68, endOffset = 78),
            Token(type = CssTokenTypes.Colon, startOffset = 78, endOffset = 79),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 79, endOffset = 80),
            Token(type = CssTokenTypes.Hash, startOffset = 80, endOffset = 81),
            Token(type = CssTokenTypes.HexDigit, startOffset = 81, endOffset = 84),
            Token(type = CssTokenTypes.Semicolon, startOffset = 84, endOffset = 85),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 85, endOffset = 90),
            Token(type = CssTokenTypes.Identifier, startOffset = 90, endOffset = 103),
            Token(type = CssTokenTypes.Colon, startOffset = 103, endOffset = 104),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 104, endOffset = 105),
            Token(type = CssTokenTypes.Number, startOffset = 105, endOffset = 106),
            Token(type = CssTokenTypes.Identifier, startOffset = 106, endOffset = 108),
            Token(type = CssTokenTypes.Semicolon, startOffset = 108, endOffset = 109),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 109, endOffset = 110),
            Token(type = CssTokenTypes.CloseCurlyBrace, startOffset = 110, endOffset = 111),
            Token(type = CssTokenTypes.EndOfFile, startOffset = 111, endOffset = 111),
        ).toList()

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

        val expected = sequenceOf(
            Token(type = CssTokenTypes.Hash, startOffset = 0, endOffset = 1),
            Token(type = CssTokenTypes.Identifier, startOffset = 1, endOffset = 8),
            Token(type = CssTokenTypes.Comma, startOffset = 8, endOffset = 9),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 9, endOffset = 10),
            Token(type = CssTokenTypes.Dot, startOffset = 10, endOffset = 11),
            Token(type = CssTokenTypes.Identifier, startOffset = 11, endOffset = 19),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 19, endOffset = 20),
            Token(type = CssTokenTypes.OpenCurlyBrace, startOffset = 20, endOffset = 21),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 21, endOffset = 26),
            Token(type = CssTokenTypes.Identifier, startOffset = 26, endOffset = 35),
            Token(type = CssTokenTypes.Colon, startOffset = 35, endOffset = 36),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 36, endOffset = 37),
            Token(type = CssTokenTypes.StartUrl, startOffset = 37, endOffset = 41),
            Token(type = CssTokenTypes.Hash, startOffset = 41, endOffset = 42),
            Token(type = CssTokenTypes.Identifier, startOffset = 42, endOffset = 54),
            Token(type = CssTokenTypes.EndUrl, startOffset = 54, endOffset = 55),
            Token(type = CssTokenTypes.Semicolon, startOffset = 55, endOffset = 56),
            Token(type = CssTokenTypes.WhiteSpace, startOffset = 56, endOffset = 57),
            Token(type = CssTokenTypes.CloseCurlyBrace, startOffset = 57, endOffset = 58),
            Token(type = CssTokenTypes.EndOfFile, startOffset = 58, endOffset = 58),
        ).toList()

        val lexer = CssLexer()
        val actual = lexer.tokenize(input).toList()
        assertEquals(expected, actual)
    }
}
