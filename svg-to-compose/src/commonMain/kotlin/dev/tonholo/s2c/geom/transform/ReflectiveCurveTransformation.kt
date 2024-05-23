package dev.tonholo.s2c.geom.transform

import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.geom.AffineTransformation

internal data object ReflectiveCurveTransformation : PathTransformation<PathNodes.ReflectiveCurveTo>() {
    override fun PathNodes.ReflectiveCurveTo.applyTransformation(
        cursor: FloatArray,
        start: FloatArray,
        transformation: AffineTransformation,
    ): PathNodes {
        val args = if (isRelative) {
            cursor[0] += x2
            cursor[1] += y2
            start[0] = cursor[0]
            start[1] = cursor[1]
            transformRelativePoint(transformation.matrix, x1, y1) +
                transformRelativePoint(transformation.matrix, x2, y2)
        } else {
            cursor[0] = x2
            cursor[1] = y2
            start[0] = cursor[0]
            start[1] = cursor[1]
            transformAbsolutePoint(transformation.matrix, x1, y1) +
                transformAbsolutePoint(transformation.matrix, x2, y2)
        }
        return new(args.toList())
    }
}
