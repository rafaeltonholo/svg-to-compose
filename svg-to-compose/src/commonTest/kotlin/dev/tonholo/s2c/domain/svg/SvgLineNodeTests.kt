package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.compose.toBrush
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class SvgLineNodeTests : BaseSvgTest() {
    @Test
    fun `given x1 y1 x2 y2 attributes - when SvgLineNode is constructed - then attributes are parsed`() {
        // Arrange
        val attributes = mutableMapOf(
            "x1" to "10",
            "y1" to "20",
            "x2" to "30",
            "y2" to "40",
        )

        // Act
        val line = SvgLineNode(root, attributes)

        // Assert
        assertEquals(expected = "line", actual = line.tagName)
        assertEquals(expected = 10f, actual = line.x1)
        assertEquals(expected = 20f, actual = line.y1)
        assertEquals(expected = 30f, actual = line.x2)
        assertEquals(expected = 40f, actual = line.y2)
    }

    @Test
    fun `given no coordinate attributes - when SvgLineNode is constructed - then coordinates default to zero`() {
        // Arrange
        val attributes = mutableMapOf<String, String>()

        // Act
        val line = SvgLineNode(root, attributes)

        // Assert
        assertEquals(expected = 0f, actual = line.x1)
        assertEquals(expected = 0f, actual = line.y1)
        assertEquals(expected = 0f, actual = line.x2)
        assertEquals(expected = 0f, actual = line.y2)
        assertNull(line.fill)
        assertNull(line.stroke)
        assertNull(line.strokeWidth)
        assertNull(line.strokeLineJoin)
        assertNull(line.strokeLineCap)
        assertNull(line.fillRule)
        assertNull(line.strokeOpacity)
        assertNull(line.strokeMiterLimit)
        assertNull(line.strokeDashArray)
    }

    @Test
    fun `given percentage stroke-width - when SvgLineNode is constructed - then stroke width resolves against root viewport`() {
        // Arrange
        root.attributes["width"] = "100"
        root.attributes["height"] = "200"
        val attributes = mutableMapOf(
            "x1" to "0",
            "y1" to "0",
            "x2" to "10",
            "y2" to "10",
            "stroke-width" to "50%",
        )

        // Act
        val line = SvgLineNode(root, attributes)

        // Assert
        // toLengthFloatOrNull resolves percentages against max(width, height).
        assertEquals(expected = 100f, actual = line.strokeWidth)
    }

    @Test
    fun `given line with stroke - when asNode is called - then path uses MoveTo and LineTo and preserves stroke`() {
        // Arrange
        val attributes = mutableMapOf(
            "x1" to "10",
            "y1" to "20",
            "x2" to "30",
            "y2" to "40",
            "stroke" to "#000000",
            "stroke-width" to "16",
            "fill" to "none",
            "stroke-linecap" to "round",
            "stroke-linejoin" to "round",
        )
        val line = SvgLineNode(root, attributes)

        // Act
        val path = line.asNode(minified = false)

        // Assert
        assertIs<ImageVectorNode.Path>(path)
        val nodes = path.wrapper.nodes
        assertEquals(expected = 2, actual = nodes.size)
        nodes[0].let {
            assertIs<PathNodes.MoveTo>(it)
            assertEquals(expected = 10.0, actual = it.x)
            assertEquals(expected = 20.0, actual = it.y)
            assertFalse(it.isRelative)
            assertFalse(it.shouldClose)
        }
        nodes[1].let {
            assertIs<PathNodes.LineTo>(it)
            assertEquals(expected = 30.0, actual = it.x)
            assertEquals(expected = 40.0, actual = it.y)
            assertFalse(it.isRelative)
            assertFalse(it.shouldClose)
        }
        assertEquals(expected = "#000000".toBrush(), actual = path.params.stroke)
        // fill="none" passes through as a SolidColor("none") brush; downstream emitters skip it.
        assertEquals(expected = "none".toBrush(), actual = path.params.fill)
        assertEquals(expected = 16f, actual = path.params.strokeLineWidth)
    }

    @Test
    fun `given line without explicit stroke - when asNode is called - then stroke is null and path is still produced`() {
        // Arrange
        val attributes = mutableMapOf(
            "x1" to "0",
            "y1" to "0",
            "x2" to "10",
            "y2" to "10",
        )
        val line = SvgLineNode(root, attributes)

        // Act
        val path = line.asNode(minified = false)

        // Assert
        assertNull(path.params.stroke)
        assertNotNull(path.wrapper.nodes)
        assertEquals(expected = 2, actual = path.wrapper.nodes.size)
    }
}
