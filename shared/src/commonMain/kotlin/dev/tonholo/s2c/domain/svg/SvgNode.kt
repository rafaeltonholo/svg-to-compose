package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlElementNode
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

sealed interface SvgNode : XmlNode {
    fun String.normalizedId(): String =
        removePrefix("url(#").removeSuffix(")")
}

class SvgElementNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : XmlElementNode(parent, children, attributes, name = TAG_NAME), SvgNode {
    val width: Float by attribute<SvgLength?, Float> { width ->
        width?.toFloat(baseDimension = SVG_DEFAULT_WIDTH)
            ?: viewportWidth
    }
    val height: Float by attribute<SvgLength?, Float> { height ->
        height?.toFloat(baseDimension = SVG_DEFAULT_WIDTH)
            ?: viewportHeight
    }
    var viewBox: FloatArray by attribute<String?, _> { viewBox ->
        parseViewBox(viewBox) ?: floatArrayOf(0f, 0f, width, height)
    }
    var fill: String? by attribute()

    val viewportWidth: Float by lazy {
        getDimensionFromViewBox(SVG_VIEW_BOX_WIDTH_POSITION) ?: safeWidth ?: SVG_DEFAULT_WIDTH
    }

    val viewportHeight: Float by lazy {
        getDimensionFromViewBox(SVG_VIEW_BOX_HEIGHT_POSITION) ?: safeHeight ?: SVG_DEFAULT_HEIGHT
    }

    /**
     * Checks if width is present in the attribute map.
     * If it is the case, return the [width] property which
     * calculates the correct width based on a [SvgLength],
     * otherwise null.
     *
     * This is required since both width and viewBox attributes
     * can be omitted.
     */
    private val safeWidth: Float?
        get() = if (attributes.contains("width")) width else null

    /**
     * Checks if width is present in the attribute map.
     * If it is the case, return the [height] property which
     * calculates the correct height based on a [SvgLength],
     * otherwise null.
     *
     * This is required since both height and viewBox attributes
     * can be omitted.
     */
    private val safeHeight: Float?
        get() = if (attributes.contains("height")) height else null

    private inline fun parseViewBox(viewBox: String?): FloatArray? =
        viewBox?.split(", ", ",", " ")?.map { it.toFloat() }?.toFloatArray()

    private inline fun getDimensionFromViewBox(dimensionIndex: Int): Float? =
        parseViewBox(attributes["viewBox"])?.getOrNull(dimensionIndex)

    companion object {
        const val TAG_NAME = "svg"

        /**
         * The default value if both width and viewBox are omitted.
         * @see <a href="https://developer.mozilla.org/en-US/docs/Web/SVG/Attribute/width#svg">
         *          Width attribute on SVG
         *      </a>
         */
        private const val SVG_DEFAULT_WIDTH = 300f

        /**
         * The default value if both height and viewBox are omitted.
         * @see <a href="https://developer.mozilla.org/en-US/docs/Web/SVG/Attribute/height#svg">
         *          Height attribute on SVG
         *     </a>
         */
        private const val SVG_DEFAULT_HEIGHT = 150f
        const val SVG_VIEW_BOX_WIDTH_POSITION = 2
        const val SVG_VIEW_BOX_HEIGHT_POSITION = 3
    }
}

inline fun SvgElementNode.asNodes(
    minified: Boolean,
): List<ImageVectorNode> {
    val masks = children.filterIsInstance<SvgMaskNode>()
    return children.mapNotNull { node -> (node as? SvgNode)?.asNode(masks, minified) }
}

inline fun SvgNode.asNode(
    masks: List<SvgMaskNode>,
    minified: Boolean,
): ImageVectorNode? = when (this) {
    is SvgGroupNode -> asNode(masks, minified)
    is SvgPathNode -> asNode(minified)
    is SvgRectNode -> asNode(minified)
    is SvgCircleNode -> asNode(minified)
    else -> null
}
