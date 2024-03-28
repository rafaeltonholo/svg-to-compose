package dev.tonholo.s2c.domain.svg.gradient

import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.svg.SvgLength
import dev.tonholo.s2c.domain.svg.SvgNode
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

abstract class SvgLinearGradient<out T>(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : SvgGradient<T>(parent, children, attributes, tagName = TAG_NAME)
    where T : SvgNode, T : XmlParentNode {
    /**
     * [X1Attribute](https://www.w3.org/TR/SVG11/single-page.html#pservers-LinearGradientElementX1Attribute)
     */
    val x1: Float by attribute<SvgLength, Float>(defaultValue = 0f, transform = ::calculateGradientCoordinate)

    /**
     * [Y1Attribute](https://www.w3.org/TR/SVG11/single-page.html#pservers-LinearGradientElementY1Attribute)
     */
    val y1: Float by attribute<SvgLength, Float>(defaultValue = 0f, transform = ::calculateGradientCoordinate)

    /**
     * [X2Attribute](https://www.w3.org/TR/SVG11/single-page.html#pservers-LinearGradientElementX2Attribute)
     */
    val x2: Float by attribute<SvgLength, Float>(defaultValue = 1f, transform = ::calculateGradientCoordinate)

    /**
     * [Y2Attribute](https://www.w3.org/TR/SVG11/single-page.html#pservers-LinearGradientElementY2Attribute)
     */
    val y2: Float by attribute<SvgLength, Float>(defaultValue = 0f, transform = ::calculateGradientCoordinate)

    companion object {
        const val TAG_NAME = "linearGradient"
    }
}
