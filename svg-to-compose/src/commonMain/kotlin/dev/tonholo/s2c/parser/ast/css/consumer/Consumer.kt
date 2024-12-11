package dev.tonholo.s2c.parser.ast.css.consumer

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.css.syntax.node.CssNode
import dev.tonholo.s2c.parser.ast.css.syntax.parserCheck
import dev.tonholo.s2c.parser.ast.iterator.AstParserIterator

/**
 * Typealias for the iterator used by the consumers.
 */
internal typealias Iterator = AstParserIterator<CssTokenKind>

/**
 * Base class for all CSS consumers.
 *
 * A consumer is responsible for consuming tokens from the iterator and
 * producing a [CssNode].
 *
 * @param T The type of [CssNode] produced by this consumer.
 */
internal abstract class Consumer<out T : CssNode>(
    protected val content: String,
) {
    /**
     * Consumes tokens from the iterator and produces a [CssNode] of type [T].
     */
    abstract fun consume(iterator: Iterator): T

    /**
     * Expects the current token to be of the given type.
     */
    protected inline fun <reified T : CssTokenKind> Iterator.expectToken(): Token<out T> {
        val current = current()
        checkNotNull(current) {
            "Expected ${T::class} but got null"
        }

        check(current.kind is T) {
            "Expected ${T::class} but got ${current.kind}"
        }

        @Suppress("UNCHECKED_CAST")
        return current as Token<out T>
    }

    /**
     * Expects the next token to be of the given type.
     */
    protected inline fun <reified T : CssTokenKind> Iterator.expectNextToken(): Token<out T> {
        val next = next()
        checkNotNull(next) {
            "Expected ${T::class} but got null"
        }

        check(next.kind is T) {
            "Expected ${T::class} but got ${next.kind}"
        }

        @Suppress("UNCHECKED_CAST")
        return next as Token<out T>
    }

    /**
     * Expects the current token to be one of the given types.
     */
    protected fun Iterator.expectToken(
        kinds: Set<CssTokenKind>
    ): Token<out CssTokenKind> {
        val current = current()
        checkNotNull(current) {
            "Expected one of $kinds but got null"
        }

        parserCheck(value = current.kind in kinds, content = content) {
            "Expected one of $kinds but got ${current.kind}"
        }
        return current
    }

    /**
     * Expects the current token to be not null.
     */
    protected fun Iterator.expectTokenNotNull(): Token<out CssTokenKind> {
        val current = current()
        checkNotNull(current) {
            "Expected token but got null"
        }
        return current
    }

    /**
     * Expects the next token to be not null.
     */
    protected fun Iterator.expectNextTokenNotNull(): Token<out CssTokenKind> {
        val next = next()
        checkNotNull(next) {
            "Expected token but got null"
        }
        return next
    }
}
