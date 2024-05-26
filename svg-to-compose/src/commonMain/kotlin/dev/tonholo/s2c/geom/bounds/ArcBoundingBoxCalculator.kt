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

/**
 * Calculates the bounding box of an elliptical arc.
 *
 * The algorithm is based on the SVG specification:
 * https://www.w3.org/TR/SVG/implnote.html#ArcConversionEndpointToCenter
 *
 * @param boundingBox The current bounding box.
 * @param currentX The current x-coordinate.
 * @param currentY The current y-coordinate.
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
    private val currentX: Double,
    private val currentY: Double,
    private val x: Double,
    private val y: Double,
    private var rx: Double,
    private var ry: Double,
    phi: Double,
    private val largeArcFlag: Boolean,
    private val sweepFlag: Boolean,
) {
    // pre-calculate cos of phi to avoid recalculating it every time
    private val cosPhi = cos(phi / 180 * PI)
    private val sinPhi = sin(phi / 180 * PI)

    // (eq. 5.1)
    private val primedCoordinates = PrecisePoint2D(
        x = (currentX - x) / 2,
        y = (currentY - y) / 2,
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
        if (rx == 0.0 || ry == 0.0 || (currentX == x && currentY == y)) {
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
                while (theta < currentAngle) currentAngle -= 360
                pointAt(((angle - theta) % 360) / (delta))
            }
            .plus(
                listOf(
                    PrecisePoint2D(currentX, currentY),
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
        val ratio = (primedCoordinates.x.pow(2) / rx.pow(2)) +
            (primedCoordinates.y.pow(2) / ry.pow(2))

        // (eq. 6.3)
        if (ratio > 1) {
            rx *= sqrt(ratio)
            ry *= sqrt(ratio)
        }
    }

    private fun computeCenterCoordinates() {
        // (eq. 5.2)
        val rxQuad = rx.pow(2)
        val ryQuad = ry.pow(2)

        val divisor1 = rxQuad * primedCoordinates.y.pow(2)
        val divisor2 = ryQuad * primedCoordinates.x.pow(2)
        val dividend = (rxQuad * ryQuad - divisor1 - divisor2)
        val multiplier = if (largeArcFlag == sweepFlag) -1 else 1

        primedCenter = if (abs(dividend) < 1e-15) {
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
            x = (currentX + x) / 2,
            y = (currentY + y) / 2,
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
        theta = thetaAngle * 180 / PI
        theta2 = (thetaAngle + deltaTheta) * 180 / PI
        delta = deltaTheta * 180 / PI
    }

    private fun computeArcRotation(): DoubleArray {
        // arc could be rotated. the min and max values then don't lie on multiples of
        // 90 degrees but are shifted by the rotation angle so we first calculate our
        // 0/90 degree angle
        var theta01 = atan(-sinPhi / cosPhi * ry / rx) * 180 / PI
        var theta02 = atan(cosPhi / sinPhi * ry / rx) * 180 / PI
        if (theta < 0 || theta2 < 0) {
            theta += 360
            theta2 += 360
        }
        if (theta2 < theta) {
            val temp = theta
            theta = theta2
            theta2 = temp
        }
        while (theta01 - 90 > theta01) theta01 -= 90
        while (theta01 < theta) theta01 += 90
        while (theta02 - 90 > theta02) theta02 -= 90
        while (theta02 < theta) theta02 += 90
        return doubleArrayOf(
            theta01,
            theta02,
            (theta01 + 90),
            (theta02 + 90),
            (theta01 + 180),
            (theta02 + 180),
            (theta01 + 270),
            (theta02 + 270),
        )
    }

    private fun pointAt(t: Double): PrecisePoint2D {
        if (currentX == x && currentY == y) return PrecisePoint2D(x = x, y = y)
        val tInAngle = (theta + t * delta) / 180 * PI
        val sinTheta = sin(tInAngle)
        val cosTheta = cos(tInAngle)
        return PrecisePoint2D(
            x = cosPhi * rx * cosTheta - sinPhi * ry * sinTheta + center.x,
            y = sinPhi * ry * cosTheta + cosPhi * rx * sinTheta + center.y
        )
    }
}
