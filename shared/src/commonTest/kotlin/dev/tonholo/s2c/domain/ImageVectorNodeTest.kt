package dev.tonholo.s2c.domain

import dev.tonholo.s2c.error.ExitProgramException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs

class ImageVectorNodeTest {
    @Test
    fun `ensure for any subsequent coordinate pair after MoveTo is parsed to LineTo`() {
        // Arrange
        val path = SvgNode.Path(
            d = "M85.122 64.795 -12.34 88.6",
            fill = null,
            opacity = null,
            fillOpacity = null,
            style = null,
        )
        // Act
        val node = path.asNode()
        val nodes = node.wrapper.nodes

        // Assert
        assertEquals(expected = 2, actual = nodes.size)
        assertIs<PathNodes.MoveTo>(nodes[0])
        assertIs<PathNodes.LineTo>(nodes[1])
    }

    @Test
    fun `ensure repeat last command when no command letter was found`() {
        // Arrange
        val path = SvgNode.Path(
            d = "M85.122 64.795 -12.34 88.6 -32.34 53.6A 5 3 20 0 1 8 8 5 3 20 0 1 8 8 " +
                    "c 0,0 143,3 185,-181 2,-11 -1,-20 1,-33",
            fill = null,
            opacity = null,
            fillOpacity = null,
            style = null,
        )
        // Act
        val node = path.asNode()
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
        val path = SvgNode.Path(
            d = "${command}85.122 64.795 -12.34 88.6",
            fill = null,
            opacity = null,
            fillOpacity = null,
            style = null,
        )
        // Act
        val exception = assertFailsWith<ExitProgramException> {
            path.asNode()
        }
        // Assert
        assertEquals(
            expected = "Not support SVG/Android Vector command. Command = $command",
            actual = exception.message,
        )
    }
}
