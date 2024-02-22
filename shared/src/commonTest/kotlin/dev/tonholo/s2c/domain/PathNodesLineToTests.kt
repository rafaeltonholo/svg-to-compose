package dev.tonholo.s2c.domain

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class PathNodesLineToTests {
    data class LineParams(
        val x: Float,
        val y: Float,
    ) {
        override fun toString(): String = "$x $y"
    }

    @Test
    fun `ensure a 'l' command is parsed to LineTo relative`() {
        // Arrange
        val path = SvgNode.Path(
            d = "l 8 10",
            fill = null,
            opacity = null,
            fillOpacity = null,
            style = null,
        )
        // Act
        val node = path.asNode()
        val nodes = node.wrapper.nodes
        // Assert
        assertEquals(expected = 1, actual = nodes.size)
        with(nodes.first()) {
            assertIs<PathNodes.LineTo>(this)
            assertTrue(isRelative)
        }
    }

    @Test
    fun `ensure a 'L' command is parsed to LineTo`() {
        // Arrange
        val path = SvgNode.Path(
            d = "L 8 10",
            fill = null,
            opacity = null,
            fillOpacity = null,
            style = null,
        )
        // Act
        val node = path.asNode()
        val nodes = node.wrapper.nodes
        // Assert
        assertEquals(expected = 1, actual = nodes.size)
        with(nodes.first()) {
            assertIs<PathNodes.LineTo>(this)
            assertFalse(isRelative)
        }
    }

    @Test
    fun `ensure materialize generates lineTo instruction properly`() {
        // Arrange
        val nonRelative = LineParams(
            x = 8f,
            y = 80f,
        )
        val relative = LineParams(
            x = 80f,
            y = 10f,
        )
        val path = SvgNode.Path(
            d = "L$nonRelative l$relative",
            fill = null,
            opacity = null,
            fillOpacity = null,
            style = null,
        )
        // Act
        val node = path.asNode()
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                |// L $this
                |lineTo(x = ${x}f, y = ${y}f)
                |""".trimMargin()
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |// l $this
                |lineToRelative(dx = ${x}f, dy = ${y}f)
                |""".trimMargin()
            )
        }
    }

    @Test
    fun `ensure materialize generates lineTo with a close instruction properly`() {
        // Arrange
        val nonRelative = LineParams(
            x = 8f,
            y = 80f,
        )
        val relative = LineParams(
            x = 80f,
            y = 10f,
        )
        val path = SvgNode.Path(
            d = "L${nonRelative}z l${relative}z",
            fill = null,
            opacity = null,
            fillOpacity = null,
            style = null,
        )
        // Act
        val node = path.asNode()
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                |// L $this
                |lineTo(x = ${x}f, y = ${y}f)
                |close()
                |""".trimMargin()
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |// l $this
                |lineToRelative(dx = ${x}f, dy = ${y}f)
                |close()
                |""".trimMargin()
            )
        }
    }
}
