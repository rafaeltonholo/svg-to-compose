package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.xml.XmlElementNode
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

class SvgDefsNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : XmlElementNode(parent, children, attributes, tagName = TAG_NAME), SvgNode {
    companion object {
        const val TAG_NAME = "defs"
    }
}
