package dev.tonholo.s2c.geom.transform

import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.geom.AffineTransformation
import dev.tonholo.s2c.geom.plus

internal data object CurveTransformation : PathTransformation<PathNodes.CurveTo>() {
    override fun PathNodes.CurveTo.applyTransformation(
        cursor: FloatArray,
        start: FloatArray,
        transformation: AffineTransformation,
    ): PathNodes {
        val args = if (isRelative) {
            cursor[0] += x3
            cursor[1] += y3
            start[0] = cursor[0]
            start[1] = cursor[1]
            transformRelativePoint(transformation.matrix, x1, y1) +
                transformRelativePoint(transformation.matrix, x2, y2) +
                transformRelativePoint(transformation.matrix, x3, y3)
        } else {
            cursor[0] = x3
            cursor[1] = y3
            start[0] = cursor[0]
            start[1] = cursor[1]
            transformAbsolutePoint(transformation.matrix, x1, y1) +
                transformAbsolutePoint(transformation.matrix, x2, y2) +
                transformAbsolutePoint(transformation.matrix, x3, y3)
        }
        return new(args.toList())
    }
}
