package dev.tonholo.s2c.lexer.css.token.consumer

import dev.tonholo.s2c.extensions.EMPTY
import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.TokenIterator
import dev.tonholo.s2c.lexer.css.CssTokenKind

internal class HashTokenConsumer(
    iterator: TokenIterator<CssTokenKind>,
) : TokenConsumer(iterator) {
    override val supportedTokenKinds: Set<CssTokenKind> = setOf(
        CssTokenKind.Hash,
    )

    override fun consume(kind: CssTokenKind): List<Token<out CssTokenKind>> {
        val next = iterator.peek(1)
        return if (next != Char.EMPTY && next.isHexDigit()) {
            var backwardLookupOffset = 0
            do {
                val prev = iterator.peek(--backwardLookupOffset)
                if (prev == Char.EMPTY || prev != ' ') {
                    break
                }
            } while (prev == ' ')

            if (iterator.peek(backwardLookupOffset) in CssTokenKind.Colon) {
                listOf(
                    Token(CssTokenKind.Hash, iterator.offset, iterator.nextOffset()),
                    handleHexDigit(start = iterator.offset),
                )
            } else {
                listOf(Token(CssTokenKind.Hash, iterator.offset, iterator.nextOffset()))
            }
        } else {
            listOf(Token(CssTokenKind.Hash, iterator.offset, iterator.nextOffset()))
        }
    }

    private fun handleHexDigit(start: Int): Token<CssTokenKind> {
        while (iterator.hasNext()) {
            val char = iterator.get()
            when (char.lowercaseChar()) {
                in '0'..'9',
                in 'a'..'f' -> {
                    iterator.nextOffset()
                }

                else -> break
            }
        }

        return Token(CssTokenKind.HexDigit, start, iterator.offset)
    }

    private fun Char.isHexDigit(): Boolean {
        return this in '0'..'9' || this in 'a'..'f' || this in 'A'..'F'
    }
}
