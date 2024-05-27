package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.compose.ComposeBrush
import dev.tonholo.s2c.domain.compose.ComposeOffset
import dev.tonholo.s2c.domain.svg.gradient.SvgRadialGradient
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

class SvgRadialGradientNode(
    parent: XmlParentNode,
    children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : SvgRadialGradient<SvgRadialGradientNode>(parent, children, attributes), SvgNode {
    override val constructor = ::SvgRadialGradientNode

    override fun toBrush(
        target: List<PathNodes>,
    ): ComposeBrush.Gradient.Radial {
        val (colors, stops) = colorStops

        val cx = calculateGradientXCoordinate(cx, target)
        val cy = calculateGradientYCoordinate(cy, target)

        val centerOffset = ComposeOffset(x = cx, y = cy)
        val gradientRadius = calculateGradientXYCoordinate(radius, target)

        return ComposeBrush.Gradient.Radial(
            center = centerOffset,
            tileMode = spreadMethod.toCompose(),
            colors = colors.map { it.toComposeColor() },
            stops = stops,
            radius = gradientRadius,
        )
    }
}
