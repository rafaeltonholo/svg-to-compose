package dev.tonholo.s2c.lexer.css.token.consumer

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.TokenIterator
import dev.tonholo.s2c.lexer.css.CssTokenKind

internal class NumberTokenConsumer(
    iterator: TokenIterator<CssTokenKind>,
) : TokenConsumer(iterator) {
    override val supportedTokenKinds: Set<CssTokenKind> = setOf(
        CssTokenKind.Number,
    )

    override fun accept(kind: CssTokenKind): Boolean {
        return super.accept(kind) || kind == CssTokenKind.Dot && iterator.peek(1).isDigit()
    }

    override fun consume(kind: CssTokenKind): List<Token<out CssTokenKind>> {
        val start = iterator.offset
        while (iterator.hasNext()) {
            val char = iterator.get()
            when (char.lowercaseChar()) {
                '.',
                'e',
                '+',
                '-',
                in '0'..'9' -> {
                    iterator.nextOffset()
                }

                else -> {
                    break
                }
            }
        }

        // find if dimension is present
        var dimension = ""
        val startDimension = iterator.offset
        while (iterator.hasNext()) {
            val char = iterator.get()
            iterator.nextOffset()
            if (isEndOfNumber(char)) {
                iterator.rewind()
                break
            }
            dimension += char
        }

        if (dimension.isEmpty()) {
            iterator.moveTo(startDimension)
        }

        return listOf(
            when {
                iterator.peek(1) in CssTokenKind.Percent -> Token(CssTokenKind.Percentage, start, iterator.offset + 2)
                dimension.isNotBlank() -> Token(CssTokenKind.Dimension, start, iterator.offset)
                else -> Token(CssTokenKind.Number, start, iterator.offset)
            },
        )
    }

    private fun isEndOfNumber(char: Char): Boolean = char in CssTokenKind.OpenParenthesis ||
        char in CssTokenKind.CloseParenthesis ||
        char in CssTokenKind.WhiteSpace ||
        char in CssTokenKind.Comma ||
        char in CssTokenKind.Semicolon ||
        char in CssTokenKind.CloseCurlyBrace
}
