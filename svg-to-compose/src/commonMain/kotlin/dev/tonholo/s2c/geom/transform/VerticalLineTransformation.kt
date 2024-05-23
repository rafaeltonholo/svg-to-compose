package dev.tonholo.s2c.geom.transform

import dev.tonholo.s2c.domain.PathCommand
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.geom.AffineTransformation

internal data object VerticalLineTransformation : PathTransformation<PathNodes.VerticalLineTo>() {
    override fun PathNodes.VerticalLineTo.applyTransformation(
        cursor: FloatArray,
        start: FloatArray,
        transformation: AffineTransformation,
    ): PathNodes = if (isRelative) {
        new(args = listOf(y))
    } else {
        // convert to lineTo to handle two-dimensional transforms
        new(
            args = listOf(
                cursor[0],
                y,
            ),
            command = PathCommand.LineTo,
        )
    }
}
