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
    protected fun Iterator.expectToken(kind: CssTokenKind): Token<out CssTokenKind> {
        val current = current()
        checkNotNull(current) {
            "Expected $kind but got null"
        }

        check(current.kind == kind) {
            "Expected $kind but got ${current.kind}"
        }

        return current
    }

    /**
     * Expects the next token to be of the given type.
     */
    protected fun Iterator.expectNextToken(kind: CssTokenKind): Token<out CssTokenKind> {
        val next = next()
        checkNotNull(next) {
            "Expected $kind but got null"
        }

        check(next.kind == kind) {
            "Expected $kind but got ${next.kind}"
        }

        return next
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
