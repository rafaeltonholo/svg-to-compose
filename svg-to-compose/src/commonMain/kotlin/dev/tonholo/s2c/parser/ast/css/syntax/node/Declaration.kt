package dev.tonholo.s2c.parser.ast.css.syntax.node

import dev.tonholo.s2c.extensions.prependIndent

/**
 * A CSS declaration.
 *
 * @property important Whether the property's value
 * is marked as important.
 * @property property The property name.
 * @property values The property values.
 */
data class Declaration(
    override val location: CssLocation,
    val important: Boolean,
    val property: String,
    val values: List<Value>,
) : CssComponentValueNode {
    override fun toString(indent: Int): String {
        return buildString {
            repeat(indent) { append(" ") }
            append(property)
            append(": ")
            append(values.joinToString(" ") { it.toString(indent = 0) })
            if (important) {
                append(" !important")
            }
            append(";")
        }
    }

    override fun toString(): String {
        return buildString {
            appendLine("Declaration(")
            appendLine("location = $location,".prependIndent(indentSize = 2))
            appendLine("important = $important,".prependIndent(indentSize = 2))
            appendLine("property = \"$property\",".prependIndent(indentSize = 2))
            appendLine(
                "values = [".prependIndent(indentSize = 2),
            )
            values.forEach {
                appendLine(it.toString().prependIndent(indentSize = 4))
            }
            appendLine("],".prependIndent(indentSize = 2))
            append(")")
        }
    }
}
