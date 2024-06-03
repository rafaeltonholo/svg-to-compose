package dev.tonholo.s2c.geom.bounds

import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.geom.PrecisePoint2D
import dev.tonholo.s2c.geom.bounds.BoundingBox.NoBoundingBox
import kotlin.math.max
import kotlin.math.min

/**
 * Represents a bounding box, which defines a rectangular area.
 *
 * @property minX The minimum x-coordinate of the bounding box.
 * @property minY The minimum y-coordinate of the bounding box.
 * @property maxX The maximum x-coordinate of the bounding box.
 * @property maxY The maximum y-coordinate of the bounding box.
 * @property x The x-coordinate of the center of the bounding box.
 * @property y The y-coordinate of the center of the bounding box.
 * @property width The width of the bounding box.
 * @property height The height of the bounding box.
 */
sealed class BoundingBox private constructor(
    open val minX: Double,
    open val minY: Double,
    open val maxX: Double,
    open val maxY: Double,
) {
    val x get() = minX
    val y get() = minY
    val width: Double
        get() = maxX - minX
    val height: Double
        get() = maxY - minY

    /**
     * This bounding box is used to represent an empty or undefined bounding box.
     */
    data object NoBoundingBox : BoundingBox(
        minX = Double.NaN,
        minY = Double.NaN,
        maxX = Double.NaN,
        maxY = Double.NaN,
    )

    private class BoundingBoxImpl(
        override val minX: Double,
        override val minY: Double,
        override val maxX: Double,
        override val maxY: Double,
    ) : BoundingBox(minX, minY, maxX, maxY)

    override fun toString(): String {
        return "BoundingBox(x=$x, y=$y, width=$width, height=$height, minX=$minX, minY=$minY, maxX=$maxX, maxY=$maxY)"
    }

    fun copy(
        minX: Double = this.minX,
        minY: Double = this.minY,
        maxX: Double = this.maxX,
        maxY: Double = this.maxY,
    ): BoundingBox = BoundingBoxImpl(
        minX = minX,
        minY = minY,
        maxX = maxX,
        maxY = maxY,
    )

    companion object {
        /**
         * Creates a [BoundingBox] from the given coordinates.
         *
         * If any of the coordinates are [Double.NaN], an empty [NoBoundingBox]
         * instance is returned.
         * Otherwise, a new [BoundingBoxImpl] instance is created with the given
         * coordinates.
         *
         * @param minX The minimum x-coordinate of the bounding box.
         * @param minY The minimum y-coordinate of the bounding box.
         * @param maxX The maximum x-coordinate of the bounding box.
         * @param maxY The maximum y-coordinate of the bounding box.
         *
         * @return A [BoundingBox] instance if the coordinates are valid, or
         * [NoBoundingBox] if any of the coordinates are [Double.NaN].
         */
        operator fun invoke(
            minX: Double,
            minY: Double,
            maxX: Double,
            maxY: Double,
        ): BoundingBox = if (minX.isNaN() || minY.isNaN() || maxX.isNaN() || maxY.isNaN()) {
            NoBoundingBox
        } else {
            BoundingBoxImpl(
                minX = minX,
                minY = minY,
                maxX = maxX,
                maxY = maxY,
            )
        }
    }
}

/**
 * Calculates the bounding box of the given list of path nodes.
 * A bounding box is a rectangle that encloses all the points in a path.
 *
 * **Preconditions:**
 *
 * - All path nodes must be absolute.
 * - There must be no reflective curve or quad nodes.
 *
 * @return The bounding box of the path or [NoBoundingBox] if the list is empty.
 * @throws IllegalArgumentException if the preconditions are not met.
 */
