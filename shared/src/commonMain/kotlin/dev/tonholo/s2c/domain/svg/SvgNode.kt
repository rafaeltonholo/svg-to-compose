package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlElementNode
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

sealed interface SvgNode : XmlNode

class SvgElementNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : XmlElementNode(parent, children, attributes, name = TAG_NAME), SvgNode {
    val width: Int by attribute<SvgLength?, Int> { width ->
        width?.toInt()
            ?: getDimensionFromViewBox(SVG_VIEW_BOX_WIDTH_POSITION)
            ?: SVG_DEFAULT_WIDTH
    }
    val height: Int by attribute<SvgLength?, Int> { height ->
        height?.toInt()
            ?: getDimensionFromViewBox(SVG_VIEW_BOX_HEIGHT_POSITION)
            ?: SVG_DEFAULT_HEIGHT
    }
    var viewBox: FloatArray by attribute<String?, _> { viewBox ->
        parseViewBox(viewBox) ?: floatArrayOf(0f, 0f, width.toFloat(), height.toFloat())
    }
    var fill: String? by attribute()

    private inline fun parseViewBox(viewBox: String?): FloatArray? =
        viewBox?.split(", ", ",", " ")?.map { it.toFloat() }?.toFloatArray()

    private inline fun getDimensionFromViewBox(dimensionIndex: Int): Int? =
        parseViewBox(attributes["viewBox"])?.getOrNull(dimensionIndex)?.toInt()

    companion object {
        const val TAG_NAME = "svg"

        /**
         * The default value if both width and viewBox are omitted.
         * @see <a href="https://developer.mozilla.org/en-US/docs/Web/SVG/Attribute/width#svg">
         *          Width attribute on SVG
         *      </a>
         */
        private const val SVG_DEFAULT_WIDTH = 300

        /**
         * The default value if both height and viewBox are omitted.
         * @see <a href="https://developer.mozilla.org/en-US/docs/Web/SVG/Attribute/height#svg">
         *          Height attribute on SVG
         *     </a>
         */
        private const val SVG_DEFAULT_HEIGHT = 150
        private const val SVG_VIEW_BOX_WIDTH_POSITION = 2
        private const val SVG_VIEW_BOX_HEIGHT_POSITION = 3
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
    else -> null
}
