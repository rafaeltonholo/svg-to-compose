package dev.tonholo.s2c.domain.xml

import AppConfig
import dev.tonholo.s2c.domain.delegate.attribute
import dev.tonholo.s2c.logger.warn

interface XmlNode {
    val tagName: String
    val id: String?
    val attributes: MutableMap<String, String>

    override fun toString(): String
}

sealed interface XmlParentNode : XmlNode {
    val children: MutableSet<XmlNode>
}

data object XmlPendingParentElement : XmlParentNode {
    override val tagName: String = "pending-parent"
    override val id: String? = null
    override val attributes: MutableMap<String, String> = mutableMapOf()
    override val children: MutableSet<XmlNode> = mutableSetOf()
}

private const val MAX_DEPTH = 5

abstract class XmlChildNode(
    parent: XmlParentNode,
) : XmlNode {
    var parent: XmlParentNode = parent
        private set

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

    override val id: String? by attribute()

    open fun toJsString(extra: (StringBuilder.() -> Unit)? = null): String = buildString {
        append("{")
        append("\"name\":\"$tagName\", ")
        append("\"attributes\":${attributes.toJsString()}, ")
        // Swallow parent toString to avoid infinity toString loop.
        append("\"parent\": \"${buildParentName()}\"")
        if (extra != null) {
            append(", ")
            extra()
        }
        append("}")
    }

    private fun buildParentName(): String {
        return if (AppConfig.debug) {
            var parentTag = parent.tagName
            var currentParent: XmlParentNode? = (parent as? XmlChildNode)?.parent
            var deep = 0
            while (currentParent != null && currentParent !is XmlRootNode && deep++ < MAX_DEPTH) {
                parentTag = "${currentParent.tagName}.$parentTag"
                currentParent = (currentParent as? XmlChildNode)?.parent
            }
            parentTag
        } else {
            parent.tagName
        }
    }

    fun attachParentIfNeeded(parent: XmlParentNode) {
        check(parent != XmlPendingParentElement) {
            "${::attachParentIfNeeded.name}() is supposed to be called with a real ${XmlParentNode::class.simpleName}."
        }

        if (this.parent == XmlPendingParentElement) {
            this.parent = parent
        } else {
            warn(
                "Trying to attach $parent to a node that already has a parent. Current parent: ${this.parent}",
            )
        }
    }
}

data class XmlRootNode(
    override val tagName: String = "#root",
    override val children: MutableSet<XmlNode>,
) : XmlParentNode {
    override val id: String? = null
    override val attributes: MutableMap<String, String> = mutableMapOf()
    override fun toString(): String {
        return "{\"name\":\"$tagName\", \"children\": ${children.toJsString()}}"
    }
}

open class XmlElementNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    override val attributes: MutableMap<String, String>,
    override val tagName: String,
) : XmlChildNode(parent), XmlParentNode {
    override fun toString(): String = toJsString()

    override fun toJsString(extra: (StringBuilder.() -> Unit)?): String =
        super.toJsString {
            append("\"children\": ${children.toJsString()}")
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
