package dev.tonholo.s2c.parser.ast.css.syntax.node

import dev.tonholo.s2c.extensions.prependIndent

/**
 * A CSS rule. Can be either an at-rule or a qualified rule.
 */
sealed interface Rule : CssStatementNode {
    override val location: CssLocation
    val prelude: Prelude<*>
    val block: Block<out CssNode>

    override fun toString(indent: Int): String {
        return buildString {
            repeat(indent) { append(" ") }
            append(prelude.components.joinToString { it.toString(indent = 0) })
            appendLine(" {")
            appendLine(block.toString(indent = indent + 2))
            repeat(indent) { append(" ") }
            append("}")
        }
    }
}

/**
 * An at-rule, e.g. `@media (min-width: 768px) { ... }`.
 */
data class AtRule(
    override val location: CssLocation,
    val name: String,
    override val prelude: Prelude.AtRule,
    override val block: Block<Rule>,
) : Rule {
    override fun toString(indent: Int): String {
        return buildString {
            appendLine("@$name ${prelude.components.joinToString { it.toString(indent = 0) }} {")
            appendLine(block.toString(indent = indent + 2))
            append("}")
        }
    }

    override fun toString(): String {
        return buildString {
            appendLine("AtRule(")
            appendLine("location = $location,".prependIndent(indentSize = 2))
            appendLine("name = \"$name\",".prependIndent(indentSize = 2))
            appendLine("prelude = $prelude,".prependIndent(indentSize = 2))
            appendLine("block = $block,".prependIndent(indentSize = 2))
            append(")")
        }
    }
}

/**
 * A qualified rule, e.g. `body { color: red; }`.
 */
data class QualifiedRule(
    override val location: CssLocation,
    override val prelude: Prelude.Selector,
    override val block: Block<Declaration>,
) : Rule {
    override fun toString(): String {
        return buildString {
            appendLine("QualifiedRule(")
            appendLine("location = $location,".prependIndent(indentSize = 2))
            appendLine("prelude = $prelude,".prependIndent(indentSize = 2))
            appendLine("block = $block,".prependIndent(indentSize = 2))
            append(")")
        }
    }
}
