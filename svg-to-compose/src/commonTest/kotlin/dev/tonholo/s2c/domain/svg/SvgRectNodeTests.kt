package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.compose.toBrush
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SvgRectNodeTests : BaseSvgTest() {
    @Test
    fun `ensure SvgRectNode initializes correctly`() {
        val attributes = mutableMapOf("x" to "10", "y" to "20", "width" to "30", "height" to "40")
        val rect = SvgRectNode(root, attributes)

        assertEquals(expected = 10, actual = rect.x)
        assertEquals(expected = 20, actual = rect.y)
        assertEquals(expected = 30, actual = rect.width)
        assertEquals(expected = 40, actual = rect.height)
    }

    @Test
    fun `ensure SvgRectNode initializes with default values`() {
        val rect = SvgRectNode(root, mutableMapOf("width" to "30", "height" to "40"))

        assertEquals(expected = 30, actual = rect.width)
        assertEquals(expected = 40, actual = rect.height)
        assertNull(rect.x)
        assertNull(rect.y)
        assertNull(rect.fill)
        assertNull(rect.stroke)
        assertNull(rect.strokeWidth)
        assertNull(rect.strokeLineJoin)
        assertNull(rect.strokeLineCap)
        assertNull(rect.fillRule)
        assertNull(rect.strokeOpacity)
        assertNull(rect.strokeMiterLimit)
        assertNull(rect.strokeDashArray)
    }

    @Test
    fun `ensure SvgRectNode sets stroke width with root viewport dimensions`() {
        root.attributes["width"] = "100"
        root.attributes["height"] = "150"
        val attributes = mutableMapOf("width" to "30", "height" to "40", "stroke-width" to "50%")
        val rect = SvgRectNode(root, attributes)

        assertEquals(expected = 75f, actual = rect.strokeWidth) // 50% of viewport height
    }

    @Test
    fun `ensure SvgRectNode creates simple rectangle`() {
        val attributes = mutableMapOf("x" to "10", "y" to "20", "width" to "30", "height" to "40")
        val rect = SvgRectNode(root, attributes)

        val path = rect.asNode(minified = false)
        assertIs<ImageVectorNode.Path>(path)

        val nodes = path.wrapper.nodes
        assertTrue(nodes.isNotEmpty())
        assertEquals(expected = 4, actual = nodes.size)
        nodes[0].let {
            assertIs<PathNodes.MoveTo>(it)
            assertEquals(expected = 10f, actual = it.x)
            assertEquals(expected = 20f, actual = it.y)
            assertFalse(it.isRelative)
        }
        nodes[1].let {
            assertIs<PathNodes.HorizontalLineTo>(it)
            assertEquals(expected = 30f, actual = it.x)
            assertTrue(it.isRelative)
        }
        nodes[2].let {
            assertIs<PathNodes.VerticalLineTo>(it)
            assertEquals(expected = 40f, actual = it.y)
            assertTrue(it.isRelative)
        }
        nodes[3].let {
            assertIs<PathNodes.HorizontalLineTo>(it)
            assertEquals(expected = -30f, actual = it.x)
            assertTrue(it.isRelative)
            assertTrue(it.shouldClose)
        }
    }

    @Test
    fun `ensure SvgRectNode creates rounded corner rectangle`() {
        val attributes = mutableMapOf(
            "x" to "10",
            "y" to "20",
            "width" to "30",
            "height" to "40",
            "rx" to "10",
            "ry" to "10",
        )
        val rect = SvgRectNode(root, attributes)

        val path = rect.asNode(minified = false)
        assertIs<ImageVectorNode.Path>(path)

        val nodes = path.wrapper.nodes
        assertTrue(nodes.isNotEmpty())
        assertEquals(expected = 8, actual = nodes.size)
        nodes[0].let {
            assertIs<PathNodes.MoveTo>(it)
            assertEquals(expected = 10f, actual = it.x)
            assertEquals(expected = 30f, actual = it.y)
            assertFalse(it.isRelative)
        }
        nodes[1].let {
            assertIs<PathNodes.ArcTo>(it)
            assertEquals(expected = 10f, actual = it.a)
            assertEquals(expected = 10f, actual = it.b)
            assertEquals(expected = 0f, actual = it.theta)
            assertFalse(it.isMoreThanHalf)
            assertTrue(it.isPositiveArc)
            assertEquals(expected = 10f, actual = it.x)
            assertEquals(expected = -10f, actual = it.y)
            assertTrue(it.isRelative)
        }
        nodes[2].let {
            assertIs<PathNodes.HorizontalLineTo>(it)
            assertEquals(expected = 10f, actual = it.x)
            assertTrue(it.isRelative)
        }
        nodes[3].let {
            assertIs<PathNodes.ArcTo>(it)
            assertEquals(expected = 10f, actual = it.a)
            assertEquals(expected = 10f, actual = it.b)
            assertEquals(expected = 0f, actual = it.theta)
            assertFalse(it.isMoreThanHalf)
            assertTrue(it.isPositiveArc)
            assertEquals(expected = 10f, actual = it.x)
            assertEquals(expected = 10f, actual = it.y)
            assertTrue(it.isRelative)
        }
        nodes[4].let {
            assertIs<PathNodes.VerticalLineTo>(it)
            assertEquals(expected = 20f, actual = it.y)
            assertTrue(it.isRelative)
        }
        nodes[5].let {
            assertIs<PathNodes.ArcTo>(it)
            assertEquals(expected = 10f, actual = it.a)
            assertEquals(expected = 10f, actual = it.b)
            assertEquals(expected = 0f, actual = it.theta)
            assertFalse(it.isMoreThanHalf)
            assertTrue(it.isPositiveArc)
            assertEquals(expected = -10f, actual = it.x)
            assertEquals(expected = 10f, actual = it.y)
            assertTrue(it.isRelative)
        }
        nodes[6].let {
            assertIs<PathNodes.HorizontalLineTo>(it)
            assertEquals(expected = -10f, actual = it.x)
            assertTrue(it.isRelative)
        }
        nodes[7].let {
            assertIs<PathNodes.ArcTo>(it)
            assertEquals(expected = 10f, actual = it.a)
            assertEquals(expected = 10f, actual = it.b)
            assertEquals(expected = 0f, actual = it.theta)
            assertFalse(it.isMoreThanHalf)
            assertTrue(it.isPositiveArc)
            assertEquals(expected = -10f, actual = it.x)
            assertEquals(expected = -10f, actual = it.y)
            assertTrue(it.isRelative)
            assertTrue(it.shouldClose)
        }
    }

    @Test
    fun `ensure SvgRectNode creates dashed rectangle`() {
        val width = 30
        val height = 40
        val attributes = mutableMapOf(
            "x" to "10",
            "y" to "20",
            "width" to "$width",
            "height" to "$height",
            "stroke" to "#FF0000",
        )
        // Define some dash patterns
        val dashPatterns = listOf(
            intArrayOf(5, 10), // Smaller dash followed by a larger gap
            intArrayOf(10, 2), // Larger dash followed by a smaller gap
            intArrayOf(15, 5, 5, 5), // Multiple dash-gap pairs
            intArrayOf(20, 2, 5, 8, 12, 50), // Multiple dash-gap pairs
        )
        val perimeter = 2f * (width + height) // 140
        val edges = floatArrayOf(
            width.toFloat(),
            (width + height).toFloat(),
            ((2 * width) + height).toFloat(),
            perimeter,
        )
        dashPatterns.forEach { dashArray ->
            attributes += "stroke-dasharray" to dashArray.joinToString()

            val rect = SvgRectNode(root, attributes)

            val rectNode = rect.asNode(minified = false)
            assertIs<ImageVectorNode.Path>(rectNode)

            assertEquals(expected = "#FF0000".toBrush(), actual = rectNode.params.stroke)
            var expectedNodeSize = 1 // always start with MoveTo.
            var drawLength = 0
            var i = 0
            var direction = 0
            while (drawLength < perimeter) {
                val dashOrGap = dashArray[i % dashArray.size]
                val nextDrawLength = drawLength + dashOrGap
                val edge = edges[direction]
                val isDash = i % 2 == 0
                val atTheEdge = nextDrawLength >= edge
                val diff = nextDrawLength - edge
                if (atTheEdge) direction++
                expectedNodeSize = when {
                    isDash && atTheEdge && diff > 0 && direction < 4 -> { // draw at the edge
                        expectedNodeSize + 3
                    }

                    else -> { // draw within the edges or gap
                        expectedNodeSize + 1
                    }
                }
                if (i % 2 != 0 && nextDrawLength >= perimeter) {
                    expectedNodeSize--
                    break
                }
                i++
                drawLength += dashOrGap
            }
            val actualNodes = rectNode.wrapper.nodes
            val errorMessage = "stroke-dasharray = [${dashArray.joinToString()}]"
            assertEquals(
                expected = expectedNodeSize,
                actual = actualNodes.size,
                message = "Failed to calculate the node size. $errorMessage",
            )
        }
    }
}
