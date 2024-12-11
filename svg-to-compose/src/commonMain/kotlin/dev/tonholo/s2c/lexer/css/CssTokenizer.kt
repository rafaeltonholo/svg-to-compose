package dev.tonholo.s2c.lexer.css

import dev.tonholo.s2c.extensions.EMPTY
import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.Tokenizer

internal class CssTokenizer : Tokenizer<CssTokenKind> {
    private var offset = 0
    private var input = ""

    override fun tokenize(input: String): Sequence<Token<out CssTokenKind>> = sequence {
        offset = 0
        this@CssTokenizer.input = input
        while (offset < input.length) {
            val char = input[offset]
            println("offset: $offset, char: $char")
            when (char.lowercaseChar()) {
                in CssTokenKind.AtKeyword -> {
                    yield(handleAtKeyword(start = offset))
                }

                in CssTokenKind.WhiteSpace -> {
                    yield(handleWhitespace(start = offset))
                }

                in CssTokenKind.Greater -> {
                    yield(handleDirectToken(kind = CssTokenKind.Greater, start = offset))
                }

                in CssTokenKind.Dot -> {
                    yield(handleDirectToken(kind = CssTokenKind.Dot, start = offset))
                }

                in CssTokenKind.Comma -> {
                    yield(handleDirectToken(kind = CssTokenKind.Comma, start = offset))
                }

                in CssTokenKind.Colon -> {
                    yield(handleDirectToken(kind = CssTokenKind.Colon, start = offset))
                }

                in CssTokenKind.Semicolon -> {
                    yield(handleDirectToken(kind = CssTokenKind.Semicolon, start = offset))
                }

                in CssTokenKind.OpenCurlyBrace -> {
                    yield(handleDirectToken(kind = CssTokenKind.OpenCurlyBrace, start = offset))
                }

                in CssTokenKind.CloseCurlyBrace -> {
                    yield(handleDirectToken(kind = CssTokenKind.CloseCurlyBrace, start = offset))
                }

                in CssTokenKind.OpenParenthesis -> {
                    yield(handleDirectToken(kind = CssTokenKind.OpenParenthesis, start = offset))
                }

                in CssTokenKind.CloseParenthesis -> {
                    yield(handleDirectToken(kind = CssTokenKind.CloseParenthesis, start = offset))
                }

                in CssTokenKind.Hash -> {
                    val next = peek(1)
                    val token = if (next != Char.EMPTY && next.isHexDigit()) {
                        var backwardLookupOffset = 0
                        do {
                            val prev = peek(--backwardLookupOffset)
                            if (prev == Char.EMPTY || prev != ' ') {
                                break
                            }
                        } while (prev == ' ')

                        if (peek(backwardLookupOffset) in CssTokenKind.Colon) {
                            yield(Token(CssTokenKind.Hash, offset, ++offset))
                            handleHexDigit(start = offset)
                        } else {
                            Token(CssTokenKind.Hash, offset, ++offset)
                        }
                    } else {
                        Token(CssTokenKind.Hash, offset, ++offset)
                    }

                    yield(token)
                }

                'u' -> {
                    if (peek(offset = 1) == 'r' && peek(offset = 2) == 'l' && peek(offset = 3) == '(') {
                        yield(handleUrl(start = offset))
                    }
                }

                in '0'..'9' -> {
                    yield(handleNumber(start = offset))
                }

                in CssTokenKind.Quote,
                in CssTokenKind.DoubleQuote -> {
                    yieldStringIfNeeded(start = offset)
                }

                else -> {
                    yield(handleIdentifier(start = offset))
                }
            }
        }
        yield(Token(CssTokenKind.EndOfFile, offset, offset))
    }

    private fun handleDirectToken(kind: CssTokenKind, start: Int): Token<CssTokenKind> {
        val token = Token(kind, start, offset + 1)
        offset++
        return token
    }

    private fun handleAtKeyword(start: Int): Token<CssTokenKind.AtKeyword> {
        while (offset < input.length) {
            val char = input[++offset]
            if (char == ' ') {
                break
            }
        }
        return Token(CssTokenKind.AtKeyword, start, offset)
    }

    private fun handleWhitespace(start: Int): Token<CssTokenKind> {
        offset++
        while (offset < input.length) {
            val char = input[offset]
            if (char !in CssTokenKind.WhiteSpace) {
                break
            }
            offset++
        }

        return Token(CssTokenKind.WhiteSpace, start, offset)
    }

