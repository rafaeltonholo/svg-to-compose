package dev.tonholo.s2c.domain

import dev.tonholo.s2c.domain.PathNodes.CurveTo
import kotlin.test.Test
import kotlin.test.assertEquals

class CurveToTests {
    @Test
    fun `materialize returns correct string representation for absolute coordinates`() {
        val curveTo = CurveTo(
            values = listOf("10", "20", "30", "40", "50", "60"),
            isRelative = false,
            minified = false
        )
        val expected = """
            |// C 10 20 30 40 50 60
            |curveTo(
            |    x1 = 10.0f,
            |    y1 = 20.0f,
            |    x2 = 30.0f,
            |    y2 = 40.0f,
            |    x3 = 50.0f,
            |    y3 = 60.0f,
            |)
            |
        """.trimMargin()

        assertEquals(expected, curveTo.materialize())
    }

    @Test
    fun `materialize returns correct string representation for relative coordinates`() {
        val curveTo = CurveTo(
            values = listOf("10", "20", "30", "40", "50", "60"),
            isRelative = true,
            minified = false
        )
        val expected = """
            |// c 10 20 30 40 50 60
            |curveToRelative(
            |    dx1 = 10.0f,
            |    dy1 = 20.0f,
            |    dx2 = 30.0f,
            |    dy2 = 40.0f,
            |    dx3 = 50.0f,
            |    dy3 = 60.0f,
            |)
            |
        """.trimMargin()

        assertEquals(expected, curveTo.materialize())
    }

    @Test
    fun `materialize returns correct string representation for absolute coordinates with a close command`() {
        val curveTo = CurveTo(
            values = listOf("10", "20", "30", "40", "50", "60z"),
            isRelative = false,
            minified = false
        )
        val expected = """
            |// C 10 20 30 40 50 60z
            |curveTo(
            |    x1 = 10.0f,
            |    y1 = 20.0f,
            |    x2 = 30.0f,
            |    y2 = 40.0f,
            |    x3 = 50.0f,
            |    y3 = 60.0f,
            |)
            |close()
            |
        """.trimMargin()

        assertEquals(expected, curveTo.materialize())
    }

    @Test
    fun `materialize returns correct string representation for relative coordinates with a close command`() {
        val curveTo = CurveTo(
            values = listOf("10", "20", "30", "40", "50", "60z"),
            isRelative = true,
            minified = false
        )
        val expected = """
            |// c 10 20 30 40 50 60z
            |curveToRelative(
            |    dx1 = 10.0f,
            |    dy1 = 20.0f,
            |    dx2 = 30.0f,
            |    dy2 = 40.0f,
            |    dx3 = 50.0f,
            |    dy3 = 60.0f,
            |)
            |close()
            |
        """.trimMargin()

        assertEquals(expected, curveTo.materialize())
    }

    @Test
    fun `materialize returns minified string representation when minified is true`() {
        val curveTo = CurveTo(
            values = listOf("10", "20", "30", "40", "50", "60"),
            isRelative = false,
            minified = true
        )
        val expected = "curveTo(x1 = 10.0f, y1 = 20.0f, x2 = 30.0f, y2 = 40.0f, x3 = 50.0f, y3 = 60.0f)"

        assertEquals(expected, curveTo.materialize())
    }

    @Test
    fun `materialize returns minified string representation when minified is true with a close command`() {
        val curveTo = CurveTo(
            values = listOf("10", "20", "30", "40", "50", "60z"),
            isRelative = false,
            minified = true
        )
        val expected = buildString {
            appendLine("curveTo(x1 = 10.0f, y1 = 20.0f, x2 = 30.0f, y2 = 40.0f, x3 = 50.0f, y3 = 60.0f)")
            append("close()")
        }

        assertEquals(expected, curveTo.materialize())
    }
}
