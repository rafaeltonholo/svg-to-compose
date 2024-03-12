package dev.tonholo.s2c.parser

import com.fleeksoft.ksoup.nodes.Element
import com.fleeksoft.ksoup.nodes.TextNode
import com.fleeksoft.ksoup.parser.Parser
import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.domain.avg.AvgClipPath
import dev.tonholo.s2c.domain.avg.AvgElementNode
import dev.tonholo.s2c.domain.avg.AvgGroupNode
import dev.tonholo.s2c.domain.avg.AvgPathNode
import dev.tonholo.s2c.domain.svg.SvgCircleNode
import dev.tonholo.s2c.domain.svg.SvgElementNode
import dev.tonholo.s2c.domain.svg.SvgGroupNode
import dev.tonholo.s2c.domain.svg.SvgMaskNode
import dev.tonholo.s2c.domain.svg.SvgPathNode
import dev.tonholo.s2c.domain.svg.SvgRectNode
import dev.tonholo.s2c.domain.xml.XmlElementNode
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.domain.xml.XmlRootNode
import dev.tonholo.s2c.domain.xml.XmlTextElementNode
import dev.tonholo.s2c.logger.verbose
import dev.tonholo.s2c.logger.verboseSection
import dev.tonholo.s2c.logger.warn
import kotlin.time.measureTimedValue

/**
 * Parse an SVG to an Android Vector Drawable
 *
 * @param content The XML content's file
 * @param fileType The root tag from the XML file
 * @return The XML as an object
 */
fun parse(content: String, fileType: FileType): XmlRootNode = verboseSection("Parsing $fileType file") {
    val strippedXml = Regex("\\r?\\n\\s*").replace(content, "")
    val (node, duration) = measureTimedValue {
        val xmlParser = Parser.xmlParser()
        val doc = xmlParser.parseInput(html = strippedXml, baseUri = "")
        val node = doc.getElementsByTag(tagName = fileType.tag)
        if (node.size != 1) {
            error("Not a proper ${fileType.extension.uppercase()} file.")
        }

        val rootNode = node.single()
        traverseSvgTree(rootNode)
    }
    verbose("Parsed ${fileType.extension.uppercase()} within ${duration.inWholeMilliseconds}ms")
    node
}

private fun traverseSvgTree(rootNode: Element): XmlRootNode {
    val root = XmlRootNode(children = mutableSetOf())
    var currentDepth = 0
    val stack = mutableListOf<XmlParentNode>(root)
    var current: XmlParentNode = root
    rootNode.traverse { node, depth ->
        if (currentDepth > depth) {
            repeat(currentDepth - depth) {
                stack.removeLast().also { verbose("removed ${it.name} from stack") }
            }
            stack.lastOrNull()?.let { current = it }
        }

        val parent = current
        val element = when (node) {
            is TextNode -> XmlTextElementNode(parent = parent, value = node.text())

            is Element -> {
                createElement(node, parent, rootTag = rootNode.tagName()).also {
                    if (it is XmlElementNode && node.childrenSize() > 0) {
                        current = it
                        stack += it
                    }
                }
            }

            else -> {
                warn("not supported node '${node.nodeName()}'.")
                null
            }
        }

        element?.let {
            parent.children += it
        }

        currentDepth = depth
    }
    return root
}

private inline fun createElement(
    node: Element,
    parent: XmlParentNode,
    rootTag: String,
): XmlNode = when (rootTag) {
    AvgElementNode.TAG_NAME -> createAvgElement(node, parent)
    SvgElementNode.TAG_NAME -> createSvgElement(node, parent)
    else -> createDefaultElement(node, parent)
}

inline fun createAvgElement(node: Element, parent: XmlParentNode) = when (node.tagName()) {
    AvgElementNode.TAG_NAME -> AvgElementNode(
        parent = parent,
        children = mutableSetOf(),
        attributes = node.attributes().associate { it.key to it.value }.toMutableMap(),
    )

    AvgPathNode.TAG_NAME -> AvgPathNode(
        parent = parent,
        attributes = node.attributes().associate { it.key to it.value }.toMutableMap(),
    )

    AvgClipPath.TAG_NAME -> AvgClipPath(
        parent = parent,
        attributes = node.attributes().associate { it.key to it.value }.toMutableMap(),
    )

    AvgGroupNode.TAG_NAME -> AvgGroupNode(
        parent = parent,
        children = mutableSetOf(),
        attributes = node.attributes().associate { it.key to it.value }.toMutableMap(),
    )

    else -> createDefaultElement(node, parent)
}

inline fun createSvgElement(node: Element, parent: XmlParentNode) = when (node.tagName()) {
    SvgElementNode.TAG_NAME -> SvgElementNode(
        parent = parent,
        children = mutableSetOf(),
        attributes = node.attributes().associate { it.key to it.value }.toMutableMap(),
    )

    SvgPathNode.TAG_NAME -> SvgPathNode(
        parent = parent,
        attributes = node.attributes().associate { it.key to it.value }.toMutableMap(),
    )

    SvgRectNode.TAG_NAME -> SvgRectNode(
        parent = parent,
        attributes = node.attributes().associate { it.key to it.value }.toMutableMap(),
    )

    SvgGroupNode.TAG_NAME -> SvgGroupNode(
        parent = parent,
        children = mutableSetOf(),
        attributes = node.attributes().associate { it.key to it.value }.toMutableMap(),
    )

    SvgMaskNode.TAG_NAME -> SvgMaskNode(
        parent = parent,
        children = mutableSetOf(),
        attributes = node.attributes().associate { it.key to it.value }.toMutableMap(),
    )

    SvgCircleNode.TAG_NAME -> SvgCircleNode(
        parent = parent,
        attributes = node.attributes().associate { it.key to it.value }.toMutableMap(),
    )

    else -> createDefaultElement(node, parent)
}

inline fun createDefaultElement(node: Element, parent: XmlParentNode) = XmlElementNode(
    parent = parent,
    children = mutableSetOf(),
    attributes = node.attributes().associate { it.key to it.value }.toMutableMap(),
    name = node.tagName(),
)
