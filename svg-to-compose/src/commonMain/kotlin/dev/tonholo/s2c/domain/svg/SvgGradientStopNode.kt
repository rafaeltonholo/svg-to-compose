package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.extensions.toPercentage
import kotlin.math.roundToInt

/**
 * Represents an `<stop>` element of an SVG `linearGradient` or `radialGradient` element.
 *
 * The `<stop>` element defines a color stop along the gradient line.
 *
 * see: [SVG Gradient Stops](https://www.w3.org/TR/SVG11/single-page.html#pservers-GradientStops)
 */
class SvgGradientStopNode(
    parent: XmlParentNode,
    override val attributes: MutableMap<String, String>,
) : SvgChildNode<SvgGradientStopNode>(parent), SvgNode {
    override val constructor = ::SvgGradientStopNode

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
    private val stopOpacity: Float by attribute(name = "stop-opacity", defaultValue = 1f)

    /**
     * Computes the color of the gradient stop, taking into account the
     * `stop-color` and `stop-opacity` attributes.
     *
     * The resulting color is an instance of [SvgColor].
     *
     * @return The computed gradient color.
     */
    @OptIn(ExperimentalStdlibApi::class)
    val gradientColor
        get(): SvgColor {
            val format = HexFormat {
                number {
                    prefix = "#"
                }
                upperCase = true
            }
            val currentColor = (
                "#" + stopColor
                    .toComposeColor()
                    .color
                )
                .hexToUInt(format)
            val alpha = (currentColor shr 24) and 0xFFu
            val mask = ((alpha.toInt() * stopOpacity).roundToInt() and 0xFF).toUInt()
            val gradientColor = (currentColor and 0x00FFFFFFu) or (mask shl 24)
            return SvgColor(value = gradientColor.toHexString(format))
        }

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
