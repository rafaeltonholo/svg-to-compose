package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

class SvgMaskNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : SvgElementNode<SvgMaskNode>(parent, children, attributes, tagName = TAG_NAME), SvgNode {
    override val constructor = ::SvgMaskNode
    override val id: String by attribute()
    val style: String? by attribute()
    val maskContentUnits: String? by attribute() // <userSpaceOnUse | objectBoundingBox>; default: userSpaceOnUse
    val maskUnits: String? by attribute() // <userSpaceOnUse | objectBoundingBox>; default: objectBoundingBox
    val x: Int? by attribute()
    val y: Int? by attribute()
    val width: Int? by attribute()
    val height: Int? by attribute()

    companion object {
        const val TAG_NAME = "mask"
    }
}
