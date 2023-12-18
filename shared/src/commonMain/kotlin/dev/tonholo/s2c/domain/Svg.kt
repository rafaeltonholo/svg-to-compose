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
    val fill: String,
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

