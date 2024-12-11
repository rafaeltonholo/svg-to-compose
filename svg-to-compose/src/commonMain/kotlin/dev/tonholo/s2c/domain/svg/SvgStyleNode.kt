package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.domain.xml.XmlTextNode
import dev.tonholo.s2c.extensions.firstInstanceOf
import dev.tonholo.s2c.lexer.css.CssTokenizer
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.AstParser
import dev.tonholo.s2c.parser.ast.css.syntax.node.StyleSheet

class SvgStyleNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    override val attributes: MutableMap<String, String>,
) : SvgElementNode<SvgStyleNode>(parent, children, attributes, tagName = TAG_NAME), SvgNode {
    override val constructor = ::SvgStyleNode
    override val tagName: String = TAG_NAME
    val content: String by lazy {
        children
            .firstInstanceOf<XmlTextNode>()
            .content
    }
    private var _tree: StyleSheet? = null
    val tree: StyleSheet
        get() = checkNotNull(_tree) {
            "Css tree is not resolved. Did you forget to call `resolveTree()`?"
        }

    override fun toString(): String = toJsString {
    }

    internal fun resolveTree(parser: AstParser<CssTokenKind, StyleSheet>) {
        val tokens = CssTokenizer().tokenize(content).toList()
        _tree = parser.parse(tokens)
    }

    companion object {
        const val TAG_NAME = "style"
    }
}
