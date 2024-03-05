package dev.tonholo.s2c.domain

import kotlinx.serialization.Polymorphic
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
            "rect",
        ]
    )
    val commands: List<@Polymorphic SvgNode>,
)
