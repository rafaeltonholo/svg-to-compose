package dev.tonholo.s2c.domain

import dev.tonholo.s2c.domain.svg.SvgPathNode
import dev.tonholo.s2c.domain.xml.XmlRootNode
import dev.tonholo.s2c.domain.svg.asNode
import dev.tonholo.s2c.extensions.removeTrailingZero
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
        override fun toString(): String = "$x1 $y1".removeTrailingZero()
    }

    private val root = XmlRootNode(children = mutableSetOf())

    @Test
    fun `ensure a 't' command is parsed to ReflectiveQuadTo relative`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "t8.5.2"), // should parse to t8.5 .2
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
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "T -2.5-6.5"), // should parse to T-2.5 -6.5
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
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "T$nonRelative t$relative"),
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
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "T${nonRelative}z t${relative}z"),
        )
        // Act
        val node = path.asNode()
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                |// T ${this}z
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
                |// t ${this}z
                |reflectiveQuadToRelative(
                |    dx1 = ${x1}f,
                |    dy1 = ${y1}f,
                |)
                |close()
                |""".trimMargin()
            )
        }
    }

    @Test
    fun `ensure materialize generates reflectiveQuadTo with inlined parameters and no comment when minified`() {
        // Arrange
        val nonRelative = ReflectiveQuadToParams(
            x1 = 15f,
            y1 = 12f,
        )
        val relative = ReflectiveQuadToParams(
            x1 = 321f,
            y1 = 125f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "T$nonRelative t$relative"),
        )
        // Act
        val node = path.asNode(minified = true)
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = "reflectiveQuadTo(x1 = ${x1}f, y1 = ${y1}f)",
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = "reflectiveQuadToRelative(dx1 = ${x1}f, dy1 = ${y1}f)",
            )
        }
    }

    @Test
    fun `ensure materialize generates reflectiveQuadTo with inlined parameters and no comment and close instruction when minified`() {
        // Arrange
        val nonRelative = ReflectiveQuadToParams(
            x1 = 15f,
            y1 = 12f,
        )
        val relative = ReflectiveQuadToParams(
            x1 = 321f,
            y1 = 125f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "T${nonRelative}z t${relative}z"),
        )
        // Act
        val node = path.asNode(minified = true)
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                    |reflectiveQuadTo(x1 = ${x1}f, y1 = ${y1}f)
                    |close()
                """.trimMargin(),
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                    |reflectiveQuadToRelative(dx1 = ${x1}f, dy1 = ${y1}f)
                    |close()
                """.trimMargin(),
            )
        }
    }
}
