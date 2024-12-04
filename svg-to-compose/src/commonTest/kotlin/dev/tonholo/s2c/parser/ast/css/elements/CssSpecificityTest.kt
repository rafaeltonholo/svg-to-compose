package dev.tonholo.s2c.parser.ast.css.elements

import app.cash.burst.Burst
import app.cash.burst.burstValues
import dev.tonholo.s2c.extensions.firstInstanceOf
import dev.tonholo.s2c.parser.ast.css.CssCombinator
import dev.tonholo.s2c.parser.ast.css.CssComponent
import dev.tonholo.s2c.parser.ast.css.CssComponentType
import dev.tonholo.s2c.parser.ast.css.CssQualifiedRule
import kotlin.test.Test
import kotlin.test.assertEquals

class CssSpecificityTest {
    @Test
    fun `when single universal selector then specificity is 0 0 0`() {
        // Arrange
        val rule = CssQualifiedRule(
            components = listOf(
                CssComponent.Single(type = CssComponentType.Universal, value = "*"),
            ),
            declarations = listOf(),
        )
        val expected = "(0, 0, 0)"
        // Act
        val actual = CssSpecificity(rule)
        // Assert
        assertEquals(expected, actual.toString())
    }

    @Test
    fun `when single id selector then specificity is 1 0 0`() {
        // Arrange
        val rule = CssQualifiedRule(
            components = listOf(
                CssComponent.Single(type = CssComponentType.Id, value = "my-id"),
            ),
            declarations = listOf(),
        )
        val expected = "(1, 0, 0)"
        // Act
        val actual = CssSpecificity(rule)
        // Assert
        assertEquals(expected, actual.toString())
    }

    @Test
    fun `when single class selector then specificity is 0 1 0`() {
        // Arrange
        val rule = CssQualifiedRule(
            components = listOf(
                CssComponent.Single(type = CssComponentType.Class, value = "my-class"),
            ),
            declarations = listOf(),
        )
        val expected = "(0, 1, 0)"
        // Act
        val actual = CssSpecificity(rule)
        // Assert
        assertEquals(expected, actual.toString())
    }

    @Test
    fun `when single tag selector then specificity is 0 0 1`() {
        // Arrange
        val rule = CssQualifiedRule(
            components = listOf(
                CssComponent.Single(type = CssComponentType.Tag, value = "div"),
            ),
            declarations = listOf(),
        )
        val expected = "(0, 0, 1)"
        // Act
        val actual = CssSpecificity(rule)
        // Assert
        assertEquals(expected, actual.toString())
    }

    @Test
    @Burst
    fun `when multiple ids selectors then specificity is n 0 0 where n is number of selectors`(
        // Arrange
        rule: CssQualifiedRule = burstValues(
            CssQualifiedRule(
                components = listOf(
                    CssComponent.Multiple(
                        components = listOf(
                            CssComponent.Single(type = CssComponentType.Id, value = "my-id-0"),
                            CssComponent.Single(type = CssComponentType.Id, value = "my-id-1"),
                        ),
                    ),
                ),
                declarations = listOf()
            ),
            CssQualifiedRule(
                components = listOf(
                    CssComponent.Multiple(
                        components = listOf(
                            CssComponent.Single(type = CssComponentType.Id, value = "my-id-0"),
                            CssComponent.Single(type = CssComponentType.Id, value = "my-id-1"),
                            CssComponent.Single(type = CssComponentType.Id, value = "my-id-2"),
                        ),
                    )
                ),
                declarations = listOf()
            ),
            CssQualifiedRule(
                components = listOf(
                    CssComponent.Multiple(
                        components = listOf(
                            CssComponent.Single(type = CssComponentType.Id, value = "my-id-0"),
                            CssComponent.Single(type = CssComponentType.Id, value = "my-id-1"),
                            CssComponent.Single(type = CssComponentType.Id, value = "my-id-2"),
                            CssComponent.Single(type = CssComponentType.Id, value = "my-id-3"),
                        ),
                    ),
                ),
                declarations = listOf()
            ),
        )
    ) {
        // Arrange (cont.)
        val n = rule.components.firstInstanceOf<CssComponent.Multiple>().components.size
        val expected = "($n, 0, 0)"
        // Act
        val actual = CssSpecificity(rule)
        // Assert
        assertEquals(expected, actual.toString())
    }

    @Test
    @Burst
    fun `when multiple class selectors then specificity is 0 n 0 where n is number of selectors`(
        // Arrange
        rule: CssQualifiedRule = burstValues(
            CssQualifiedRule(
                components = listOf(
                    CssComponent.Multiple(
                        components = listOf(
                            CssComponent.Single(type = CssComponentType.Class, value = "class-0"),
                            CssComponent.Single(type = CssComponentType.Class, value = "class-1"),
                        ),
                    ),
                ),
                declarations = listOf()
            ),
            CssQualifiedRule(
                components = listOf(
                    CssComponent.Multiple(
                        components = listOf(
                            CssComponent.Single(type = CssComponentType.Class, value = "class-0"),
                            CssComponent.Single(type = CssComponentType.Class, value = "class-1"),
                            CssComponent.Single(type = CssComponentType.Class, value = "class-2"),
                        ),
                    )
                ),
                declarations = listOf()
            ),
            CssQualifiedRule(
                components = listOf(
                    CssComponent.Multiple(
                        components = listOf(
                            CssComponent.Single(type = CssComponentType.Class, value = "class-0"),
                            CssComponent.Single(type = CssComponentType.Class, value = "class-1"),
                            CssComponent.Single(type = CssComponentType.Class, value = "class-2"),
                            CssComponent.Single(type = CssComponentType.Class, value = "class-3"),
                        ),
                    ),
                ),
                declarations = listOf()
            ),
        )
    ) {
        // Arrange (cont.)
        val n = rule.components.firstInstanceOf<CssComponent.Multiple>().components.size
        val expected = "(0, $n, 0)"
        // Act
        val actual = CssSpecificity(rule)
        // Assert
        assertEquals(expected, actual.toString())
    }

