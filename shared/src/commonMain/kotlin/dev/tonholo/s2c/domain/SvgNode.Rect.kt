package dev.tonholo.s2c.domain

import dev.tonholo.s2c.extensions.toLengthFloat
import dev.tonholo.s2c.extensions.toLengthInt
import dev.tonholo.s2c.logger.warn

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
            fillOpacity?.let { append("fill-opacity=\"$it\" ") }
            style?.let { append("style=\"$it\" ") }
            stroke?.let { append("stroke=\"$it\" ") }
            strokeWidth?.let { append("stroke-width=\"$it\" ") }
            strokeLineJoin?.let { append("stroke-line-join=\"$it\" ") }
            strokeLineCap?.let { append("stroke-line-cap=\"$it\" ") }
            fillRule?.let { append("fill-rule=\"$it\" ") }
            strokeOpacity?.let { append("stroke-opacity=\"$it\" ") }
            strokeMiterLimit?.let { append("stroke-miter-limit=\"$it\" ") }
            strokeDashArray?.let { append("stroke-dasharray=\"$it\" ") }
            append("/>")
        },
        nodes = when {
            !strokeDashArray.isNullOrEmpty() && rx == null && ry == null -> {
                warn(
                    "Parsing a `stroke-dasharray` attribute is experimental and " +
                        "might differ a little from the original."
                )
                StrokeDashArray(strokeDashArray).createDashedPathForRect(
                    x = x,
                    y = y,
                    width = width,
                    height = height,
                    strokeWidth = strokeWidth?.toLengthInt(width, height) ?: 1,
                    isMinified = isMinified,
                )
            }

            xCornerSize == 0 && yCornerSize == 0 -> {
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
            }

            else -> {
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
            }
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
        strokeLineWidth = strokeWidth?.toLengthFloat(width, height) ?: stroke?.let { 1f },
    ),
    wrapper = createPath(minified),
    minified = minified,
)
