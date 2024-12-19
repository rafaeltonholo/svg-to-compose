package dev.tonholo.s2c.lexer.css.token.consumer

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.TokenIterator
import dev.tonholo.s2c.lexer.css.CssTokenKind

internal class IdentTokenConsumer(
    iterator: TokenIterator<CssTokenKind>,
) : TokenConsumer(iterator) {
    override val supportedTokenKinds: Set<CssTokenKind> = setOf(
        CssTokenKind.Ident,
    )

    override fun consume(kind: CssTokenKind): List<Token<out CssTokenKind>> {
        val start = iterator.offset
        while (iterator.hasNext()) {
            val char = iterator.get()

            when (char) {
                in '0'..'9',
                in 'a'..'z',
                in 'A'..'Z',
                '-',
                '_' -> {
                    iterator.nextOffset()
                }

                else -> {
                    break
                }
            }
        }

        return listOf(
            if (
                iterator.hasNext() && iterator.get() == '(' &&
                start - 1 > 0 && iterator.lookup(start - 1) in CssTokenKind.WhiteSpace
            ) {
                Token(CssTokenKind.Function, start, iterator.offset)
            } else {
                Token(CssTokenKind.Ident, start, iterator.offset)
            },
        )
    }
}
