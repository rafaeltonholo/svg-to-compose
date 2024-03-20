package dev.tonholo.s2c.geom

data class Point2D(val x: Float, val y: Float) {
    operator fun plus(other: Point2D): FloatArray = floatArrayOf(x, y, other.x, other.y)
}

operator fun FloatArray.plus(other: Point2D): FloatArray = this + floatArrayOf(other.x, other.y)
