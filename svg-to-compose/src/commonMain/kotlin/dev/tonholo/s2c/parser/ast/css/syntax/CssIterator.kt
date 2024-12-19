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
) : AstParserIterator<CssTokenKind>(tokens.trim()) {
    companion object {
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
                        (prev == null && token.kind == CssTokenKind.WhiteSpace) || next == null -> {
                            removeLast()
                            i++
                        }

                        (
                            token.kind == CssTokenKind.WhiteSpace &&
                                prev?.kind in CssTokenKind.WhiteSpaceSignificantAdjacentTokens &&
                                next.kind in CssTokenKind.WhiteSpaceSignificantAdjacentTokens
                            ) -> i++

                        token.kind == CssTokenKind.WhiteSpace || token.kind == CssTokenKind.Comment -> {
                            removeLast()
                            i++
                        }

                        else -> i++
                    }
                }
            }
        }
    }
}
