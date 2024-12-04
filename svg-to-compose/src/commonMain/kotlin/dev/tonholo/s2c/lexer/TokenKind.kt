package dev.tonholo.s2c.lexer

interface TokenKind {
    val representation: String

    operator fun contains(other: Char): Boolean {
        return other in representation
    }
}
