package dev.tonholo.s2c.parser.ast

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.TokenKind

/**
 * Represents a parser for an Abstract Syntax Tree (AST).
 *
 * @param TTokenKind The type of token kinds used in the parser.
 * @param TAstNode The type of AST nodes produced by the parser.
 */
interface AstParser<TTokenKind : TokenKind, out TAstNode : Element> {
    /**
     * Parses a list of tokens into an AST node.
     *
     * @param tokens The list of tokens to parse.
     * @return The root node of the parsed AST.
     */
    fun parse(tokens: List<Token<out TTokenKind>>): TAstNode
}
