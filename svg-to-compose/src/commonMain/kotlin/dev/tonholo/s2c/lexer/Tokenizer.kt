package dev.tonholo.s2c.lexer

/**
 * Defines a tokenizer interface.
 *
 * @param T The type of token kinds this tokenizer produces.
 */
internal interface Tokenizer<out T : TokenKind> {
    /**
     * Tokenizes the given input string into a list of tokens.
     * @param input The input string to tokenize.
     * @return A list of tokens representing the input string.
     */
    fun tokenize(input: String): List<Token<out T>>
}
