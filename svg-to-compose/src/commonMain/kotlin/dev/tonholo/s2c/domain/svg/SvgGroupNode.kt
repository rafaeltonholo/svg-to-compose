package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.extensions.firstInstanceOfOrNull
import dev.tonholo.s2c.parser.ImageParser.SvgParser.ComputedRule

class SvgGroupNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : SvgElementNode<SvgGroupNode>(parent, children, attributes, tagName = TAG_NAME), SvgNode {
    override val constructor = ::SvgGroupNode
    val maskId: String? by attribute("mask")
    val filterId: String? by attribute("filter")
    val opacity: Float? by attribute()
    val fillOpacity: Float? by attribute("fill-opacity")
    val clipPath: SvgClipPath? by lazy {
        children.firstInstanceOfOrNull()
    }

    companion object {
        const val TAG_NAME = "g"
    }
}

private fun SvgGroupNode.asNode(
    masks: List<SvgMaskNode>,
    computedRules: List<ComputedRule> = emptyList(),
    minified: Boolean = false,
): ImageVectorNode.Group {
    // Can a svg mask have more than one path?
    // Currently, a group on ImageVector only receives a single PathData as a parameter.
    // Not sure if it would support multiple path tags inside a mask from a svg.
    val clipPath = createGroupClipPath(computedRules, masks, minified)

    return ImageVectorNode.Group(
        params = ImageVectorNode.Group.Params(
            clipPath = clipPath,
        ),
        commands = children
            .mapNotNull { (it as? SvgNode)?.asNodes(computedRules, masks, minified) }
            .flatten(),
        minified = minified,
        transformations = transform?.toTransformations(),
    )
}

fun SvgGroupNode.flatNode(
    masks: List<SvgMaskNode>,
    computedRules: List<ComputedRule> = emptyList(),
    minified: Boolean = false,
): List<ImageVectorNode> {
    val node = asNode(masks, computedRules, minified)
    return if (node.params.isEmpty()) {
        node.commands
    } else {
        listOf(node)
    }
}

/**
 * Creates a group clip path from the given masks.
 *
 * @param masks The list of masks.
 * @param minified Whether to minify the output.
 * @return The group clip path, or null if no mask with the given ID is found.
 */
private fun SvgGroupNode.createGroupClipPath(
    computedRules: List<ComputedRule>,
    masks: List<SvgMaskNode>,
    minified: Boolean,
): ImageVectorNode.NodeWrapper? {
    val mask = masks
        .firstOrNull { it.id == maskId?.normalizedId() }

    val children = mask
        ?.children
        ?.filterIsInstance<SvgNode>()

    val paths = children
        ?.mapNotNull { node -> node.asNodes(computedRules, masks, minified) }
        ?.flatten()
        ?.filterIsInstance<ImageVectorNode.Path>()
        ?.map { it.wrapper }
        ?.reduceOrNull { acc, imageVectorNode ->
            acc + imageVectorNode
        }

    val clipPathAttribute = attributes["clip-path"]
    return when {
        paths != null -> paths
        clipPath != null -> clipPath?.asNodeWrapper(computedRules, minified = true)
        clipPathAttribute?.startsWith("url") == true -> {
            val clipPathId = clipPathAttribute.normalizedId()
            return (rootParent as? SvgRootNode)
                ?.clipPaths[clipPathId]
                ?.asNodeWrapper(computedRules)
        }

        else -> null
    }
}
