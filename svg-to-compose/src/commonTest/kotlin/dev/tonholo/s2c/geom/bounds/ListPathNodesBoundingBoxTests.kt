package dev.tonholo.s2c.geom.bounds

import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.PathNodes.ArcTo
import dev.tonholo.s2c.domain.PathNodes.CurveTo
import dev.tonholo.s2c.domain.PathNodes.HorizontalLineTo
import dev.tonholo.s2c.domain.PathNodes.LineTo
import dev.tonholo.s2c.domain.PathNodes.MoveTo
import dev.tonholo.s2c.domain.PathNodes.QuadTo
import dev.tonholo.s2c.domain.PathNodes.ReflectiveCurveTo
import dev.tonholo.s2c.domain.PathNodes.VerticalLineTo
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ListPathNodesBoundingBoxTests {
    @Test
    fun `ensure boundingBox calculation for empty list returns NoBoundingBox`() {
        val nodes: List<PathNodes> = emptyList()
        val boundingBox = nodes.boundingBox()
        assertTrue(boundingBox is BoundingBox.NoBoundingBox)
    }

    @Test
    fun `ensure boundingBox calculation for single MoveTo node works`() {
        val nodes = listOf(
            MoveTo(listOf("1.0", "2.0"), isRelative = false, minified = false),
        )
        val boundingBox = nodes.boundingBox()
        assertEquals(expected = 1.0, actual = boundingBox.x)
        assertEquals(expected = 2.0, actual = boundingBox.y)
        assertEquals(expected = 0.0, actual = boundingBox.width)
        assertEquals(expected = 0.0, actual = boundingBox.height)
    }

    @Test
    fun `ensure boundingBox calculation for multiple LineTo nodes works`() {
        val nodes = listOf(
            MoveTo(listOf("1.0", "2.0"), isRelative = false, minified = false),
            LineTo(listOf("13.0", "14.0"), isRelative = false, minified = false),
            LineTo(listOf("15.0", "16.0"), isRelative = false, minified = false),
        )
        val boundingBox = nodes.boundingBox()
        assertEquals(expected = 1.0, actual = boundingBox.x)
        assertEquals(expected = 2.0, actual = boundingBox.y)
        assertEquals(expected = 14.0, actual = boundingBox.width)
        assertEquals(expected = 14.0, actual = boundingBox.height)
    }

    @Test
    fun `ensure boundingBox calculation for mixed nodes works`() {
        val nodes = listOf(
            MoveTo(listOf("1.0", "2.0"), isRelative = false, minified = false),
            LineTo(listOf("3.0", "4.0"), isRelative = false, minified = false),
            VerticalLineTo(listOf("16.0"), isRelative = false, minified = false),
            HorizontalLineTo(listOf("-15.0"), isRelative = false, minified = false),
        )
        val boundingBox = nodes.boundingBox()
        assertEquals(expected = -15.0, actual = boundingBox.x)
        assertEquals(expected = 2.0, actual = boundingBox.y)
        assertEquals(expected = 18.0, actual = boundingBox.width)
        assertEquals(expected = 14.0, actual = boundingBox.height)
    }

    @Test
    fun `ensure boundingBox calculation for CurveTo node works`() {
        val nodes = listOf(
            MoveTo(listOf("1.0", "2.0"), isRelative = false, minified = false),
            CurveTo(listOf("3.0", "4.0", "5.0", "6.0", "7.0", "8.0"), isRelative = false, minified = false),
        )
        val boundingBox = nodes.boundingBox()
        assertEquals(expected = 1.0, actual = boundingBox.x)
        assertEquals(expected = 2.0, actual = boundingBox.y)
        assertEquals(expected = 6.0, actual = boundingBox.width)
        assertEquals(expected = 6.0, actual = boundingBox.height)
    }

    @Test
    fun `ensure boundingBox calculation for ArcTo node works`() {
        val nodes = listOf(
            MoveTo(listOf("50", "50"), isRelative = false, minified = false),
            ArcTo(listOf("38.463", "38.463", "0", "0", "0", "46.74", "41.353"), isRelative = false, minified = false),
        )
        val boundingBox = nodes.boundingBox()
        assertEquals(46.7400016784668, boundingBox.x)
        assertEquals(41.35300064086914, boundingBox.y)
        assertEquals(3.259998321533203, boundingBox.width)
        assertEquals(8.64699935913086, boundingBox.height)
    }

    @Test
    fun `ensure boundingBox calculation for QuadTo node works`() {
        val nodes = listOf(
            MoveTo(listOf("1.0", "2.0"), isRelative = false, minified = false),
            QuadTo(listOf("3.0", "4.0", "5.0", "6.0"), isRelative = false, minified = false),
        )
        val boundingBox = nodes.boundingBox()
        assertEquals(expected = 1.0, actual = boundingBox.x)
        assertEquals(expected = 2.0, actual = boundingBox.y)
        assertEquals(expected = 4.0, actual = boundingBox.width)
        assertEquals(expected = 4.0, actual = boundingBox.height)
    }

    @Test
    fun `ensure boundingBox calculation for HorizontalLineTo node works`() {
        val nodes = listOf(
            MoveTo(listOf("1.0", "2.0"), isRelative = false, minified = false),
            HorizontalLineTo(listOf("3.0"), isRelative = false, minified = false),
        )
        val boundingBox = nodes.boundingBox()
        assertEquals(expected = 1.0, actual = boundingBox.x)
        assertEquals(expected = 2.0, actual = boundingBox.y)
        assertEquals(expected = 2.0, actual = boundingBox.width)
        assertEquals(expected = 0.0, actual = boundingBox.height)
    }

    @Test
    fun `ensure boundingBox calculation for VerticalLineTo node works`() {
        val nodes = listOf(
            MoveTo(listOf("1.0", "2.0"), isRelative = false, minified = false),
            VerticalLineTo(listOf("3.0"), isRelative = false, minified = false),
        )
        val boundingBox = nodes.boundingBox()
        assertEquals(expected = 1.0, actual = boundingBox.x)
        assertEquals(expected = 2.0, actual = boundingBox.y)
        assertEquals(expected = 0.0, actual = boundingBox.width)
        assertEquals(expected = 1.0, actual = boundingBox.height)
    }

    @Test
    fun `ensure boundingBox calculation for negative coordinates works`() {
        val nodes = listOf(
            MoveTo(listOf("-1.0", "-2.0"), isRelative = false, minified = false),
            LineTo(listOf("-3.0", "-4.0"), isRelative = false, minified = false),
            LineTo(listOf("-5.0", "-6.0"), isRelative = false, minified = false),
        )
        val boundingBox = nodes.boundingBox()
        assertEquals(expected = -5.0, actual = boundingBox.x)
        assertEquals(expected = -6.0, actual = boundingBox.y)
        assertEquals(expected = 4.0, actual = boundingBox.width)
        assertEquals(expected = 4.0, actual = boundingBox.height)
    }

    @Test
    fun `ensure boundingBox calculation for relative path nodes throws IllegalStateException`() {
        val nodes = listOf(
            MoveTo(listOf("1.0", "2.0"), isRelative = true, minified = false),
        )
        val exception = assertFailsWith<IllegalStateException> {
            nodes.boundingBox()
        }
        assertEquals(
            expected = "Relative path nodes are not supported. " +
                "Call List<PathNodes>.toAbsolute() before calling boundingBox().",
            actual = exception.message
        )
    }

    @Test
    fun `ensure boundingBox calculation for unsupported reflective nodes throws IllegalStateException`() {
        val nodes = listOf(
            MoveTo(listOf("1.0", "2.0"), isRelative = false, minified = false),
            ReflectiveCurveTo(listOf("3.0", "4.0", "5.0", "6.0"), isRelative = false, minified = false),
        )
        val exception = assertFailsWith<IllegalStateException> {
            nodes.boundingBox()
        }
        assertEquals(
            expected = "ReflectiveCurveTo and ReflectiveQuadTo are not supported. " +
                "Call List<PathNodes>.removeShorthandNodes() before calling boundingBox().",
            actual = exception.message,
        )
    }

    @Test
    fun `ensure boundingBox calculation for mixed relative and absolute nodes throws IllegalStateException`() {
        val nodes = listOf(
            MoveTo(listOf("1.0", "2.0"), isRelative = false, minified = false),
            LineTo(listOf("3.0", "4.0"), isRelative = true, minified = false),
        )
        val exception = assertFailsWith<IllegalStateException> {
            nodes.boundingBox()
        }
        assertEquals(
            expected = "Relative path nodes are not supported. " +
                "Call List<PathNodes>.toAbsolute() before calling boundingBox().",
            actual = exception.message,
        )
    }
}
