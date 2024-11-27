package dev.tonholo.s2c.lexer.css

import dev.tonholo.s2c.lexer.TokenType

sealed class CssTokenTypes(
    override val representation: String,
) : TokenType {
    data object EndOfFile : CssTokenTypes(representation = "")
    data object WhiteSpace : CssTokenTypes(representation = " \n\t")
    data object At : CssTokenTypes(representation = "@")
    data object Dot : CssTokenTypes(representation = ".")
    data object Asterisk : CssTokenTypes(representation = "*")
    data object Ampersand : CssTokenTypes(representation = "&")
    data object Tilde : CssTokenTypes(representation = "~")
    data object Equals : CssTokenTypes(representation = "=")
    data object Plus : CssTokenTypes(representation = "+")
    data object Minus : CssTokenTypes(representation = "-")
    data object Colon : CssTokenTypes(representation = ":")
    data object Semicolon : CssTokenTypes(representation = ";")
    data object Comma : CssTokenTypes(representation = ",")
    data object Greater : CssTokenTypes(representation = ">")
    data object Or : CssTokenTypes(representation = "|")
    data object Caret : CssTokenTypes(representation = "^")
    data object Dollar : CssTokenTypes(representation = "$")
    data object Slash : CssTokenTypes(representation = "/")
    data object Percent : CssTokenTypes(representation = "%")
    data object Bang : CssTokenTypes(representation = "!")
    data object Identifier : CssTokenTypes(representation = "")
    data object Hash : CssTokenTypes(representation = "#")

    // Decimal or float
    data object Number : CssTokenTypes(representation = "")
    data object HexDigit : CssTokenTypes(representation = "")
    data object StringLiteral : CssTokenTypes(representation = "")
    data object MultilineString : CssTokenTypes(representation = "")

    // Missing end quote, mismatched quotes (missing start quote will yield one or more identifiers)
    data object InvalidString : CssTokenTypes(representation = "")

    // "URL(" string  - note that space between URL and ( is not allowed
    data object StartUrl : CssTokenTypes(representation = "url(")
    data object EndUrl : CssTokenTypes(representation = ")")

    // Unquoted URL
    data object UnquotedUrlString : CssTokenTypes(representation = "")
    data object OneOf : CssTokenTypes(representation = "~=")
    data object ContainsString : CssTokenTypes(representation = "*=")
    data object EndsWith : CssTokenTypes(representation = "$")
    data object BeginsWith : CssTokenTypes(representation = "^=")
    data object ListBeginsWith : CssTokenTypes(representation = "|=")
    data object DoubleColon : CssTokenTypes(representation = "::")
    data object DoublePipe : CssTokenTypes(representation = "||")
    data object OpenCurlyBrace : CssTokenTypes(representation = "{")
    data object CloseCurlyBrace : CssTokenTypes(representation = "}")
    data object OpenSquareBracket : CssTokenTypes(representation = "[")
    data object CloseSquareBracket : CssTokenTypes(representation = "]")
    data object OpenFunctionBrace : CssTokenTypes(representation = "(")
    data object CloseFunctionBrace : CssTokenTypes(representation = ")")
    data object HtmlComment : CssTokenTypes(representation = "")
    data object MultilineComment : CssTokenTypes(representation = "")
    data object SingleComment : CssTokenTypes(representation = "")
    data object Function : CssTokenTypes(representation = "")
    data object Units : CssTokenTypes(representation = "")
    data object Quote : CssTokenTypes(representation = "'")
    data object DoubleQuote : CssTokenTypes(representation = "\"")

    // U+4E00-9FFF, U+30?? see https://www.w3.org/TR/css-fonts-3/#unicode-range-desc
    data object UnicodeRange : CssTokenTypes(representation = "u")
}
