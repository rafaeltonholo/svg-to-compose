package dev.tonholo.s2c.parser.ast.css.consumer

import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.css.syntax.node.AtRule
import dev.tonholo.s2c.parser.ast.css.syntax.node.CssLocation
import dev.tonholo.s2c.parser.ast.css.syntax.node.CssStatementNode
import dev.tonholo.s2c.parser.ast.css.syntax.node.QualifiedRule
import dev.tonholo.s2c.parser.ast.css.syntax.node.StyleSheet
import dev.tonholo.s2c.parser.ast.iterator.AstParserIterator

/**
 * Consumes a CSS stylesheet and builds a [StyleSheet] object.
 *
 * This class iterates through the CSS tokens and delegates the consumption of
 * at-rules and qualified rules to specific consumers.
 *
 * @param content The CSS content string.
 * @param atRuleConsumer The consumer responsible for handling at-rules.
 * @param qualifiedRuleConsumer The consumer responsible for handling qualified rules.
 */
internal class StyleSheetConsumer(
    content: String,
    private val atRuleConsumer: Consumer<AtRule>,
    private val qualifiedRuleConsumer: Consumer<QualifiedRule>,
) : Consumer<StyleSheet>(content) {
    override fun consume(iterator: AstParserIterator<CssTokenKind>): StyleSheet {
        val children = mutableListOf<CssStatementNode>()
        val location = CssLocation(
            source = content,
            start = 0,
            end = content.length,
        )
        val styleSheet = StyleSheet(
            location = location,
            children = children,
        )
        while (iterator.hasNext()) {
            val next = iterator.next()
            checkNotNull(next) {
                "Expected token but got null"
            }
            when (next.kind) {
                CssTokenKind.WhiteSpace, CssTokenKind.Semicolon -> Unit
                CssTokenKind.EndOfFile -> return styleSheet
                CssTokenKind.AtKeyword -> children += atRuleConsumer.consume(iterator)
                else -> children += qualifiedRuleConsumer.consume(iterator)
            }
        }
        return styleSheet
    }
}
