package dev.tonholo.s2c.parser.ast.css.consumer

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.css.CssAstParser
import dev.tonholo.s2c.parser.ast.css.CssElement

internal abstract class CssConsumer<out TElement : CssElement>(
    protected val content: String,
    protected val parser: CssAstParser,
) {
    abstract fun consume(token: Token<out CssTokenKind>): TElement
}
