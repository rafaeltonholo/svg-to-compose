package dev.tonholo.s2c.domain

import dev.tonholo.s2c.extensions.toLengthFloat

fun SvgNode.Path.asNode(
    width: Float,
    height: Float,
    minified: Boolean = false,
): ImageVectorNode.Path = ImageVectorNode.Path(
    params = ImageVectorNode.Path.Params(
        fill = fill.orEmpty(),
        fillAlpha = fillOpacity,
        pathFillType = PathFillType(fillRule),
        stroke = stroke,
        strokeAlpha = strokeOpacity?.toLengthFloat(width, height),
        strokeLineCap = StrokeCap(strokeLineCap),
        strokeLineJoin = StrokeJoin(strokeLineJoin),
        strokeMiterLimit = strokeMiterLimit,
        strokeLineWidth = strokeWidth?.toLengthFloat(width, height),
    ),
    wrapper = d.asNodeWrapper(minified),
    minified = minified,
)
