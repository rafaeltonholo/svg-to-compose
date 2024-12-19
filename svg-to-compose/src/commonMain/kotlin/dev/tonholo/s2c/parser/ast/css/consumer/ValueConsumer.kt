package dev.tonholo.s2c.parser.ast.css.consumer

import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.css.syntax.node.CssLocation
import dev.tonholo.s2c.parser.ast.css.syntax.node.Value
import dev.tonholo.s2c.parser.ast.iterator.parserError

private val colorFunctions = setOf(
    "rgb",
    "rgba",
    "hsl",
    "hsla",
)

/**
 * Consumes a CSS value from the given iterator and builds a [Value] object.
 *
 * This class is responsible for parsing and consuming different types of CSS values
 * from a token stream. It handles identifiers, numbers, dimensions, percentages,
 * strings, functions, URLs, and colors.
 *
 * @param content The CSS content string being parsed.
 */
internal class ValueConsumer(
    content: String,
) : Consumer<Value>(content) {
    override fun consume(iterator: Iterator): Value {
        val current = iterator.expectTokenNotNull()
        return when (current.kind) {
            CssTokenKind.BadUrl -> iterator.parserError(
                content,
                "Incomplete URL value: missing closing ')' in 'url(...)",
            )

            CssTokenKind.Ident -> iterator.parseIdentToken()
            CssTokenKind.Number -> iterator.parseNumberToken()
            CssTokenKind.Dimension -> iterator.parseDimensionToken()
            CssTokenKind.Percentage -> iterator.parsePercentageToken()
            CssTokenKind.String -> iterator.parseStringToken()
            CssTokenKind.Function -> iterator.parseFunction()
            CssTokenKind.Url -> iterator.parseUrl()
            CssTokenKind.Hash -> iterator.parseColor()
            else -> iterator.parserError(
                content = content,
                message = "Unexpected token: $current",
                backtrack = 2,
                forward = 2,
            )
        }
    }

    /**
     * Consumes an identifier value.
     */
    private fun Iterator.parseIdentToken(): Value.Identifier {
        val current = expectToken(kind = CssTokenKind.Ident)
        return Value.Identifier(
            location = CssLocation(
                source = content.substring(startIndex = current.startOffset, endIndex = current.endOffset),
                start = current.startOffset,
                end = current.endOffset,
            ),
            name = content.substring(startIndex = current.startOffset, endIndex = current.endOffset),
        )
    }

    /**
     * Consumes a number value.
     */
    private fun Iterator.parseNumberToken(): Value {
        val current = expectToken(kind = CssTokenKind.Number)
        return Value.Number(
            location = CssLocation(
                source = content.substring(startIndex = current.startOffset, endIndex = current.endOffset),
                start = current.startOffset,
                end = current.endOffset,
            ),
            value = content.substring(startIndex = current.startOffset, endIndex = current.endOffset),
        )
    }

    /**
     * Consumes a dimension value.
     */
    private fun Iterator.parseDimensionToken(): Value.Dimension {
        val current = expectToken(kind = CssTokenKind.Dimension)
        val value = content.substring(startIndex = current.startOffset, endIndex = current.endOffset)
        var unit = value.takeLastWhile { char -> char.isLetter() }
        return Value.Dimension(
            location = CssLocation(
                source = value,
                start = current.startOffset,
                end = current.endOffset,
            ),
            value = value.removeSuffix(unit),
            unit = unit,
        )
    }

    /**
     * Consumes a percentage value.
     */
    private fun Iterator.parsePercentageToken(): Value.Percentage {
        val current = expectToken(kind = CssTokenKind.Percentage)
        return Value.Percentage(
            location = CssLocation(
                source = content.substring(startIndex = current.startOffset, endIndex = current.endOffset),
                start = current.startOffset,
                end = current.endOffset,
            ),
            value = content.substring(startIndex = current.startOffset, endIndex = current.endOffset),
        )
    }

    /**
     * Consumes a string value.
     */
    private fun Iterator.parseStringToken(): Value.String {
        val current = expectToken(kind = CssTokenKind.String)
        return Value.String(
            location = CssLocation(
                source = content.substring(startIndex = current.startOffset, endIndex = current.endOffset),
                start = current.startOffset,
                end = current.endOffset,
            ),
            value = content.substring(startIndex = current.startOffset, endIndex = current.endOffset),
        )
    }

    /**
     * Consumes a function value.
     */
    private fun Iterator.parseFunction(): Value {
        val current = expectToken(kind = CssTokenKind.Function)
        val name = content.substring(startIndex = current.startOffset, endIndex = current.endOffset)
        if (name in colorFunctions) {
            return parseColor()
        } else if (name == "url") {
            return parseUrl()
        }

        val arguments = mutableListOf<Value>()
        expectNextToken(kind = CssTokenKind.OpenParenthesis)
        do {
            val next = expectNextTokenNotNull()
            if (next.kind == CssTokenKind.Comma || next.kind == CssTokenKind.WhiteSpace) {
                continue
            }
            if (next.kind == CssTokenKind.CloseParenthesis) {
                break
            }
            arguments += consume(iterator = this)
        } while (next.kind != CssTokenKind.CloseParenthesis)
        val last = arguments.last()
        val endOffset = last.location.end + 1
        return Value.Function(
            location = CssLocation(
                source = content.substring(startIndex = current.startOffset, endIndex = endOffset),
                start = current.startOffset,
                end = endOffset,
            ),
            name = name,
            arguments = arguments,
        )
    }

    /**
     * Consumes a URL value.
     */
    private fun Iterator.parseUrl(): Value.Url {
        val current = expectTokenNotNull()
        if (current.kind == CssTokenKind.Url) {
            val urlContentStartOffset = current.startOffset + 4
            val urlContentEndOffset = current.endOffset - 1
            return Value.Url(
                location = CssLocation(
                    source = content.substring(startIndex = urlContentStartOffset, endIndex = urlContentEndOffset),
                    start = urlContentStartOffset,
                    end = urlContentEndOffset,
                ),
                value = content.substring(startIndex = urlContentStartOffset, endIndex = urlContentEndOffset),
            )
        }

        expectToken(kind = CssTokenKind.Function)

        var endUrlOffset = current.endOffset
        var steps = 1
        while (hasNext()) {
            val next = expectNextTokenNotNull()
            when (next.kind) {
                CssTokenKind.CloseParenthesis -> break
                CssTokenKind.Semicolon, CssTokenKind.CloseCurlyBrace -> {
                    parserError(
                        content = content,
                        message = "Incomplete URL. A URL must have",
                        backtrack = steps,
                        forward = 0
                    )
                }

                else -> {
                    steps++
                    endUrlOffset = next.endOffset
                }
            }
        }

        return Value.Url(
            location = CssLocation(
                source = content.substring(startIndex = current.endOffset, endIndex = endUrlOffset),
                start = current.endOffset,
                end = endUrlOffset,
            ),
            value = content.substring(startIndex = current.endOffset, endIndex = endUrlOffset),
        )
    }

    /**
     * Consumes a color value.
     */
    private fun Iterator.parseColor(): Value.Color {
        val current = expectTokenNotNull()
        return when (current.kind) {
            CssTokenKind.Hash -> {
                val next = expectNextToken(kind = CssTokenKind.HexDigit)
                Value.Color(
                    location = CssLocation(
                        source = content.substring(startIndex = current.startOffset, endIndex = next.endOffset),
                        start = current.startOffset,
                        end = next.endOffset,
                    ),
                    value = content.substring(startIndex = current.startOffset, endIndex = next.endOffset),
                )
            }

            CssTokenKind.Function -> {
                expectNextToken(kind = CssTokenKind.OpenParenthesis)
                var colorEndOffset = current.endOffset
                while (hasNext()) {
                    val next = expectNextTokenNotNull()
                    if (next.kind == CssTokenKind.CloseParenthesis) {
                        break
                    }
                    colorEndOffset = next.endOffset
                }
                // account ) in color function
                colorEndOffset++
                Value.Color(
                    location = CssLocation(
                        source = content.substring(startIndex = current.startOffset, endIndex = colorEndOffset),
                        start = current.startOffset,
                        end = colorEndOffset,
                    ),
                    value = content.substring(startIndex = current.startOffset, endIndex = colorEndOffset),
                )
            }

            else -> parserError(content = content, message = "Unexpected token: $current")
        }
    }
}