    private fun handleIdentifier(start: Int): Token<CssTokenKind> {
        while (offset < input.length) {
            val char = input[offset]
            when (char) {
                in '0'..'9',
                in 'a'..'z',
                in 'A'..'Z',
                '-',
                '_' -> {
                    offset++
                }

                else -> {
                    break
                }
            }
        }

        return if (
            offset < input.length && input[offset] == '(' &&
            start - 1 > 0 && input[start - 1] in CssTokenKind.WhiteSpace
        ) {
            return Token(CssTokenKind.Function, start, offset)
        } else {
            Token(CssTokenKind.Ident, start, offset)
        }
    }

    private fun handleNumber(start: Int): Token<CssTokenKind> {
        while (offset < input.length) {
            val char = input[offset]
            when (char.lowercaseChar()) {
                '.',
                'e',
                '+',
                '-',
                in '0'..'9' -> {
                    offset++
                }

                else -> {
                    break
                }
            }
        }

        // find if dimension is present
        var dimension = ""
        val startDimension = offset
        while (offset < input.length) {
            val char = input[offset++]
            if (
                char in CssTokenKind.OpenParenthesis ||
                char in CssTokenKind.CloseParenthesis ||
                char in CssTokenKind.WhiteSpace ||
                char in CssTokenKind.Comma ||
                char in CssTokenKind.Semicolon
            ) {
                offset--
                break
            }
            dimension += char
        }

        if (dimension.isEmpty()) {
            offset = startDimension
        }

        return when {
            input[offset + 1] in CssTokenKind.Percent -> Token(CssTokenKind.Percentage, start, offset + 2)
            dimension.isNotBlank() -> Token(CssTokenKind.Dimension(dimension), start, offset)
            else -> Token(CssTokenKind.Number, start, offset)
        }
    }

    private fun handleUrl(start: Int): Token<CssTokenKind> {
        val urlContentStart = advance(steps = 4)
        var contentOffset = urlContentStart

        // § 4.3.6. Consume as much whitespace as possible.
        var char = input[contentOffset]
        while (char in CssTokenKind.WhiteSpace) {
            char = input[++contentOffset]
        }

        // If the next one or two input code points are U+0022 QUOTATION MARK ("), U+0027 APOSTROPHE ('),
        // or whitespace followed by U+0022 QUOTATION MARK (") or U+0027 APOSTROPHE ('),
        // then create a <function-token> with its value set to string and return it.
        if (input[contentOffset] in CssTokenKind.Quote || input[contentOffset] in CssTokenKind.DoubleQuote) {
            // Rewind to process the '(' token.
            rewind()
            return Token(kind = CssTokenKind.Function, startOffset = start, endOffset = offset)
        }

        // § 4.3.6. Consume a url token
        // Note: This algorithm assumes that the initial "url(" has already been consumed.
        // This algorithm also assumes that it’s being called to consume an "unquoted" value, like url(foo).
        // A quoted value, like url("foo"), is parsed as a <function-token>. Consume an ident-like token
        // automatically handles this distinction; this algorithm shouldn’t be called directly otherwise.
        advance(contentOffset - urlContentStart)

        while (offset < input.length) {
            char = input[offset++]
            if (char in CssTokenKind.CloseParenthesis) {
                break
            }
        }

        return Token(
            kind = if (char !in CssTokenKind.CloseParenthesis) {
                CssTokenKind.BadUrl
            } else {
                CssTokenKind.Url
            },
            startOffset = start,
            endOffset = offset,
        )
    }

    private fun handleHexDigit(start: Int): Token<CssTokenKind> {
        while (offset < input.length) {
            val char = input[offset]
            when (char.lowercaseChar()) {
                in '0'..'9',
                in 'a'..'f' -> {
                    offset++
                }

                else -> break
            }
        }

        return Token(CssTokenKind.HexDigit, start, offset)
    }

    private suspend fun SequenceScope<Token<out CssTokenKind>>.yieldStringIfNeeded(start: Int) {
        var currentOffset = start + 1
        while (currentOffset < input.length) {
            val current = input[currentOffset++]
            when (current) {
                in CssTokenKind.Quote,
                in CssTokenKind.DoubleQuote -> {
                    break
                }

                '\n', in CssTokenKind.Semicolon -> {
                    return
                }
            }
        }

        yield(Token(CssTokenKind.String, start, currentOffset))
        offset = currentOffset
    }

    private fun peek(offset: Int): Char {
        val peekIndex = this.offset + offset
        return if (peekIndex < 0 || peekIndex >= input.length) {
            Char.EMPTY
        } else {
            input[peekIndex]
        }
    }

    private fun advance(steps: Int = 1): Int {
        offset += steps
        return offset
    }

    private fun rewind(steps: Int = 1) {
        offset -= steps
    }

    private fun Char.isHexDigit(): Boolean {
        return this in '0'..'9' || this in 'a'..'f' || this in 'A'..'F'
    }
}
