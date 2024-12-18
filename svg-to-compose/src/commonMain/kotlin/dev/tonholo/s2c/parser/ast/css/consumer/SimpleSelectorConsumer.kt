package dev.tonholo.s2c.parser.ast.css.consumer

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.css.CssCombinator
import dev.tonholo.s2c.parser.ast.css.syntax.node.CssLocation
import dev.tonholo.s2c.parser.ast.css.syntax.node.Selector
import dev.tonholo.s2c.parser.ast.css.syntax.parserError
import dev.tonholo.s2c.parser.ast.iterator.AstParserIterator

/**
 * Consumes a simple CSS selectors from the given iterator and builds a [Selector] object.
 *
 * This consumer handles the parsing of individual selector components,
 * such as type selectors, ID selectors, class selectors, attribute selectors,
 * pseudo-class selectors, and pseudo-element selectors. It also handles
 * combinators between selectors.
 *
 * @param content The CSS content being parsed.
 */
internal class SimpleSelectorConsumer(
    content: String,
) : Consumer<Selector>(content) {
    override fun consume(iterator: AstParserIterator<CssTokenKind>): Selector {
        val current = iterator.expectToken(selectorTokens)
        return when (current.kind) {
            CssTokenKind.Ident, CssTokenKind.Asterisk -> Selector.Type(
                location = calculateLocation(current, current),
                name = content.substring(current.startOffset, endIndex = current.endOffset),
                combinator = lookupForCombinator(iterator),
            )

            CssTokenKind.Hash -> {
                val next = iterator.expectNextToken(kind = CssTokenKind.Ident)
                Selector.Id(
                    location = calculateLocation(current, next),
                    name = content.substring(next.startOffset, endIndex = next.endOffset),
                    combinator = lookupForCombinator(iterator),
                )
            }

            CssTokenKind.Dot -> {
                val next = iterator.expectNextToken(kind = CssTokenKind.Ident)
                Selector.Class(
                    location = calculateLocation(current, next),
                    name = content.substring(next.startOffset, endIndex = next.endOffset),
                    combinator = lookupForCombinator(iterator),
                )
            }

            CssTokenKind.OpenSquareBracket -> {
                val next = iterator.expectNextToken(kind = CssTokenKind.Ident)
                Selector.Attribute(
                    location = calculateLocation(current, next),
                    name = content.substring(next.startOffset, endIndex = next.endOffset),
                    matcher = null,
                    value = null,
                    combinator = lookupForCombinator(iterator),
                )
            }

            CssTokenKind.DoubleColon -> {
                val next = iterator.expectNextToken(kind = CssTokenKind.Ident)
                val parameters = buildParameters(iterator)
                val endOffset = if (parameters.isEmpty()) {
                    next.endOffset
                } else {
                    parameters.last().location.end + 1
                }
                Selector.PseudoElement(
                    location = calculateLocation(current.startOffset, endOffset),
                    name = content.substring(next.startOffset, endIndex = next.endOffset),
                    parameters = parameters,
                    combinator = lookupForCombinator(iterator),
                )
            }

            CssTokenKind.Colon -> {
                val next = iterator.expectNextToken(kind = CssTokenKind.Ident)
                val parameters = buildParameters(iterator)
                val endOffset = if (parameters.isEmpty()) {
                    next.endOffset
                } else {
                    parameters.last().location.end + 1
                }
                Selector.PseudoClass(
                    location = calculateLocation(current.startOffset, endOffset),
                    name = content.substring(next.startOffset, endIndex = next.endOffset),
                    parameters = parameters,
                    combinator = lookupForCombinator(iterator),
                )
            }

            else -> iterator.parserError(content, "Unexpected token ${current.kind}")
        }
    }

    private fun lookupForCombinator(iterator: AstParserIterator<CssTokenKind>): CssCombinator? {
        return CssCombinator.from(iterator.peek(steps = 0)?.kind)
    }

    private fun calculateLocation(
        startToken: Token<out CssTokenKind>,
        endToken: Token<out CssTokenKind>,
    ): CssLocation = calculateLocation(startToken.startOffset, endToken.endOffset)

    private fun calculateLocation(
        startOffset: Int,
        endOffset: Int,
    ): CssLocation = CssLocation(
        source = content.substring(startOffset, endOffset),
        start = startOffset,
        end = endOffset,
    )

    private fun buildParameters(iterator: AstParserIterator<CssTokenKind>): List<Selector> {
        if (iterator.expectNextTokenNotNull().kind != CssTokenKind.OpenParenthesis) {
            iterator.rewind()
            return emptyList()
        }
        val parameters = mutableListOf<Selector>()
        while (iterator.hasNext()) {
            val next = iterator.expectNextTokenNotNull()
            if (next.kind == CssTokenKind.CloseParenthesis) {
                break
            }
            parameters += consume(iterator)
        }

        return parameters
    }
}
