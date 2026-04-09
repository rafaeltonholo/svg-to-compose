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

        var cx = calculateGradientXCoordinate(cx, target)
        var cy = calculateGradientYCoordinate(cy, target)
        var gradientRadius = calculateGradientXYCoordinate(radius, target, translateByBoundingBoxOrigin = false)

        val matrix = computeGradientTransformMatrix()
        if (matrix != null) {
            val transformed = Point2D(cx, cy).transform(matrix)
            cx = transformed.x
            cy = transformed.y
            gradientRadius = approximateCircleRadius(gradientRadius, matrix)
        }
        val centerOffset = ComposeOffset(x = cx.toFloat(), y = cy.toFloat())

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
     * The calculation uses the formula: `sqrt((σ₁² + σ₂²) / 2)`, where `σ₁` and `σ₂` are the
     * singular values, and `σ₁² + σ₂² = a² + b² + c² + d²`, with a, b, c, d being the
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
