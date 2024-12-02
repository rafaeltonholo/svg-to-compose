package dev.tonholo.s2c.parser.ast.css.selectors

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.css.CssComponent
import dev.tonholo.s2c.parser.ast.css.CssComponentType
import dev.tonholo.s2c.parser.ast.css.CssRootNode
import dev.tonholo.s2c.parser.ast.css.elements.CssElementParser
import dev.tonholo.s2c.parser.ast.css.selectorStarters
import dev.tonholo.s2c.parser.ast.css.terminalTokens
import dev.tonholo.s2c.parser.ast.iterator.AstParserIterator

internal class SelectorParser(
    private val content: String,
    private val iterator: AstParserIterator<CssTokenKind, CssRootNode>,
    private val buildErrorMessage: (message: String, backtrack: Int, forward: Int) -> String,
) : CssElementParser<CssComponent> {
    override fun parse(starterToken: Token<out CssTokenKind>): CssComponent {
        return when (starterToken.kind) {
            CssTokenKind.Dot -> createSelector(type = CssSelectorType.Class)
            CssTokenKind.Hash -> createSelector(type = CssSelectorType.Id)
            CssTokenKind.Dot -> createSelector(type = CssComponentType.Class)
            CssTokenKind.Hash -> createSelector(type = CssComponentType.Id)
            CssTokenKind.Identifier -> {
                val selector = CssComponent.Single(
                    type = CssComponentType.Tag,
                    value = content.substring(starterToken.startOffset, starterToken.endOffset)
                )
                val nextToken = iterator.peek(0)
                if (nextToken?.kind in terminalTokens || nextToken?.kind in selectorStarters) {
                    selector
                } else {
                    createAggregateSelectorParser(iterator, selector)
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
        val token = requireNotNull(iterator.next()) { "Expected token but found null" }
        check(token.kind is CssTokenKind.Identifier) { "Expected identifier but found ${token.kind}" }
        val next = iterator.peek(0)
        val selector = CssComponent.Single(
            type = type,
            value = content.substring(token.startOffset, token.endOffset),
        )
        return if (next?.kind in terminalTokens || next?.kind in selectorStarters) {
            selector
        } else {
            createAggregateSelectorParser(iterator, selector)
        }
    }
}
