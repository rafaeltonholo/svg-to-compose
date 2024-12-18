package dev.tonholo.s2c.parser.ast.css.syntax

import dev.tonholo.s2c.extensions.prependIndent
import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.TokenKind
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.iterator.AstParserIterator
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * An exception thrown when an error occurs during CSS parsing.
 *
 * This exception [message] provides detailed information about the error, including:
 * - The error message.
 * - Start offset and end offset of the offending token.
 * - The content where the the offending token is located at with indications of what wrong.
 *
 * @property message The error message.
 * @property tokens The list of tokens parsed so far.
 * @property offset The offset where the error occurred.
 * @property content The original CSS content.
 * @property backtrack The number of tokens to backtrack for context.
 * @property forward The number of tokens to look ahead for context.
 */
internal class CssParserException internal constructor(
    message: String,
    private val tokens: List<Token<out CssTokenKind>>,
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

/**
 * Throws a [CssParserException] indicating a parsing error.
 *
 * This function is used to signal errors encountered during CSS parsing. It creates
 * a [CssParserException] with relevant information about the error, including
 * the error message, the current parsing position, and the surrounding tokens.
 *
 * @param content The original CSS content being parsed.
 * @param message A descriptive message explaining the parsing error.
 * @param backtrack The number of tokens to include before the error position in the
 * exception context. Defaults to 1.
 * @param forward The number of tokens to include after the error position in the
 * exception context. Defaults to 1.
 *
 * @throws CssParserException Always throws a [CssParserException] to indicate a parsing error.
 */
internal fun CssIterator.parserError(content: String, message: String, backtrack: Int = 1, forward: Int = 1): Nothing {
    throw CssParserException(
        message = message,
        tokens = tokens,
        offset = offset,
        content,
        backtrack,
        forward,
    )
}

internal inline fun <TTokenKind : TokenKind> AstParserIterator<TTokenKind>.parserError(
    content: String,
    message: String,
    backtrack: Int = 1,
    forward: Int = 1,
): Nothing {
    if (this is CssIterator) {
        parserError(content, message, backtrack, forward)
    } else {
        error(message)
    }
}

/**
 * Checks a condition during CSS parsing and throws a [CssParserException]
 * if the condition is false.
 *
 * This function is used to validate the CSS input during parsing. If the
 * provided [precondition] is false, it throws a [CssParserException] with a
 * detailed error message and context information.
 *
 * @param precondition The condition to check. If false, an exception is thrown.
 * @param content The original CSS content being parsed.
 * @param backtrack The number of characters to include before the current
 * parsing position in the error message.
 * @param forward The number of characters to include after the current
 * parsing position in the error message.
 * @param lazyMessage A lambda function that provides the error message.
 * This is evaluated lazily only when an exception is thrown.
 *
 * @throws CssParserException If the `value` is false.
 */
@OptIn(ExperimentalContracts::class)
internal inline fun CssIterator.parserCheck(
    precondition: Boolean,
    content: String,
    backtrack: Int = 1,
    forward: Int = 1,
    lazyMessage: () -> String,
) {
    contract {
        returns() implies precondition
    }
    if (!precondition) {
        val message = lazyMessage()
        throw CssParserException(
            message = message,
            tokens = tokens,
            offset = offset,
            content,
            backtrack,
            forward,
        )
    }
}

@OptIn(ExperimentalContracts::class)
internal inline fun <TTokenKind : TokenKind> AstParserIterator<TTokenKind>.parserCheck(
    value: Boolean,
    content: String,
    backtrack: Int = 1,
    forward: Int = 1,
    lazyMessage: () -> String,
) {
    contract {
        returns() implies value
    }
    if (this is CssIterator) {
        parserCheck(value, content, backtrack, forward, lazyMessage)
    } else {
        check(value, lazyMessage)
    }
}
