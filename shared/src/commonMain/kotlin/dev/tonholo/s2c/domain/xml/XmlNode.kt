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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as XmlElementNode

        if (children != other.children) return false
        if (attributes != other.attributes) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = children.hashCode()
        result = 31 * result + attributes.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}

data class XmlTextElementNode(
    override val parent: XmlParentNode,
    override val name: String = "#text",
    val value: String,
) : XmlChildNode {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is XmlTextElementNode) return false

        if (name != other.name) return false
        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

fun Map<String, String>.toJsString() =
    "[${map { "{\"${it.key}\":\"${it.value}\"}" }.joinToString(",")}]"

fun Set<*>.toJsString() =
    "[${joinToString(",")}]"
