package dev.tonholo.s2c.domain.avg

import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlChildNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

class AvgClipPath(
    override val parent: XmlParentNode,
    override val attributes: MutableMap<String, String>,
) : XmlChildNode(), AvgNode {
    override val tagName: String = TAG_NAME
    val pathData: String by attribute(namespace = AvgNode.NAMESPACE)

    companion object {
        const val TAG_NAME = "clip-path"
    }

    override fun toString(): String = toJsString()
}
