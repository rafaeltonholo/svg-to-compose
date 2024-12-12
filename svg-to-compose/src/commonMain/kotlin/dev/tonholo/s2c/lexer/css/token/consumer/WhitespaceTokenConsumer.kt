package dev.tonholo.s2c.lexer.css.token.consumer

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.TokenIterator
import dev.tonholo.s2c.lexer.css.CssTokenKind

internal class WhitespaceTokenConsumer(
    iterator: TokenIterator<CssTokenKind>,
) : TokenConsumer(iterator) {
    override val supportedTokenKinds: Set<CssTokenKind> = setOf(
        CssTokenKind.WhiteSpace,
    )

    override fun consume(kind: CssTokenKind): List<Token<out CssTokenKind>> {
        val start = iterator.offset
        iterator.nextOffset()
        while (iterator.hasNext()) {
            val char = iterator.get()
            if (char !in CssTokenKind.WhiteSpace) {
                break
            }
            iterator.nextOffset()
        }

        return listOf(Token(CssTokenKind.WhiteSpace, start, iterator.offset))
    }
}
