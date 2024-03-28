package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathCommand
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.builder.pathNode
import dev.tonholo.s2c.domain.compose.toBrush
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.extensions.toLengthFloat
import dev.tonholo.s2c.logger.warn
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class SvgCircleNode(
    parent: XmlParentNode,
    attributes: MutableMap<String, String>,
) : SvgGraphicNode<SvgCircleNode>(parent, attributes, TAG_NAME), SvgNode {
    override val constructor = ::SvgCircleNode
    val cx: Float by attribute<SvgLength, Float>(defaultValue = 0.0f) { cx ->
        val root = rootParent as SvgRootNode
        val baseDimension = root.viewportWidth
        cx.toFloat(baseDimension)
    }
    val cy: Float by attribute<SvgLength, Float>(defaultValue = 0.0f) { cy ->
        val root = rootParent as SvgRootNode
        val baseDimension = root.viewportHeight
        cy.toFloat(baseDimension)
    }
    val radius: Float by attribute<String, Float>(name = "r", defaultValue = 0.0f) { radius ->
        val root = rootParent as SvgRootNode
        radius.toLengthFloat(width = root.viewportWidth, root.viewportHeight)
    }

    companion object {
        const val TAG_NAME = "circle"
    }
}

fun SvgCircleNode.asNode(
    minified: Boolean = false,
): ImageVectorNode = when {
    strokeDashArray != null -> {
        warn(
            "Parsing a `stroke-dasharray` attribute is experimental and " +
                "might differ a little from the original."
        )
        createDashedCircle(minified)
    }

    else -> createSimpleRect(minified)
}

private fun SvgCircleNode.buildNormalizedPath(): String = buildString {
    append("<circle ")
    append("cx=\"$cx\" ")
    append("cy=\"$cy\" ")
    append("radius=\"$radius\" ")
    append(graphicNodeParams())
    append("/>")
}

/**
 * Creates a simplified representation of a circle.
 *
 * @param minified A [Boolean] parameter that specifies whether the output
 * [ImageVectorNode.Path] should be minified. If `true`, the output will not
 * include any spaces or newlines.
 * @param radius The radius of the circle.
 * @param override Parameters which are intended to override the default parameters.
 * If not provided, default parameters are considered.
 * @return An [ImageVectorNode.Path] which represents the SVG circle.
 */
private fun SvgCircleNode.createSimpleRect(
    minified: Boolean,
    radius: Float = this.radius,
    override: ImageVectorNode.Path.Params? = null,
) = ImageVectorNode.Path(
    params = override ?: ImageVectorNode.Path.Params(
        fill = fill.orDefault().value.toBrush(), // Circle has a filling by default.
        fillAlpha = fillOpacity,
        pathFillType = fillRule,
        stroke = stroke?.value?.toBrush(),
        strokeAlpha = strokeOpacity,
        strokeLineCap = strokeLineCap,
        strokeLineJoin = strokeLineJoin,
        strokeMiterLimit = strokeMiterLimit,
        strokeLineWidth = strokeWidth ?: stroke?.let { 1f },
    ),
    wrapper = ImageVectorNode.NodeWrapper(
        normalizedPath = buildNormalizedPath(),
        nodes = @Suppress("MagicNumber") listOf(
            pathNode(command = PathCommand.MoveTo) {
                args(cx, cy)
                this.minified = minified
            },
            pathNode(command = PathCommand.MoveTo) {
                args(-radius, 0)
                isRelative = true
                this.minified = minified
            },
            pathNode(command = PathCommand.ArcTo) {
                args(radius, radius, 0f, true, true, 2 * radius, 0)
                isRelative = true
                this.minified = minified
            },
            pathNode(command = PathCommand.ArcTo) {
                args(radius, radius, 0f, true, true, -2 * radius, 0)
                isRelative = true
                this.minified = minified
                close = true
            },
        )
    ),
    minified = minified,
)

/**
 * Create a dashed circle from a given circle SVG node.
 *
 * Dashed circles in SVG are not supported nativaly on Android. Instead,
 * two paths are created for each dashed circle.
 *
 * The first path corresponds to the filling of the circle, in case it
 * has a fill.
 *
 * The second path corresponds to the dashes of the circle. Stroke parameters
 * are repurposed as fill parameters, as the "dash" is treated as a path.
 *
 * @param minified A [Boolean] parameter that specifies whether the output
 * [ImageVectorNode.Group] should be minified. If `true`, the output will not
 * include any spaces or newlines.
 * @return an [ImageVectorNode.Group] that represents the dashed circle.
 * The returned group includes a list of commands to draw the dashed circle
 * and its fill if any.
 */
private fun SvgCircleNode.createDashedCircle(minified: Boolean): ImageVectorNode = ImageVectorNode.Group(
    clipPath = null,
    commands = buildList {
        val strokeDashArray = strokeDashArray ?: error("stroke-dasharray should not be null in this case.")
        val fill = fill
        // dashed circle is not supported, and because of that, we create two paths for each dashed circle.
        // the 1st path is for the filling, in case it has one.
        if (fill != null) {
            add(
                createSimpleRect(
                    minified,
                    // The radius is overridden, taking in consideration the size of the stroke width
                    radius = radius - ((strokeWidth ?: 1f) / 2f),
                    override = ImageVectorNode.Path.Params(
                        fill = fill.value.toBrush(),
                        fillAlpha = fillOpacity,
                        pathFillType = fillRule,
                    )
                ),
            )
        }
        // 2nd path is the dashes.
        add(
            ImageVectorNode.Path(
                params = ImageVectorNode.Path.Params(
                    // as the dash is a path now, all the stroke parameters
                    // are used inside the fill parameters instead.
                    fill = stroke.orDefault().value.toBrush(),
                    fillAlpha = strokeOpacity,
                ),
                wrapper = ImageVectorNode.NodeWrapper(
                    normalizedPath = buildNormalizedPath(),
                    nodes = createDashedCirclePath(
                        dashes = strokeDashArray.dashesAndGaps,
                        isMinified = minified,
                    ),
                ),
                minified = minified,
            ),
        )
    },
    minified = minified,
)

