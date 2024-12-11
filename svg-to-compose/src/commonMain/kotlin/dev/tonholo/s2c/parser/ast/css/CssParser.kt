package dev.tonholo.s2c.parser.ast.css

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.AstParser
import dev.tonholo.s2c.parser.ast.css.consumer.AtRuleConsumer
import dev.tonholo.s2c.parser.ast.css.consumer.Consumer
import dev.tonholo.s2c.parser.ast.css.consumer.DeclarationConsumer
import dev.tonholo.s2c.parser.ast.css.consumer.QualifiedRuleConsumer
import dev.tonholo.s2c.parser.ast.css.consumer.SelectorListItemConsumer
import dev.tonholo.s2c.parser.ast.css.consumer.SimpleBlockConsumer
import dev.tonholo.s2c.parser.ast.css.consumer.SimpleDeclarationBlockConsumer
import dev.tonholo.s2c.parser.ast.css.consumer.SimpleRuleBlockConsumer
import dev.tonholo.s2c.parser.ast.css.consumer.SimpleSelectorConsumer
import dev.tonholo.s2c.parser.ast.css.consumer.StyleSheetConsumer
import dev.tonholo.s2c.parser.ast.css.consumer.ValueConsumer
import dev.tonholo.s2c.parser.ast.css.syntax.CssIterator
import dev.tonholo.s2c.parser.ast.css.syntax.node.AtRule
import dev.tonholo.s2c.parser.ast.css.syntax.node.Declaration
import dev.tonholo.s2c.parser.ast.css.syntax.node.QualifiedRule
import dev.tonholo.s2c.parser.ast.css.syntax.node.Rule
import dev.tonholo.s2c.parser.ast.css.syntax.node.Selector
import dev.tonholo.s2c.parser.ast.css.syntax.node.SelectorListItem
import dev.tonholo.s2c.parser.ast.css.syntax.node.StyleSheet
import dev.tonholo.s2c.parser.ast.css.syntax.node.Value

/**
 * A parser for CSS (Cascading Style Sheets) code.
 *
 * This class takes CSS code as input and parses it into an Abstract Syntax Tree (AST)
 * represented by the [StyleSheet] class. It utilizes a series of consumer classes
 * to handle different parts of the CSS syntax, breaking down the parsing process
 * into smaller, manageable units.
 *
 * @param content The CSS code to be parsed.
 * @param selectorConsumer A consumer responsible for parsing selectors.
 * @param selectorListItemConsumer A consumer responsible for parsing selector list items.
 * @param valueConsumer A consumer responsible for parsing CSS values.
 * @param declarationConsumer A consumer responsible for parsing CSS declarations.
 * @param declarationBlockConsumer A consumer responsible for parsing declaration blocks.
 * @param qualifiedRuleConsumer A consumer responsible for parsing qualified rules.
 * @param ruleBlockConsumer A consumer responsible for parsing rule blocks.
 * @param atRuleConsumer A consumer responsible for parsing at-rules.
 * @param styleSheetConsumer A consumer responsible for parsing the entire style sheet.
 */
internal class CssParser(
    private val content: String,
    private val selectorConsumer: Consumer<Selector> = SimpleSelectorConsumer(content),
    private val selectorListItemConsumer: Consumer<SelectorListItem> = SelectorListItemConsumer(
        content,
        selectorConsumer,
    ),
    private val valueConsumer: Consumer<Value> = ValueConsumer(content),
    private val declarationConsumer: Consumer<Declaration> = DeclarationConsumer(content, valueConsumer),
    private val declarationBlockConsumer: SimpleBlockConsumer<Declaration> = SimpleDeclarationBlockConsumer(
        content = content,
        declarationConsumer = declarationConsumer,
    ),
    private val qualifiedRuleConsumer: Consumer<QualifiedRule> = QualifiedRuleConsumer(
        content = content,
        selectorListItemConsumer = selectorListItemConsumer,
        blockConsumer = declarationBlockConsumer,
    ),
    private val ruleBlockConsumer: SimpleBlockConsumer<Rule> = SimpleRuleBlockConsumer(
        content = content,
        qualifiedRuleConsumer = qualifiedRuleConsumer,
    ),
    private val atRuleConsumer: Consumer<AtRule> = AtRuleConsumer(
        content = content,
        blockConsumer = ruleBlockConsumer,
    ),
    private val styleSheetConsumer: Consumer<StyleSheet> = StyleSheetConsumer(
        content = content,
        atRuleConsumer = atRuleConsumer,
        qualifiedRuleConsumer = qualifiedRuleConsumer,
    ),
) : AstParser<CssTokenKind, StyleSheet> {
    override fun parse(tokens: List<Token<out CssTokenKind>>): StyleSheet {
        val iterator = CssIterator(tokens)
        val root = styleSheetConsumer.consume(iterator = iterator)
        return root
    }
}
