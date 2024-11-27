package dev.tonholo.s2c.parser.ast

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.TokenKind

internal interface AstParser<TTokenKind : TokenKind, TAstNode : Element> {
    fun parse(tokens: List<Token<out TTokenKind>>): TAstNode
}
