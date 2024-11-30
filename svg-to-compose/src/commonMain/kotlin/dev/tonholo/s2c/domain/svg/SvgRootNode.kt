package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.svg.SvgNode.Companion.ATTR_HEIGHT
import dev.tonholo.s2c.domain.svg.SvgNode.Companion.ATTR_TRANSFORM
import dev.tonholo.s2c.domain.svg.SvgNode.Companion.ATTR_VIEW_BOX
import dev.tonholo.s2c.domain.svg.SvgNode.Companion.ATTR_WIDTH
import dev.tonholo.s2c.domain.svg.gradient.SvgGradient
import dev.tonholo.s2c.domain.svg.transform.SvgTransform
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.domain.xml.XmlRootNode

/**
 * Represents the root node of an SVG document.
 *
 * This class holds information about the SVG's dimensions, viewBox, and other attributes.
 * It also manages child nodes and provides access to definitions and gradients.
 *
 * @property parent The parent node of this node (always a [XmlRootNode]).
 * @property children The set of child nodes of this node.
 * @property attributes The map of attributes associated with this node.
 */
class SvgRootNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : SvgElementNode<SvgRootNode>(parent, children, attributes, tagName = TAG_NAME), SvgNode {
    override val constructor = ::SvgRootNode
    val width: Float by attribute<SvgLength?, Float> { width ->
        width?.toFloat(baseDimension = viewportWidth)
            ?: viewportWidth
    }
    val height: Float by attribute<SvgLength?, Float> { height ->
        height?.toFloat(baseDimension = viewportHeight)
            ?: viewportHeight
    }

    // backing field for memoization
    private var _viewBox: FloatArray? = null

    /**
     * The viewBox attribute defines the position and dimension, in user space,
     * of an SVG viewport.
     *
     * The value of the viewBox attribute is a list of four numbers:
     *  - min-x, represented by [viewportX]
     *  - min-y, [viewportY]
     *  - width, represented by [viewportWidth]
     *  - height, represented by [viewportHeight]
     *
     * These numbers are separated by whitespace and/or a comma, which must be
     * treated as a single space.
     *
     * If specified, the viewBox attribute establishes a new user space with its
     * origin at min-x, min-y and a width and height as specified. The viewport
     * (which is by default the SVG content element's nearest ancestor 'svg' element
     * or the root 'svg' element if there is no such ancestor) will stretch or shrink
     * its contents to fit the viewBox.
     *
     * The parsed viewBox is stored as a FloatArray with the following structure:
     * [0] - min-x
     * [1] - min-y
     * [2] - width
     * [3] - height
     *
     * If not specified via attributes, the view box is calculated based on the width
     * and height attributes. If no width or height is specified, the default values
     * are [0, 0, 350, 150]
     * @see <a href="https://www.w3.org/TR/SVG/coords.html#ViewBoxAttribute">
     *          The viewBox attribute on SVG
     *     </a>
     * @see [SVG_DEFAULT_WIDTH]
     * @see [SVG_DEFAULT_HEIGHT]
     */
    var viewBox: FloatArray by attribute<String?, _>(defaultValue = _viewBox) { viewBox ->
        parseViewBox(viewBox)
    }

    /**
     * The fill-color of the SVG.
     */
    var fill: String? by attribute()

    /**
     * The Min-X coordinate of the SVG's viewport, derived from the viewBox attribute.
     *
     * @see <a href="https://www.w3.org/TR/SVG/coords.html#ViewBoxAttribute">
     *          The viewBox attribute on SVG
     *     </a>
     */
    private val viewportX: Float by lazy {
        getDimensionFromViewBox(SVG_VIEW_BOX_X_POSITION) ?: 0f
    }

    /**
     * The Min-Y coordinate of the SVG's viewport, derived from the viewBox attribute.
     * @see <a href="https://www.w3.org/TR/SVG/coords.html#ViewBoxAttribute">
     *          The viewBox attribute on SVG
     *     </a>
     */
    private val viewportY: Float by lazy {
        getDimensionFromViewBox(SVG_VIEW_BOX_Y_POSITION) ?: 0f
    }

