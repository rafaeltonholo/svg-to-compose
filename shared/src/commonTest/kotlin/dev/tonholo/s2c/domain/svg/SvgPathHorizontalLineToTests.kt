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

class SvgPathHorizontalLineToTests : BaseSvgTest() {
    data class HorizontalLineParams(
        val x: Float,
    ) {
        override fun toString(): String = "$x".removeTrailingZero()
    }

    @Test
    fun `ensure a 'h' command is parsed to HorizontalLineTo relative`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "h 8"),
        )
        // Act
        val node = path.asNode() as ImageVectorNode.Path
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
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "H 8"),
        )
        // Act
        val node = path.asNode() as ImageVectorNode.Path
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
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "H$nonRelative h$relative"),
        )
        // Act
        val node = path.asNode() as ImageVectorNode.Path
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
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "H${nonRelative}z h${relative}z"),
        )
        // Act
        val node = path.asNode() as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                |// H ${this}z
                |horizontalLineTo(x = ${x}f)
                |close()
                |""".trimMargin()
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |// h ${this}z
                |horizontalLineToRelative(dx = ${x}f)
                |close()
                |""".trimMargin()
            )
        }
    }

    @Test
    fun `ensure materialize generates horizontalLineTo with no comment when minified`() {
        // Arrange
        val nonRelative = HorizontalLineParams(
            x = 8f,
        )
        val relative = HorizontalLineParams(
            x = 80f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "H$nonRelative h$relative"),
        )
        // Act
        val node = path.asNode(minified = true) as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = "horizontalLineTo(x = ${x}f)",
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = "horizontalLineToRelative(dx = ${x}f)",
            )
        }
    }

    @Test
    fun `ensure materialize generates horizontalLineTo with no comment with close instruction when minified`() {
        // Arrange
        val nonRelative = HorizontalLineParams(
            x = 8f,
        )
        val relative = HorizontalLineParams(
            x = 80f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "H${nonRelative}z h${relative}z"),
        )
        // Act
        val node = path.asNode(minified = true) as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                |horizontalLineTo(x = ${x}f)
                |close()""".trimMargin()
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |horizontalLineToRelative(dx = ${x}f)
                |close()""".trimMargin()
            )
        }
    }
}
