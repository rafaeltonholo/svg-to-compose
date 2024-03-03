package dev.tonholo.s2c.domain

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

fun SvgNode.asNode(masks: List<SvgNode.Mask>, minified: Boolean = false): ImageVectorNode? = when (this) {
    is SvgNode.Group -> asNode(masks, minified)
    is SvgNode.Mask -> null
    is SvgNode.Path -> asNode(minified)
}

fun SvgNode.Path.asNode(minified: Boolean = false): ImageVectorNode.Path = ImageVectorNode.Path(
    fillColor = fill.orEmpty(),
    wrapper = d.asNodeWrapper(minified),
    minified = minified,
)

fun SvgNode.Group.asNode(
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
        commands = commands.mapNotNull { it.asNode(masks, minified) },
        minified = minified,
    )
}
