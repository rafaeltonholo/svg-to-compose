package dev.tonholo.s2c.serializer.domain.compose

import dev.tonholo.s2c.domain.compose.StrokeJoin
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class StrokeJoinSerializerTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `ensure Miter round-trips correctly`() {
        // Arrange
        val original = StrokeJoin.Miter

        // Act
        val encoded = json.encodeToString(StrokeJoin.serializer(), original)
        val decoded = json.decodeFromString(StrokeJoin.serializer(), encoded)

        // Assert
        assertEquals(expected = StrokeJoin.Miter, actual = decoded)
    }

    @Test
    fun `ensure Round round-trips correctly`() {
        // Arrange
        val original = StrokeJoin.Round

        // Act
        val encoded = json.encodeToString(StrokeJoin.serializer(), original)
        val decoded = json.decodeFromString(StrokeJoin.serializer(), encoded)

        // Assert
        assertEquals(expected = StrokeJoin.Round, actual = decoded)
    }

    @Test
    fun `ensure Bevel round-trips correctly`() {
        // Arrange
        val original = StrokeJoin.Bevel

        // Act
        val encoded = json.encodeToString(StrokeJoin.serializer(), original)
        val decoded = json.decodeFromString(StrokeJoin.serializer(), encoded)

        // Assert
        assertEquals(expected = StrokeJoin.Bevel, actual = decoded)
    }

    @Test
    fun `ensure Miter serializes as JSON primitive string`() {
        // Arrange
        val original = StrokeJoin.Miter

        // Act
        val element = json.encodeToJsonElement(StrokeJoin.serializer(), original)

        // Assert
        assertIs<JsonPrimitive>(element)
        assertEquals(expected = "Miter", actual = element.content)
    }

    @Test
    fun `ensure all variants serialize to their string value`() {
        // Arrange
        val variants = listOf(
            StrokeJoin.Miter to "Miter",
            StrokeJoin.Round to "Round",
            StrokeJoin.Bevel to "Bevel",
        )

        for ((strokeJoin, expectedValue) in variants) {
            // Act
            val element = json.encodeToJsonElement(StrokeJoin.serializer(), strokeJoin)

            // Assert
            assertIs<JsonPrimitive>(element)
            assertEquals(expected = expectedValue, actual = element.content)
        }
    }
}
