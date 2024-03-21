package dev.tonholo.s2c.domain.avg.gradient

import dev.tonholo.s2c.domain.avg.AvgColor

sealed interface AvgGradient {
    /**
     * Start color of the gradient.
     */
    val startColor: AvgColor?

    /**
     * Optional center color.
     */
    val centerColor: AvgColor?

    /**
     * End color of the gradient.
     */
    val endColor: AvgColor?

    /**
     * Type of gradient. The default type is linear.
     */
    val type: AvgGradientType?

    companion object {
        const val TAG_NAME = "gradient"
    }
}

sealed interface AvgGradientWithCenterCoordinates : AvgGradient {
    /**
     * X coordinate of the center of the gradient within the path.
     */
    val centerX: Float?

    /**
     * Y coordinate of the center of the gradient within the path.
     */
    val centerY: Float?
}

internal interface AvgSweepGradient : AvgGradientWithCenterCoordinates
internal interface AvgRadianGradient : AvgGradientWithCenterCoordinates {
    /**
     * Radius of the gradient, used only with radial gradient.
     */
    val gradientRadius: Float?
}

internal interface AvgLinearGradient : AvgGradient {
    /**
     * X coordinate of the start point origin of the gradient.
     * Defined in the same coordinates as the path itself
     */
    val startX: Float?

    /**
     * Y coordinate of the start point of the gradient within the shape.
     * Defined in the same coordinates as the path itself
     */
    val startY: Float?

    /**
     * X coordinate of the end point origin of the gradient.
     * Defined in the same coordinates as the path itself
     */
    val endX: Float?

    /**
     * Y coordinate of the end point of the gradient within the shape.
     * Defined in the same coordinates as the path itself
     */
    val endY: Float?

    /**
     * Defines the tile mode of the gradient. SweepGradient don't support tiling.
     */
    val tileMode: AvgGradientTileMode?
}
