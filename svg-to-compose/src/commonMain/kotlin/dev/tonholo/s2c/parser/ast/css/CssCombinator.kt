package dev.tonholo.s2c.parser.ast.css

import dev.tonholo.s2c.lexer.css.CssTokenKind

/**
 * Represents the different types of CSS combinators.
 * Combinators are used to combine multiple selectors in a CSS rule
 * to target specific elements based on their relationship in the DOM.
 *
 * @property representation The string representation of the combinator.
 * @property tokenKind The [CssTokenKind] associated with the combinator.
 */
enum class CssCombinator(val representation: String, val tokenKind: CssTokenKind) {
    ChildCombinator(representation = " > ", tokenKind = CssTokenKind.Greater),
    NextSiblingCombinator(representation = " ~ ", tokenKind = CssTokenKind.Tilde),
    DescendantCombinator(representation = " ", tokenKind = CssTokenKind.WhiteSpace),
    SubsequentSiblingCombinator(representation = " + ", tokenKind = CssTokenKind.Plus),
    ;

    companion object {
        val tokens = entries.map { it.tokenKind }

        /**
         * Creates a [CssCombinator] from the given [CssTokenKind].
         *
         * This function maps specific token kinds to their corresponding combinators:
         * - [CssTokenKind.Greater] : [ChildCombinator] (" > ")
         * - [CssTokenKind.Tilde] : [NextSiblingCombinator] (" ~ ")
         * - [CssTokenKind.WhiteSpace] : [DescendantCombinator] (" ")
         * - [CssTokenKind.Plus] : [SubsequentSiblingCombinator] (" + ")
         *
         * For any other token kind, including `null`, the function returns `null`.
         *
         * @param tokenKind The [CssTokenKind] to convert to a combinator.
         * @return The corresponding [CssCombinator], or `null` if no matching combinator is found.
         */
        fun from(tokenKind: CssTokenKind?): CssCombinator? = when (tokenKind) {
            CssTokenKind.Greater -> ChildCombinator
            CssTokenKind.Tilde -> NextSiblingCombinator
            CssTokenKind.WhiteSpace -> DescendantCombinator
            CssTokenKind.Plus -> SubsequentSiblingCombinator
            else -> null
        }
    }
}
