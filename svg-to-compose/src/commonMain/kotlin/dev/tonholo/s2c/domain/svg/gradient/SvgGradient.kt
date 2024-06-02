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
import dev.tonholo.s2c.domain.svg.transform.SvgTransform
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.geom.bounds.boundingBox
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
    val spreadMethod: SvgGradientSpreadMethod by attribute<String, _>(
        defaultValue = SvgGradientSpreadMethod.Pad,
    ) { spreadMethod ->
        spreadMethod.let(SvgGradientSpreadMethod::invoke)
    }
    val href: String? by attribute(name = "xlink:href")

    val colorStops: Pair<List<SvgColor>, List<Float>>
        get() {
            if (children.isEmpty()) {
                return emptyList<SvgColor>() to emptyList()
            }

            return children
                .filterIsInstance<SvgGradientStopNode>()
                .map { it.gradientColor to it.offset }
                .unzip()
        }

    internal inline fun calculateGradientXCoordinate(
        length: SvgLength,
        target: List<PathNodes> = emptyList(),
    ): Float {
        val root = rootParent as SvgRootNode
        return if (gradientUnits == "objectBoundingBox") {
            check(target.isNotEmpty())
            val boundingBox = target.boundingBox()
            length.toFloat(boundingBox.width.toFloat()) + boundingBox.x.toFloat()
        } else {
            val baseDimension = root.viewportWidth
            length.toFloat(baseDimension)
        }
    }

    internal inline fun calculateGradientYCoordinate(
        length: SvgLength,
        target: List<PathNodes> = emptyList(),
    ): Float {
        val root = rootParent as SvgRootNode
        return if (gradientUnits == "objectBoundingBox") {
            check(target.isNotEmpty())
            val boundingBox = target.boundingBox()
            length.toFloat(boundingBox.height.toFloat()) + boundingBox.y.toFloat()
        } else {
            val baseDimension = root.viewportHeight
            length.toFloat(baseDimension)
        }
    }

    internal inline fun calculateGradientXYCoordinate(
        length: SvgLength,
        target: List<PathNodes> = emptyList(),
    ): Float {
        val root = rootParent as SvgRootNode
        return if (gradientUnits == "objectBoundingBox") {
            check(target.isNotEmpty())
            val boundingBox = target.boundingBox()
            length.toFloat(max(boundingBox.width, boundingBox.height).toFloat()) +
                max(boundingBox.x, boundingBox.y).toFloat()
        } else {
            val baseDimension = max(root.viewportWidth, root.viewportHeight)
            length.toFloat(baseDimension)
        }
    }

    abstract fun toBrush(
        target: List<PathNodes>,
    ): ComposeBrush.Gradient
}
