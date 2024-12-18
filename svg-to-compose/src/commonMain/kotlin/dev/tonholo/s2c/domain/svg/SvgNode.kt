package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.svg.SvgNode.Companion.ATTR_TRANSFORM
import dev.tonholo.s2c.domain.svg.transform.SvgTransform
import dev.tonholo.s2c.domain.xml.XmlChildNode
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.domain.xml.XmlRootNode
import dev.tonholo.s2c.parser.ImageParser.SvgParser.ComputedRule
import dev.tonholo.s2c.parser.ast.css.syntax.node.Value

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

    fun resolveAttributesFromStyle(computedRules: List<ComputedRule>) {
        val attributes = buildMap<String, String> {
            resolveAttributesByStyleAttribute()

            id?.let { id ->
                resolveAttributesBySelector(computedRules, selector = id)
            }

            className?.let { className ->
                resolveAttributesBySelector(computedRules, selector = className)
            }
        }

        for ((key, value) in attributes) {
            // Declared attributes have precedence over computed attributes.
            if (this.attributes.contains(key).not()) {
                this.attributes[key] = value
            }
        }
    }

    private fun MutableMap<String, String>.resolveAttributesByStyleAttribute() {
        style?.let { style ->
            val parts = style.split(";")
            for (part in parts) {
                val (property, value) = part.split(":")
                if (attributes.containsKey(property).not()) {
                    put(property.trim(), value.trim())
                }
            }
        }
    }

    private fun MutableMap<String, String>.resolveAttributesBySelector(
        computedRules: List<ComputedRule>,
        selector: String,
    ) {
        val rulesPerSelector = computedRules
            .filter { it.selector.endsWith(selector) }
            .sortedDescending()
        for (rule in rulesPerSelector) {
            for (declaration in rule.declarations) {
                when {
                    attributes.containsKey(declaration.property) || containsKey(declaration.property) -> Unit
                    else -> put(
                        declaration.property,
                        declaration.values.joinToString(" ") { value -> resolveAttributeValue(value) })
                }
            }
        }
    }

    private fun resolveAttributeValue(value: Value): String {
        return when (value) {
            is Value.Url -> "url(${value.value})"
            else -> value.location.source
        }
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
    computedRules: List<ComputedRule>,
    masks: List<SvgMaskNode>,
    minified: Boolean,
): List<ImageVectorNode>? {
    resolveAttributesFromStyle(computedRules)
    return when (this) {
        is SvgRootNode -> asNodes(computedRules, minified = minified)
        is SvgGraphicNode<*> if maskId != null ->
            asMaskGroup().flatNode(masks, computedRules, minified)

        is SvgGroupNode -> flatNode(masks, computedRules, minified)
        is SvgCircleNode -> listOf(asNode(minified = minified))
        is SvgPathNode -> listOf(asNode(minified = minified))
        is SvgRectNode -> listOf(asNode(minified = minified))
        is SvgPolygonNode -> listOf(asNode(minified = minified))
        is SvgPolylineNode -> listOf(asNode(minified = minified))
        is SvgEllipseNode -> listOf(asNode(minified = minified))
        else -> null
    }
}
