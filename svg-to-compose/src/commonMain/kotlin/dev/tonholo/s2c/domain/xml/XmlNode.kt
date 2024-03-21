package dev.tonholo.s2c.domain.xml

import dev.tonholo.s2c.domain.delegate.attribute

interface XmlNode {
    val tagName: String
}

sealed interface XmlParentNode : XmlNode {
    val children: MutableSet<XmlNode>
}

abstract class XmlChildNode : XmlNode {
    abstract val parent: XmlParentNode
    abstract val attributes: MutableMap<String, String>

    val rootParent: XmlParentNode by lazy {
        var current: XmlChildNode
        var currentParent = parent
        do {
            current = currentParent as XmlChildNode
            currentParent = when (currentParent) {
                is XmlRootNode -> break
                else -> current.parent
            }
        } while (currentParent !is XmlRootNode)

        // XmlRootNode is the Document itself and not an actual node.
        // Because of that, we return the direct child that we use to
        // access its parent to reach the XmlRootNode.
        current as XmlParentNode
    }

    open val id: String? by attribute()

    abstract override fun toString(): String

    open fun toJsString(extra: (StringBuilder.() -> Unit)? = null): String = buildString {
        append("{")
        append("\"name\":\"$tagName\", ")
        append("\"attributes\":${attributes.toJsString()}")
        if (extra != null) {
            append(", ")
            extra()
        }
        append("}")
    }
}

data class XmlRootNode(
    override val tagName: String = "#root",
    override val children: MutableSet<XmlNode>,
) : XmlParentNode {
    override fun toString(): String {
        return "{\"name\":\"$tagName\", children:${children.toJsString()}}"
    }
}

open class XmlElementNode(
    override val parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    override val attributes: MutableMap<String, String>,
    override val tagName: String,
) : XmlChildNode(), XmlParentNode {
    override fun toString(): String = toJsString()

    override fun toJsString(extra: (StringBuilder.() -> Unit)?): String =
        super.toJsString {
            append("children: ${children.toJsString()}, ")
            // Swallow parent toString to avoid infinity toString loop.
            append("parent:\"${parent.tagName}\"")
            if (extra != null) {
                append(", ")
                extra()
            }
        }
}

fun Map<String, String>.toJsString() =
    "[${map { "{\"${it.key}\":\"${it.value}\"}" }.joinToString(",")}]"

fun Set<*>.toJsString() =
    "[${joinToString(",")}]"
