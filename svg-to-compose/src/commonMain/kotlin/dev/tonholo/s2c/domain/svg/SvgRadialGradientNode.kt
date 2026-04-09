package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.compose.ComposeBrush
import dev.tonholo.s2c.domain.compose.ComposeOffset
import dev.tonholo.s2c.domain.svg.gradient.SvgRadialGradient
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.geom.AffineTransformation
import dev.tonholo.s2c.geom.Point2D
import dev.tonholo.s2c.geom.transform
import kotlin.math.sqrt

class SvgRadialGradientNode(
    parent: XmlParentNode,
    children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : SvgRadialGradient<SvgRadialGradientNode>(parent, children, attributes),
    SvgNode {
    override val constructor = ::SvgRadialGradientNode

    override fun createGradientBrush(target: List<PathNodes>): ComposeBrush.Gradient.Radial {
        val (colors, stops) = colorStops

        // Compose's radialGradient has a single center parameter with no separate
        // focal point. SVG defines cx/cy as the end circle center (where 100%
        // stops map) and fx/fy as the focal point (where 0% stops originate).
        //
        // We use cx/cy as Compose's center to preserve correct gradient circle
        // boundaries. The focal point (fx/fy) is dropped because Compose has no
        // equivalent parameter. This matches Android Studio's SVG-to-VectorDrawable
        // conversion behavior. When fx/fy differs from cx/cy, the eccentric color
        // origin is lost, but the circle boundary remains accurate, which produces
        // fewer visual artifacts overall.
        var centerX = calculateGradientXCoordinate(cx, target)
        var centerY = calculateGradientYCoordinate(cy, target)
        var gradientRadius = calculateGradientXYCoordinate(radius, target, translateByBoundingBoxOrigin = false)

        val matrix = computeGradientTransformMatrix()
        if (matrix != null) {
            val transformedCenter = Point2D(centerX, centerY).transform(matrix)
            centerX = transformedCenter.x
            centerY = transformedCenter.y
            gradientRadius = approximateCircleRadius(gradientRadius, matrix)
        }

        val centerOffset = ComposeOffset(x = centerX.toFloat(), y = centerY.toFloat())
        return ComposeBrush.Gradient.Radial(
            center = centerOffset,
            tileMode = spreadMethod.toCompose(),
            colors = colors.map { it.toComposeColor() },
            stops = stops,
            radius = gradientRadius.toFloat(),
        )
    }

    /**
     * Approximates the radius of a circle after applying an affine transformation.
     *
     * When an affine transformation is applied to a circle, it generally becomes an ellipse.
     * This method calculates an approximate radius for the transformed shape using the
     * root-mean-square (RMS) of the singular values of the transformation matrix's linear part.
     * This provides an average scale factor that represents how the transformation affects
     * the circle's size.
     *
     * The calculation uses the formula: `sqrt((s1^2 + s2^2) / 2)`, where `s1` and `s2` are the
     * singular values, and `s1^2 + s2^2 = a^2 + b^2 + c^2 + d^2`, with a, b, c, d being the
     * elements of the 2x2 linear transformation matrix.
     *
     * @param gradientRadius the original radius of the circle before transformation
     * @param matrix the affine transformation matrix to apply, where only the linear
     *  transformation part (top-left 2x2 submatrix) is used for the approximation
     * @return the approximated radius after transformation
     */
    private fun approximateCircleRadius(gradientRadius: Double, matrix: AffineTransformation.Matrix): Double {
        val a = matrix[0][0]
        val b = matrix[1][0]
        val c = matrix[0][1]
        val d = matrix[1][1]
        @Suppress("MagicNumber")
        return gradientRadius * sqrt((a * a + b * b + c * c + d * d) / 2.0)
    }
}
