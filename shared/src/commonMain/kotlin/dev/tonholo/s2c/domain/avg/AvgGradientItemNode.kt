package dev.tonholo.s2c.domain.avg

import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlChildNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

class AvgGradientItemNode(
    override val parent: XmlParentNode,
    override val attributes: MutableMap<String, String>,
) : XmlChildNode(), AvgNode {
    override val tagName: String = TAG_NAME
    val offset: Float? by attribute(namespace = AvgNode.NAMESPACE)
    val color: String? by attribute(namespace = AvgNode.NAMESPACE)

    override fun toString(): String = toJsString {
        append("\"offset\": $offset, ")
        append("\"color\": \"$color\"")
    }

    companion object {
        const val TAG_NAME = "item"
    }
}
