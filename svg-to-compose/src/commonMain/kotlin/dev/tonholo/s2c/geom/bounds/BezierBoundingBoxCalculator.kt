package dev.tonholo.s2c.geom.bounds

import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Calculates the bounding box of a Bézier curve.
 *
 * The Bézier curve is defined by four points: the start point, two control points, and the end point.
 * The bounding box is the smallest rectangle that contains the entire curve.
 *
 * The algorithm used to calculate the bounding box is based on the following steps:
 *
 * 1. Calculate the derivative of the curve with respect to t.
 * 2. Solve the quadratic equation for the derivative to find the values of t where the derivative is zero.
 * 3. Evaluate the curve at the values of t found in step 2.
 * 4. Find the minimum and maximum x and y values of the points evaluated in step 3.
 * 5. Create a bounding box from the minimum and maximum x and y values.
 *
 * @property initialBoundingBox The bounding box of the start point.
 * @property currentX The x coordinate of the current point.
 * @property currentY The y coordinate of the current point.
 * @property controlPoint1x The x coordinate of the first control point.
 * @property controlPoint1y The y coordinate of the first control point.
 * @property controlPoint2x The x coordinate of the second control point.
 * @property controlPoint2y The y coordinate of the second control point.
 * @property endX The x coordinate of the end point.
 * @property endY The y coordinate of the end point.
 */
internal class BezierBoundingBoxCalculator(
    private val initialBoundingBox: BoundingBox,
    private val currentX: Double,
    private val currentY: Double,
    private val controlPoint1x: Double,
    private val controlPoint1y: Double,
    private val controlPoint2x: Double,
    private val controlPoint2y: Double,
    private val endX: Double,
    private val endY: Double,
) {

    /**
     * Calculates the bounding box that encompasses the cubic bezier curve defined by the initial bounding box,
     * control points, and end point.
     *
     * Bezier curve formula:
     * ```math
     * B(t) = (1 - t)^3 * P0 + 3(1 - t)^2 * t * P1 + 3(1 - t) * t^2 * P2 + t^3 * P3, t in [0, 1],
     * where P0 = start(x,y), P1 = control1(x,y), P2 = control2(x,y), P3 = end(x,y)
     * ```
     * The derivative of the curve, with respect to t, is:
     * ```math
     * B'(t) = 3(1 - t)^2 * i + 6(1 - t) * t * j + 3t^2 * k, t in [0, 1],
     * where i = P1 - P0, j = P2 - P1, k = P3 - P2
     * ```
     *
     * @return the bounding box for the cubic bezier curve.
     */
    fun calculate(): BoundingBox {
        var boundingBox = initialBoundingBox.copy(
            minX = min(initialBoundingBox.minX, currentX),
            minY = min(initialBoundingBox.minY, currentY),
            maxX = max(initialBoundingBox.maxX, currentX),
            maxY = max(initialBoundingBox.maxY, currentY),
        )
        val (x1, x2) = solveQuadraticEquation(
            start = currentX,
            control1 = controlPoint1x,
            control2 = controlPoint2x,
            end = endX,
        )

        if (!x1.isNaN()) {
            boundingBox = boundingBox.copy(
                minX = min(boundingBox.minX, x1),
                maxX = max(boundingBox.maxX, x1),
            )
        }

        if (!x2.isNaN()) {
            boundingBox = boundingBox.copy(
                minX = min(boundingBox.minX, x2),
                maxX = max(boundingBox.maxX, x2),
            )
        }

        val (y1, y2) = solveQuadraticEquation(
            start = currentY,
            control1 = controlPoint1y,
            control2 = controlPoint2y,
            end = endY,
        )

        if (!y1.isNaN()) {
            boundingBox = boundingBox.copy(
                minY = min(boundingBox.minY, y1),
                maxY = max(boundingBox.maxY, y1),
            )
        }

        if (!y2.isNaN()) {
            boundingBox = boundingBox.copy(
                minY = min(boundingBox.minY, y2),
                maxY = max(boundingBox.maxY, y2),
            )
        }

        return boundingBox.copy(
            minX = min(boundingBox.minX, endX),
            maxX = max(boundingBox.maxX, endX),
            minY = min(boundingBox.minY, endY),
            maxY = max(boundingBox.maxY, endY),
        )
    }

    private fun solveQuadraticEquation(
        start: Double,
        control1: Double,
        control2: Double,
        end: Double,
    ): Pair<Double, Double> {
        val i = control1 - start
        val j = control2 - control1
        val k = end - control2

        // B'(x) = (3i - 6j + 3k)t^2 + (-6i + 6j)t + 3i
        // B'(x) = a * t^2 + b * t + c, where:
        // a = 3i - 6j + 3k
        val a = 3 * i - 6 * j + 3 * k
        // b = -6i + 6j
        val b = -6 * i + 6 * j
        // c =3i
        val c = 3 * i

        if (a == 0.0) {
            return Pair(
                b.takeIf { it != 0.0 }?.let { -c / it } ?: Double.NaN,
                Double.NaN,
            )
        }

        val sqrtPart = b * b - 4 * a * c
        if (sqrtPart < 0) return Pair(Double.NaN, Double.NaN)

        val t1 = (-b - sqrt(sqrtPart)) / (2 * a)
        val t2 = (-b + sqrt(sqrtPart)) / (2 * a)

        var s1 = Double.NaN
        var s2 = Double.NaN

        if (t1 in 0.0..1.0) {
            s1 = pointAt(t1, start, control1, control2, end)
        }
        if (t2 in 0.0..1.0) {
            s2 = pointAt(t2, start, control1, control2, end)
        }

        return Pair(s1, s2)
    }

    /** @suppress the `MagicNumber` in this case as it is part of a formula that has no name */
    @Suppress("MagicNumber")
    private fun pointAt(
        t: Double,
        start: Double,
        control1: Double,
        control2: Double,
        end: Double,
    ): Double = (1 - t).let { oneMinusT ->
        (oneMinusT.pow(n = 3) * start) +
            (3 * oneMinusT.pow(n = 2) * t * control1) +
            (3 * oneMinusT * t.pow(n = 2) * control2) +
            (t.pow(n = 3) * end)
    }
}
