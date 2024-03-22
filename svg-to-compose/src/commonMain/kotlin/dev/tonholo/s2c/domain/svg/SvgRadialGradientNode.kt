package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.svg.gradient.SvgRadialGradient
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

class SvgRadialGradientNode(
    parent: XmlParentNode,
    children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : SvgRadialGradient(parent, children, attributes), SvgNode
