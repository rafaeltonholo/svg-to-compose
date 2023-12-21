package dev.tonholo.s2c.domain

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

private const val ANDROID_NS = "http://schemas.android.com/apk/res/android"
private const val ANDROID_PREFIX = "android"

@Serializable
@XmlSerialName("vector")
data class AndroidVector(
    @XmlSerialName("width", ANDROID_NS, ANDROID_PREFIX)
    val width: String,
    @XmlSerialName("height", ANDROID_NS, ANDROID_PREFIX)
    val height: String,
    @XmlSerialName("viewportWidth", ANDROID_NS, ANDROID_PREFIX)
    val viewportWidth: Int,
    @XmlSerialName("viewportHeight", ANDROID_NS, ANDROID_PREFIX)
    val viewportHeight: Int,
    val nodes: List<@Polymorphic AndroidVectorNode>
)

@Serializable
sealed interface AndroidVectorNode {
    @Serializable
    @SerialName("path")
    data class Path(
        @XmlSerialName("pathData", ANDROID_NS, ANDROID_PREFIX)
        val pathData: String,
        @XmlSerialName("fillColor", ANDROID_NS, ANDROID_PREFIX)
        val fillColor: String?,
        @XmlSerialName("fillAlpha", ANDROID_NS, ANDROID_PREFIX)
        val fillAlpha: Float?,
        @XmlSerialName("fillType", ANDROID_NS, ANDROID_PREFIX)
        val fillType: String?, // evenOdd, noZero
        @XmlSerialName("strokeColor", ANDROID_NS, ANDROID_PREFIX)
        val strokeColor: String?,
        @XmlSerialName("strokeAlpha", ANDROID_NS, ANDROID_PREFIX)
        val strokeAlpha: Float?,
        @XmlSerialName("strokeLineCap", ANDROID_NS, ANDROID_PREFIX)
        val strokeLineCap: String?, // butt, round, square
        @XmlSerialName("strokeLineJoin", ANDROID_NS, ANDROID_PREFIX)
        val strokeLineJoin: String?, // miter, round, bevel
        @XmlSerialName("strokeMiterLimit", ANDROID_NS, ANDROID_PREFIX)
        val strokeMiterLimit: Float?,
        @XmlSerialName("strokeWidth", ANDROID_NS, ANDROID_PREFIX)
        val strokeWidth: Float?,
    ) : AndroidVectorNode

    @Serializable
    @SerialName("group")
    data class Group(
        @XmlElement
        @SerialName("clip-path")
        val clipPath: ClipPath,
        val paths: List<Path>,
    ) : AndroidVectorNode
}

@Serializable
@XmlSerialName("clip-path")
data class ClipPath(
    @XmlSerialName("pathData", ANDROID_NS, ANDROID_PREFIX)
    val pathData: String,
)

fun AndroidVectorNode.asNode(): ImageVectorNode = when (this) {
    is AndroidVectorNode.Group -> asNode()
    is AndroidVectorNode.Path -> asNode()
}

fun AndroidVectorNode.Path.asNode(): ImageVectorNode.Path = ImageVectorNode.Path(
    fillColor = fillColor.orEmpty(),
    wrapper = pathData.asNodeWrapper(),
)

fun AndroidVectorNode.Group.asNode(): ImageVectorNode.Group = ImageVectorNode.Group(
    clipPath = clipPath.pathData.asNodeWrapper(),
    nodes = paths.map { it.asNode() },
)
