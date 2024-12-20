package dev.tonholo.s2c.lexer.css.token.consumer

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.TokenIterator
import dev.tonholo.s2c.lexer.css.CssTokenKind

internal class UrlTokenConsumer(
    iterator: TokenIterator<CssTokenKind>,
) : TokenConsumer(iterator) {
    override val supportedTokenKinds: Set<CssTokenKind> = setOf(
        CssTokenKind.Url,
    )

    override fun consume(kind: CssTokenKind): List<Token<out CssTokenKind>> {
        val start = iterator.offset - 4
        var contentOffset = 0

        // § 4.3.6. Consume as much whitespace as possible.
        var char = iterator.peek(contentOffset)
        while (char in CssTokenKind.WhiteSpace) {
            char = iterator.peek(++contentOffset)
        }

        // If the next one or two input code points are U+0022 QUOTATION MARK ("), U+0027 APOSTROPHE ('),
        // or whitespace followed by U+0022 QUOTATION MARK (") or U+0027 APOSTROPHE ('),
        // then create a <function-token> with its value set to string and return it.
        if (iterator.peek(contentOffset) in CssTokenKind.Quote ||
            iterator.peek(contentOffset) in CssTokenKind.DoubleQuote
        ) {
            // Rewind to process the '(' token.
            iterator.rewind()
            return listOf(
                Token(
                    kind = CssTokenKind.Function,
                    startOffset = start,
                    endOffset = iterator.offset,
                ),
            )
        }

        // § 4.3.6. Consume a url token
        // Note: This algorithm assumes that the initial "url(" has already been consumed.
        // This algorithm also assumes that it’s being called to consume an "unquoted" value, like url(foo).
        // A quoted value, like url("foo"), is parsed as a <function-token>. Consume an ident-like token
        // automatically handles this distinction; this algorithm shouldn’t be called directly otherwise.
        iterator.nextOffset(contentOffset)

        while (iterator.hasNext()) {
            char = iterator.get()
            iterator.nextOffset()
            if (char in CssTokenKind.CloseParenthesis) {
                break
            }
        }

        return listOf(
            Token(
                kind = if (char !in CssTokenKind.CloseParenthesis) {
                    CssTokenKind.BadUrl
                } else {
                    CssTokenKind.Url
                },
                startOffset = start,
                endOffset = iterator.offset,
            ),
        )
    }
}
