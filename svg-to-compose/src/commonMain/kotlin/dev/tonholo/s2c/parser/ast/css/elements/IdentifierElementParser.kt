package dev.tonholo.s2c.parser.ast.css.elements

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.css.CssDeclaration
import dev.tonholo.s2c.parser.ast.css.CssElement
import dev.tonholo.s2c.parser.ast.css.CssRootNode
import dev.tonholo.s2c.parser.ast.css.CssSelector
import dev.tonholo.s2c.parser.ast.css.CssSelectorType
import dev.tonholo.s2c.parser.ast.css.selectorStarters
import dev.tonholo.s2c.parser.ast.css.selectors.SelectorParser
import dev.tonholo.s2c.parser.ast.css.selectors.createAggregateSelectorParser
import dev.tonholo.s2c.parser.ast.css.terminalTokens
import dev.tonholo.s2c.parser.ast.iterator.AstParserIterator

internal class IdentifierElementParser(
    private val content: String,
    private val iterator: AstParserIterator<CssTokenKind, CssRootNode>,
    private val propertyParser: PropertyParser,
    private val selectorParser: SelectorParser,
    private val buildErrorMessage: (message: String, backtrack: Int, forward: Int) -> String,
) : CssElementParser<CssElement> {
    override fun parse(starterToken: Token<out CssTokenKind>): CssElement? {
        check(starterToken.kind is CssTokenKind.Identifier) {
            buildErrorMessage(
                "Expected ${CssTokenKind.Identifier} but found ${starterToken.kind}",
                2,
                2,
            )
        }
        val token = iterator.next()
        check(token != null) {
            buildErrorMessage(
                "Expected token but found null",
                2,
                2,
            )
        }
        val value = content.substring(starterToken.startOffset, starterToken.endOffset)

        return when (token.kind) {
            in terminalTokens -> {
                CssSelector.Single(
                    type = CssSelectorType.Tag,
                    value = value,
                )
            }

            in selectorStarters -> {
                iterator.rewind()
                selectorParser.createAggregateSelectorParser(
                    iterator,
                    CssSelector.Single(
                        type = CssSelectorType.Tag,
                        value = value,
                    ),
                )
            }

            is CssTokenKind.Colon -> {
                val propertyValueToken = iterator.next()
                val propertyValue = propertyValueToken?.let(propertyParser::parse)
                CssDeclaration(
                    property = value,
                    value = requireNotNull(propertyValue) {
                        buildErrorMessage(
                            "Incomplete property '$value' value",
                            2,
                            2,
                        )
                    },
                )
            }

            else -> null
        }
    }
}
