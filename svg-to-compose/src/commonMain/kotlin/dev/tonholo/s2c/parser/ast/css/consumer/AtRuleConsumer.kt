package dev.tonholo.s2c.parser.ast.css.consumer

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.css.CssAstParser
import dev.tonholo.s2c.parser.ast.css.CssAtRule
import dev.tonholo.s2c.parser.ast.css.CssComponent

internal class AtRuleConsumer(
    content: String,
    parser: CssAstParser,
) : CssConsumer<CssAtRule>(content, parser) {
    override fun consume(token: Token<out CssTokenKind>): CssAtRule {
        check(token.kind is CssTokenKind.AtKeyword) {
            parser.buildErrorMessage(message = "Expected @rule but got ${token.kind}", backtrack = 1, forward = 2)
        }

        var last: Token<out CssTokenKind>? = null
        while (true) {
            val next = parser.next()
            if (next == null || next.kind is CssTokenKind.Semicolon || next.kind is CssTokenKind.OpenCurlyBrace) {
                break
            }
            last = next
        }
        checkNotNull(last) {
            parser.buildErrorMessage(
                message = "Incomplete @rule.",
                backtrack = 1,
                forward = 2,
            )
        }

        val nestedRules = parser.parseRules()
        return CssAtRule(
            name = content.substring(token.startOffset, token.endOffset),
            components = listOf(
                CssComponent.AtRule(
                    value = content.substring(token.endOffset + 1, last.endOffset)
                )
            ),
            rules = nestedRules,
        )
    }
}
