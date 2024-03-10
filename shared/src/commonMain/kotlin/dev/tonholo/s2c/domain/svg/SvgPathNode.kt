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

    companion object {
        const val TAG_NAME = "path"
    }
}

fun SvgPathNode.asNode(
    minified: Boolean = false,
): ImageVectorNode.Path = ImageVectorNode.Path(
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
