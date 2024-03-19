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

class PathNodesLineToTests {
    data class LineParams(
        val x: Float,
        val y: Float,
    ) {
        override fun toString(): String = "$x $y".removeTrailingZero()
    }

    private val root = SvgElementNode(
        parent = XmlRootNode(children = mutableSetOf()),
        children = mutableSetOf(),
        attributes = mutableMapOf(),
    )

    @Test
    fun `ensure a 'l' command is parsed to LineTo relative`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "l 8 10"),
        )
        // Act
        val node = path.asNode() as ImageVectorNode.Path
        val nodes = node.wrapper.nodes
        // Assert
        assertEquals(expected = 1, actual = nodes.size)
        with(nodes.first()) {
            assertIs<PathNodes.LineTo>(this)
            assertTrue(isRelative)
        }
    }

    @Test
    fun `ensure a 'L' command is parsed to LineTo`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "L 8 10"),
        )
        // Act
        val node = path.asNode() as ImageVectorNode.Path
        val nodes = node.wrapper.nodes
        // Assert
        assertEquals(expected = 1, actual = nodes.size)
        with(nodes.first()) {
            assertIs<PathNodes.LineTo>(this)
            assertFalse(isRelative)
        }
    }

    @Test
    fun `ensure materialize generates lineTo instruction properly`() {
        // Arrange
        val nonRelative = LineParams(
            x = 8f,
            y = 80f,
        )
        val relative = LineParams(
            x = 80f,
            y = 10f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "L$nonRelative l$relative"),
        )
        // Act
        val node = path.asNode() as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                |// L $this
                |lineTo(x = ${x}f, y = ${y}f)
                |""".trimMargin()
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |// l $this
                |lineToRelative(dx = ${x}f, dy = ${y}f)
                |""".trimMargin()
            )
        }
    }

    @Test
    fun `ensure materialize generates lineTo with a close instruction properly`() {
        // Arrange
        val nonRelative = LineParams(
            x = 8f,
            y = 80f,
        )
        val relative = LineParams(
            x = 80f,
            y = 10f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "L${nonRelative}z l${relative}z"),
        )
        // Act
        val node = path.asNode() as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                |// L ${this}z
                |lineTo(x = ${x}f, y = ${y}f)
                |close()
                |""".trimMargin()
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |// l ${this}z
                |lineToRelative(dx = ${x}f, dy = ${y}f)
                |close()
                |""".trimMargin()
            )
        }
    }

    @Test
    fun `ensure materialize generates lineTo with no comment when minified`() {
        // Arrange
        val nonRelative = LineParams(
            x = 8f,
            y = 80f,
        )
        val relative = LineParams(
            x = 80f,
            y = 10f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "L$nonRelative l$relative"),
        )
        // Act
        val node = path.asNode(minified = true) as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = "lineTo(x = ${x}f, y = ${y}f)"
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = "lineToRelative(dx = ${x}f, dy = ${y}f)"
            )
        }
    }

    @Test
    fun `ensure materialize generates lineTo with no comment and a close instruction when minified`() {
        // Arrange
        val nonRelative = LineParams(
            x = 8f,
            y = 80f,
        )
        val relative = LineParams(
            x = 80f,
            y = 10f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "L${nonRelative}z l${relative}z"),
        )
        // Act
        val node = path.asNode(minified = true) as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                    |lineTo(x = ${x}f, y = ${y}f)
                    |close()
                """.trimMargin()
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                    |lineToRelative(dx = ${x}f, dy = ${y}f)
                    |close()
                """.trimMargin()
            )
        }
    }
}
