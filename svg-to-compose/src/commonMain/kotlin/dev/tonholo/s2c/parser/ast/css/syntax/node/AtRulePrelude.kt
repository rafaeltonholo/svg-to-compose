package dev.tonholo.s2c.parser.ast.css.syntax.node

import dev.tonholo.s2c.extensions.prependIndent

/**
 * Represents the prelude of an at-rule in a CSS stylesheet.
 *
 * The prelude is the part of the at-rule that comes before the block,
 * and typically contains the rule's name and any parameters.
 *
 * For example, in the at-rule `@media screen and (max-width: 600px)`,
 * the prelude is `screen and (max-width: 600px)`.
 *
 * @property location The location of the prelude in the CSS source code.
 * @property value The string value of the prelude.
 */
data class AtRulePrelude(
    override val location: CssLocation,
    val value: String,
) : CssComponentValueNode {
    override fun toString(indent: Int): String = value

    override fun toString(): String {
        return buildString {
            appendLine("AtRulePrelude(")
            appendLine(
                "location = ${location},".prependIndent(indentSize = 2),
            )
            appendLine(
                "value = \"${value}\",".prependIndent(indentSize = 2),
            )
            append(")")
        }
    }
}
