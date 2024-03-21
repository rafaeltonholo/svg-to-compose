package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.xml.XmlElementNode
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

class SvgClipPath(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : XmlElementNode(parent, children, attributes, tagName = TAG_NAME), SvgNode {
    companion object {
        const val TAG_NAME = "clipPath"
    }
}

fun SvgClipPath.asNodeWrapper(
    minified: Boolean = false,
): ImageVectorNode.NodeWrapper {
    val nodes = children
        .asSequence()
        .filterNot { it is SvgGroupNode }
        .mapNotNull { (it as? SvgNode)?.asNode(masks = listOf(), minified) }
        .filterIsInstance<ImageVectorNode.Path>()
        .flatMap { it.wrapper.nodes }
        .toList()

    // When minified, the normalized path is ignored,
    // so we can save some computation here.
    val normalizedPath = if (minified) {
        ""
    } else {
        nodes.joinToString(" ") {
            with(it) {
                toString().removeTrailingZeroConsiderCloseCommand()
            }
        }
    }
    return ImageVectorNode.NodeWrapper(
        normalizedPath = normalizedPath,
        nodes = nodes,
    )
}
