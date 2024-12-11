package dev.tonholo.s2c.parser.ast.css.syntax.node

import dev.tonholo.s2c.extensions.prependIndent

/**
 * Represents a CSS stylesheet, containing a list of CSS statements.
 *
 * @property location The location of the stylesheet in the source code.
 * @property children The list of CSS statements within the stylesheet.
 */
data class StyleSheet(
    override val location: CssLocation,
    val children: List<CssStatementNode>,
) : CssNode {
    override fun toString(indent: Int): String {
        return children.joinToString("\n") { it.toString(indent) }
    }

    override fun toString(): String = buildString {
        appendLine("StyleSheet(")
        appendLine("location = ${location},".prependIndent(indentSize = 2))
        appendLine(
            "children = [".prependIndent(indentSize = 2),
        )
        appendLine(children.joinToString { statement ->
            statement
                .toString()
                .prependIndent(indentSize = 4)
        })
        appendLine("],".prependIndent(indentSize = 2))
        append(")")
    }
}
