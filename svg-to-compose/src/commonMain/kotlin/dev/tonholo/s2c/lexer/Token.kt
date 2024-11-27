package dev.tonholo.s2c.lexer

data class Token<T : TokenType>(
    val type: T,
    // Inclusive
    val startOffset: Int,
    // Exclusive
    val endOffset: Int,
)
