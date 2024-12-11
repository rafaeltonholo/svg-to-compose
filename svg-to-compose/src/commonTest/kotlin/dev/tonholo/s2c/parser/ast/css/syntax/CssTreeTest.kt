package dev.tonholo.s2c.parser.ast.css.syntax

import dev.tonholo.s2c.parser.ast.css.CssCombinator
import dev.tonholo.s2c.parser.ast.css.syntax.node.AtRule
import dev.tonholo.s2c.parser.ast.css.syntax.node.AtRulePrelude
import dev.tonholo.s2c.parser.ast.css.syntax.node.Block
import dev.tonholo.s2c.parser.ast.css.syntax.node.SelectorListItem
import dev.tonholo.s2c.parser.ast.css.syntax.node.CssLocation
import dev.tonholo.s2c.parser.ast.css.syntax.node.Declaration
import dev.tonholo.s2c.parser.ast.css.syntax.node.Prelude
import dev.tonholo.s2c.parser.ast.css.syntax.node.QualifiedRule
import dev.tonholo.s2c.parser.ast.css.syntax.node.Selector
import dev.tonholo.s2c.parser.ast.css.syntax.node.Value
import kotlin.test.Test
import kotlin.test.assertEquals

class CssTreeTest {
    @Test
    fun `when pseudo-element is present should build a valid css tree`() {
        val location = CssLocation(
            source = "",
            start = 0,
            end = 0
        )
        val rule = QualifiedRule(
            location = location,
            prelude = Prelude.Selector(
                components = listOf(
                    SelectorListItem(
                        location = location,
                        selectors = listOf(
                            Selector.Type(
                                location = location,
                                name = "a",
                            ),
                            Selector.Id(
                                location = location,
                                name = "ref",
                            ),
                            Selector.PseudoElement(
                                location = location,
                                name = "after",
                                parameters = emptyList(),
                            )
                        ),
                    )
                ),
            ),
            block = Block.SimpleBlock(
                location = location,
                children = listOf(
                    Declaration(
                        location = location,
                        important = false,
                        property = "width",
                        values = listOf(
                            Value.Percentage(
                                location = location,
                                value = "100",
                            ),
                        ),
                    ),
                    Declaration(
                        location = location,
                        important = false,
                        property = "content",
                        values = listOf(
                            Value.String(
                                location = location,
                                value = " (",
                            ),
                            Value.Function(
                                location = location,
                                name = "attr",
                                arguments = listOf(
                                    Value.Identifier(
                                        location = location,
                                        name = "href",
                                    )
                                ),
                            ),
                            Value.String(
                                location = location,
                                value = ")",
                            ),
                        ),
                    ),
                ),
            ),
        )

        val expected = """
            |a#ref::after {
            |  width: 100%;
            |  content: ' (' attr(href) ')';
            |}
        """.trimMargin()
        val actual = rule.toString(indent = 0)
        println(actual)
        assertEquals(expected, actual)
    }

    @Test
    fun `when at-rule is present should build a valid css tree`() {
        val location = CssLocation(
            source = "",
            start = 0,
            end = 0
        )
        val rule = AtRule(
            location = location,
            name = "media",
            prelude = Prelude.AtRule(
                components = listOf(
                    AtRulePrelude(
                        location = location,
                        value = "screen and (min-width: 768px)",
                    ),
                ),
            ),
            block = Block.SimpleBlock(
                location = location,
                children = listOf(
                    QualifiedRule(
                        location = location,
                        prelude = Prelude.Selector(
                            components = listOf(
                                SelectorListItem(
                                    location = location,
                                    selectors = listOf(
                                        Selector.Type(
                                            location = location,
                                            name = "body",
                                            combinator = null,
                                        ),
                                        Selector.Class(
                                            location = location,
                                            name = "homepage",
                                            combinator = CssCombinator.DescendantCombinator,
                                        ),
                                        Selector.Class(
                                            location = location,
                                            name = "main-content",
                                            combinator = CssCombinator.ChildCombinator,
                                        ),
                                        Selector.Type(
                                            location = location,
                                            name = "section",
                                            combinator = null,
                                        ),
                                        Selector.PseudoClass(
                                            location = location,
                                            name = "first-of-type",
                                            parameters = emptyList(),
                                        ),
                                        Selector.PseudoClass(
                                            location = location,
                                            name = "not",
                                            parameters = listOf(
                                                Selector.Class(
                                                    location = location,
                                                    name = "hidden",
                                                    combinator = null,
                                                ),
                                            ),
                                        ),
                                    ),
                                ),
                            ),
                        ),
                        block = Block.SimpleBlock(
                            location = location,
                            children = listOf(
                                Declaration(
                                    location = location,
                                    important = false,
                                    property = "display",
                                    values = listOf(
                                        Value.Identifier(
                                            location = location,
                                            name = "flex",
                                        ),
                                    ),
                                ),
                                Declaration(
                                    location = location,
                                    important = false,
                                    property = "flex-direction",
                                    values = listOf(
                                        Value.Identifier(
                                            location = location,
                                            name = "column",
                                        ),
                                    ),
                                ),
                                Declaration(
                                    location = location,
                                    important = false,
                                    property = "align-items",
                                    values = listOf(
                                        Value.Identifier(
                                            location = location,
                                            name = "center",
                                        ),
                                    ),
                                ),
                                Declaration(
                                    location = location,
                                    important = false,
                                    property = "background-color",
                                    values = listOf(
                                        Value.Color(
                                            location = location,
                                            value = "rgba(255, 255, 255, 0.8)",
                                        ),
                                    ),
                                ),
                                Declaration(
                                    location = location,
                                    important = false,
                                    property = "box-shadow",
                                    values = listOf(
                                        Value.Number(
                                            location = location,
                                            value = "0",
                                        ),
                                        Value.Dimension(
                                            location = location,
                                            value = "4",
                                            unit = "px",
                                        ),
                                        Value.Dimension(
                                            location = location,
                                            value = "6",
                                            unit = "px",
                                        ),
                                        Value.Color(
                                            location = location,
                                            value = "rgba(0, 0, 0, 0.1)",
                                        ),
                                    ),
                                ),
                            ),
                        ),
                    ),
                ),
            ),
        )
        val expected = """
            |@media screen and (min-width: 768px) {
            |  body.homepage .main-content > section:first-of-type:not(.hidden) {
            |    display: flex;
            |    flex-direction: column;
            |    align-items: center;
            |    background-color: rgba(255, 255, 255, 0.8);
            |    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            |  }
            |}
        """.trimMargin()
        val actual = rule.toString(indent = 0)
        println(actual)
        assertEquals(expected, actual)
    }

