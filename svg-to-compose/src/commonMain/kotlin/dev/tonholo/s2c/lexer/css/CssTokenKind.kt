package dev.tonholo.s2c.lexer.css

import dev.tonholo.s2c.lexer.TokenKind

sealed class CssTokenKind(
    override val representation: kotlin.String,
) : TokenKind {
    data object EndOfFile : CssTokenKind(representation = "")
    data object WhiteSpace : CssTokenKind(representation = " \n\t") {
        val significantAdjacentTokens = setOf(
            Ident,
            Hash,
            Dot,
            Number,
            String,
            Function,
            Url,
            OpenParenthesis,
            CloseParenthesis,
            OpenSquareBracket,
            CloseSquareBracket,
        )
    }

    data object AtKeyword : CssTokenKind(representation = "@")
    data object Dot : CssTokenKind(representation = ".")
    data object Asterisk : CssTokenKind(representation = "*")
    data object Tilde : CssTokenKind(representation = "~")
    data object Plus : CssTokenKind(representation = "+")
    data object Colon : CssTokenKind(representation = ":")
    data object Semicolon : CssTokenKind(representation = ";")
    data object Comma : CssTokenKind(representation = ",")
    data object Greater : CssTokenKind(representation = ">")
    data object Percent : CssTokenKind(representation = "%")
    data object Bang : CssTokenKind(representation = "!")
    data object Ident : CssTokenKind(representation = "")
    data object Hash : CssTokenKind(representation = "#")

    // Decimal or float
    data object Number : CssTokenKind(representation = "")
    data class Dimension(
        val unit: kotlin.String,
    ) : CssTokenKind(representation = "px|em|rem|cm|mm|in|pt|pc|vh|vw|vmin|vmax|ch|ex") {
        companion object {
            override fun toString(): kotlin.String {
                return "Dimension"
            }
        }
    }

    data object Percentage : CssTokenKind(representation = "%")
    data object HexDigit : CssTokenKind(representation = "")
    data object String : CssTokenKind(representation = "")

    // Missing end quote, mismatched quotes (missing start quote will yield one or more identifiers)
    data object InvalidString : CssTokenKind(representation = "")

    // "URL(" string  - note that space between URL and ( is not allowed
    data object Url : CssTokenKind(representation = "url")
    data object BadUrl : CssTokenKind(representation = "")

    data object DoubleColon : CssTokenKind(representation = "::")
    data object OpenCurlyBrace : CssTokenKind(representation = "{")
    data object CloseCurlyBrace : CssTokenKind(representation = "}")
    data object OpenSquareBracket : CssTokenKind(representation = "[")
    data object CloseSquareBracket : CssTokenKind(representation = "]")
    data object OpenParenthesis : CssTokenKind(representation = "(")
    data object CloseParenthesis : CssTokenKind(representation = ")")
    data object HtmlComment : CssTokenKind(representation = "")
    data object MultilineComment : CssTokenKind(representation = "")
    data object SingleComment : CssTokenKind(representation = "")
    data object Function : CssTokenKind(representation = "")
    data object Quote : CssTokenKind(representation = "'")
    data object DoubleQuote : CssTokenKind(representation = "\"")
}
