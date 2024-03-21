package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathCommand
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.StrokeDashArray
import dev.tonholo.s2c.domain.builder.pathNode
import dev.tonholo.s2c.domain.compose.toBrush
import dev.tonholo.s2c.domain.createDashedPathForRect
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.logger.warn

class SvgRectNode(
    parent: XmlParentNode,
    attributes: MutableMap<String, String>,
) : SvgGraphicNode(parent, attributes, TAG_NAME), SvgNode {
    val width: Int by attribute<SvgLength, Int> { width ->
        val root = rootParent as SvgElementNode
        val baseDimension = root.viewportWidth
        width.toIntOrNull(baseDimension) ?: error("Invalid width '$width'")
    }
    val height: Int by attribute<SvgLength, Int> { height ->
        val root = rootParent as SvgElementNode
        val baseDimension = root.viewportHeight
        height.toIntOrNull(baseDimension) ?: error("Invalid height '$height'")
    }
    val x: Int? by attribute<SvgLength?, Int?> { x ->
        val root = rootParent as SvgElementNode
        val baseDimension = root.viewportWidth
        x?.toIntOrNull(baseDimension)
    }
    val y: Int? by attribute<SvgLength?, Int?> { y ->
        val root = rootParent as SvgElementNode
        val baseDimension = root.viewportHeight
        y?.toIntOrNull(baseDimension)
    }
    val rx: Int? by attribute()
    val ry: Int? by attribute()

    companion object {
        const val TAG_NAME = "rect"
    }
}

fun SvgRectNode.asNode(
    minified: Boolean = false,
): ImageVectorNode.Path = ImageVectorNode.Path(
    params = ImageVectorNode.Path.Params(
        fill = fill.orDefault().value.toBrush(), // Rect has a filling by default.
        fillAlpha = fillOpacity,
        pathFillType = fillRule,
        stroke = stroke?.value?.toBrush(),
        strokeAlpha = strokeOpacity,
        strokeLineCap = strokeLineCap,
        strokeLineJoin = strokeLineJoin,
        strokeMiterLimit = strokeMiterLimit,
        strokeLineWidth = strokeWidth ?: stroke?.let { 1f },
    ),
    wrapper = createPath(minified),
    minified = minified,
)

private fun SvgRectNode.createPath(isMinified: Boolean): ImageVectorNode.NodeWrapper {
    val xCornerSize = rx ?: ry ?: 0
    val yCornerSize = ry ?: rx ?: 0
    val x = x ?: 0
    val y = y ?: 0
    val strokeDashArray = strokeDashArray

    return ImageVectorNode.NodeWrapper(
        normalizedPath = buildNormalizedPath(),
        nodes = when {
            strokeDashArray != null && rx == null && ry == null ->
                createDashedRect(strokeDashArray, x, y, isMinified)

            xCornerSize == 0 && yCornerSize == 0 ->
                createRegularRect(x, y, isMinified)

            else ->
                createRoundedCornerRect(x, y, yCornerSize, isMinified, xCornerSize)
        },
    )
}

private fun SvgRectNode.createDashedRect(
    strokeDashArray: StrokeDashArray,
    x: Int,
    y: Int,
    isMinified: Boolean,
): List<PathNodes> {
    warn(
        "Parsing a `stroke-dasharray` attribute is experimental and " +
            "might differ a little from the original."
    )
    return strokeDashArray.createDashedPathForRect(
        x = x,
        y = y,
        width = width,
        height = height,
        strokeWidth = strokeWidth?.toInt() ?: 1,
        isMinified = isMinified,
    )
}

private fun SvgRectNode.createRegularRect(
    x: Int,
    y: Int,
    isMinified: Boolean,
) = listOf(
    pathNode(command = PathCommand.MoveTo) {
        args(x, y)
        minified = isMinified
    },
    pathNode(command = PathCommand.HorizontalLineTo) {
        args(width)
        isRelative = true
        minified = isMinified
    },
    pathNode(command = PathCommand.VerticalLineTo) {
        args(height)
        isRelative = true
        minified = isMinified
    },
    pathNode(command = PathCommand.HorizontalLineTo) {
        args(-width)
        isRelative = true
        close = true
        minified = isMinified
    },
)

private fun SvgRectNode.createRoundedCornerRect(
    x: Int,
    y: Int,
    yCornerSize: Int,
    isMinified: Boolean,
    xCornerSize: Int,
) = listOf(
    pathNode(command = PathCommand.MoveTo) {
        args(x, y + yCornerSize)
        minified = isMinified
    },
    pathNode(command = PathCommand.ArcTo) {
        args(xCornerSize, yCornerSize, 0, false, true, xCornerSize, -yCornerSize)
        isRelative = true
        minified = isMinified
    },
    pathNode(command = PathCommand.HorizontalLineTo) {
        args(width - xCornerSize * 2)
        isRelative = true
        minified = isMinified
    },
    pathNode(command = PathCommand.ArcTo) {
        args(xCornerSize, yCornerSize, 0, false, true, xCornerSize, yCornerSize)
        isRelative = true
        minified = isMinified
    },
    pathNode(command = PathCommand.VerticalLineTo) {
        args(height - yCornerSize * 2)
        isRelative = true
        minified = isMinified
    },
    pathNode(command = PathCommand.ArcTo) {
        args(xCornerSize, yCornerSize, 0, false, true, -xCornerSize, yCornerSize)
        isRelative = true
        minified = isMinified
    },
    pathNode(command = PathCommand.HorizontalLineTo) {
        args(-(width - xCornerSize * 2))
        isRelative = true
        minified = isMinified
    },
    pathNode(command = PathCommand.ArcTo) {
        args(
            xCornerSize,
            yCornerSize,
            0,
            false,
            true,
            -xCornerSize,
            -yCornerSize,
        )
        isRelative = true
        minified = isMinified
        close = true
    },
)

private fun SvgRectNode.buildNormalizedPath(): String = buildString {
    append("<rect ")
    append("width=\"$width\" ")
    append("height=\"$height\" ")
    rx?.let { append("rx=\"$it\" ") }
    ry?.let { append("ry=\"$it\" ") }
    x?.let { append("x=\"$it\" ") }
    y?.let { append("y=\"$it\" ") }
    append(graphicNodeParams())
    append("/>")
}
