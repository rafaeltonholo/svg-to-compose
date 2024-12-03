package dev.tonholo.s2c.parser.ast.css.selectors

import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.css.CssCombinator
import dev.tonholo.s2c.parser.ast.css.CssComponent
import dev.tonholo.s2c.parser.ast.css.CssRootNode
import dev.tonholo.s2c.parser.ast.css.terminalTokens
import dev.tonholo.s2c.parser.ast.iterator.AstParserIterator

/**
 * Creates an aggregate selector parser, which parses multiple selectors separated by combinators.
 *
 * This function is responsible for parsing selectors that are combined together,
 * such as `div > p` or `a.link + span`.
 *
 * @param iterator The iterator to use for parsing.
 * @param initiator The initial selector to start with.
 * @return A [CssComponent.Multiple] representing the aggregate selector.
 */
internal fun SelectorParser.createAggregateSelectorParser(
    iterator: AstParserIterator<CssTokenKind, CssRootNode>,
    initiator: CssComponent,
): CssComponent.Multiple {
    val selectors = mutableListOf(initiator)
    while (true) {
        var next = iterator.next()
        if (CssCombinator.from(next?.kind) != null)
            next = iterator.next()

        if (next == null || next.kind in terminalTokens) {
            iterator.rewind()
            break
        }

        val selector = parse(next)
        if (selector != null) {
            selectors += selector
        } else {
            break
        }
    }

    return CssComponent.Multiple(components = selectors)
}
