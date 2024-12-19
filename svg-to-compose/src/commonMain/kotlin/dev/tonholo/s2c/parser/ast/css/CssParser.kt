package dev.tonholo.s2c.parser.ast.css

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.AstParser
import dev.tonholo.s2c.parser.ast.css.consumer.CssConsumers
import dev.tonholo.s2c.parser.ast.css.syntax.CssIterator
import dev.tonholo.s2c.parser.ast.css.syntax.node.StyleSheet

/**
 * A parser for CSS (Cascading Style Sheets) code.
 *
 * This class takes CSS code as input and parses it into an Abstract Syntax Tree (AST)
 * represented by the [StyleSheet] class. It utilizes a series of consumer classes
 * to handle different parts of the CSS syntax, breaking down the parsing process
 * into smaller, manageable units.
 *
 * @property consumers The consumers used to parse the CSS syntax.
 */
internal class CssParser(
    private val consumers: CssConsumers,
) : AstParser<CssTokenKind, StyleSheet> {
    override fun parse(tokens: List<Token<out CssTokenKind>>): StyleSheet {
        val iterator = CssIterator(tokens)
        val root = consumers.styleSheetConsumer.consume(iterator = iterator)
        return root
    }
}
