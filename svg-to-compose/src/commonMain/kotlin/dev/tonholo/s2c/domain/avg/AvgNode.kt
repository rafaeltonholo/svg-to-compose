package dev.tonholo.s2c.domain.avg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlElementNode
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

sealed interface AvgNode : XmlNode {
    companion object {
        const val NAMESPACE = "android"
    }
}

class AvgElementNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : XmlElementNode(parent, children, attributes, tagName = TAG_NAME), AvgNode {
    val width: Float by attribute<String, _>(namespace = AvgNode.NAMESPACE) {
        it.removeSuffix("dp").toFloat()
    }
    val height: Float by attribute<String, _>(namespace = AvgNode.NAMESPACE) {
        it.removeSuffix("dp").toFloat()
    }
    val viewportWidth: Float by attribute(namespace = AvgNode.NAMESPACE)
    val viewportHeight: Float by attribute(namespace = AvgNode.NAMESPACE)

    companion object {
        const val TAG_NAME = "vector"
    }
}

inline fun AvgElementNode.asNodes(
    minified: Boolean,
): List<ImageVectorNode> = children.mapNotNull { node ->
    (node as? AvgNode)?.asNode(minified)
}

inline fun AvgNode.asNode(
    minified: Boolean,
): ImageVectorNode? = when (this) {
    is AvgGroupNode -> asNode(minified)
    is AvgPathNode -> asNode(minified)
    else -> null
}
