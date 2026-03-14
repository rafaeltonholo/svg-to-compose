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
import kotlin.test.assertTrue

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
    // NodeWrapper.plus tests (changed in this PR: refactored to expression body)
    // -------------------------------------------------------------------------

    @Test
    fun `NodeWrapper plus concatenates normalizedPath with a space`() {
        val wrapper1 = ImageVectorNode.NodeWrapper(
            normalizedPath = "M 0 0 L 10 10",
            nodes = emptyList(),
        )
        val wrapper2 = ImageVectorNode.NodeWrapper(
            normalizedPath = "L 20 20",
            nodes = emptyList(),
        )
        val combined = wrapper1 + wrapper2
        assertEquals("M 0 0 L 10 10 L 20 20", combined.normalizedPath)
    }

    @Test
    fun `NodeWrapper plus combines nodes from both wrappers in order`() {
        val path1 = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "M 0 0"),
        )
        val path2 = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "L 10 10"),
        )
        val node1 = (path1.asNode(minified = false) as ImageVectorNode.Path).wrapper
        val node2 = (path2.asNode(minified = false) as ImageVectorNode.Path).wrapper

        val combined = node1 + node2
        assertEquals(node1.nodes.size + node2.nodes.size, combined.nodes.size)
        assertEquals(node1.nodes[0], combined.nodes[0])
        assertEquals(node2.nodes[0], combined.nodes[node1.nodes.size])
    }

    @Test
    fun `NodeWrapper plus with empty second wrapper preserves first wrapper content`() {
        val wrapper = ImageVectorNode.NodeWrapper(
            normalizedPath = "M 5 5 L 10 10",
            nodes = emptyList(),
        )
        val empty = ImageVectorNode.NodeWrapper(
            normalizedPath = "",
            nodes = emptyList(),
        )
        val combined = wrapper + empty
        assertEquals("M 5 5 L 10 10 ", combined.normalizedPath)
        assertTrue(combined.nodes.isEmpty())
    }

    @Test
    fun `NodeWrapper plus is not commutative for normalizedPath`() {
        val w1 = ImageVectorNode.NodeWrapper(normalizedPath = "M 0 0", nodes = emptyList())
        val w2 = ImageVectorNode.NodeWrapper(normalizedPath = "L 5 5", nodes = emptyList())
        val leftFirst = (w1 + w2).normalizedPath
        val rightFirst = (w2 + w1).normalizedPath
        assertTrue(leftFirst != rightFirst)
    }

    @Test
    fun `applyTransformation returns same node when transformations is null`() {
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "M 10 10 L 20 20"),
        )
        val node = path.asNode(minified = false) as ImageVectorNode.Path
        // No transformations set (null) — should return same instance
        val result = node.applyTransformation()
        assertEquals(node, result)
    }

    @Test
    fun `applyTransformation on ChunkFunction throws error`() {
        val chunkFunction = ImageVectorNode.ChunkFunction(
            functionName = "iconChunk0",
            nodes = emptyList(),
        )
        assertFailsWith<IllegalStateException> {
            chunkFunction.applyTransformation()
        }
    }
}