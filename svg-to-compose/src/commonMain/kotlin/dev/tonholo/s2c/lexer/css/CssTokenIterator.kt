package dev.tonholo.s2c.lexer.css

import dev.tonholo.s2c.lexer.TokenIterator

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
            char == 'u' && peek(1) == 'r' && peek(2) == 'l' && peek(offset = 3) == '(' -> {
                nextOffset(steps = 4)
                CssTokenKind.Url
            }

            isNumber(char) -> {
                CssTokenKind.Number
            }

            char == '/' && peek(1) == '*' -> CssTokenKind.Comment
            char == '<' && peek(1) == '!' && peek(2) == '-' && peek(3) == '-' -> CssTokenKind.CDO
            char == '-' && peek(1) == '-' && peek(2) == '!' && peek(3) == '>' -> CssTokenKind.CDC

            else -> {
                CssTokenKind.fromChar(char) ?: CssTokenKind.Ident
            }
        }
    }

    private fun isNumber(char: Char): Boolean {
        return char in '0'..'9' ||
                (char == '.' && (peek(-1) == ':' || peek(-1).isWhitespace()) &&
                        peek(1) in '0'..'9')
    }
}
