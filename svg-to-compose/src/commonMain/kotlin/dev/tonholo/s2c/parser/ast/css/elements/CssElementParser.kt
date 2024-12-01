package dev.tonholo.s2c.parser.ast.css.elements

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.Element

internal interface CssElementParser<T: Element> {
    fun parse(starterToken: Token<out CssTokenKind>): T?
}
