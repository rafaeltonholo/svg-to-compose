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
    // NodeWrapper.plus()
    // -------------------------------------------------------------------------

    @Test
    fun `NodeWrapper plus concatenates normalizedPath with a space`() {
        // Arrange
        val a = ImageVectorNode.NodeWrapper(
            normalizedPath = "M 0 0 L 10 0",
            nodes = emptyList(),
        )
        val b = ImageVectorNode.NodeWrapper(
            normalizedPath = "L 10 10 Z",
            nodes = emptyList(),
        )
        // Act
        val combined = a + b
        // Assert
        assertEquals("M 0 0 L 10 0 L 10 10 Z", combined.normalizedPath)
    }

    @Test
    fun `NodeWrapper plus merges nodes from both wrappers in order`() {
        // Arrange
        val nodeA = PathNodes.MoveTo(values = listOf("0", "0"), isRelative = false, minified = false)
        val nodeB = PathNodes.LineTo(values = listOf("10", "0"), isRelative = false, minified = false)
        val a = ImageVectorNode.NodeWrapper(normalizedPath = "M 0 0", nodes = listOf(nodeA))
        val b = ImageVectorNode.NodeWrapper(normalizedPath = "L 10 0", nodes = listOf(nodeB))
        // Act
        val combined = a + b
        // Assert
        assertEquals(2, combined.nodes.size)
        assertSame(nodeA, combined.nodes[0])
        assertSame(nodeB, combined.nodes[1])
    }

    @Test
    fun `NodeWrapper plus with empty left wrapper preserves right wrapper path`() {
        // Arrange
        val empty = ImageVectorNode.NodeWrapper(normalizedPath = "", nodes = emptyList())
        val right = ImageVectorNode.NodeWrapper(normalizedPath = "M 5 5", nodes = emptyList())
        // Act
        val combined = empty + right
        // Assert
        assertEquals(" M 5 5", combined.normalizedPath)
        assertTrue(combined.nodes.isEmpty())
    }

    @Test
    fun `NodeWrapper plus with empty right wrapper keeps left nodes`() {
        // Arrange
        val node = PathNodes.MoveTo(values = listOf("1", "2"), isRelative = false, minified = false)
        val left = ImageVectorNode.NodeWrapper(normalizedPath = "M 1 2", nodes = listOf(node))
        val empty = ImageVectorNode.NodeWrapper(normalizedPath = "", nodes = emptyList())
        // Act
        val combined = left + empty
        // Assert
        assertEquals(1, combined.nodes.size)
        assertSame(node, combined.nodes[0])
    }

    // -------------------------------------------------------------------------
    // ImageVectorNode.applyTransformation()
    // -------------------------------------------------------------------------

    @Test
    fun `applyTransformation on Path with null transformations returns same instance`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "M10 20 L30 40"),
        ).asNode(minified = false) as ImageVectorNode.Path
        // transformations is null by default from asNode()
        // Act
        val result = path.applyTransformation()
        // Assert
        assertSame(path, result)
    }

    @Test
    fun `applyTransformation on Group with null transformations returns same instance`() {
        // Arrange
        val group = ImageVectorNode.Group(
            commands = emptyList(),
            minified = false,
            transformations = null,
        )
        // Act
        val result = group.applyTransformation()
        // Assert
        assertSame(group, result)
    }

    @Test
    fun `applyTransformation on Path with identity transformation returns a new copy`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "M10 20 L30 40"),
        ).asNode(minified = false) as ImageVectorNode.Path
        val pathWithTransform = path.copy(transformations = listOf(AffineTransformation.Identity))
        // Act
        val result = pathWithTransform.applyTransformation()
        // Assert – result is a distinct object (copy was made)
        assertNotSame(pathWithTransform, result)
        assertIs<ImageVectorNode.Path>(result)
        // The number of nodes is preserved
        assertEquals(pathWithTransform.wrapper.nodes.size, result.wrapper.nodes.size)
    }

    @Test
    fun `applyTransformation on Group with identity transformation returns a new copy`() {
        // Arrange
        val innerPath = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "M0 0 L5 5"),
        ).asNode(minified = false) as ImageVectorNode.Path

        val group = ImageVectorNode.Group(
            commands = listOf(innerPath),
            minified = false,
            transformations = listOf(AffineTransformation.Identity),
        )
        // Act
        val result = group.applyTransformation()
        // Assert
        assertNotSame(group, result)
        assertIs<ImageVectorNode.Group>(result)
        assertEquals(1, result.commands.size)
    }

    @Test
    fun `applyTransformation on ChunkFunction throws error when accessing transformations`() {
        // Arrange
        val chunk = ImageVectorNode.ChunkFunction(
            functionName = "chunk1",
            nodes = emptyList(),
        )
        // Act & Assert
        // ChunkFunction.transformations getter throws unconditionally
        assertFailsWith<IllegalStateException> {
            chunk.applyTransformation()
        }
    }
}