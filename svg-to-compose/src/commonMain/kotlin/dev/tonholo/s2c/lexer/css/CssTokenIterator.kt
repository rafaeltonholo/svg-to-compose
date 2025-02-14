package dev.tonholo.s2c.lexer.css

import dev.tonholo.s2c.extensions.EMPTY
import dev.tonholo.s2c.lexer.TokenIterator
import dev.tonholo.s2c.lexer.css.constants.CssFunctionConstants

/**
 * Iterator for CSS tokens.
 */
internal class CssTokenIterator : TokenIterator<CssTokenKind>() {
    /**
     * Gets the kind of the current token.
     * @return The kind of the current token.
     */
    override fun getTokenKind(): CssTokenKind {
        val char = get()
        return when {
            isUrlToken(char) -> {
                nextOffset(steps = 4)
                CssTokenKind.Url
            }

            char.isNumber() -> CssTokenKind.Number
            char.isCommentStart() -> CssTokenKind.Comment
            char.isCDOToken() -> CssTokenKind.CDO
            char.isCDCToken() -> CssTokenKind.CDC
            char.isFunction() -> CssTokenKind.Function

            else -> {
                CssTokenKind.fromChar(char) ?: CssTokenKind.Ident
            }
        }
    }

    private fun Char.isNumber(): Boolean {
        val next = peek(1)
        return when (this) {
            in '0'..'9' -> true
            '.' -> {
                var prevIndex = -1
                var prevNonWhitespace = ' '
                while (prevNonWhitespace.isWhitespace()) {
                    prevNonWhitespace = peek(--prevIndex)
                    if (prevNonWhitespace == Char.EMPTY) {
                        break
                    }
                }
                next.isDigit() && prevNonWhitespace == ':'
            }

            '+', '-' -> next.isDigit() || next == '.'
            else -> false
        }
    }

    private fun Char.isCommentStart(): Boolean =
        this == '/' && peek(1) == '*'

    private fun Char.isCDOToken(): Boolean =
        this == '<' && peek(1) == '!' && peek(2) == '-' && peek(offset = 3) == '-'

    private fun Char.isCDCToken(): Boolean =
        this == '-' && peek(1) == '-' && peek(2) == '!' && peek(offset = 3) == '>'

    private fun isUrlToken(char: Char): Boolean =
        char == 'u' && peek(1) == 'r' && peek(2) == 'l' && peek(offset = 3) == '('

    private fun Char.isFunction(): Boolean {
        val prev = peek(-1)
        if (prev !in CssTokenKind.WhiteSpace && prev !in CssTokenKind.Colon) {
            return false
        }

        return CssFunctionConstants.AllFunctions
            .any { fnName ->
                partialContent(offset, offset + fnName.length) == fnName
            }
    }
}
