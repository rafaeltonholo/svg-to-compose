package dev.tonholo.s2c.parser.ast.css.consumer

import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.css.CssCombinator
import dev.tonholo.s2c.parser.ast.css.syntax.node.CssLocation
import dev.tonholo.s2c.parser.ast.css.syntax.node.Selector
import dev.tonholo.s2c.parser.ast.css.syntax.node.SelectorListItem
import dev.tonholo.s2c.parser.ast.iterator.AstParserIterator

internal val selectorTokens = setOf(
    CssTokenKind.Ident,
    CssTokenKind.Hash,
    CssTokenKind.Asterisk,
    CssTokenKind.Colon,
    CssTokenKind.Dot,
    CssTokenKind.OpenSquareBracket,
    CssTokenKind.Colon,
    CssTokenKind.DoubleColon,
)

/**
 * Consumes a selector list item, which is a comma-separated list of selectors
 * from the given iterator and builds a [SelectorListItem] object.
 *
 * For example:
 * ```css
 * h1, h2 { color: red; }
 * ```
 *
 * In this case, `h1` and `h2` are two selector list items.
 *
 * @param content The entire CSS content string.
 * @param simpleSelectorConsumer A consumer responsible for consuming individual selectors.
 */
internal class SelectorListItemConsumer(
    content: String,
    private val simpleSelectorConsumer: Consumer<Selector>,
) : Consumer<SelectorListItem>(content) {
    override fun consume(iterator: AstParserIterator<CssTokenKind>): SelectorListItem {
        val current = iterator.expectToken(selectorTokens)
        val location = CssLocation.Undefined
        val selectors = mutableListOf<Selector>()
        val selectorListItem = SelectorListItem(
            location = location,
            selectors = selectors,
        )

        // re-consume current token.
        iterator.rewind()
        while (iterator.hasNext()) {
            val next = iterator.expectNextTokenNotNull()
            when (next.kind) {
                CssTokenKind.Comma -> break
                CssTokenKind.OpenCurlyBrace -> {
                    iterator.rewind()
                    break
                }

                in CssCombinator.tokens -> Unit

                else -> selectors += simpleSelectorConsumer.consume(iterator)
            }
        }

        return selectorListItem.copy(
            location = selectors.last().let { last ->
                CssLocation(
                    source = content.substring(current.startOffset, last.location.end),
                    start = current.startOffset,
                    end = last.location.end,
                )
            },
        )
    }
}
