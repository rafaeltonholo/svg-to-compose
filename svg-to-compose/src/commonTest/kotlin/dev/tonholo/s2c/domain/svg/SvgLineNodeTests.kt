package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathCommand
import dev.tonholo.s2c.domain.compose.StrokeCap
import dev.tonholo.s2c.domain.compose.toBrush
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull

class SvgLineNodeTests : BaseSvgTest() {
    @Test
    fun `ensure SvgLineNode initializes correctly`() {
        val attributes = mutableMapOf("x1" to "10", "y1" to "20", "x2" to "30", "y2" to "40")
        val line = SvgLineNode(root, attributes)

        assertEquals("line", line.tagName)
        assertEquals(10f, line.x1)
        assertEquals(20f, line.y1)
        assertEquals(30f, line.x2)
        assertEquals(40f, line.y2)
    }

    @Test
    fun `ensure SvgLineNode initializes with default values`() {
        val attributes = mutableMapOf<String, String>()
        val line = SvgLineNode(root, attributes)

        assertEquals(expected = 0f, actual = line.x1)
        assertEquals(expected = 0f, actual = line.y1)
        assertEquals(expected = 0f, actual = line.x2)
        assertEquals(expected = 0f, actual = line.y2)
        assertNull(line.fill)
        assertNull(line.stroke)
        assertNull(line.strokeWidth)
    }

    @Test
    fun `ensure SvgLineNode correctly handles missing attributes`() {
        val attributes = mutableMapOf("x2" to "50", "y2" to "60")
        val line = SvgLineNode(root, attributes)

        assertEquals(0f, line.x1)
        assertEquals(0f, line.y1)
        assertEquals(50f, line.x2)
        assertEquals(60f, line.y2)
    }

    @Test
    fun `ensure SvgLineNode creates path with MoveTo then LineTo commands`() {
        val attributes = mutableMapOf("x1" to "10", "y1" to "20", "x2" to "30", "y2" to "40")
        val line = SvgLineNode(root, attributes)

        val path = line.asNode(minified = false)
        assertIs<ImageVectorNode.Path>(path)

        val nodes = path.wrapper.nodes
        assertEquals(2, nodes.size)
        assertEquals(PathCommand.MoveTo, nodes[0].command)
        assertEquals(PathCommand.LineTo, nodes[1].command)
    }

    @Test
    fun `ensure SvgLineNode propagates stroke styling through asNode`() {
        val attributes = mutableMapOf(
            "x1" to "0",
            "y1" to "0",
            "x2" to "100",
            "y2" to "100",
            "stroke" to "#FF0000",
            "stroke-width" to "3",
            "stroke-linecap" to "round",
        )
        val line = SvgLineNode(root, attributes)

        val path = line.asNode(minified = false)
        assertIs<ImageVectorNode.Path>(path)

        assertEquals(expected = "#FF0000".toBrush(), actual = path.params.stroke)
        assertEquals(expected = 3f, actual = path.params.strokeLineWidth)
        assertEquals(expected = StrokeCap.Round, actual = path.params.strokeLineCap)
    }

    @Test
    fun `ensure SvgLineNode resolves percentage lengths against viewport`() {
        root.attributes["width"] = "100"
        root.attributes["height"] = "50"
        val attributes = mutableMapOf("x1" to "10%", "y1" to "20%", "x2" to "50%", "y2" to "100%")
        val line = SvgLineNode(root, attributes)

        assertEquals(expected = 10f, actual = line.x1)
        assertEquals(expected = 10f, actual = line.y1)
        assertEquals(expected = 50f, actual = line.x2)
        assertEquals(expected = 50f, actual = line.y2)
    }
}