fun List<PathNodes>.boundingBox(): BoundingBox {
    if (isEmpty()) return NoBoundingBox
    check(all { it.isRelative.not() }) {
        "Relative path nodes are not supported. Call List<PathNodes>.toAbsolute() before calling boundingBox()."
    }
    check(none { it is PathNodes.ReflectiveCurveTo || it is PathNodes.ReflectiveQuadTo }) {
        "ReflectiveCurveTo and ReflectiveQuadTo are not supported. " +
            "Call List<PathNodes>.removeShorthandNodes() before calling boundingBox()."
    }

    val current = DoubleArray(size = 2)

    return fold(
        initial = BoundingBox(
            minX = Double.POSITIVE_INFINITY,
            minY = Double.POSITIVE_INFINITY,
            maxX = Double.NEGATIVE_INFINITY,
            maxY = Double.NEGATIVE_INFINITY,
        ),
    ) { box, node ->
        node.calculateBoundingBox(box, current[0], current[1]).also {
            when (node) {
                is PathNodes.CurveTo -> {
                    current[0] = node.x3.toDouble()
                    current[1] = node.y3.toDouble()
                }

                is PathNodes.HorizontalLineTo -> {
                    current[0] = node.x.toDouble()
                }

                is PathNodes.LineTo, is PathNodes.MoveTo, is PathNodes.ArcTo -> {
                    current[0] = node.x.toDouble()
                    current[1] = node.y.toDouble()
                }

                is PathNodes.QuadTo -> {
                    current[0] = node.x2.toDouble()
                    current[1] = node.y2.toDouble()
                }

                is PathNodes.VerticalLineTo -> {
                    current[1] = node.y.toDouble()
                }

                // Ignore ReflectiveCurveTo and ReflectiveQuadTo since they were removed.
                else -> Unit
            }
        }
    }
}

private fun PathNodes.calculateBoundingBox(
    boundingBox: BoundingBox,
    currentX: Double,
    currentY: Double,
): BoundingBox {
    return when (this) {
        is PathNodes.ArcTo -> ArcBoundingBoxCalculator(
            boundingBox = boundingBox,
            current = PrecisePoint2D(x = currentX, y = currentY),
            x = x.toDouble(),
            y = y.toDouble(),
            rx = a.toDouble(),
            ry = b.toDouble(),
            phi = theta.toDouble(),
            largeArcFlag = isMoreThanHalf,
            sweepFlag = isPositiveArc,
        ).calculate()

        is PathNodes.CurveTo -> {
            BezierBoundingBoxCalculator(
                initialBoundingBox = boundingBox,
                currentX = currentX,
                currentY = currentY,
                controlPoint1x = x1.toDouble(),
                controlPoint1y = y1.toDouble(),
                controlPoint2x = x2.toDouble(),
                controlPoint2y = y2.toDouble(),
                endX = x3.toDouble(),
                endY = y3.toDouble(),
            ).calculate()
        }

        is PathNodes.HorizontalLineTo -> boundingBox.copy(
            minX = min(boundingBox.minX, x.toDouble()),
            maxX = max(boundingBox.maxX, x.toDouble()),
        )

        is PathNodes.LineTo, is PathNodes.MoveTo -> boundingBox.copy(
            minX = min(boundingBox.minX, x.toDouble()),
            minY = min(boundingBox.minY, y.toDouble()),
            maxX = max(boundingBox.maxX, x.toDouble()),
            maxY = max(boundingBox.maxY, y.toDouble()),
        )

        is PathNodes.QuadTo -> {
            // CP1 = QP0 + 2/3 * (QP1 - QP0)
            val controlPoint1 = doubleArrayOf(
                currentX + 2 / 3 * (x1 - currentX),
                currentY + 2 / 3 * (y1 - currentY),
            )
            // CP2 = CP1 + 1/3 * (QP2 - QP0)
            val controlPoint2 = doubleArrayOf(
                currentX + 1 / 3 * (x2 - currentX),
                currentY + 1 / 3 * (y2 - currentY),
            )

            BezierBoundingBoxCalculator(
                initialBoundingBox = boundingBox,
                currentX = currentX,
                currentY = currentY,
                controlPoint1x = controlPoint1[0],
                controlPoint1y = controlPoint1[1],
                controlPoint2x = controlPoint2[0],
                controlPoint2y = controlPoint2[1],
                endX = x2.toDouble(),
                endY = y2.toDouble(),
            ).calculate()
        }

        is PathNodes.VerticalLineTo -> boundingBox.copy(
            minY = min(boundingBox.minY, y.toDouble()),
            maxY = max(boundingBox.maxY, y.toDouble()),
        )

        // Ignore ReflectiveCurveTo and ReflectiveQuadTo since they were removed.
        else -> error("Unexpected node type = $command")
    }
}
