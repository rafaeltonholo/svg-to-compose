package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathCommand
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.builder.pathNode
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlParentNode

class SvgEllipseNode(
    parent: XmlParentNode,
    attributes: MutableMap<String, String>,
) : SvgGraphicNode<SvgEllipseNode>(parent, attributes, TAG_NAME) {
    override val constructor: SvgChildNodeConstructorFn<SvgEllipseNode> = ::SvgEllipseNode
    val cx: Float by attribute<SvgLength, Float>(defaultValue = 0.0f) { cx ->
        val root = rootParent as SvgRootNode
        val baseDimension = root.viewportWidth
        cx.toFloat(baseDimension)
    }
    val cy: Float by attribute<SvgLength, Float>(defaultValue = 0.0f) { cy ->
        val root = rootParent as SvgRootNode
        val baseDimension = root.viewportHeight
        cy.toFloat(baseDimension)
    }
    val rx: Float by attribute<SvgLength, Float>(defaultValue = 0.0f) { rx ->
        val root = rootParent as SvgRootNode
        val baseDimension = root.viewportWidth
        rx.toFloat(baseDimension)
    }
    val ry: Float by attribute<SvgLength, Float>(defaultValue = 0.0f) { ry ->
        val root = rootParent as SvgRootNode
        val baseDimension = root.viewportHeight
        ry.toFloat(baseDimension)
    }

    companion object {
        const val TAG_NAME = "ellipse"
    }
}

fun SvgEllipseNode.asNode(
    minified: Boolean,
): ImageVectorNode.Path {
    val nodes = createSimpleEllipseNodes(minified)
    return ImageVectorNode.Path(
        params = ImageVectorNode.Path.Params(
            fill = fillBrush(nodes),
            fillAlpha = fillOpacity,
            pathFillType = fillRule,
            stroke = strokeBrush(nodes),
            strokeAlpha = strokeOpacity,
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

private fun SvgEllipseNode.buildNormalizedPath(): String = buildString {
    append("<ellipse ")
    append("cx=\"$cx\" ")
    append("cy=\"$cy\" ")
    append("rx=\"$rx\" ")
    append("ry=\"$ry\" ")
    append(graphicNodeParams())
    append("/>")
}

private fun SvgEllipseNode.createSimpleEllipseNodes(
    minified: Boolean,
): List<PathNodes> = listOf(
    pathNode(command = PathCommand.MoveTo) {
        args(cx, cy - ry)
        this.minified = minified
    },
    pathNode(command = PathCommand.ArcTo) {
        args(rx, ry, 0f, true, false, cx, cy + ry)
        this.minified = minified
    },
    pathNode(command = PathCommand.ArcTo) {
        args(rx, ry, 0f, true, false, cx, cy - ry)
        this.minified = minified
        close = true
    },
)
