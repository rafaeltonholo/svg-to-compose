package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathCommand
import kotlin.math.PI
import kotlin.math.roundToInt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SvgCircleNodeTests : BaseSvgTest() {
    @Test
    fun `ensure SvgCircleNode initializes correctly`() {
        val attributes = mutableMapOf("cx" to "10", "cy" to "20", "r" to "30")
        val circle = SvgCircleNode(root, attributes)

        assertEquals("circle", circle.tagName)
        assertEquals(10f, circle.cx)
        assertEquals(20f, circle.cy)
        assertEquals(30f, circle.radius)
    }

    @Test
    fun `ensure SvgCircleNode initializes with default values`() {
        val attributes = mutableMapOf<String, String>()
        val circle = SvgCircleNode(root, attributes)

        assertEquals(expected = 0f, actual = circle.cx)
        assertEquals(expected = 0f, actual = circle.cy)
        assertEquals(expected = 0f, actual = circle.radius)
        assertNull(circle.fill)
        assertNull(circle.stroke)
        assertNull(circle.strokeWidth)
        assertNull(circle.strokeLineJoin)
        assertNull(circle.strokeLineCap)
        assertNull(circle.fillRule)
        assertNull(circle.strokeOpacity)
        assertNull(circle.strokeMiterLimit)
        assertNull(circle.strokeDashArray)
    }

    @Test
    fun `ensure SvgCircleNode sets stroke width with root viewport dimensions`() {
        root.attributes["width"] = "100"
        root.attributes["height"] = "50"
        val attributes = mutableMapOf(
            "cx" to "10",
            "cy" to "20",
            "r" to "30",
            "stroke-width" to "50%",
        )
        val circle = SvgCircleNode(root, attributes)

        assertEquals(expected = 50f, actual = circle.strokeWidth) // 50% of viewport width
    }

    @Test
    fun `ensure SvgCircleNode correctly handles missing attributes`() {
        val attributes = mutableMapOf("r" to "30") // Missing 'cx' and 'cy'
        val circle = SvgCircleNode(root, attributes)

        assertEquals(0f, circle.cx) // Default value
        assertEquals(0f, circle.cy) // Default value
        assertEquals(30f, circle.radius)
    }

    @Test
    fun `ensure SvgCircleNode creates simple circle`() {
        val attributes = mutableMapOf("cx" to "10", "cy" to "20", "r" to "30")
        val circle = SvgCircleNode(root, attributes)

        val path = circle.asNode(minified = false)
        assertIs<ImageVectorNode.Path>(path)

        assertTrue(path.wrapper.nodes.isNotEmpty()) // Path should have nodes
    }

    @Test
    fun `ensure SvgCircleNode creates dashed circle with fill and stroke-dasharray`() {
        val attributes = mutableMapOf(
            "cx" to "10",
            "cy" to "20",
            "r" to "30",
            "fill" to "#0000FF",
            "stroke-dasharray" to "5,10",
            "stroke" to "#FFFFFF",
        )
        val circle = SvgCircleNode(root, attributes)

        val group = circle.asNode(minified = false)
        assertIs<ImageVectorNode.Group>(group)

        assertEquals(expected = 2, actual = group.commands.size)
        val fill = group.commands[0]
        assertIs<ImageVectorNode.Path>(fill)
        assertEquals(expected = "#0000FF", actual = fill.params.fill)
        assertNull(fill.params.stroke)

        val dashes = group.commands[1]
        assertIs<ImageVectorNode.Path>(dashes)
        assertEquals(expected = "#FFFFFF", actual = dashes.params.fill)
        assertNull(dashes.params.stroke)
    }

    @Test
    fun `ensure SvgCircleNode creates dashed circle`() {
        val attributes = mutableMapOf(
            "cx" to "10",
            "cy" to "20",
            "r" to "30",
            "stroke-dasharray" to "5,10",
            "stroke" to "#FF0000",
        )
        val circle = SvgCircleNode(root, attributes)

        val group = circle.asNode(minified = false)
        assertIs<ImageVectorNode.Group>(group)

        assertEquals(expected = 1, actual = group.commands.size)
        val dashes = group.commands[0]
        assertIs<ImageVectorNode.Path>(dashes)
        assertEquals(expected = "#FF0000", actual = dashes.params.fill)
        assertNull(dashes.params.stroke)
    }

    @Test
    fun `ensure createDashedCirclePath creates correct paths`() {
        // Arrange
        val radius = 45
        val attributes = mutableMapOf(
            "cx" to "50",
            "cy" to "150",
            "r" to radius.toString(),
        )
        // Define some dash patterns
        val dashPatterns = listOf(
            intArrayOf(10, 2), // Larger dash followed by a smaller gap
            intArrayOf(5, 10), // Smaller dash followed by a larger gap
            intArrayOf(15, 5, 5, 5), // Multiple dash-gap pairs
        )
        val segmentCommands = listOf(
            PathCommand.MoveTo,
            PathCommand.LineTo,
            PathCommand.ArcTo,
            PathCommand.LineTo,
            PathCommand.ArcTo,
        )

        dashPatterns.forEach { dashPattern ->
            attributes["stroke-dasharray"] = dashPattern.joinToString(" ")
            val circumference = 2 * PI.toFloat() * radius
            val dashSum = dashPattern.sum()
            val segments = (circumference / dashSum).roundToInt() * dashPattern.size

            val circle = SvgCircleNode(root, attributes)

            val group = circle.asNode()
            assertIs<ImageVectorNode.Group>(group)
            assertEquals(expected = 1, group.commands.size)
            val path = group.commands.first()
            assertIs<ImageVectorNode.Path>(path)

            val nodes = path.wrapper.nodes
            val errorMessage = "stroke-dasharray = [${dashPattern.joinToString()}]"
            // Ensure all segments were created
            // As a dash array alternate between dashes and gaps
            // the segments' size is divided by 2.
            assertEquals(
                expected = (segments / 2) * segmentCommands.size,
                actual = nodes.size,
                message = "Failed to calculate the node size. $errorMessage",
            )
            nodes.forEachIndexed { index, node ->
                val expectedSegmentCommandIndex = index % segmentCommands.size
                // ensure the commands were created in the right order.
                assertEquals(
                    expected = segmentCommands[expectedSegmentCommandIndex],
                    actual = node.command,
                    message = "The current segment is not correct. $errorMessage",
                )
                // ensure closing the path when the last ArcTo is placed.
                assertEquals(
                    expected = index % segmentCommands.size == segmentCommands.lastIndex,
                    actual = node.shouldClose,
                    message = "The current segment should close. $errorMessage",
                )
            }
        }
    }
}
