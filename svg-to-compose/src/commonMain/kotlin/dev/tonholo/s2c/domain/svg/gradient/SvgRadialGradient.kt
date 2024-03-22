package dev.tonholo.s2c.domain.svg.gradient

import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.svg.SvgLength
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

abstract class SvgRadialGradient(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : SvgGradient(parent, children, attributes, tagName = TAG_NAME) {
    /**
     * [CXAttribute](https://www.w3.org/TR/SVG11/single-page.html#pservers-RadialGradientElementCXAttribute)
     */
    val cx: Float by attribute<SvgLength, Float>(defaultValue = 0.5f, transform = ::calculateGradientCoordinate)

    /**
     * [CYAttribute](https://www.w3.org/TR/SVG11/single-page.html#pservers-RadialGradientElementCYAttribute)
     */
    val cy: Float by attribute<SvgLength, Float>(defaultValue = 0.5f, transform = ::calculateGradientCoordinate)

    /**
     * [RAttribute](https://www.w3.org/TR/SVG11/single-page.html#pservers-RadialGradientElementRAttribute)
     */
    val radius: Float by attribute<SvgLength?, Float>(
        name = "r",
        transform = { length ->
            calculateGradientCoordinate(length, defaultLength = SvgLength("50%"))
        }
    )
    /**
     * [FXAttribute](https://www.w3.org/TR/SVG11/single-page.html#pservers-RadialGradientElementFXAttribute)
     */
    val fx: Float by attribute<SvgLength, Float>(defaultValue = cx, transform = ::calculateGradientCoordinate)

    /**
     * [FYAttribute](https://www.w3.org/TR/SVG11/single-page.html#pservers-RadialGradientElementFYAttribute)
     */
    val fy: Float by attribute<SvgLength, Float>(defaultValue = cy, transform = ::calculateGradientCoordinate)

    companion object {
        const val TAG_NAME = "radialGradient"
    }
}
