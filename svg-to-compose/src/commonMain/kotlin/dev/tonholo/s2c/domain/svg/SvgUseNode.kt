package dev.tonholo.s2c.domain.svg

import com.fleeksoft.ksoup.nodes.Attributes
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.svg.transform.SvgTransform
import dev.tonholo.s2c.domain.xml.XmlParentNode

class SvgUseNode(
    parent: XmlParentNode,
    override val attributes: MutableMap<String, String>,
    val replacement: SvgGroupNode,
) : SvgPresentationNode<SvgUseNode>(parent) {
    override val constructor
        get() = error("SvgUseNode can't use constructor property.")

    override val tagName: String = TAG_NAME
    val x: Float by attribute<SvgLength, Float>(defaultValue = 0f) { x ->
        val root = rootParent as SvgRootNode
        val baseDimension = root.viewportWidth
        x.toFloat(baseDimension)
    }
    val y: Float by attribute<SvgLength, Float>(defaultValue = 0f) { y ->
        val root = rootParent as SvgRootNode
        val baseDimension = root.viewportHeight
        y.toFloat(baseDimension)
    }
    val width: Float by attribute<SvgLength, Float> { width ->
        val root = rootParent as SvgRootNode
        val baseDimension = root.viewportWidth
        width.toFloatOrNull(baseDimension) ?: error("Invalid width '$width'")
    }
    val height: Float by attribute<SvgLength, Float> { height ->
        val root = rootParent as SvgRootNode
        val baseDimension = root.viewportHeight
        height.toFloatOrNull(baseDimension) ?: error("Invalid height '$height'")
    }
    val href: String by attribute(namespace = SvgNode.XLINK_NS)
    override val transform: SvgTransform? by attribute<String?, SvgTransform?> {
        it?.let(::SvgTransform)
    }

    override fun toString(): String = toJsString {
        append("\"replacement\": $replacement")
    }

    override fun copy(parent: XmlParentNode?, attributes: Map<String, String>?): SvgUseNode =
        SvgUseNode(
            parent = parent ?: this.parent,
            attributes = attributes?.toMutableMap() ?: this.attributes.toMutableMap(),
            replacement = replacement,
        )

    fun resolve(): SvgGroupNode {
        val parent = parent
        return replacement.let { replacement ->
            val newChildren = buildSet {
                replacement.children
                    .forEach { child ->
                        when (child) {
                            is SvgChildNode<*> -> add(child.copy(parent = replacement))
                            is SvgElementNode<*> -> add(child.copy(parent = replacement))
                            else -> Unit
                        }
                    }
            }

            replacement.copy(parent = parent, children = newChildren.toMutableSet())
        }
    }

    companion object {
        const val TAG_NAME = "use"
        const val HREF_ATTR_KEY = "${SvgNode.XLINK_NS}:href"

        /**
         * Creates the [SvgGroupNode] that wrap the [replacement] as per
         * SVG guidelines.
         *
         * @see [Svg Use Element](https://www.w3.org/TR/SVG11/single-page.html#struct-UseElement)
         */
        fun createReplacementGroupNode(
            useNodeAttrs: Attributes,
            replacement: SvgNode,
            parent: XmlParentNode,
        ): SvgGroupNode {
            var childReplacement = replacement
            val excludedAttributes = setOf(
                SvgNode.ATTR_X,
                SvgNode.ATTR_Y,
                SvgNode.ATTR_WIDTH,
                SvgNode.ATTR_HEIGHT,
                HREF_ATTR_KEY,
            )
            val useNodeX = useNodeAttrs[SvgNode.ATTR_X].toFloatOrNull()
            val useNodeY = useNodeAttrs[SvgNode.ATTR_Y].toFloatOrNull()
            val useNodeWidth = useNodeAttrs.attribute(SvgNode.ATTR_WIDTH)?.value
            val useNodeHeight = useNodeAttrs.attribute(SvgNode.ATTR_HEIGHT)?.value

            val groupAttributes = useNodeAttrs
                .filter {
                    it.key !in excludedAttributes
                }
                .associate { it.key to it.value }
                .toMutableMap()


            when (childReplacement) {
                // When the 'use' element is a 'symbol', convert it to
                // an 'svg' element and set 'width' and 'height' attributes
                // with 100% as default value.
                is SvgSymbolNode -> {
                    childReplacement = SvgRootNode(
                        attributes = childReplacement.attributes.apply {
                            put(SvgNode.ATTR_WIDTH, useNodeWidth ?: "100%")
                            put(SvgNode.ATTR_HEIGHT, useNodeHeight ?: "100%")
                        },
                        parent = childReplacement.parent,
                        children = childReplacement.children,
                    )
                }

                // When the 'use' element is an 'svg', override 'width' and 'height'
                // attributes with 'use's' 'width' and 'height' if present.
                is SvgRootNode -> {
                    useNodeWidth?.let { childReplacement.attributes[SvgNode.ATTR_WIDTH] = it }
                    useNodeHeight?.let { childReplacement.attributes[SvgNode.ATTR_HEIGHT] = it }
                }

                else -> Unit // no-op
            }

            // The generated 'group' should add a 'translate' at the end of the
            // 'transform' attribute if 'x' and/or 'y' is specified on 'use' element.
            groupAttributes.apply {
                if ((useNodeX != null && useNodeX != 0f) || (useNodeY != null && useNodeY != 0f)) {
                    val transform = get(SvgNode.ATTR_TRANSFORM)
                        ?.let { "$it " }
                        .orEmpty() +
                        "translate(${useNodeX ?: 0}, ${useNodeY ?: 0})"

                    put(SvgNode.ATTR_TRANSFORM, transform)
                }
            }

            return SvgGroupNode(
                parent = parent,
                children = mutableSetOf(childReplacement),
                attributes = groupAttributes,
            )
        }
    }
}
