package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.extensions.removeTrailingZero
import dev.tonholo.s2c.extensions.toInt
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class SvgPathArcToTests : BaseSvgTest() {
    data class ArcParams(
        val a: Float,
        val b: Float,
        val theta: Float,
        val isMoreThanHalf: Boolean,
        val isPositiveArc: Boolean,
        val x: Float,
        val y: Float,
    ) {
        override fun toString(): String =
            "${this.a} ${this.b} $theta ${isMoreThanHalf.toInt()} ${isPositiveArc.toInt()} $x $y"
                .removeTrailingZero()
    }

    @Test
    fun `ensure a 'a' command is parsed to ArcTo relative`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf(
                "d" to "a 5 3 20 0 1 8 8",
            ),
        )
        // Act
        val node = path.asNode(minified = false) as ImageVectorNode.Path
        val nodes = node.wrapper.nodes
        // Assert
        assertEquals(expected = 1, actual = nodes.size)
        with(nodes.first()) {
            assertIs<PathNodes.ArcTo>(this)
            assertTrue(isRelative)
        }
    }

    @Test
    fun `ensure no space after flags doesn't break the logic`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf(
                "d" to "a20 60 45 0130 20 " +
                    "a20 60 45 01-30 20 " +
                    "a20 60 45 01-.30 20 " +
                    "a20 60 45 10-.30 20 " +
                    "a20 60 45 11-.30 20 " +
                    "a20 60 45 01.3 20 " +
                    "a20 60 45 01.0 20 " +
                    "a20 60 45 11.0 20 " +
                    "a20 60 45 0 1 30 20",
            ),
        )
        // Act
        val node = path.asNode(minified = true) as ImageVectorNode.Path
        val nodes = node.wrapper.nodes
        // Assert
        assertEquals(expected = 9, actual = nodes.size)
        assertEquals(expected = 9, actual = nodes.filterIsInstance<PathNodes.ArcTo>().size)

        val commands = nodes.map {
            it.buildParameters().apply { println(this) }.size
        }.toSet()
        assertEquals(expected = 1, actual = commands.size)
    }

    @Test
    fun `ensure a 'A' command is parsed to ArcTo`() {
        // Arrange
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf(
                "d" to "A 5 3 20 0 1 8 8",
            ),
        )
        // Act
        val node = path.asNode(minified = false) as ImageVectorNode.Path
        val nodes = node.wrapper.nodes
        // Assert
        assertEquals(expected = 1, actual = nodes.size)
        with(nodes.first()) {
            assertIs<PathNodes.ArcTo>(this)
            assertFalse(isRelative)
        }
    }

    @Test
    fun `ensure materialize generates arcTo instruction properly`() {
        // Arrange
        val nonRelative = ArcParams(
            a = 5f,
            b = 3f,
            theta = 20f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            x = 8f,
            y = 8f,
        )
        val relative = ArcParams(
            a = 15f,
            b = 13f,
            theta = 50f,
            isMoreThanHalf = true,
            isPositiveArc = false,
            x = 18f,
            y = 80f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf(
                "d" to "A$nonRelative a$relative",
            ),
        )
        // Act
        val node = path.asNode(minified = false) as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                |// A $this
                |arcTo(
                |    horizontalEllipseRadius = ${a}f,
                |    verticalEllipseRadius = ${b}f,
                |    theta = ${theta}f,
                |    isMoreThanHalf = $isMoreThanHalf,
                |    isPositiveArc = $isPositiveArc,
                |    x1 = ${x}f,
                |    y1 = ${y}f,
                |)
                |""".trimMargin()
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |// a $this
                |arcToRelative(
                |    a = ${a}f,
                |    b = ${b}f,
                |    theta = ${theta}f,
                |    isMoreThanHalf = $isMoreThanHalf,
                |    isPositiveArc = $isPositiveArc,
                |    dx1 = ${x}f,
                |    dy1 = ${y}f,
                |)
                |""".trimMargin()
            )
        }
    }

    @Test
    fun `ensure materialize generates arcTo with a close instruction properly`() {
        // Arrange
        val nonRelative = ArcParams(
            a = 5f,
            b = 3f,
            theta = 20f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            x = 8f,
            y = 8f,
        )
        val relative = ArcParams(
            a = 15f,
            b = 13f,
            theta = 50f,
            isMoreThanHalf = true,
            isPositiveArc = false,
            x = 18f,
            y = 80f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf(
                "d" to "A${nonRelative}z a${relative}z",
            ),
        )
        // Act
        val node = path.asNode(minified = false) as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = """
                |// A ${this}z
                |arcTo(
                |    horizontalEllipseRadius = ${a}f,
                |    verticalEllipseRadius = ${b}f,
                |    theta = ${theta}f,
                |    isMoreThanHalf = $isMoreThanHalf,
                |    isPositiveArc = $isPositiveArc,
                |    x1 = ${x}f,
                |    y1 = ${y}f,
                |)
                |close()
                |""".trimMargin()
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = """
                |// a ${this}z
                |arcToRelative(
                |    a = ${a}f,
                |    b = ${b}f,
                |    theta = ${theta}f,
                |    isMoreThanHalf = $isMoreThanHalf,
                |    isPositiveArc = $isPositiveArc,
                |    dx1 = ${x}f,
                |    dy1 = ${y}f,
                |)
                |close()
                |""".trimMargin()
            )
        }
    }

    @Test
    fun `ensure materialize generates arcTo with inlined parameters and no comment when minified`() {
        // Arrange
        val nonRelative = ArcParams(
            a = 5f,
            b = 3f,
            theta = 20f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            x = 8f,
            y = 8f,
        )
        val relative = ArcParams(
            a = 15f,
            b = 13f,
            theta = 50f,
            isMoreThanHalf = true,
            isPositiveArc = false,
            x = 18f,
            y = 80f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf(
                "d" to "A$nonRelative a$relative",
            ),
        )
        // Act
        val node = path.asNode(minified = true) as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = buildString {
                    append("arcTo(")
                    append("horizontalEllipseRadius = ${a}f,")
                    append(" verticalEllipseRadius = ${b}f,")
                    append(" theta = ${theta}f,")
                    append(" isMoreThanHalf = $isMoreThanHalf,")
                    append(" isPositiveArc = $isPositiveArc,")
                    append(" x1 = ${x}f,")
                    append(" y1 = ${y}f")
                    append(")")
                },
            )
        }
        with(relative) {
            assertContains(
                array = materialized,
                element = buildString {
                    append("arcToRelative(")
                    append("a = ${a}f,")
                    append(" b = ${b}f,")
                    append(" theta = ${theta}f,")
                    append(" isMoreThanHalf = $isMoreThanHalf,")
                    append(" isPositiveArc = $isPositiveArc,")
                    append(" dx1 = ${x}f,")
                    append(" dy1 = ${y}f")
                    append(")")
                },
            )
        }
    }

    @Test
    fun `ensure materialize generates arcTo with inlined parameters and no comment with close instruction when minified`() {
        // Arrange
        val nonRelative = ArcParams(
            a = 5f,
            b = 3f,
            theta = 20f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            x = 8f,
            y = 8f,
        )
        val relative = ArcParams(
            a = 15f,
            b = 13f,
            theta = 50f,
            isMoreThanHalf = true,
            isPositiveArc = false,
            x = 18f,
            y = 80f,
        )
        val path = SvgPathNode(
            parent = root,
            attributes = mutableMapOf(
                "d" to "A${nonRelative}z a${relative}z",
            ),
        )
        // Act
        val node = path.asNode(minified = true) as ImageVectorNode.Path
        val materialized = node.wrapper.nodes.map { it.materialize() }.toTypedArray()

        // Assert
        with(nonRelative) {
            assertContains(
                array = materialized,
                element = buildString {
                    append("arcTo(")
                    append("horizontalEllipseRadius = ${a}f,")
                    append(" verticalEllipseRadius = ${b}f,")
                    append(" theta = ${theta}f,")
                    append(" isMoreThanHalf = $isMoreThanHalf,")
                    append(" isPositiveArc = $isPositiveArc,")
                    append(" x1 = ${x}f,")
                    append(" y1 = ${y}f")
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
                    append("arcToRelative(")
                    append("a = ${a}f,")
                    append(" b = ${b}f,")
                    append(" theta = ${theta}f,")
                    append(" isMoreThanHalf = $isMoreThanHalf,")
                    append(" isPositiveArc = $isPositiveArc,")
                    append(" dx1 = ${x}f,")
                    append(" dy1 = ${y}f")
                    append(")")
                    appendLine()
                    append("close()")
                }
            )
        }
    }
}
