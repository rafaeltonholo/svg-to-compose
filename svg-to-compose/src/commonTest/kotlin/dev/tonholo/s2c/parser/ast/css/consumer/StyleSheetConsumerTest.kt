package dev.tonholo.s2c.parser.ast.css.consumer

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.css.CssCombinator
import dev.tonholo.s2c.parser.ast.css.syntax.CssIterator
import dev.tonholo.s2c.parser.ast.css.syntax.node.AtRule
import dev.tonholo.s2c.parser.ast.css.syntax.node.AtRulePrelude
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
import kotlin.test.assertEquals

class StyleSheetConsumerTest {
    @Test
    fun `when multiple selectors separated by comma are present - then create StyleSheet with one QualifiedRule with multiple selectors`() {
        // Arrange
        val tokens = listOf(
            Token(kind = CssTokenKind.Hash, startOffset = 0, endOffset = 1),
            Token(kind = CssTokenKind.Ident, startOffset = 1, endOffset = 8),
            Token(kind = CssTokenKind.Comma, startOffset = 8, endOffset = 9),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 9, endOffset = 10),
            Token(kind = CssTokenKind.Dot, startOffset = 10, endOffset = 11),
            Token(kind = CssTokenKind.Ident, startOffset = 11, endOffset = 19),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 19, endOffset = 20),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 20, endOffset = 21),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 21, endOffset = 24),
            Token(kind = CssTokenKind.Ident, startOffset = 24, endOffset = 33),
            Token(kind = CssTokenKind.Colon, startOffset = 33, endOffset = 34),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 34, endOffset = 35),
            Token(kind = CssTokenKind.Url, startOffset = 35, endOffset = 53),
            Token(kind = CssTokenKind.Semicolon, startOffset = 53, endOffset = 54),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 54, endOffset = 55),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 55, endOffset = 56),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 56, endOffset = 56),
        )
        val content = """
            |#my-rule, .my-class {
            |  clip-path: url(#my-clip-path);
            |}
            """.trimMargin()
        val expected = StyleSheet(
            location = CssLocation(content, start = 0, end = content.length),
            children = listOf(
                QualifiedRule(
                    location = CssLocation(content, start = 0, end = content.length),
                    prelude = Prelude.Selector(
                        components = listOf(
                            SelectorListItem(
                                location = CssLocation(content.substring(0, 8), start = 0, end = 8),
                                selectors = listOf(
                                    Selector.Id(
                                        location = CssLocation(content.substring(0, 8), start = 0, end = 8),
                                        name = "my-rule",
                                    ),
                                ),
                            ),
                            SelectorListItem(
                                location = CssLocation(content.substring(10, 19), start = 10, end = 19),
                                selectors = listOf(
                                    Selector.Class(
                                        location = CssLocation(content.substring(10, 19), start = 10, end = 19),
                                        name = "my-class",
                                    ),
                                ),
                            ),
                        ),
                    ),
                    block = Block.SimpleBlock(
                        location = CssLocation(content.substring(20), start = 20, end = content.length),
                        children = listOf(
                            Declaration(
                                location = CssLocation(content.substring(24, 54), start = 24, end = 54),
                                property = "clip-path",
                                values = listOf(
                                    Value.Url(
                                        location = CssLocation(content.substring(39, 52), start = 39, end = 52),
                                        value = "#my-clip-path"
                                    )
                                ),
                                important = false,
                            ),
                        ),
                    ),
                )
            ),
        )
        val consumer = buildStyleSheetConsumer(content = content)
        val iterator = CssIterator(tokens)

        // Act
        val actual = consumer.consume(iterator)
        println("tree: $actual")
        val actualString = actual.toString(indent = 0)
        println(actualString)

        // Arrange
        assertEquals(expected, actual)
        assertEquals(content, actualString)
    }

    @Test
    fun `when at-rule is present - then create StyleSheet with one AtRule`() {
        // Arrange
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
                kind = CssTokenKind.Dimension,
                startOffset = 30,
                endOffset = 35,
            ),          // '768px'
            Token(kind = CssTokenKind.CloseParenthesis, startOffset = 35, endOffset = 36),   // ')'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 36, endOffset = 37),         // ' '
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 37, endOffset = 38),     // '{'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 38, endOffset = 43),         // '\n    '

            // body.homepage .main-content > section:first-of-type:not(.hidden) {
            Token(kind = CssTokenKind.Ident, startOffset = 43, endOffset = 47),              // 'body'
            Token(kind = CssTokenKind.Dot, startOffset = 47, endOffset = 48),                // '.'
            Token(kind = CssTokenKind.Ident, startOffset = 48, endOffset = 56),              // 'homepage'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 56, endOffset = 57),         // ' '
            Token(kind = CssTokenKind.Dot, startOffset = 57, endOffset = 58),                // '.'
            Token(kind = CssTokenKind.Ident, startOffset = 58, endOffset = 70),              // 'main-content'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 70, endOffset = 71),         // ' '
            Token(kind = CssTokenKind.Greater, startOffset = 71, endOffset = 72),            // '>'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 72, endOffset = 73),         // ' '
            Token(kind = CssTokenKind.Ident, startOffset = 73, endOffset = 80),              // 'section'
            Token(kind = CssTokenKind.Colon, startOffset = 80, endOffset = 81),              // ':'
            Token(kind = CssTokenKind.Ident, startOffset = 81, endOffset = 94),              // 'first-of-type'
            Token(kind = CssTokenKind.Colon, startOffset = 94, endOffset = 95),              // ':'
            Token(kind = CssTokenKind.Ident, startOffset = 95, endOffset = 98),              // 'not'
            Token(kind = CssTokenKind.OpenParenthesis, startOffset = 98, endOffset = 99),    // '('
            Token(kind = CssTokenKind.Dot, startOffset = 99, endOffset = 100),               // '.'
            Token(kind = CssTokenKind.Ident, startOffset = 100, endOffset = 106),            // 'hidden'
            Token(kind = CssTokenKind.CloseParenthesis, startOffset = 106, endOffset = 107), // ')'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 107, endOffset = 108),       // ' '
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 108, endOffset = 109),   // '{'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 109, endOffset = 118),       // '\n        '

            // Declaration: display: flex;
            Token(kind = CssTokenKind.Ident, startOffset = 118, endOffset = 125),            // 'display'
            Token(kind = CssTokenKind.Colon, startOffset = 125, endOffset = 126),            // ':'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 126, endOffset = 127),       // ' '
            Token(kind = CssTokenKind.Ident, startOffset = 127, endOffset = 131),            // 'flex'
            Token(kind = CssTokenKind.Semicolon, startOffset = 131, endOffset = 132),        // ';'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 132, endOffset = 141),       // '\n        '

            /* Declaration: flex-direction: column; */
            Token(kind = CssTokenKind.Ident, startOffset = 141, endOffset = 155),            // 'flex-direction'
            Token(kind = CssTokenKind.Colon, startOffset = 155, endOffset = 156),            // ':'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 156, endOffset = 157),       // ' '
            Token(kind = CssTokenKind.Ident, startOffset = 157, endOffset = 163),            // 'column'
            Token(kind = CssTokenKind.Semicolon, startOffset = 163, endOffset = 164),        // ';'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 164, endOffset = 173),       // '\n        '

            /* Declaration: align-items: center; */
            Token(kind = CssTokenKind.Ident, startOffset = 173, endOffset = 184),            // 'align-items'
            Token(kind = CssTokenKind.Colon, startOffset = 184, endOffset = 185),            // ':'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 185, endOffset = 186),       // ' '
            Token(kind = CssTokenKind.Ident, startOffset = 186, endOffset = 192),            // 'center'
            Token(kind = CssTokenKind.Semicolon, startOffset = 192, endOffset = 193),        // ';'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 193, endOffset = 202),       // '\n        '

            /* Declaration: background-color: rgba(255, 255, 255, 0.8); */
            Token(kind = CssTokenKind.Ident, startOffset = 202, endOffset = 218),            // 'background-color'
            Token(kind = CssTokenKind.Colon, startOffset = 218, endOffset = 219),            // ':'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 219, endOffset = 220),       // ' '
            Token(kind = CssTokenKind.Function, startOffset = 220, endOffset = 224),         // 'rgba'
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
                kind = CssTokenKind.Dimension,
                startOffset = 268,
                endOffset = 271,
            ),                                                                               // '4px'
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 271, endOffset = 272),       // ' '
            Token(
                kind = CssTokenKind.Dimension,
                startOffset = 272,
                endOffset = 275,
            ),                                                                               // '6px'
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
        val iterator = CssIterator(tokens)
        val consumer = buildStyleSheetConsumer(content = content)
        val expected = StyleSheet(
            location = CssLocation(content, start = 0, end = 303),
            children = listOf(
                AtRule(
                    location = CssLocation(content, start = 0, end = 303),
                    name = "@media",
                    prelude = Prelude.AtRule(
                        components = listOf(
                            AtRulePrelude(
                                location = CssLocation(content.substring(7, 36), start = 7, end = 36),
                                value = "screen and (min-width: 768px)",
                            ),
                        ),
                    ),
                    block = Block.SimpleBlock(
                        location = CssLocation(content.substring(37), start = 37, end = 303),
                        children = listOf(
                            QualifiedRule(
                                location = CssLocation(content.substring(43, 301), start = 43, end = 301),
                                prelude = Prelude.Selector(
                                    components = listOf(
                                        SelectorListItem(
                                            location = CssLocation(content.substring(43, 107), start = 43, end = 107),
                                            selectors = listOf(
                                                Selector.Type(
                                                    location = CssLocation(
                                                        source = content.substring(43, 47),
                                                        start = 43,
                                                        end = 47
                                                    ),
                                                    name = "body",
                                                ),
                                                Selector.Class(
                                                    location = CssLocation(
                                                        source = content.substring(47, 56),
                                                        start = 47,
                                                        end = 56,
                                                    ),
                                                    name = "homepage",
                                                    combinator = CssCombinator.DescendantCombinator,
                                                ),
                                                Selector.Class(
                                                    location = CssLocation(
                                                        source = content.substring(57, 70),
                                                        start = 57,
                                                        end = 70,
                                                    ),
                                                    name = "main-content",
                                                    combinator = CssCombinator.ChildCombinator,
                                                ),
                                                Selector.Type(
                                                    location = CssLocation(
                                                        source = content.substring(73, 80),
                                                        start = 73,
                                                        end = 80,
                                                    ),
                                                    name = "section",
                                                ),
                                                Selector.PseudoClass(
                                                    location = CssLocation(
                                                        source = content.substring(80, 94),
                                                        start = 80,
                                                        end = 94,
                                                    ),
                                                    name = "first-of-type",
                                                    parameters = emptyList(),
                                                ),
                                                Selector.PseudoClass(
                                                    location = CssLocation(
                                                        source = content.substring(94, 107),
                                                        start = 94,
                                                        end = 107,
                                                    ),
                                                    name = "not",
                                                    parameters = listOf(
                                                        Selector.Class(
                                                            location = CssLocation(
                                                                source = content.substring(99, 106),
                                                                start = 99,
                                                                end = 106,
                                                            ),
                                                            name = "hidden",
                                                        ),
                                                    ),
                                                ),
                                            ),
                                        )
                                    ),
                                ),
                                block = Block.SimpleBlock(
                                    location = CssLocation(content.substring(108, 301), start = 108, end = 301),
                                    children = listOf(
                                        Declaration(
                                            location = CssLocation(content.substring(118, 132), start = 118, end = 132),
                                            property = "display",
                                            important = false,
                                            values = listOf(
                                                Value.Identifier(
                                                    location = CssLocation(
                                                        source = content.substring(127, 131),
                                                        start = 127,
                                                        end = 131,
                                                    ),
                                                    name = "flex",
                                                ),
                                            ),
                                        ),
                                        Declaration(
                                            location = CssLocation(
                                                source = content.substring(141, 164),
                                                start = 141,
                                                end = 164,
                                            ),
                                            property = "flex-direction",
                                            important = false,
                                            values = listOf(
                                                Value.Identifier(
                                                    location = CssLocation(
                                                        source = content.substring(157, 163),
                                                        start = 157,
                                                        end = 163,
                                                    ),
                                                    name = "column",
                                                ),
                                            ),
                                        ),
                                        Declaration(
                                            location = CssLocation(
                                                source = content.substring(173, 193),
                                                start = 173,
                                                end = 193,
                                            ),
                                            property = "align-items",
                                            important = false,
                                            values = listOf(
                                                Value.Identifier(
                                                    location = CssLocation(
                                                        source = content.substring(186, 192),
                                                        start = 186,
                                                        end = 192,
                                                    ),
                                                    name = "center",
                                                ),
                                            ),
                                        ),
                                        Declaration(
                                            location = CssLocation(
                                                source = content.substring(202, 245),
                                                start = 202,
                                                end = 245,
                                            ),
                                            property = "background-color",
                                            important = false,
                                            values = listOf(
                                                Value.Color(
                                                    location = CssLocation(
                                                        source = content.substring(220, 244),
                                                        start = 220,
                                                        end = 244,
                                                    ),
                                                    value = "rgba(255, 255, 255, 0.8)",
                                                ),
                                            ),
                                        ),
                                        Declaration(
                                            location = CssLocation(
                                                source = content.substring(254, 295),
                                                start = 254,
                                                end = 295,
                                            ),
                                            property = "box-shadow",
                                            important = false,
                                            values = listOf(
                                                Value.Number(
                                                    location = CssLocation(
                                                        source = content.substring(266, 267),
                                                        start = 266,
                                                        end = 267,
                                                    ),
                                                    value = "0",
                                                ),
                                                Value.Dimension(
                                                    location = CssLocation(
                                                        source = content.substring(268, 271),
                                                        start = 268,
                                                        end = 271,
                                                    ),
                                                    value = "4",
                                                    unit = "px",
                                                ),
                                                Value.Dimension(
                                                    location = CssLocation(
                                                        source = content.substring(272, 275),
                                                        start = 272,
                                                        end = 275,
                                                    ),
                                                    value = "6",
                                                    unit = "px",
                                                ),
                                                Value.Color(
                                                    location = CssLocation(
                                                        source = content.substring(276, 294),
                                                        start = 276,
                                                        end = 294,
                                                    ),
                                                    value = "rgba(0, 0, 0, 0.1)",
                                                ),
                                            ),
                                        ),
                                    ),
                                ),
                            ),
                        ),
                    ),
                ),
            ),
        )

        // Act
        val actual = consumer.consume(iterator)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `when two different rules are present - then create StyleSheet with two QualifiedRules`() {
        // Arrange
        val content = """
            |.my-class {
            |    background: #f0f;
            |    color: #000;
            |}
            |
            |#my-id {
            |    background: #0ff;
            |    margin-bottom: 1px;
            |}
            """.trimMargin()
        val tokens = listOf(
            Token(kind = CssTokenKind.Dot, startOffset = 0, endOffset = 1),
            Token(kind = CssTokenKind.Ident, startOffset = 1, endOffset = 9),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 9, endOffset = 10),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 10, endOffset = 11),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 11, endOffset = 16),
            Token(kind = CssTokenKind.Ident, startOffset = 16, endOffset = 26),
            Token(kind = CssTokenKind.Colon, startOffset = 26, endOffset = 27),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 27, endOffset = 28),
            Token(kind = CssTokenKind.Hash, startOffset = 28, endOffset = 29),
            Token(kind = CssTokenKind.HexDigit, startOffset = 29, endOffset = 32),
            Token(kind = CssTokenKind.Semicolon, startOffset = 32, endOffset = 33),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 33, endOffset = 38),
            Token(kind = CssTokenKind.Ident, startOffset = 38, endOffset = 43),
            Token(kind = CssTokenKind.Colon, startOffset = 43, endOffset = 44),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 44, endOffset = 45),
            Token(kind = CssTokenKind.Hash, startOffset = 45, endOffset = 46),
            Token(kind = CssTokenKind.HexDigit, startOffset = 46, endOffset = 49),
            Token(kind = CssTokenKind.Semicolon, startOffset = 49, endOffset = 50),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 50, endOffset = 51),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 51, endOffset = 52),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 52, endOffset = 54),

            Token(kind = CssTokenKind.Hash, startOffset = 54, endOffset = 55),
            Token(kind = CssTokenKind.Ident, startOffset = 55, endOffset = 60),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 60, endOffset = 61),
            Token(kind = CssTokenKind.OpenCurlyBrace, startOffset = 61, endOffset = 62),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 62, endOffset = 67),
            Token(kind = CssTokenKind.Ident, startOffset = 67, endOffset = 77),
            Token(kind = CssTokenKind.Colon, startOffset = 77, endOffset = 78),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 78, endOffset = 79),
            Token(kind = CssTokenKind.Hash, startOffset = 79, endOffset = 80),
            Token(kind = CssTokenKind.HexDigit, startOffset = 80, endOffset = 83),
            Token(kind = CssTokenKind.Semicolon, startOffset = 83, endOffset = 84),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 84, endOffset = 89),
            Token(kind = CssTokenKind.Ident, startOffset = 89, endOffset = 102),
            Token(kind = CssTokenKind.Colon, startOffset = 102, endOffset = 103),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 103, endOffset = 104),
            Token(kind = CssTokenKind.Dimension, startOffset = 104, endOffset = 107),
            Token(kind = CssTokenKind.Semicolon, startOffset = 107, endOffset = 108),
            Token(kind = CssTokenKind.WhiteSpace, startOffset = 108, endOffset = 109),
            Token(kind = CssTokenKind.CloseCurlyBrace, startOffset = 109, endOffset = 110),
            Token(kind = CssTokenKind.EndOfFile, startOffset = 110, endOffset = 110),
        )
        val consumer = buildStyleSheetConsumer(content = content)
        val iterator = CssIterator(tokens)
        val expected = StyleSheet(
            location = CssLocation(
                source = content,
                start = 0,
                end = content.length,
            ),
            children = listOf(
                QualifiedRule(
                    location = CssLocation(source = content.substring(0, 52), start = 0, end = 52),
                    prelude = Prelude.Selector(
                        components = listOf(
                            SelectorListItem(
                                location = CssLocation(source = content.substring(0, 9), start = 0, end = 9),
                                selectors = listOf(
                                    Selector.Class(
                                        location = CssLocation(source = content.substring(0, 9), start = 0, end = 9),
                                        name = "my-class",
                                    ),
                                ),
                            )
                        )
                    ),
                    block = Block.SimpleBlock(
                        location = CssLocation(source = content.substring(10, 52), start = 10, end = 52),
                        children = listOf(
                            Declaration(
                                location = CssLocation(source = content.substring(16, 33), start = 16, end = 33),
                                property = "background",
                                important = false,
                                values = listOf(
                                    Value.Color(
                                        location = CssLocation(
                                            source = content.substring(28, 32),
                                            start = 28,
                                            end = 32,
                                        ),
                                        value = "#f0f",
                                    ),
                                ),
                            ),
                            Declaration(
                                location = CssLocation(source = content.substring(38, 50), start = 38, end = 50),
                                property = "color",
                                important = false,
                                values = listOf(
                                    Value.Color(
                                        location = CssLocation(
                                            source = content.substring(45, 49),
                                            start = 45,
                                            end = 49,
                                        ),
                                        value = "#000",
                                    ),
                                ),
                            ),
                        ),
                    ),
                ),
                QualifiedRule(
                    location = CssLocation(source = content.substring(54), start = 54, end = 110),
                    prelude = Prelude.Selector(
                        components = listOf(
                            SelectorListItem(
                                location = CssLocation(source = content.substring(54, 60), start = 54, end = 60),
                                selectors = listOf(
                                    Selector.Id(
                                        location = CssLocation(
                                            source = content.substring(54, 60),
                                            start = 54,
                                            end = 60
                                        ),
                                        name = "my-id",
                                    ),
                                ),
                            ),
                        )
                    ),
                    block = Block.SimpleBlock(
                        location = CssLocation(
                            source = content.substring(61, content.length),
                            start = 61,
                            end = content.length,
                        ),
                        children = listOf(
                            Declaration(
                                location = CssLocation(source = content.substring(67, 84), start = 67, end = 84),
                                property = "background",
                                important = false,
                                values = listOf(
                                    Value.Color(
                                        location = CssLocation(
                                            source = content.substring(79, 83),
                                            start = 79,
                                            end = 83,
                                        ),
                                        value = "#0ff",
                                    ),
                                ),
                            ),
                            Declaration(
                                location = CssLocation(source = content.substring(89, 108), start = 89, end = 108),
                                property = "margin-bottom",
                                important = false,
                                values = listOf(
                                    Value.Dimension(
                                        location = CssLocation(
                                            source = content.substring(104, 107),
                                            start = 104,
                                            end = 107,
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

        // Act
        val actual = consumer.consume(iterator)

        // Assert
        assertEquals(expected, actual)
    }

    private fun buildStyleSheetConsumer(content: String): StyleSheetConsumer {
        val simpleSelectorConsumer = SimpleSelectorConsumer(
            content = content,
        )
        val valueConsumer = ValueConsumer(content = content)
        val declarationConsumer = DeclarationConsumer(
            content = content,
            valueConsumer = valueConsumer,
        )
        val selectorListItemConsumer = SelectorListItemConsumer(
            content = content,
            simpleSelectorConsumer = simpleSelectorConsumer,
        )
        val declarationBlockConsumer = SimpleDeclarationBlockConsumer(
            content = content,
            declarationConsumer = declarationConsumer,
        )

        val qualifiedRuleConsumer = QualifiedRuleConsumer(
            content = content,
            selectorListItemConsumer = selectorListItemConsumer,
            blockConsumer = declarationBlockConsumer,
        )

        val simpleRuleBlockConsumer = SimpleRuleBlockConsumer(
            content = content,
            qualifiedRuleConsumer = qualifiedRuleConsumer,
        )

        val atRuleConsumer = AtRuleConsumer(
            content = content,
            blockConsumer = simpleRuleBlockConsumer,
        )

        return StyleSheetConsumer(
            content = content,
            atRuleConsumer = atRuleConsumer,
            qualifiedRuleConsumer = qualifiedRuleConsumer,
        )
    }
}
