package dev.tonholo.s2c.domain.avg

import dev.tonholo.s2c.domain.xml.XmlChildNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

abstract class AvgChildNode(parent: XmlParentNode) : XmlChildNode(parent), AvgNode {
    override fun toString(): String = toJsString()
}
