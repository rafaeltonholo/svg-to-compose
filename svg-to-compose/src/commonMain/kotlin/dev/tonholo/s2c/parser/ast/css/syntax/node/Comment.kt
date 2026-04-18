package dev.tonholo.s2c.parser.ast.css.syntax.node

/**
 * Represents a CSS comment.
 */
data class Comment(
    val value: String,
    override val location: CssLocation,
    val isHtmlComment: Boolean = false,
) : CssStatementNode {
    override fun toString(indent: Int): String = if (isHtmlComment) {
        "<!-- $value -->"
    } else {
        "/* $value */"
    }
}
