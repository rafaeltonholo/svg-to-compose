package dev.tonholo.s2c.parser.ast.css.selectors

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.css.CssAstParser
import dev.tonholo.s2c.parser.ast.css.CssCombinator
import dev.tonholo.s2c.parser.ast.css.CssComponent
import dev.tonholo.s2c.parser.ast.css.CssComponentType
import dev.tonholo.s2c.parser.ast.css.elements.CssElementParser
import dev.tonholo.s2c.parser.ast.css.selectorStarters
import dev.tonholo.s2c.parser.ast.css.terminalTokens

internal class SelectorParser(
    private val content: String,
    private val parser: CssAstParser,
    private val buildErrorMessage: (message: String, backtrack: Int, forward: Int) -> String,
) : CssElementParser<CssComponent> {
    override fun parse(starterToken: Token<out CssTokenKind>): CssComponent? {
        return when (starterToken.kind) {
            CssTokenKind.Dot -> createSelector(type = CssComponentType.Class)
            CssTokenKind.Hash -> createSelector(type = CssComponentType.Id)
            CssTokenKind.Greater,
            CssTokenKind.Plus,
            CssTokenKind.Tilde,
                -> parse(requireNotNull(parser.next()))

            CssTokenKind.Colon -> {
                parser.rewind()
                parser.parseNext(sibling = null) as? CssComponent
            }

            CssTokenKind.Identifier -> {
                val selector = CssComponent.Single(
                    type = CssComponentType.Tag,
                    value = content.substring(starterToken.startOffset, starterToken.endOffset)
                )
                val nextToken = parser.peek(0)
                when (nextToken?.kind) {
                    in terminalTokens, in selectorStarters -> {
                        selector.copy(combinator = CssCombinator.from(nextToken?.kind))
                    }

                    else -> {
                        createAggregateSelectorParser(parser, selector)
                    }
                }
            }

            else -> error(
                buildErrorMessage(
                    "Expected $selectorStarters but found ${starterToken.kind}",
                    1,
                    1,
                )
            )
        }
    }

    private fun createSelector(type: CssComponentType): CssComponent {
        val token = requireNotNull(parser.next()) { "Expected token but found null" }
        check(token.kind is CssTokenKind.Identifier) { "Expected identifier but found ${token.kind}" }
        val next = parser.peek(0)
        val selector = CssComponent.Single(
            type = type,
            value = content.substring(token.startOffset, token.endOffset),
        )
        return if (next?.kind in terminalTokens || next?.kind in selectorStarters) {
            selector.copy(combinator = CssCombinator.from(next?.kind))
        } else {
            createAggregateSelectorParser(parser, selector)
        }
    }
}
