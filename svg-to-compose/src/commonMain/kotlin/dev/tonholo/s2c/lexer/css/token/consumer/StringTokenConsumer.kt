package dev.tonholo.s2c.lexer.css.token.consumer

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.TokenIterator
import dev.tonholo.s2c.lexer.css.CssTokenKind

internal class StringTokenConsumer(
    iterator: TokenIterator<CssTokenKind>,
) : TokenConsumer(iterator) {
    override val supportedTokenKinds: Set<CssTokenKind> = setOf(
        CssTokenKind.Quote,
        CssTokenKind.DoubleQuote,
    )

    override fun consume(kind: CssTokenKind): List<Token<out CssTokenKind>> {
        val start = iterator.offset
        var currentOffset = 1
        while (iterator.hasNext()) {
            val current = iterator.peek(currentOffset++)
            when (current) {
                in CssTokenKind.Quote,
                in CssTokenKind.DoubleQuote -> {
                    break
                }

                '\n', in CssTokenKind.Semicolon -> {
                    return listOf()
                }
            }
        }
        iterator.nextOffset(currentOffset)
        return listOf(Token(CssTokenKind.String, start, iterator.offset))
    }
}
