package dev.tonholo.s2c.domain.svg.gradient

import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.svg.SvgElementNode
import dev.tonholo.s2c.domain.svg.SvgLength
import dev.tonholo.s2c.domain.xml.XmlElementNode
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

sealed class SvgGradient(
    parent: XmlParentNode,
    children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
    tagName: String,
) : XmlElementNode(parent, children, attributes, tagName) {
    val gradientUnits: String by attribute(defaultValue = "objectBoundingBox") // <userSpaceOnUse | objectBoundingBox>
    val gradientTransform: String? by attribute()
    val spreadMethod: String by attribute(defaultValue = "pad") // <pad | reflect | repeat>
    val href: String? by attribute(name = "xlink:href")

    internal inline fun calculateGradientCoordinate(length: SvgLength): Float {
        val root = rootParent as SvgElementNode
        return if (gradientUnits == "userSpaceOnUse") {
            val boundingBox = root.viewportWidth
            // TODO: calculate bounding box.
            length.toFloat(boundingBox)
        } else {
            val baseDimension = root.viewportWidth
            length.toFloat(baseDimension)
        }
    }

    internal inline fun calculateGradientCoordinate(length: SvgLength?, defaultLength: SvgLength): Float {
        val svgLength = length ?: defaultLength
        return calculateGradientCoordinate(svgLength)
    }
}
