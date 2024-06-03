package dev.tonholo.s2c.geom.bounds

import dev.tonholo.s2c.geom.AffineTransformation
import dev.tonholo.s2c.geom.PrecisePoint2D
import dev.tonholo.s2c.geom.transform
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.properties.Delegates

private const val DOUBLE_ZERO_TOLERANCE = 1e-15
private const val ANGLE_90 = 90
private const val ANGLE_180 = 180
private const val ANGLE_270 = 270
private const val ANGLE_360 = 360

/**
 * Calculates the bounding box of an elliptical arc.
 *
 * The algorithm is based on the SVG specification:
 * https://www.w3.org/TR/SVG/implnote.html#ArcConversionEndpointToCenter
 *
 * @param boundingBox The current bounding box.
 * @param current The current coordinate.
 * @param x The x-coordinate of the arc's end point.
 * @param y The y-coordinate of the arc's end point.
 * @param rx The x-axis radius of the arc.
 * @param ry The y-axis radius of the arc.
 * @param phi The angle of rotation of the arc.
 * @param largeArcFlag Whether the arc is a large arc.
 * @param sweepFlag Whether the arc is a sweep arc.
 * @remark Inspired on https://github.com/svgdotjs/svgdom/blob/0cf61d3d3d8be88c69304ec589ea035e606071f4/src/utils/pathUtils.js#L176
 */
