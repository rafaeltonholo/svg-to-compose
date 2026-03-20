package dev.tonholo.s2c.serializer.domain.compose

import dev.tonholo.s2c.domain.compose.StrokeCap
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class StrokeCapSerializerTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `ensure Butt round-trips correctly`() {
        // Arrange
        val original = StrokeCap.Butt

        // Act
        val encoded = json.encodeToString(StrokeCap.serializer(), original)
        val decoded = json.decodeFromString(StrokeCap.serializer(), encoded)

        // Assert
        assertEquals(expected = StrokeCap.Butt, actual = decoded)
    }

    @Test
    fun `ensure Round round-trips correctly`() {
        // Arrange
        val original = StrokeCap.Round

        // Act
        val encoded = json.encodeToString(StrokeCap.serializer(), original)
        val decoded = json.decodeFromString(StrokeCap.serializer(), encoded)

        // Assert
        assertEquals(expected = StrokeCap.Round, actual = decoded)
    }

    @Test
    fun `ensure Square round-trips correctly`() {
        // Arrange
        val original = StrokeCap.Square

        // Act
        val encoded = json.encodeToString(StrokeCap.serializer(), original)
        val decoded = json.decodeFromString(StrokeCap.serializer(), encoded)

        // Assert
        assertEquals(expected = StrokeCap.Square, actual = decoded)
    }

    @Test
    fun `ensure Butt serializes as JSON primitive string`() {
        // Arrange
        val original = StrokeCap.Butt

        // Act
        val element = json.encodeToJsonElement(StrokeCap.serializer(), original)

        // Assert
        assertIs<JsonPrimitive>(element)
        assertEquals(expected = "Butt", actual = element.content)
    }

    @Test
    fun `ensure all variants serialize to their string value`() {
        // Arrange
        val variants = listOf(
            StrokeCap.Butt to "Butt",
            StrokeCap.Round to "Round",
            StrokeCap.Square to "Square",
        )

        for ((strokeCap, expectedValue) in variants) {
            // Act
            val element = json.encodeToJsonElement(StrokeCap.serializer(), strokeCap)

            // Assert
            assertIs<JsonPrimitive>(element)
            assertEquals(expected = expectedValue, actual = element.content)
        }
    }
}
