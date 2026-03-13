package dev.tonholo.s2c.parser.ast.css.elements

import app.cash.burst.Burst
import app.cash.burst.burstValues
import dev.tonholo.s2c.parser.ast.css.CssCombinator
import dev.tonholo.s2c.parser.ast.css.calculateSelectorsSpecificity
import dev.tonholo.s2c.parser.ast.css.syntax.node.CssLocation
import dev.tonholo.s2c.parser.ast.css.syntax.node.Prelude
import dev.tonholo.s2c.parser.ast.css.syntax.node.Selector
import dev.tonholo.s2c.parser.ast.css.syntax.node.SelectorListItem
import kotlin.test.Test
import kotlin.test.assertEquals

class CssSpecificityTest {
    private val location = CssLocation.Undefined

    @Test
    fun `when single universal selector then specificity is 0 0 0`() {
        // Arrange
        val prelude = Prelude.Selector(
            components = listOf(
                SelectorListItem(
                    location = location,
                    selectors = listOf(
                        Selector.Type(
                            location = location,
                            name = "*",
                        ),
                    ),
                ),
            ),
        )
        val expected = "[(0, 0, 0)]"
        // Act
        val actual = calculateSelectorsSpecificity(prelude).values
        // Assert
        assertEquals(expected, actual.toString())
    }

    @Test
    fun `when single id selector then specificity is 1 0 0`() {
        // Arrange
        val prelude = Prelude.Selector(
            components = listOf(
                SelectorListItem(
                    location = location,
                    selectors = listOf(
                        Selector.Id(
                            location = location,
                            name = "my-id",
                        ),
                    ),
                ),
            ),
        )
        val expected = "[(1, 0, 0)]"
        // Act
        val actual = calculateSelectorsSpecificity(prelude).values
        // Assert
        assertEquals(expected, actual.toString())
    }

    @Test
    fun `when single class selector then specificity is 0 1 0`() {
        // Arrange
        val prelude = Prelude.Selector(
            components = listOf(
                SelectorListItem(
                    location = location,
                    selectors = listOf(
                        Selector.Class(
                            location = location,
                            name = "my-class",
                        ),
                    ),
                ),
            ),
        )
        val expected = "[(0, 1, 0)]"
        // Act
        val actual = calculateSelectorsSpecificity(prelude).values
        // Assert
        assertEquals(expected, actual.toString())
    }

    @Test
    fun `when single tag selector then specificity is 0 0 1`() {
        // Arrange
        val prelude = Prelude.Selector(
            components = listOf(
                SelectorListItem(
                    location = location,
                    selectors = listOf(
                        Selector.Type(
                            location = location,
                            name = "div",
                        ),
                    ),
                ),
            ),
        )
        val expected = "[(0, 0, 1)]"
        // Act
        val actual = calculateSelectorsSpecificity(prelude).values
        // Assert
        assertEquals(expected, actual.toString())
    }

    @Test
    @Burst
    fun `when multiple ids selectors then specificity is n 0 0 where n is number of selectors`(
        // Arrange
        selectors: List<Selector> = burstValues(
            listOf(
                Selector.Id(location = CssLocation.Undefined, name = "my-id-0"),
                Selector.Id(location = CssLocation.Undefined, name = "my-id-1"),
            ),
            listOf(
                Selector.Id(location = CssLocation.Undefined, name = "my-id-0"),
                Selector.Id(location = CssLocation.Undefined, name = "my-id-1"),
                Selector.Id(location = CssLocation.Undefined, name = "my-id-2"),
            ),
            listOf(
                Selector.Id(location = CssLocation.Undefined, name = "my-id-0"),
                Selector.Id(location = CssLocation.Undefined, name = "my-id-1"),
                Selector.Id(location = CssLocation.Undefined, name = "my-id-2"),
                Selector.Id(location = CssLocation.Undefined, name = "my-id-3"),
            ),
        )
    ) {
        // Arrange (cont.)
        val prelude = Prelude.Selector(
            components = listOf(
                SelectorListItem(
                    location = location,
                    selectors = selectors,
                ),
            ),
        )
        val n = selectors.size
        val expected = "[($n, 0, 0)]"
        // Act
        val actual = calculateSelectorsSpecificity(prelude).values
        // Assert
        assertEquals(expected, actual.toString())
    }

