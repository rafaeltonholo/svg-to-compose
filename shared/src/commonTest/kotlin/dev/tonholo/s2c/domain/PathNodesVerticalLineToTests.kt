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

class PathNodesVerticalLineToTests {
    data class VerticalLineParams(
        val y: Float,
    ) {
        override fun toString(): String = "$y".removeTrailingZero()
    }

    private val root = XmlRootNode(children = mutableSetOf())

    @Test
    fun `ensure a 'v' command is parsed to VerticalLineTo relative`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "v 8"),
        )
        // Act
        val node = path.asNode()
        val nodes = node.wrapper.nodes
        // Assert
        assertEquals(expected = 1, actual = nodes.size)
        with(nodes.first()) {
            assertIs<PathNodes.VerticalLineTo>(this)
            assertTrue(isRelative)
        }
    }

    @Test
    fun `ensure a 'V' command is parsed to VerticalLineTo`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "V 8"),
        )
        // Act
        val node = path.asNode()
        val nodes = node.wrapper.nodes
        // Assert
        assertEquals(expected = 1, actual = nodes.size)
        with(nodes.first()) {
            assertIs<PathNodes.VerticalLineTo>(this)
            assertFalse(isRelative)
        }
    }

    @Test
    fun `ensure materialize generates verticalLineTo instruction properly`() {
        // Arrange
        val nonRelative = VerticalLineParams(
            y = 8f,
        )
        val relative = VerticalLineParams(
            y = 80f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "V$nonRelative v$relative"),
        )
        // Act
        val node = path.asNode()
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                |// V $this
                |verticalLineTo(y = ${y}f)
                |""".trimMargin()
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |// v $this
                |verticalLineToRelative(dy = ${y}f)
                |""".trimMargin()
            )
        }
    }

    @Test
    fun `ensure materialize generates verticalLineTo with a close instruction properly`() {
        // Arrange
        val nonRelative = VerticalLineParams(
            y = 8f,
        )
        val relative = VerticalLineParams(
            y = 80f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "V${nonRelative}z v${relative}z"),
        )
        // Act
        val node = path.asNode()
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                |// V ${this}z
                |verticalLineTo(y = ${y}f)
                |close()
                |""".trimMargin()
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |// v ${this}z
                |verticalLineToRelative(dy = ${y}f)
                |close()
                |""".trimMargin()
            )
        }
    }

    @Test
    fun `ensure materialize generates verticalLineTo with inlined parameters and no comment when minified`() {
        // Arrange
        val nonRelative = VerticalLineParams(
            y = 8f,
        )
        val relative = VerticalLineParams(
            y = 80f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "V$nonRelative v$relative"),
        )
        // Act
        val node = path.asNode(minified = true)
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = "verticalLineTo(y = ${y}f)",
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = "verticalLineToRelative(dy = ${y}f)",
            )
        }
    }

    @Test
    fun `ensure materialize generates verticalLineTo with inlined parameters and no comment and close instruction when minified`() {
        // Arrange
        val nonRelative = VerticalLineParams(
            y = 8f,
        )
        val relative = VerticalLineParams(
            y = 80f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "V${nonRelative}z v${relative}z"),
        )
        // Act
        val node = path.asNode(minified = true)
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                |verticalLineTo(y = ${y}f)
                |close()
                """.trimMargin(),
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |verticalLineToRelative(dy = ${y}f)
                |close()
                """.trimMargin(),
            )
        }
    }
}
