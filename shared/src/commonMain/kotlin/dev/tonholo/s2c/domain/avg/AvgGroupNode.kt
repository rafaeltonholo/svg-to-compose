package dev.tonholo.s2c.domain.avg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.asNodeWrapper
import dev.tonholo.s2c.domain.xml.XmlElementNode
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

class AvgGroupNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : XmlElementNode(parent, children, attributes, name = TAG_NAME), AvgNode {
    val clipPath: AvgClipPath?
        get() = children.firstOrNull { it is AvgClipPath } as? AvgClipPath

    companion object {
        const val TAG_NAME = "group"
    }
}

fun AvgGroupNode.asNode(
    minified: Boolean,
): ImageVectorNode.Group = ImageVectorNode.Group(
    clipPath = clipPath?.pathData?.asNodeWrapper(minified),
    commands = children.mapNotNull { (it as? AvgNode)?.asNode(minified) },
    minified = minified,
)
