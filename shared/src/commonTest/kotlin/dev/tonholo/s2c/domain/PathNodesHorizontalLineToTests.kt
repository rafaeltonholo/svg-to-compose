package dev.tonholo.s2c.domain

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class PathNodesHorizontalLineToTests {
    data class HorizontalLineParams(
        val x: Float,
    ) {
        override fun toString(): String = "$x"
    }

    @Test
    fun `ensure a 'h' command is parsed to HorizontalLineTo relative`() {
        // Arrange
        val path = SvgNode.Path(
            d = "h 8",
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
            assertIs<PathNodes.HorizontalLineTo>(this)
            assertTrue(isRelative)
        }
    }

    @Test
    fun `ensure a 'H' command is parsed to HorizontalLineTo`() {
        // Arrange
        val path = SvgNode.Path(
            d = "H 8",
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
            assertIs<PathNodes.HorizontalLineTo>(this)
            assertFalse(isRelative)
        }
    }

    @Test
    fun `ensure materialize generates horizontalLineTo instruction properly`() {
        // Arrange
        val nonRelative = HorizontalLineParams(
            x = 8f,
        )
        val relative = HorizontalLineParams(
            x = 80f,
        )
        val path = SvgNode.Path(
            d = "H$nonRelative h$relative",
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
                |// H $this
                |horizontalLineTo(x = ${x}f)
                |""".trimMargin()
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |// h $this
                |horizontalLineToRelative(dx = ${x}f)
                |""".trimMargin()
            )
        }
    }

    @Test
    fun `ensure materialize generates horizontalLineTo with a close instruction properly`() {
        // Arrange
        val nonRelative = HorizontalLineParams(
            x = 8f,
        )
        val relative = HorizontalLineParams(
            x = 80f,
        )
        val path = SvgNode.Path(
            d = "H${nonRelative}z h${relative}z",
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
                |// H $this
                |horizontalLineTo(x = ${x}f)
                |close()
                |""".trimMargin()
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |// h $this
                |horizontalLineToRelative(dx = ${x}f)
                |close()
                |""".trimMargin()
            )
        }
    }
}
