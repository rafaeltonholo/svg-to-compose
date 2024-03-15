package dev.tonholo.s2c.domain.xml

interface XmlNode {
    val name: String
}

sealed interface XmlParentNode : XmlNode {
    val children: MutableSet<XmlNode>
}

sealed interface XmlChildNode : XmlNode {
    val parent: XmlParentNode
}

interface XmlChildNodeWithAttributes : XmlChildNode {
    val attributes: MutableMap<String, String>

    override fun toString(): String

    fun toJsString(): String = buildString {
        append("{")
        append("\"name\":\"$name\", ")
        append("\"attributes\":$attributes.toJsString()")
        append("}")
    }
}

data class XmlRootNode(
    override val name: String = "#root",
    override val children: MutableSet<XmlNode>,
) : XmlParentNode {
    override fun toString(): String {
        return "{\"name\":\"$name\", children:${children.toJsString()}}"
    }
}

open class XmlElementNode(
    override val parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    override val attributes: MutableMap<String, String>,
    override val name: String,
) : XmlChildNodeWithAttributes, XmlParentNode {
    override fun toString(): String = buildString {
        append("{name:\"$name\",")
        append(" attributes: ${attributes.toJsString()}, ")
        append("children: ${children.toJsString()}, ")
        // Swallow parent toString to avoid infinity toString loop.
        append("parent:\"${parent.name}\"")
        append("}")
    }
}

fun Map<String, String>.toJsString() =
    "[${map { "{\"${it.key}\":\"${it.value}\"}" }.joinToString(",")}]"

fun Set<*>.toJsString() =
    "[${joinToString(",")}]"
