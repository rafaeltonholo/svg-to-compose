package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.StrokeDashArray
import dev.tonholo.s2c.domain.compose.PathFillType
import dev.tonholo.s2c.domain.compose.StrokeCap
import dev.tonholo.s2c.domain.compose.StrokeJoin
import dev.tonholo.s2c.domain.compose.lowercase
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlChildNode
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.domain.xml.toJsString
import dev.tonholo.s2c.extensions.removeTrailingZero
import dev.tonholo.s2c.extensions.toLengthFloatOrNull

abstract class SvgGraphicNode(
    override val parent: XmlParentNode,
    override val attributes: MutableMap<String, String>,
    override val name: String,
) : XmlChildNode(), SvgNode {
    val fill: SvgColor? by attribute<String?, _> { it?.let { SvgColor(it) } }

    val opacity: Float? by attribute()

    val fillOpacity: Float? by attribute(name = "fill-opacity")

    val style: String? by attribute()

    val stroke: SvgColor? by attribute<String?, _> { it?.let { SvgColor(it) } }

    val strokeWidth: Float? by attribute<String?, _>(name = "stroke-width") {
        val root = rootParent as SvgElementNode
        it?.toLengthFloatOrNull(width = root.viewportWidth, height = root.viewportHeight)
    } // <length | percentage>

    val strokeLineJoin: StrokeJoin? by attribute<String?, _>(name = "stroke-linejoin") {
        it?.let { StrokeJoin(it) }
    } // <arcs | bevel |miter | miter-clip | round>

    val strokeLineCap: StrokeCap? by attribute<String?, _>(name = "stroke-linecap") {
        it?.let { StrokeCap(it) }
    }

    val fillRule: PathFillType? by attribute<String?, _>(name = "fill-rule") {
        it?.let { PathFillType(it) }
    } // <nonzero | evenodd>

    val strokeOpacity: Float? by attribute<String?, _>(name = "stroke-opacity") {
        val root = rootParent as SvgElementNode
        it?.toLengthFloatOrNull(width = root.viewportWidth, height = root.viewportHeight)
    } // <0..1 | percentage>

    val strokeMiterLimit: Float? by attribute(name = "stroke-miterlimit")

    val strokeDashArray: StrokeDashArray? by attribute<String?, _>(name = "stroke-dasharray") { strokeDashArray ->
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
        strokeMiterLimit?.let { append("stroke-miter-limit=\"${it.toString().removeTrailingZero()}\" ") }
        strokeDashArray?.let { append("stroke-dasharray=\"${it}\" ") }
    }

    override fun toString(): String {
        // Swallow parent toString to avoid infinity toString loop.
        return "{name:\"$name\", attributes:${attributes.toJsString()}, parent:\"${parent.name}\"}"
    }
}
