package dev.tonholo.s2c.domain

import dev.tonholo.s2c.domain.svg.SvgPathNode
import dev.tonholo.s2c.domain.svg.SvgRootNode
import dev.tonholo.s2c.domain.svg.asNode
import dev.tonholo.s2c.domain.xml.XmlRootNode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.geom.AffineTransformation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import kotlin.test.assertNotSame
import kotlin.test.assertSame

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

    // NodeWrapper.plus() tests

    @Test
    fun `NodeWrapper plus combines normalizedPath with space separator`() {
        // Arrange
        val wrapper1 = ImageVectorNode.NodeWrapper(normalizedPath = "M 0 0 L 10 10", nodes = emptyList())
        val wrapper2 = ImageVectorNode.NodeWrapper(normalizedPath = "M 20 20 L 30 30", nodes = emptyList())

        // Act
        val combined = wrapper1 + wrapper2

        // Assert
        assertEquals("M 0 0 L 10 10 M 20 20 L 30 30", combined.normalizedPath)
    }

    @Test
    fun `NodeWrapper plus combines nodes from both wrappers`() {
        // Arrange
        val path1 = SvgPathNode(parent = root, attributes = mutableMapOf("d" to "M10 10 L20 20"))
        val path2 = SvgPathNode(parent = root, attributes = mutableMapOf("d" to "M30 30 L40 40"))
        val node1 = path1.asNode(minified = false) as ImageVectorNode.Path
        val node2 = path2.asNode(minified = false) as ImageVectorNode.Path
        val wrapper1 = node1.wrapper
        val wrapper2 = node2.wrapper

        // Act
        val combined = wrapper1 + wrapper2

        // Assert
        assertEquals(wrapper1.nodes.size + wrapper2.nodes.size, combined.nodes.size)
    }

    @Test
    fun `NodeWrapper plus with empty nodes still combines normalizedPath`() {
        // Arrange
        val wrapper1 = ImageVectorNode.NodeWrapper(normalizedPath = "M 0 0", nodes = emptyList())
        val wrapper2 = ImageVectorNode.NodeWrapper(normalizedPath = "Z", nodes = emptyList())

        // Act
        val combined = wrapper1 + wrapper2

        // Assert
        assertEquals("M 0 0 Z", combined.normalizedPath)
        assertEquals(0, combined.nodes.size)
    }

    @Test
    fun `NodeWrapper plus preserves all nodes from both wrappers in order`() {
        // Arrange
        val path1 = SvgPathNode(parent = root, attributes = mutableMapOf("d" to "M10 10"))
        val path2 = SvgPathNode(parent = root, attributes = mutableMapOf("d" to "L20 20"))
        val wrapper1 = (path1.asNode(minified = false) as ImageVectorNode.Path).wrapper
        val wrapper2 = (path2.asNode(minified = false) as ImageVectorNode.Path).wrapper

        // Act
        val combined = wrapper1 + wrapper2

        // Assert
        assertEquals(wrapper1.nodes + wrapper2.nodes, combined.nodes)
    }

    // applyTransformation() tests

    @Test
    fun `applyTransformation returns same instance when transformations is null for Path`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "M10 10 L20 20"),
        )
        val node = path.asNode(minified = false) as ImageVectorNode.Path
        // transformations is null by default

        // Act
        val result = node.applyTransformation()

        // Assert
        assertSame(node, result)
    }

    @Test
    fun `applyTransformation returns new Path instance when transformations is not null`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "M10 10 L20 20"),
        )
        val node = (path.asNode(minified = false) as ImageVectorNode.Path).copy(
            transformations = listOf(AffineTransformation.Identity),
        )

        // Act
        val result = node.applyTransformation()

        // Assert
        assertIs<ImageVectorNode.Path>(result)
        assertNotSame(node, result)
    }

    @Test
    fun `applyTransformation with Identity transformation preserves node count for Path`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "M10 10 L20 20 L30 30"),
        )
        val originalNode = (path.asNode(minified = false) as ImageVectorNode.Path)
        val nodeWithTransformation = originalNode.copy(
            transformations = listOf(AffineTransformation.Identity),
        )
        val originalNodeCount = originalNode.wrapper.nodes.size

        // Act
        val result = nodeWithTransformation.applyTransformation() as ImageVectorNode.Path

        // Assert
        assertEquals(originalNodeCount, result.wrapper.nodes.size)
    }

    @Test
    fun `applyTransformation on ChunkFunction throws error`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "M10 10 L20 20"),
        )
        val vectorNode = path.asNode(minified = false) as ImageVectorNode.Path
        val chunkFunction = ImageVectorNode.ChunkFunction(
            functionName = "drawIconChunk0",
            nodes = listOf(vectorNode),
        )

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            chunkFunction.applyTransformation()
        }
    }

    @Test
    fun `applyTransformation returns same instance when transformations is null for Group`() {
        // Arrange
        val groupNode = ImageVectorNode.Group(
            params = ImageVectorNode.Group.Params(),
            commands = emptyList(),
            minified = false,
            transformations = null,
        )

        // Act
        val result = groupNode.applyTransformation()

        // Assert
        assertSame(groupNode, result)
    }
}