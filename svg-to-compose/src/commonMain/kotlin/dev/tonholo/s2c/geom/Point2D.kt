package dev.tonholo.s2c.geom

import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sign
import kotlin.math.sqrt

data class Point2D(val x: Double, val y: Double) {
    /**
     * Combines the coordinates of this point and another point into a single array.
     * The resulting array contains the x and y coordinates of this point followed by
     * the x and y coordinates of the other point, in that order.
     *
     * @param other The other point whose coordinates will be combined with this point's coordinates.
     * @return A DoubleArray containing [x, y, other.x, other.y].
     */
    infix fun coordinatesWith(other: Point2D): DoubleArray = doubleArrayOf(
        x,
        y,
        other.x,
        other.y
    )

    /**
     * Adds the coordinates of this point and the other point,
     * returning a new [Point2D].
     *
     * @param other The other [Point2D] to add.
     * @return A new [Point2D] instance with the sum of the
     *  coordinates of the two input points.
     */
    operator fun plus(other: Point2D): Point2D =
        Point2D(x + other.x, y + other.y)

    /**
     * Multiplies the coordinates of this point by the given factor.
     *
     * @param factor the factor to multiply the coordinates by
     * @return a new point with the multiplied coordinates
     */
    operator fun times(factor: Double): Point2D =
        Point2D(x = x * factor, y = y * factor)

    /**
     * Multiplies the coordinates of this point by the given factor.
     *
     * @param factor the factor to multiply the coordinates by
     * @return a new point with the multiplied coordinates
     */
    operator fun times(factor: Int): Point2D =
        Point2D(x = x * factor, y = y * factor)

    /**
     * Returns the angle formed by the vector from the origin to this
     * point and the vector from the origin to the specified point.
     * The angle is measured in radians and is in the range [0, π].
     *
     * @param point the point to compute the angle to
     * @return the angle in radians
     */
    infix fun angleTo(point: Point2D): Double {
        val sign = sign(x * point.y - y * point.x).takeIf { it != 0.0 } ?: 1.0

        val dividend = this dot point

        return sign * acos(x = round(x = (dividend / (norm() * point.norm())) * 1_000_000) / 1_000_000)
    }

    /**
     * Computes the dot product of this point with another point.
     *
     * The dot product is defined as the sum of the products of the corresponding coordinates of two points.
     *
     * @param point the other point
     * @return the dot product of this point with the given point
     */
    private infix fun dot(point: Point2D): Double =
        x * point.x + y * point.y

    /**
     * Calculates the norm (Euclidean distance) of the complex number.
     *
     * @return the norm of the complex number.
     */
    private fun norm(): Double = sqrt(abs(x).pow(2) + abs(y).pow(2))
}

/**
 * Combines this [DoubleArray] with a [Point2D] by appending the point's x and y coordinates.
 *
 * @param other the [Point2D] whose coordinates will be appended to this array
 * @return a new [DoubleArray] containing all elements of this array followed by the x and y coordinates of the point
 */
infix fun DoubleArray.combineWith(other: Point2D): DoubleArray =
    this + doubleArrayOf(other.x, other.y)

/**
 * Transforms this point by applying the given affine transformation matrix.
 * The transformation applies the matrix multiplication to the point's coordinates
 * treating the point as a homogeneous coordinate vector `[x, y, 1]`.
 *
 * @param matrix the affine transformation matrix to apply to this point
 * @return a new [Point2D] with the transformed coordinates
 */
fun Point2D.transform(matrix: AffineTransformation.Matrix): Point2D = Point2D(
    x = matrix[0][0] * x + matrix[0][1] * y + matrix[0][2] * 1,
    y = matrix[1][0] * x + matrix[1][1] * y + matrix[1][2] * 1,
)
