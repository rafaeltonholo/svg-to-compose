package dev.tonholo.s2c.parser.ast.css.syntax.node

import dev.tonholo.s2c.extensions.prependIndent
import dev.tonholo.s2c.parser.ast.css.CssSpecificity
import dev.tonholo.s2c.parser.ast.css.calculateSelectorsSpecificity

/**
 * Represents the prelude of a [Rule], which can be either
 * a selector list or an at-rule prelude.
 *
 * @param T The type of component value nodes within the prelude.
 */
sealed interface Prelude<T : CssComponentValueNode> {
    /**
     * The list of component value nodes that make up the prelude.
     */
    val components: List<T>

    /**
     * Represents a selector list prelude.
     */
    data class Selector(
        override val components: List<SelectorListItem>,
    ) : Prelude<SelectorListItem> {
        val specificities: Map<SelectorListItem, CssSpecificity> by lazy {
            calculateSelectorsSpecificity(this)
        }

        override fun toString(): String {
            return buildString {
                appendLine("Selector(")
                appendLine("components = [".prependIndent(indentSize = 2))
                components.forEach {
                    appendLine(it.toString().prependIndent(indentSize = 4))
                }
                appendLine("],".prependIndent(indentSize = 2))
                appendLine(")")
            }
        }
    }

    /**
     * Represents an at-rule prelude.
     */
    data class AtRule(
        override val components: List<AtRulePrelude>,
    ) : Prelude<AtRulePrelude> {
        override fun toString(): String {
            return buildString {
                appendLine("AtRule(")
                appendLine("components = [".prependIndent(indentSize = 2))
                components.forEach {
                    appendLine(it.toString().prependIndent(indentSize = 4))
                }
                appendLine("],".prependIndent(indentSize = 2))
                appendLine(")")
            }
        }
    }
}
