package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.svg.transform.SvgTransform
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
    protected abstract val constructor: SvgElementNodeConstructorFn<T>
    override val transform: SvgTransform? by lazy {
        stackedTransform(parent)
    }

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

    fun resolveUseNodes() {
        val children = mutableSetOf<XmlNode>().apply {
            addAll(children)
        }
        val resolvedChildren = buildSet {
            for (child in children) {
                when (child) {
                    // remove <use> nodes from the tree since there is no utility anymore.
                    is SvgDefsNode -> add(
                        child.copy(
                            children = child
                                .children
                                .asSequence()
                                .filterIsInstance<SvgNode>()
                                .filterNot { node -> node is SvgUseNode }
                                .toMutableSet(),
                        ),
                    )

                    is SvgUseNode -> {
                        val replacement = child.resolve()
                        replacement.resolveUseNodes()
                        add(replacement)
                    }

                    is SvgElementNode<*> -> {
                        child.resolveUseNodes()
                        add(child)
                    }

                    else -> add(child)
                }
            }
        }
        this.children.apply {
            clear()
            addAll(resolvedChildren)
        }
    }
}

fun <T> SvgElementNode<T>.asNodes(
    minified: Boolean,
): List<ImageVectorNode>
    where T : SvgNode, T : XmlParentNode {
    val defsMasks = children
        .filterIsInstance<SvgDefsNode>()
        .flatMap { defs -> defs.children.filterIsInstance<SvgMaskNode>() }
    val masks = defsMasks + children.filterIsInstance<SvgMaskNode>()
    return children
        .asSequence()
        .mapNotNull { node -> (node as? SvgNode)?.asNodes(masks, minified) }
        .flatten()
        .toList()
}
