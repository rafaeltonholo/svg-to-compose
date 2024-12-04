package dev.tonholo.s2c.parser.ast

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.TokenKind
import dev.tonholo.s2c.parser.ast.iterator.AstParserIterator

/**
 * Represents a parser for an Abstract Syntax Tree (AST).
 *
 * @param TTokenKind The type of token kinds used in the parser.
 * @param TAstNode The type of AST nodes produced by the parser.
 */
interface AstParser<TTokenKind : TokenKind, TAstNode : Element> : AstParserIterator<TTokenKind, TAstNode> {
    /**
     * Parses a list of tokens into an AST node.
     *
     * @param tokens The list of tokens to parse.
     * @return The root node of the parsed AST.
     */
    fun parse(tokens: List<Token<out TTokenKind>>): TAstNode

    /**
     * Builds an error message with context from the current parser state.
     */
    fun buildErrorMessage(
        message: String,
        backtrack: Int = 1,
        forward: Int = 1,
    ): String
}
