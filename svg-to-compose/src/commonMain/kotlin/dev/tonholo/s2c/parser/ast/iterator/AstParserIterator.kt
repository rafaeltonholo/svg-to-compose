package dev.tonholo.s2c.parser.ast.iterator

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.TokenKind
import dev.tonholo.s2c.parser.ast.Element

/**
 * An iterator for the AST parser.
 *
 * @param TTokenKind The type of token kind.
 * @param TAstNode The type of AST node.
 */
internal interface AstParserIterator<TTokenKind : TokenKind, TAstNode : Element> {
    /**
     * Returns the next token in the iteration.
     */
    fun next(): Token<out TTokenKind>?

    /**
     * Peeks the next token without consuming it.
     */
    fun peek(steps: Int = 1): Token<out TTokenKind>?

    /**
     * Rewinds the iterator by the specified number of steps.
     */
    fun rewind(steps: Int = 1)
}
