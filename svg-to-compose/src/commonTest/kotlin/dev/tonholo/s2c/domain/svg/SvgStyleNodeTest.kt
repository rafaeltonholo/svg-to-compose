package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlRootNode
import dev.tonholo.s2c.domain.xml.XmlTextNode
import dev.tonholo.s2c.parser.ast.css.CssParser
import dev.tonholo.s2c.parser.ast.css.consumer.CssConsumers
import dev.tonholo.s2c.parser.ast.css.syntax.AstParserException
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
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SvgStyleNodeTest {

    @Test
    fun `given a style tag with no children nodes - when resolving the css tree - then must throw an exception`() {
        val style = SvgStyleNode(
            parent = XmlRootNode(children = mutableSetOf()),
            children = mutableSetOf(),
            attributes = mutableMapOf(),
        )
        val parser = CssParser(consumers = CssConsumers(style.content))

        assertEquals("", style.content)
        val exception = assertFailsWith<IllegalStateException> {
            style.resolveTree(parser = parser)
        }
        assertEquals("Style node content is empty.", exception.message)
    }

    @Test
    fun `given a style tag with an empty text node - when resolving the css tree - then must throw an exception`() {
        val children = mutableSetOf<XmlNode>()
        val style = SvgStyleNode(
            parent = XmlRootNode(children = mutableSetOf()),
            children = children,
            attributes = mutableMapOf(),
        )
        val textNode = XmlTextNode(parent = style, content = "")
        children.add(textNode)

        val parser = CssParser(consumers = CssConsumers(style.content))

        assertEquals("", style.content)
        val exception = assertFailsWith<IllegalStateException> {
            style.resolveTree(parser = parser)
        }
        assertEquals("Style node content is empty.", exception.message)
    }

    @Test
    fun `given a style tag with a text node containing a simple css rule - when resolving the css tree - then must return the css tree`() {
        val children = mutableSetOf<XmlNode>()
        val style = SvgStyleNode(
            parent = XmlRootNode(children = mutableSetOf()),
            children = children,
            attributes = mutableMapOf(),
        )
        val styleContent = "body { color: red; }"
        val textNode = XmlTextNode(parent = style, content = styleContent)
        children.add(textNode)

        val parser = CssParser(consumers = CssConsumers(style.content))
        val expectedTree = StyleSheet(
            location = CssLocation(
                source = style.content,
                start = 0,
                end = style.content.length,
            ),
            children = listOf(
                QualifiedRule(
                    location = CssLocation(
                        source = "body { color: red; }",
                        start = 0,
                        end = 20,
                    ),
                    prelude = Prelude.Selector(
                        components = listOf(
                            SelectorListItem(
                                location = CssLocation(
                                    source = "body",
                                    start = 0,
                                    end = 4,
                                ),
                                selectors = listOf(
                                    Selector.Type(
                                        location = CssLocation(
                                            source = "body",
                                            start = 0,
                                            end = 4,
                                        ),
                                        name = "body",
                                    ),
                                ),
                            ),
                        ),
                    ),
                    block = Block.SimpleBlock(
                        location = CssLocation(
                            source = "{ color: red; }",
                            start = 5,
                            end = 20,
                        ),
                        children = listOf(
                            Declaration(
                                location = CssLocation(
                                    source = "color: red;",
                                    start = 7,
                                    end = 18,
                                ),
                                property = "color",
                                important = false,
                                values = listOf(
                                    Value.Identifier(
                                        location = CssLocation(
                                            source = "red",
                                            start = 14,
                                            end = 17,
                                        ),
                                        name = "red",
                                    ),
                                ),
                            ),
                        ),
                    )
                )
            ),
        )

        assertEquals(styleContent, style.content)
        val tree = style.resolveTree(parser = parser)
        assertEquals(expectedTree, tree)
    }

    @Test
    fun `given a style tag with a text node containing multiple css rules - when resolving the css tree - then must return the css tree`() {
        val children = mutableSetOf<XmlNode>()
        val style = SvgStyleNode(
            parent = XmlRootNode(children = mutableSetOf()),
            children = children,
            attributes = mutableMapOf(),
        )
        val styleContent = "body { color: red; } p { margin: 0; }"
        val textNode = XmlTextNode(parent = style, content = styleContent)
        children.add(textNode)

        val parser = CssParser(consumers = CssConsumers(style.content))
        val expectedTree = StyleSheet(
            location = CssLocation(
                source = style.content,
                start = 0,
                end = style.content.length,
            ),
            children = listOf(
                QualifiedRule(
                    location = CssLocation(
                        source = "body { color: red; }",
                        start = 0,
                        end = 20,
                    ),
                    prelude = Prelude.Selector(
                        components = listOf(
                            SelectorListItem(
                                location = CssLocation(
                                    source = "body",
                                    start = 0,
                                    end = 4,
                                ),
                                selectors = listOf(
                                    Selector.Type(
                                        location = CssLocation(
                                            source = "body",
                                            start = 0,
                                            end = 4,
                                        ),
                                        name = "body",
                                    ),
                                ),
                            ),
                        ),
                    ),
                    block = Block.SimpleBlock(
                        location = CssLocation(
                            source = "{ color: red; }",
                            start = 5,
                            end = 20,
                        ),
                        children = listOf(
                            Declaration(
                                location = CssLocation(
                                    source = "color: red;",
                                    start = 7,
                                    end = 18,
                                ),
                                property = "color",
                                important = false,
                                values = listOf(
                                    Value.Identifier(
                                        location = CssLocation(
                                            source = "red",
                                            start = 14,
                                            end = 17,
                                        ),
                                        name = "red",
                                    ),
                                ),
                            ),
                        ),
                    )
                ),
                QualifiedRule(
                    location = CssLocation(
                        source = "p { margin: 0; }",
                        start = 21,
                        end = 37,
                    ),
                    prelude = Prelude.Selector(
                        components = listOf(
                            SelectorListItem(
                                location = CssLocation(
                                    source = "p",
                                    start = 21,
                                    end = 22,
                                ),
                                selectors = listOf(
                                    Selector.Type(
                                        location = CssLocation(
                                            source = "p",
                                            start = 21,
                                            end = 22,
                                        ),
                                        name = "p",
                                    ),
                                ),
                            ),
                        ),
                    ),
                    block = Block.SimpleBlock(
                        location = CssLocation(
                            source = "{ margin: 0; }",
                            start = 23,
                            end = 37,
                        ),
                        children = listOf(
                            Declaration(
                                location = CssLocation(
                                    source = "margin: 0;",
                                    start = 25,
                                    end = 35,
                                ),
                                property = "margin",
                                important = false,
                                values = listOf(
                                    Value.Number(
                                        location = CssLocation(
                                            source = "0",
                                            start = 33,
                                            end = 34,
                                        ),
                                        value = "0",
                                    ),
                                ),
                            ),
                        ),
                    )
                )
            ),
        )

        assertEquals(styleContent, style.content)
        val tree = style.resolveTree(parser = parser)
        assertEquals(expectedTree, tree)
    }

    @Test
    fun `given a style tag with a text node containing an invalid css rule - when resolving the css tree - then must throw an exception`() {
        val children = mutableSetOf<XmlNode>()
        val style = SvgStyleNode(
            parent = XmlRootNode(children = mutableSetOf()),
            children = children,
            attributes = mutableMapOf(),
        )
        val styleContent = "body { color: "
        val textNode = XmlTextNode(parent = style, content = styleContent)
        children.add(textNode)

        val parser = CssParser(consumers = CssConsumers(style.content))

        assertEquals(styleContent, style.content)
        val exception = assertFailsWith<AstParserException> {
            style.resolveTree(parser = parser)
        }
        assertContains(exception.message, "Parser error")
    }

    @Test
    fun `given a style tag with a text node containing nested css rules - when resolving the css tree - then must return the css tree`() {
        val children = mutableSetOf<XmlNode>()
        val style = SvgStyleNode(
            parent = XmlRootNode(children = mutableSetOf()),
            children = children,
            attributes = mutableMapOf(),
        )
        val styleContent = "@media screen { body { color: red; } }"
        val textNode = XmlTextNode(parent = style, content = styleContent)
        children.add(textNode)

        val parser = CssParser(consumers = CssConsumers(style.content))
        val expectedTree = StyleSheet(
            location = CssLocation(
                source = style.content,
                start = 0,
                end = style.content.length,
            ),
            children = listOf(
                AtRule(
                    location = CssLocation(
                        source = "@media screen { body { color: red; } }",
                        start = 0,
                        end = 38,
                    ),
                    prelude = Prelude.AtRule(
                        components = listOf(
                            AtRulePrelude(
                                location = CssLocation(
                                    source = "screen",
                                    start = 7,
                                    end = 13,
                                ),
                                value = "screen",
                            ),
                        ),
                    ),
                    name = "@media",
                    block = Block.SimpleBlock(
                        location = CssLocation(
                            source = "{ body { color: red; } }",
                            start = 14,
                            end = 38,
                        ),
                        children = listOf(
                            QualifiedRule(
                                location = CssLocation(
                                    source = "body { color: red; }",
                                    start = 16,
                                    end = 36,
                                ),
                                prelude = Prelude.Selector(
                                    components = listOf(
                                        SelectorListItem(
                                            location = CssLocation(
                                                source = "body",
                                                start = 16,
                                                end = 20,
                                            ),
                                            selectors = listOf(
                                                Selector.Type(
                                                    location = CssLocation(
                                                        source = "body",
                                                        start = 16,
                                                        end = 20,
                                                    ),
                                                    name = "body",
                                                ),
                                            ),
                                        ),
                                    ),
                                ),
                                block = Block.SimpleBlock(
                                    location = CssLocation(
                                        source = "{ color: red; }",
                                        start = 21,
                                        end = 36,
                                    ),
                                    children = listOf(
                                        Declaration(
                                            location = CssLocation(
                                                source = "color: red;",
                                                start = 23,
                                                end = 34,
                                            ),
                                            property = "color",
                                            important = false,
                                            values = listOf(
                                                Value.Identifier(
                                                    location = CssLocation(
                                                        source = "red",
                                                        start = 30,
                                                        end = 33,
                                                    ),
                                                    name = "red",
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

        assertEquals(styleContent, style.content)
        val tree = style.resolveTree(parser = parser)
        assertEquals(expectedTree, tree)
    }
}
