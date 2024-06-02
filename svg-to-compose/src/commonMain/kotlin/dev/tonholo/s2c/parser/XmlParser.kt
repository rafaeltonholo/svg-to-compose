package dev.tonholo.s2c.parser

import com.fleeksoft.ksoup.nodes.Attributes
import com.fleeksoft.ksoup.nodes.Comment
import com.fleeksoft.ksoup.nodes.Element
import com.fleeksoft.ksoup.nodes.Node
import com.fleeksoft.ksoup.nodes.TextNode
import com.fleeksoft.ksoup.parser.Parser
import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.domain.xml.XmlChildNode
import dev.tonholo.s2c.domain.xml.XmlElementNode
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlParentNode
import dev.tonholo.s2c.domain.xml.XmlRootNode
import dev.tonholo.s2c.logger.verbose
import dev.tonholo.s2c.logger.verboseSection
import dev.tonholo.s2c.logger.warn
import kotlin.time.measureTimedValue

/**
 * Abstract class representing an XML parser.
 *
 * @property fileType The file type of the XML being parsed.
 * @property root The root element of the parsed XML.
 * @property rootNode The root element of the XML as an Element object.
 * @property elementsWithId A set of elements with an ID attribute.
 * @property elementsPendingParent A set of elements that have not yet been assigned
 * a parent.
 */
abstract class XmlParser {
    companion object {
        private val parsers = setOf(
            AvgParser(),
            SvgParser(),
        )

        /**
         * Parses the given XML content into an [XmlRootNode] using the appropriate
         * parser for the given file type.
         *
         * @param content The XML content to parse.
         * @param fileType The type of the XML file.
         * @return An [XmlRootNode] representing the parsed XML content.
         * @throws IllegalStateException If no parser is found for the given file type.
         */
        fun parse(content: String, fileType: FileType): XmlRootNode = parsers
            .firstOrNull { it.accept(fileType) }
            ?.parse(content)
            ?: error("No parser for $fileType")
    }

    protected abstract val fileType: FileType
    protected var root: XmlElementNode? = null
    protected lateinit var rootNode: Element
    protected val elementsWithId = mutableSetOf<XmlNode>()
    protected val elementsPendingParent = mutableSetOf<XmlNode>()

    /**
     * Checks if the parser accepts the given file type.
     *
     * @param fileType The file type to check.
     * @return True if the parser accepts the file type, false otherwise.
     */
    fun accept(fileType: FileType): Boolean =
        this.fileType == fileType

    /**
     * Parse a XML string content to an [XmlRootNode]
     *
     * @param content The XML content's file
     * @return The XML as an object
     */
    fun parse(content: String): XmlRootNode =
        verboseSection("Parsing $fileType file") {
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
                traverseXmlTree(rootNode)
            }
            verbose("Parsed ${fileType.extension.uppercase()} within ${duration.inWholeMilliseconds}ms")
            node
        }

    /**
     * Creates a default [XmlElementNode] instance with the given parameters.
     * This method is used when no specific element type is provided by the parser.
     *
     * @param nodeName The name of the XML element.
     * @param attributes The attributes of the XML element.
     * @param parent The parent node of the XML element.
     * @return A new [XmlElementNode] instance.
     */
    protected fun createDefaultElement(
        nodeName: String,
        attributes: Attributes,
        parent: XmlParentNode,
    ) = XmlElementNode(
        parent = parent,
        children = mutableSetOf(),
        attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        tagName = nodeName,
    )

    /**
     * Creates an [XmlNode] instance with the given parameters.
     * @param node The XML node to be converted.
     * @param parent The parent node of the XML node.
     */
    protected abstract fun createElement(
        node: Element,
        parent: XmlParentNode,
    ): XmlNode

    /**
     * Checks if the given XML node is the root node of the XML document.
     */
    protected abstract fun XmlNode.isRootNode(): Boolean

    /**
     * Returns a pre-processed Element node.
     *
     * @param node The Element node to pre-process.
     * @param parent The parent node of the Element node.
     * @return A pre-processed Element node, or null if no pre-processing is performed.
     */
    protected open fun getPreProcessedElement(node: Node, parent: XmlParentNode): XmlNode? = null

    /**
     * Traverses the given XML tree and returns the root node of the parsed XML.
     *
     * This method traverses the XML tree using a depth-first search algorithm.
     * It maintains a stack of parent nodes and a current depth to keep track of the current
     * location in the tree.
     * For each node in the tree, it performs the following steps:
     *
     * 1. If the current depth is greater than the depth of the current node, it pops parent
     * nodes from the stack until the current depth matches the depth of the current node.
     * 2. It processes the current node and creates a corresponding XmlNode object.
     * 3. If the current node is an element node and has child nodes, it pushes the current
     * node onto the stack and sets the current node to the newly created element node.
     * 4. If the current node is a child node and has an ID, it adds the node to a list of
     * elements with IDs.
     * 5. It adds the newly created XmlNode object to the parent node's list of children.
     * 6. It updates the current depth to the depth of the current node.
     *
     * Finally, the method returns the root node of the parsed XML.
     *
     * @param rootNode The root element of the XML tree.
     * @return The root node of the parsed XML.
     */
    private fun traverseXmlTree(rootNode: Element): XmlRootNode {
        this.rootNode = rootNode
        elementsWithId.clear()
        elementsPendingParent.clear()
        root = null

        val document = XmlRootNode(children = mutableSetOf())
        var currentDepth = 0
        val stack = ArrayDeque<XmlParentNode>().apply {
            addLast(document)
        }
        var current: XmlParentNode = document
        rootNode.traverse { node, depth ->
            if (currentDepth > depth) {
                repeat(currentDepth - depth) {
                    stack.removeLast().also { verbose("removed ${it.tagName} from stack") }
                }
                stack.lastOrNull()?.let { current = it }
            }

            val parent = current
            val element = processElement(node, parent)

            if (element != null && element is XmlElementNode && node is Element && node.hasChildNodes()) {
                current = element
                stack.addLast(element)
                if (depth == 0 && element.isRootNode()) {
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

    /**
     * Processes an XML node into an XmlNode object.
     *
     * @param node The XML node to process.
     * @param parent The parent node of the processed node.
     * @return The processed XmlNode object, or null if the node is ignored or not supported.
     */
    private fun processElement(
        node: Node,
        parent: XmlParentNode
    ): XmlNode? {
        val preProcessedElement = getPreProcessedElement(node, parent)

        val element = preProcessedElement ?: when (node) {
            is Element -> {
                createElement(
                    node = node,
                    parent = parent,
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
        return element
    }
}
