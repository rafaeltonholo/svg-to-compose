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
    val viewBox: String, /*"0 0 116 114"*/
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
        val fill: String,
        val opacity: Int?,
    ) : SvgNode

    @Serializable
    @SerialName("g")
    data class Group(
        val paths: List<Path>,
        @XmlSerialName("mask")
        @SerialName("mask")
        val maskId: String,
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

fun SvgNode.asNode(svg: Svg? = null): ImageVectorNode? = when (this) {
    is SvgNode.Group -> svg?.let { asNode(masks = svg.commands.filterIsInstance<SvgNode.Mask>()) }
    is SvgNode.Mask -> null
    is SvgNode.Path -> asNode()
}

fun SvgNode.Path.asNode(): ImageVectorNode.Path = ImageVectorNode.Path(
    fillColor = fill,
    wrapper = d.asNodeWrapper(),
)


fun SvgNode.Group.asNode(
    masks: List<SvgNode.Mask>,
): ImageVectorNode.Group {

    // Can a svg mask have more than one path?
    // Currently, a group on ImageVector only receive a single PathData as a parameter. Not sure if it would support
    // multiple path tags inside a mask from a svg.
    val clipPath = masks.first {
        it.id == maskId.removePrefix("url(#").removeSuffix(")")
    }.paths.first().d.asNodeWrapper()

    return ImageVectorNode.Group(
        clipPath = clipPath,
        nodes = paths.map { it.asNode() },
    )
}
