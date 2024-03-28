package dev.tonholo.s2c.domain.avg

import dev.tonholo.s2c.domain.xml.XmlElementNode
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

abstract class AvgElementNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
    override val tagName: String,
) : XmlElementNode(parent, children, attributes, tagName), AvgNode
