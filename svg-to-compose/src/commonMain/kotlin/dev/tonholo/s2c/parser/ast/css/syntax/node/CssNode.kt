package dev.tonholo.s2c.parser.ast.css.syntax.node

import dev.tonholo.s2c.extensions.prependIndent
import dev.tonholo.s2c.parser.ast.Element

/**
 * The base interface for all CSS nodes.
 */
sealed interface CssNode : Element {
    val location: CssLocation
    fun toString(indent: Int = 0): String
}

/**
 * A CSS node that represents a component value.
 * Component values are the basic building blocks of CSS properties.
 */
sealed interface CssComponentValueNode : CssNode

/**
 * A CSS node that represents a statement.
 * Statements are the top-level constructs in CSS, such as rulesets and at-rules.
 */
sealed interface CssStatementNode : CssNode

/**
 * Represents a location in a CSS source file.
 */
data class CssLocation(
    val source: String,
    val start: Int,
    val end: Int,
) {
    override fun toString(): String = buildString {
        appendLine("CssLocation(")
        appendLine("source = \"\"\"$source\"\"\",".prependIndent(indentSize = 2))
        appendLine("start = $start,".prependIndent(indentSize = 2))
        appendLine("end = $end".prependIndent(indentSize = 2))
        append(")")
    }

    companion object {
        val Undefined = CssLocation(
            source = "",
            start = -1,
            end = -1,
        )
    }
}
