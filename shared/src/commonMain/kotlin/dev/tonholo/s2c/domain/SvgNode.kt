package dev.tonholo.s2c.domain

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlPolyChildren

@Serializable
sealed interface SvgNode {
    interface Element {
        val fill: String?
        val opacity: Float?

        @SerialName("fill-opacity")
        val fillOpacity: Float?
        val style: String?
        val stroke: String?

        @SerialName("stroke-width")
        val strokeWidth: String? // <length | percentage>

        @SerialName("stroke-linejoin")
        val strokeLineJoin: String? // <arcs | bevel |miter | miter-clip | round>

        @SerialName("stroke-linecap")
        val strokeLineCap: String?

        @SerialName("fill-rule")
        val fillRule: String? // <nonzero | evenodd>

        @SerialName("stroke-opacity")
        val strokeOpacity: String? // <0..1 | percentage>

        @SerialName("stroke-miterlimit")
        val strokeMiterLimit: Float?

        @SerialName("stroke-dasharray")
        val strokeDashArray: String?
    }

    @Serializable
    @SerialName("path")
    data class Path(
        val d: String,
        override val fill: String?,
        override val opacity: Float?,
        @SerialName("fill-opacity")
        override val fillOpacity: Float?,
        override val style: String?,
        override val stroke: String?,
        @SerialName("stroke-width")
        override val strokeWidth: String?, // <length | percentage>
        @SerialName("stroke-linejoin")
        override val strokeLineJoin: String?, // <arcs | bevel |miter | miter-clip | round>
        @SerialName("stroke-linecap")
        override val strokeLineCap: String?,
        @SerialName("fill-rule")
        override val fillRule: String?, // <nonzero | evenodd>
        @SerialName("stroke-opacity")
        override val strokeOpacity: String?, // <0..1 | percentage>
        @SerialName("stroke-miterlimit")
        override val strokeMiterLimit: Float?,
        @SerialName("stroke-dasharray")
        override val strokeDashArray: String?,
    ) : SvgNode, Element

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

    @Serializable
    @SerialName("rect")
    data class Rect(
        val x: Int?,
        val y: Int?,
        val width: Int,
        val height: Int,
        val rx: Int?,
        val ry: Int?,
        override val fill: String?,
        override val opacity: Float?,
        @SerialName("fill-opacity")
        override val fillOpacity: Float?,
        override val style: String?,
        override val stroke: String?,
        @SerialName("stroke-width")
        override val strokeWidth: String?, // <length | percentage>
        @SerialName("stroke-linejoin")
        override val strokeLineJoin: String?, // <arcs | bevel |miter | miter-clip | round>
        @SerialName("stroke-linecap")
        override val strokeLineCap: String?,
        @SerialName("fill-rule")
        override val fillRule: String?, // <nonzero | evenodd>
        @SerialName("stroke-opacity")
        override val strokeOpacity: String?, // <0..1 | percentage>
        @SerialName("stroke-miterlimit")
        override val strokeMiterLimit: Float?,
        @SerialName("stroke-dasharray")
        override val strokeDashArray: String?,
    ) : SvgNode, Element
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
    is SvgNode.Rect -> asNode(width, height, minified)
}
