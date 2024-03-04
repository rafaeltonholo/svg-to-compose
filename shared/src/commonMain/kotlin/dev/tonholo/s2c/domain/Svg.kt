package dev.tonholo.s2c.domain

import dev.tonholo.s2c.extensions.toLengthFloat
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlPolyChildren
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("svg", "http://www.w3.org/2000/svg")
data class Svg(
    val width: Int,
    val height: Int,
    val viewBox: String?, // "0 0 116 114"
    val fill: String?,
    @XmlElement
    @XmlPolyChildren(
        [
            "path",
            "g",
            "mask",
        ]
    )
    val commands: List<@Polymorphic SvgNode>,
)

@Serializable
sealed interface SvgNode {
    @Serializable
    @SerialName("path")
    data class Path(
        val d: String,
        val fill: String?,
        val opacity: Float?,
        @SerialName("fill-opacity")
        val fillOpacity: Float?,
        val style: String?,
        val stroke: String?,
        @SerialName("stroke-width")
        val strokeWidth: String?, // <length | percentage>
        @SerialName("stroke-linejoin")
        val strokeLineJoin: String?, // <arcs | bevel |miter | miter-clip | round>
        @SerialName("stroke-linecap")
        val strokeLineCap: String?,
        @SerialName("fill-rule")
        val fillRule: String?, // <nonzero | evenodd>
        @SerialName("stroke-opacity")
        val strokeOpacity: String?, // <0..1 | percentage>
        @SerialName("stroke-miterlimit")
        val strokeMiterLimit: Float?,
    ) : SvgNode

    @Serializable
    @SerialName("g")
    data class Group(
        @SerialName("mask")
        val maskId: String?,
        @SerialName("filter")
        val filterId: String?,
        val opacity: Float?,
        val style: String?,
        @XmlElement
        @XmlPolyChildren(
            [
                "path",
                "g",
                "mask",
            ]
        )
        val commands: List<@Polymorphic SvgNode>,
    ) : SvgNode

    @Serializable
    @SerialName("mask")
    data class Mask(
        val id: String,
        val style: String,
        val maskUnits: String,
        val x: Int,
        val y: Int,
        val width: Int,
        val height: Int,
        val paths: List<Path>,
    ) : SvgNode
}

fun SvgNode.asNode(
    width: Float,
    height: Float,
    masks: List<SvgNode.Mask>,
    minified: Boolean = false,
): ImageVectorNode? = when (this) {
    is SvgNode.Group -> asNode(width, height, masks, minified)
    is SvgNode.Mask -> null
    is SvgNode.Path -> asNode(width, height, minified)
}

fun SvgNode.Path.asNode(
    width: Float,
    height: Float,
    minified: Boolean = false,
): ImageVectorNode.Path = ImageVectorNode.Path(
    params = ImageVectorNode.Path.Params(
        fill = fill.orEmpty(),
        fillAlpha = fillOpacity,
        pathFillType = PathFillType(fillRule),
        stroke = stroke,
        strokeAlpha = strokeOpacity?.toLengthFloat(width, height),
        strokeLineCap = StrokeCap(strokeLineCap),
        strokeLineJoin = StrokeJoin(strokeLineJoin),
        strokeMiterLimit = strokeMiterLimit,
        strokeLineWidth = strokeWidth?.toLengthFloat(width, height),
    ),
    wrapper = d.asNodeWrapper(minified),
    minified = minified,
)

fun SvgNode.Group.asNode(
    width: Float,
    height: Float,
    masks: List<SvgNode.Mask>,
    minified: Boolean = false,
): ImageVectorNode.Group {
    // Can a svg mask have more than one path?
    // Currently, a group on ImageVector only receives a single PathData as a parameter.
    // Not sure if it would support multiple path tags inside a mask from a svg.
    val clipPath = masks.firstOrNull {
        it.id == maskId?.removePrefix("url(#")?.removeSuffix(")")
    }?.paths?.first()?.d?.asNodeWrapper(minified)

    return ImageVectorNode.Group(
        clipPath = clipPath,
        commands = commands.mapNotNull { it.asNode(width, height, masks, minified) },
        minified = minified,
    )
}
