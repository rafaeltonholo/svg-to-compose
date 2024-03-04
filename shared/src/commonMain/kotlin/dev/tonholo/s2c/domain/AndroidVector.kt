package dev.tonholo.s2c.domain

import dev.tonholo.s2c.extensions.toLengthFloat
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlPolyChildren
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
        val fillType: String?, // evenOdd, nonZero
        @XmlSerialName("strokeColor", ANDROID_NS, ANDROID_PREFIX)
        val strokeColor: String?,
        // Although Android Vector only accepts Float, Svg accepts %.
        // As we still parse the SVG to XML after optimizing, we need to deal with % here too.
        @XmlSerialName("strokeAlpha", ANDROID_NS, ANDROID_PREFIX)
        val strokeAlpha: String?,
        @XmlSerialName("strokeLineCap", ANDROID_NS, ANDROID_PREFIX)
        val strokeLineCap: String?, // butt, round, square
        @XmlSerialName("strokeLineJoin", ANDROID_NS, ANDROID_PREFIX)
        val strokeLineJoin: String?, // miter, round, bevel
        @XmlSerialName("strokeMiterLimit", ANDROID_NS, ANDROID_PREFIX)
        val strokeMiterLimit: Float?,
        // Although Android Vector only accepts Float, Svg accepts %.
        // As we still parse the SVG to XML after optimizing, we need to deal with % here too.
        @XmlSerialName("strokeWidth", ANDROID_NS, ANDROID_PREFIX)
        val strokeWidth: String?,
    ) : AndroidVectorNode

    @Serializable
    @SerialName("group")
    data class Group(
        @SerialName("clip-path")
        val clipPath: ClipPath?,
        @XmlElement
        @XmlPolyChildren(
            [
                "path",
                "group",
            ]
        )
        val commands: List<@Polymorphic AndroidVectorNode>,
    ) : AndroidVectorNode
}

@Serializable
@XmlSerialName("clip-path")
data class ClipPath(
    @XmlSerialName("pathData", ANDROID_NS, ANDROID_PREFIX)
    val pathData: String,
)

fun AndroidVectorNode.asNode(
    width: Float,
    height: Float,
    minified: Boolean,
): ImageVectorNode = when (this) {
    is AndroidVectorNode.Group -> asNode(width, height, minified)
    is AndroidVectorNode.Path -> asNode(width, height, minified)
}

fun AndroidVectorNode.Path.asNode(
    width: Float,
    height: Float,
    minified: Boolean,
): ImageVectorNode.Path = ImageVectorNode.Path(
    params = ImageVectorNode.Path.Params(
        fill = fillColor.orEmpty(),
        fillAlpha = fillAlpha,
        pathFillType = PathFillType(fillType),
        stroke = strokeColor,
        strokeAlpha = strokeAlpha?.toLengthFloat(width, height),
        strokeLineCap = StrokeCap(strokeLineCap),
        strokeLineJoin = StrokeJoin(strokeLineJoin),
        strokeMiterLimit = strokeMiterLimit,
        strokeLineWidth = strokeWidth?.toLengthFloat(width, height),
    ),
    wrapper = pathData.asNodeWrapper(minified),
    minified = minified,
)

fun AndroidVectorNode.Group.asNode(
    width: Float,
    height: Float,
    minified: Boolean,
): ImageVectorNode.Group = ImageVectorNode.Group(
    clipPath = clipPath?.pathData?.asNodeWrapper(minified),
    commands = commands.map { it.asNode(width, height, minified) },
    minified = minified,
)
