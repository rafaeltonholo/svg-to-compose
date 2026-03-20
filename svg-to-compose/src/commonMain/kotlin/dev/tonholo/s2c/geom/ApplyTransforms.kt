package dev.tonholo.s2c.geom

import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.geom.transform.ArcTransformation.applyTransformation
import dev.tonholo.s2c.geom.transform.CurveTransformation.applyTransformation
import dev.tonholo.s2c.geom.transform.HorizontalLineTransformation.applyTransformation
import dev.tonholo.s2c.geom.transform.LineTransformation.applyTransformation
import dev.tonholo.s2c.geom.transform.MoveTransformation.applyTransformation
import dev.tonholo.s2c.geom.transform.QuadTransformation.applyTransformation
import dev.tonholo.s2c.geom.transform.ReflectiveCurveTransformation.applyTransformation
import dev.tonholo.s2c.geom.transform.ReflectiveQuadTransformation.applyTransformation
import dev.tonholo.s2c.geom.transform.VerticalLineTransformation.applyTransformation
import dev.tonholo.s2c.serializer.geom.AffineTransformationSerializer
import kotlinx.serialization.Serializable
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

private const val MATRIX_SIZE = 3
private const val DEGREES_IN_HALF_CIRCLE = 180.0

/**
 * Converts degrees to radians.
 */
private val Double.rad: Double get() = this * PI / DEGREES_IN_HALF_CIRCLE

@Serializable(with = AffineTransformationSerializer::class)
sealed class AffineTransformation(vararg matrix: DoubleArray) {
    val matrix: Array<out DoubleArray>

    init {
        check(matrix.size == MATRIX_SIZE && matrix.all { it.size == MATRIX_SIZE }) {
            "The Affine transformation matrix should be represented as a 3x3 matrix."
        }
        this.matrix = matrix
    }

    operator fun times(other: AffineTransformation): AffineTransformation {
        fun calculateMatrixElement(
            row: Int,
            column: Int,
            original: Array<out DoubleArray>,
            multiplier: Array<out DoubleArray>,
        ): Double = (original[row][0] * multiplier[0][column]) +
            (original[row][1] * multiplier[1][column]) +
            (original[row][2] * multiplier[2][column])
        return Matrix(
            doubleArrayOf(
                calculateMatrixElement(row = 0, column = 0, matrix, other.matrix),
                calculateMatrixElement(row = 0, column = 1, matrix, other.matrix),
                calculateMatrixElement(row = 0, column = 2, matrix, other.matrix),
            ),
            doubleArrayOf(
                calculateMatrixElement(row = 1, column = 0, matrix, other.matrix),
                calculateMatrixElement(row = 1, column = 1, matrix, other.matrix),
                calculateMatrixElement(row = 1, column = 2, matrix, other.matrix),
            ),
            doubleArrayOf(
                calculateMatrixElement(row = 2, column = 0, matrix, other.matrix),
                calculateMatrixElement(row = 2, column = 1, matrix, other.matrix),
                calculateMatrixElement(row = 2, column = 2, matrix, other.matrix),
            ),
        )
    }

    class Matrix(vararg matrix: DoubleArray) : AffineTransformation(matrix = matrix) {
        operator fun get(index: Int): DoubleArray = matrix[index]
    }

    data object Identity : AffineTransformation(
        doubleArrayOf(1.0, 0.0, 0.0),
        doubleArrayOf(0.0, 1.0, 0.0),
        doubleArrayOf(0.0, 0.0, 1.0),
    )

    data class Translate(val tx: Double, val ty: Double) :
        AffineTransformation(
            doubleArrayOf(1.0, 0.0, tx),
            doubleArrayOf(0.0, 1.0, ty),
            doubleArrayOf(0.0, 0.0, 1.0),
        )

    data class Rotate(val angle: Double, val centerX: Double = 0.0, val centerY: Double = 0.0) :
        AffineTransformation(
            matrix = run {
                val cos = cos(angle.rad)
                val sin = sin(angle.rad)
                arrayOf(
                    doubleArrayOf(cos, -sin, (1 - cos) * centerX + sin * centerY),
                    doubleArrayOf(sin, cos, (1 - cos) * centerY - sin * centerX),
                    doubleArrayOf(0.0, 0.0, 1.0),
                )
            },
        )

    data class Scale(val sx: Double, val sy: Double = sx) :
        AffineTransformation(
            doubleArrayOf(sx, 0.0, 0.0),
            doubleArrayOf(0.0, sy, 0.0),
            doubleArrayOf(0.0, 0.0, 1.0),
        )

    data class SkewX(val angle: Double) :
        AffineTransformation(
            doubleArrayOf(1.0, tan(angle.rad), 0.0),
            doubleArrayOf(0.0, 1.0, 0.0),
            doubleArrayOf(0.0, 0.0, 1.0),
        )

    data class SkewY(val angle: Double) :
        AffineTransformation(
            doubleArrayOf(1.0, 0.0, 0.0),
            doubleArrayOf(tan(angle.rad), 1.0, 0.0),
            doubleArrayOf(0.0, 0.0, 1.0),
        )
}

fun List<PathNodes>.applyTransformations(vararg transformations: AffineTransformation): Sequence<PathNodes> {
    if (transformations.isEmpty()) return asSequence()
    val combinedMatrix = transformations.reduce { acc, current -> (acc * current) }
    return applyTransformation(combinedMatrix)
}

fun List<PathNodes>.applyTransformation(transformation: AffineTransformation): Sequence<PathNodes> = sequence {
    val start = doubleArrayOf(0.0, 0.0)
    val cursor = doubleArrayOf(0.0, 0.0)

    for (node in this@applyTransformation) {
        val newNode = node.transform(cursor, start, transformation).let { transformed ->
            // As some transformation changes the type of the node,
            // we need to run the transformation again for the new type.
            if (node::class != transformed::class) {
                transformed.transform(cursor, start, transformation)
            } else {
                transformed
            }
        }

        if (newNode.shouldClose) {
            cursor[0] = start[0]
            cursor[1] = start[1]
        }

        yield(newNode)
    }
}

private fun PathNodes.transform(cursor: DoubleArray, start: DoubleArray, transformation: AffineTransformation) =
    when (this) {
        is PathNodes.MoveTo -> applyTransformation(cursor, start, transformation)
        is PathNodes.HorizontalLineTo -> applyTransformation(cursor, start, transformation)
        is PathNodes.VerticalLineTo -> applyTransformation(cursor, start, transformation)
        is PathNodes.LineTo -> applyTransformation(cursor, start, transformation)
        is PathNodes.CurveTo -> applyTransformation(cursor, start, transformation)
        is PathNodes.ReflectiveCurveTo -> applyTransformation(cursor, start, transformation)
        is PathNodes.QuadTo -> applyTransformation(cursor, start, transformation)
        is PathNodes.ReflectiveQuadTo -> applyTransformation(cursor, start, transformation)
        is PathNodes.ArcTo -> applyTransformation(cursor, start, transformation)
    }
