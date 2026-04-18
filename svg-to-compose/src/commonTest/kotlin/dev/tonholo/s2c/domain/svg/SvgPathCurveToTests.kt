package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.emit
import dev.tonholo.s2c.extensions.removeTrailingZero
import dev.tonholo.s2c.extensions.toStringConsistent
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class SvgPathCurveToTests : BaseSvgTest() {
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

    @Test
    fun `ensure a 'c' command is parsed to CurveTo relative`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "c2,8 8,8 8,5"),
        )
        // Act
        val node = with(logger) { path.asNode(minified = false) } as ImageVectorNode.Path
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
        val node = with(logger) { path.asNode(minified = false) } as ImageVectorNode.Path
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
        val node = with(logger) { path.asNode(minified = false) } as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.emit() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                |// C $this
                |curveTo(
                |    x1 = ${x1.toStringConsistent()}f,
                |    y1 = ${y1.toStringConsistent()}f,
                |    x2 = ${x2.toStringConsistent()}f,
                |    y2 = ${y2.toStringConsistent()}f,
                |    x3 = ${x3.toStringConsistent()}f,
                |    y3 = ${y3.toStringConsistent()}f,
                |)
                |
                """.trimMargin(),
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |// c $this
                |curveToRelative(
                |    dx1 = ${x1.toStringConsistent()}f,
                |    dy1 = ${y1.toStringConsistent()}f,
                |    dx2 = ${x2.toStringConsistent()}f,
                |    dy2 = ${y2.toStringConsistent()}f,
                |    dx3 = ${x3.toStringConsistent()}f,
                |    dy3 = ${y3.toStringConsistent()}f,
                |)
                |
                """.trimMargin(),
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
        val node = with(logger) { path.asNode(minified = false) } as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.emit() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                |// C ${this}z
                |curveTo(
                |    x1 = ${x1.toStringConsistent()}f,
                |    y1 = ${y1.toStringConsistent()}f,
                |    x2 = ${x2.toStringConsistent()}f,
                |    y2 = ${y2.toStringConsistent()}f,
                |    x3 = ${x3.toStringConsistent()}f,
                |    y3 = ${y3.toStringConsistent()}f,
                |)
                |close()
                |
                """.trimMargin(),
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |// c ${this}z
                |curveToRelative(
                |    dx1 = ${x1.toStringConsistent()}f,
                |    dy1 = ${y1.toStringConsistent()}f,
                |    dx2 = ${x2.toStringConsistent()}f,
                |    dy2 = ${y2.toStringConsistent()}f,
                |    dx3 = ${x3.toStringConsistent()}f,
                |    dy3 = ${y3.toStringConsistent()}f,
                |)
                |close()
                |
                """.trimMargin(),
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
        val node = with(logger) { path.asNode(minified = true) } as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.emit() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = buildString {
                    append("curveTo(")
                    append("x1 = ${x1.toStringConsistent()}f,")
                    append(" y1 = ${y1.toStringConsistent()}f,")
                    append(" x2 = ${x2.toStringConsistent()}f,")
                    append(" y2 = ${y2.toStringConsistent()}f,")
                    append(" x3 = ${x3.toStringConsistent()}f,")
                    append(" y3 = ${y3.toStringConsistent()}f")
                    append(")")
                },
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = buildString {
                    append("curveToRelative(")
                    append("dx1 = ${x1.toStringConsistent()}f,")
                    append(" dy1 = ${y1.toStringConsistent()}f,")
                    append(" dx2 = ${x2.toStringConsistent()}f,")
                    append(" dy2 = ${y2.toStringConsistent()}f,")
                    append(" dx3 = ${x3.toStringConsistent()}f,")
                    append(" dy3 = ${y3.toStringConsistent()}f")
                    append(")")
                },
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
        val node = with(logger) { path.asNode(minified = true) } as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.emit() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = buildString {
                    append("curveTo(")
                    append("x1 = ${x1.toStringConsistent()}f,")
                    append(" y1 = ${y1.toStringConsistent()}f,")
                    append(" x2 = ${x2.toStringConsistent()}f,")
                    append(" y2 = ${y2.toStringConsistent()}f,")
                    append(" x3 = ${x3.toStringConsistent()}f,")
                    append(" y3 = ${y3.toStringConsistent()}f")
                    append(")")
                    appendLine()
                    append("close()")
                },
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = buildString {
                    append("curveToRelative(")
                    append("dx1 = ${x1.toStringConsistent()}f,")
                    append(" dy1 = ${y1.toStringConsistent()}f,")
                    append(" dx2 = ${x2.toStringConsistent()}f,")
                    append(" dy2 = ${y2.toStringConsistent()}f,")
                    append(" dx3 = ${x3.toStringConsistent()}f,")
                    append(" dy3 = ${y3.toStringConsistent()}f")
                    append(")")
                    appendLine()
                    append("close()")
                },
            )
        }
    }
}
