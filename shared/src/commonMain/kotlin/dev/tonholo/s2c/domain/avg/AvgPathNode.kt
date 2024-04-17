package dev.tonholo.s2c.domain.avg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.asNodeWrapper
import dev.tonholo.s2c.domain.compose.PathFillType
import dev.tonholo.s2c.domain.compose.StrokeCap
import dev.tonholo.s2c.domain.compose.StrokeJoin
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlElementNode
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.extensions.toLengthFloat

class AvgPathNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : XmlElementNode(parent, children, attributes, TAG_NAME), AvgNode {
    val pathData: String by attribute(namespace = AvgNode.NAMESPACE)

    val fillColor: String? by attribute(namespace = AvgNode.NAMESPACE)

    val fillAlpha: Float? by attribute(namespace = AvgNode.NAMESPACE)

    val fillType: PathFillType? by attribute<String?, _>(namespace = AvgNode.NAMESPACE) {
        it?.let(PathFillType::invoke)
    } // evenOdd, nonZero

    val strokeColor: String? by attribute(namespace = AvgNode.NAMESPACE)

    // Although Android Vector only accepts Float, Svg accepts %.
    // As we still parse the SVG to XML after optimizing, we need to deal with % here too.
    val strokeAlpha: Float? by attribute<String?, _>(namespace = AvgNode.NAMESPACE) {
        (parent as? AvgElementNode)?.let { parent -> it?.toLengthFloat(parent.width, parent.height) }
    }

    val strokeLineCap: StrokeCap? by attribute<String?, _>(namespace = AvgNode.NAMESPACE) {
        it?.let(StrokeCap::invoke)
    } // butt, round, square

    val strokeLineJoin: StrokeJoin? by attribute<String?, _>(namespace = AvgNode.NAMESPACE) {
        it?.let(StrokeJoin::invoke)
    } // miter, round, bevel

    val strokeMiterLimit: Float? by attribute(namespace = AvgNode.NAMESPACE)

    // Although Android Vector only accepts Float, Svg accepts %.
    // As we still parse the SVG to XML after optimizing, we need to deal with % here too.
    val strokeWidth: Float? by attribute<String?, _>(namespace = AvgNode.NAMESPACE) {
        (parent as? AvgElementNode)?.let { parent -> it?.toLengthFloat(parent.width, parent.height) }
    }

    companion object {
        const val TAG_NAME = "path"
    }
}

fun AvgPathNode.asNode(
    minified: Boolean,
): ImageVectorNode.Path = ImageVectorNode.Path(
    params = ImageVectorNode.Path.Params(
        fill = fillColor.orEmpty(),
        fillAlpha = fillAlpha,
        pathFillType = fillType,
        stroke = strokeColor,
        strokeAlpha = strokeAlpha,
        strokeLineCap = strokeLineCap,
        strokeLineJoin = strokeLineJoin,
        strokeMiterLimit = strokeMiterLimit,
        strokeLineWidth = strokeWidth,
    ),
    wrapper = pathData.asNodeWrapper(minified),
    minified = minified,
)
