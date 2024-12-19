package dev.tonholo.s2c.lexer.css

import app.cash.burst.Burst
import app.cash.burst.burstValues
import dev.tonholo.s2c.lexer.Token
import kotlin.test.Test
import kotlin.test.assertEquals

class CssTokenizerTest {
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
            Token(kind = CssTokenKind.Ident, startOffset = 1, endOffset = 8),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 8, endOffset = 9),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 9, endOffset = 10),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 10, endOffset = 15),
            Token(kind = CssTokenKind.Ident, startOffset = 15, endOffset = 25),
            Token(kind = CssTokenKind.Colon, startOffset = 25, endOffset = 26),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 26, endOffset = 27),
            Token(kind = CssTokenKind.Hash, startOffset = 27, endOffset = 28),
            Token(kind = CssTokenKind.HexDigit, startOffset = 28, endOffset = 31),
            Token(kind = CssTokenKind.Semicolon, startOffset = 31, endOffset = 32),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 32, endOffset = 37),
            Token(kind = CssTokenKind.Ident, startOffset = 37, endOffset = 42),
            Token(kind = CssTokenKind.Colon, startOffset = 42, endOffset = 43),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 43, endOffset = 44),
            Token(kind = CssTokenKind.Hash, startOffset = 44, endOffset = 45),
            Token(kind = CssTokenKind.HexDigit, startOffset = 45, endOffset = 48),
            Token(kind = CssTokenKind.Semicolon, startOffset = 48, endOffset = 49),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 49, endOffset = 50),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 50, endOffset = 51),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 51, endOffset = 51),
        )

        assertTokens(input, expected)
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
            Token(kind = CssTokenKind.Ident, startOffset = 1, endOffset = 8),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 8, endOffset = 9),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 9, endOffset = 10),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 10, endOffset = 15),
            Token(kind = CssTokenKind.Ident, startOffset = 15, endOffset = 25),
            Token(kind = CssTokenKind.Colon, startOffset = 25, endOffset = 26),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 26, endOffset = 27),
            Token(kind = CssTokenKind.Hash, startOffset = 27, endOffset = 28),
            Token(kind = CssTokenKind.HexDigit, startOffset = 28, endOffset = 31),
            Token(kind = CssTokenKind.Semicolon, startOffset = 31, endOffset = 32),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 32, endOffset = 37),
            Token(kind = CssTokenKind.Ident, startOffset = 37, endOffset = 42),
            Token(kind = CssTokenKind.Colon, startOffset = 42, endOffset = 43),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 43, endOffset = 44),
            Token(kind = CssTokenKind.Hash, startOffset = 44, endOffset = 45),
            Token(kind = CssTokenKind.HexDigit, startOffset = 45, endOffset = 48),
            Token(kind = CssTokenKind.Semicolon, startOffset = 48, endOffset = 49),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 49, endOffset = 50),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 50, endOffset = 51),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 51, endOffset = 51),
        )

        assertTokens(input, expected)
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
            Token(kind = CssTokenKind.Ident, startOffset = 1, endOffset = 8),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 8, endOffset = 9),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 9, endOffset = 10),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 10, endOffset = 15),
            Token(kind = CssTokenKind.Ident, startOffset = 15, endOffset = 25),
            Token(kind = CssTokenKind.Colon, startOffset = 25, endOffset = 26),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 26, endOffset = 27),
            Token(kind = CssTokenKind.Hash, startOffset = 27, endOffset = 28),
            Token(kind = CssTokenKind.HexDigit, startOffset = 28, endOffset = 31),
            Token(kind = CssTokenKind.Semicolon, startOffset = 31, endOffset = 32),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 32, endOffset = 37),
            Token(kind = CssTokenKind.Ident, startOffset = 37, endOffset = 42),
            Token(kind = CssTokenKind.Colon, startOffset = 42, endOffset = 43),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 43, endOffset = 44),
            Token(kind = CssTokenKind.Hash, startOffset = 44, endOffset = 45),
            Token(kind = CssTokenKind.HexDigit, startOffset = 45, endOffset = 48),
            Token(kind = CssTokenKind.Semicolon, startOffset = 48, endOffset = 49),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 49, endOffset = 50),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 50, endOffset = 51),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 51, endOffset = 53),

            Token(kind = CssTokenKind.Hash, startOffset = 53, endOffset = 54),
            Token(kind = CssTokenKind.Ident, startOffset = 54, endOffset = 61),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 61, endOffset = 62),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 62, endOffset = 63),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 63, endOffset = 68),
            Token(kind = CssTokenKind.Ident, startOffset = 68, endOffset = 78),
            Token(kind = CssTokenKind.Colon, startOffset = 78, endOffset = 79),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 79, endOffset = 80),
            Token(kind = CssTokenKind.Hash, startOffset = 80, endOffset = 81),
            Token(kind = CssTokenKind.HexDigit, startOffset = 81, endOffset = 84),
            Token(kind = CssTokenKind.Semicolon, startOffset = 84, endOffset = 85),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 85, endOffset = 90),
            Token(kind = CssTokenKind.Ident, startOffset = 90, endOffset = 103),
            Token(kind = CssTokenKind.Colon, startOffset = 103, endOffset = 104),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 104, endOffset = 105),
            Token(
                kind = CssTokenKind.Dimension(
                    unit = "px",
                ),
                startOffset = 105,
                endOffset = 108,
            ),
            Token(kind = CssTokenKind.Semicolon, startOffset = 108, endOffset = 109),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 109, endOffset = 110),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 110, endOffset = 111),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 111, endOffset = 111),
        )
        assertTokens(input, expected)
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
            Token(kind = CssTokenKind.Ident, startOffset = 1, endOffset = 8),
            Token(kind = CssTokenKind.Comma, startOffset = 8, endOffset = 9),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 9, endOffset = 10),
            Token(kind = CssTokenKind.Dot, startOffset = 10, endOffset = 11),
            Token(kind = CssTokenKind.Ident, startOffset = 11, endOffset = 19),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 19, endOffset = 20),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 20, endOffset = 21),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 21, endOffset = 26),
            Token(kind = CssTokenKind.Ident, startOffset = 26, endOffset = 35),
            Token(kind = CssTokenKind.Colon, startOffset = 35, endOffset = 36),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 36, endOffset = 37),
            Token(kind = CssTokenKind.Url, startOffset = 37, endOffset = 55),
            Token(kind = CssTokenKind.Semicolon, startOffset = 55, endOffset = 56),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 56, endOffset = 57),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 57, endOffset = 58),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 58, endOffset = 58),
        )

        assertTokens(input, expected)
    }

    @Test
    fun `create tokens for rule with empty url`() {
        val input = """
            |div {
            |    background-image: url();
            |}
            """.trimMargin()

        val expected = listOf(
            Token(kind = CssTokenKind.Ident, startOffset = 0, endOffset = 3),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 3, endOffset = 4),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 4, endOffset = 5),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 5, endOffset = 10),
            Token(kind = CssTokenKind.Ident, startOffset = 10, endOffset = 26),
            Token(kind = CssTokenKind.Colon, startOffset = 26, endOffset = 27),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 27, endOffset = 28),
            Token(kind = CssTokenKind.Url, startOffset = 28, endOffset = 33),
            Token(kind = CssTokenKind.Semicolon, startOffset = 33, endOffset = 34),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 34, endOffset = 35),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 35, endOffset = 36),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 36, endOffset = 36),
        )

        assertTokens(input, expected)
    }

    @Test
    fun `create tokens for rule with element with multiple selectors`() {
        val content = """
            |dev.element-class#element-id {
            |   display: none;
            |}
            """.trimMargin()
        val tokens = listOf(
            Token(kind = CssTokenKind.Ident, startOffset = 0, endOffset = 3),
            Token(kind = CssTokenKind.Dot, startOffset = 3, endOffset = 4),
            Token(kind = CssTokenKind.Ident, startOffset = 4, endOffset = 17),
            Token(kind = CssTokenKind.Hash, startOffset = 17, endOffset = 18),
            Token(kind = CssTokenKind.Ident, startOffset = 18, endOffset = 28),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 28, endOffset = 29),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 29, endOffset = 30),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 30, endOffset = 34),
            Token(kind = CssTokenKind.Ident, startOffset = 34, endOffset = 41),
            Token(kind = CssTokenKind.Colon, startOffset = 41, endOffset = 42),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 42, endOffset = 43),
            Token(kind = CssTokenKind.Ident, startOffset = 43, endOffset = 47),
            Token(kind = CssTokenKind.Semicolon, startOffset = 47, endOffset = 48),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 48, endOffset = 49),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 49, endOffset = 50),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 50, endOffset = 50),
        )
        assertTokens(content, tokens)
    }

    @Test
    fun `create tokens for css without formatting`() {
        val content = """
            |div{display:none}
            """.trimMargin()

        val tokens = listOf(
            Token(kind = CssTokenKind.Ident, startOffset = 0, endOffset = 3),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 3, endOffset = 4),
            Token(kind = CssTokenKind.Ident, startOffset = 4, endOffset = 11),
            Token(kind = CssTokenKind.Colon, startOffset = 11, endOffset = 12),
            Token(kind = CssTokenKind.Ident, startOffset = 12, endOffset = 16),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 16, endOffset = 17),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 17, endOffset = 17),
        )
        assertTokens(content, tokens)
    }

    @Test
    fun `create tokens for aggregate selectors using spaces and multiple rules`() {
        val content = """
            |div .child a, a .child span {
            |    display: flex;
            |}
        """.trimMargin()
        val tokens = listOf(
            Token(kind = CssTokenKind.Ident, startOffset = 0, endOffset = 3),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 3, endOffset = 4),
            Token(kind = CssTokenKind.Dot, startOffset = 4, endOffset = 5),
            Token(kind = CssTokenKind.Ident, startOffset = 5, endOffset = 10),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 10, endOffset = 11),
            Token(kind = CssTokenKind.Ident, startOffset = 11, endOffset = 12),
            Token(kind = CssTokenKind.Comma, startOffset = 12, endOffset = 13),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 13, endOffset = 14),
            Token(kind = CssTokenKind.Ident, startOffset = 14, endOffset = 15),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 15, endOffset = 16),
            Token(kind = CssTokenKind.Dot, startOffset = 16, endOffset = 17),
            Token(kind = CssTokenKind.Ident, startOffset = 17, endOffset = 22),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 22, endOffset = 23),
            Token(kind = CssTokenKind.Ident, startOffset = 23, endOffset = 27),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 27, endOffset = 28),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 28, endOffset = 29),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 29, endOffset = 34),
            Token(kind = CssTokenKind.Ident, startOffset = 34, endOffset = 41),
            Token(kind = CssTokenKind.Colon, startOffset = 41, endOffset = 42),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 42, endOffset = 43),
            Token(kind = CssTokenKind.Ident, startOffset = 43, endOffset = 47),
            Token(kind = CssTokenKind.Semicolon, startOffset = 47, endOffset = 48),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 48, endOffset = 49),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 49, endOffset = 50),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 50, endOffset = 50),
        )
        assertTokens(content, tokens)
    }

    @Test
    fun `create tokens for css rule with margin with two values`() {
        val content = """
            |.my-class {
            |    margin: 0 auto;
            |}
        """.trimMargin()
        val tokens = listOf(
            Token(kind = CssTokenKind.Dot, startOffset = 0, endOffset = 1),
            Token(kind = CssTokenKind.Ident, startOffset = 1, endOffset = 9),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 9, endOffset = 10),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 10, endOffset = 11),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 11, endOffset = 16),
            Token(kind = CssTokenKind.Ident, startOffset = 16, endOffset = 22),
            Token(kind = CssTokenKind.Colon, startOffset = 22, endOffset = 23),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 23, endOffset = 24),
            Token(kind = CssTokenKind.Number, startOffset = 24, endOffset = 25),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 25, endOffset = 26),
            Token(kind = CssTokenKind.Ident, startOffset = 26, endOffset = 30),
            Token(kind = CssTokenKind.Semicolon, startOffset = 30, endOffset = 31),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 31, endOffset = 32),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 32, endOffset = 33),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 33, endOffset = 33),
        )
        assertTokens(content, tokens)
    }

    @Test
    fun `create tokens for css rule with aggregate selectors using identifiers and class`() {
        val content = """
            |div span label .my-class {
            |    color: blue;
            |}
        """.trimMargin()
        val tokens = listOf(
            Token(kind = CssTokenKind.Ident, startOffset = 0, endOffset = 3),   // 'div'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 3, endOffset = 4),    // ' '
            Token(kind = CssTokenKind.Ident, startOffset = 4, endOffset = 8),    // 'span'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 8, endOffset = 9),    // ' '
            Token(kind = CssTokenKind.Ident, startOffset = 9, endOffset = 14),   // 'label'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 14, endOffset = 15),  // ' '
            Token(kind = CssTokenKind.Dot, startOffset = 15, endOffset = 16),         // '.'
            Token(kind = CssTokenKind.Ident, startOffset = 16, endOffset = 24),  // 'my-class'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 24, endOffset = 25),  // ' '
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 25, endOffset = 26), // '{'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 26, endOffset = 31),  // '\n    '
            Token(kind = CssTokenKind.Ident, startOffset = 31, endOffset = 36),  // 'color'
            Token(kind = CssTokenKind.Colon, startOffset = 36, endOffset = 37),       // ':'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 37, endOffset = 38),  // ' '
            Token(kind = CssTokenKind.Ident, startOffset = 38, endOffset = 42),  // 'blue'
            Token(kind = CssTokenKind.Semicolon, startOffset = 42, endOffset = 43),   // ';'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 43, endOffset = 44),  // '\n'
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 44, endOffset = 45), // '}'
            Token(kind = CssTokenKind.EndOfFile, startOffset = 45, endOffset = 45),
        )
        assertTokens(content, tokens)
    }

    @Test
    fun `create tokens for complex css rule`() {
        val content = """
            |@media screen and (min-width: 768px) {
            |    body.homepage .main-content > section:first-of-type:not(.hidden) {
            |        display: flex;
            |        flex-direction: column;
            |        align-items: center;
            |        background-color: rgba(255, 255, 255, 0.8);
            |        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            |    }
            |}
        """.trimMargin()
        val tokens = listOf(
            // @media screen and (min-width: 768px) {
            Token(kind = CssTokenKind.AtKeyword, startOffset = 0, endOffset = 6),            // '@media'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 6, endOffset = 7),           // ' '
            Token(kind = CssTokenKind.Ident, startOffset = 7, endOffset = 13),          // 'screen'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 13, endOffset = 14),         // ' '
            Token(kind = CssTokenKind.Ident, startOffset = 14, endOffset = 17),         // 'and'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 17, endOffset = 18),         // ' '
            Token(kind = CssTokenKind.OpenParenthesis, startOffset = 18, endOffset = 19),    // '('
            Token(kind = CssTokenKind.Ident, startOffset = 19, endOffset = 28),         // 'min-width'
            Token(kind = CssTokenKind.Colon, startOffset = 28, endOffset = 29),              // ':'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 29, endOffset = 30),         // ' '
            Token(
                kind = CssTokenKind.Dimension(unit = "px"),
                startOffset = 30,
                endOffset = 35,
            ),          // '768px'
            Token(kind = CssTokenKind.CloseParenthesis, startOffset = 35, endOffset = 36),   // ')'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 36, endOffset = 37),         // ' '
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 37, endOffset = 38),     // '{'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 38, endOffset = 43),         // '\n    '

            // body.homepage .main-content > section:first-of-type:not(.hidden) {
            Token(kind = CssTokenKind.Ident, startOffset = 43, endOffset = 47),         // 'body'
            Token(kind = CssTokenKind.Dot, startOffset = 47, endOffset = 48),                // '.'
            Token(kind = CssTokenKind.Ident, startOffset = 48, endOffset = 56),         // 'homepage'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 56, endOffset = 57),         // ' '
            Token(kind = CssTokenKind.Dot, startOffset = 57, endOffset = 58),                // '.'
            Token(kind = CssTokenKind.Ident, startOffset = 58, endOffset = 70),         // 'main-content'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 70, endOffset = 71),         // ' '
            Token(kind = CssTokenKind.Greater, startOffset = 71, endOffset = 72),        // '>'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 72, endOffset = 73),         // ' '
            Token(kind = CssTokenKind.Ident, startOffset = 73, endOffset = 80),         // 'section'
            Token(kind = CssTokenKind.Colon, startOffset = 80, endOffset = 81),              // ':'
            Token(kind = CssTokenKind.Ident, startOffset = 81, endOffset = 94),         // 'first-of-type'
            Token(kind = CssTokenKind.Colon, startOffset = 94, endOffset = 95),              // ':'
            Token(kind = CssTokenKind.Ident, startOffset = 95, endOffset = 98),         // 'not'
            Token(kind = CssTokenKind.OpenParenthesis, startOffset = 98, endOffset = 99),    // '('
            Token(kind = CssTokenKind.Dot, startOffset = 99, endOffset = 100),               // '.'
            Token(kind = CssTokenKind.Ident, startOffset = 100, endOffset = 106),       // 'hidden'
            Token(kind = CssTokenKind.CloseParenthesis, startOffset = 106, endOffset = 107), // ')'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 107, endOffset = 108),       // ' '
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 108, endOffset = 109),   // '{'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 109, endOffset = 118),       // '\n        '

            // Declaration: display: flex;
            Token(kind = CssTokenKind.Ident, startOffset = 118, endOffset = 125),       // 'display'
            Token(kind = CssTokenKind.Colon, startOffset = 125, endOffset = 126),            // ':'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 126, endOffset = 127),       // ' '
            Token(kind = CssTokenKind.Ident, startOffset = 127, endOffset = 131),       // 'flex'
            Token(kind = CssTokenKind.Semicolon, startOffset = 131, endOffset = 132),        // ';'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 132, endOffset = 141),       // '\n        '

            /* Declaration: flex-direction: column; */
            Token(kind = CssTokenKind.Ident, startOffset = 141, endOffset = 155),       // 'flex-direction'
            Token(kind = CssTokenKind.Colon, startOffset = 155, endOffset = 156),            // ':'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 156, endOffset = 157),       // ' '
            Token(kind = CssTokenKind.Ident, startOffset = 157, endOffset = 163),       // 'column'
            Token(kind = CssTokenKind.Semicolon, startOffset = 163, endOffset = 164),        // ';'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 164, endOffset = 173),       // '\n        '

            /* Declaration: align-items: center; */
            Token(kind = CssTokenKind.Ident, startOffset = 173, endOffset = 184),       // 'align-items'
            Token(kind = CssTokenKind.Colon, startOffset = 184, endOffset = 185),            // ':'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 185, endOffset = 186),       // ' '
            Token(kind = CssTokenKind.Ident, startOffset = 186, endOffset = 192),       // 'center'
            Token(kind = CssTokenKind.Semicolon, startOffset = 192, endOffset = 193),        // ';'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 193, endOffset = 202),       // '\n        '

            /* Declaration: background-color: rgba(255, 255, 255, 0.8); */
            Token(kind = CssTokenKind.Ident, startOffset = 202, endOffset = 218),       // 'background-color'
            Token(kind = CssTokenKind.Colon, startOffset = 218, endOffset = 219),            // ':'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 219, endOffset = 220),       // ' '
            Token(kind = CssTokenKind.Function, startOffset = 220, endOffset = 224),       // 'rgba'
            Token(kind = CssTokenKind.OpenParenthesis, startOffset = 224, endOffset = 225),  // '('
            Token(kind = CssTokenKind.Number, startOffset = 225, endOffset = 228),           // '255'
            Token(kind = CssTokenKind.Comma, startOffset = 228, endOffset = 229),            // ','
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 229, endOffset = 230),       // ' '
            Token(kind = CssTokenKind.Number, startOffset = 230, endOffset = 233),           // '255'
            Token(kind = CssTokenKind.Comma, startOffset = 233, endOffset = 234),            // ','
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 234, endOffset = 235),       // ' '
            Token(kind = CssTokenKind.Number, startOffset = 235, endOffset = 238),           // '255'
            Token(kind = CssTokenKind.Comma, startOffset = 238, endOffset = 239),            // ','
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 239, endOffset = 240),       // ' '
            Token(kind = CssTokenKind.Number, startOffset = 240, endOffset = 243),           // '0.8'
            Token(kind = CssTokenKind.CloseParenthesis, startOffset = 243, endOffset = 244), // ')'
            Token(kind = CssTokenKind.Semicolon, startOffset = 244, endOffset = 245),        // ';'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 245, endOffset = 254),       // '\n        '

            /* Declaration: box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); */
            Token(kind = CssTokenKind.Ident, startOffset = 254, endOffset = 264),            // 'box-shadow'
            Token(kind = CssTokenKind.Colon, startOffset = 264, endOffset = 265),            // ':'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 265, endOffset = 266),       // ' '
            Token(kind = CssTokenKind.Number, startOffset = 266, endOffset = 267),           // '0'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 267, endOffset = 268),       // ' '
            Token(
                kind = CssTokenKind.Dimension(
                    unit = "px",
                ),
                startOffset = 268,
                endOffset = 271,
            ),        // '4px'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 271, endOffset = 272),       // ' '
            Token(
                kind = CssTokenKind.Dimension(
                    unit = "px",
                ),
                startOffset = 272,
                endOffset = 275,
            ),        // '6px'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 275, endOffset = 276),       // ' '
            Token(kind = CssTokenKind.Function, startOffset = 276, endOffset = 280),         // 'rgba'
            Token(kind = CssTokenKind.OpenParenthesis, startOffset = 280, endOffset = 281),  // '('
            Token(kind = CssTokenKind.Number, startOffset = 281, endOffset = 282),           // '0'
            Token(kind = CssTokenKind.Comma, startOffset = 282, endOffset = 283),            // ','
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 283, endOffset = 284),       // ' '
            Token(kind = CssTokenKind.Number, startOffset = 284, endOffset = 285),           // '0'
            Token(kind = CssTokenKind.Comma, startOffset = 285, endOffset = 286),            // ','
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 286, endOffset = 287),       // ' '
            Token(kind = CssTokenKind.Number, startOffset = 287, endOffset = 288),           // '0'
            Token(kind = CssTokenKind.Comma, startOffset = 288, endOffset = 289),            // ','
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 289, endOffset = 290),       // ' '
            Token(kind = CssTokenKind.Number, startOffset = 290, endOffset = 293),           // '0.1'
            Token(kind = CssTokenKind.CloseParenthesis, startOffset = 293, endOffset = 294), // ')'
            Token(kind = CssTokenKind.Semicolon, startOffset = 294, endOffset = 295),        // ';'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 295, endOffset = 300),       // '\n        '

            /* Closing curly braces */
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 300, endOffset = 301),  // '}'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 301, endOffset = 302),       // '\n'
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 302, endOffset = 303),  // '}'
            Token(kind = CssTokenKind.EndOfFile, startOffset = 303, endOffset = 303),
        )
        assertTokens(content, tokens)
    }

    @Test
    @Burst
    fun `when url is incomplete - then should create a BadUrl token`(
        // Arrange
        url: String = burstValues(
            "",
            "(",
            "#abc",
            "https://example.com",
            " https://example.com",
            "               https://example.com",
        )
    ) {
        println("url: '$url'")
        // Arrange (cont.)
        val content = """
            |div {
            |    background-image: url($url;
            |}
            """.trimMargin()
        val tokens = listOf(
            Token(kind = CssTokenKind.Ident, startOffset = 0, endOffset = 3),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 3, endOffset = 4),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 4, endOffset = 5),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 5, endOffset = 10),
            Token(kind = CssTokenKind.Ident, startOffset = 10, endOffset = 26),
            Token(kind = CssTokenKind.Colon, startOffset = 26, endOffset = 27),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 27, endOffset = 28),
            Token(kind = CssTokenKind.BadUrl, startOffset = 28, endOffset = content.length),
            Token(kind = CssTokenKind.EndOfFile, startOffset = content.length, endOffset = content.length),
        )

        // Act / Assert
        assertTokens(content, tokens)
    }

    @Test
    @Burst
    fun `when next non-whitespace token after url is quote or double-quote - then should create a Function token`(
        // Arrange
        url: String = burstValues(
            "\"\"",
            "\"#abc\"",
            "\"https://example.com\"",
            " \"https://example.com\"",
            "               \"https://example.com\"",
            "               \"https://example.com\"            ",
            "''",
            "'#abc'",
            "'https://example.com'",
            " 'https://example.com'",
            "               'https://example.com'",
            "               'https://example.com'            ",
        )
    ) {
        println("url: '$url'")
        // Arrange (cont.)
        val content = """
            |div {
            |    background-image: url($url);
            |}
            """.trimMargin()
        val openParenthesisEndOffset = 32
        val leadingWhitespaceEndOffset = if (url.startsWith(' ')) {
            openParenthesisEndOffset + url.indexOfFirst { it == '"' || it == '\'' } - 1
        } else {
            -1
        }
        val trailingWhitespaceEndOffset = if (url.endsWith(' ')) {
            openParenthesisEndOffset + url.length
        } else {
            -1
        }
        var startOffset = openParenthesisEndOffset
        var endOffset = openParenthesisEndOffset
        val tokens = buildList {
            addAll(
                listOf(
                    Token(kind = CssTokenKind.Ident, startOffset = 0, endOffset = 3),
                    Token(kind = CssTokenKind.WhiteSpace, startOffset = 3, endOffset = 4),
                    Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 4, endOffset = 5),
                    Token(kind = CssTokenKind.WhiteSpace, startOffset = 5, endOffset = 10),
                    Token(kind = CssTokenKind.Ident, startOffset = 10, endOffset = 26),
                    Token(kind = CssTokenKind.Colon, startOffset = 26, endOffset = 27),
                    Token(kind = CssTokenKind.WhiteSpace, startOffset = 27, endOffset = 28),
                    Token(kind = CssTokenKind.Function, startOffset = 28, endOffset = 31),
                    Token(kind = CssTokenKind.OpenParenthesis, startOffset = 31, endOffset = openParenthesisEndOffset),
                ),
            )

            if (leadingWhitespaceEndOffset != -1) {
                add(
                    Token(
                        kind = CssTokenKind.WhiteSpace,
                        startOffset = startOffset,
                        endOffset = leadingWhitespaceEndOffset + 1,
                    ),
                )
                startOffset = leadingWhitespaceEndOffset + 1
                endOffset = leadingWhitespaceEndOffset + url.trim().length + 1
            } else {
                endOffset += url.trim().length
            }

            add(
                Token(
                    kind = CssTokenKind.String,
                    startOffset = startOffset,
                    endOffset = endOffset,
                ),
            )

            if (trailingWhitespaceEndOffset != -1) {
                add(
                    Token(
                        kind = CssTokenKind.WhiteSpace,
                        startOffset = endOffset,
                        endOffset = trailingWhitespaceEndOffset,
                    ),
                )
                endOffset = trailingWhitespaceEndOffset
            }

            addAll(
                listOf(
                    Token(
                        kind = CssTokenKind.CloseParenthesis,
                        startOffset = endOffset++,
                        endOffset = endOffset,
                    ),
                    Token(
                        kind = CssTokenKind.Semicolon,
                        startOffset = endOffset++,
                        endOffset = endOffset,
                    ),
                    Token(
                        kind = CssTokenKind.WhiteSpace,
                        startOffset = endOffset++,
                        endOffset = endOffset,
                    ),
                    Token(
                        kind = CssTokenKind.CloseCurlyBrace,
                        startOffset = endOffset++,
                        endOffset = endOffset,
                    ),
                    Token(kind = CssTokenKind.EndOfFile, startOffset = endOffset, endOffset = endOffset),
                ),
            )
        }

        // Act / Assert
        assertTokens(content, tokens)
    }

    private fun assertTokens(content: String, tokens: List<Token<out CssTokenKind>>) {
        val lexer = CssTokenizer()
        val actual = lexer.tokenize(content).toList()
        assertEquals(expected = tokens, actual = actual)
    }
}
