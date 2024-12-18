package dev.tonholo.s2c.parser.ast.css.consumer

import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.css.syntax.node.Block
import dev.tonholo.s2c.parser.ast.css.syntax.node.CssLocation
import dev.tonholo.s2c.parser.ast.css.syntax.node.Declaration
import dev.tonholo.s2c.parser.ast.css.syntax.node.Prelude
import dev.tonholo.s2c.parser.ast.css.syntax.node.QualifiedRule
import dev.tonholo.s2c.parser.ast.css.syntax.node.SelectorListItem
import dev.tonholo.s2c.parser.ast.css.syntax.parserError
import dev.tonholo.s2c.parser.ast.iterator.AstParserIterator

/**
 * Consumes a qualified rule from the given iterator and builds a [QualifiedRule]
 * object.
 *
 * A qualified rule is a CSS rule that consists of a selector list followed by a
 * declaration block.
 *
 * For example:
 * ```css
 * h1, h2 {
 *   color: red;
 * }
 * ```
 * In this example, `h1, h2` is the selector list and `{ color: red; }` is the
 * declaration block.
 *
 * This class is responsible for parsing the selector list and the declaration
 * block using the provided consumers.
 *
 * @param content The CSS content being parsed.
 * @param selectorListItemConsumer The consumer used to parse individual selector
 * list items.
 * @param blockConsumer The consumer used to parse the declaration block.
 */
internal class QualifiedRuleConsumer(
    content: String,
    private val selectorListItemConsumer: Consumer<SelectorListItem>,
    private val blockConsumer: SimpleBlockConsumer<Declaration>,
) : Consumer<QualifiedRule>(content) {
    override fun consume(iterator: AstParserIterator<CssTokenKind>): QualifiedRule {
        val current = iterator.current()
        checkNotNull(current) {
            "Expected qualified rule but got null"
        }
        val selectors = mutableListOf<SelectorListItem>()
        val rule = QualifiedRule(
            location = CssLocation.Undefined,
            prelude = Prelude.Selector(
                components = selectors,
            ),
            block = Block.EmptyDeclarationBlock,
        )

        // re-consume current token.
        iterator.rewind()
        while (iterator.hasNext()) {
            val next = iterator.next()
            checkNotNull(next) {
                "Expected token but got null"
            }
            when (next.kind) {
                CssTokenKind.EndOfFile -> iterator.parserError(content, "Incomplete qualified rule.")
                CssTokenKind.OpenCurlyBrace -> {
                    val block = blockConsumer.consume(iterator)
                    return rule.copy(
                        location = CssLocation(
                            source = content.substring(
                                current.startOffset,
                                block.location.end.coerceAtMost(content.length),
                            ),
                            start = current.startOffset,
                            end = block.location.end,
                        ),
                        block = block,
                    )
                }

                CssTokenKind.WhiteSpace -> Unit

                else -> selectors += selectorListItemConsumer.consume(iterator)
            }
        }

        iterator.parserError(content, "parse error")
    }
}
