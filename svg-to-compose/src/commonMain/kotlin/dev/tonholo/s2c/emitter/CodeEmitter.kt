package dev.tonholo.s2c.emitter

import dev.tonholo.s2c.domain.IconFileContents

/**
 * Emits Kotlin source code from a parsed icon model.
 *
 * Each implementation targets a specific [OutputFormat] and produces
 * a complete Kotlin file as a [String].
 *
 * @see IconFileContents
 */
interface CodeEmitter {
    /**
     * Generates Kotlin source code for the given icon file contents.
     *
     * @param contents The parsed icon model to emit code for.
     * @return The generated Kotlin source code.
     */
    fun emit(contents: IconFileContents): String
}
