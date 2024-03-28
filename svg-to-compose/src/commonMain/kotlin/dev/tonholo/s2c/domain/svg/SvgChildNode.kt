package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.svg.transform.SvgTransform
import dev.tonholo.s2c.domain.xml.XmlChildNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

typealias SvgChildNodeConstructorFn<T> = (
    parent: XmlParentNode,
    attributes: MutableMap<String, String>,
) -> T

abstract class SvgChildNode<out T>(parent: XmlParentNode) : XmlChildNode(parent), SvgNode
    where T : SvgNode, T : XmlChildNode {
    protected abstract val constructor: SvgChildNodeConstructorFn<out T>
    override val transform: SvgTransform? by lazy {
        stackedTransform(parent)
    }

    override fun toJsString(extra: (StringBuilder.() -> Unit)?): String {
        return super.toJsString {
            append("\"stackedTransform\": \"$transform\"")
            if (extra != null) {
                append(", ")
                extra()
            }
        }
    }

    open fun copy(parent: XmlParentNode? = null, attributes: Map<String, String>? = null): T =
        constructor(
            parent ?: this.parent,
            (attributes ?: this.attributes).toMutableMap(),
        )
}
