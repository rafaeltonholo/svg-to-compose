package dev.tonholo.s2c.parser.ast.css.consumer

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.css.syntax.node.CssLocation
import dev.tonholo.s2c.parser.ast.css.syntax.node.Declaration
import dev.tonholo.s2c.parser.ast.css.syntax.node.Value
import dev.tonholo.s2c.parser.ast.iterator.AstParserIterator

/**
 * Consumes a CSS declaration from the given iterator and builds a [Declaration] object.
 *
 * This class is responsible for parsing the individual components of a CSS declaration,
 * such as the property name, values, and the `!important` flag. It utilizes a
 * [ValueConsumer] to handle the parsing of the declaration's values.
 *
 * @param content The complete CSS content being parsed.
 * @param valueConsumer The [Consumer] responsible for parsing value tokens into [Value] objects.
 */
internal class DeclarationConsumer(
    content: String,
    private val valueConsumer: Consumer<Value>,
) : Consumer<Declaration>(content) {
    override fun consume(iterator: AstParserIterator<CssTokenKind>): Declaration {
        val current = iterator.expectToken<CssTokenKind.Ident>()
        val property = content.substring(startIndex = current.startOffset, endIndex = current.endOffset)
        val values = mutableListOf<Value>()
        var important = false
        var last: Token<out CssTokenKind>? = null
        while (iterator.hasNext()) {
            val next = iterator.expectNextTokenNotNull()
            when (next.kind) {
                CssTokenKind.Colon, CssTokenKind.WhiteSpace -> Unit
                CssTokenKind.Semicolon, CssTokenKind.CloseCurlyBrace -> {
                    last = next
                    break
                }

                CssTokenKind.Bang -> {
                    important = true
                }

                else -> values += valueConsumer.consume(iterator)
            }
            last = next
        }

        if (last != null && last.kind == CssTokenKind.CloseCurlyBrace) {
            iterator.rewind()
            last = iterator.current()
        }

        return Declaration(
            location = CssLocation(
                source = content.substring(
                    startIndex = current.startOffset,
                    endIndex = last?.endOffset ?: current.endOffset,
                ),
                start = current.startOffset,
                end = last?.endOffset ?: current.endOffset,
            ),
            important = important,
            property = property,
            values = values,
        )
    }
}
