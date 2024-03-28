package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.svg.gradient.SvgLinearGradient
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

class SvgLinearGradientNode(
    parent: XmlParentNode,
    children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : SvgLinearGradient<SvgLinearGradientNode>(parent, children, attributes), SvgNode {
    override val constructor = ::SvgLinearGradientNode
}
