package dev.tonholo.s2c.lexer.css

import dev.tonholo.s2c.extensions.EMPTY
import dev.tonholo.s2c.lexer.Lexer
import dev.tonholo.s2c.lexer.Token

internal class CssLexer : Lexer<CssTokenKind> {
    private var offset = 0
    private var input = ""

    override fun tokenize(input: String): Sequence<Token<out CssTokenKind>> = sequence {
        offset = 0
        this@CssLexer.input = input
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
                        yieldUrl(start = offset)
                    }
                }

                in '0'..'9' -> {
                    yield(handleNumber(start = offset))
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

        return Token(CssTokenKind.Identifier, start, offset)
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
                char in CssTokenKind.Semicolon) {
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
            dimension.isNotBlank() -> Token(CssTokenKind.Dimension, start, offset)
            else -> Token(CssTokenKind.Number, start, offset)
        }
    }

    private suspend fun SequenceScope<Token<out CssTokenKind>>.yieldUrl(start: Int) {
        val urlContentStart = advance(steps = 4)
        yield(Token(CssTokenKind.StartUrl, start, offset))
        while (offset < input.length && input[offset] !in CssTokenKind.EndUrl) {
            offset++
        }


        val urlContent = input.substring(startIndex = urlContentStart, offset)
        yieldAll(
            // TODO: find a better way for that.
            CssLexer()
                .tokenize(urlContent)
                .map {
                    it.copy(
                        startOffset = urlContentStart + it.startOffset,
                        endOffset = urlContentStart + it.endOffset,
                    )
                }
                .filterNot { it.kind == CssTokenKind.EndOfFile }
        )
        yield(Token(CssTokenKind.EndUrl, offset, ++offset))
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

    private fun backward(steps: Int = 1) {
        offset -= steps
    }

    private fun Char.isHexDigit(): Boolean {
        return this in '0'..'9' || this in 'a'..'f' || this in 'A'..'F'
    }
}
