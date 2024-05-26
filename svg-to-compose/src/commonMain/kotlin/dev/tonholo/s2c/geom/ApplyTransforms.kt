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
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

private const val MATRIX_SIZE = 3

sealed class AffineTransformation(
    vararg matrix: FloatArray,
) {
    val matrix: Array<out FloatArray>

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
            original: Array<out FloatArray>,
            other: Array<out FloatArray>,
        ): Float {
            return (original[row][0] * other[0][column]) +
                (original[row][1] * other[1][column]) +
                (original[row][2] * other[2][column])
        }
        return Matrix(
            floatArrayOf(
                calculateMatrixElement(row = 0, column = 0, matrix, other.matrix),
                calculateMatrixElement(row = 0, column = 1, matrix, other.matrix),
                calculateMatrixElement(row = 0, column = 2, matrix, other.matrix),
            ),
            floatArrayOf(
                calculateMatrixElement(row = 1, column = 0, matrix, other.matrix),
                calculateMatrixElement(row = 1, column = 1, matrix, other.matrix),
                calculateMatrixElement(row = 1, column = 2, matrix, other.matrix),
            ),
            floatArrayOf(
                calculateMatrixElement(row = 2, column = 0, matrix, other.matrix),
                calculateMatrixElement(row = 2, column = 1, matrix, other.matrix),
                calculateMatrixElement(row = 2, column = 2, matrix, other.matrix),
            ),
        )
    }

    companion object {
        private inline val Float.rad: Float get() = this * PI.toFloat() / 180f
    }

    class Matrix(
        vararg matrix: FloatArray,
    ) : AffineTransformation(matrix = matrix) {
        operator fun get(index: Int): FloatArray = matrix[index]
    }

    data object Identity : AffineTransformation(
        floatArrayOf(1f, 0f, 0f),
        floatArrayOf(0f, 1f, 0f),
        floatArrayOf(0f, 0f, 1f),
    )

    data class Translate(val tx: Float, val ty: Float) : AffineTransformation(
        floatArrayOf(1f, 0f, tx),
        floatArrayOf(0f, 1f, ty),
        floatArrayOf(0f, 0f, 1f),
    )

    data class Rotate(
        val angle: Float,
        val centerX: Float = 0f,
        val centerY: Float = 0f,
    ) : AffineTransformation(
        matrix = run {
            val cos = cos(angle.rad)
            val sin = sin(angle.rad)
            arrayOf(
                floatArrayOf(cos, -sin, (1 - cos) * centerX + sin * centerY),
                floatArrayOf(sin, cos, (1 - cos) * centerY - sin * centerX),
                floatArrayOf(0f, 0f, 1f),
            )
        },
    )

    data class Scale(val sx: Float, val sy: Float = sx) : AffineTransformation(
        floatArrayOf(sx, 0f, 0f),
        floatArrayOf(0f, sy, 0f),
        floatArrayOf(0f, 0f, 1f),
    )

    data class SkewX(val angle: Float) : AffineTransformation(
        floatArrayOf(1f, tan(angle.rad), 0f),
        floatArrayOf(0f, 1f, 0f),
        floatArrayOf(0f, 0f, 1f),
    )

    data class SkewY(val angle: Float) : AffineTransformation(
        floatArrayOf(1f, 0f, 0f),
        floatArrayOf(tan(angle.rad), 1f, 0f),
        floatArrayOf(0f, 0f, 1f),
    )
}

fun List<PathNodes>.applyTransformations(vararg transformations: AffineTransformation): Sequence<PathNodes> {
    val combinedMatrix = transformations.reduce { acc, current -> (acc * current) }
    return applyTransformation(combinedMatrix)
}

fun List<PathNodes>.applyTransformation(
    transformation: AffineTransformation,
): Sequence<PathNodes> = sequence {
    val start = floatArrayOf(0f, 0f)
    val cursor = floatArrayOf(0f, 0f)

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

private fun PathNodes.transform(
    cursor: FloatArray,
    start: FloatArray,
    transformation: AffineTransformation,
) = when (this) {
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