internal class ArcBoundingBoxCalculator(
    private val boundingBox: BoundingBox,
    private val current: PrecisePoint2D,
    private val x: Double,
    private val y: Double,
    private var rx: Double,
    private var ry: Double,
    phi: Double,
    private val largeArcFlag: Boolean,
    private val sweepFlag: Boolean,
) {
    // pre-calculate cos of phi to avoid recalculating it every time
    private val cosPhi = cos(x = phi / ANGLE_180 * PI)
    private val sinPhi = sin(x = phi / ANGLE_180 * PI)

    // (eq. 5.1)
    private val primedCoordinates = PrecisePoint2D(
        x = (current.x - x) / 2,
        y = (current.y - y) / 2,
    ).transform(
        AffineTransformation.Matrix(
            floatArrayOf(cosPhi.toFloat(), sinPhi.toFloat(), 0f),
            floatArrayOf(-sinPhi.toFloat(), cosPhi.toFloat(), 0f),
            floatArrayOf(0f, 0f, 0f),
        )
    )

    private lateinit var primedCenter: PrecisePoint2D
    private lateinit var center: PrecisePoint2D
    private var theta by Delegates.notNull<Double>()
    private var theta2 by Delegates.notNull<Double>()
    private var delta by Delegates.notNull<Double>()

    // https://www.w3.org/TR/SVG/implnote.html#ArcConversionEndpointToCenter
    fun calculate(): BoundingBox {
        // If rx = 0 or ry = 0, then treat this as a straight line from (x1, y1) to (x2, y2) and stop.
        if (rx == 0.0 || ry == 0.0 || (current.x == x && current.y == y)) {
            // calculate bounding box as line.
            return boundingBox.copy(
                minX = min(boundingBox.minX, x),
                minY = min(boundingBox.minY, y),
                maxX = max(boundingBox.maxX, x),
                maxY = max(boundingBox.maxY, y),
            )
        }

        // eq. 6.1, 6.2 end 6.3
        correctOutOfRangeRadii(primedCoordinates)

        // eq. 5.2 and 5.3
        computeCenterCoordinates()

        // eq. 5.4, 5.5, 5.6
        computeThetasAndDelta()

        return computeArcRotation()
            .filter { angle -> angle > theta && angle < theta2 }
            .map { angle ->
                var currentAngle = angle
                while (theta < currentAngle) currentAngle -= ANGLE_360
                pointAt(((angle - theta) % ANGLE_360) / (delta))
            }
            .plus(
                listOf(
                    PrecisePoint2D(current.x, current.y),
                    PrecisePoint2D(x, y),
                )
            )
            .fold(
                initial = boundingBox,
            ) { box, point ->
                box.copy(
                    minX = min(box.minX, point.x),
                    minY = min(box.minY, point.y),
                    maxX = max(box.maxX, point.x),
                    maxY = max(box.maxY, point.y),
                )
            }
    }

    private fun correctOutOfRangeRadii(
        primedCoordinates: PrecisePoint2D,
    ) {
        // eq. 6.1
        rx = abs(rx)
        ry = abs(ry)

        // (eq. 6.2)
        // Make sure the radius fit with the arc and correct if necessary
        val ratio = (primedCoordinates.x.pow(n = 2) / rx.pow(n = 2)) +
            (primedCoordinates.y.pow(n = 2) / ry.pow(n = 2))

        // (eq. 6.3)
        if (ratio > 1) {
            rx *= sqrt(ratio)
            ry *= sqrt(ratio)
        }
    }

    private fun computeCenterCoordinates() {
        // (eq. 5.2)
        val rxQuad = rx.pow(n = 2)
        val ryQuad = ry.pow(n = 2)

        val divisor1 = rxQuad * primedCoordinates.y.pow(n = 2)
        val divisor2 = ryQuad * primedCoordinates.x.pow(n = 2)
        val dividend = (rxQuad * ryQuad - divisor1 - divisor2)
        val multiplier = if (largeArcFlag == sweepFlag) -1 else 1

        primedCenter = if (abs(dividend) < DOUBLE_ZERO_TOLERANCE) {
            PrecisePoint2D(x = 0.0, y = 0.0)
        } else {
            val sqrt = sqrt(
                dividend / (divisor1 + divisor2)
            )

            if (sqrt.isNaN()) {
                PrecisePoint2D(x = 0.0, y = 0.0)
            } else {
                PrecisePoint2D(
                    rx * primedCoordinates.y / ry,
                    -ry * primedCoordinates.x / rx
                ) * sqrt
            }
        } * multiplier

        // (eq. 5.3)
        center = primedCenter.transform(
            AffineTransformation.Matrix(
                floatArrayOf(cosPhi.toFloat(), sinPhi.toFloat(), 0f),
                floatArrayOf(-sinPhi.toFloat(), cosPhi.toFloat(), 0f),
                floatArrayOf(0f, 0f, 0f),
            )
        ) + PrecisePoint2D(
            x = (current.x + x) / 2,
            y = (current.y + y) / 2,
        )
    }

    private fun computeThetasAndDelta() {
        val anglePoint = PrecisePoint2D(
            x = (primedCoordinates.x - primedCenter.x) / rx,
            y = (primedCoordinates.y - primedCenter.y) / ry,
        )

        // (eq. 5.5)
        // For eq. 5.4 see angleTo function
        val thetaAngle = PrecisePoint2D(1.0, 0.0) angleTo anglePoint
        // (eq. 5.6)
        var deltaTheta = anglePoint.angleTo(
            PrecisePoint2D(
                x = (-primedCoordinates.x - primedCenter.x) / rx,
                y = (-primedCoordinates.y - primedCenter.y) / ry,
            ),
        )

        deltaTheta %= (2 * PI)
        if (!sweepFlag && deltaTheta > 0) {
            deltaTheta -= 2 * PI
        } else if (sweepFlag && deltaTheta < 0) {
            deltaTheta += 2 * PI
        }
        theta = thetaAngle * ANGLE_180 / PI
        theta2 = (thetaAngle + deltaTheta) * ANGLE_180 / PI
        delta = deltaTheta * ANGLE_180 / PI
    }

    private fun computeArcRotation(): DoubleArray {
        // arc could be rotated. the min and max values then don't lie on multiples of
        // 90 degrees but are shifted by the rotation angle so we first calculate our
        // 0/90 degree angle
        var theta01 = atan(-sinPhi / cosPhi * ry / rx) * ANGLE_180 / PI
        var theta02 = atan(cosPhi / sinPhi * ry / rx) * ANGLE_180 / PI
        if (theta < 0 || theta2 < 0) {
            theta += ANGLE_360
            theta2 += ANGLE_360
        }
        if (theta2 < theta) {
            val temp = theta
            theta = theta2
            theta2 = temp
        }
        while (theta01 - ANGLE_90 > theta01) theta01 -= ANGLE_90
        while (theta01 < theta) theta01 += ANGLE_90
        while (theta02 - ANGLE_90 > theta02) theta02 -= ANGLE_90
        while (theta02 < theta) theta02 += ANGLE_90
        return doubleArrayOf(
            theta01,
            theta02,
            (theta01 + ANGLE_90),
            (theta02 + ANGLE_90),
            (theta01 + ANGLE_180),
            (theta02 + ANGLE_180),
            (theta01 + ANGLE_270),
            (theta02 + ANGLE_270),
        )
    }

    private fun pointAt(t: Double): PrecisePoint2D {
        if (current.x == x && current.y == y) return PrecisePoint2D(x = x, y = y)
        val tInAngle = (theta + t * delta) / ANGLE_180 * PI
        val sinTheta = sin(tInAngle)
        val cosTheta = cos(tInAngle)
        return PrecisePoint2D(
            x = cosPhi * rx * cosTheta - sinPhi * ry * sinTheta + center.x,
            y = sinPhi * ry * cosTheta + cosPhi * rx * sinTheta + center.y
        )
    }
}
