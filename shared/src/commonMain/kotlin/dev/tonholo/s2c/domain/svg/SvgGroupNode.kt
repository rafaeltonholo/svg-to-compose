package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.asNodeWrapper
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlElementNode
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

class SvgGroupNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : XmlElementNode(parent, children, attributes, name = TAG_NAME), SvgNode {
    val maskId: String? by attribute("mask")
    val filterId: String? by attribute("filter")
    val opacity: Float? by attribute()
    val fillOpacity: Float? by attribute("fill-opacity")
    val style: String? by attribute()

    companion object {
        const val TAG_NAME = "g"
    }
}

fun SvgGroupNode.asNode(
    masks: List<SvgMaskNode>,
    minified: Boolean = false,
): ImageVectorNode.Group {
    // Can a svg mask have more than one path?
    // Currently, a group on ImageVector only receives a single PathData as a parameter.
    // Not sure if it would support multiple path tags inside a mask from a svg.
    val clipPath = masks
        .firstOrNull {
            it.id == maskId?.removePrefix("url(#")?.removeSuffix(")")
        }
        ?.path
        ?.d
        ?.asNodeWrapper(minified)

    return ImageVectorNode.Group(
        clipPath = clipPath,
        commands = children.mapNotNull { (it as? SvgNode)?.asNode(masks, minified) },
        minified = minified,
    )
}
