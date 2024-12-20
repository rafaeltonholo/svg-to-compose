package dev.tonholo.s2c.parser.ast.css

import app.cash.burst.Burst
import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.css.consumer.CssConsumers
import dev.tonholo.s2c.parser.ast.css.syntax.AstParserException
import dev.tonholo.s2c.parser.ast.css.syntax.node.Block
import dev.tonholo.s2c.parser.ast.css.syntax.node.CssLocation
import dev.tonholo.s2c.parser.ast.css.syntax.node.Declaration
import dev.tonholo.s2c.parser.ast.css.syntax.node.Prelude
import dev.tonholo.s2c.parser.ast.css.syntax.node.QualifiedRule
import dev.tonholo.s2c.parser.ast.css.syntax.node.Selector
import dev.tonholo.s2c.parser.ast.css.syntax.node.SelectorListItem
import dev.tonholo.s2c.parser.ast.css.syntax.node.StyleSheet
import dev.tonholo.s2c.parser.ast.css.syntax.node.Value
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class CssParserTest {
    @Test
    fun `parse css class rule to StyleSheet`() {
        val content = """
            |.my-rule {
            |    background: #f0f;
            |    color: #000;
            |}
            """.trimMargin()
        val tokens = listOf(
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

        val expected = StyleSheet(
            location = CssLocation(
                source = content.substring(0, 51),
                start = 0,
                end = 51,
            ),
            children = listOf(
                QualifiedRule(
                    location = CssLocation(
                        source = content.substring(0, 51),
                        start = 0,
                        end = 51,
                    ),
                    prelude = Prelude.Selector(
                        components = listOf(
                            SelectorListItem(
                                location = CssLocation(
                                    source = content.substring(0, 8),
                                    start = 0,
                                    end = 8,
                                ),
                                selectors = listOf(
                                    Selector.Class(
                                        location = CssLocation(
                                            source = content.substring(0, 8),
                                            start = 0,
                                            end = 8,
                                        ),
                                        name = "my-rule",
                                    ),
                                ),
                            ),
                        ),
                    ),
                    block = Block.SimpleBlock(
                        location = CssLocation(
                            source = content.substring(9, 51),
                            start = 9,
                            end = 51,
                        ),
                        children = listOf(
                            Declaration(
                                location = CssLocation(
                                    source = content.substring(15, 32),
                                    start = 15,
                                    end = 32,
                                ),
                                important = false,
                                property = "background",
                                values = listOf(
                                    Value.Color(
                                        location = CssLocation(
                                            source = content.substring(27, 31),
                                            start = 27,
                                            end = 31,
                                        ),
                                        value = "#f0f",
                                    ),
                                ),
                            ),
                            Declaration(
                                location = CssLocation(
                                    source = content.substring(37, 49),
                                    start = 37,
                                    end = 49,
                                ),
                                important = false,
                                property = "color",
                                values = listOf(
                                    Value.Color(
                                        location = CssLocation(
                                            source = content.substring(44, 48),
                                            start = 44,
                                            end = 48,
                                        ),
                                        value = "#000",
                                    ),
                                ),
                            ),
                        ),
                    ),
                ),
            ),
        )

        assert(content, tokens, expected)
    }

    @Test
    fun `parse css id rule to StyleSheet`() {
        val content = """
            |#my-rule {
            |    background: #f0f;
            |    color: #000;
            |}
            """.trimMargin()

        val tokens = listOf(
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
        val expected = StyleSheet(
            location = CssLocation(
                source = content.substring(0, 51),
                start = 0,
                end = 51,
            ),
            children = listOf(
                QualifiedRule(
                    location = CssLocation(
                        source = content.substring(0, 51),
                        start = 0,
                        end = 51,
                    ),
                    prelude = Prelude.Selector(
                        components = listOf(
                            SelectorListItem(
                                location = CssLocation(
                                    source = content.substring(0, 8),
                                    start = 0,
                                    end = 8,
                                ),
                                selectors = listOf(
                                    Selector.Id(
                                        location = CssLocation(
                                            source = content.substring(0, 8),
                                            start = 0,
                                            end = 8,
                                        ),
                                        name = "my-rule",
                                    ),
                                ),
                            ),
                        ),
                    ),
                    block = Block.SimpleBlock(
                        location = CssLocation(
                            source = content.substring(9, 51),
                            start = 9,
                            end = 51,
                        ),
                        children = listOf(
                            Declaration(
                                location = CssLocation(
                                    source = content.substring(15, 32),
                                    start = 15,
                                    end = 32,
                                ),
                                important = false,
                                property = "background",
                                values = listOf(
                                    Value.Color(
                                        location = CssLocation(
                                            source = content.substring(27, 31),
                                            start = 27,
                                            end = 31,
                                        ),
                                        value = "#f0f",
                                    ),
                                ),
                            ),
                            Declaration(
                                location = CssLocation(
                                    source = content.substring(37, 49),
                                    start = 37,
                                    end = 49,
                                ),
                                important = false,
                                property = "color",
                                values = listOf(
                                    Value.Color(
                                        location = CssLocation(
                                            source = content.substring(44, 48),
                                            start = 44,
                                            end = 48,
                                        ),
                                        value = "#000",
                                    ),
                                ),
                            ),
                        ),
                    ),
                ),
            ),
        )

        assert(content, tokens, expected)
    }

    @Test
    fun `parse css with multiple rules to StyleSheet`() {
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
            Token(kind = CssTokenKind.Dimension, startOffset = 105, endOffset = 108),
            Token(kind = CssTokenKind.Semicolon, startOffset = 108, endOffset = 109),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 109, endOffset = 110),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 110, endOffset = 111),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 111, endOffset = 111),
        )
        val expected = StyleSheet(
            location = CssLocation(
                source = content.substring(0, 111),
                start = 0,
                end = 111,
            ),
            children = listOf(
                QualifiedRule(
                    location = CssLocation(
                        source = content.substring(0, 51),
                        start = 0,
                        end = 51,
                    ),
                    prelude = Prelude.Selector(
                        components = listOf(
                            SelectorListItem(
                                location = CssLocation(
                                    source = content.substring(0, 8),
                                    start = 0,
                                    end = 8,
                                ),
                                selectors = listOf(
                                    Selector.Class(
                                        location = CssLocation(
                                            source = content.substring(0, 8),
                                            start = 0,
                                            end = 8,
                                        ),
                                        name = "my-rule",
                                    ),
                                ),
                            ),
                        ),
                    ),
                    block = Block.SimpleBlock(
                        location = CssLocation(
                            source = content.substring(9, 51),
                            start = 9,
                            end = 51,
                        ),
                        children = listOf(
                            Declaration(
                                location = CssLocation(
                                    source = content.substring(15, 32),
                                    start = 15,
                                    end = 32,
                                ),
                                important = false,
                                property = "background",
                                values = listOf(
                                    Value.Color(
                                        location = CssLocation(
                                            source = content.substring(27, 31),
                                            start = 27,
                                            end = 31,
                                        ),
                                        value = "#f0f",
                                    ),
                                ),
                            ),
                            Declaration(
                                location = CssLocation(
                                    source = content.substring(37, 49),
                                    start = 37,
                                    end = 49,
                                ),
                                important = false,
                                property = "color",
                                values = listOf(
                                    Value.Color(
                                        location = CssLocation(
                                            source = content.substring(44, 48),
                                            start = 44,
                                            end = 48,
                                        ),
                                        value = "#000",
                                    ),
                                ),
                            ),
                        ),
                    ),
                ),
                QualifiedRule(
                    location = CssLocation(
                        source = content.substring(53, 111),
                        start = 53,
                        end = 111,
                    ),
                    prelude = Prelude.Selector(
                        components = listOf(
                            SelectorListItem(
                                location = CssLocation(
                                    source = content.substring(53, 61),
                                    start = 53,
                                    end = 61,
                                ),
                                selectors = listOf(
                                    Selector.Id(
                                        location = CssLocation(
                                            source = content.substring(53, 61),
                                            start = 53,
                                            end = 61,
                                        ),
                                        name = "my-rule",
                                    ),
                                ),
                            ),
                        ),
                    ),
                    block = Block.SimpleBlock(
                        location = CssLocation(
                            source = content.substring(62, 111),
                            start = 62,
                            end = 111,
                        ),
                        children = listOf(
                            Declaration(
                                location = CssLocation(
                                    source = content.substring(68, 85),
                                    start = 68,
                                    end = 85,
                                ),
                                important = false,
                                property = "background",
                                values = listOf(
                                    Value.Color(
                                        location = CssLocation(
                                            source = content.substring(80, 84),
                                            start = 80,
                                            end = 84,
                                        ),
                                        value = "#0ff",
                                    ),
                                ),
                            ),
                            Declaration(
                                location = CssLocation(
                                    source = content.substring(90, 109),
                                    start = 90,
                                    end = 109,
                                ),
                                important = false,
                                property = "margin-bottom",
                                values = listOf(
                                    Value.Dimension(
                                        location = CssLocation(
                                            source = content.substring(105, 108),
                                            start = 105,
                                            end = 108,
                                        ),
                                        value = "1",
                                        unit = "px",
                                    ),
                                ),
                            ),
                        ),
                    ),
                ),
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
            Token(CssTokenKind.Ident, startOffset = 1, endOffset = 8),
            Token(CssTokenKind.WhiteSpace, startOffset = 8, endOffset = 9),
            Token(CssTokenKind.OpenCurlyBrace, startOffset = 9, endOffset = 10),
            Token(CssTokenKind.WhiteSpace, startOffset = 10, endOffset = 15),
            Token(CssTokenKind.Ident, startOffset = 15, endOffset = 24),
            Token(CssTokenKind.Colon, startOffset = 24, endOffset = 25),
            Token(CssTokenKind.WhiteSpace, startOffset = 25, endOffset = 26),
            Token(CssTokenKind.Number, startOffset = 26, endOffset = 28),
            Token(CssTokenKind.Semicolon, startOffset = 28, endOffset = 29),
            Token(CssTokenKind.WhiteSpace, startOffset = 29, endOffset = 30),
            Token(CssTokenKind.CloseCurlyBrace, startOffset = 30, endOffset = 31),
            Token(CssTokenKind.EndOfFile, startOffset = 31, endOffset = 31),
        )
        val expected = StyleSheet(
            location = CssLocation(
                source = content.substring(0, 31),
                start = 0,
                end = 31,
            ),
            children = listOf(
                QualifiedRule(
                    location = CssLocation(
                        source = content.substring(0, 31),
                        start = 0,
                        end = 31,
                    ),
                    prelude = Prelude.Selector(
                        components = listOf(
                            SelectorListItem(
                                location = CssLocation(
                                    source = content.substring(0, 8),
                                    start = 0,
                                    end = 8,
                                ),
                                selectors = listOf(
                                    Selector.Class(
                                        location = CssLocation(
                                            source = content.substring(0, 8),
                                            start = 0,
                                            end = 8,
                                        ),
                                        name = "my-rule",
                                    ),
                                ),
                            ),
                        ),
                    ),
                    block = Block.SimpleBlock(
                        location = CssLocation(
                            source = content.substring(9, 31),
                            start = 9,
                            end = 31,
                        ),
                        children = listOf(
                            Declaration(
                                location = CssLocation(
                                    source = content.substring(15, 29),
                                    start = 15,
                                    end = 29,
                                ),
                                important = false,
                                property = "font-size",
                                values = listOf(
                                    Value.Number(
                                        location = CssLocation(
                                            source = content.substring(26, 28),
                                            start = 26,
                                            end = 28,
                                        ),
                                        value = "16",
                                    ),
                                ),
                            ),
                        ),
                    ),
                ),
            ),
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

        val expected = StyleSheet(
            location = CssLocation(
                source = content.substring(0, 58),
                start = 0,
                end = 58,
            ),
            children = listOf(
                QualifiedRule(
                    location = CssLocation(
                        source = content.substring(0, 58),
                        start = 0,
                        end = 58,
                    ),
                    prelude = Prelude.Selector(
                        components = listOf(
                            SelectorListItem(
                                location = CssLocation(
                                    source = content.substring(0, 8),
                                    start = 0,
                                    end = 8,
                                ),
                                selectors = listOf(
                                    Selector.Id(
                                        location = CssLocation(
                                            source = content.substring(0, 8),
                                            start = 0,
                                            end = 8,
                                        ),
                                        name = "my-rule",
                                    ),
                                ),
                            ),
                            SelectorListItem(
                                location = CssLocation(
                                    source = content.substring(10, 19),
                                    start = 10,
                                    end = 19,
                                ),
                                selectors = listOf(
                                    Selector.Class(
                                        location = CssLocation(
                                            source = content.substring(10, 19),
                                            start = 10,
                                            end = 19,
                                        ),
                                        name = "my-class",
                                    ),
                                ),
                            ),
                        ),
                    ),
                    block = Block.SimpleBlock(
                        location = CssLocation(
                            source = content.substring(20, 58),
                            start = 20,
                            end = 58,
                        ),
                        children = listOf(
                            Declaration(
                                location = CssLocation(
                                    source = content.substring(26, 56),
                                    start = 26,
                                    end = 56,
                                ),
                                important = false,
                                property = "clip-path",
                                values = listOf(
                                    Value.Url(
                                        location = CssLocation(
                                            source = content.substring(41, 54),
                                            start = 41,
                                            end = 54,
                                        ),
                                        value = "#my-clip-path",
                                    ),
                                ),
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
            Token(kind = CssTokenKind.Ident, startOffset = 0, endOffset = 3),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 3, endOffset = 4),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 4, endOffset = 5),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 5, endOffset = 10),
            Token(kind = CssTokenKind.Ident, startOffset = 10, endOffset = 20),
            Token(kind = CssTokenKind.Colon, startOffset = 20, endOffset = 21),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 21, endOffset = 22),
            Token(kind = CssTokenKind.Hash, startOffset = 22, endOffset = 23),
            Token(kind = CssTokenKind.HexDigit, startOffset = 23, endOffset = 26),
            Token(kind = CssTokenKind.Semicolon, startOffset = 26, endOffset = 27),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 27, endOffset = 32),
            Token(kind = CssTokenKind.Ident, startOffset = 32, endOffset = 37),
            Token(kind = CssTokenKind.Colon, startOffset = 37, endOffset = 38),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 38, endOffset = 39),
            Token(kind = CssTokenKind.Hash, startOffset = 39, endOffset = 40),
            Token(kind = CssTokenKind.HexDigit, startOffset = 40, endOffset = 43),
            Token(kind = CssTokenKind.Semicolon, startOffset = 43, endOffset = 44),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 44, endOffset = 45),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 45, endOffset = 46),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 46, endOffset = 46),
        )

        val expected = StyleSheet(
            location = CssLocation(
                source = content.substring(0, 46),
                start = 0,
                end = 46,
            ),
            children = listOf(
                QualifiedRule(
                    location = CssLocation(
                        source = content.substring(0, 46),
                        start = 0,
                        end = 46,
                    ),
                    prelude = Prelude.Selector(
                        components = listOf(
                            SelectorListItem(
                                location = CssLocation(
                                    source = content.substring(0, 3),
                                    start = 0,
                                    end = 3,
                                ),
                                selectors = listOf(
                                    Selector.Type(
                                        location = CssLocation(
                                            source = content.substring(0, 3),
                                            start = 0,
                                            end = 3,
                                        ),
                                        name = "div",
                                    ),
                                ),
                            ),
                        ),
                    ),
                    block = Block.SimpleBlock(
                        location = CssLocation(
                            source = content.substring(4, 46),
                            start = 4,
                            end = 46,
                        ),
                        children = listOf(
                            Declaration(
                                location = CssLocation(
                                    source = content.substring(10, 27),
                                    start = 10,
                                    end = 27,
                                ),
                                important = false,
                                property = "background",
                                values = listOf(
                                    Value.Color(
                                        location = CssLocation(
                                            source = content.substring(22, 26),
                                            start = 22,
                                            end = 26,
                                        ),
                                        value = "#f0f",
                                    ),
                                ),
                            ),
                            Declaration(
                                location = CssLocation(
                                    source = content.substring(32, 44),
                                    start = 32,
                                    end = 44,
                                ),
                                important = false,
                                property = "color",
                                values = listOf(
                                    Value.Color(
                                        location = CssLocation(
                                            source = content.substring(39, 43),
                                            start = 39,
                                            end = 43,
                                        ),
                                        value = "#000",
                                    ),
                                ),
                            ),
                        ),
                    ),
                ),
            ),
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
            Token(kind = CssTokenKind.Ident, startOffset = 0, endOffset = 3),
            Token(kind = CssTokenKind.Comma, startOffset = 3, endOffset = 4),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 4, endOffset = 5),
            Token(kind = CssTokenKind.Dot, startOffset = 5, endOffset = 6),
            Token(kind = CssTokenKind.Ident, startOffset = 6, endOffset = 13),
            Token(kind = CssTokenKind.Comma, startOffset = 13, endOffset = 14),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 14, endOffset = 15),
            Token(kind = CssTokenKind.Hash, startOffset = 15, endOffset = 16),
            Token(kind = CssTokenKind.Ident, startOffset = 16, endOffset = 23),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 23, endOffset = 24),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 24, endOffset = 25),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 25, endOffset = 30),
            Token(kind = CssTokenKind.Ident, startOffset = 30, endOffset = 40),
            Token(kind = CssTokenKind.Colon, startOffset = 40, endOffset = 41),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 41, endOffset = 42),
            Token(kind = CssTokenKind.Hash, startOffset = 42, endOffset = 43),
            Token(kind = CssTokenKind.HexDigit, startOffset = 43, endOffset = 46),
            Token(kind = CssTokenKind.Semicolon, startOffset = 46, endOffset = 47),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 47, endOffset = 52),
            Token(kind = CssTokenKind.Ident, startOffset = 52, endOffset = 57),
            Token(kind = CssTokenKind.Colon, startOffset = 57, endOffset = 58),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 58, endOffset = 59),
            Token(kind = CssTokenKind.Hash, startOffset = 59, endOffset = 60),
            Token(kind = CssTokenKind.HexDigit, startOffset = 60, endOffset = 63),
            Token(kind = CssTokenKind.Semicolon, startOffset = 63, endOffset = 64),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 64, endOffset = 65),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 65, endOffset = 66),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 66, endOffset = 66),
        )

        val expected = StyleSheet(
            location = CssLocation(
                source = content.substring(0, 66),
                start = 0,
                end = 66,
            ),
            children = listOf(
                QualifiedRule(
                    location = CssLocation(
                        source = content.substring(0, 66),
                        start = 0,
                        end = 66,
                    ),
                    prelude = Prelude.Selector(
                        components = listOf(
                            SelectorListItem(
                                location = CssLocation(
                                    source = content.substring(0, 3),
                                    start = 0,
                                    end = 3,
                                ),
                                selectors = listOf(
                                    Selector.Type(
                                        location = CssLocation(
                                            source = content.substring(0, 3),
                                            start = 0,
                                            end = 3,
                                        ),
                                        name = "div",
                                    ),
                                ),
                            ),
                            SelectorListItem(
                                location = CssLocation(
                                    source = content.substring(5, 13),
                                    start = 5,
                                    end = 13,
                                ),
                                selectors = listOf(
                                    Selector.Class(
                                        location = CssLocation(
                                            source = content.substring(5, 13),
                                            start = 5,
                                            end = 13,
                                        ),
                                        name = "content",
                                    ),
                                ),
                            ),
                            SelectorListItem(
                                location = CssLocation(
                                    source = content.substring(15, 23),
                                    start = 15,
                                    end = 23,
                                ),
                                selectors = listOf(
                                    Selector.Id(
                                        location = CssLocation(
                                            source = content.substring(15, 23),
                                            start = 15,
                                            end = 23,
                                        ),
                                        name = "my-rule",
                                    ),
                                ),
                            ),
                        ),
                    ),
                    block = Block.SimpleBlock(
                        location = CssLocation(
                            source = content.substring(24, 66),
                            start = 24,
                            end = 66,
                        ),
                        children = listOf(
                            Declaration(
                                location = CssLocation(
                                    source = content.substring(30, 47),
                                    start = 30,
                                    end = 47,
                                ),
                                important = false,
                                property = "background",
                                values = listOf(
                                    Value.Color(
                                        location = CssLocation(
                                            source = content.substring(42, 46),
                                            start = 42,
                                            end = 46,
                                        ),
                                        value = "#f0f",
                                    ),
                                ),
                            ),
                            Declaration(
                                location = CssLocation(
                                    source = content.substring(52, 64),
                                    start = 52,
                                    end = 64,
                                ),
                                important = false,
                                property = "color",
                                values = listOf(
                                    Value.Color(
                                        location = CssLocation(
                                            source = content.substring(59, 63),
                                            start = 59,
                                            end = 63,
                                        ),
                                        value = "#000",
                                    ),
                                ),
                            ),
                        ),
                    )
                ),
            ),
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
        val expected = StyleSheet(
            location = CssLocation(
                source = content.substring(0, 50),
                start = 0,
                end = 50,
            ),
            children = listOf(
                QualifiedRule(
                    location = CssLocation(
                        source = content.substring(0, 50),
                        start = 0,
                        end = 50,
                    ),
                    prelude = Prelude.Selector(
                        components = listOf(
                            SelectorListItem(
                                location = CssLocation(
                                    source = content.substring(0, 28),
                                    start = 0,
                                    end = 28,
                                ),
                                selectors = listOf(
                                    Selector.Type(
                                        location = CssLocation(
                                            source = content.substring(0, 3),
                                            start = 0,
                                            end = 3,
                                        ),
                                        name = "div",
                                    ),
                                    Selector.Class(
                                        location = CssLocation(
                                            source = content.substring(3, 17),
                                            start = 3,
                                            end = 17,
                                        ),
                                        name = "element-class",
                                    ),
                                    Selector.Id(
                                        location = CssLocation(
                                            source = content.substring(17, 28),
                                            start = 17,
                                            end = 28,
                                        ),
                                        name = "element-id",
                                    ),
                                ),
                            ),
                        ),
                    ),
                    block = Block.SimpleBlock(
                        location = CssLocation(
                            source = content.substring(29, 50),
                            start = 29,
                            end = 50,
                        ),
                        children = listOf(
                            Declaration(
                                location = CssLocation(
                                    source = content.substring(34, 48),
                                    start = 34,
                                    end = 48,
                                ),
                                important = false,
                                property = "display",
                                values = listOf(
                                    Value.Identifier(
                                        location = CssLocation(
                                            source = content.substring(43, 47),
                                            start = 43,
                                            end = 47,
                                        ),
                                        name = "none",
                                    ),
                                ),
                            ),
                        ),
                    )
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
            Token(kind = CssTokenKind.Ident, startOffset = 0, endOffset = 3),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 3, endOffset = 4),
            Token(kind = CssTokenKind.Ident, startOffset = 4, endOffset = 11),
            Token(kind = CssTokenKind.Colon, startOffset = 11, endOffset = 12),
            Token(kind = CssTokenKind.Ident, startOffset = 12, endOffset = 16),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 16, endOffset = 17),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 17, endOffset = 17),
        )
        val expected = StyleSheet(
            location = CssLocation(
                source = content.substring(0, 17),
                start = 0,
                end = 17,
            ),
            children = listOf(
                QualifiedRule(
                    location = CssLocation(
                        source = content.substring(0, 17),
                        start = 0,
                        end = 17,
                    ),
                    prelude = Prelude.Selector(
                        components = listOf(
                            SelectorListItem(
                                location = CssLocation(
                                    source = content.substring(0, 3),
                                    start = 0,
                                    end = 3,
                                ),
                                selectors = listOf(
                                    Selector.Type(
                                        location = CssLocation(
                                            source = content.substring(0, 3),
                                            start = 0,
                                            end = 3,
                                        ),
                                        name = "div",
                                    ),
                                ),
                            ),
                        ),
                    ),
                    block = Block.SimpleBlock(
                        location = CssLocation(
                            source = content.substring(3, 17),
                            start = 3,
                            end = 17,
                        ),
                        children = listOf(
                            Declaration(
                                location = CssLocation(
                                    source = content.substring(4, 16),
                                    start = 4,
                                    end = 16,
                                ),
                                important = false,
                                property = "display",
                                values = listOf(
                                    Value.Identifier(
                                        location = CssLocation(
                                            source = content.substring(12, 16),
                                            start = 12,
                                            end = 16
                                        ),
                                        name = "none",
                                    ),
                                ),
                            ),
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
            Token(kind = CssTokenKind.Ident, startOffset = 0, endOffset = 3),
            Token(kind = CssTokenKind.Hash, startOffset = 3, endOffset = 4),
            Token(kind = CssTokenKind.Ident, startOffset = 4, endOffset = 6),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 6, endOffset = 7),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 7, endOffset = 8),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 8, endOffset = 9),
            Token(kind = CssTokenKind.Ident, startOffset = 9, endOffset = 16),
            Token(kind = CssTokenKind.Colon, startOffset = 16, endOffset = 17),
            Token(kind = CssTokenKind.Ident, startOffset = 17, endOffset = 21),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 21, endOffset = 22),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 22, endOffset = 23),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 23, endOffset = 23),
        )
        val expected = StyleSheet(
            location = CssLocation(
                source = content.substring(0, 23),
                start = 0,
                end = 23,
            ),
            children = listOf(
                QualifiedRule(
                    location = CssLocation(
                        source = content.substring(0, 23),
                        start = 0,
                        end = 23,
                    ),
                    prelude = Prelude.Selector(
                        components = listOf(
                            SelectorListItem(
                                location = CssLocation(
                                    source = content.substring(0, 6),
                                    start = 0,
                                    end = 6,
                                ),
                                selectors = listOf(
                                    Selector.Type(
                                        location = CssLocation(
                                            source = content.substring(0, 3),
                                            start = 0,
                                            end = 3,
                                        ),
                                        name = "div",
                                    ),
                                    Selector.Id(
                                        location = CssLocation(
                                            source = content.substring(3, 6),
                                            start = 3,
                                            end = 6,
                                        ),
                                        name = "id",
                                    ),
                                ),
                            ),
                        ),
                    ),
                    block = Block.SimpleBlock(
                        location = CssLocation(
                            source = content.substring(7, 23),
                            start = 7,
                            end = 23,
                        ),
                        children = listOf(
                            Declaration(
                                location = CssLocation(
                                    source = content.substring(9, 21),
                                    start = 9,
                                    end = 21,
                                ),
                                important = false,
                                property = "display",
                                values = listOf(
                                    Value.Identifier(
                                        location = CssLocation(
                                            source = content.substring(17, 21),
                                            start = 17,
                                            end = 21,
                                        ),
                                        name = "none",
                                    ),
                                ),
                            ),
                        ),
                    ),
                ),
            ),
        )
        assert(content, tokens, expected)
    }

    @Test
    fun `parse rules with aggregate selectors using spaces and multiple rules`() {
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
        val expected = StyleSheet(
            location = CssLocation(
                source = content.substring(0, 50),
                start = 0,
                end = 50,
            ),
            children = listOf(
                QualifiedRule(
                    location = CssLocation(
                        source = content.substring(0, 50),
                        start = 0,
                        end = 50,
                    ),
                    prelude = Prelude.Selector(
                        components = listOf(
                            SelectorListItem(
                                location = CssLocation(
                                    source = content.substring(0, 12),
                                    start = 0,
                                    end = 12,
                                ),
                                selectors = listOf(
                                    Selector.Type(
                                        location = CssLocation(
                                            source = content.substring(0, 3),
                                            start = 0,
                                            end = 3,
                                        ),
                                        name = "div",
                                        combinator = CssCombinator.DescendantCombinator,
                                    ),
                                    Selector.Class(
                                        location = CssLocation(
                                            source = content.substring(4, 10),
                                            start = 4,
                                            end = 10,
                                        ),
                                        name = "child",
                                        combinator = CssCombinator.DescendantCombinator,
                                    ),
                                    Selector.Type(
                                        location = CssLocation(
                                            source = content.substring(11, 12),
                                            start = 11,
                                            end = 12,
                                        ),
                                        name = "a",
                                    ),
                                ),
                            ),
                            SelectorListItem(
                                location = CssLocation(
                                    source = content.substring(14, 27),
                                    start = 14,
                                    end = 27,
                                ),
                                selectors = listOf(
                                    Selector.Type(
                                        location = CssLocation(
                                            source = content.substring(14, 15),
                                            start = 14,
                                            end = 15,
                                        ),
                                        name = "a",
                                        combinator = CssCombinator.DescendantCombinator,
                                    ),
                                    Selector.Class(
                                        location = CssLocation(
                                            source = content.substring(16, 22),
                                            start = 16,
                                            end = 22,
                                        ),
                                        name = "child",
                                        combinator = CssCombinator.DescendantCombinator,
                                    ),
                                    Selector.Type(
                                        location = CssLocation(
                                            source = content.substring(23, 27),
                                            start = 23,
                                            end = 27,
                                        ),
                                        name = "span",
                                    ),
                                ),
                            ),
                        ),
                    ),
                    block = Block.SimpleBlock(
                        location = CssLocation(
                            source = content.substring(28, 50),
                            start = 28,
                            end = 50,
                        ),
                        children = listOf(
                            Declaration(
                                location = CssLocation(
                                    source = content.substring(34, 48),
                                    start = 34,
                                    end = 48,
                                ),
                                important = false,
                                property = "display",
                                values = listOf(
                                    Value.Identifier(
                                        location = CssLocation(
                                            source = content.substring(43, 47),
                                            start = 43,
                                            end = 47,
                                        ),
                                        name = "flex",
                                    ),
                                ),
                            ),
                        ),
                    ),
                ),
            ),
        )
        assert(content, tokens, expected)
    }

    @Test
    fun `parse rules with aggregate selectors using identifiers and class`() {
        val content = """
            |div span label .my-class {
            |    color: blue;
            |}
        """.trimMargin()
        val tokens = listOf(
            Token(kind = CssTokenKind.Ident, startOffset = 0, endOffset = 3),               // 'div'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 3, endOffset = 4),          // ' '
            Token(kind = CssTokenKind.Ident, startOffset = 4, endOffset = 8),               // 'span'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 8, endOffset = 9),          // ' '
            Token(kind = CssTokenKind.Ident, startOffset = 9, endOffset = 14),              // 'label'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 14, endOffset = 15),        // ' '
            Token(kind = CssTokenKind.Dot, startOffset = 15, endOffset = 16),               // '.'
            Token(kind = CssTokenKind.Ident, startOffset = 16, endOffset = 24),             // 'my-class'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 24, endOffset = 25),        // ' '
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 25, endOffset = 26),    // '{'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 26, endOffset = 31),        // '\n    '
            Token(kind = CssTokenKind.Ident, startOffset = 31, endOffset = 36),             // 'color'
            Token(kind = CssTokenKind.Colon, startOffset = 36, endOffset = 37),             // ':'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 37, endOffset = 38),        // ' '
            Token(kind = CssTokenKind.Ident, startOffset = 38, endOffset = 42),             // 'blue'
            Token(kind = CssTokenKind.Semicolon, startOffset = 42, endOffset = 43),         // ';'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 43, endOffset = 44),        // '\n'
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 44, endOffset = 45),   // '}'
            Token(kind = CssTokenKind.EndOfFile, startOffset = 45, endOffset = 45),
        )
        val expected = StyleSheet(
            location = CssLocation(
                source = content.substring(0, 45),
                start = 0,
                end = 45,
            ),
            children = listOf(
                QualifiedRule(
                    location = CssLocation(
                        source = content.substring(0, 45),
                        start = 0,
                        end = 45,
                    ),
                    prelude = Prelude.Selector(
                        components = listOf(
                            SelectorListItem(
                                location = CssLocation(
                                    source = content.substring(0, 24),
                                    start = 0,
                                    end = 24,
                                ),
                                selectors = listOf(
                                    Selector.Type(
                                        location = CssLocation(
                                            source = content.substring(0, 3),
                                            start = 0,
                                            end = 3,
                                        ),
                                        name = "div",
                                        combinator = CssCombinator.DescendantCombinator,
                                    ),
                                    Selector.Type(
                                        location = CssLocation(
                                            source = content.substring(4, 8),
                                            start = 4,
                                            end = 8,
                                        ),
                                        name = "span",
                                        combinator = CssCombinator.DescendantCombinator,
                                    ),
                                    Selector.Type(
                                        location = CssLocation(
                                            source = content.substring(9, 14),
                                            start = 9,
                                            end = 14,
                                        ),
                                        name = "label",
                                        combinator = CssCombinator.DescendantCombinator,
                                    ),
                                    Selector.Class(
                                        location = CssLocation(
                                            source = content.substring(15, 24),
                                            start = 15,
                                            end = 24,
                                        ),
                                        name = "my-class",
                                    ),
                                ),
                            ),
                        ),
                    ),
                    block = Block.SimpleBlock(
                        location = CssLocation(
                            source = content.substring(25, 45),
                            start = 25,
                            end = 45,
                        ),
                        children = listOf(
                            Declaration(
                                location = CssLocation(
                                    source = content.substring(31, 43),
                                    start = 31,
                                    end = 43,
                                ),
                                important = false,
                                property = "color",
                                values = listOf(
                                    Value.Identifier(
                                        location = CssLocation(
                                            source = content.substring(38, 42),
                                            start = 38,
                                            end = 42,
                                        ),
                                        name = "blue",
                                    ),
                                ),
                            ),
                        ),
                    ),
                ),
            ),
        )
        assert(content, tokens, expected)
    }

    @Test
    fun `parse css rule with margin with two values`() {
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
        val expected = StyleSheet(
            location = CssLocation(
                source = content.substring(0, 33),
                start = 0,
                end = 33,
            ),
            children = listOf(
                QualifiedRule(
                    location = CssLocation(
                        source = content.substring(0, 33),
                        start = 0,
                        end = 33,
                    ),
                    prelude = Prelude.Selector(
                        components = listOf(
                            SelectorListItem(
                                location = CssLocation(
                                    source = content.substring(0, 9),
                                    start = 0,
                                    end = 9,
                                ),
                                selectors = listOf(
                                    Selector.Class(
                                        location = CssLocation(
                                            source = content.substring(0, 9),
                                            start = 0,
                                            end = 9,
                                        ),
                                        name = "my-class",
                                    ),
                                ),
                            ),
                        ),
                    ),
                    block = Block.SimpleBlock(
                        location = CssLocation(
                            source = content.substring(10, 33),
                            start = 10,
                            end = 33,
                        ),
                        children = listOf(
                            Declaration(
                                location = CssLocation(
                                    source = content.substring(16, 31),
                                    start = 16,
                                    end = 31,
                                ),
                                important = false,
                                property = "margin",
                                values = listOf(
                                    Value.Number(
                                        location = CssLocation(
                                            source = content.substring(24, 25),
                                            start = 24,
                                            end = 25,
                                        ),
                                        value = "0",
                                    ),
                                    Value.Identifier(
                                        location = CssLocation(
                                            source = content.substring(26, 30),
                                            start = 26,
                                            end = 30,
                                        ),
                                        name = "auto",
                                    ),
                                ),
                            ),
                        ),
                    ),
                ),
            ),
        )
        assert(content, tokens, expected)
    }

    @Test
    @Burst
    fun `throw CssParserException when a BadUrl token is present`(
        // Arrange
        url: BadUrlParam,
    ) {
        println("url: $url")
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

        val parser = CssParser(consumers = CssConsumers(content))

        // Act
        val exception = assertFailsWith<AstParserException> {
            parser.parse(tokens)
        }

        // Assert
        val message = exception.message
        assertNotNull(message)
        assertContains(message, "Incomplete URL value: missing closing ')' in 'url(...)")
    }

    @Test
    fun `when invalid at-rule is present - throw CssParserException`() {
        // Arrange
        val content = """
            |@invalid-rule {
            |    display: none;
            |}
            """.trimMargin()
        val tokens = listOf(
            Token(kind = CssTokenKind.AtKeyword, startOffset = 0, endOffset = 14),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 14, endOffset = 15),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 15, endOffset = 16),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 16, endOffset = 21),
            Token(kind = CssTokenKind.Ident, startOffset = 21, endOffset = 28),
            Token(kind = CssTokenKind.Colon, startOffset = 28, endOffset = 29),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 29, endOffset = 30),
            Token(kind = CssTokenKind.Ident, startOffset = 30, endOffset = 34),
            Token(kind = CssTokenKind.Semicolon, startOffset = 34, endOffset = 35),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 35, endOffset = 36),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 36, endOffset = 37),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 37, endOffset = 37),
        )

        val parser = CssParser(consumers = CssConsumers(content))

        // Act
        val exception = assertFailsWith<AstParserException> {
            parser.parse(tokens)
        }

        // Assert
        val message = exception.message
        assertNotNull(message)
        assertContains(message, "Invalid at-rule: @invalid-rule")
    }

    private fun assert(
        content: String,
        tokens: List<Token<out CssTokenKind>>,
        expected: StyleSheet,
    ) {
        val astParser = CssParser(consumers = CssConsumers(content))
        val actual = astParser.parse(tokens)
        assertEquals(expected, actual)
    }

    @Suppress("unused", "EnumEntryName")
    enum class BadUrlParam(private val value: String) {
        `with an empty url`(""),
        `with a hashed url`("#abc"),
        `with a quoted url`("\"https://example.com\""),
        `with a space and quoted url`(" \"https://example.com\" "),
        `with an unquoted url`("https://example.com"),
        `with a spaced and unquoted url`(" https://example.com"),
        `with a leading spaced unquote url`("               https://example.com"),
        ;

        override fun toString(): String = value
    }
}
