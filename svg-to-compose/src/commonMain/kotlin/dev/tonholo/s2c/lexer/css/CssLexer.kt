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
                in CssTokenKind.WhiteSpace -> {
                    yield(handleWhitespace(start = offset))
                }

                in CssTokenKind.Dot -> {
                    yield(Token(CssTokenKind.Dot, offset, offset + 1))
                    offset++
                }

                in CssTokenKind.Comma -> {
                    yield(Token(CssTokenKind.Comma, offset, offset + 1))
                    offset++
                }

                in CssTokenKind.Colon -> {
                    yield(Token(CssTokenKind.Colon, offset, offset + 1))
                    offset++
                }

                in CssTokenKind.Semicolon -> {
                    yield(Token(CssTokenKind.Semicolon, offset, offset + 1))
                    offset++
                }

                in CssTokenKind.OpenCurlyBrace -> {
                    yield(Token(CssTokenKind.OpenCurlyBrace, offset, offset + 1))
                    offset++
                }

                in CssTokenKind.CloseCurlyBrace -> {
                    yield(Token(CssTokenKind.CloseCurlyBrace, offset, offset + 1))
                    offset++
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

        return Token(CssTokenKind.Number, start, offset)
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
