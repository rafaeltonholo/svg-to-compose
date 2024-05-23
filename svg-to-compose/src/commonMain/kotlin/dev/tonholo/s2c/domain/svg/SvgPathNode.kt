package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.asNodeWrapper
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlParentNode

class SvgPathNode(
    parent: XmlParentNode,
    attributes: MutableMap<String, String>,
) : SvgGraphicNode(parent, attributes, TAG_NAME), SvgNode {
    val d: String by attribute()
    val clipPath: String? by attribute(name = "clip-path")

    companion object {
        const val TAG_NAME = "path"
    }
}

fun SvgPathNode.asNode(
    minified: Boolean = false,
): ImageVectorNode {
    val path = ImageVectorNode.Path(
        params = ImageVectorNode.Path.Params(
            fill = fill?.value.orEmpty(),
            fillAlpha = fillOpacity,
            pathFillType = fillRule,
            stroke = stroke?.value,
            strokeAlpha = strokeOpacity,
            strokeLineCap = strokeLineCap,
            strokeLineJoin = strokeLineJoin,
            strokeMiterLimit = strokeMiterLimit,
            strokeLineWidth = strokeWidth,
        ),
        wrapper = d.asNodeWrapper(minified),
        minified = minified,
    )

    return clipPath?.normalizedId()?.let { clipPathId ->
        val clipPath = rootParent
            .children
            .find { it is SvgClipPath && it.id == clipPathId } as? SvgClipPath
        clipPath?.let {
            ImageVectorNode.Group(
                clipPath = it.asNodeWrapper(minified),
                commands = listOf(path),
                minified = minified,
            )
        }
    } ?: path
}
