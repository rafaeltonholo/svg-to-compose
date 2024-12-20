package dev.tonholo.s2c.parser.ast.css.syntax.node

import dev.tonholo.s2c.extensions.prependIndent
import dev.tonholo.s2c.parser.ast.css.CssCombinator

/**
 * Represents a CSS selector.
 *
 * @property name The name of the selector (e.g., tag name, class name, ID).
 * @property combinator The combinator used to combine this selector with
 * other selectors (e.g., descendant, child, adjacent sibling).
 * @see CssCombinator
 */
sealed interface Selector : CssNode {
    val name: String
    val combinator: CssCombinator?

    override fun toString(indent: Int): String =
        name + combinator?.representation.orEmpty()

    /**
     * Represents a type selector (e.g., `div`, `p`).
     */
    data class Type(
        override val location: CssLocation,
        override val name: String,
        override val combinator: CssCombinator? = null,
    ) : Selector

    /**
     * Represents a class selector (e.g., `.my-class`).
     *
     * @property name The name of the class.
     * @property combinator The combinator used to combine this selector with
     * other selectors (e.g., descendant, child, adjacent sibling).
     * @see CssCombinator
     */
    data class Class(
        override val location: CssLocation,
        override val name: String,
        override val combinator: CssCombinator? = null,
    ) : Selector {
        override fun toString(indent: Int): String {
            return ".${super.toString(indent)}"
        }
    }

    /**
     * Represents an ID selector (e.g., `#my-id`).
     *
     * @property name The ID.
     * @property combinator The combinator used to combine this selector with
     * other selectors (e.g., descendant, child, adjacent sibling).
     * @see CssCombinator
     */
    data class Id(
        override val location: CssLocation,
        override val name: String,
        override val combinator: CssCombinator? = null,
    ) : Selector {
        override fun toString(indent: Int): String {
            return "#${super.toString(indent)}"
        }
    }

    /**
     * Represents a pseudo-class selector (e.g., `:hover`, `:nth-child(2)`).
     *
     * @property name The name of the pseudo-class.
     * @property parameters The parameters of the pseudo-class (if any).
     * @property combinator The combinator used to combine this selector with
     * other selectors (e.g., descendant, child, adjacent sibling).
     * @see CssCombinator
     */
    data class PseudoClass(
        override val location: CssLocation,
        override val name: String,
        val parameters: List<Selector>,
        override val combinator: CssCombinator? = null,
    ) : Selector {
        override fun toString(indent: Int): String {
            return buildString {
                append(":$name")
                if (parameters.isNotEmpty()) {
                    append("(")
                    append(parameters.joinToString(", ") { it.toString(indent = 0) })
                    append(")")
                }
                append(combinator?.representation.orEmpty())
            }
        }
    }

    /**
     * Represents a pseudo-element selector (e.g., `::before`, `::first-line`).
     *
     * @property name The name of the pseudo-element.
     * @property parameters The parameters of the pseudo-element (if any).
     * @property combinator The combinator used to combine this selector with
     * other selectors (e.g., descendant, child, adjacent sibling).
     * @see CssCombinator
     */
    data class PseudoElement(
        override val location: CssLocation,
        override val name: String,
        val parameters: List<Selector>,
        override val combinator: CssCombinator? = null,
    ) : Selector {
        override fun toString(indent: Int): String {
            return buildString {
                append("::$name")
                if (parameters.isNotEmpty()) {
                    append("(")
                    append(parameters.joinToString(", ") { it.toString(indent = 0) })
                    append(")")
                }
                append(combinator?.representation.orEmpty())
            }
        }
    }

    /**
     * Represents an attribute selector (e.g., `[type="text"]`, `[ disabled ]`).
     *
     * @property name The name of the attribute.
     * @property matcher The matcher used to compare the attribute value (e.g., `=`, `~=`, `|=`).
     * @property value The value to match against.
     * @property combinator The combinator used to combine this selector with
     * other selectors (e.g., descendant, child, adjacent sibling).
     * @see CssCombinator
     */
    data class Attribute(
        override val location: CssLocation,
        override val name: String,
        val matcher: String? = null,
        val value: String? = null,
        override val combinator: CssCombinator? = null,
    ) : Selector {
        override fun toString(indent: Int): String {
            return buildString {
                append("[$name")
                if (value != null && matcher != null) {
                    append(matcher)
                    append("\"$value\"")
                }
                append("]")
                append(combinator?.representation.orEmpty())
            }
        }
    }
}

/**
 * Represents a list of selectors, separated by commas.
 *
 * @property selectors The list of selectors.
 */
data class SelectorListItem(
    override val location: CssLocation,
    val selectors: List<Selector>,
) : CssComponentValueNode {
    override fun toString(indent: Int): String =
        selectors.joinToString("") { it.toString(indent = 0) }

    override fun toString(): String {
        return buildString {
            appendLine("SelectorListItem(")
            appendLine(
                "location = $location,".prependIndent(indentSize = 2),
            )
            appendLine(
                "selectors = [".prependIndent(indentSize = 2),
            )
            selectors.forEach {
                appendLine(it.toString().prependIndent(indentSize = 4))
            }
            appendLine("],".prependIndent(indentSize = 2))
            append(")")
        }
    }
}
