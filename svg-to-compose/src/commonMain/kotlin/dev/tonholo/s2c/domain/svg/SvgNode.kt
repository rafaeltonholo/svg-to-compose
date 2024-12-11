package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.svg.SvgNode.Companion.ATTR_TRANSFORM
import dev.tonholo.s2c.domain.svg.transform.SvgTransform
import dev.tonholo.s2c.domain.xml.XmlChildNode
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.domain.xml.XmlRootNode

/**
 * Represents a node in the SVG tree.
 */
sealed interface SvgNode : XmlNode {
    /**
     * The transform attribute of the node.
     */
    val transform: SvgTransform?

    /**
     * Normalizes an id by removing prefixes and suffixes commonly used in SVGs.
     *
     * For example:
     * - `#someId` becomes `someId`
     * - `url(#someId)` becomes `someId`
     */
    fun String.normalizedId(): String = normalizeId(id = this)

    companion object {
        const val XLINK_NS = "xlink"
        const val ATTR_X = "x"
        const val ATTR_Y = "y"
        const val ATTR_WIDTH = "width"
        const val ATTR_HEIGHT = "height"
        const val ATTR_VIEW_BOX = "viewBox"
        const val ATTR_TRANSFORM = "transform"

        /**
         * Normalizes an id by removing prefixes and suffixes commonly used in SVGs.
         *
         * For example:
         * - `#someId` becomes `someId`
         * - `url(#someId)` becomes `someId`
         */
        fun String.normalizedId(): String = normalizeId(this)

        private fun normalizeId(id: String): String =
            id.removePrefix("#").removePrefix("url(#").removeSuffix(")")
    }
}

/**
 * Calculates the stacked transform of the node by traversing up the tree
 * and concatenating the transform attributes of the ancestors.
 * @param parent The parent node of the current node.
 * @return The stacked transform of the node.
 */
fun SvgNode.stackedTransform(parent: XmlParentNode): SvgTransform? {
    var stacked = attributes[ATTR_TRANSFORM]
    if (parent !is SvgDefsNode) {
        var currentParent: XmlParentNode? = parent

        while (currentParent !is XmlRootNode && currentParent != null) {
            val transform = if (currentParent is SvgRootNode) {
                currentParent.transform?.value
            } else {
                currentParent.attributes[ATTR_TRANSFORM]
            }

            transform?.let { value ->
                stacked = value + stacked?.let { " $it" }.orEmpty()
            }
            currentParent = (currentParent as? XmlChildNode)?.parent
            if (currentParent is SvgDefsNode) {
                stacked = attributes[ATTR_TRANSFORM]
                break
            }
        }
    }
    return stacked?.let(::SvgTransform)
}

/**
 * Converts the SVG node to a list of [ImageVectorNode].
 * @param masks The list of masks defined in the SVG.
 * @param minified Whether to minify the output.
 * @return A list of [ImageVectorNode] representing the SVG node.
 * Returns `null` if the node is not supported.
 */
fun SvgNode.asNodes(
    masks: List<SvgMaskNode>,
    minified: Boolean,
): List<ImageVectorNode>? {
    return when (this) {
        is SvgRootNode -> asNodes(minified = minified)
        is SvgGraphicNode<*> if maskId != null ->
            asMaskGroup().flatNode(masks, minified)

        is SvgGroupNode -> flatNode(masks, minified)
        is SvgCircleNode -> listOf(asNode(minified = minified))
        is SvgPathNode -> listOf(asNode(minified = minified))
        is SvgRectNode -> listOf(asNode(minified = minified))
        is SvgPolygonNode -> listOf(asNode(minified = minified))
        is SvgPolylineNode -> listOf(asNode(minified = minified))
        is SvgEllipseNode -> listOf(asNode(minified = minified))
        else -> null
    }
}
