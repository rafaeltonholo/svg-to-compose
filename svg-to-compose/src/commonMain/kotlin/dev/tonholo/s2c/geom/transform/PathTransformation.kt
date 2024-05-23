package dev.tonholo.s2c.geom.transform

import dev.tonholo.s2c.domain.PathCommand
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.builder.pathNode
import dev.tonholo.s2c.geom.AffineTransformation
import dev.tonholo.s2c.geom.Point2D

internal sealed class PathTransformation<T : PathNodes> {
    abstract fun T.applyTransformation(
        cursor: FloatArray,
        start: FloatArray = floatArrayOf(),
        transformation: AffineTransformation = AffineTransformation.Identity,
    ): PathNodes

    protected fun transformAbsolutePoint(matrix: Array<out FloatArray>, x: Float, y: Float): Point2D {
        val newX = matrix[0][0] * x + matrix[0][1] * y + matrix[0][2]
        val newY = matrix[1][0] * x + matrix[1][1] * y + matrix[1][2]
        return Point2D(newX, newY)
    }

    protected fun transformRelativePoint(matrix: Array<out FloatArray>, x: Float, y: Float): Point2D {
        val newX = matrix[0][0] * x + matrix[0][1] * y
        val newY = matrix[1][0] * x + matrix[1][1] * y
        return Point2D(newX, newY)
    }

    fun PathNodes.new(
        args: List<Any>,
        command: PathCommand? = null,
    ): PathNodes = pathNode(command ?: this.command) {
        args(args)
        isRelative = this@new.isRelative
        minified = this@new.minified
        close = this@new.shouldClose
    }
}
