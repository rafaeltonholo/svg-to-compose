package dev.tonholo.s2c.parser

import com.fleeksoft.ksoup.nodes.Attributes
import com.fleeksoft.ksoup.nodes.Comment
import com.fleeksoft.ksoup.nodes.Element
import com.fleeksoft.ksoup.nodes.TextNode
import com.fleeksoft.ksoup.parser.Parser
import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.domain.avg.AvgAttrNode
import dev.tonholo.s2c.domain.avg.AvgClipPath
import dev.tonholo.s2c.domain.avg.AvgGradientItemNode
import dev.tonholo.s2c.domain.avg.AvgGradientNode
import dev.tonholo.s2c.domain.avg.AvgGroupNode
import dev.tonholo.s2c.domain.avg.AvgPathNode
import dev.tonholo.s2c.domain.avg.AvgRootNode
import dev.tonholo.s2c.domain.avg.gradient.AvgGradient
import dev.tonholo.s2c.domain.svg.SvgCircleNode
import dev.tonholo.s2c.domain.svg.SvgClipPath
import dev.tonholo.s2c.domain.svg.SvgDefsNode
import dev.tonholo.s2c.domain.svg.SvgGroupNode
import dev.tonholo.s2c.domain.svg.SvgLinearGradientNode
import dev.tonholo.s2c.domain.svg.SvgMaskNode
import dev.tonholo.s2c.domain.svg.SvgNode
import dev.tonholo.s2c.domain.svg.SvgNode.Companion.normalizedId
import dev.tonholo.s2c.domain.svg.SvgPathNode
import dev.tonholo.s2c.domain.svg.SvgRadialGradientNode
import dev.tonholo.s2c.domain.svg.SvgRectNode
import dev.tonholo.s2c.domain.svg.SvgRootNode
import dev.tonholo.s2c.domain.svg.SvgSymbolNode
import dev.tonholo.s2c.domain.svg.SvgUseNode
import dev.tonholo.s2c.domain.svg.gradient.SvgLinearGradient
import dev.tonholo.s2c.domain.svg.gradient.SvgRadialGradient
import dev.tonholo.s2c.domain.xml.XmlChildNode
import dev.tonholo.s2c.domain.xml.XmlElementNode
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.domain.xml.XmlPendingParentElement
import dev.tonholo.s2c.domain.xml.XmlRootNode
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
    val strippedXml = content
        .replace("\\r?\\n".toRegex(), "")
        .replace("\\s{2,}".toRegex(), " ")
        .replace("> <", "><")
    val (node, duration) = measureTimedValue {
        val xmlParser = Parser.xmlParser()
        val doc = xmlParser.parseInput(html = strippedXml, baseUri = "")
        val node = doc.getElementsByTag(tagName = fileType.tag)
        if (node.size != 1) {
            error("Not a proper ${fileType.extension.uppercase()} file.")
        }

        val rootNode = node.single()
        traverseSvgTree(rootNode, fileType)
    }
    verbose("Parsed ${fileType.extension.uppercase()} within ${duration.inWholeMilliseconds}ms")
    node
}

private fun traverseSvgTree(rootNode: Element, fileType: FileType): XmlRootNode {
    val document = XmlRootNode(children = mutableSetOf())
    var currentDepth = 0
    val stack = ArrayDeque<XmlParentNode>().apply {
        addLast(document)
    }
    var current: XmlParentNode = document
    var root: XmlElementNode? = null
    val elementsWithId = mutableSetOf<XmlNode>()
    val elementsPendingParent = mutableSetOf<XmlNode>()
    rootNode.traverse { node, depth ->
        if (currentDepth > depth) {
            repeat(currentDepth - depth) {
                stack.removeLast().also { verbose("removed ${it.tagName} from stack") }
            }
            stack.lastOrNull()?.let { current = it }
        }

        val parent = current
        val preProcessedElement = elementsPendingParent
            .find { it.id == node.attributes()["id"] }
            ?.apply {
                if (this is XmlChildNode) {
                    attachParentIfNeeded(parent)
                    elementsPendingParent.remove(this)
                }
            }

        val element = preProcessedElement ?: when (node) {
            is Element -> {
                createElement(
                    node = node,
                    parent = parent,
                    fileType = fileType,
                    rootElement = rootNode,
                    elementsWithId = elementsWithId,
                    elementsPendingParent = elementsPendingParent,
                    root = root,
                )
            }

            // Ignored elements
            is TextNode,
            is Comment,
            -> null

            else -> {
                warn("not supported node '${node.nodeName()}'.")
                null
            }
        }

        if (element != null && element is XmlElementNode && node is Element && node.hasChildNodes()) {
            current = element
            stack.addLast(element)
            if (depth == 0 && (element is SvgRootNode || element is AvgRootNode)) {
                root = element
            }
        }
        if (element != null && element is XmlChildNode && !element.id.isNullOrEmpty()) {
            elementsWithId.add(element)
        }

        element?.let {
            parent.children += it
        }

        currentDepth = depth
    }
    return document
}

