package dev.tonholo.s2c.lexer

internal data class Token<T : TokenKind>(
    val kind: T,
    // Inclusive
    val startOffset: Int,
    // Exclusive
    val endOffset: Int,
)
