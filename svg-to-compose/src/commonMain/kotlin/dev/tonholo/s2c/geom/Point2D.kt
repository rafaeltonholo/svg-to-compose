package dev.tonholo.s2c.geom

import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sign
import kotlin.math.sqrt

data class Point2D(val x: Float, val y: Float) {
    operator fun plus(other: Point2D): FloatArray = floatArrayOf(x, y, other.x, other.y)

    operator fun plus(other: PrecisePoint2D): DoubleArray =
        doubleArrayOf(x.toDouble(), y.toDouble(), other.x, other.y)
}

operator fun FloatArray.plus(other: Point2D): FloatArray = this + floatArrayOf(other.x, other.y)

// TODO: consider migrating Point2D to use Double instead.
/**
 * A class representing a 2D point with double precision.
 *
 * @param x The x-coordinate of the point.
 * @param y The y-coordinate of the point.
 *
 * @remarks inspired on https://github.com/svgdotjs/svgdom/blob/0cf61d3d3d8be88c69304ec589ea035e606071f4/src/other/Point.js#L3
 */
data class PrecisePoint2D(val x: Double, val y: Double) {
    /**
     * Returns a double array containing the [x] and [y] coordinates
     * of this point and the [x] and [y] coordinates of the other point.
     *
     * @param other the other point
     * @return a double array containing the coordinates of both points
     */
    operator fun plus(other: Point2D): DoubleArray =
        doubleArrayOf(x, y, other.x.toDouble(), other.y.toDouble())

    /**
     * Adds two [PrecisePoint2D] instances together, returning a new
     * [PrecisePoint2D] instance.
     *
     * The resulting [PrecisePoint2D] will have the sum of the [x]
     * and [y] coordinates of the two input points.
     *
     * @param other The other [PrecisePoint2D] to add.
     * @return A new [PrecisePoint2D] instance with the sum of the
     *  coordinates of the two input points.
     */
    operator fun plus(other: PrecisePoint2D): PrecisePoint2D =
        PrecisePoint2D(x + other.x, y + other.y)

    /**
     * Multiplies the coordinates of this point by the given factor.
     *
     * @param factor the factor to multiply the coordinates by
     * @return a new point with the multiplied coordinates
     */
    operator fun times(factor: Double): PrecisePoint2D =
        PrecisePoint2D(x = x * factor, y = y * factor)

    /**
     * Multiplies the coordinates of this point by the given factor.
     *
     * @param factor the factor to multiply the coordinates by
     * @return a new point with the multiplied coordinates
     */
    operator fun times(factor: Int): PrecisePoint2D =
        PrecisePoint2D(x = x * factor, y = y * factor)

    /**
     * Returns the angle formed by the vector from the origin to this
     * point and the vector from the origin to the specified point.
     * The angle is measured in radians and is in the range [0, π].
     *
     * @param point the point to compute the angle to
     * @return the angle in radians
     */
    infix fun angleTo(point: PrecisePoint2D): Double {
        val sign = sign(x * point.y - y * point.x).takeIf { it != 0.0 } ?: 1.0

        val dividend = this dot point

        return sign * acos(round((dividend / (norm() * point.norm())) * 1_000_000) / 1_000_000)
    }

    /**
     * Computes the dot product of this point with another point.
     *
     * The dot product is defined as the sum of the products of the corresponding coordinates of two points.
     *
     * @param point the other point
     * @return the dot product of this point with the given point
     */
    private infix fun dot(point: PrecisePoint2D): Double =
        x * point.x + y * point.y

    /**
     * Calculates the norm (Euclidean distance) of the complex number.
     *
     * @return the norm of the complex number.
     */
    private fun norm(): Double = sqrt(abs(x).pow(2) + abs(y).pow(2))
}

operator fun DoubleArray.plus(other: PrecisePoint2D): DoubleArray =
    this + doubleArrayOf(other.x, other.y)

fun PrecisePoint2D.transform(matrix: AffineTransformation.Matrix): PrecisePoint2D =
    PrecisePoint2D(
        x = matrix[0][0] * x + matrix[0][1] * y + matrix[0][2] * 1,
        y = matrix[1][0] * x + matrix[1][1] * y + matrix[1][2] * 1,
    )
