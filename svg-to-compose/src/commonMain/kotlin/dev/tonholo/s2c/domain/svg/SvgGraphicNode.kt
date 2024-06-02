package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.StrokeDashArray
import dev.tonholo.s2c.domain.compose.ComposeBrush
import dev.tonholo.s2c.domain.compose.PathFillType
import dev.tonholo.s2c.domain.compose.StrokeCap
import dev.tonholo.s2c.domain.compose.StrokeJoin
import dev.tonholo.s2c.domain.compose.lowercase
import dev.tonholo.s2c.domain.compose.toBrush
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlChildNode
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.extensions.removeTrailingZero
import dev.tonholo.s2c.extensions.toLengthFloatOrNull
import dev.tonholo.s2c.geom.applyTransformations
import dev.tonholo.s2c.geom.path.removeShorthandNodes
import dev.tonholo.s2c.geom.path.toAbsolute

abstract class SvgGraphicNode<out T>(
    parent: XmlParentNode,
    override val attributes: MutableMap<String, String>,
    override val tagName: String,
) : SvgChildNode<T>(parent), SvgNode
    where T : SvgNode, T : XmlChildNode {
    val fill: SvgColor? by attribute<String?, _>(inherited = true) { it?.let(SvgColor::invoke) }

    val opacity: Float? by attribute()

    val fillOpacity: Float? by attribute(name = "fill-opacity", inherited = true)

    val style: String? by attribute()

    val stroke: SvgColor? by attribute<String?, _>(inherited = true) { it?.let(SvgColor::invoke) }

    val strokeWidth: Float? by attribute<String?, _>(name = "stroke-width", inherited = true) {
        val root = rootParent as SvgRootNode
        it?.toLengthFloatOrNull(width = root.viewportWidth, height = root.viewportHeight)
    } // <length | percentage>

    val strokeLineJoin: StrokeJoin? by attribute<String?, _>(name = "stroke-linejoin", inherited = true) {
        it?.let { StrokeJoin(it) }
    } // <arcs | bevel |miter | miter-clip | round>

    val strokeLineCap: StrokeCap? by attribute<String?, _>(name = "stroke-linecap", inherited = true) {
        it?.let { StrokeCap(it) }
    }

    val fillRule: PathFillType? by attribute<String?, _>(name = "fill-rule", inherited = true) {
        it?.let { PathFillType(it) }
    } // <nonzero | evenodd>

    val strokeOpacity: Float? by attribute<String?, _>(name = "stroke-opacity", inherited = true) {
        val root = rootParent as SvgRootNode
        it?.toLengthFloatOrNull(width = root.viewportWidth, height = root.viewportHeight)
    } // <0..1 | percentage>

    val strokeMiterLimit: Float? by attribute(name = "stroke-miterlimit", inherited = true)

    val strokeDashArray: StrokeDashArray? by attribute<String?, _>(
        name = "stroke-dasharray",
        inherited = true,
    ) { strokeDashArray ->
        strokeDashArray?.let(::StrokeDashArray)
    }

    fun graphicNodeParams(): String = buildString {
        fill?.let { append("fill=\"${it.value}\" ") }
        opacity?.let { append("opacity=\"$it\" ") }
        fillOpacity?.let { append("fill-opacity=\"$it\" ") }
        style?.let { append("style=\"$it\" ") }
        stroke?.let { append("stroke=\"${it.value}\" ") }
        strokeWidth?.let { append("stroke-width=\"${it.toString().removeTrailingZero()}\" ") }
        strokeLineJoin?.let { append("stroke-line-join=\"${it.lowercase()}\" ") }
        strokeLineCap?.let { append("stroke-line-cap=\"${it.lowercase()}\" ") }
        fillRule?.let { append("fill-rule=\"${it.lowercase()}\" ") }
        strokeOpacity?.let { append("stroke-opacity=\"$it\" ") }
        strokeMiterLimit?.let {
            append(
                "stroke-miter-limit=\"${
                    it.toString().removeTrailingZero()
                }\" ",
            )
        }
        strokeDashArray?.let { append("stroke-dasharray=\"${it}\" ") }
    }

    open fun fillBrush(nodes: List<PathNodes>): ComposeBrush? {
        val fill = fill.orDefault().value
        return if (fill.startsWith("url(")) {
            getGradient(fill, nodes)
        } else {
            fill.toBrush()
        }
    }

    open fun strokeBrush(nodes: List<PathNodes>): ComposeBrush? {
        val stroke = stroke?.value ?: return null
        return if (stroke.startsWith("url(")) {
            getGradient(stroke, nodes)
        } else {
            stroke.toBrush()
        }
    }

    protected fun getGradient(fillColor: String, nodes: List<PathNodes>): ComposeBrush.Gradient? {
        val root = rootParent as SvgRootNode
        val gradientId = fillColor.normalizedId()
        val gradient = root.gradients[gradientId] ?: return null
        val transformations = transform?.toTransformations()
            ?.plus(gradient.gradientTransform?.toTransformations() ?: emptyList())
            ?.toTypedArray()
            ?: emptyArray()

        return root.gradients[gradientId]?.toBrush(
            target = nodes
                .applyTransformations(transformations = transformations)
                .toList()
                .toAbsolute()
                .removeShorthandNodes(),
        )
    }

    override fun toString(): String = toJsString()
}
