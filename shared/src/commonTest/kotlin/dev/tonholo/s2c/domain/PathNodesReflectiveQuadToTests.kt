package dev.tonholo.s2c.domain

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class PathNodesReflectiveQuadToTests {
    data class ReflectiveQuadToParams(
        val x1: Float,
        val y1: Float,
    ) {
        override fun toString(): String = "$x1 $y1"
    }

    @Test
    fun `ensure a 't' command is parsed to ReflectiveQuadTo relative`() {
        // Arrange
        val path = SvgNode.Path(
            d = "t8.5.2", // should parse to t8.5 .2
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
            assertIs<PathNodes.ReflectiveQuadTo>(this)
            assertTrue(isRelative)
        }
    }

    @Test
    fun `ensure a 'T' command is parsed to ReflectiveQuadTo`() {
        // Arrange
        val path = SvgNode.Path(
            d = "T -2.5-6.5", // should parse to T-2.5 -6.5
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
            assertIs<PathNodes.ReflectiveQuadTo>(this)
            assertFalse(isRelative)
        }
    }

    @Test
    fun `ensure materialize generates reflectiveQuadTo instruction properly`() {
        // Arrange
        val nonRelative = ReflectiveQuadToParams(
            x1 = 15f,
            y1 = 12f,
        )
        val relative = ReflectiveQuadToParams(
            x1 = 321f,
            y1 = 125f,
        )
        val path = SvgNode.Path(
            d = "T$nonRelative t$relative",
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
                |// T $this
                |reflectiveQuadTo(
                |    x1 = ${x1}f,
                |    y1 = ${y1}f,
                |)
                |""".trimMargin()
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |// t $this
                |reflectiveQuadToRelative(
                |    dx1 = ${x1}f,
                |    dy1 = ${y1}f,
                |)
                |""".trimMargin()
            )
        }
    }

    @Test
    fun `ensure materialize generates reflectiveQuadTo with a close instruction properly`() {
        // Arrange
        val nonRelative = ReflectiveQuadToParams(
            x1 = 15f,
            y1 = 12f,
        )
        val relative = ReflectiveQuadToParams(
            x1 = 321f,
            y1 = 125f,
        )
        val path = SvgNode.Path(
            d = "T${nonRelative}z t${relative}z",
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
                |// T $this
                |reflectiveQuadTo(
                |    x1 = ${x1}f,
                |    y1 = ${y1}f,
                |)
                |close()
                |""".trimMargin()
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |// t $this
                |reflectiveQuadToRelative(
                |    dx1 = ${x1}f,
                |    dy1 = ${y1}f,
                |)
                |close()
                |""".trimMargin()
            )
        }
    }
}