    /**
     * The width of the SVG's viewport, derived from the viewBox attribute.
     *
     * If the viewBox attribute is not present, it defaults to the width attribute
     * or [SVG_DEFAULT_WIDTH] if neither is present.
     *
     * @see safeWidth
     * @see <a href="https://www.w3.org/TR/SVG/coords.html#ViewBoxAttribute">
     *          The viewBox attribute on SVG
     *     </a>
     */
    val viewportWidth: Float by lazy {
        getDimensionFromViewBox(SVG_VIEW_BOX_WIDTH_POSITION) ?: safeWidth ?: SVG_DEFAULT_WIDTH
    }

    /**
     * The height of the SVG's viewport, derived from the viewBox attribute.
     *
     * If the viewBox attribute is not present, it defaults to the height attribute
     * or [SVG_DEFAULT_HEIGHT] if neither is present.
     *
     * @see safeHeight
     * @see <a href="https://www.w3.org/TR/SVG/coords.html#ViewBoxAttribute">
     *          The viewBox attribute on SVG
     *     </a>
     */
    val viewportHeight: Float by lazy {
        getDimensionFromViewBox(SVG_VIEW_BOX_HEIGHT_POSITION) ?: safeHeight ?: SVG_DEFAULT_HEIGHT
    }

    /**
     * A map of definitions (elements with an ID) within the SVG.
     * Used for referencing elements by ID, primarily for &lt;use&gt; elements.
     */
    val defs: HashMap<String, SvgUseNode> = hashMapOf()

    /**
     * A map of gradients defined within the SVG.
     * Used for applying gradients to elements.
     */
    val gradients: HashMap<String, SvgGradient<*>> = hashMapOf()

    /**
     * The transform applied to the entire SVG.
     * If the SVG has a viewBox with an offset (x or y not 0), a translation
     * is automatically applied to compensate for the offset.
     */
    override val transform: SvgTransform? by attribute<String?, SvgTransform?> {
        var transform = it
        if (viewportX != 0f || viewportY != 0f) {
            // Apply translation if viewBox has an offset
            transform = "translate(${-viewportX}, ${-viewportY})"
            attributes[ATTR_TRANSFORM] = transform
        }
        transform?.let(::SvgTransform)
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
        get() = attributes[ATTR_WIDTH]
            ?.let(::SvgLength)
            ?.toFloat(baseDimension = SVG_DEFAULT_WIDTH)

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
        get() = attributes[ATTR_HEIGHT]
            ?.let(::SvgLength)
            ?.toFloat(baseDimension = SVG_DEFAULT_HEIGHT)

    /**
     * Parses the viewBox attribute string into a [FloatArray].
     *
     * If the viewBox attribute is null or blank, it calculates a default viewBox
     * based on the width and height attributes.
     *
     * @param viewBoxRaw The raw viewBox attribute string.
     * @return A [FloatArray] representing the viewBox.
     * @throws IllegalArgumentException If the viewBox parsing fails to either
     *  parse values of create default view box, or in case of racing conditions.
     */
    private fun parseViewBox(viewBoxRaw: String?): FloatArray {
        if (_viewBox == null) {
            _viewBox = if (viewBoxRaw.isNullOrBlank()) {
                calculateDefaultViewBox(safeWidth, safeHeight)
            } else {
                val viewBox = viewBoxRaw
                    .splitToSequence(delimiters = viewBoxDelimiters)
                    .filter { it.isNotBlank() }
                    .mapNotNull { it.toFloatOrNull() }
                    .toList()
                    .toFloatArray()

                if (viewBox.size < VIEW_BOX_SIZE) {
                    calculateDefaultViewBox(safeWidth, safeHeight)
                } else {
                    viewBox
                }
            }
        }

        return requireNotNull(_viewBox) {
            "Invalid viewBox attribute."
        }
    }

    private fun getDimensionFromViewBox(dimensionIndex: Int): Float? =
        parseViewBox(attributes[ATTR_VIEW_BOX]).getOrNull(dimensionIndex)

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
        const val SVG_VIEW_BOX_X_POSITION = 0
        const val SVG_VIEW_BOX_Y_POSITION = 1
        const val SVG_VIEW_BOX_WIDTH_POSITION = 2
        const val SVG_VIEW_BOX_HEIGHT_POSITION = 3

        private const val VIEW_BOX_SIZE = 4
        private val viewBoxDelimiters = charArrayOf(',', ' ')

        private fun calculateDefaultViewBox(width: Float?, height: Float?): FloatArray =
            floatArrayOf(0f, 0f, width ?: SVG_DEFAULT_WIDTH, height ?: SVG_DEFAULT_HEIGHT)
    }
}
