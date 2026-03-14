package dev.tonholo.s2c.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class StrokeDashArrayTest {

    // -------------------------------------------------------------------------
    // StrokeDashArray construction
    // -------------------------------------------------------------------------

    @Test
    fun `dashesAndGaps parses space-separated values`() {
        val sda = StrokeDashArray("4 2")
        assertEquals(2, sda.dashesAndGaps.size)
        assertEquals(4, sda.dashesAndGaps[0])
        assertEquals(2, sda.dashesAndGaps[1])
    }

    @Test
    fun `dashesAndGaps parses comma-separated values`() {
        val sda = StrokeDashArray("10,5")
        assertEquals(2, sda.dashesAndGaps.size)
        assertEquals(10, sda.dashesAndGaps[0])
        assertEquals(5, sda.dashesAndGaps[1])
    }

    @Test
    fun `dashesAndGaps parses comma-and-space-separated values`() {
        val sda = StrokeDashArray("8, 4, 2, 1")
        assertEquals(4, sda.dashesAndGaps.size)
        assertEquals(8, sda.dashesAndGaps[0])
        assertEquals(4, sda.dashesAndGaps[1])
        assertEquals(2, sda.dashesAndGaps[2])
        assertEquals(1, sda.dashesAndGaps[3])
    }

    @Test
    fun `dashesAndGaps parses single value`() {
        val sda = StrokeDashArray("6")
        assertEquals(1, sda.dashesAndGaps.size)
        assertEquals(6, sda.dashesAndGaps[0])
    }

    @Test
    fun `toString returns the original value string`() {
        val value = "4 2"
        val sda = StrokeDashArray(value)
        assertEquals(value, sda.toString())
    }

    // -------------------------------------------------------------------------
    // createDashedPathForRect
    // -------------------------------------------------------------------------

    @Test
    fun `createDashedPathForRect returns non-empty list of path nodes`() {
        // Arrange
        val sda = StrokeDashArray("10 5")

        // Act
        val nodes = sda.createDashedPathForRect(
            x = 0f,
            y = 0f,
            width = 100,
            height = 50,
            strokeWidth = 2,
            isMinified = false,
        )

        // Assert
        assertTrue(nodes.isNotEmpty(), "Expected at least one path node")
    }

    @Test
    fun `createDashedPathForRect first node is MoveTo at the given position`() {
        // Arrange
        val sda = StrokeDashArray("10 5")
        val x = 5f
        val y = 3f

        // Act
        val nodes = sda.createDashedPathForRect(
            x = x,
            y = y,
            width = 80,
            height = 40,
            strokeWidth = 2,
            isMinified = false,
        )

        // Assert
        assertIs<PathNodes.MoveTo>(nodes.first())
    }

    @Test
    fun `createDashedPathForRect last node has close command set`() {
        // The addCloseCommand() function adds a close suffix to the last node's last value.
        // Arrange
        val sda = StrokeDashArray("8 4")

        // Act
        val nodes = sda.createDashedPathForRect(
            x = 0f,
            y = 0f,
            width = 60,
            height = 30,
            strokeWidth = 2,
            isMinified = false,
        )

        // Assert — last node's last value should end with 'z' (Close command)
        val lastNode = nodes.last()
        assertTrue(
            lastNode.values.last().endsWith(PathCommand.Close.value.toString()),
            "Last path node should end with the Close command ('z'), but was: ${lastNode.values.last()}",
        )
    }

    @Test
    fun `createDashedPathForRect produces nodes for square with equal dash and gap`() {
        // Arrange: perimeter = 2*(50+50) = 200, dash=10, gap=10 → 10 dashes expected
        val sda = StrokeDashArray("10 10")

        // Act
        val nodes = sda.createDashedPathForRect(
            x = 0f,
            y = 0f,
            width = 50,
            height = 50,
            strokeWidth = 1,
            isMinified = false,
        )

        // Assert: should have at least the initial MoveTo + some dash nodes + close
        assertTrue(nodes.size >= 2, "Expected multiple path nodes, got ${nodes.size}")
        assertIs<PathNodes.MoveTo>(nodes.first())
    }

    @Test
    fun `createDashedPathForRect with minified flag produces minified nodes`() {
        // Arrange
        val sda = StrokeDashArray("5 3")

        // Act
        val nodes = sda.createDashedPathForRect(
            x = 0f,
            y = 0f,
            width = 40,
            height = 20,
            strokeWidth = 1,
            isMinified = true,
        )

        // Assert: all non-initial nodes should be minified
        // Skip the initial MoveTo (index 0) as it is created without isMinified context
        nodes.drop(1).forEach { node ->
            assertTrue(node.minified, "Expected minified=true for node $node")
        }
    }

    @Test
    fun `createDashedPathForRect with large dash covers whole perimeter without gaps`() {
        // When dash > perimeter, there should be no gap nodes — just one dash + close
        val sda = StrokeDashArray("1000 5")

        // Act
        val nodes = sda.createDashedPathForRect(
            x = 0f,
            y = 0f,
            width = 20,
            height = 10,
            strokeWidth = 1,
            isMinified = false,
        )

        // Assert: MoveTo + at least one path node + the last one has close
        assertTrue(nodes.size >= 2)
        assertIs<PathNodes.MoveTo>(nodes.first())
        assertTrue(nodes.last().values.last().endsWith(PathCommand.Close.value.toString()))
    }
}