package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlChildNode
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.extensions.toPercentage

class SvgGradientStopNode(
    override val parent: XmlParentNode,
    override val attributes: MutableMap<String, String>,
) : XmlChildNode(), SvgNode {
    /**
     * [StopElementOffsetAttribute](https://www.w3.org/TR/SVG11/single-page.html#pservers-StopElementOffsetAttribute)
     */
    val offset: Float by attribute<String, Float>(defaultValue = 0f, transform = String::toPercentage)

    /**
     * [StopColorProperty](https://www.w3.org/TR/SVG11/single-page.html#pservers-StopColorProperty)
     */
    val stopColor: SvgColor by attribute<String, SvgColor>(
        name = "stop-color",
        defaultValue = SvgColor("black"),
        transform = SvgColor::invoke,
    )

    /**
     * [StopOpacityProperty](https://www.w3.org/TR/SVG11/single-page.html#pservers-StopOpacityProperty)
     */
    val stopOpacity: Float by attribute(name = "stop-opacity", defaultValue = 1f)

    override fun toString(): String = toJsString {
        append("\"offset\":\"$offset, \"")
        append("\"stop-color\":\"$stopColor\", ")
        append("\"stop-opacity\":\"$stopOpacity\"")
    }

    override val tagName: String = TAG_NAME

    companion object {
        const val TAG_NAME = "stop"
    }
}
