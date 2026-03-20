package dev.tonholo.s2c.serializer.domain.compose

import dev.tonholo.s2c.domain.compose.PathFillType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class PathFillTypeSerializerTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `ensure EvenOdd round-trips correctly`() {
        // Arrange
        val original = PathFillType.EvenOdd

        // Act
        val encoded = json.encodeToString(PathFillType.serializer(), original)
        val decoded = json.decodeFromString(PathFillType.serializer(), encoded)

        // Assert
        assertEquals(expected = PathFillType.EvenOdd, actual = decoded)
    }

    @Test
    fun `ensure NonZero round-trips correctly`() {
        // Arrange
        val original = PathFillType.NonZero

        // Act
        val encoded = json.encodeToString(PathFillType.serializer(), original)
        val decoded = json.decodeFromString(PathFillType.serializer(), encoded)

        // Assert
        assertEquals(expected = PathFillType.NonZero, actual = decoded)
    }

    @Test
    fun `ensure EvenOdd serializes as JSON primitive string`() {
        // Arrange
        val original = PathFillType.EvenOdd

        // Act
        val element = json.encodeToJsonElement(PathFillType.serializer(), original)

        // Assert
        assertIs<JsonPrimitive>(element)
        assertEquals(expected = "EvenOdd", actual = element.content)
    }

    @Test
    fun `ensure NonZero serializes as JSON primitive string`() {
        // Arrange
        val original = PathFillType.NonZero

        // Act
        val element = json.encodeToJsonElement(PathFillType.serializer(), original)

        // Assert
        assertIs<JsonPrimitive>(element)
        assertEquals(expected = "NonZero", actual = element.content)
    }

    @Test
    fun `ensure all variants serialize to their string value`() {
        // Arrange
        val variants = listOf(
            PathFillType.EvenOdd to "EvenOdd",
            PathFillType.NonZero to "NonZero",
        )

        for ((fillType, expectedValue) in variants) {
            // Act
            val element = json.encodeToJsonElement(PathFillType.serializer(), fillType)

            // Assert
            assertIs<JsonPrimitive>(element)
            assertEquals(expected = expectedValue, actual = element.content)
        }
    }
}
