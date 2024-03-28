package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.xml.XmlElementNode
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

typealias SvgElementNodeConstructorFn<T> = (
    parent: XmlParentNode,
    children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) -> T

abstract class SvgElementNode<out T>(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    override val attributes: MutableMap<String, String>,
    override val tagName: String,
) : XmlElementNode(parent, children, attributes, tagName), SvgNode
    where T : SvgNode, T : XmlParentNode {
    protected abstract val constructor: SvgElementNodeConstructorFn<out T>

    open fun copy(
        parent: XmlParentNode? = null,
        children: MutableSet<SvgNode>? = null,
        attributes: Map<String, String>? = null,
    ): T {
        val currentChildren = (children ?: this.children.filterIsInstance<SvgNode>())
        return constructor(
            parent ?: this.parent,
            mutableSetOf(),
            attributes?.toMutableMap() ?: this.attributes.toMutableMap(),
        ).apply {
            this.children.addAll(
                currentChildren.map {
                    when (it) {
                        is SvgChildNode<*> -> it.copy(parent = this)
                        is SvgElementNode<*> -> it.copy(parent = this)
                        else -> it
                    }
                }
            )
        }
    }
}
