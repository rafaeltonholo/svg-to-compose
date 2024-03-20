package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.PathFillType
import dev.tonholo.s2c.domain.StrokeCap
import dev.tonholo.s2c.domain.StrokeJoin
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlChildNodeWithAttributes
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.domain.xml.toJsString
import dev.tonholo.s2c.extensions.toLengthFloat

abstract class SvgGraphicNode(
    override val parent: XmlParentNode,
    override val attributes: MutableMap<String, String>,
    override val name: String,
) : XmlChildNodeWithAttributes {
    val fill: SvgColor? by attribute<String?, _> { it?.let { SvgColor(it) } }

    val opacity: Float? by attribute()

    val fillOpacity: Float? by attribute(name = "fill-opacity")

    val style: String? by attribute()

    val stroke: SvgColor? by attribute<String?, _> { it?.let { SvgColor(it) } }

    open val strokeWidth: Float? by attribute<String?, _>(name = "stroke-width") {
        (parent as? SvgElementNode)?.let { parent ->
            it?.toLengthFloat(width = parent.width.toFloat(), height = parent.height.toFloat())
        }
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

    open val strokeOpacity: Float? by attribute<String?, _>(name = "stroke-opacity") {
        (parent as? SvgElementNode)?.let { parent ->
            it?.toLengthFloat(width = parent.width.toFloat(), height = parent.height.toFloat())
        }
    } // <0..1 | percentage>

    val strokeMiterLimit: Float? by attribute(name = "stroke-miterlimit")

    val strokeDashArray: IntArray? by attribute<String?, _>(name = "stroke-dasharray") { strokeDashArray ->
        strokeDashArray?.split(" ")?.map { it.toInt() }?.toIntArray()
    }

    override fun toString(): String {
        // Swallow parent toString to avoid infinity toString loop.
        return "{name:\"$name\", attributes:${attributes.toJsString()}, parent:\"${parent.name}\"}"
    }
}
