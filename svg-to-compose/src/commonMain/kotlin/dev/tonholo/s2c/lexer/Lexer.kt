package dev.tonholo.s2c.lexer

interface Lexer<out T : TokenType> {
    fun tokenize(input: String): Sequence<Token<out T>>
}
