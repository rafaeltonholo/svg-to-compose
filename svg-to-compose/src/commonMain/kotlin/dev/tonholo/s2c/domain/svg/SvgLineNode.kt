package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathCommand
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.builder.pathNode
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlParentNode

class SvgLineNode(parent: XmlParentNode, attributes: MutableMap<String, String>) :
    SvgGraphicNode<SvgLineNode>(parent, attributes, TAG_NAME),
    SvgNode {
    override val constructor = ::SvgLineNode
    val x1: Float by attribute<SvgLength, Float>(defaultValue = 0.0f) { x1 ->
        val root = rootParent as SvgRootNode
        val baseDimension = root.viewportWidth
        x1.toFloat(baseDimension)
    }
    val y1: Float by attribute<SvgLength, Float>(defaultValue = 0.0f) { y1 ->
        val root = rootParent as SvgRootNode
        val baseDimension = root.viewportHeight
        y1.toFloat(baseDimension)
    }
    val x2: Float by attribute<SvgLength, Float>(defaultValue = 0.0f) { x2 ->
        val root = rootParent as SvgRootNode
        val baseDimension = root.viewportWidth
        x2.toFloat(baseDimension)
    }
    val y2: Float by attribute<SvgLength, Float>(defaultValue = 0.0f) { y2 ->
        val root = rootParent as SvgRootNode
        val baseDimension = root.viewportHeight
        y2.toFloat(baseDimension)
    }

    companion object {
        const val TAG_NAME = "line"
    }
}

fun SvgLineNode.asNode(minified: Boolean): ImageVectorNode.Path {
    val nodes = createSimpleLineNodes(minified)
    return ImageVectorNode.Path(
        params = ImageVectorNode.Path.Params(
            fill = fillBrush(nodes),
            fillAlpha = fillOpacity ?: opacity,
            pathFillType = fillRule,
            stroke = strokeBrush(nodes),
            strokeAlpha = strokeOpacity ?: opacity,
            strokeLineCap = strokeLineCap,
            strokeLineJoin = strokeLineJoin,
            strokeMiterLimit = strokeMiterLimit,
            strokeLineWidth = strokeWidth ?: stroke?.let { 1f },
        ),
        wrapper = ImageVectorNode.NodeWrapper(
            normalizedPath = buildNormalizedPath(),
            nodes = nodes,
        ),
        minified = minified,
        transformations = transform?.toTransformations(),
    )
}

private fun SvgLineNode.buildNormalizedPath(): String = buildString {
    append("<line ")
    append("x1=\"$x1\" ")
    append("y1=\"$y1\" ")
    append("x2=\"$x2\" ")
    append("y2=\"$y2\" ")
    append(graphicNodeParams())
    append("/>")
}

private fun SvgLineNode.createSimpleLineNodes(minified: Boolean): List<PathNodes> = listOf(
    pathNode(command = PathCommand.MoveTo) {
        args(x1, y1)
        this.minified = minified
    },
    pathNode(command = PathCommand.LineTo) {
        args(x2, y2)
        this.minified = minified
    },
)
