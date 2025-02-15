package dev.tonholo.s2c.lexer.css.token.consumer

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.TokenIterator
import dev.tonholo.s2c.lexer.css.CssTokenKind

/**
 * Base class for all token consumers.
 *
 * A token consumer is responsible for consuming a specific type of token
 * and returning a list of tokens that represent the consumed token.
 *
 * @param iterator The token iterator to consume tokens from.
 */
internal abstract class TokenConsumer(
    protected val iterator: TokenIterator<CssTokenKind>,
) {
    /**
     * The set of token kinds that this consumer supports.
     */
    protected abstract val supportedTokenKinds: Set<CssTokenKind>

    /**
     * Checks if this consumer accepts the given token kind.
     */
    open fun accept(kind: CssTokenKind): Boolean {
        return kind in supportedTokenKinds
    }

    /**
     * Consumes the given token kind and returns a list of tokens that represent the consumed token.
     */
    abstract fun consume(kind: CssTokenKind): List<Token<out CssTokenKind>>
}
