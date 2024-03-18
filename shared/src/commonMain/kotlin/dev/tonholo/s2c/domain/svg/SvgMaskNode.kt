package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlElementNode
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

class SvgMaskNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : XmlElementNode(parent, children, attributes, name = TAG_NAME), SvgNode {
    override val id: String by attribute()
    val style: String? by attribute()
    val maskContentUnits: String? by attribute() // <userSpaceOnUse | objectBoundingBox>; default: userSpaceOnUse
    val maskUnits: String? by attribute() // <userSpaceOnUse | objectBoundingBox>; default: objectBoundingBox
    val x: Int? by attribute()
    val y: Int? by attribute()
    val width: Int? by attribute()
    val height: Int? by attribute()

    private var _path: SvgPathNode? = null

    // Can a svg mask have more than one path?
    // Currently, a group on ImageVector only receives a single PathData as a parameter.
    // Not sure if it would support multiple path tags inside a mask from a svg.
    val path: SvgPathNode
        get() = _path ?: children.single { it is SvgPathNode } as SvgPathNode

    companion object {
        const val TAG_NAME = "mask"
    }
}
