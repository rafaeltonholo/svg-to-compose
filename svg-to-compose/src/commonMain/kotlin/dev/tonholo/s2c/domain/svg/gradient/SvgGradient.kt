package dev.tonholo.s2c.domain.svg.gradient

import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.compose.ComposeBrush
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.svg.SvgColor
import dev.tonholo.s2c.domain.svg.SvgElementNode
import dev.tonholo.s2c.domain.svg.SvgGradientStopNode
import dev.tonholo.s2c.domain.svg.SvgLength
import dev.tonholo.s2c.domain.svg.SvgNode
import dev.tonholo.s2c.domain.svg.SvgRootNode
import dev.tonholo.s2c.domain.svg.SvgUseNode
import dev.tonholo.s2c.domain.svg.transform.SvgTransform
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.geom.AffineTransformation
import dev.tonholo.s2c.geom.bounds.BoundingBox
import dev.tonholo.s2c.geom.bounds.boundingBox
import dev.tonholo.s2c.logger.NoOpLogger
import kotlin.math.max

sealed class SvgGradient<out T>(
    parent: XmlParentNode,
    children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
    tagName: String,
) : SvgElementNode<T>(parent, children, attributes, tagName)
    where T : SvgNode, T : XmlParentNode {
    val gradientUnits: String by attribute(defaultValue = "objectBoundingBox") // <userSpaceOnUse | objectBoundingBox>
    val gradientTransform: SvgTransform? by attribute<String?, SvgTransform?> {
        it?.let(::SvgTransform)
    }

    // TODO(#225): figure out a way to avoid this NoOpLogger workaround for context parameters in attribute delegates.
    val spreadMethod: SvgGradientSpreadMethod by attribute<String, _>(
        defaultValue = SvgGradientSpreadMethod.Pad,
    ) { spreadMethod ->
        with(NoOpLogger) { SvgGradientSpreadMethod(spreadMethod) }
    }
    val href: String? by attribute(name = "xlink:href")

    val colorStops: Pair<List<SvgColor>, List<Float>>
        get() {
            if (children.isEmpty()) {
                return emptyList<SvgColor>() to emptyList()
            }

            return children
                .filterIsInstance<SvgGradientStopNode>()
                .onEach { it.resolveAttributesFromStyle((rootParent as SvgRootNode).rules) }
                .map { it.gradientColor to it.offset }
                .unzip()
        }

    /**
     * Calculates the X coordinate for gradient positioning based on the SVG length and
     * gradient units' system.
     *
     * @param length the SVG length value specifying the X coordinate, which may include
     *  units or percentages
     * @param target the list of path nodes used to calculate the bounding box when
     *  [gradientUnits] is `"objectBoundingBox"`, defaults to an empty list
     * @return the calculated X coordinate value as a [Double] in the appropriate coordinate system
     */
    internal fun calculateGradientXCoordinate(length: SvgLength, target: List<PathNodes> = emptyList()): Double =
        calculateGradientAxisCoordinate(
            getViewportAxis = { viewportX },
            getViewportDimension = { viewportWidth },
            getBoundingBoxAxis = { x },
            getBoundingBoxDimension = { width },
            length = length,
            target = target,
        )

    /**
     * Calculates the Y coordinate for gradient positioning based on the SVG length and
     * gradient units' system.
     *
     * @param length the SVG length value specifying the Y coordinate, which may include
     *  units or percentages
     * @param target the list of path nodes used to calculate the bounding box when
     *  [gradientUnits] is `"objectBoundingBox"`, defaults to an empty list
     * @return the calculated Y coordinate value as a [Double] in the appropriate coordinate system
     */
    internal fun calculateGradientYCoordinate(length: SvgLength, target: List<PathNodes> = emptyList()): Double =
        calculateGradientAxisCoordinate(
            getViewportAxis = { viewportY },
            getViewportDimension = { viewportHeight },
            getBoundingBoxAxis = { y },
            getBoundingBoxDimension = { height },
            length = length,
            target = target,
        )

    /**
     * Calculates a single axis coordinate for gradient positioning based on the SVG length and
     * gradient units' system.
     *
     * When [gradientUnits] is `"objectBoundingBox"`, the coordinate is calculated relative
     * to the target's bounding box using the provided bounding box accessor functions.
     *
     * When [gradientUnits] is `"userSpaceOnUse"` or any other value, the coordinate is
     * calculated relative to the root SVG viewport using the provided viewport accessor functions,
     * with a translation applied based on the viewport axis position.
     *
     * @param getViewportAxis function to extract the axis coordinate (x or y) from the SVG root viewport
     * @param getViewportDimension function to extract the dimension (width or height) from the SVG root viewport
     * @param getBoundingBoxAxis function to extract the axis coordinate (x or y) from the bounding box
     * @param getBoundingBoxDimension function to extract the dimension (width or height) from the bounding box
     * @param length the SVG length value specifying the coordinate, which may include units or percentages
     * @param target the list of path nodes used to calculate the bounding box when [gradientUnits]
     *  is `"objectBoundingBox"`, defaults to an empty list
     * @return the calculated coordinate value as a [Double] in the appropriate coordinate system
     */
    private fun calculateGradientAxisCoordinate(
        getViewportAxis: SvgRootNode.() -> Float,
        getViewportDimension: SvgRootNode.() -> Float,
        getBoundingBoxAxis: BoundingBox.() -> Double,
        getBoundingBoxDimension: BoundingBox.() -> Double,
        length: SvgLength,
        target: List<PathNodes> = emptyList(),
    ): Double {
        val root = rootParent as SvgRootNode
        return if (gradientUnits == "objectBoundingBox") {
            check(target.isNotEmpty())
            val boundingBox = target.boundingBox()
            val dimension = boundingBox.getBoundingBoxDimension()
            // objectBoundingBox values are fractions of the bbox dimension.
            // Normalize by treating the value as a fraction of unit length,
            // so both "0.4" (fraction) and "40%" map to 0.4 * dimension.
            val fraction = length.toDouble(baseDimension = 1.0)
            fraction * dimension + boundingBox.getBoundingBoxAxis()
        } else {
            val translate = -root.getViewportAxis()
            translate + length.toDouble(root.getViewportDimension().toDouble())
        }
    }

    /**
     * Calculates the X and Y coordinate for gradient positioning based on the gradient
     * units coordinate system.
     *
     * When [gradientUnits] is `"objectBoundingBox"`, the coordinate is calculated relative
     * to the target's bounding box, using the maximum of the bounding box's width and
     * height as the base dimension, and adding the maximum of the bounding box's `x` and
     * `y` position.
     *
     * When [gradientUnits] is `"userSpaceOnUse"` or any other value, the coordinate is
     * calculated relative to the root SVG viewport, using the maximum of the viewport's
     * `width` and `height` as the base dimension.
     *
     * @param length the SVG length value to convert to a coordinate
     * @param target the list of path nodes used to calculate the bounding box when
     *  [gradientUnits] is "objectBoundingBox"
     * @param translateByBoundingBoxOrigin whether to add the bounding box origin offset
     *  to the result. Should be `true` for positional coordinates (e.g., cx, cy) and
     *  `false` for size-based values (e.g., radius r).
     * @return the calculated coordinate value as a Double
     */
    internal fun calculateGradientXYCoordinate(
        length: SvgLength,
        target: List<PathNodes> = emptyList(),
        translateByBoundingBoxOrigin: Boolean = true,
    ): Double {
        val root = rootParent as SvgRootNode
        return if (gradientUnits == "objectBoundingBox") {
            check(target.isNotEmpty())
            val boundingBox = target.boundingBox()
            val dimension = max(boundingBox.width, boundingBox.height)
            val fraction = length.toDouble(baseDimension = 1.0)
            val value = fraction * dimension
            if (translateByBoundingBoxOrigin) {
                value + max(boundingBox.x, boundingBox.y)
            } else {
                value
            }
        } else {
            val baseDimension = max(root.viewportWidth, root.viewportHeight).toDouble()
            length.toDouble(baseDimension)
        }
    }

    /**
     * Computes the combined gradient transformation matrix from the [gradientTransform] attribute.
     *
     * Parses the gradient transformations, combines them by multiplying them in sequence,
     * and returns the result as an [AffineTransformation.Matrix]. If the [gradientTransform]
     * attribute is `null`, empty, or if the combined result cannot be represented as a Matrix,
     * the method returns `null` or creates a new Matrix from the transformation's underlying matrix.
     *
     * @return the combined transformation matrix, or `null` if [gradientTransform] is `null`,
     * blank, or contains no valid transformations
     */
    internal fun computeGradientTransformMatrix(): AffineTransformation.Matrix? {
        val transformations = gradientTransform?.toTransformations() ?: return null
        if (transformations.isEmpty()) return null
        val combined = transformations.reduce { acc, current -> acc * current }
        return combined as? AffineTransformation.Matrix
            ?: AffineTransformation.Matrix(combined.matrix[0], combined.matrix[1], combined.matrix[2])
    }

    /**
     * Converts this SVG gradient definition into a Compose Brush gradient.
     *
     * @param target the list of path nodes representing the shape to which this gradient
     *  will be applied, used for calculating gradient coordinates when [gradientUnits] is
     *  `"objectBoundingBox"`
     * @return a Compose gradient brush configured with the resolved gradient properties,
     *  colour stops, transformations, and spread method
     */
    fun toBrush(target: List<PathNodes>): ComposeBrush.Gradient {
        val resolvedHref = href
        if (resolvedHref != null) {
            val resolved = resolveHrefChain(resolvedHref)
            val mergedCopy = copy(
                attributes = resolved.attributes,
                children = resolved.children.filterIsInstance<SvgNode>().toMutableSet(),
            ) as SvgGradient<*>
            return mergedCopy.toBrush(target)
        }
        return createGradientBrush(target)
    }

    /**
     * Creates a Compose gradient brush specific to the concrete gradient type.
     *
     * @param target the list of path nodes representing the shape to which the gradient will be applied,
     *  used for calculating gradient coordinates when gradientUnits is "objectBoundingBox"
     * @return a Compose gradient brush configured with the appropriate gradient properties, colour stops,
     *  transformations, and spread method for the specific gradient type
     */
    protected abstract fun createGradientBrush(target: List<PathNodes>): ComposeBrush.Gradient

    /**
     * Resolves the referenced gradient's attributes and stops, producing a copy of THIS
     * gradient's type with merged attributes and the correct stop children.
     *
     * Per SVG spec:
     * - Local attributes override referenced gradient's attributes.
     * - If the referencing gradient has its own stops, they are used. Otherwise, the
     *   referenced gradient's stops are inherited (following the href chain if needed).
     * - The element type of the referencing gradient determines the brush type, even
     *   when the referenced gradient is a different type (cross-type href).
     */
    protected fun resolveHrefChain(href: String): ResolvedGradientData {
        val root = rootParent as SvgRootNode
        val hrefId = href.normalizedId()
        val referenced = checkNotNull(root.gradients[hrefId]) {
            "Referenced gradient not found: $hrefId"
        }
        val mergedAttributes = buildMergedAttributes(referenced, visited = mutableSetOf(hrefId))
        val resolvedChildren = resolveChildren(referenced, visited = mutableSetOf(hrefId))
        return ResolvedGradientData(
            attributes = mergedAttributes,
            children = resolvedChildren,
        )
    }

    private fun buildMergedAttributes(
        referencedGradient: SvgGradient<*>,
        visited: MutableSet<String>,
    ): MutableMap<String, String> {
        val merged = referencedGradient.attributes.toMutableMap()
        merged.remove(SvgUseNode.HREF_ATTR_KEY)
        // Recursively resolve chained href before overlaying local attributes
        val referencedHref = referencedGradient.href
        if (referencedHref != null) {
            val root = rootParent as SvgRootNode
            val chainedId = referencedHref.normalizedId()
            check(visited.add(chainedId)) {
                "Circular gradient reference detected: '$chainedId' was already visited in chain $visited"
            }
            val chainedGradient = checkNotNull(root.gradients[chainedId]) {
                "Chained gradient not found: $chainedId"
            }
            val chainedAttributes = buildMergedAttributes(chainedGradient, visited)
            // Chained attributes fill in defaults (don't override what referenced already defines)
            for ((key, value) in chainedAttributes) {
                if (!merged.containsKey(key)) {
                    merged[key] = value
                }
            }
        }
        // Local attributes always override inherited ones
        for ((key, value) in this@SvgGradient.attributes) {
            if (key != SvgUseNode.HREF_ATTR_KEY) {
                merged[key] = value
            }
        }
        return merged
    }

    /**
     * Resolves stop children: if the referencing gradient has its own stops, use them.
     * Otherwise, walk the href chain to find inherited stops.
     */
    private fun resolveChildren(referencedGradient: SvgGradient<*>, visited: MutableSet<String>): MutableSet<XmlNode> {
        val localStops = children.filterIsInstance<SvgGradientStopNode>()
        if (localStops.isNotEmpty()) {
            return children.toMutableSet()
        }
        val referencedStops = referencedGradient.children.filterIsInstance<SvgGradientStopNode>()
        if (referencedStops.isNotEmpty()) {
            return referencedGradient.children.toMutableSet()
        }
        // Follow the chain deeper
        val referencedHref = referencedGradient.href ?: return mutableSetOf()
        val root = rootParent as SvgRootNode
        val chainedId = referencedHref.normalizedId()
        check(visited.add(chainedId)) {
            "Circular gradient reference detected: '$chainedId' was already visited in chain $visited"
        }
        val chainedGradient = checkNotNull(root.gradients[chainedId]) {
            "Chained gradient not found: $chainedId"
        }
        return resolveChildren(chainedGradient, visited)
    }

    protected data class ResolvedGradientData(
        val attributes: MutableMap<String, String>,
        val children: MutableSet<XmlNode>,
    )
}
