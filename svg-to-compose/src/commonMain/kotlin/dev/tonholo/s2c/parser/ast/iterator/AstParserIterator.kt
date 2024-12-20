package dev.tonholo.s2c.parser.ast.iterator

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.TokenKind
import dev.tonholo.s2c.parser.ast.css.syntax.AstParserException
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * An iterator for the AST parser.
 *
 * @param TTokenKind The type of token kind.
 */
abstract class AstParserIterator<TTokenKind : TokenKind>(
    internal val tokens: List<Token<out TTokenKind>>,
) {
    internal var offset = 0
        private set

    fun hasNext(): Boolean {
        return offset < tokens.size
    }

    /**
     * Returns the next token in the iteration.
     */
    fun next(): Token<out TTokenKind>? = if (offset < tokens.size) {
        tokens[offset++]
    } else {
        null
    }

    /**
     * Peeks the next token without consuming it.
     */
    fun peek(steps: Int = 1): Token<out TTokenKind>? =
        tokens.getOrNull(offset + steps)

    /**
     * Returns the current token, which is the token that was previously consumed.
     * This is equivalent to peeking one step back.
     *
     * @return The current token, or null if there is no current token.
     */
    fun current(): Token<out TTokenKind>? = peek(steps = -1)

    /**
     * Rewinds the iterator by the specified number of steps.
     */
    fun rewind(steps: Int = 1) {
        offset -= steps
    }
}

/**
 * Throws a parser error with contextual information.
 *
 * @param TTokenKind The type of token kind used by the parser.
 * @param content The input content being parsed.
 * @param message The error message to display.
 * @param backtrack The number of tokens to include before the
 * error location in the context snippet. Defaults to 1.
 * @param forward The number of tokens to include after the error
 * location in the context snippet. Defaults to 1.
 *
 * @throws AstParserException When called, always throws an exception
 * with the provided error message and context.
 */
internal fun <TTokenKind : TokenKind> AstParserIterator<TTokenKind>.parserError(
    content: String,
    message: String,
    backtrack: Int = 1,
    forward: Int = 1,
): Nothing {
    throw AstParserException(
        message = message,
        tokens = tokens,
        offset = offset,
        content,
        backtrack,
        forward,
    )
}

/**
 * Asserts a condition during parsing. If the condition is false,
 * a parsing error is thrown.
 *
 * @param TTokenKind The type of token kind used by the parser.
 * @param predicate The condition to assert. If false, an error is
 * thrown.
 * @param content The relevant content from the input stream where
 * the assertion is made. This is used in the error message to provide
 * context.
 * @param backtrack The number of tokens to step back in the error
 * report to show the relevant part of the input. Defaults to 1.
 * @param forward The number of tokens to step forward in the error
 * report to show the relevant part of the input. Defaults to 1.
 * @param lazyMessage A function that provides a detailed error
 * message explaining the failed assertion. This function is only
 * called if the assertion fails, improving performance.
 *
 * @throws AstParserException If the [predicate] is false, indicating
 * a syntax error in the input.
 */
@OptIn(ExperimentalContracts::class)
internal inline fun <TTokenKind : TokenKind> AstParserIterator<TTokenKind>.parserRequire(
    predicate: Boolean,
    content: String,
    backtrack: Int = 1,
    forward: Int = 1,
    lazyMessage: () -> String,
) {
    contract {
        returns() implies predicate
    }
    if (!predicate) {
        parserError(content, lazyMessage(), backtrack, forward)
    }
}

/**
 * Checks a condition during AST parsing and throws a exception
 * if the condition is false.
 *
 * @param predicate The condition to check. If false, an exception is thrown.
 * @param content The relevant content from the input stream where
 * the assertion is made. This is used in the error message to provide
 * context.
 * @param backtrack The number of tokens to step back in the error
 * report to show the relevant part of the input. Defaults to 1.
 * @param forward The number of tokens to step forward in the error
 * report to show the relevant part of the input. Defaults to 1.
 * @param lazyMessage A lambda function that provides the error message.
 * This is evaluated lazily only when an exception is thrown.
 *
 * @throws AstParserException If the [predicate] is false.
 */
@OptIn(ExperimentalContracts::class)
internal inline fun <TTokenKind : TokenKind> AstParserIterator<TTokenKind>.parserCheck(
    predicate: Boolean,
    content: String,
    backtrack: Int = 1,
    forward: Int = 1,
    lazyMessage: () -> String,
) {
    contract {
        returns() implies predicate
    }
    if (!predicate) {
        parserError(content, lazyMessage(), backtrack, forward)
    }
}

/**
 * Checks if the given [value] is not null. If the value is null, an
 * exception is thrown.
 *
 * @param TTokenKind The type of token kinds handled by the parser.
 * @param T The type of the value being checked.
 * @param value The value to check for nullity.
 * @param content The relevant content from the input stream where
 * the assertion is made. This is used in the error message to provide
 * context.
 * @param backtrack The number of tokens to step back in the error
 * report to show the relevant part of the input. Defaults to 1.
 * @param forward The number of tokens to step forward in the error
 * report to show the relevant part of the input. Defaults to 1.
 * @param lazyMessage A lambda function providing the error message.
 * It's invoked lazily only if an error occurs.
 * @return [value] if not null.
 *
 * @throws AstParserException If the [value] is null.
 */
@OptIn(ExperimentalContracts::class)
internal inline fun <TTokenKind : TokenKind, reified T> AstParserIterator<TTokenKind>.parserCheckNotNull(
    value: T?,
    content: String,
    backtrack: Int = 1,
    forward: Int = 1,
    lazyMessage: () -> String,
): T {
    contract {
        returns() implies (value != null)
    }
    if (value == null) {
        parserError(content, lazyMessage(), backtrack, forward)
    } else {
        return value
    }
}
