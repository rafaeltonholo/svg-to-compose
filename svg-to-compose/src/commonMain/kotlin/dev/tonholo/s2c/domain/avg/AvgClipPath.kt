package dev.tonholo.s2c.domain.avg

import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlParentNode

class AvgClipPath(
    parent: XmlParentNode,
    override val attributes: MutableMap<String, String>,
) : AvgChildNode(parent), AvgNode {
    override val tagName: String = TAG_NAME
    val pathData: String by attribute(namespace = AvgNode.NAMESPACE)

    companion object {
        const val TAG_NAME = "clip-path"
    }
}
