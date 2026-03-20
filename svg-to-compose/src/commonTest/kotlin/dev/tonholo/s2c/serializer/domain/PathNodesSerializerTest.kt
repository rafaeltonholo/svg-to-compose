package dev.tonholo.s2c.serializer.domain

import dev.tonholo.s2c.domain.PathNodes
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class PathNodesSerializerTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `ensure MoveTo round-trips correctly`() {
        // Arrange
        val original = PathNodes.MoveTo(
            values = listOf("10.0", "20.0"),
            isRelative = false,
            minified = false,
        )

        // Act
        val encoded = json.encodeToString(PathNodes.serializer(), original)
        val decoded = json.decodeFromString(PathNodes.serializer(), encoded)

        // Assert
        assertIs<PathNodes.MoveTo>(decoded)
        assertEquals(expected = 10.0, actual = decoded.x)
        assertEquals(expected = 20.0, actual = decoded.y)
        assertFalse(decoded.isRelative)
        assertFalse(decoded.minified)
    }

    @Test
    fun `ensure LineTo round-trips correctly`() {
        // Arrange
        val original = PathNodes.LineTo(
            values = listOf("30.0", "40.0"),
            isRelative = false,
            minified = false,
        )

        // Act
        val encoded = json.encodeToString(PathNodes.serializer(), original)
        val decoded = json.decodeFromString(PathNodes.serializer(), encoded)

        // Assert
        assertIs<PathNodes.LineTo>(decoded)
        assertEquals(expected = 30.0, actual = decoded.x)
        assertEquals(expected = 40.0, actual = decoded.y)
        assertFalse(decoded.isRelative)
        assertFalse(decoded.minified)
    }

    @Test
    fun `ensure close flag is preserved`() {
        // Arrange
        val original = PathNodes.MoveTo(
            values = listOf("10.0", "20.0z"),
            isRelative = false,
            minified = false,
        )

        // Act
        val encoded = json.encodeToString(PathNodes.serializer(), original)
        val decoded = json.decodeFromString(PathNodes.serializer(), encoded)

        // Assert
        assertIs<PathNodes.MoveTo>(decoded)
        assertTrue(decoded.shouldClose)
        assertEquals(expected = 10.0, actual = decoded.x)
        assertEquals(expected = 20.0, actual = decoded.y)
    }

    @Test
    fun `ensure CurveTo round-trips correctly`() {
        // Arrange
        val original = PathNodes.CurveTo(
            values = listOf("1.0", "2.0", "3.0", "4.0", "5.0", "6.0"),
            isRelative = false,
            minified = false,
        )

        // Act
        val encoded = json.encodeToString(PathNodes.serializer(), original)
        val decoded = json.decodeFromString(PathNodes.serializer(), encoded)

        // Assert
        assertIs<PathNodes.CurveTo>(decoded)
        assertEquals(expected = 1.0, actual = decoded.x1)
        assertEquals(expected = 2.0, actual = decoded.y1)
        assertEquals(expected = 3.0, actual = decoded.x2)
        assertEquals(expected = 4.0, actual = decoded.y2)
        assertEquals(expected = 5.0, actual = decoded.x3)
        assertEquals(expected = 6.0, actual = decoded.y3)
        assertFalse(decoded.isRelative)
        assertFalse(decoded.minified)
    }

    @Test
    fun `ensure CurveTo relative round-trips correctly`() {
        // Arrange
        val original = PathNodes.CurveTo(
            values = listOf("10.0", "20.0", "30.0", "40.0", "50.0", "60.0"),
            isRelative = true,
            minified = false,
        )

        // Act
        val encoded = json.encodeToString(PathNodes.serializer(), original)
        val decoded = json.decodeFromString(PathNodes.serializer(), encoded)

        // Assert
        assertIs<PathNodes.CurveTo>(decoded)
        assertTrue(decoded.isRelative)
        assertEquals(expected = 10.0, actual = decoded.x1)
        assertEquals(expected = 20.0, actual = decoded.y1)
        assertEquals(expected = 30.0, actual = decoded.x2)
        assertEquals(expected = 40.0, actual = decoded.y2)
        assertEquals(expected = 50.0, actual = decoded.x3)
        assertEquals(expected = 60.0, actual = decoded.y3)
    }

    @Test
    fun `ensure ArcTo round-trips correctly`() {
        // Arrange
        val original = PathNodes.ArcTo(
            values = listOf("25.0", "30.0", "0.0", "1", "0", "50.0", "60.0"),
            isRelative = false,
            minified = false,
        )

        // Act
        val encoded = json.encodeToString(PathNodes.serializer(), original)
        val decoded = json.decodeFromString(PathNodes.serializer(), encoded)

        // Assert
        assertIs<PathNodes.ArcTo>(decoded)
        assertEquals(expected = 25.0, actual = decoded.a)
        assertEquals(expected = 30.0, actual = decoded.b)
        assertEquals(expected = 0.0, actual = decoded.theta)
        assertTrue(decoded.isMoreThanHalf)
        assertFalse(decoded.isPositiveArc)
        assertEquals(expected = 50.0, actual = decoded.x)
        assertEquals(expected = 60.0, actual = decoded.y)
        assertFalse(decoded.isRelative)
        assertFalse(decoded.minified)
    }

    @Test
    fun `ensure ArcTo with all flags set round-trips correctly`() {
        // Arrange
        val original = PathNodes.ArcTo(
            values = listOf("10.0", "20.0", "45.0", "1", "1", "100.0", "200.0"),
            isRelative = true,
            minified = true,
        )

        // Act
        val encoded = json.encodeToString(PathNodes.serializer(), original)
        val decoded = json.decodeFromString(PathNodes.serializer(), encoded)

        // Assert
        assertIs<PathNodes.ArcTo>(decoded)
        assertEquals(expected = 10.0, actual = decoded.a)
        assertEquals(expected = 20.0, actual = decoded.b)
        assertEquals(expected = 45.0, actual = decoded.theta)
        assertTrue(decoded.isMoreThanHalf)
        assertTrue(decoded.isPositiveArc)
        assertEquals(expected = 100.0, actual = decoded.x)
        assertEquals(expected = 200.0, actual = decoded.y)
        assertTrue(decoded.isRelative)
        assertTrue(decoded.minified)
    }

    @Test
    fun `ensure VerticalLineTo round-trips correctly`() {
        // Arrange
        val original = PathNodes.VerticalLineTo(
            values = listOf("42.0"),
            isRelative = false,
            minified = false,
        )

        // Act
        val encoded = json.encodeToString(PathNodes.serializer(), original)
        val decoded = json.decodeFromString(PathNodes.serializer(), encoded)

        // Assert
        assertIs<PathNodes.VerticalLineTo>(decoded)
        assertEquals(expected = 42.0, actual = decoded.y)
        assertFalse(decoded.isRelative)
    }

    @Test
    fun `ensure HorizontalLineTo round-trips correctly`() {
        // Arrange
        val original = PathNodes.HorizontalLineTo(
            values = listOf("15.0"),
            isRelative = false,
            minified = false,
        )

        // Act
        val encoded = json.encodeToString(PathNodes.serializer(), original)
        val decoded = json.decodeFromString(PathNodes.serializer(), encoded)

        // Assert
        assertIs<PathNodes.HorizontalLineTo>(decoded)
        assertEquals(expected = 15.0, actual = decoded.x)
        assertFalse(decoded.isRelative)
    }

    @Test
    fun `ensure QuadTo round-trips correctly`() {
        // Arrange
        val original = PathNodes.QuadTo(
            values = listOf("10.0", "20.0", "30.0", "40.0"),
            isRelative = false,
            minified = false,
        )

        // Act
        val encoded = json.encodeToString(PathNodes.serializer(), original)
        val decoded = json.decodeFromString(PathNodes.serializer(), encoded)

        // Assert
        assertIs<PathNodes.QuadTo>(decoded)
        assertEquals(expected = 10.0, actual = decoded.x1)
        assertEquals(expected = 20.0, actual = decoded.y1)
        assertEquals(expected = 30.0, actual = decoded.x2)
        assertEquals(expected = 40.0, actual = decoded.y2)
        assertFalse(decoded.isRelative)
    }

    @Test
    fun `ensure ReflectiveCurveTo round-trips correctly`() {
        // Arrange
        val original = PathNodes.ReflectiveCurveTo(
            values = listOf("10.0", "20.0", "30.0", "40.0"),
            isRelative = false,
            minified = false,
        )

        // Act
        val encoded = json.encodeToString(PathNodes.serializer(), original)
        val decoded = json.decodeFromString(PathNodes.serializer(), encoded)

        // Assert
        assertIs<PathNodes.ReflectiveCurveTo>(decoded)
        assertEquals(expected = 10.0, actual = decoded.x1)
        assertEquals(expected = 20.0, actual = decoded.y1)
        assertEquals(expected = 30.0, actual = decoded.x2)
        assertEquals(expected = 40.0, actual = decoded.y2)
        assertFalse(decoded.isRelative)
    }

    @Test
    fun `ensure ReflectiveQuadTo round-trips correctly`() {
        // Arrange
        val original = PathNodes.ReflectiveQuadTo(
            values = listOf("10.0", "20.0"),
            isRelative = false,
            minified = false,
        )

        // Act
        val encoded = json.encodeToString(PathNodes.serializer(), original)
        val decoded = json.decodeFromString(PathNodes.serializer(), encoded)

        // Assert
        assertIs<PathNodes.ReflectiveQuadTo>(decoded)
        assertEquals(expected = 10.0, actual = decoded.x1)
        assertEquals(expected = 20.0, actual = decoded.y1)
        assertFalse(decoded.isRelative)
    }

    @Test
    fun `ensure minified flag is preserved`() {
        // Arrange
        val original = PathNodes.LineTo(
            values = listOf("5.0", "10.0"),
            isRelative = false,
            minified = true,
        )

        // Act
        val encoded = json.encodeToString(PathNodes.serializer(), original)
        val decoded = json.decodeFromString(PathNodes.serializer(), encoded)

        // Assert
        assertIs<PathNodes.LineTo>(decoded)
        assertTrue(decoded.minified)
    }

    @Test
    fun `ensure relative flag is preserved`() {
        // Arrange
        val original = PathNodes.MoveTo(
            values = listOf("5.0", "10.0"),
            isRelative = true,
            minified = false,
        )

        // Act
        val encoded = json.encodeToString(PathNodes.serializer(), original)
        val decoded = json.decodeFromString(PathNodes.serializer(), encoded)

        // Assert
        assertIs<PathNodes.MoveTo>(decoded)
        assertTrue(decoded.isRelative)
    }
}
