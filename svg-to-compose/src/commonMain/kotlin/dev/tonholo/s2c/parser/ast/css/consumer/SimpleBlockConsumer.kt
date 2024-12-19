package dev.tonholo.s2c.parser.ast.css.consumer

import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.css.syntax.node.Block
import dev.tonholo.s2c.parser.ast.css.syntax.node.CssLocation
import dev.tonholo.s2c.parser.ast.css.syntax.node.CssNode
import dev.tonholo.s2c.parser.ast.css.syntax.node.Declaration
import dev.tonholo.s2c.parser.ast.css.syntax.node.Rule
import dev.tonholo.s2c.parser.ast.iterator.AstParserIterator
import dev.tonholo.s2c.parser.ast.iterator.parserCheck
import dev.tonholo.s2c.parser.ast.iterator.parserCheckNotNull
import dev.tonholo.s2c.parser.ast.iterator.parserError

private val blockOpeningTokens = mapOf(
    CssTokenKind.OpenParenthesis to CssTokenKind.CloseParenthesis,
    CssTokenKind.OpenSquareBracket to CssTokenKind.CloseSquareBracket,
    CssTokenKind.OpenCurlyBrace to CssTokenKind.CloseCurlyBrace,
)

/**
 * Consumes a simple block, which is a block of tokens enclosed by parenthesis,
 * brackets or braces, from the given iterator and builds a [Block.SimpleBlock]
 * object.
 * @param T The type of the children of the block.
 */
internal abstract class SimpleBlockConsumer<T : CssNode>(
    content: String,
    private val consumer: Consumer<T>,
) : Consumer<Block.SimpleBlock<T>>(content) {
    override fun consume(iterator: AstParserIterator<CssTokenKind>): Block.SimpleBlock<T> {
        val prev = iterator.peek(steps = -1)
        iterator.parserCheckNotNull(value = prev, content = content) {
            "Expected Component value but got null"
        }
        iterator.parserCheck(predicate = prev.kind in blockOpeningTokens, content = content) {
            "Expected block opening token but got ${prev.kind}"
        }
        val closingToken = blockOpeningTokens.getValue(prev.kind)

        val children = mutableListOf<T>()

        val simpleBlock = Block.SimpleBlock(
            location = CssLocation.Undefined,
            children = children,
        )

        while (iterator.hasNext()) {
            val current = iterator.next()
            checkNotNull(current) {
                "Expected Component value but got null"
            }
            when (current.kind) {
                closingToken -> return simpleBlock.copy(
                    location = CssLocation(
                        source = content.substring(prev.startOffset, current.endOffset.coerceAtMost(content.length)),
                        start = prev.startOffset,
                        end = current.endOffset,
                    )
                )

                CssTokenKind.EndOfFile -> iterator.parserError(content, "Incomplete simple block")
                else -> {
                    children += consumer.consume(iterator)
                }
            }
        }
        iterator.parserError(content, "Parser error.")
    }
}

/**
 * Consumes a simple block of rules.
 */
internal class SimpleRuleBlockConsumer(
    content: String,
    qualifiedRuleConsumer: Consumer<Rule>,
) : SimpleBlockConsumer<Rule>(content, qualifiedRuleConsumer)

/**
 * Consumes a simple block of declarations.
 * This is typically used for consuming the declarations inside a rule.
 *
 * E.g.: `selector { declaration1; declaration2; }`
 */
internal class SimpleDeclarationBlockConsumer(
    content: String,
    declarationConsumer: Consumer<Declaration>,
) : SimpleBlockConsumer<Declaration>(
    content = content,
    consumer = declarationConsumer,
)
