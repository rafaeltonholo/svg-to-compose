package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlElementNode
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

sealed interface SvgNode : XmlNode

class SvgElementNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : XmlElementNode(parent, children, attributes, name = TAG_NAME), SvgNode {
    val width: Int by attribute()
    val height: Int by attribute()
    var viewBox: FloatArray by attribute<String?, _> { viewBox ->
        viewBox?.split(" ")?.map { it.toFloat() }?.toFloatArray()
            ?: floatArrayOf(0f, 0f, width.toFloat(), height.toFloat())
    }
    var fill: String? by attribute()

    companion object {
        const val TAG_NAME = "svg"
    }
}

inline fun SvgElementNode.asNodes(
    minified: Boolean,
): List<ImageVectorNode> {
    val masks = children.filterIsInstance<SvgMaskNode>()
    return children.mapNotNull { node -> (node as? SvgNode)?.asNode(masks, minified) }
}

inline fun SvgNode.asNode(
    masks: List<SvgMaskNode>,
    minified: Boolean,
): ImageVectorNode? = when (this) {
    is SvgGroupNode -> asNode(masks, minified)
    is SvgPathNode -> asNode(minified)
    is SvgRectNode -> asNode(minified)
    else -> null
}
