package dev.tonholo.s2c.domain

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

class PathNodesMoveToTests {
    private data class MoveToParams(val x: String, val y: String) {
        override fun toString(): String =
            "$x $y".removeTrailingZero()
    }

    private val root = XmlRootNode(children = mutableSetOf())

    @Test
    fun `ensure a 'm' command is parsed to MoveTo relative`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "m85.122 64.795"),
        )
        // Act
        val node = path.asNode() as ImageVectorNode.Path
        val nodes = node.wrapper.nodes
        // Assert
        assertEquals(expected = 1, actual = nodes.size)
        with(nodes.first()) {
            assertIs<PathNodes.MoveTo>(this)
            assertTrue(isRelative)
        }
    }

    @Test
    fun `ensure a 'M' command is parsed to MoveTo`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "M85.122 64.795"),
        )
        // Act
        val node = path.asNode() as ImageVectorNode.Path
        val nodes = node.wrapper.nodes
        // Assert
        assertEquals(expected = 1, actual = nodes.size)
        with(nodes.first()) {
            assertIs<PathNodes.MoveTo>(this)
            assertFalse(isRelative)
        }
    }

    @Test
    fun `ensure materialize generates moveTo instruction properly`() {
        // Arrange
        val nonRelativeMoveToParams = MoveToParams(x = "85.122", y = "64.795")
        val relativeMoveToParams = MoveToParams(x = "123", y = "654")
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "M${nonRelativeMoveToParams.x} ${nonRelativeMoveToParams.y} m${relativeMoveToParams.x} ${relativeMoveToParams.y}"),
        )
        // Act
        val node = path.asNode() as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelativeMoveToParams) {
            val x = this.x.toFloat()
            val y = this.y.toFloat()
            assertContains(
                array = materialized,
                element = """
                |// M $this
                |moveTo(x = ${x}f, y = ${y}f)
                |
            """.trimMargin()
            )
        }
        with(relativeMoveToParams) {
            val x = this.x.toFloat()
            val y = this.y.toFloat()
            assertContains(
                array = materialized,
                element = """
                |// m $this
                |moveToRelative(dx = ${x}f, dy = ${y}f)
                |
            """.trimMargin()
            )
        }
    }

    @Test
    fun `ensure materialize generates moveTo with a close instruction properly`() {
        // Arrange
        val nonRelativeMoveToParams = MoveToParams(x = "85.122", y = "64.795")
        val relativeMoveToParams = MoveToParams(x = "123", y = "654")
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "M${nonRelativeMoveToParams.x} ${nonRelativeMoveToParams.y}z m${relativeMoveToParams.x} ${relativeMoveToParams.y}z"),
        )
        // Act
        val node = path.asNode() as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelativeMoveToParams) {
            val x = this.x.toFloat()
            val y = this.y.toFloat()
            assertContains(
                array = materialized,
                element = """
                |// M ${this}z
                |moveTo(x = ${x}f, y = ${y}f)
                |close()
                |
            """.trimMargin()
            )
        }
        with(relativeMoveToParams) {
            val x = this.x.toFloat()
            val y = this.y.toFloat()
            assertContains(
                array = materialized,
                element = """
                |// m ${this}z
                |moveToRelative(dx = ${x}f, dy = ${y}f)
                |close()
                |
            """.trimMargin()
            )
        }
    }

    @Test
    fun `ensure materialize generates moveTo with no comment when minified`() {
        // Arrange
        val nonRelativeMoveToParams = MoveToParams(x = "85.122", y = "64.795")
        val relativeMoveToParams = MoveToParams(x = "123", y = "654")
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "M${nonRelativeMoveToParams.x} ${nonRelativeMoveToParams.y} m${relativeMoveToParams.x} ${relativeMoveToParams.y}"),
        )
        // Act
        val node = path.asNode(minified = true) as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelativeMoveToParams) {
            val x = this.x.toFloat()
            val y = this.y.toFloat()
            assertContains(
                array = materialized,
                element = "moveTo(x = ${x}f, y = ${y}f)",
            )
        }
        with(relativeMoveToParams) {
            val x = this.x.toFloat()
            val y = this.y.toFloat()
            assertContains(
                array = materialized,
                element = "moveToRelative(dx = ${x}f, dy = ${y}f)"
            )
        }
    }

    @Test
    fun `ensure materialize generates moveTo with no comment and a close instruction when minified`() {
        // Arrange
        val nonRelativeMoveToParams = MoveToParams(x = "85.122", y = "64.795")
        val relativeMoveToParams = MoveToParams(x = "123", y = "654")
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf("d" to "M${nonRelativeMoveToParams.x} ${nonRelativeMoveToParams.y}z m${relativeMoveToParams.x} ${relativeMoveToParams.y}z"),
        )
        // Act
        val node = path.asNode(minified = true) as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelativeMoveToParams) {
            val x = this.x.toFloat()
            val y = this.y.toFloat()
            assertContains(
                array = materialized,
                element = """
                    |moveTo(x = ${x}f, y = ${y}f)
                    |close()
                """.trimMargin(),
            )
        }
        with(relativeMoveToParams) {
            val x = this.x.toFloat()
            val y = this.y.toFloat()
            assertContains(
                array = materialized,
                element = """
                    |moveToRelative(dx = ${x}f, dy = ${y}f)
                    |close()
                """.trimMargin(),
            )
        }
    }
}
