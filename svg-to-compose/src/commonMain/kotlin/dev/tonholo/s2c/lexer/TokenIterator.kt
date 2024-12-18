package dev.tonholo.s2c.lexer

import dev.tonholo.s2c.extensions.EMPTY

/**
 * An iterator for tokens.
 *
 * It provides methods to navigate and analyze the input string.
 *
 * @param T The type of token kind this iterator produces.
 */
internal abstract class TokenIterator<out T : TokenKind> {
    private var _content: String? = null

    /**
     * The content being iterated over.
     *
     * This property is initialized by [initialize] and throws an exception
     * if accessed before initialization.
     */
    private val content
        get() = requireNotNull(_content) {
            "Content not initialized. Did you miss calling initialize(content)?"
        }

    /**
     * The current offset within the content string.
     */
    var offset = 0
        get() = field.coerceIn(0, content.length)
        private set

    /**
     * Initializes the iterator with the given content string.
     *
     * Resets the offset to 0.
     *
     * @param content The string to iterate over.
     */
    fun initialize(content: String) {
        _content = content
        offset = 0
    }

    /**
     * Checks if there are more characters to iterate over.
     *
     * @return `true` if the current offset is less than the content length,
     * `false` otherwise.
     */
    fun hasNext(): Boolean = offset < content.length

    /**
     * Returns the character at the current offset.
     *
     * @return The character at the current offset.
     */
    fun get(): Char = content[offset]

    /**
     * Returns the character at the current offset and advances the offset by 1.
     *
     * @return The character at the current offset before advancing.
     */
    fun next(): Char = content.getOrElse(++offset) { Char.EMPTY }

    /**
     * Abstract method to determine the token kind at the current offset.
     *
     * Subclasses must implement this method to provide specific token kind
     * identification logic.
     *
     * @return The token kind at the current offset, or `null` if no valid
     * token is found.
     */
    abstract fun getTokenKind(): T?

    /**
     * Peeks at the character at the given offset relative to the current offset.
     *
     * @param offset The offset relative to the current offset.
     * @return The character at the specified offset, or [EMPTY] if the offset
     * is out of bounds.
     */
    fun peek(offset: Int): Char =
        content.getOrElse(this.offset + offset) { Char.EMPTY }

    /**
     * Looks up the character at the given absolute offset.
     *
     * @param offset The absolute offset within the content string.
     * @return The character at the specified offset, or [EMPTY] if the offset
     * is out of bounds.
     */
    fun lookup(offset: Int): Char =
        content.getOrElse(offset) { Char.EMPTY }

    /** Advances the offset by the specified number of steps. */
    fun nextOffset(steps: Int = 1): Int {
        offset = (offset + steps).coerceIn(0, content.length)
        return offset
    }

    /** Moves the offset to the specified position. */
    fun moveTo(offset: Int) {
        this.offset = offset.coerceIn(0, content.length)
    }

    /** Rewinds the offset by the specified number of steps. */
    fun rewind(steps: Int = 1) {
        offset = (offset - steps).coerceIn(0, content.length)
    }
}
