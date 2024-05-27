package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.compose.ComposeBrush
import dev.tonholo.s2c.domain.compose.ComposeOffset
import dev.tonholo.s2c.domain.svg.gradient.SvgLinearGradient
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

class SvgLinearGradientNode(
    parent: XmlParentNode,
    children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : SvgLinearGradient<SvgLinearGradientNode>(parent, children, attributes), SvgNode {
    override val constructor = ::SvgLinearGradientNode

    override fun toBrush(
        target: List<PathNodes>,
    ): ComposeBrush.Gradient.Linear {
        val (colors, stops) = colorStops

        val startOffset = ComposeOffset(
            x = calculateGradientXCoordinate(x1, target),
            y = calculateGradientXYCoordinate(y1, target),
        )

        val endOffset = ComposeOffset(
            x = calculateGradientXCoordinate(x2, target),
            y = calculateGradientYCoordinate(y2, target),
        )

        return ComposeBrush.Gradient.Linear(
            start = startOffset,
            end = endOffset,
            tileMode = spreadMethod.toCompose(),
            colors = colors.map { it.toComposeColor() },
            stops = stops,
        )
    }
}
