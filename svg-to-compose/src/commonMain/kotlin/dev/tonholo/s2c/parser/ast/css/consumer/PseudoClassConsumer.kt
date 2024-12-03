package dev.tonholo.s2c.parser.ast.css.consumer

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.css.CssAstParser
import dev.tonholo.s2c.parser.ast.css.CssComponent
import dev.tonholo.s2c.parser.ast.css.CssComponentType
import dev.tonholo.s2c.parser.ast.css.elements.CssElementParser

internal class PseudoClassConsumer(
    content: String,
    parser: CssAstParser,
    private val selectorParser: CssElementParser<CssComponent>,
) : CssConsumer<CssComponent>(content, parser) {
    override fun consume(token: Token<out CssTokenKind>): CssComponent {
        check(token.kind is CssTokenKind.Colon) {
            parser.buildErrorMessage(
                message = "Expected colon but found ${token.kind}",
                backtrack = 2,
                forward = 1,
            )
        }
        val pseudoClassName = checkNotNull(parser.next()) {
            parser.buildErrorMessage(
                message = "Expected pseudo class name but found 'null'",
                backtrack = 2,
                forward = 1,
            )
        }
        check(pseudoClassName.kind is CssTokenKind.Identifier) {
            parser.buildErrorMessage(
                message = "Expected ${CssTokenKind.Identifier} but found ${pseudoClassName.kind}",
                backtrack = 2,
                forward = 1,
            )
        }

        val parameters = mutableListOf<CssComponent>()
        val current = parser.next()
        if (current?.kind is CssTokenKind.OpenParenthesis) {
            // parse parameters
            while (true) {
                val next = parser.next()
                if (next == null || next.kind is CssTokenKind.CloseParenthesis) {
                    break
                }
                val parameter = selectorParser.parse(starterToken = next)

                if (parameter != null) {
                    parameters.add(parameter)
                }
            }
        } else {
            parser.rewind()
        }

        return CssComponent.Single(
            type = CssComponentType.PseudoClass(
                parameters = parameters,
            ),
            value = content.substring(token.startOffset, pseudoClassName.endOffset),
        )
    }
}
