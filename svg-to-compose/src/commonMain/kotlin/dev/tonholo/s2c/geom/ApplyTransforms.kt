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

    @Suppress("SpreadOperator")
    class Matrix(
        vararg matrix: FloatArray,
    ) : AffineTransformation(*matrix)

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
        floatArrayOf(cos(angle), -sin(angle), centerX),
        floatArrayOf(sin(angle), cos(angle), centerX),
        floatArrayOf(0f, 0f, 1f),
    )

    data class Scale(val sx: Float, val sy: Float) : AffineTransformation(
        floatArrayOf(sx, 0f, 0f),
        floatArrayOf(0f, sy, 0f),
        floatArrayOf(0f, 0f, 1f),
    )

    data class SkewX(val angle: Float) : AffineTransformation(
        floatArrayOf(1f, tan(angle), 0f),
        floatArrayOf(0f, 1f, 0f),
        floatArrayOf(0f, 0f, 1f),
    )

    data class SkewY(val angle: Float) : AffineTransformation(
        floatArrayOf(1f, 0f, 0f),
        floatArrayOf(tan(angle), 1f, 0f),
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
        val newNode = when (node) {
            is PathNodes.MoveTo -> node.applyTransformation(cursor, start, transformation)
            is PathNodes.HorizontalLineTo -> node.applyTransformation(cursor, start, transformation)
            is PathNodes.VerticalLineTo -> node.applyTransformation(cursor, start, transformation)
            is PathNodes.LineTo -> node.applyTransformation(cursor, start, transformation)
            is PathNodes.CurveTo -> node.applyTransformation(cursor, start, transformation)
            is PathNodes.ReflectiveCurveTo -> node.applyTransformation(cursor, start, transformation)
            is PathNodes.QuadTo -> node.applyTransformation(cursor, start, transformation)
            is PathNodes.ReflectiveQuadTo -> node.applyTransformation(cursor, start, transformation)
            is PathNodes.ArcTo -> node.applyTransformation(cursor, start, transformation)
        }

        if (newNode.shouldClose) {
            cursor[0] = start[0]
            cursor[1] = start[1]
        }

        yield(newNode)
    }
}
