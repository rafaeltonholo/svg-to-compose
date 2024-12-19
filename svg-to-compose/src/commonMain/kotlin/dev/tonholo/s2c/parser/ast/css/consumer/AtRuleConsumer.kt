package dev.tonholo.s2c.parser.ast.css.consumer

import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.css.syntax.node.AtRule
import dev.tonholo.s2c.parser.ast.css.syntax.node.AtRulePrelude
import dev.tonholo.s2c.parser.ast.css.syntax.node.Block
import dev.tonholo.s2c.parser.ast.css.syntax.node.CssLocation
import dev.tonholo.s2c.parser.ast.css.syntax.node.Prelude
import dev.tonholo.s2c.parser.ast.css.syntax.node.Rule
import dev.tonholo.s2c.parser.ast.iterator.AstParserIterator
import dev.tonholo.s2c.parser.ast.iterator.parserCheck
import dev.tonholo.s2c.parser.ast.iterator.parserCheckNotNull
import dev.tonholo.s2c.parser.ast.iterator.parserError
import dev.tonholo.s2c.parser.ast.iterator.parserRequire

private val validAtRules = setOf("media", "keyframes", "import", "charset", "supports", "page", "font-face")

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
        iterator.parserCheckNotNull(value = current, content = content) {
            "Expected @rule but got null"
        }
        iterator.parserCheck(predicate = current.kind == CssTokenKind.AtKeyword, content = content) {
            "Expected @rule but got ${current.kind}"
        }
        val preludeStartOffset = current.endOffset + 1
        var preludeContentEndOffset = current.endOffset
        var atRule = AtRule(
            location = CssLocation.Undefined,
            name = content.substring(current.startOffset, current.endOffset).also {
                iterator.parserRequire(
                    predicate = it.substring(1) in validAtRules,
                    content = content,
                ) { "Invalid at-rule: $it" }
            },
            prelude = Prelude.AtRule(
                components = emptyList(),
            ),
            block = Block.EmptyRuleBlock,
        )
        while (iterator.hasNext()) {
            val next = checkNotNull(iterator.next())
            when (next.kind) {
                CssTokenKind.Semicolon, CssTokenKind.CloseCurlyBrace -> break

                CssTokenKind.EndOfFile -> iterator.parserError(content, "Incomplete @rule.")
                CssTokenKind.OpenCurlyBrace -> {
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
