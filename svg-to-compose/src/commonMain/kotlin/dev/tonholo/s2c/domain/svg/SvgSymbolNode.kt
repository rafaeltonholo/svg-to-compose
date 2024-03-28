package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

class SvgSymbolNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    override val attributes: MutableMap<String, String>,
) : SvgElementNode<SvgSymbolNode>(parent, children, attributes, tagName = TAG_NAME), SvgNode {
    override val constructor = ::SvgSymbolNode
    companion object {
        const val TAG_NAME = "symbol"
    }
}
