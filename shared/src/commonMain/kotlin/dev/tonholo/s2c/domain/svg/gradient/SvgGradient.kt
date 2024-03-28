package dev.tonholo.s2c.domain.svg.gradient

import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.svg.SvgElementNode
import dev.tonholo.s2c.domain.svg.SvgLength
import dev.tonholo.s2c.domain.svg.SvgNode
import dev.tonholo.s2c.domain.svg.SvgRootNode
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

sealed class SvgGradient<out T>(
    parent: XmlParentNode,
    children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
    tagName: String,
) : SvgElementNode<T>(parent, children, attributes, tagName)
    where T : SvgNode, T : XmlParentNode {
    val gradientUnits: String by attribute(defaultValue = "objectBoundingBox") // <userSpaceOnUse | objectBoundingBox>
    val gradientTransform: String? by attribute()
    val spreadMethod: String by attribute(defaultValue = "pad") // <pad | reflect | repeat>
    val href: String? by attribute(name = "xlink:href")

    internal inline fun calculateGradientCoordinate(length: SvgLength): Float {
        val root = rootParent as SvgRootNode
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
