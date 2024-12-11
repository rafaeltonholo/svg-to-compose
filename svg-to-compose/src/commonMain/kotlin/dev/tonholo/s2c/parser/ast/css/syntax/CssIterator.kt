package dev.tonholo.s2c.parser.ast.css.syntax

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.iterator.AstParserIterator

/**
 * An iterator for parsing CSS tokens.
 *
 * This iterator wraps a list of CSS tokens and provides methods for navigating
 * and manipulating the token stream. It also performs whitespace trimming to
 * simplify parsing logic.
 *
 * @property tokens The list of CSS tokens to iterate over.
 */
internal class CssIterator(
    tokens: List<Token<out CssTokenKind>>,
) : AstParserIterator<CssTokenKind> {
    internal val tokens: List<Token<out CssTokenKind>> = tokens.trim()
    internal var offset = 0
        private set

    override fun hasNext(): Boolean {
        return offset < tokens.size
    }

    override fun next(): Token<out CssTokenKind>? = if (offset < tokens.size) {
        tokens[offset++]
    } else {
        null
    }

    override fun peek(steps: Int): Token<out CssTokenKind>? =
        tokens.getOrNull(offset + steps)

    override fun rewind(steps: Int) {
        offset -= steps
    }

    private fun List<Token<out CssTokenKind>>.trim(): List<Token<out CssTokenKind>> {
        val tokens = this
        var i = 0
        return buildList {
            while (i < tokens.size) {
                val token = tokens[i]
                add(token)
                val prev = tokens.getOrNull(i - 1)
                val next = tokens.getOrNull(i + 1)
                when {
                    // trim leading or trailing whitespaces
                    (prev == null && token.kind is CssTokenKind.WhiteSpace) || next == null -> {
                        removeLast()
                        i++
                    }

                    (token.kind is CssTokenKind.WhiteSpace &&
                        prev?.kind in CssTokenKind.WhiteSpace.significantAdjacentTokens &&
                        next.kind in CssTokenKind.WhiteSpace.significantAdjacentTokens) -> i++

                    token.kind is CssTokenKind.WhiteSpace -> {
                        removeLast()
                        i++
                    }

                    else -> i++
                }
            }
        }
    }
}
