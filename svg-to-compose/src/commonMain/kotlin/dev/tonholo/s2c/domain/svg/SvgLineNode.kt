package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathCommand
import dev.tonholo.s2c.domain.builder.pathNode
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlParentNode

/**
 * Represents an SVG `<line>` element, a straight stroked segment between
 * `(x1, y1)` and `(x2, y2)`. Unset coordinates default to `0` per the SVG
 * specification.
 *
 * @see <a href="https://www.w3.org/TR/SVG11/shapes.html#LineElement">
 *          The line element on SVG
 *     </a>
 */
class SvgLineNode(parent: XmlParentNode, attributes: MutableMap<String, String>) :
    SvgGraphicNode<SvgLineNode>(parent, attributes, TAG_NAME),
    SvgNode {
    override val constructor: SvgChildNodeConstructorFn<SvgLineNode> = ::SvgLineNode

    /**
     * The x-axis coordinate of the start of the line, resolved against the
     * root viewport width. Defaults to `0` when omitted.
     *
     * @see <a href="https://www.w3.org/TR/SVG11/shapes.html#LineElementX1Attribute">
     *          The x1 attribute on SVG
     *     </a>
     */
    val x1: Float by attribute<SvgLength, Float>(defaultValue = 0.0f) { value ->
        val root = rootParent as SvgRootNode
        value.toFloat(root.viewportWidth)
    }

    /**
     * The y-axis coordinate of the start of the line, resolved against the
     * root viewport height. Defaults to `0` when omitted.
     *
     * @see <a href="https://www.w3.org/TR/SVG11/shapes.html#LineElementY1Attribute">
     *          The y1 attribute on SVG
     *     </a>
     */
    val y1: Float by attribute<SvgLength, Float>(defaultValue = 0.0f) { value ->
        val root = rootParent as SvgRootNode
        value.toFloat(root.viewportHeight)
    }

    /**
     * The x-axis coordinate of the end of the line, resolved against the
     * root viewport width. Defaults to `0` when omitted.
     *
     * @see <a href="https://www.w3.org/TR/SVG11/shapes.html#LineElementX2Attribute">
     *          The x2 attribute on SVG
     *     </a>
     */
    val x2: Float by attribute<SvgLength, Float>(defaultValue = 0.0f) { value ->
        val root = rootParent as SvgRootNode
        value.toFloat(root.viewportWidth)
    }

    /**
     * The y-axis coordinate of the end of the line, resolved against the
     * root viewport height. Defaults to `0` when omitted.
     *
     * @see <a href="https://www.w3.org/TR/SVG11/shapes.html#LineElementY2Attribute">
     *          The y2 attribute on SVG
     *     </a>
     */
    val y2: Float by attribute<SvgLength, Float>(defaultValue = 0.0f) { value ->
        val root = rootParent as SvgRootNode
        value.toFloat(root.viewportHeight)
    }

    companion object {
        const val TAG_NAME = "line"
    }
}

/**
 * Converts this line into an [ImageVectorNode.Path] composed of a
 * `MoveTo(x1, y1)` followed by `LineTo(x2, y2)`, carrying the inherited
 * fill, stroke, and transformation parameters.
 *
 * @param minified When `true`, emits the path nodes without surrounding
 * comments or formatting whitespace.
 */
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

/**
 * Rebuilds the canonical `<line>` markup with the resolved coordinates and
 * graphic attributes for inclusion in the normalized SVG snapshot.
 */
private fun SvgLineNode.buildNormalizedPath(): String = buildString {
    append("<line ")
    append("x1=\"$x1\" ")
    append("y1=\"$y1\" ")
    append("x2=\"$x2\" ")
    append("y2=\"$y2\" ")
    append(graphicNodeParams())
    append("/>")
}
