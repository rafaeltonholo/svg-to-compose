package dev.tonholo.s2c.lexer.css.token.consumer

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.TokenIterator
import dev.tonholo.s2c.lexer.css.CssTokenKind

internal class CommentTokenConsumer(
    iterator: TokenIterator<CssTokenKind>,
) : TokenConsumer(iterator) {
    override val supportedTokenKinds: Set<CssTokenKind> = setOf(
        CssTokenKind.CDO,
        CssTokenKind.CDC,
        CssTokenKind.Comment,
    )

    override fun consume(kind: CssTokenKind): List<Token<out CssTokenKind>> {
        return listOf(
            when (kind) {
                CssTokenKind.CDO -> consumeHtmlComment()
                CssTokenKind.Comment -> consumeCssComment()
                else -> error("Unsupported token kind: $kind")
            },
        )
    }

    private fun consumeHtmlComment(): Token<CssTokenKind> {
        val start = iterator.offset
        iterator.nextOffset(steps = 3) // skips '<' and '!'
        while (iterator.hasNext()) {
            val char = iterator.next()
            if (char == '-' && iterator.peek(1) == '-' && iterator.peek(2) == '!' && iterator.peek(offset = 3) == '>') {
                iterator.nextOffset(steps = 4) // skips '-', '!' and '>'
                break
            }
        }

        return Token(CssTokenKind.Comment, start, iterator.offset)
    }

    private fun consumeCssComment(): Token<CssTokenKind> {
        val start = iterator.offset
        iterator.nextOffset() // skips '/' and '*'
        while (iterator.hasNext()) {
            val char = iterator.next()
            if (char == '*' && iterator.peek(1) == '/') {
                iterator.nextOffset(steps = 2) // skips '/'
                break
            }
        }

        return Token(CssTokenKind.Comment, start, iterator.offset)
    }
}
