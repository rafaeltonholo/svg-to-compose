package dev.tonholo.s2c.lexer.css.token.consumer

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.TokenIterator
import dev.tonholo.s2c.lexer.css.CssTokenKind

internal class DirectTokenConsumer(
    iterator: TokenIterator<CssTokenKind>,
) : TokenConsumer(iterator) {
    override val supportedTokenKinds: Set<CssTokenKind> = setOf(
        CssTokenKind.Greater,
        CssTokenKind.Dot,
        CssTokenKind.Comma,
        CssTokenKind.Colon,
        CssTokenKind.Semicolon,
        CssTokenKind.OpenCurlyBrace,
        CssTokenKind.CloseCurlyBrace,
        CssTokenKind.OpenParenthesis,
        CssTokenKind.CloseParenthesis,
    )

    override fun accept(kind: CssTokenKind): Boolean {
        return when {
            kind == CssTokenKind.Dot -> {
                val next = iterator.peek(offset = 1)
                !next.isDigit()
            }
            else -> super.accept(kind)
        }
    }

    override fun consume(kind: CssTokenKind): List<Token<out CssTokenKind>> {
        val start = iterator.offset
        val end = iterator.nextOffset()
        val token = Token(kind, start, end)
        return listOf(token)
    }
}
