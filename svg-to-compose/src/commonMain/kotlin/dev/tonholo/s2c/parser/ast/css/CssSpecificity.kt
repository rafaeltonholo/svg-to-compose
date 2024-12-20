package dev.tonholo.s2c.parser.ast.css

import dev.tonholo.s2c.parser.ast.css.syntax.node.Prelude
import dev.tonholo.s2c.parser.ast.css.syntax.node.Selector
import dev.tonholo.s2c.parser.ast.css.syntax.node.SelectorListItem

/**
 * Represents CSS specificity as defined in the CSS specification.
 *
 * Specificity is a weight that is applied to CSS rules to determine
 * which rule is applied when multiple rules target the same element.
 *
 * It is comprised of three components:
 * - **[a]:** Represents the number of ID selectors in the rule.
 * - **[b]:** Represents the number of class selectors, attribute selectors, and pseudo-classes in the rule.
 * - **[c]:** Represents the number of type selectors and pseudo-elements in the rule.
 *
 * This class provides methods for comparing and adding specificities.
 *
 * @property a The number of ID selectors.
 * @property b The number of class selectors, attribute selectors, and pseudo-classes.
 * @property c The number of type selectors and pseudo-elements.
 */
data class CssSpecificity(
    val a: Int = 0,
    val b: Int = 0,
    val c: Int = 0,
) : Comparable<CssSpecificity> {
    operator fun get(index: Int): Int = when (index) {
        0 -> a
        1 -> b
        2 -> c
        else -> throw IndexOutOfBoundsException("Index $index is out of bounds.")
    }

    operator fun plus(other: CssSpecificity): CssSpecificity {
        return CssSpecificity(
            a = a + other.a,
            b = b + other.b,
            c = c + other.c,
        )
    }

    override operator fun compareTo(other: CssSpecificity): Int {
        return when {
            a != other.a -> a.compareTo(other.a)
            b != other.b -> b.compareTo(other.b)
            c != other.c -> c.compareTo(other.c)
            else -> 0
        }
    }

    override fun toString(): String {
        return "($a, $b, $c)"
    }
}

/**
 * Calculates the specificity of a CSS rule.
 *
 * Specificity is a weight that is applied to CSS rules to determine
 * which one is applied when multiple rules target the same element.
 *
 * It is calculated based on the selectors in the rule's prelude.
 *
 * @param prelude The prelude of the CSS rule.
 * @return A map where the keys are the selector list items and the values
 * are the [CssSpecificity] of each selector in the rule's prelude.
 */
fun calculateSelectorsSpecificity(prelude: Prelude.Selector): Map<SelectorListItem, CssSpecificity> {
    return prelude.components.associateWith { selector ->
        selector.selectors.fold(CssSpecificity()) { selectorSpecificity, selector ->
            selectorSpecificity + selector.calculateSpecificity()
        }
    }
}

private fun Selector.calculateSpecificity(): CssSpecificity {
    var a = 0
    var b = 0
    var c = 0
    when (this) {
        is Selector.Id -> a = 1

        is Selector.Attribute,
        is Selector.Class -> b = 1

        is Selector.Type if name != "*" -> c = 1

        is Selector.PseudoClass -> {
            val specificity = calculateSpecificity()
            a = specificity.a
            b = specificity.b
            c = specificity.c
        }

        is Selector.PseudoElement -> c = 1

        else -> Unit
    }
    return CssSpecificity(a, b, c)
}

private fun Selector.PseudoClass.calculateSpecificity(): CssSpecificity {
    var a = 0
    var b = 0
    var c = 0
    when (name) {
        "where" -> Unit
        "not",
        "is",
        "has",
        "matches" -> {
            parameters
                .maxOf { it.calculateSpecificity() }
                .also {
                    a = it.a
                    b = it.b
                    c = it.c
                }
        }

        "nth-child",
        "nth-last-child" -> {
            parameters
                .maxOf { it.calculateSpecificity() }
                .also {
                    a = it.a
                    b = it.b + 1
                    c = it.c
                }
        }

        "before",
        "after",
        "first-line",
        "first-letter" -> {
            c = 1
        }

        else -> b = 1
    }
    return CssSpecificity(a, b, c)
}