private inline fun createElement(
    node: Element,
    parent: XmlParentNode,
    fileType: FileType,
    rootElement: Element,
    elementsWithId: MutableSet<XmlNode>,
    elementsPendingParent: MutableSet<XmlNode>,
    root: XmlElementNode?,
): XmlNode = when (fileType) {
    FileType.Avg -> createAvgElement(node.tagName(), node.attributes(), parent)
    FileType.Svg -> createSvgElement(
        nodeName = node.tagName(),
        attributes = node.attributes(),
        parent = parent,
        rootElement = rootElement,
        elementsWithId = elementsWithId,
        elementsPendingParent = elementsPendingParent,
        root = root as? SvgRootNode,
    )
}

inline fun createAvgElement(
    nodeName: String,
    attributes: Attributes,
    parent: XmlParentNode,
): XmlNode {
    return when (nodeName) {
        AvgRootNode.TAG_NAME -> AvgRootNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        AvgPathNode.TAG_NAME -> AvgPathNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        AvgClipPath.TAG_NAME -> AvgClipPath(
            parent = parent,
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        AvgGroupNode.TAG_NAME -> AvgGroupNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        AvgAttrNode.TAG_NAME -> AvgAttrNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        AvgGradient.TAG_NAME -> AvgGradientNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        AvgGradientItemNode.TAG_NAME -> AvgGradientItemNode(
            parent = parent,
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        else -> createDefaultElement(nodeName, attributes, parent)
    }
}

// TODO: consider splitting XmlParser in two classes. One for AVG another for SVG. This file is getting too huge.
inline fun createSvgElement(
    nodeName: String,
    attributes: Attributes,
    parent: XmlParentNode,
    rootElement: Element,
    elementsWithId: MutableSet<XmlNode>,
    elementsPendingParent: MutableSet<XmlNode>,
    root: SvgRootNode?,
): XmlNode {
    return when (nodeName) {
        SvgRootNode.TAG_NAME -> SvgRootNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgPathNode.TAG_NAME -> SvgPathNode(
            parent = parent,
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgRectNode.TAG_NAME -> SvgRectNode(
            parent = parent,
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgGroupNode.TAG_NAME -> SvgGroupNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgMaskNode.TAG_NAME -> SvgMaskNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgCircleNode.TAG_NAME -> SvgCircleNode(
            parent = parent,
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgClipPath.TAG_NAME -> SvgClipPath(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgDefsNode.TAG_NAME -> SvgDefsNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgLinearGradient.TAG_NAME -> SvgLinearGradientNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgRadialGradient.TAG_NAME -> SvgRadialGradientNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgUseNode.TAG_NAME -> SvgUseNode(
            parent = parent,
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
            replacement = findReplacementNode(
                href = attributes[SvgUseNode.HREF_ATTR_KEY],
                rootElement = rootElement,
                useNodeAttrs = attributes,
                elementsWithId = elementsWithId,
                elementsPendingParent = elementsPendingParent,
                root = root,
                parent = parent,
            ),
        ).also { useNode ->
            root?.apply {
                if (useNode.href !in defs) {
                    defs.getOrPut(useNode.href) { useNode }
                }
            }
        }

        SvgSymbolNode.TAG_NAME -> SvgSymbolNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        else -> createDefaultElement(nodeName, attributes, parent)
    }
}

fun findReplacementNode(
    href: String,
    rootElement: Element,
    useNodeAttrs: Attributes,
    elementsWithId: MutableSet<XmlNode>,
    elementsPendingParent: MutableSet<XmlNode>,
    root: SvgRootNode?,
    parent: XmlParentNode,
): SvgGroupNode {
    check(elementsWithId.isEmpty() || (elementsWithId.isNotEmpty() && elementsWithId.first() is SvgNode)) {
        "${::findReplacementNode.name}() should only be called in the context of Svg parsing."
    }

    val processedNode = elementsWithId
        .find {
            with(SvgNode) {
                // As we are processing SVGs, it is safe to say this element is an SvgNode.
                val id = it.id
                requireNotNull(id) {
                    buildString {
                        appendLine("Something is not correct. All elements in this list must have ID.")
                        appendLine("Element: $it")
                    }
                }
                href.normalizedId() == id.normalizedId()
            }
        } as? SvgNode

    val replacement = processedNode
        ?: rootElement.getElementById(href.normalizedId())?.let { node ->
            createSvgElement(
                nodeName = node.tagName(),
                attributes = node.attributes(),
                parent = XmlPendingParentElement,
                rootElement = rootElement,
                elementsWithId = elementsWithId,
                elementsPendingParent = elementsPendingParent,
                root = root,
            ).also {
                elementsWithId += it
                elementsPendingParent += it
            } as SvgNode
        } ?: error("Missing element with id '$href' on SVG tree.")

    return SvgUseNode.createReplacementGroupNode(useNodeAttrs, replacement, parent)
}

inline fun createDefaultElement(
    nodeName: String,
    attributes: Attributes,
    parent: XmlParentNode,
) = XmlElementNode(
    parent = parent,
    children = mutableSetOf(),
    attributes = attributes.associate { it.key to it.value }.toMutableMap(),
    tagName = nodeName,
)
