package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathCommand
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.builder.pathNode
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.geom.Point2D

class SvgPolygonNode(
    parent: XmlParentNode,
    attributes: MutableMap<String, String>,
) : SvgGraphicNode<SvgPolygonNode>(parent, attributes, TAG_NAME) {
    override val constructor: SvgChildNodeConstructorFn<SvgPolygonNode> = ::SvgPolygonNode

    val points by attribute<String?, List<Point2D>> { points ->
        if (points.isNullOrBlank()) {
            emptyList()
        } else {
            "[\\s,]+".toRegex()
                .split(points)
                .windowed(size = 2, step = 2)
                .map { (x, y) -> Point2D(x.toFloat(), y.toFloat()) }
        }
    }

    companion object {
        const val TAG_NAME = "polygon"
    }
}

fun SvgPolygonNode.asNode(
    minified: Boolean,
): ImageVectorNode.Path {
    val nodes = createSimplePolygonNodes(minified)
    return ImageVectorNode.Path(
        params = ImageVectorNode.Path.Params(
            fill = fillBrush(nodes),
            fillAlpha = fillOpacity,
            pathFillType = fillRule,
        ),
        wrapper = ImageVectorNode.NodeWrapper(
            normalizedPath = buildNormalizedPath(),
            nodes = nodes,
        ),
        minified = minified,
        transformations = transform?.toTransformations(),
    )
}

private fun SvgPolygonNode.buildNormalizedPath(): String = buildString {
    append("<polygon ")
    append("points=\"${points.joinToString(" ") { "${it.x} ${it.y}" }}\" ")
    append(graphicNodeParams())
    append("/>")
}

private fun SvgPolygonNode.createSimplePolygonNodes(
    minified: Boolean,
): List<PathNodes> = buildList {
    val points = points.toMutableList()
    val first = points.removeFirst()
    add(
        pathNode(command = PathCommand.MoveTo) {
            args(first.x, first.y)
            this.minified = minified
        },
    )
    points.forEachIndexed { index, (x, y) ->
        add(
            pathNode(command = PathCommand.LineTo) {
                args(x, y)
                this.minified = minified
                close = index == points.lastIndex
            },
        )
    }
}
