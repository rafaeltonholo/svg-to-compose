package dev.tonholo.s2c.parser.ast.css.consumer

import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.css.syntax.node.AtRule
import dev.tonholo.s2c.parser.ast.css.syntax.node.AtRulePrelude
import dev.tonholo.s2c.parser.ast.css.syntax.node.Block
import dev.tonholo.s2c.parser.ast.css.syntax.node.CssLocation
import dev.tonholo.s2c.parser.ast.css.syntax.node.Prelude
import dev.tonholo.s2c.parser.ast.css.syntax.node.Rule
import dev.tonholo.s2c.parser.ast.css.syntax.parserError
import dev.tonholo.s2c.parser.ast.iterator.AstParserIterator

/**
 * Consumes an at-rule from the given iterator and builds a [AtRule] object.
 *
 * An at-rule consists of an at-keyword followed by a prelude and an optional block.
 *
 * @param content The CSS content being parsed.
 * @param blockConsumer The consumer used to parse the block of the at-rule, if present.
 */
internal class AtRuleConsumer(
    content: String,
    private val blockConsumer: SimpleBlockConsumer<Rule>,
) : Consumer<AtRule>(content) {
    override fun consume(iterator: AstParserIterator<CssTokenKind>): AtRule {
        val current = iterator.current()
        checkNotNull(current) {
            "Expected @rule but got null"
        }
        check(current.kind is CssTokenKind.AtKeyword) {
            "Expected @rule but got ${current.kind}"
        }
        val preludeStartOffset = current.endOffset + 1
        var preludeContentEndOffset = current.endOffset
        var atRule = AtRule(
            location = CssLocation.Undefined,
            name = content.substring(current.startOffset, current.endOffset),
            prelude = Prelude.AtRule(
                components = emptyList(),
            ),
            block = Block.EmptyRuleBlock,
        )
        while (iterator.hasNext()) {
            val next = checkNotNull(iterator.next())
            when (next.kind) {
                is CssTokenKind.Semicolon, CssTokenKind.CloseCurlyBrace -> break

                is CssTokenKind.EndOfFile -> iterator.parserError(content, "Incomplete @rule.")
                is CssTokenKind.OpenCurlyBrace -> {
                    val block = blockConsumer.consume(iterator)
                    atRule = atRule.copy(
                        location = CssLocation(
                            source = content.substring(current.startOffset, block.location.end),
                            start = current.startOffset,
                            end = block.location.end,
                        ),
                        block = block,
                    )
                }

                else -> {
                    preludeContentEndOffset = next.endOffset
                }
            }
        }

        return atRule.copy(
            prelude = Prelude.AtRule(
                components = listOf(
                    AtRulePrelude(
                        location = CssLocation(
                            source = content.substring(preludeStartOffset, preludeContentEndOffset),
                            start = preludeStartOffset,
                            end = preludeContentEndOffset,
                        ),
                        value = content.substring(preludeStartOffset, preludeContentEndOffset),
                    )
                ),
            ),
        )
    }
}
