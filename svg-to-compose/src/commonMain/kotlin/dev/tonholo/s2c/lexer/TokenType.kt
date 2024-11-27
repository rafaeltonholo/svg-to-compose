package dev.tonholo.s2c.lexer

interface TokenType {
    val representation: String

    operator fun contains(other: Char): Boolean {
        return other in representation
    }
}
