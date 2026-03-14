package dev.tonholo.s2c.domain

import dev.tonholo.s2c.domain.svg.SvgPathNode
import dev.tonholo.s2c.domain.svg.SvgRootNode
import dev.tonholo.s2c.domain.svg.asNode
import dev.tonholo.s2c.domain.xml.XmlRootNode
import dev.tonholo.s2c.error.ExitProgramException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs

class ImageVectorNodeTest {
    private val root = SvgRootNode(
        parent = XmlRootNode(children = mutableSetOf()),
        children = mutableSetOf(),
        attributes = mutableMapOf(),
    )

    @Test
    fun `ensure for any subsequent coordinate pair after MoveTo is parsed to LineTo`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf(
                "d" to "M85.122 64.795 -12.34 88.6",
            ),
        )
        // Act
        val node = path.asNode(minified = false) as ImageVectorNode.Path
        val nodes = node.wrapper.nodes

        // Assert
        assertEquals(expected = 2, actual = nodes.size)
        assertIs<PathNodes.MoveTo>(nodes[0])
        assertIs<PathNodes.LineTo>(nodes[1])
    }

    @Test
    fun `ensure repeat last command when no command letter was found`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf(
                "d" to "M85.122 64.795 -12.34 88.6 -32.34 53.6A 5 3 20 0 1 8 8 5 3 20 0 1 8 8 " +
                    "c 0,0 143,3 185,-181 2,-11 -1,-20 1,-33",
            ),
        )
        // Act
        val node = path.asNode(minified = false) as ImageVectorNode.Path
        val nodes = node.wrapper.nodes

        // Assert
        assertEquals(expected = 7, actual = nodes.size)
        assertIs<PathNodes.MoveTo>(nodes[0])
        assertIs<PathNodes.LineTo>(nodes[1])
        assertIs<PathNodes.LineTo>(nodes[2])
        assertIs<PathNodes.ArcTo>(nodes[3])
        assertIs<PathNodes.ArcTo>(nodes[4])
        assertIs<PathNodes.CurveTo>(nodes[5])
        assertIs<PathNodes.CurveTo>(nodes[6])
    }

    @Test
    fun `should throw ExitProgramException when a not supported command is found on SVG path`() {
        // Arrange
        val command = "X"
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf(
                "d" to "${command}85.122 64.795 -12.34 88.6",
            ),
        )
        // Act
        val exception = assertFailsWith<ExitProgramException> {
            path.asNode(minified = false)
        }
        // Assert
        assertEquals(
            expected = "Not support SVG/Android Vector command. Command = $command",
            actual = exception.message,
        )
    }

    // -------------------------------------------------------------------------
    // NodeWrapper.plus()
    // -------------------------------------------------------------------------

    @Test
    fun `NodeWrapper plus combines normalizedPath with a space separator`() {
        // Arrange
        val left = "M 0 0 L 1 1".asNodeWrapper(minified = false)
        val right = "L 2 2".asNodeWrapper(minified = false)

        // Act
        val combined = left + right

        // Assert – path strings are joined with a single space
        val expectedPath = "${left.normalizedPath} ${right.normalizedPath}"
        assertEquals(expectedPath, combined.normalizedPath)
    }

    @Test
    fun `NodeWrapper plus merges node lists in order`() {
        // Arrange
        val left = "M 0 0 L 1 1".asNodeWrapper(minified = false)
        val right = "L 2 2 L 3 3".asNodeWrapper(minified = false)

        // Act
        val combined = left + right

        // Assert – all nodes from left appear before all nodes from right
        assertEquals(left.nodes.size + right.nodes.size, combined.nodes.size)
        assertEquals(left.nodes + right.nodes, combined.nodes)
    }

    @Test
    fun `NodeWrapper plus with empty right operand preserves left unchanged`() {
        // Arrange – a single close command "z" has an empty node list
        val left = "M 10 20 L 30 40".asNodeWrapper(minified = false)
        val emptyRight = ImageVectorNode.NodeWrapper(normalizedPath = "", nodes = emptyList())

        // Act
        val combined = left + emptyRight

        // Assert
        assertEquals("${left.normalizedPath} ", combined.normalizedPath)
        assertEquals(left.nodes, combined.nodes)
    }

    @Test
    fun `NodeWrapper plus with empty left operand preserves right unchanged`() {
        // Arrange
        val emptyLeft = ImageVectorNode.NodeWrapper(normalizedPath = "", nodes = emptyList())
        val right = "M 5 5 L 6 6".asNodeWrapper(minified = false)

        // Act
        val combined = emptyLeft + right

        // Assert
        assertEquals(" ${right.normalizedPath}", combined.normalizedPath)
        assertEquals(right.nodes, combined.nodes)
    }

    @Test
    fun `NodeWrapper plus is not commutative for normalizedPath`() {
        // Arrange
        val a = "M 0 0".asNodeWrapper(minified = false)
        val b = "L 1 1".asNodeWrapper(minified = false)

        // Act
        val ab = a + b
        val ba = b + a

        // Assert – order matters for the path string
        assertEquals("${a.normalizedPath} ${b.normalizedPath}", ab.normalizedPath)
        assertEquals("${b.normalizedPath} ${a.normalizedPath}", ba.normalizedPath)
    }
}