package dev.tonholo.s2c.parser.ast.iterator

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.TokenKind

/**
 * An iterator for the AST parser.
 *
 * @param TTokenKind The type of token kind.
 */
interface AstParserIterator<TTokenKind : TokenKind> {
    fun hasNext(): Boolean

    /**
     * Returns the next token in the iteration.
     */
    fun next(): Token<out TTokenKind>?

    /**
     * Peeks the next token without consuming it.
     */
    fun peek(steps: Int = 1): Token<out TTokenKind>?

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
    fun rewind(steps: Int = 1)
}
