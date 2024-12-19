package dev.tonholo.s2c.parser.ast.css.selectors

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.css.CssRootNode
import dev.tonholo.s2c.parser.ast.css.CssSelector
import dev.tonholo.s2c.parser.ast.css.terminalTokens
import dev.tonholo.s2c.parser.ast.iterator.AstParserIterator

internal fun SelectorParser.createAggregateSelectorParser(
    iterator: AstParserIterator<CssTokenKind, CssRootNode>,
    initiator: CssSelector,
): CssSelector.Multiple {
    val selectors = mutableListOf(initiator)
    while (true) {
        var next = iterator.next()
        if (next?.kind is CssTokenKind.WhiteSpace)
            next = iterator.next()

        if (next == null || next.kind in terminalTokens) {
            break
        }

        selectors += parse(next)
    }

    return CssSelector.Multiple(
        selectors = selectors,
    )
}