    @Test
    fun `when multiples selectors for one rule is present should build a valid css tree`() {
        val location = CssLocation(
            source = "",
            start = 0,
            end = 0
        )
        val rule = QualifiedRule(
            location = location,
            prelude = Prelude.Selector(
                components = listOf(
                    SelectorListItem(
                        location = location,
                        selectors = listOf(
                            Selector.Type(
                                location = location,
                                name = "div",
                                combinator = CssCombinator.DescendantCombinator,
                            ),
                            Selector.Class(
                                location = location,
                                name = "child",
                                combinator = CssCombinator.DescendantCombinator,
                            ),
                            Selector.Type(
                                location = location,
                                name = "a",
                            ),
                        ),
                    ),
                    SelectorListItem(
                        location = location,
                        selectors = listOf(
                            Selector.Type(
                                location = location,
                                name = "a",
                                combinator = CssCombinator.DescendantCombinator,
                            ),
                            Selector.Class(
                                location = location,
                                name = "child",
                                combinator = CssCombinator.DescendantCombinator,
                            ),
                            Selector.Type(
                                location = location,
                                name = "span",
                            ),
                        ),
                    ),
                ),
            ),
            block = Block.SimpleBlock(
                location = location,
                children = listOf(
                    Declaration(
                        location = location,
                        important = false,
                        property = "display",
                        values = listOf(
                            Value.Identifier(
                                location = location,
                                name = "flex",
                            ),
                        ),
                    ),
                    Declaration(
                        location = location,
                        important = false,
                        property = "background",
                        values = listOf(
                            Value.Url(
                                location = location,
                                value = "#abc",
                            ),
                        ),
                    )
                ),
            )
        )
        val expected = """
            |div .child a, a .child span {
            |  display: flex;
            |  background: url(#abc);
            |}
        """.trimMargin()
        val actual = rule.toString(indent = 0)
        println(actual)
        assertEquals(expected, actual)
    }

    @Test
    fun `when attribute selector is present should build a valid css tree`() {
        val location = CssLocation(
            source = "",
            start = 0,
            end = 0,
        )
        val expected = """
            |input[type="password"]:not(:valid) {
            |  color: red;
            |}
        """.trimMargin()

        val rule = QualifiedRule(
            location = location,
            prelude = Prelude.Selector(
                components = listOf(
                    SelectorListItem(
                        location = location,
                        selectors = listOf(
                            Selector.Type(
                                location = location,
                                name = "input",
                            ),
                            Selector.Attribute(
                                location = location,
                                name = "type",
                                matcher = "=",
                                value = "password",
                            ),
                            Selector.PseudoClass(
                                location = location,
                                name = "not",
                                parameters = listOf(
                                    Selector.PseudoClass(
                                        location = location,
                                        name = "valid",
                                        parameters = emptyList(),
                                    ),
                                ),
                            ),
                        ),
                    ),
                ),
            ),
            block = Block.SimpleBlock(
                location = location,
                children = listOf(
                    Declaration(
                        location = location,
                        important = false,
                        property = "color",
                        values = listOf(
                            Value.Identifier(
                                location = location,
                                name = "red",
                            ),
                        ),
                    ),
                ),
            ),
        )

        val actual = rule.toString(indent = 0)
        println(actual)
        assertEquals(expected, actual)
    }
}
