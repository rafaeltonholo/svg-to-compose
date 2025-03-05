package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathCommand
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.StrokeDashArray
import dev.tonholo.s2c.domain.builder.pathNode
import dev.tonholo.s2c.domain.createDashedPathForRect
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.logger.warn

class SvgRectNode(
    parent: XmlParentNode,
    attributes: MutableMap<String, String>,
) : SvgGraphicNode<SvgRectNode>(parent, attributes, TAG_NAME), SvgNode {
    override val constructor = ::SvgRectNode
    val width: Int by attribute<SvgLength, Int> { width ->
        val root = rootParent as SvgRootNode
        val baseDimension = root.viewportWidth
        width.toIntOrNull(baseDimension) ?: error("Invalid width '$width'")
    }
    val height: Int by attribute<SvgLength, Int> { height ->
        val root = rootParent as SvgRootNode
        val baseDimension = root.viewportHeight
        height.toIntOrNull(baseDimension) ?: error("Invalid height '$height'")
    }
    val x: Float? by attribute<SvgLength?, Float?> { x ->
        val root = rootParent as SvgRootNode
        val baseDimension = root.viewportWidth
        x?.toFloatOrNull(baseDimension)
    }
    val y: Float? by attribute<SvgLength?, Float?> { y ->
        val root = rootParent as SvgRootNode
        val baseDimension = root.viewportHeight
        y?.toFloatOrNull(baseDimension)
    }
    val rx: Double? by attribute()
    val ry: Double? by attribute()

    companion object {
        const val TAG_NAME = "rect"
    }
}

fun SvgRectNode.asNode(
    minified: Boolean,
): ImageVectorNode.Path {
    val wrapper = createPath(minified)
    return ImageVectorNode.Path(
        params = ImageVectorNode.Path.Params(
            fill = fillBrush(wrapper.nodes),
            fillAlpha = fillOpacity ?: opacity,
            pathFillType = fillRule,
            stroke = strokeBrush(wrapper.nodes),
            strokeAlpha = strokeOpacity ?: opacity,
            strokeLineCap = strokeLineCap,
            strokeLineJoin = strokeLineJoin,
            strokeMiterLimit = strokeMiterLimit,
            strokeLineWidth = strokeWidth ?: stroke?.let { 1f },
        ),
        wrapper = wrapper,
        minified = minified,
        transformations = transform?.toTransformations(),
    )
}

private fun SvgRectNode.createPath(isMinified: Boolean): ImageVectorNode.NodeWrapper {
    val xCornerSize = rx ?: ry ?: 0.0
    val yCornerSize = ry ?: rx ?: 0.0
    val x = x ?: 0f
    val y = y ?: 0f
    val strokeDashArray = strokeDashArray

    return ImageVectorNode.NodeWrapper(
        normalizedPath = buildNormalizedPath(),
        nodes = when {
            strokeDashArray != null && rx == null && ry == null ->
                createDashedRect(strokeDashArray, x, y, isMinified)

            xCornerSize == 0.0 && yCornerSize == 0.0 ->
                createRegularRect(x, y, isMinified)

            else ->
                createRoundedCornerRect(x, y, xCornerSize, yCornerSize, isMinified)
        },
    )
}

private fun SvgRectNode.createDashedRect(
    strokeDashArray: StrokeDashArray,
    x: Float,
    y: Float,
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
    x: Float,
    y: Float,
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
    x: Float,
    y: Float,
    xCornerSize: Double,
    yCornerSize: Double,
    isMinified: Boolean,
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
