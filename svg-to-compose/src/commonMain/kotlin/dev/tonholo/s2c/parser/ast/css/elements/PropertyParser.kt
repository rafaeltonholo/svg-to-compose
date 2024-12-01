package dev.tonholo.s2c.parser.ast.css.elements

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.css.CssRootNode
import dev.tonholo.s2c.parser.ast.css.PropertyValue
import dev.tonholo.s2c.parser.ast.css.terminalTokens
import dev.tonholo.s2c.parser.ast.iterator.AstParserIterator

/**
 * A parser for CSS property values.
 *
 * @param content The CSS content string to parse.
 * @param next A function that provides the next token in the content.
 * @param check A function to perform assertions during parsing. MUST use [kotlin.check].
 */
internal class PropertyParser(
    private val content: String,
    private val iterator: AstParserIterator<CssTokenKind, CssRootNode>,
    private val buildErrorMessage: (message: String, backtrack: Int, forward: Int) -> String,
) : CssElementParser<PropertyValue> {
    /**
     * Parses the property value starting from the [starterToken].
     *
     * This function handles different types of CSS property values, such as colors, strings,
     * identifiers, numbers with optional units, and URLs. It collects these values into a list,
     * which is then used to construct the appropriate [PropertyValue] object.
     *
     * @param starterToken The starting token of the property value.
     * @return A [PropertyValue] representing the parsed value(s), or `null` if parsing fails.
     */
    override fun parse(starterToken: Token<out CssTokenKind>): PropertyValue? {
        var token = starterToken

        // Skip leading whitespaces or colons
        while (token.kind is CssTokenKind.WhiteSpace || token.kind is CssTokenKind.Colon) {
            token = iterator.next() ?: return null
        }

        val propertyTokens = findPropertyTokens(starterToken = token)
        val iterator = propertyTokens.listIterator()
        val values = mutableListOf<PropertyValue>()
        while (iterator.hasNext()) {
            token = iterator.next()
            val value = when (token.kind) {
                is CssTokenKind.Hash -> parseColorValue(iterator, token)
                CssTokenKind.String -> parseStringValue(token)
                CssTokenKind.Identifier -> parseIdentifierValue(token)
                CssTokenKind.Number -> parseNumberValue(iterator, token)
                CssTokenKind.StartUrl -> parseUrlValue(iterator, token)
                else -> null
            }

            if (value != null) {
                values += value
            }
        }

        return when (values.size) {
            0 -> null
            1 -> values.first()
            else -> PropertyValue.Multiple(values)
        }
    }

    private fun findPropertyTokens(starterToken: Token<out CssTokenKind>): List<Token<out CssTokenKind>> {
        val propertyTokens = mutableListOf(starterToken)
        val propertyTerminalTokens = terminalTokens + CssTokenKind.CloseCurlyBrace
        var token: Token<out CssTokenKind>?
        do {
            token = iterator.next()
            if (token == null || token.kind in propertyTerminalTokens) {
                break
            }
            propertyTokens += token
        } while (token?.kind !in propertyTerminalTokens)

        return propertyTokens
    }

    /**
     * Parses a color value starting from a '#' token.
     *
     * This function expects a hexadecimal digit to follow the '#' token, forming a valid color value.
     *
     * @param iterator The iterator over the tokens.
     * @param hashToken The '#' token indicating the start of a color value.
     * @return A [PropertyValue.Color] representing the parsed color value.
     * @throws IllegalStateException if the color value is incomplete or invalid.
     */
    private fun parseColorValue(
        iterator: ListIterator<Token<out CssTokenKind>>,
        hashToken: Token<out CssTokenKind>,
    ): PropertyValue.Color {
        check(hashToken.kind is CssTokenKind.Hash) {
            buildErrorMessage(
                "Expected token of ${CssTokenKind.Hash} but got token of ${hashToken.kind} instead.",
                2,
                2,
            )
        }
        // Ensure there's a hex digit after the hash
        check(iterator.hasNext()) {
            buildErrorMessage(
                "Incomplete color value after '#' at position ${hashToken.startOffset}",
                2,
                2,
            )
        }
        // Ensure next token is a hexadecimal digit
        val hexDigit = iterator.next()
        check(hexDigit.kind is CssTokenKind.HexDigit) {
            buildErrorMessage(
                "Expected ${CssTokenKind.HexDigit} after '#' but found ${hexDigit.kind} " +
                    "at position ${hexDigit.startOffset}",
                2,
                2,
            )
        }
        return PropertyValue.Color(content.substring(hashToken.startOffset, hexDigit.endOffset))
    }

    /**
     * Parses a string literal value.
     *
     * This function handles string values enclosed in quotes.
     *
     * @param token The token representing the string literal.
     * @return A [PropertyValue.String] representing the parsed string value.
     */
    private fun parseStringValue(token: Token<out CssTokenKind>): PropertyValue.String {
        check(token.kind is CssTokenKind.String) {
            buildErrorMessage(
                "Expected token of ${CssTokenKind.String} but got token of ${token.kind} instead.",
                2,
                2,
            )
        }
        return PropertyValue.String(content.substring(token.startOffset, token.endOffset))
    }

    /**
     * Parses an identifier value.
     *
     * Identifiers can be property values like 'auto', 'inherit', or any custom identifier.
     *
     * @param token The token representing the identifier.
     * @return A [PropertyValue.Identifier] representing the parsed identifier.
     */
    private fun parseIdentifierValue(token: Token<out CssTokenKind>): PropertyValue.Identifier {
        check(token.kind is CssTokenKind.Identifier) {
            buildErrorMessage(
                "Expected token of ${CssTokenKind.Identifier} but got token of ${token.kind} instead.",
                2,
                2,
            )
        }
        return PropertyValue.Identifier(content.substring(token.startOffset, token.endOffset))
    }

    /**
     * Parses a numerical value, potentially followed by units.
     *
     * This function handles numbers like '16', '1.5', and numbers with units like '16px', '1.5em'.
     *
     * @param iterator The iterator over the tokens.
     * @param numberToken The token representing the number.
     * @return A [PropertyValue.Number] representing the parsed numerical value.
     */
    private fun parseNumberValue(
        iterator: ListIterator<Token<out CssTokenKind>>,
        numberToken: Token<out CssTokenKind>
    ): PropertyValue.Number {
        check(numberToken.kind is CssTokenKind.Number) {
            buildErrorMessage(
                "Expected token of ${CssTokenKind.Number} but got token of ${numberToken.kind} instead.",
                2,
                2,
            )
        }
        val unitsToken = if (iterator.hasNext()) iterator.next() else null
        val units = if (unitsToken != null && unitsToken.kind is CssTokenKind.Identifier) {
            content.substring(unitsToken.startOffset, unitsToken.endOffset)
        } else {
            // Move iterator back if the next token is not a unit
            if (unitsToken != null) iterator.previous()
            null
        }
        return PropertyValue.Number(
            value = content.substring(numberToken.startOffset, numberToken.endOffset),
            units = units,
        )
    }

    /**
     * Parses a URL value starting from an 'url(' token.
     *
     * This function collects all content until it finds a matching closing parenthesis.
     *
     * @param iterator The iterator over the tokens.
     * @param token The token representing the 'url(' token.
     * @return A [PropertyValue.Url] representing the parsed URL value.
     * @throws IllegalStateException if the URL value is incomplete (missing closing parenthesis).
     */
    private fun parseUrlValue(
        iterator: ListIterator<Token<out CssTokenKind>>,
        token: Token<out CssTokenKind>,
    ): PropertyValue.Url {
        check(token.kind is CssTokenKind.StartUrl) {
            buildErrorMessage(
                "Expected token of ${CssTokenKind.StartUrl} but got token of ${token.kind} instead.",
                2,
                2,
            )
        }
        check(iterator.hasNext()) {
            buildErrorMessage(
                "Incomplete URL value.",
                2,
                2,
            )
        }

        val startOffset = iterator.next().startOffset
        var endOffset: Int
        do {
            check(iterator.hasNext()) {
                buildErrorMessage(
                    "Incomplete URL value: missing closing ')' in 'url(...)'",
                    2,
                    2,
                )
            }
            val next = iterator.next()
            endOffset = next.endOffset
        } while (next.kind != CssTokenKind.EndUrl)

        return PropertyValue.Url(value = content.substring(startOffset, endOffset - 1))
    }
}
