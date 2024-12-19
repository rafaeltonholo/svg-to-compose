package dev.tonholo.s2c.parser.ast.css.consumer

import dev.tonholo.s2c.parser.ast.css.syntax.node.AtRule
import dev.tonholo.s2c.parser.ast.css.syntax.node.Declaration
import dev.tonholo.s2c.parser.ast.css.syntax.node.QualifiedRule
import dev.tonholo.s2c.parser.ast.css.syntax.node.Rule
import dev.tonholo.s2c.parser.ast.css.syntax.node.Selector
import dev.tonholo.s2c.parser.ast.css.syntax.node.SelectorListItem
import dev.tonholo.s2c.parser.ast.css.syntax.node.StyleSheet
import dev.tonholo.s2c.parser.ast.css.syntax.node.Value

/**
 * DSL marker for CssConsumers DSL.
 */
@DslMarker
internal annotation class CssConsumersDslMarker

/**
 * Interface defining various consumers for CSS parsing.
 *
 *  @property selectorConsumer A consumer responsible for parsing selectors.
 *  @property selectorListItemConsumer A consumer responsible for parsing selector list items.
 *  @property valueConsumer A consumer responsible for parsing CSS values.
 *  @property declarationConsumer A consumer responsible for parsing CSS declarations.
 *  @property declarationBlockConsumer A consumer responsible for parsing declaration blocks.
 *  @property qualifiedRuleConsumer A consumer responsible for parsing qualified rules.
 *  @property ruleBlockConsumer A consumer responsible for parsing rule blocks.
 *  @property atRuleConsumer A consumer responsible for parsing at-rules.
 *  @property styleSheetConsumer A consumer responsible for parsing the entire style sheet.
 */
@CssConsumersDslMarker
internal interface CssConsumers {
    val selectorConsumer: Consumer<Selector>
    val selectorListItemConsumer: Consumer<SelectorListItem>
    val valueConsumer: Consumer<Value>
    val declarationConsumer: Consumer<Declaration>
    val declarationBlockConsumer: SimpleBlockConsumer<Declaration>
    val qualifiedRuleConsumer: Consumer<QualifiedRule>
    val ruleBlockConsumer: SimpleBlockConsumer<Rule>
    val atRuleConsumer: Consumer<AtRule>
    val styleSheetConsumer: Consumer<StyleSheet>
}

/**
 * Builder class for creating instances of [CssConsumers].
 */
internal class CssConsumersBuilder {
    var customSelectorConsumer: Consumer<Selector>? = null
    var customSelectorListItemConsumer: Consumer<SelectorListItem>? = null
    var customValueConsumer: Consumer<Value>? = null
    var customDeclarationConsumer: Consumer<Declaration>? = null
    var customDeclarationBlockConsumer: SimpleBlockConsumer<Declaration>? = null
    var customQualifiedRuleConsumer: Consumer<QualifiedRule>? = null
    var customRuleBlockConsumer: SimpleBlockConsumer<Rule>? = null
    var customAtRuleConsumer: Consumer<AtRule>? = null
    var customStyleSheetConsumer: Consumer<StyleSheet>? = null

    /**
     * Builds an instance of [CssConsumers] using the provided content and custom consumers.
     *
     * @param content The CSS content to be parsed.
     * @return An instance of [CssConsumers].
     */
    fun build(content: String): CssConsumers {
        val customSelectorConsumer = customSelectorConsumer ?: SimpleSelectorConsumer(content)
        val customValueConsumer = customValueConsumer ?: ValueConsumer(content)
        val customDeclarationConsumer = customDeclarationConsumer ?: DeclarationConsumer(
            content = content,
            valueConsumer = customValueConsumer,
        )
        val customSelectorListItemConsumer = customSelectorListItemConsumer ?: SelectorListItemConsumer(
            content,
            simpleSelectorConsumer = customSelectorConsumer,
        )
        val customDeclarationBlockConsumer = customDeclarationBlockConsumer ?: SimpleDeclarationBlockConsumer(
            content = content,
            declarationConsumer = customDeclarationConsumer,
        )
        val customQualifiedRuleConsumer = customQualifiedRuleConsumer ?: QualifiedRuleConsumer(
            content = content,
            selectorListItemConsumer = customSelectorListItemConsumer,
            blockConsumer = customDeclarationBlockConsumer,
        )
        val customRuleBlockConsumer = customRuleBlockConsumer ?: SimpleRuleBlockConsumer(
            content = content,
            qualifiedRuleConsumer = customQualifiedRuleConsumer,
        )
        val customAtRuleConsumer = customAtRuleConsumer ?: AtRuleConsumer(
            content = content,
            blockConsumer = customRuleBlockConsumer,
        )
        val customStyleSheetConsumer = customStyleSheetConsumer ?: StyleSheetConsumer(
            content = content,
            atRuleConsumer = customAtRuleConsumer,
            qualifiedRuleConsumer = customQualifiedRuleConsumer,
        )

        return object : CssConsumers {
            override val selectorConsumer: Consumer<Selector> = customSelectorConsumer
            override val selectorListItemConsumer: Consumer<SelectorListItem> = customSelectorListItemConsumer
            override val valueConsumer: Consumer<Value> = customValueConsumer
            override val declarationConsumer: Consumer<Declaration> = customDeclarationConsumer
            override val declarationBlockConsumer: SimpleBlockConsumer<Declaration> = customDeclarationBlockConsumer
            override val qualifiedRuleConsumer: Consumer<QualifiedRule> = customQualifiedRuleConsumer
            override val ruleBlockConsumer: SimpleBlockConsumer<Rule> = customRuleBlockConsumer
            override val atRuleConsumer: Consumer<AtRule> = customAtRuleConsumer
            override val styleSheetConsumer: Consumer<StyleSheet> = customStyleSheetConsumer
        }
    }
}

/**
 * DSL function for creating an instance of [CssConsumers] with optional custom consumers.
 *
 * @param content The CSS content to be parsed.
 * @param builder A lambda for configuring custom consumers.
 * @return An instance of [CssConsumers].
 */
@CssConsumersDslMarker
internal fun CssConsumers(content: String, builder: CssConsumersBuilder.() -> Unit = {}): CssConsumers {
    return CssConsumersBuilder().apply(builder).build(content)
}
