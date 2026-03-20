package dev.tonholo.s2c.serializer.domain.compose

import dev.tonholo.s2c.domain.compose.GradientTileMode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class GradientTileModeSerializerTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `ensure Clamp round-trips correctly`() {
        // Arrange
        val original = GradientTileMode.Clamp

        // Act
        val encoded = json.encodeToString(GradientTileMode.serializer(), original)
        val decoded = json.decodeFromString(GradientTileMode.serializer(), encoded)

        // Assert
        assertEquals(expected = GradientTileMode.Clamp, actual = decoded)
    }

    @Test
    fun `ensure Repeated round-trips correctly`() {
        // Arrange
        val original = GradientTileMode.Repeated

        // Act
        val encoded = json.encodeToString(GradientTileMode.serializer(), original)
        val decoded = json.decodeFromString(GradientTileMode.serializer(), encoded)

        // Assert
        assertEquals(expected = GradientTileMode.Repeated, actual = decoded)
    }

    @Test
    fun `ensure Mirror round-trips correctly`() {
        // Arrange
        val original = GradientTileMode.Mirror

        // Act
        val encoded = json.encodeToString(GradientTileMode.serializer(), original)
        val decoded = json.decodeFromString(GradientTileMode.serializer(), encoded)

        // Assert
        assertEquals(expected = GradientTileMode.Mirror, actual = decoded)
    }

    @Test
    fun `ensure Decal round-trips correctly`() {
        // Arrange
        val original = GradientTileMode.Decal

        // Act
        val encoded = json.encodeToString(GradientTileMode.serializer(), original)
        val decoded = json.decodeFromString(GradientTileMode.serializer(), encoded)

        // Assert
        assertEquals(expected = GradientTileMode.Decal, actual = decoded)
    }

    @Test
    fun `ensure GradientTileMode serializes as JSON primitive string`() {
        // Arrange
        val original = GradientTileMode.Clamp

        // Act
        val element = json.encodeToJsonElement(GradientTileMode.serializer(), original)

        // Assert
        assertIs<JsonPrimitive>(element)
        assertEquals(expected = "Clamp", actual = element.content)
    }

    @Test
    fun `ensure all variants serialize to their string value`() {
        // Arrange
        val variants = listOf(
            GradientTileMode.Clamp to "Clamp",
            GradientTileMode.Repeated to "Repeated",
            GradientTileMode.Mirror to "Mirror",
            GradientTileMode.Decal to "Decal",
        )

        for ((tileMode, expectedValue) in variants) {
            // Act
            val element = json.encodeToJsonElement(GradientTileMode.serializer(), tileMode)

            // Assert
            assertIs<JsonPrimitive>(element)
            assertEquals(expected = expectedValue, actual = element.content)
        }
    }
}
