package dev.tonholo.s2c.domain

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class PathNodesCurveToTests {
    data class CurveToParams(
        val x1: Float,
        val y1: Float,
        val x2: Float,
        val y2: Float,
        val x3: Float,
        val y3: Float,
    ) {
        override fun toString(): String = "$x1 $y1 $x2 $y2 $x3 $y3"
    }

    @Test
    fun `ensure a 'c' command is parsed to CurveTo relative`() {
        // Arrange
        val path = SvgNode.Path(
            d = "c2,8 8,8 8,5",
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
            assertIs<PathNodes.CurveTo>(this)
            assertTrue(isRelative)
        }
    }

    @Test
    fun `ensure a 'C' command is parsed to CurveTo`() {
        // Arrange
        val path = SvgNode.Path(
            d = "C2,8 8,8 8,5",
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
            assertIs<PathNodes.CurveTo>(this)
            assertFalse(isRelative)
        }
    }

    @Test
    fun `ensure materialize generates curveTo instruction properly`() {
        // Arrange
        val nonRelative = CurveToParams(
            x1 = 15f,
            y1 = 12f,
            x2 = 10f,
            y2 = 5f,
            x3 = 0f,
            y3 = 123f,
        )
        val relative = CurveToParams(
            x1 = 321f,
            y1 = 125f,
            x2 = 510f,
            y2 = 51f,
            x3 = 10f,
            y3 = 123f,
        )
        val path = SvgNode.Path(
            d = "C$nonRelative c$relative",
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
                |// C $this
                |curveTo(
                |    x1 = ${x1}f,
                |    y1 = ${y1}f,
                |    x2 = ${x2}f,
                |    y2 = ${y2}f,
                |    x3 = ${x3}f,
                |    y3 = ${y3}f,
                |)
                |""".trimMargin()
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |// c $this
                |curveToRelative(
                |    dx1 = ${x1}f,
                |    dy1 = ${y1}f,
                |    dx2 = ${x2}f,
                |    dy2 = ${y2}f,
                |    dx3 = ${x3}f,
                |    dy3 = ${y3}f,
                |)
                |""".trimMargin()
            )
        }
    }

    @Test
    fun `ensure materialize generates curveTo with a close instruction properly`() {
        // Arrange
        val nonRelative = CurveToParams(
            x1 = 15f,
            y1 = 12f,
            x2 = 10f,
            y2 = 5f,
            x3 = 0f,
            y3 = 123f,
        )
        val relative = CurveToParams(
            x1 = 321f,
            y1 = 125f,
            x2 = 510f,
            y2 = 51f,
            x3 = 10f,
            y3 = 123f,
        )
        val path = SvgNode.Path(
            d = "C${nonRelative}z c${relative}z",
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
                |// C $this
                |curveTo(
                |    x1 = ${x1}f,
                |    y1 = ${y1}f,
                |    x2 = ${x2}f,
                |    y2 = ${y2}f,
                |    x3 = ${x3}f,
                |    y3 = ${y3}f,
                |)
                |close()
                |""".trimMargin()
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |// c $this
                |curveToRelative(
                |    dx1 = ${x1}f,
                |    dy1 = ${y1}f,
                |    dx2 = ${x2}f,
                |    dy2 = ${y2}f,
                |    dx3 = ${x3}f,
                |    dy3 = ${y3}f,
                |)
                |close()
                |""".trimMargin()
            )
        }
    }
}
