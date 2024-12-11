package dev.tonholo.s2c.lexer

internal interface Tokenizer<out T : TokenKind> {
    fun tokenize(input: String): Sequence<Token<out T>>
}
