package dev.tonholo.s2c.domain.avg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.asNodeWrapper
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode

class AvgGroupNode(
    parent: XmlParentNode,
    children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : AvgElementNode(parent, children, attributes, tagName = TAG_NAME), AvgNode {
    val clipPath: AvgClipPath?
        get() = children.firstOrNull { it is AvgClipPath } as? AvgClipPath
    val rotation: Float? by attribute(namespace = AvgNode.NAMESPACE)
    val pivotX: Float? by attribute(namespace = AvgNode.NAMESPACE)
    val pivotY: Float? by attribute(namespace = AvgNode.NAMESPACE)
    val translateX: Float? by attribute(namespace = AvgNode.NAMESPACE)
    val translateY: Float? by attribute(namespace = AvgNode.NAMESPACE)
    val scaleX: Float? by attribute(namespace = AvgNode.NAMESPACE)
    val scaleY: Float? by attribute(namespace = AvgNode.NAMESPACE)

    companion object {
        const val TAG_NAME = "group"
    }
}

fun AvgGroupNode.asNode(
    minified: Boolean,
): ImageVectorNode.Group = ImageVectorNode.Group(
    params = ImageVectorNode.Group.Params(
        clipPath = clipPath?.pathData?.asNodeWrapper(minified),
        rotate = rotation,
        pivotX = pivotX,
        pivotY = pivotY,
        scaleX = scaleX,
        scaleY = scaleY,
        translationX = translateX,
        translationY = translateY,
    ),
    commands = children.mapNotNull { (it as? AvgNode)?.asNode(minified) },
    minified = minified,
)
