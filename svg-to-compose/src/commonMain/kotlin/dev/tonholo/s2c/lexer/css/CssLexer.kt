package dev.tonholo.s2c.lexer.css

import dev.tonholo.s2c.extensions.EMPTY
import dev.tonholo.s2c.lexer.Lexer
import dev.tonholo.s2c.lexer.Token

class CssLexer : Lexer<CssTokenTypes> {
    private var offset = 0
    private var input = ""

    override fun tokenize(input: String): Sequence<Token<out CssTokenTypes>> = sequence {
        offset = 0
        this@CssLexer.input = input
        while (offset < input.length) {
            val char = input[offset]
            println("offset: $offset, char: $char")
            when (char.lowercaseChar()) {
                in CssTokenTypes.WhiteSpace -> {
                    yield(handleWhitespace(start = offset))
                }

                in CssTokenTypes.Dot -> {
                    yield(Token(CssTokenTypes.Dot, offset, offset + 1))
                    offset++
                }

                in CssTokenTypes.Comma -> {
                    yield(Token(CssTokenTypes.Comma, offset, offset + 1))
                    offset++
                }

                in CssTokenTypes.Colon -> {
                    yield(Token(CssTokenTypes.Colon, offset, offset + 1))
                    offset++
                }

                in CssTokenTypes.Semicolon -> {
                    yield(Token(CssTokenTypes.Semicolon, offset, offset + 1))
                    offset++
                }

                in CssTokenTypes.OpenCurlyBrace -> {
                    yield(Token(CssTokenTypes.OpenCurlyBrace, offset, offset + 1))
                    offset++
                }

                in CssTokenTypes.CloseCurlyBrace -> {
                    yield(Token(CssTokenTypes.CloseCurlyBrace, offset, offset + 1))
                    offset++
                }

                in CssTokenTypes.Hash -> {
                    val next = peek(1)
                    val token = if (next != Char.EMPTY && next.isHexDigit()) {
                        var backwardLookupOffset = 0
                        do {
                            val prev = peek(--backwardLookupOffset)
                            if (prev == Char.EMPTY || prev != ' ') {
                                break
                            }
                        } while (prev == ' ')

                        if (peek(backwardLookupOffset) in CssTokenTypes.Colon) {
                            yield(Token(CssTokenTypes.Hash, offset, ++offset))
                            handleHexDigit(start = offset)
                        } else {
                            Token(CssTokenTypes.Hash, offset, ++offset)
                        }
                    } else {
                        Token(CssTokenTypes.Hash, offset, ++offset)
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
        yield(Token(CssTokenTypes.EndOfFile, offset, offset))
    }

    private fun handleWhitespace(start: Int): Token<CssTokenTypes> {
        offset++
        while (offset < input.length) {
            val char = input[offset]
            if (char !in CssTokenTypes.WhiteSpace) {
                break
            }
            offset++
        }

        return Token(CssTokenTypes.WhiteSpace, start, offset)
    }

    private fun handleIdentifier(start: Int): Token<CssTokenTypes> {
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

        return Token(CssTokenTypes.Identifier, start, offset)
    }

    private fun handleNumber(start: Int): Token<CssTokenTypes> {
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

        return Token(CssTokenTypes.Number, start, offset)
    }

    private suspend fun SequenceScope<Token<out CssTokenTypes>>.yieldUrl(start: Int) {
        val urlContentStart = advance(steps = 4)
        yield(Token(CssTokenTypes.StartUrl, start, offset))
        while (offset < input.length && input[offset] !in CssTokenTypes.EndUrl) {
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
                .filterNot { it.type == CssTokenTypes.EndOfFile }
        )
        yield(Token(CssTokenTypes.EndUrl, offset, ++offset))
    }

    private fun handleHexDigit(start: Int): Token<CssTokenTypes> {
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

        return Token(CssTokenTypes.HexDigit, start, offset)
    }

    private fun peek(offset: Int): Char = if (this.offset + offset >= input.length) {
        Char.EMPTY
    } else {
        val index = this.offset + offset
        input[index]
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
