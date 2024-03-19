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

class PathNodesReflectiveCurveToTests {
    data class ReflectiveCurveToParams(
        val x1: Float,
        val y1: Float,
        val x2: Float,
        val y2: Float,
    ) {
        override fun toString(): String = "$x1 $y1 $x2 $y2".removeTrailingZero()
    }

    private val root = SvgElementNode(
        parent = XmlRootNode(children = mutableSetOf()),
        children = mutableSetOf(),
        attributes = mutableMapOf(),
    )

    @Test
    fun `ensure a 's' command is parsed to ReflectiveCurveTo relative`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "s2,-2 4,5"),
        )
        // Act
        val node = path.asNode() as ImageVectorNode.Path
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
        val node = path.asNode() as ImageVectorNode.Path
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
        val node = path.asNode() as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                |// S $this
                |reflectiveCurveTo(
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
                |// s $this
                |reflectiveCurveToRelative(
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
        val node = path.asNode() as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                |// S ${this}z
                |reflectiveCurveTo(
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
                |// s ${this}z
                |reflectiveCurveToRelative(
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
        val node = path.asNode(minified = true) as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = buildString {
                    append("reflectiveCurveTo(")
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
                    append("reflectiveCurveToRelative(")
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
        val node = path.asNode(minified = true) as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = buildString {
                    append("reflectiveCurveTo(")
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
                    append("reflectiveCurveToRelative(")
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
