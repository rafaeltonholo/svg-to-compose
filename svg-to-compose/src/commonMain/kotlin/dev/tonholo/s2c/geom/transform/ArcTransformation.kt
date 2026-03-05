package dev.tonholo.s2c.geom.transform

import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.geom.AffineTransformation
import dev.tonholo.s2c.geom.Point2D
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

@Suppress("MagicNumber")
internal data object ArcTransformation : PathTransformation<PathNodes.ArcTo>() {
    override fun PathNodes.ArcTo.applyTransformation(
        cursor: DoubleArray,
        start: DoubleArray,
        transformation: AffineTransformation,
    ): PathNodes {
        val newNode = if (isRelative) {
            val transformedNode = transformArc(cursor = doubleArrayOf(0.0, 0.0), transformation)
            cursor[0] += transformedNode.x
            cursor[1] += transformedNode.y
            transformArc(transformedNode, matrix = transformation.matrix, transform = ::transformRelativePoint)
        } else {
            val transformedNode = transformArc(cursor, transformation)
            cursor[0] = transformedNode.x
            cursor[1] = transformedNode.y
            transformArc(transformedNode, matrix = transformation.matrix, transform = ::transformAbsolutePoint)
        }
        return newNode
    }

    private fun transformArc(
        node: PathNodes.ArcTo,
        matrix: Array<out DoubleArray>,
        transform: (matrix: Array<out DoubleArray>, x: Double, y: Double) -> Point2D,
    ): PathNodes {
        // reduce the number of digits in rotation angle
        val (a, b, theta) = if (abs(node.theta) > 80) {
            val newA = node.b
            val newB = node.a
            val newTheta = node.theta + (if (node.theta > 0) -90 else 90)
            doubleArrayOf(newA, newB, newTheta)
        } else {
            doubleArrayOf(node.a, node.b, node.theta)
        }
        val (x, y) = transform(matrix, node.x, node.y)
        return node.new(
            listOf(
                a,
                b,
                theta,
                node.isMoreThanHalf,
                node.isPositiveArc,
                x,
                y,
            )
        )
    }

    /**
     * Applies transformation to an arc.
     * To do so, we represent ellipse as a matrix, multiply it
     * by the transformation matrix and use a singular value decomposition
     * to represent in a form rotate(θ)·scale(a b)·rotate(φ).
     *
     * This gives us new ellipse params a, b and θ.
     * SVD is being done with the formulae provided by Wolfram|Alpha
     * (svd {{m0, m2}, {m1, m3}})
     *
     * @see <a href="https://github.com/svg/svgo/blob/main/plugins/applyTransforms.js">SVGO apply transform plugin</a>
     */
    private fun PathNodes.ArcTo.transformArc(
        cursor: DoubleArray,
        transformation: AffineTransformation,
    ): PathNodes.ArcTo {
        val x = x - cursor[0]
        val y = y - cursor[1]
        val theta = (theta * PI) / 180.0
        val cos = cos(theta)
        val sin = sin(theta)
        // skip if radius is 0
        val (a, b) = applyOutOfRangeRadiiCorrection(x, cos, y, sin)

        val ellipse = parseEllipticalArcToEllipseMatrix(a, cos, b, sin)
        // apply transformations to elliptical-arc as matrix.
        val m = (transformation * ellipse).matrix

        // Decompose the new ellipse matrix
        val (newA, newB, newTheta) = decomposeEllipseMatrix(m)

        // Flip the sweep flag if coordinates are being flipped horizontally XOR vertically
        val sweepFlag = (transformation.matrix[0][0] < 0 != transformation.matrix[1][1] < 0) xor isPositiveArc

        return new(
            args = listOf(
                /*a =*/
                newA,
                /*b =*/
                newB,
                /*theta =*/
                newTheta,
                /*isMoreThanHalf =*/
                isMoreThanHalf,
                /*isPositiveArc =*/
                sweepFlag,
                /*x =*/
                this.x,
                /*y =*/
                this.y,
            ),
        ) as PathNodes.ArcTo
    }

    private fun decomposeEllipseMatrix(matrix: Array<out DoubleArray>): DoubleArray {
        val lastColumn = matrix[0][1] * matrix[0][1] + matrix[1][1] * matrix[1][1]
        val squareSum = matrix[0][0] * matrix[0][0] + matrix[1][0] * matrix[1][0] + lastColumn
        val root =
            hypot(matrix[0][0] - matrix[1][1], matrix[1][0] + matrix[0][1]) *
                hypot(matrix[0][0] + matrix[1][1], matrix[1][0] - matrix[0][1])

        return if (root == 0.0) {
            // circle
            doubleArrayOf(
                sqrt(squareSum / 2),
                sqrt(squareSum / 2),
                0.0,
            )
        } else {
            val majorAxisSqr = (squareSum + root) / 2
            val minorAxisSqr = (squareSum - root) / 2
            val major = abs(majorAxisSqr - lastColumn) > 1e-6
            val sub = (if (major) majorAxisSqr else minorAxisSqr) - lastColumn
            val rowsSum = matrix[0][0] * matrix[0][1] + matrix[1][0] * matrix[1][1]
            val term1 = matrix[0][0] * sub + matrix[0][1] * rowsSum
            val term2 = matrix[1][0] * sub + matrix[1][1] * rowsSum
            val isNegative = if (major) term2 < 0 else term1 > 0
            val negativeMultiplier = if (isNegative) -1 else 1

            doubleArrayOf(
                sqrt(majorAxisSqr),
                sqrt(minorAxisSqr),
                (negativeMultiplier * acos((if (major) term1 else term2) / hypot(term1, term2)) * 180) / PI,
            )
        }
    }

    private fun parseEllipticalArcToEllipseMatrix(
        a: Double,
        cos: Double,
        b: Double,
        sin: Double,
    ) = AffineTransformation.Matrix(
        doubleArrayOf(a * cos, -b * sin, 0.0),
        doubleArrayOf(a * sin, b * cos, 0.0),
        doubleArrayOf(0.0, 0.0, 1.0),
    )

    private fun PathNodes.ArcTo.applyOutOfRangeRadiiCorrection(
        x: Double,
        cos: Double,
        y: Double,
        sin: Double,
    ) = if (a > 0 && b > 0) {
        val a = a
        val b = b
        // If a and b bigger than 0, it ensures that the ellipse arc is
        // valid and not degenerated.

        // Determine if the endpoint of the arc is outside the bounds of the ellipse.
        // By using the distance formula: x'^2 / a^2 + y'^2 / b^2 = 1
        // where x' and y' are coordinates of the rotated point
        // x' = ((x * cos(θ) + y * sin(θ)) / 2 * a)
        // y' = ((y * cos(θ) - x * sin(θ)) / 2 * b)
        var scalingFactor = (x * cos + y * sin).pow(2) / (4 * a * a) +
            (y * cos - x * sin).pow(2) / (4 * b * b)

        if (scalingFactor > 1) {
            // The endpoint is outside the ellipse.
            // Adjust it to ensure that the ellipse encompasses the endpoint of the arc.
            scalingFactor = sqrt(scalingFactor)

            // Scales both a and b by this scaling factor.
            a * scalingFactor to b * scalingFactor
        } else {
            a to b
        }
    } else {
        a to b
    }
}