/**
 * Creates a list of PathNodes representing a dashed circle.
 *
 * @param dashes An array of integers representing the lengths of
 * dashes and gaps.
 * @param isMinified A boolean indicating whether to minify the path
 * nodes, removing the comments and inlining the path parameters.
 * @return A list of [PathNodes] representing the dashed circle.
 */
private fun SvgCircleNode.createDashedCirclePath(dashes: IntArray, isMinified: Boolean): List<PathNodes> {
    val radius = radius
    val circumference = 2 * PI.toFloat() * radius
    val dashSum = dashes.sum()
    // Calculate the number of segments required to cover the circumference with the given dashes
    val segments = (circumference / dashSum).roundToInt() * dashes.size
    val ratios = dashes.map { it / circumference }
    val strokeWidth = strokeWidth ?: 1f

    // inner "circle" radius
    val innerRadius = radius - strokeWidth
    // starts from the first angle (theta) of the circle.
    var theta = 0f

    return buildList {
        repeat(segments) { index ->
            // Get the angle of the segment (delta)
            val delta = ratios[index % ratios.size] * 2 * PI.toFloat()

            if (index % 2 != 0) {
                // Should be a gap.
                // Sum the theta and move to next segment position
                theta += delta
                return@repeat // continue
            }

            // We need to draw two arcs in order to create the dash shape.

            // The outer arc uses the original radius of the circle.
            // These are the points on the circumference that the outer arc
            // starts and ends
            val startPoint = circumferencePointFromAngle(
                cx,
                cy,
                radius,
                angle = theta,
            )
            val endPoint = circumferencePointFromAngle(cx, cy, radius, angle = theta + delta)

            // The inner arc should take in consideration the `stroke-width`
            // property to draw, so the radius removes it.
            // These are the points on the circumference, taking in consideration the
            // `stroke-width` that the inner arc starts and ends.
            val innerRadiusStartPoint = circumferencePointFromAngle(
                cx,
                cy,
                radius = innerRadius,
                angle = theta,
            )
            val innerRadiusEndPoint = circumferencePointFromAngle(
                cx,
                cy,
                radius = innerRadius,
                angle = theta + delta,
            )

            // Add the current delta to theta for the next segment
            theta += delta

            // We draw the dash on clockwise mode.
            // Bottom-Left -> Top-Left -> Top-Right -> Bottom-Right -> Bottom-Left.
            // Move the pen to the initial position of the inner arc.
            // Initial Position -> Bottom-Left.
            add(
                pathNode(command = PathCommand.MoveTo) {
                    args(innerRadiusStartPoint.x, innerRadiusStartPoint.y)
                    minified = isMinified
                },
            )

            // Draw a Line to the starting position of the outer arc.
            // Bottom-Left -> Top-Left
            add(
                pathNode(command = PathCommand.LineTo) {
                    args(startPoint.x, startPoint.y)
                    minified = isMinified
                },
            )

            // Draw the outer arc.
            // As this is an outer arc, the sweep flat should be set to `true`.
            // Top-Left -> Top-Right.
            add(
                pathNode(command = PathCommand.ArcTo) {
                    args(
                        radius,
                        radius,
                        /* x-axis rotation */
                        0,
                        /* large arc flag */
                        false,
                        /* sweep flag */
                        true,
                        endPoint.x,
                        endPoint.y
                    )
                    minified = isMinified
                },
            )

            // Draw a line to the end of the inner arc.
            // Top-Right -> Bottom-Right.
            add(
                pathNode(command = PathCommand.LineTo) {
                    args(innerRadiusEndPoint.x, innerRadiusEndPoint.y)
                    minified = isMinified
                },
            )

            // Draw the inner arc and close the path.
            // As this is an inner arc, the sweep flat should be set to `false`.
            // Bottom-Right -> Bottom-Left.
            add(
                pathNode(command = PathCommand.ArcTo) {
                    args(
                        innerRadius,
                        innerRadius,
                        /* x-axis rotation */
                        0,
                        /* large arc flag */
                        false,
                        /* sweep flag */
                        false,
                        innerRadiusStartPoint.x,
                        innerRadiusStartPoint.y,
                    )
                    minified = isMinified
                    close = true
                },
            )
        }
    }
}

private data class Point(val x: Float, val y: Float)

/**
 * Calculates the coordinates of a point on the circumference
 * of a circle based on a provided angle.
 *
 * @param centerX The center x-coordinate of the circle.
 * @param centerY The center y-coordinate of the circle.
 * @param radius The radius of the circle.
 * @param angle The angle (in radians) at which the point lies
 * on the circumference of the circle.
 * @return A [Point] object which represents the coordinates of
 * the point on the circumference.
 */
private fun circumferencePointFromAngle(
    centerX: Float,
    centerY: Float,
    radius: Float,
    angle: Float,
): Point = Point(
    x = centerX + radius * cos(angle),
    y = centerY + radius * sin(angle),
)