    @Test
    @Burst
    fun `when multiple tag selectors then specificity is 0 0 n where n is number of selectors`(
        // Arrange
        rule: CssQualifiedRule = burstValues(
            CssQualifiedRule(
                components = listOf(
                    CssComponent.Multiple(
                        components = listOf(
                            CssComponent.Single(type = CssComponentType.Tag, value = "ul"),
                            CssComponent.Single(type = CssComponentType.Tag, value = "li"),
                        ),
                    ),
                ),
                declarations = listOf()
            ),
            CssQualifiedRule(
                components = listOf(
                    CssComponent.Multiple(
                        components = listOf(
                            CssComponent.Single(type = CssComponentType.Tag, value = "div"),
                            CssComponent.Single(type = CssComponentType.Tag, value = "ul"),
                            CssComponent.Single(type = CssComponentType.Tag, value = "li"),
                        ),
                    )
                ),
                declarations = listOf()
            ),
            CssQualifiedRule(
                components = listOf(
                    CssComponent.Multiple(
                        components = listOf(
                            CssComponent.Single(type = CssComponentType.Tag, value = "div"),
                            CssComponent.Single(type = CssComponentType.Tag, value = "section"),
                            CssComponent.Single(type = CssComponentType.Tag, value = "ul"),
                            CssComponent.Single(type = CssComponentType.Tag, value = "li"),
                        ),
                    ),
                ),
                declarations = listOf()
            ),
        )
    ) {
        // Arrange (cont.)
        val n = rule.components.firstInstanceOf<CssComponent.Multiple>().components.size
        val expected = "(0, 0, $n)"
        // Act
        val actual = CssSpecificity(rule)
        // Assert
        assertEquals(expected, actual.toString())
    }

    @Test
    @Burst
    fun `should calculate specificity for multiple selectors`(
        // Arrange
        rules: Pair<CssQualifiedRule, String> = burstValues(
            CssQualifiedRule(
                components = listOf(
                    CssComponent.Single(type = CssComponentType.Tag, value = "li"),
                    CssComponent.Single(type = CssComponentType.Class, value = "my-class"),
                    CssComponent.Single(type = CssComponentType.Class, value = "my-second-class"),
                ),
                declarations = listOf(),
            ) to "(0, 2, 1)",
            CssQualifiedRule(
                components = listOf(
                    CssComponent.Single(
                        type = CssComponentType.Tag,
                        value = "ol",
                        combinator = CssCombinator.DescendantCombinator,
                    ),
                    CssComponent.Single(
                        type = CssComponentType.Universal,
                        value = "*",
                        combinator = CssCombinator.DescendantCombinator,
                    ),
                    CssComponent.Single(
                        type = CssComponentType.Class,
                        value = "my-second-class",
                    ),
                ),
                declarations = listOf(),
            ) to "(0, 1, 1)",
            CssQualifiedRule(
                components = listOf(
                    CssComponent.Multiple(
                        components = listOf(
                            CssComponent.Single(
                                type = CssComponentType.Id,
                                value = "s12",
                                combinator = CssCombinator.DescendantCombinator,
                            ),
                            CssComponent.Single(
                                type = CssComponentType.PseudoClass(
                                    parameters = listOf(
                                        CssComponent.Single(
                                            type = CssComponentType.Tag,
                                            value = "FOO",
                                        )
                                    )
                                ),
                                value = ":not",
                            ),
                        ),
                    ),
                ),
                declarations = listOf(),
            ) to "(1, 0, 1)",
            CssQualifiedRule(
                components = listOf(
                    CssComponent.Single(
                        type = CssComponentType.Class,
                        value = "foo",
                        combinator = CssCombinator.DescendantCombinator,
                    ),
                    CssComponent.Single(
                        type = CssComponentType.PseudoClass(
                            parameters = listOf(
                                CssComponent.Single(
                                    type = CssComponentType.Class,
                                    value = "bar",
                                ),
                                CssComponent.Single(
                                    type = CssComponentType.Id,
                                    value = "baz",
                                )
                            )
                        ),
                        value = ":is",
                    ),
                ),
                declarations = listOf(),
            ) to "(1, 1, 0)",
        )
    ) {
        // Arrange (cont.)
        val (rule, expected) = rules
        // Act
        val actual = CssSpecificity(rule)
        // Assert
        assertEquals(expected, actual.toString())
    }
}
