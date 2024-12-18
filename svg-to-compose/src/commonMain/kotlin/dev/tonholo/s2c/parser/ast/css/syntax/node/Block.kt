package dev.tonholo.s2c.parser.ast.css.syntax.node

import dev.tonholo.s2c.extensions.prependIndent

/**
 * Represents a block of CSS component values, like a rule block or a declaration block.
 * @param T The type of the children nodes.
 */
sealed class Block<T : CssComponentValueNode>(
    open val children: List<T>,
) : CssComponentValueNode {
    override fun toString(indent: Int): String {
        return children.joinToString("\n") { it.toString(indent) }
    }

    /**
     * A simple block with a location and children.
     * @param T The type of the children nodes.
     */
    data class SimpleBlock<T : CssComponentValueNode>(
        override val location: CssLocation,
        override val children: List<T>,
    ) : Block<T>(children) {
        override fun toString(): String {
            return buildString {
                appendLine("SimpleBlock(")
                appendLine("location = $location,".prependIndent(indentSize = 2))
                appendLine(
                    "children = [".prependIndent(indentSize = 2),
                )
                children.forEach {
                    appendLine(it.toString().prependIndent(indentSize = 4))
                }
                appendLine("],".prependIndent(indentSize = 2))
                append(")")
            }
        }
    }

    /**
     * An empty rule block, used as a placeholder when no rules are present.
     */
    data object EmptyRuleBlock : Block<Rule>(emptyList()) {
        override val location: CssLocation = CssLocation(
            source = "",
            start = -1,
            end = -1,
        )
    }

    /**
     * An empty declaration block, used as a placeholder when no declarations are present.
     */
    data object EmptyDeclarationBlock : Block<Declaration>(emptyList()) {
        override val location: CssLocation = CssLocation(
            source = "",
            start = -1,
            end = -1,
        )
    }
}
