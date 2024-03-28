package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

class SvgDefsNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : SvgElementNode<SvgDefsNode>(parent, children, attributes, tagName = TAG_NAME), SvgNode {
    override val constructor = ::SvgDefsNode
    companion object {
        const val TAG_NAME = "defs"
    }
}
