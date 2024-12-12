package dev.tonholo.s2c.lexer.css

import dev.tonholo.s2c.lexer.TokenKind
import dev.tonholo.s2c.lexer.css.CssTokenKind.entries

/**
 * Represents the different kinds of tokens that can be encountered
 * in a CSS document.
 *
 * @see <a href="https://www.w3.org/TR/css-syntax-3/#tokenizer-definitions">CSS Syntax Token Definition</a>
 */
enum class CssTokenKind(
    override val representation: Set<Char> = emptySet(),
) : TokenKind {
    EndOfFile,
    WhiteSpace(representation = setOf(' ', '\n', '\t')),
    AtKeyword(representation = setOf('@')),
    Dot(representation = setOf('.')),
    Asterisk(representation = setOf('*')),
    Tilde(representation = setOf('~')),
    Plus(representation = setOf('+')),
    Colon(representation = setOf(':')),
    Semicolon(representation = setOf(';')),
    Comma(representation = setOf(',')),
    Greater(representation = setOf('>')),
    Percent(representation = setOf('%')),
    Bang(representation = setOf('!')),
    Ident,
    Hash(representation = setOf('#')),

    // Decimal or float
    Number,
    Dimension,
    Percentage(representation = setOf('%')),
    HexDigit,
    String,

    // Missing end quote, mismatched quotes (missing start quote will yield one or more identifiers)
    InvalidString,

    // "URL(" string  - note that space between URL and ( is not allowed
    Url,
    BadUrl,

    DoubleColon,
    OpenCurlyBrace(representation = setOf('{')),
    CloseCurlyBrace(representation = setOf('}')),
    OpenSquareBracket(representation = setOf('[')),
    CloseSquareBracket(representation = setOf(']')),
    OpenParenthesis(representation = setOf('(')),
    CloseParenthesis(representation = setOf(')')),
    HtmlComment,
    MultilineComment,
    SingleComment,
    Function,
    Quote(representation = setOf('\'')),
    DoubleQuote(representation = setOf('"')),
    ;

    companion object {
        /**
         * A set of significant adjacent token types where [WhiteSpace] is
         * between.
         *
         * This means that adding or removing whitespace between two tokens
         * of these types can change the meaning of the CSS.
         *
         * For example, `div.class` is different from `div .class`.
         *
         * This set is used to determine when whitespace should be preserved or
         * removed during CSS parsing.
         */
        val WhiteSpaceSignificantAdjacentTokens = setOf(
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

        /**
         * Retrieves the [CssTokenKind] associated with the given character.
         *
         * This function searches through the entries of the enum class [CssTokenKind]
         * and returns the first entry where the given [Char] is contained within the
         * entry's [CssTokenKind.representation].
         *
         * @param char The character to find the corresponding [CssTokenKind] for.
         * @return The [CssTokenKind] associated with the given [char], or `null` if no
         * match is found.
         */
        fun fromChar(char: Char): CssTokenKind? {
            return entries.firstOrNull { char in it }
        }
    }
}
