package dev.tonholo.s2c.parser.ast.css.syntax

import dev.tonholo.s2c.extensions.prependIndent
import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.TokenKind

/**
 * An exception thrown when an error occurs during AST parsing.
 *
 * This exception [message] provides detailed information about the error, including:
 * - The error message.
 * - Start offset and end offset of the offending token.
 * - The content where the the offending token is located at with indications of what wrong.
 *
 * @property message The error message.
 * @property tokens The list of tokens parsed so far.
 * @property offset The offset where the error occurred.
 * @property content The original string content.
 * @property backtrack The number of tokens to backtrack for context.
 * @property forward The number of tokens to look ahead for context.
 */
internal class AstParserException internal constructor(
    message: String,
    private val tokens: List<Token<out TokenKind>>,
    private val offset: Int,
    private val content: String,
    private val backtrack: Int,
    private val forward: Int
) : IllegalStateException(message) {
    private val _message = message

    /**
     * Builds an error message with context from the current parser state.
     */
    override val message: String
        get() = buildString {
            appendLine(_message)
            val currentOffset = offset - 1
            val prev = tokens.getOrNull(currentOffset - backtrack)?.startOffset ?: 0
            val next = tokens.getOrNull(currentOffset + forward)?.endOffset ?: content.length
            val current = tokens.getOrNull(currentOffset)
            if (current != null) {
                appendLine("Start offset: ${current.startOffset}")
                appendLine("End offset: ${current.endOffset}")
                appendLine("Content:")
                var indent = 4
                appendLine(
                    content
                        .substring(prev, next)
                        .trimEnd('\n')
                        .prependIndent(indent),
                )
                appendLine("^".repeat(current.endOffset - prev).prependIndent(indent))
                indent += current.startOffset.minus(prev)
                append("^".repeat(current.endOffset - current.startOffset).prependIndent(indent))
            }
        }
}
