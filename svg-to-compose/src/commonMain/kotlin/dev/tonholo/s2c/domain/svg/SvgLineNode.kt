package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathCommand
import dev.tonholo.s2c.domain.builder.pathNode
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlParentNode

class SvgLineNode(parent: XmlParentNode, attributes: MutableMap<String, String>) :
    SvgGraphicNode<SvgLineNode>(parent, attributes, TAG_NAME),
    SvgNode {
    override val constructor: SvgChildNodeConstructorFn<SvgLineNode> = ::SvgLineNode

    val x1: Float by attribute<SvgLength, Float>(defaultValue = 0.0f) { value ->
        val root = rootParent as SvgRootNode
        value.toFloat(root.viewportWidth)
    }
    val y1: Float by attribute<SvgLength, Float>(defaultValue = 0.0f) { value ->
        val root = rootParent as SvgRootNode
        value.toFloat(root.viewportHeight)
    }
    val x2: Float by attribute<SvgLength, Float>(defaultValue = 0.0f) { value ->
        val root = rootParent as SvgRootNode
        value.toFloat(root.viewportWidth)
    }
    val y2: Float by attribute<SvgLength, Float>(defaultValue = 0.0f) { value ->
        val root = rootParent as SvgRootNode
        value.toFloat(root.viewportHeight)
    }

    companion object {
        const val TAG_NAME = "line"
    }
}

fun SvgLineNode.asNode(minified: Boolean): ImageVectorNode.Path {
    val nodes = listOf(
        pathNode(command = PathCommand.MoveTo) {
            args(x1, y1)
            this.minified = minified
        },
        pathNode(command = PathCommand.LineTo) {
            args(x2, y2)
            this.minified = minified
        },
    )
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
            strokeLineWidth = strokeWidth,
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
