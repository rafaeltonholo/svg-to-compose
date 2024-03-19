package dev.tonholo.s2c.domain

import dev.tonholo.s2c.domain.svg.SvgElementNode
import dev.tonholo.s2c.domain.svg.SvgPathNode
import dev.tonholo.s2c.domain.svg.asNode
import dev.tonholo.s2c.domain.xml.XmlRootNode
import dev.tonholo.s2c.extensions.removeTrailingZero
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
        override fun toString(): String = "$x1 $y1 $x2 $y2 $x3 $y3".removeTrailingZero()
    }

    private val root = SvgElementNode(
        parent = XmlRootNode(children = mutableSetOf()),
        children = mutableSetOf(),
        attributes = mutableMapOf(),
    )

    @Test
    fun `ensure a 'c' command is parsed to CurveTo relative`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "c2,8 8,8 8,5"),
        )
        // Act
        val node = path.asNode() as ImageVectorNode.Path
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
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "C2,8 8,8 8,5"),
        )
        // Act
        val node = path.asNode() as ImageVectorNode.Path
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
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "C$nonRelative c$relative"),
        )
        // Act
        val node = path.asNode() as ImageVectorNode.Path
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
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "C${nonRelative}z c${relative}z"),
        )
        // Act
        val node = path.asNode() as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                |// C ${this}z
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
                |// c ${this}z
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

    @Test
    fun `ensure materialize generates curveTo with inlined parameters and no comment when minified`() {
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
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "C$nonRelative c$relative"),
        )
        // Act
        val node = path.asNode(minified = true) as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = buildString {
                    append("curveTo(")
                    append("x1 = ${x1}f,")
                    append(" y1 = ${y1}f,")
                    append(" x2 = ${x2}f,")
                    append(" y2 = ${y2}f,")
                    append(" x3 = ${x3}f,")
                    append(" y3 = ${y3}f")
                    append(")")
                }
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = buildString {
                    append("curveToRelative(")
                    append("dx1 = ${x1}f,")
                    append(" dy1 = ${y1}f,")
                    append(" dx2 = ${x2}f,")
                    append(" dy2 = ${y2}f,")
                    append(" dx3 = ${x3}f,")
                    append(" dy3 = ${y3}f")
                    append(")")
                }
            )
        }
    }

    @Test
    fun `ensure materialize generates curveTo with inlined parameters and no comment with close instruction when minified`() {
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
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "C${nonRelative}z c${relative}z"),
        )
        // Act
        val node = path.asNode(minified = true) as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = buildString {
                    append("curveTo(")
                    append("x1 = ${x1}f,")
                    append(" y1 = ${y1}f,")
                    append(" x2 = ${x2}f,")
                    append(" y2 = ${y2}f,")
                    append(" x3 = ${x3}f,")
                    append(" y3 = ${y3}f")
                    append(")")
                    appendLine()
                    append("close()")
                }
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = buildString {
                    append("curveToRelative(")
                    append("dx1 = ${x1}f,")
                    append(" dy1 = ${y1}f,")
                    append(" dx2 = ${x2}f,")
                    append(" dy2 = ${y2}f,")
                    append(" dx3 = ${x3}f,")
                    append(" dy3 = ${y3}f")
                    append(")")
                    appendLine()
                    append("close()")
                }
            )
        }
    }
}
