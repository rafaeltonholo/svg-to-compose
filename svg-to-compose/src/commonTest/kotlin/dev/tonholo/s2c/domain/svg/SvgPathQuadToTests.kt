package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.extensions.removeTrailingZero
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class SvgPathQuadToTests : BaseSvgTest() {
    data class QuadToParams(
        val x1: Float,
        val y1: Float,
        val x2: Float,
        val y2: Float,
    ) {
        override fun toString(): String = "$x1 $y1 $x2 $y2".removeTrailingZero()
    }

    @Test
    fun `ensure a 'q' command is parsed to QuadTo relative`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "q8,2 8,8"),
        )
        // Act
        val node = path.asNode(minified = false) as ImageVectorNode.Path
        val nodes = node.wrapper.nodes
        // Assert
        assertEquals(expected = 1, actual = nodes.size)
        with(nodes.first()) {
            assertIs<PathNodes.QuadTo>(this)
            assertTrue(isRelative)
        }
    }

    @Test
    fun `ensure a 'S' command is parsed to QuadTo`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "Q2,-2 4,5"),
        )
        // Act
        val node = path.asNode(minified = false) as ImageVectorNode.Path
        val nodes = node.wrapper.nodes
        // Assert
        assertEquals(expected = 1, actual = nodes.size)
        with(nodes.first()) {
            assertIs<PathNodes.QuadTo>(this)
            assertFalse(isRelative)
        }
    }

    @Test
    fun `ensure materialize generates quadTo instruction properly`() {
        // Arrange
        val nonRelative = QuadToParams(
            x1 = 15f,
            y1 = 12f,
            x2 = 10f,
            y2 = 5f,
        )
        val relative = QuadToParams(
            x1 = 321f,
            y1 = 125f,
            x2 = 510f,
            y2 = 51f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "Q$nonRelative q$relative"),
        )
        // Act
        val node = path.asNode(minified = false) as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                |// Q $this
                |quadTo(
                |    x1 = ${x1}f,
                |    y1 = ${y1}f,
                |    x2 = ${x2}f,
                |    y2 = ${y2}f,
                |)
                |""".trimMargin()
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |// q $this
                |quadToRelative(
                |    dx1 = ${x1}f,
                |    dy1 = ${y1}f,
                |    dx2 = ${x2}f,
                |    dy2 = ${y2}f,
                |)
                |""".trimMargin()
            )
        }
    }

    @Test
    fun `ensure materialize generates quadTo with a close instruction properly`() {
        // Arrange
        val nonRelative = QuadToParams(
            x1 = 15f,
            y1 = 12f,
            x2 = 10f,
            y2 = 5f,
        )
        val relative = QuadToParams(
            x1 = 321f,
            y1 = 125f,
            x2 = 510f,
            y2 = 51f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "Q${nonRelative}z q${relative}z"),
        )
        // Act
        val node = path.asNode(minified = false) as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                |// Q ${this}z
                |quadTo(
                |    x1 = ${x1}f,
                |    y1 = ${y1}f,
                |    x2 = ${x2}f,
                |    y2 = ${y2}f,
                |)
                |close()
                |""".trimMargin()
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |// q ${this}z
                |quadToRelative(
                |    dx1 = ${x1}f,
                |    dy1 = ${y1}f,
                |    dx2 = ${x2}f,
                |    dy2 = ${y2}f,
                |)
                |close()
                |""".trimMargin()
            )
        }
    }

    @Test
    fun `ensure materialize generates quadTo with inlined parameters and no comment`() {
        // Arrange
        val nonRelative = QuadToParams(
            x1 = 15f,
            y1 = 12f,
            x2 = 10f,
            y2 = 5f,
        )
        val relative = QuadToParams(
            x1 = 321f,
            y1 = 125f,
            x2 = 510f,
            y2 = 51f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "Q$nonRelative q$relative"),
        )
        // Act
        val node = path.asNode(minified = true) as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = buildString {
                    append("quadTo(")
                    append("x1 = ${x1}f,")
                    append(" y1 = ${y1}f,")
                    append(" x2 = ${x2}f,")
                    append(" y2 = ${y2}f")
                    append(")")
                },
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = buildString {
                    append("quadToRelative(")
                    append("dx1 = ${x1}f,")
                    append(" dy1 = ${y1}f,")
                    append(" dx2 = ${x2}f,")
                    append(" dy2 = ${y2}f")
                    append(")")
                },
            )
        }
    }

    @Test
    fun `ensure materialize generates quadTo with inlined parameters and no comment with close instruction when minified`() {
        // Arrange
        val nonRelative = QuadToParams(
            x1 = 15f,
            y1 = 12f,
            x2 = 10f,
            y2 = 5f,
        )
        val relative = QuadToParams(
            x1 = 321f,
            y1 = 125f,
            x2 = 510f,
            y2 = 51f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "Q${nonRelative}z q${relative}z"),
        )
        // Act
        val node = path.asNode(minified = true) as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = buildString {
                    append("quadTo(")
                    append("x1 = ${x1}f,")
                    append(" y1 = ${y1}f,")
                    append(" x2 = ${x2}f,")
                    append(" y2 = ${y2}f")
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
                    append("quadToRelative(")
                    append("dx1 = ${x1}f,")
                    append(" dy1 = ${y1}f,")
                    append(" dx2 = ${x2}f,")
                    append(" dy2 = ${y2}f")
                    append(")")
                    appendLine()
                    append("close()")
                },
            )
        }
    }
}
