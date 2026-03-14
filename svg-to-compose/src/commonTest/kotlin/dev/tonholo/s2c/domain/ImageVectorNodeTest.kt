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
    // NodeWrapper.plus operator
    // -------------------------------------------------------------------------

    @Test
    fun `NodeWrapper plus concatenates normalizedPaths with a space`() {
        // Arrange
        val first = ImageVectorNode.NodeWrapper(normalizedPath = "M 0 0", nodes = emptyList())
        val second = ImageVectorNode.NodeWrapper(normalizedPath = "L 10 10", nodes = emptyList())

        // Act
        val combined = first + second

        // Assert
        assertEquals("M 0 0 L 10 10", combined.normalizedPath)
    }

    @Test
    fun `NodeWrapper plus combines node lists`() {
        // Arrange
        val moveNode = PathNodes.MoveTo(values = listOf("0", "0"), isRelative = false, minified = false)
        val lineNode = PathNodes.LineTo(values = listOf("10", "20"), isRelative = false, minified = false)
        val first = ImageVectorNode.NodeWrapper(normalizedPath = "M 0 0", nodes = listOf(moveNode))
        val second = ImageVectorNode.NodeWrapper(normalizedPath = "L 10 20", nodes = listOf(lineNode))

        // Act
        val combined = first + second

        // Assert
        assertEquals(2, combined.nodes.size)
        assertIs<PathNodes.MoveTo>(combined.nodes[0])
        assertIs<PathNodes.LineTo>(combined.nodes[1])
    }

    @Test
    fun `NodeWrapper plus with empty first wrapper`() {
        // Arrange
        val moveNode = PathNodes.MoveTo(values = listOf("5", "5"), isRelative = false, minified = false)
        val empty = ImageVectorNode.NodeWrapper(normalizedPath = "", nodes = emptyList())
        val second = ImageVectorNode.NodeWrapper(normalizedPath = "M 5 5", nodes = listOf(moveNode))

        // Act
        val combined = empty + second

        // Assert
        assertEquals(" M 5 5", combined.normalizedPath)
        assertEquals(1, combined.nodes.size)
    }

    @Test
    fun `NodeWrapper plus with empty second wrapper`() {
        // Arrange
        val moveNode = PathNodes.MoveTo(values = listOf("1", "2"), isRelative = false, minified = false)
        val first = ImageVectorNode.NodeWrapper(normalizedPath = "M 1 2", nodes = listOf(moveNode))
        val empty = ImageVectorNode.NodeWrapper(normalizedPath = "", nodes = emptyList())

        // Act
        val combined = first + empty

        // Assert
        assertEquals("M 1 2 ", combined.normalizedPath)
        assertEquals(1, combined.nodes.size)
    }

    @Test
    fun `NodeWrapper plus with multiple nodes on both sides`() {
        // Arrange
        val move1 = PathNodes.MoveTo(values = listOf("0", "0"), isRelative = false, minified = false)
        val line1 = PathNodes.LineTo(values = listOf("5", "5"), isRelative = false, minified = false)
        val move2 = PathNodes.MoveTo(values = listOf("10", "10"), isRelative = true, minified = false)
        val line2 = PathNodes.LineTo(values = listOf("20", "20"), isRelative = true, minified = false)

        val first = ImageVectorNode.NodeWrapper(normalizedPath = "M 0 0 L 5 5", nodes = listOf(move1, line1))
        val second = ImageVectorNode.NodeWrapper(normalizedPath = "m 10 10 l 20 20", nodes = listOf(move2, line2))

        // Act
        val combined = first + second

        // Assert
        assertEquals("M 0 0 L 5 5 m 10 10 l 20 20", combined.normalizedPath)
        assertEquals(4, combined.nodes.size)
        assertTrue(combined.nodes[0] === move1)
        assertTrue(combined.nodes[1] === line1)
        assertTrue(combined.nodes[2] === move2)
        assertTrue(combined.nodes[3] === line2)
    }

    @Test
    fun `NodeWrapper plus is not commutative for normalizedPath`() {
        // The order of concatenation matters — first path comes before second
        val a = ImageVectorNode.NodeWrapper(normalizedPath = "M 0 0", nodes = emptyList())
        val b = ImageVectorNode.NodeWrapper(normalizedPath = "L 1 1", nodes = emptyList())

        val ab = a + b
        val ba = b + a

        assertEquals("M 0 0 L 1 1", ab.normalizedPath)
        assertEquals("L 1 1 M 0 0", ba.normalizedPath)
    }
}