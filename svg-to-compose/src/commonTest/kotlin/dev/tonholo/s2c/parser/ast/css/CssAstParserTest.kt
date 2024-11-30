package dev.tonholo.s2c.parser.ast.css

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.css.CssTokenKind
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class CssAstParserTest {
    @Test
    fun `parse css class rule to CssRootNode`() {
        val content = """
            |.my-rule {
            |    background: #f0f;
            |    color: #000;
            |}
            """.trimMargin()
        val tokens = listOf(
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
        val expected = CssRootNode(
            rules = listOf(
                CssRule(
                    selectors = listOf(
                        CssSelector.Single(
                            type = CssSelectorType.Class,
                            value = "my-rule",
                        ),
                    ),
                    declarations = listOf(
                        CssDeclaration(
                            property = "background",
                            value = PropertyValue.Color("#f0f"),
                        ),
                        CssDeclaration(
                            property = "color",
                            value = PropertyValue.Color("#000"),
                        ),
                    ),
                )
            )
        )

        assert(content, tokens, expected)
    }

    @Test
    fun `parse css id rule to CssRootNode`() {
        val content = """
            |#my-rule {
            |    background: #f0f;
            |    color: #000;
            |}
            """.trimMargin()

        val tokens = listOf(
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
        val expected = CssRootNode(
            rules = listOf(
                CssRule(
                    selectors = listOf(
                        CssSelector.Single(
                            type = CssSelectorType.Id,
                            value = "my-rule",
                        ),
                    ),
                    declarations = listOf(
                        CssDeclaration(
                            property = "background",
                            value = PropertyValue.Color("#f0f"),
                        ),
                        CssDeclaration(
                            property = "color",
                            value = PropertyValue.Color("#000"),
                        ),
                    ),
                ),
            ),
        )

        assert(content, tokens, expected)
    }

    @Test
    fun `parse css with multiple rules to CssRootNode`() {
        val content = """
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
        val tokens = listOf(
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
        val expected = CssRootNode(
            rules = listOf(
                CssRule(
                    selectors = listOf(
                        CssSelector.Single(
                            type = CssSelectorType.Class,
                            value = "my-rule",
                        ),
                    ),
                    declarations = listOf(
                        CssDeclaration(
                            property = "background",
                            value = PropertyValue.Color("#f0f"),
                        ),
                        CssDeclaration(
                            property = "color",
                            value = PropertyValue.Color("#000"),
                        ),
                    ),
                ),
                CssRule(
                    selectors = listOf(
                        CssSelector.Single(
                            type = CssSelectorType.Id,
                            value = "my-rule",
                        ),
                    ),
                    declarations = listOf(
                        CssDeclaration(
                            property = "background",
                            value = PropertyValue.Color("#0ff"),
                        ),
                        CssDeclaration(
                            property = "margin-bottom",
                            value = PropertyValue.Number(value = "1", units = "px"),
                        ),
                    )
                )
            ),
        )

        assert(content, tokens, expected)
    }

    @Test
    fun `parse css rule with number without unit`() {
        val content = """
            |.my-rule {
            |    font-size: 16;
            |}
            """.trimMargin()
        val tokens = listOf(
            Token(CssTokenKind.Dot, startOffset = 0, endOffset = 1),
            Token(CssTokenKind.Identifier, startOffset = 1, endOffset = 8),
            Token(CssTokenKind.WhiteSpace, startOffset = 8, endOffset = 9),
            Token(CssTokenKind.OpenCurlyBrace, startOffset = 9, endOffset = 10),
            Token(CssTokenKind.WhiteSpace, startOffset = 10, endOffset = 15),
            Token(CssTokenKind.Identifier, startOffset = 15, endOffset = 24),
            Token(CssTokenKind.Colon, startOffset = 24, endOffset = 25),
            Token(CssTokenKind.WhiteSpace, startOffset = 25, endOffset = 26),
            Token(CssTokenKind.Number, startOffset = 26, endOffset = 28),
            Token(CssTokenKind.Semicolon, startOffset = 28, endOffset = 29),
            Token(CssTokenKind.WhiteSpace, startOffset = 29, endOffset = 30),
            Token(CssTokenKind.CloseCurlyBrace, startOffset = 30, endOffset = 31),
            Token(CssTokenKind.EndOfFile, startOffset = 31, endOffset = 31),
        )
        val expected = CssRootNode(
            rules = listOf(
                CssRule(
                    selectors = listOf(
                        CssSelector.Single(
                            type = CssSelectorType.Class,
                            value = "my-rule",
                        ),
                    ),
                    declarations = listOf(
                        CssDeclaration(
                            property = "font-size",
                            value = PropertyValue.Number(value = "16", units = null),
                        ),
                    ),
                ),
            )
        )

        assert(content, tokens, expected)
    }

    @Test
    fun `parse css rule with multiple selectors`() {
        val content = """
            |#my-rule, .my-class {
            |    clip-path: url(#my-clip-path);
            |}
            """.trimMargin()

        val tokens = listOf(
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

        val expected = CssRootNode(
            rules = listOf(
                CssRule(
                    selectors = listOf(
                        CssSelector.Single(
                            type = CssSelectorType.Id,
                            value = "my-rule",
                        ),
                        CssSelector.Single(
                            type = CssSelectorType.Class,
                            value = "my-class",
                        ),
                    ),
                    declarations = listOf(
                        CssDeclaration(
                            property = "clip-path",
                            value = PropertyValue.Url(
                                value = "#my-clip-path",
                            ),
                        ),
                    ),
                ),
            ),
        )

        assert(content, tokens, expected)
    }

    @Test
    fun `parse css rule for tag`() {
        val content = """
            |div {
            |    background: #f0f;
            |    color: #000;
            |}
            """.trimMargin()
        val tokens = listOf(
            Token(kind = CssTokenKind.Identifier, startOffset = 0, endOffset = 3),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 3, endOffset = 4),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 4, endOffset = 5),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 5, endOffset = 10),
            Token(kind = CssTokenKind.Identifier, startOffset = 10, endOffset = 20),
            Token(kind = CssTokenKind.Colon, startOffset = 20, endOffset = 21),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 21, endOffset = 22),
            Token(kind = CssTokenKind.Hash, startOffset = 22, endOffset = 23),
            Token(kind = CssTokenKind.HexDigit, startOffset = 23, endOffset = 26),
            Token(kind = CssTokenKind.Semicolon, startOffset = 26, endOffset = 27),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 27, endOffset = 32),
            Token(kind = CssTokenKind.Identifier, startOffset = 32, endOffset = 37),
            Token(kind = CssTokenKind.Colon, startOffset = 37, endOffset = 38),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 38, endOffset = 39),
            Token(kind = CssTokenKind.Hash, startOffset = 39, endOffset = 40),
            Token(kind = CssTokenKind.HexDigit, startOffset = 40, endOffset = 43),
            Token(kind = CssTokenKind.Semicolon, startOffset = 43, endOffset = 44),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 44, endOffset = 45),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 45, endOffset = 46),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 46, endOffset = 46),
        )

        val expected = CssRootNode(
            rules = listOf(
                CssRule(
                    selectors = listOf(
                        CssSelector.Single(
                            type = CssSelectorType.Tag,
                            value = "div",
                        ),
                    ),
                    declarations = listOf(
                        CssDeclaration(
                            property = "background",
                            value = PropertyValue.Color("#f0f"),
                        ),
                        CssDeclaration(
                            property = "color",
                            value = PropertyValue.Color("#000"),
                        ),
                    ),
                ),
            )
        )

        assert(content, tokens, expected)
    }

    @Test
    fun `parse css rule for multiple selectors with different types`() {
        val content = """
            |div, .content, #my-rule {
            |    background: #f0f;
            |    color: #000;
            |}
            """.trimMargin()
        val tokens = listOf(
            Token(kind = CssTokenKind.Identifier, startOffset = 0, endOffset = 3),
            Token(kind = CssTokenKind.Comma, startOffset = 3, endOffset = 4),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 4, endOffset = 5),
            Token(kind = CssTokenKind.Dot, startOffset = 5, endOffset = 6),
            Token(kind = CssTokenKind.Identifier, startOffset = 6, endOffset = 13),
            Token(kind = CssTokenKind.Comma, startOffset = 13, endOffset = 14),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 14, endOffset = 15),
            Token(kind = CssTokenKind.Hash, startOffset = 15, endOffset = 16),
            Token(kind = CssTokenKind.Identifier, startOffset = 16, endOffset = 23),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 23, endOffset = 24),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 24, endOffset = 25),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 25, endOffset = 30),
            Token(kind = CssTokenKind.Identifier, startOffset = 30, endOffset = 40),
            Token(kind = CssTokenKind.Colon, startOffset = 40, endOffset = 41),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 41, endOffset = 42),
            Token(kind = CssTokenKind.Hash, startOffset = 42, endOffset = 43),
            Token(kind = CssTokenKind.HexDigit, startOffset = 43, endOffset = 46),
            Token(kind = CssTokenKind.Semicolon, startOffset = 46, endOffset = 47),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 47, endOffset = 52),
            Token(kind = CssTokenKind.Identifier, startOffset = 52, endOffset = 57),
            Token(kind = CssTokenKind.Colon, startOffset = 57, endOffset = 58),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 58, endOffset = 59),
            Token(kind = CssTokenKind.Hash, startOffset = 59, endOffset = 60),
            Token(kind = CssTokenKind.HexDigit, startOffset = 60, endOffset = 63),
            Token(kind = CssTokenKind.Semicolon, startOffset = 63, endOffset = 64),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 64, endOffset = 65),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 65, endOffset = 66),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 66, endOffset = 66),
        )

        val expected = CssRootNode(
            rules = listOf(
                CssRule(
                    selectors = listOf(
                        CssSelector.Single(
                            type = CssSelectorType.Tag,
                            value = "div",
                        ),
                        CssSelector.Single(
                            type = CssSelectorType.Class,
                            value = "content",
                        ),
                        CssSelector.Single(
                            type = CssSelectorType.Id,
                            value = "my-rule",
                        ),
                    ),
                    declarations = listOf(
                        CssDeclaration(
                            property = "background",
                            value = PropertyValue.Color("#f0f"),
                        ),
                        CssDeclaration(
                            property = "color",
                            value = PropertyValue.Color("#000"),
                        ),
                    ),
                ),
            )
        )

        assert(content, tokens, expected)
    }

    @Test
    fun `parse rules for css with element with multiple selectors`() {
        val content = """
            |div.element-class#element-id {
            |   display: none;
            |}
            """.trimMargin()
        val tokens = listOf(
            Token(kind = CssTokenKind.Identifier, startOffset = 0, endOffset = 3),
            Token(kind = CssTokenKind.Dot, startOffset = 3, endOffset = 4),
            Token(kind = CssTokenKind.Identifier, startOffset = 4, endOffset = 17),
            Token(kind = CssTokenKind.Hash, startOffset = 17, endOffset = 18),
            Token(kind = CssTokenKind.Identifier, startOffset = 18, endOffset = 28),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 28, endOffset = 29),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 29, endOffset = 30),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 30, endOffset = 34),
            Token(kind = CssTokenKind.Identifier, startOffset = 34, endOffset = 41),
            Token(kind = CssTokenKind.Colon, startOffset = 41, endOffset = 42),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 42, endOffset = 43),
            Token(kind = CssTokenKind.Identifier, startOffset = 43, endOffset = 47),
            Token(kind = CssTokenKind.Semicolon, startOffset = 47, endOffset = 48),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 48, endOffset = 49),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 49, endOffset = 50),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 50, endOffset = 50),
        )
        val expected = CssRootNode(
            rules = listOf(
                CssRule(
                    selectors = listOf(
                        CssSelector.Multiple(
                            selectors = listOf(
                                CssSelector.Single(
                                    type = CssSelectorType.Tag,
                                    value = "div",
                                ),
                                CssSelector.Single(
                                    type = CssSelectorType.Class,
                                    value = "element-class",
                                ),
                                CssSelector.Single(
                                    type = CssSelectorType.Id,
                                    value = "element-id",
                                ),
                            ),
                        ),
                    ),
                    declarations = listOf(
                        CssDeclaration(
                            property = "display",
                            value = PropertyValue.Identifier("none"),
                        ),
                    ),
                ),
            ),
        )

        assert(content, tokens, expected)
    }

    @Test
    fun `parse rules for css without formatting`() {
        val content = """
            |div{display:none}
            """.trimMargin()

        val tokens = listOf(
            Token(kind = CssTokenKind.Identifier, startOffset = 0, endOffset = 3),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 3, endOffset = 4),
            Token(kind = CssTokenKind.Identifier, startOffset = 4, endOffset = 11),
            Token(kind = CssTokenKind.Colon, startOffset = 11, endOffset = 12),
            Token(kind = CssTokenKind.Identifier, startOffset = 12, endOffset = 16),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 16, endOffset = 17),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 17, endOffset = 17),
        )
        val expected = CssRootNode(
            rules = listOf(
                CssRule(
                    selectors = listOf(
                        CssSelector.Single(
                            type = CssSelectorType.Tag,
                            value = "div",
                        ),
                    ),
                    declarations = listOf(
                        CssDeclaration(
                            property = "display",
                            value = PropertyValue.Identifier("none"),
                        ),
                    ),
                ),
            ),
        )
        assert(content, tokens, expected)
    }

    @Test
    fun `parse rules for css with tag and id`() {
        val content = """
            |div#id { display:none }
            """.trimMargin()

        val tokens = listOf(
            Token(kind = CssTokenKind.Identifier, startOffset = 0, endOffset = 3),
            Token(kind = CssTokenKind.Hash, startOffset = 3, endOffset = 4),
            Token(kind = CssTokenKind.Identifier, startOffset = 4, endOffset = 6),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 6, endOffset = 7),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 7, endOffset = 8),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 8, endOffset = 9),
            Token(kind = CssTokenKind.Identifier, startOffset = 9, endOffset = 16),
            Token(kind = CssTokenKind.Colon, startOffset = 16, endOffset = 17),
            Token(kind = CssTokenKind.Identifier, startOffset = 17, endOffset = 21),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 21, endOffset = 22),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 22, endOffset = 22),
        )
        val expected = CssRootNode(
            rules = listOf(
                CssRule(
                    selectors = listOf(
                        CssSelector.Multiple(
                            selectors = listOf(
                                CssSelector.Single(
                                    type = CssSelectorType.Tag,
                                    value = "div",
                                ),
                                CssSelector.Single(
                                    type = CssSelectorType.Id,
                                    value = "id",
                                ),
                            )
                        )
                    ),
                    declarations = listOf(
                        CssDeclaration(
                            property = "display",
                            value = PropertyValue.Identifier("none"),
                        ),
                    ),
                ),
            ),
        )
        assert(content, tokens, expected)
    }

    @Test
    fun `throw IllegalStateException when incomplete url property value`() {
        val content = """
            |div {
            |    background-image: url();
            |}
            """.trimMargin()
        val tokens = listOf(
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

        val astParser = CssAstParser(content)
        val exception = assertFailsWith<IllegalStateException> {
            astParser.parse(tokens)
        }
        val message = exception.message
        println(message)
        assertNotNull(message)
        assertContains(message, "Incomplete URL value.")
    }

    private fun assert(
        content: String,
        tokens: List<Token<out CssTokenKind>>,
        expected: CssRootNode,
    ) {
        val astParser = CssAstParser(content)
        val actual = astParser.parse(tokens)
        assertEquals(expected, actual)
    }
}
