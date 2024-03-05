package dev.tonholo.s2c.domain

import dev.tonholo.s2c.extensions.toLengthFloat

fun SvgNode.Rect.createPath(isMinified: Boolean): ImageVectorNode.NodeWrapper {
    val xCornerSize = rx ?: ry ?: 0
    val yCornerSize = ry ?: rx ?: 0
    val x = x ?: 0
    val y = y ?: 0

    return ImageVectorNode.NodeWrapper(
        normalizedPath = buildString {
            append("<rect ")
            append("width=\"$width\" ")
            append("height=\"$height\" ")
            rx?.let { append("rx=\"$it\" ") }
            ry?.let { append("ry=\"$it\" ") }
            fill?.let { append("fill=\"$it\" ") }
            opacity?.let { append("opacity=\"$it\" ") }
            fillOpacity?.let { append("fillOpacity=\"$it\" ") }
            style?.let { append("style=\"$it\" ") }
            stroke?.let { append("stroke=\"$it\" ") }
            strokeWidth?.let { append("strokeWidth=\"$it\" ") }
            strokeLineJoin?.let { append("strokeLineJoin=\"$it\" ") }
            strokeLineCap?.let { append("strokeLineCap=\"$it\" ") }
            fillRule?.let { append("fillRule=\"$it\" ") }
            strokeOpacity?.let { append("strokeOpacity=\"$it\" ") }
            strokeMiterLimit?.let { append("strokeMiterLimit=\"$it\" ") }
            append("/>")
        },
        nodes = if (xCornerSize == 0 && yCornerSize == 0) {
            listOf(
                pathNode(command = PathNodes.MoveTo.COMMAND) {
                    args(x, y)
                    minified = isMinified
                },
                pathNode(command = PathNodes.HorizontalLineTo.COMMAND) {
                    args(width)
                    isRelative = true
                    minified = isMinified
                },
                pathNode(command = PathNodes.VerticalLineTo.COMMAND) {
                    args(height)
                    isRelative = true
                    minified = isMinified
                },
                pathNode(command = PathNodes.HorizontalLineTo.COMMAND) {
                    args(x, PathNodes.CLOSE_COMMAND)
                    minified = isMinified
                },
            )
        } else {
            listOf(
                pathNode(command = PathNodes.MoveTo.COMMAND) {
                    args(x, y + yCornerSize)
                    minified = isMinified
                },
                pathNode(command = PathNodes.ArcTo.COMMAND) {
                    args(xCornerSize, yCornerSize, 0, false, true, xCornerSize, -yCornerSize)
                    isRelative = true
                    minified = isMinified
                },
                pathNode(command = PathNodes.HorizontalLineTo.COMMAND) {
                    args(width - xCornerSize * 2)
                    isRelative = true
                    minified = isMinified
                },
                pathNode(command = PathNodes.ArcTo.COMMAND) {
                    args(xCornerSize, yCornerSize, 0, false, true, xCornerSize, yCornerSize)
                    isRelative = true
                    minified = isMinified
                },
                pathNode(command = PathNodes.VerticalLineTo.COMMAND) {
                    args(height - yCornerSize * 2)
                    isRelative = true
                    minified = isMinified
                },
                pathNode(command = PathNodes.ArcTo.COMMAND) {
                    args(xCornerSize, yCornerSize, 0, false, true, -xCornerSize, yCornerSize)
                    isRelative = true
                    minified = isMinified
                },
                pathNode(command = PathNodes.HorizontalLineTo.COMMAND) {
                    args(-(width - xCornerSize * 2))
                    isRelative = true
                    minified = isMinified
                },
                pathNode(command = PathNodes.ArcTo.COMMAND) {
                    args(
                        xCornerSize,
                        yCornerSize,
                        0,
                        0,
                        true,
                        -xCornerSize,
                        -yCornerSize,
                        PathNodes.CLOSE_COMMAND,
                    )
                    isRelative = true
                    minified = isMinified
                },
            )
        },
    )
}

fun SvgNode.Rect.asNode(
    width: Float,
    height: Float,
    minified: Boolean = false,
): ImageVectorNode.Path = ImageVectorNode.Path(
    params = ImageVectorNode.Path.Params(
        fill = fill ?: "#000000", // Rect has a filling by default.
        fillAlpha = fillOpacity,
        pathFillType = PathFillType(fillRule),
        stroke = stroke,
        strokeAlpha = strokeOpacity?.toLengthFloat(width, height),
        strokeLineCap = StrokeCap(strokeLineCap),
        strokeLineJoin = StrokeJoin(strokeLineJoin),
        strokeMiterLimit = strokeMiterLimit,
        strokeLineWidth = strokeWidth?.toLengthFloat(width, height),
    ),
    wrapper = createPath(minified),
    minified = minified,
)
