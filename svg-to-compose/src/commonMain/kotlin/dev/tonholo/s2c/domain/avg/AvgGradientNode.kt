package dev.tonholo.s2c.domain.avg

import dev.tonholo.s2c.domain.avg.gradient.AvgGradient
import dev.tonholo.s2c.domain.avg.gradient.AvgGradientTileMode
import dev.tonholo.s2c.domain.avg.gradient.AvgGradientType
import dev.tonholo.s2c.domain.avg.gradient.AvgLinearGradient
import dev.tonholo.s2c.domain.avg.gradient.AvgRadianGradient
import dev.tonholo.s2c.domain.avg.gradient.AvgSweepGradient
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlElementNode
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

class AvgGradientNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : XmlElementNode(parent, children, attributes, tagName = AvgGradient.TAG_NAME),
    AvgNode,
    AvgLinearGradient,
    AvgRadianGradient,
    AvgSweepGradient {
    override val gradientRadius: Float? by attribute(namespace = AvgNode.NAMESPACE)
    override val centerX: Float? by attribute(namespace = AvgNode.NAMESPACE)
    override val centerY: Float? by attribute(namespace = AvgNode.NAMESPACE)
    override val startX: Float? by attribute(namespace = AvgNode.NAMESPACE)
    override val startY: Float? by attribute(namespace = AvgNode.NAMESPACE)
    override val endX: Float? by attribute(namespace = AvgNode.NAMESPACE)
    override val endY: Float? by attribute(namespace = AvgNode.NAMESPACE)
    override val tileMode: AvgGradientTileMode? by attribute<String?, _>(namespace = AvgNode.NAMESPACE) { tileMode ->
        tileMode?.let { AvgGradientTileMode(it) }
    }
    override val startColor: AvgColor? by attribute<String?, _>(namespace = AvgNode.NAMESPACE) {
        it?.let(::AvgColor)
    }
    override val centerColor: AvgColor? by attribute<String?, _>(namespace = AvgNode.NAMESPACE) {
        it?.let(::AvgColor)
    }
    override val endColor: AvgColor? by attribute(namespace = AvgNode.NAMESPACE)
    override val type: AvgGradientType? by attribute<String?, _>(namespace = AvgNode.NAMESPACE) { tileMode ->
        tileMode?.let { AvgGradientType(it) }
    }

    val items: Set<AvgGradientItemNode> by lazy {
        children.filterIsInstance<AvgGradientItemNode>().toSet()
    }

    override fun toString(): String {
        return super.toString()
    }
}