    @Test
    @Burst
    fun `when multiple class selectors then specificity is 0 n 0 where n is number of selectors`(
        // Arrange
        selectors: List<Selector> = burstValues(
            listOf(
                Selector.Class(location = CssLocation.Undefined, name = "my-class-0"),
                Selector.Class(location = CssLocation.Undefined, name = "my-class-1"),
            ),
            listOf(
                Selector.Class(location = CssLocation.Undefined, name = "my-class-0"),
                Selector.Class(location = CssLocation.Undefined, name = "my-class-1"),
                Selector.Class(location = CssLocation.Undefined, name = "my-class-2"),
            ),
            listOf(
                Selector.Class(location = CssLocation.Undefined, name = "my-class-0"),
                Selector.Class(location = CssLocation.Undefined, name = "my-class-1"),
                Selector.Class(location = CssLocation.Undefined, name = "my-class-2"),
                Selector.Class(location = CssLocation.Undefined, name = "my-class-3"),
            ),
        )
    ) {
        // Arrange (cont.)
        val prelude = Prelude.Selector(
            components = listOf(
                SelectorListItem(
                    location = location,
                    selectors = selectors,
                ),
            ),
        )
        val n = selectors.size
        val expected = "[(0, $n, 0)]"
        // Act
        val actual = calculateSelectorsSpecificity(prelude).values
        // Assert
        assertEquals(expected, actual.toString())
    }

    @Test
    @Burst
    fun `when multiple tag selectors then specificity is 0 0 n where n is number of selectors`(
        // Arrange
        selectors: List<Selector> = burstValues(
            listOf(
                Selector.Type(location = CssLocation.Undefined, name = "ul"),
                Selector.Type(location = CssLocation.Undefined, name = "li"),
            ),
            listOf(
                Selector.Type(location = CssLocation.Undefined, name = "div"),
                Selector.Type(location = CssLocation.Undefined, name = "ul"),
                Selector.Type(location = CssLocation.Undefined, name = "li"),
            ),
            listOf(
                Selector.Type(location = CssLocation.Undefined, name = "div"),
                Selector.Type(location = CssLocation.Undefined, name = "section"),
                Selector.Type(location = CssLocation.Undefined, name = "ul"),
                Selector.Type(location = CssLocation.Undefined, name = "li"),
            ),
        )
    ) {
        // Arrange (cont.)
        val prelude = Prelude.Selector(
            components = listOf(
                SelectorListItem(
                    location = location,
                    selectors = selectors,
                ),
            ),
        )
        val n = selectors.size
        val expected = "[(0, 0, $n)]"
        // Act
        val actual = calculateSelectorsSpecificity(prelude).values
        // Assert
        assertEquals(expected, actual.toString())
    }

    @Test
    @Burst
    fun `should calculate specificity for multiple selectors`(
        // Arrange
        rules: Pair<List<Selector>, String> = burstValues(
            listOf(
                Selector.Type(location = CssLocation.Undefined, name = "li"),
                Selector.Class(location = CssLocation.Undefined, name = "my-class"),
                Selector.Class(location = CssLocation.Undefined, name = "my-second-class"),
            ) to "[(0, 2, 1)]",
            listOf(
                Selector.Type(
                    location = CssLocation.Undefined,
                    name = "ol",
                    combinator = CssCombinator.DescendantCombinator,
                ),
                Selector.Type(
                    location = CssLocation.Undefined,
                    name = "*",
                    combinator = CssCombinator.DescendantCombinator,
                ),
                Selector.Class(location = CssLocation.Undefined, name = "my-second-class"),
            ) to "[(0, 1, 1)]",
            listOf(
                Selector.Id(location = CssLocation.Undefined, name = "s12"),
                Selector.PseudoClass(
                    location = CssLocation.Undefined,
                    name = "not",
                    parameters = listOf(
                        Selector.Type(location = CssLocation.Undefined, name = "FOO")
                    ),
                ),
            ) to "[(1, 0, 1)]",
            listOf(
                Selector.Class(location = CssLocation.Undefined, name = "bar"),
                Selector.Id(location = CssLocation.Undefined, name = "baz"),
            ) to "[(1, 1, 0)]",
            listOf(
                Selector.Type(location = CssLocation.Undefined, name = "*"),
            ) to "[(0, 0, 0)]",
            listOf(
                Selector.Type(
                    location = CssLocation.Undefined,
                    name = "h1",
                    combinator = CssCombinator.SubsequentSiblingCombinator
                ),
                Selector.Type(location = CssLocation.Undefined, name = "*"),
                Selector.Attribute(
                    location = CssLocation.Undefined,
                    name = "rel",
                    matcher = "=",
                    value = "up",
                ),
            ) to "[(0, 1, 1)]",
        )
    ) {
        // Arrange (cont.)
        val (selectors, expected) = rules
        val prelude = Prelude.Selector(
            components = listOf(
                SelectorListItem(
                    location = location,
                    selectors = selectors,
                ),
            ),
        )
        // Act
        val actual = calculateSelectorsSpecificity(prelude).values
        // Assert
        assertEquals(expected, actual.toString())
    }
}
