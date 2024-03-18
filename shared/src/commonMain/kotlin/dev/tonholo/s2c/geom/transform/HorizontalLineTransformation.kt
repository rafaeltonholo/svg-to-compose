package dev.tonholo.s2c.geom.transform

import dev.tonholo.s2c.domain.PathCommand
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.geom.AffineTransformation

internal data object HorizontalLineTransformation : PathTransformation<PathNodes.HorizontalLineTo>() {
    override fun PathNodes.HorizontalLineTo.applyTransformation(
        cursor: FloatArray,
        start: FloatArray,
        transformation: AffineTransformation,
    ): PathNodes {
        return if (isRelative) {
            new(args = listOf(x))
        } else {
            // convert to lineTo to handle two-dimensional transforms
            new(
                args = listOf(
                    x,
                    cursor[1],
                ),
                command = PathCommand.LineTo,
            )
        }
    }
}
