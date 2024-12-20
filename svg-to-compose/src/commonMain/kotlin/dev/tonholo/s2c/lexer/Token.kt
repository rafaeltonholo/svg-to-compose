package dev.tonholo.s2c.lexer

import dev.tonholo.s2c.extensions.prependIndent

/**
 * Represents a token in a sequence of text.
 *
 * A token is a meaningful unit of text, such as a keyword, identifier, or operator.
 * It is characterized by its kind, start offset, and end offset.
 *
 * @param T The type of the token kind. Must implement [TokenKind].
 * @property kind The kind of the token.
 * @property startOffset The inclusive start offset of the token in the original text.
 * @property endOffset The exclusive end offset of the token in the original text.
 */
data class Token<T : TokenKind>(
    val kind: T,
    val startOffset: Int,
    val endOffset: Int,
) {
    override fun toString(): String {
        return buildString {
            appendLine("Token(")
            appendLine("kind = $kind,".prependIndent(indentSize = 2))
            appendLine("startOffset = $startOffset,".prependIndent(indentSize = 2))
            appendLine("endOffset = $endOffset,".prependIndent(indentSize = 2))
            appendLine(")")
        }
    }
}
