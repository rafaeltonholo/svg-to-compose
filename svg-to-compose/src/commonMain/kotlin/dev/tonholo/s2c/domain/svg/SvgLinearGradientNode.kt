package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.compose.ComposeBrush
import dev.tonholo.s2c.domain.compose.ComposeOffset
import dev.tonholo.s2c.domain.svg.gradient.SvgLinearGradient
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.geom.Point2D
import dev.tonholo.s2c.geom.transform

class SvgLinearGradientNode(
    parent: XmlParentNode,
    children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : SvgLinearGradient<SvgLinearGradientNode>(parent, children, attributes),
    SvgNode {
    override val constructor = ::SvgLinearGradientNode

    override fun toBrush(target: List<PathNodes>): ComposeBrush.Gradient = if (href != null) {
        resolveReferencedGradient().toBrush(target)
    } else {
        createLinearGradientBrush(target)
    }

    private fun resolveReferencedGradient(): SvgLinearGradientNode {
        val root = rootParent as SvgRootNode
        val hrefId = checkNotNull(href).normalizedId()
        val referencedGradient = checkNotNull(root.gradients[hrefId]) as SvgLinearGradientNode
        val mergedAttributes = buildMergedAttributes(referencedGradient)

        return referencedGradient.copy(attributes = mergedAttributes) as SvgLinearGradientNode
    }

    private fun buildMergedAttributes(
        referencedGradient: SvgLinearGradientNode,
    ): MutableMap<String, String> = referencedGradient.attributes.toMutableMap().apply {
        remove(SvgUseNode.HREF_ATTR_KEY)
        // Overlay this node's attributes onto referenced, so local values override inherited ones
        for ((key, value) in this@SvgLinearGradientNode.attributes) {
            if (key != SvgUseNode.HREF_ATTR_KEY) {
                put(key, value)
            }
        }
    }

    private fun createLinearGradientBrush(target: List<PathNodes>): ComposeBrush.Gradient.Linear {
        val (colors, stops) = colorStops
        val (start, end) = calculateGradientOffsets(target)

        return ComposeBrush.Gradient.Linear(
            start = ComposeOffset(start.x.toFloat(), start.y.toFloat()),
            end = ComposeOffset(end.x.toFloat(), end.y.toFloat()),
            tileMode = spreadMethod.toCompose(),
            colors = colors.map { it.toComposeColor() },
            stops = stops,
        )
    }

    private fun calculateGradientOffsets(target: List<PathNodes>): Pair<Point2D, Point2D> {
        val startOffset = Point2D(
            x = calculateGradientXCoordinate(x1, target),
            y = calculateGradientYCoordinate(y1, target),
        )
        val endOffset = Point2D(
            x = calculateGradientXCoordinate(x2, target),
            y = calculateGradientYCoordinate(y2, target),
        )

        return applyGradientTransform(startOffset, endOffset)
    }

    private fun applyGradientTransform(start: Point2D, end: Point2D): Pair<Point2D, Point2D> {
        val matrix = computeGradientTransformMatrix()
        return if (matrix != null) {
            start.transform(matrix) to end.transform(matrix)
        } else {
            start to end
        }
    }
}
