package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.xml.XmlRootNode

abstract class BaseSvgTest {
    val root = SvgElementNode(
        parent = XmlRootNode(children = mutableSetOf()),
        children = mutableSetOf(),
        attributes = mutableMapOf(),
    )
}
