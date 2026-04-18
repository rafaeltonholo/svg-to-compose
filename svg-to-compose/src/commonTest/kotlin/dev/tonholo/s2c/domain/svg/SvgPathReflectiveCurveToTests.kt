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

class SvgPathReflectiveCurveToTests : BaseSvgTest() {
    data class ReflectiveCurveToParams(val x1: Float, val y1: Float, val x2: Float, val y2: Float) {
        override fun toString(): String = "$x1 $y1 $x2 $y2".removeTrailingZero()
    }

    @Test
    fun `ensure a 's' command is parsed to ReflectiveCurveTo relative`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "s2,-2 4,5"),
        )
        // Act
        val node = with(logger) { path.asNode(minified = false) } as ImageVectorNode.Path
        val nodes = node.wrapper.nodes
        // Assert
        assertEquals(expected = 1, actual = nodes.size)
        with(nodes.first()) {
            assertIs<PathNodes.ReflectiveCurveTo>(this)
            assertTrue(isRelative)
        }
    }

    @Test
    fun `ensure a 'S' command is parsed to ReflectiveCurveTo`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "S2,-2 4,5"),
        )
        // Act
        val node = with(logger) { path.asNode(minified = false) } as ImageVectorNode.Path
        val nodes = node.wrapper.nodes
        // Assert
        assertEquals(expected = 1, actual = nodes.size)
        with(nodes.first()) {
            assertIs<PathNodes.ReflectiveCurveTo>(this)
            assertFalse(isRelative)
        }
    }

    @Test
    fun `ensure materialize generates reflectiveCurveTo instruction properly`() {
        // Arrange
        val nonRelative = ReflectiveCurveToParams(
            x1 = 15f,
            y1 = 12f,
            x2 = 10f,
            y2 = 5f,
        )
        val relative = ReflectiveCurveToParams(
            x1 = 321f,
            y1 = 125f,
            x2 = 510f,
            y2 = 51f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "S$nonRelative s$relative"),
        )
        // Act
        val node = with(logger) { path.asNode(minified = false) } as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.emit() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                |// S $this
                |reflectiveCurveTo(
                |    x1 = ${x1.toStringConsistent()}f,
                |    y1 = ${y1.toStringConsistent()}f,
                |    x2 = ${x2.toStringConsistent()}f,
                |    y2 = ${y2.toStringConsistent()}f,
                |)
                |
                """.trimMargin(),
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |// s $this
                |reflectiveCurveToRelative(
                |    dx1 = ${x1.toStringConsistent()}f,
                |    dy1 = ${y1.toStringConsistent()}f,
                |    dx2 = ${x2.toStringConsistent()}f,
                |    dy2 = ${y2.toStringConsistent()}f,
                |)
                |
                """.trimMargin(),
            )
        }
    }

    @Test
    fun `ensure materialize generates reflectiveCurveTo with a close instruction properly`() {
        // Arrange
        val nonRelative = ReflectiveCurveToParams(
            x1 = 15f,
            y1 = 12f,
            x2 = 10f,
            y2 = 5f,
        )
        val relative = ReflectiveCurveToParams(
            x1 = 321f,
            y1 = 125f,
            x2 = 510f,
            y2 = 51f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "S${nonRelative}z s${relative}z"),
        )
        // Act
        val node = with(logger) { path.asNode(minified = false) } as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.emit() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                |// S ${this}z
                |reflectiveCurveTo(
                |    x1 = ${x1.toStringConsistent()}f,
                |    y1 = ${y1.toStringConsistent()}f,
                |    x2 = ${x2.toStringConsistent()}f,
                |    y2 = ${y2.toStringConsistent()}f,
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
                |// s ${this}z
                |reflectiveCurveToRelative(
                |    dx1 = ${x1.toStringConsistent()}f,
                |    dy1 = ${y1.toStringConsistent()}f,
                |    dx2 = ${x2.toStringConsistent()}f,
                |    dy2 = ${y2.toStringConsistent()}f,
                |)
                |close()
                |
                """.trimMargin(),
            )
        }
    }

    @Test
    fun `ensure materialize generates reflectiveCurveTo with inlined parameters and no comment`() {
        // Arrange
        val nonRelative = ReflectiveCurveToParams(
            x1 = 15f,
            y1 = 12f,
            x2 = 10f,
            y2 = 5f,
        )
        val relative = ReflectiveCurveToParams(
            x1 = 321f,
            y1 = 125f,
            x2 = 510f,
            y2 = 51f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "S$nonRelative s$relative"),
        )
        // Act
        val node = with(logger) { path.asNode(minified = true) } as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.emit() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = buildString {
                    append("reflectiveCurveTo(")
                    append("x1 = ${x1.toStringConsistent()}f,")
                    append(" y1 = ${y1.toStringConsistent()}f,")
                    append(" x2 = ${x2.toStringConsistent()}f,")
                    append(" y2 = ${y2.toStringConsistent()}f")
                    append(")")
                },
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = buildString {
                    append("reflectiveCurveToRelative(")
                    append("dx1 = ${x1.toStringConsistent()}f,")
                    append(" dy1 = ${y1.toStringConsistent()}f,")
                    append(" dx2 = ${x2.toStringConsistent()}f,")
                    append(" dy2 = ${y2.toStringConsistent()}f")
                    append(")")
                },
            )
        }
    }

    @Test
    fun `ensure materialize generates reflectiveCurveTo with inlined parameters and no comment and close instruction when minified`() {
        // Arrange
        val nonRelative = ReflectiveCurveToParams(
            x1 = 15f,
            y1 = 12f,
            x2 = 10f,
            y2 = 5f,
        )
        val relative = ReflectiveCurveToParams(
            x1 = 321f,
            y1 = 125f,
            x2 = 510f,
            y2 = 51f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "S${nonRelative}z s${relative}z"),
        )
        // Act
        val node = with(logger) { path.asNode(minified = true) } as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.emit() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = buildString {
                    append("reflectiveCurveTo(")
                    append("x1 = ${x1.toStringConsistent()}f,")
                    append(" y1 = ${y1.toStringConsistent()}f,")
                    append(" x2 = ${x2.toStringConsistent()}f,")
                    append(" y2 = ${y2.toStringConsistent()}f")
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
                    append("reflectiveCurveToRelative(")
                    append("dx1 = ${x1.toStringConsistent()}f,")
                    append(" dy1 = ${y1.toStringConsistent()}f,")
                    append(" dx2 = ${x2.toStringConsistent()}f,")
                    append(" dy2 = ${y2.toStringConsistent()}f")
                    append(")")
                    appendLine()
                    append("close()")
                },
            )
        }
    }
}
