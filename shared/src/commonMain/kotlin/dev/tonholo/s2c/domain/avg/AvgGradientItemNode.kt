package dev.tonholo.s2c.domain.avg

import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlParentNode

class AvgGradientItemNode(
    parent: XmlParentNode,
    override val attributes: MutableMap<String, String>,
) : AvgChildNode(parent), AvgNode {
    override val tagName: String = TAG_NAME
    val offset: Float? by attribute(namespace = AvgNode.NAMESPACE)
    val color: AvgColor? by attribute<String?, _>(namespace = AvgNode.NAMESPACE) {
        it?.let(::AvgColor)
    }

    override fun toString(): String = toJsString {
        append("\"offset\": $offset, ")
        append("\"color\": \"$color\"")
    }

    companion object {
        const val TAG_NAME = "item"
    }
}
