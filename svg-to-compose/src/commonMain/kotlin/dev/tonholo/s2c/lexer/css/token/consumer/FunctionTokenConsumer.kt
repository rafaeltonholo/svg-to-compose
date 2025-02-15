package dev.tonholo.s2c.lexer.css.token.consumer

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.TokenIterator
import dev.tonholo.s2c.lexer.css.CssTokenKind

internal class FunctionTokenConsumer(
    iterator: TokenIterator<CssTokenKind>,
) : TokenConsumer(iterator) {
    override val supportedTokenKinds: Set<CssTokenKind> = setOf(
        CssTokenKind.Function,
    )

    override fun consume(kind: CssTokenKind): List<Token<out CssTokenKind>> {
        val start = iterator.offset
        while (iterator.hasNext()) {
            val char = iterator.next()
            if (char in CssTokenKind.OpenParenthesis) {
                break
            }
            if (char.isLetterOrDigit().not() && char != '-' && char != '_') {
                error("Invalid function name: ${iterator.partialContent(start, iterator.offset)}")
            }
        }
        return listOf(
            Token(CssTokenKind.Function, start, iterator.offset)
        )
    }
}
