package dev.tonholo.s2c.serializer.domain.compose

import dev.tonholo.s2c.domain.compose.ComposeBrush
import dev.tonholo.s2c.domain.compose.ComposeColor
import dev.tonholo.s2c.domain.compose.ComposeOffset
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class ComposeBrushSerializerTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `ensure SolidColor round-trips correctly`() {
        // Arrange
        val original = ComposeBrush.SolidColor("FF0000FF")

        // Act
        val encoded = json.encodeToString(ComposeBrush.serializer(), original)
        val decoded = json.decodeFromString(ComposeBrush.serializer(), encoded)

        // Assert
        assertIs<ComposeBrush.SolidColor>(decoded)
        assertEquals(expected = "FF0000FF", actual = decoded.value)
    }

    @Test
    fun `ensure SolidColor serializes as JSON primitive`() {
        // Arrange
        val original = ComposeBrush.SolidColor("FF0000FF")

        // Act
        val element = json.encodeToJsonElement(ComposeBrush.serializer(), original)

        // Assert
        assertIs<JsonPrimitive>(element)
        assertEquals(expected = "FF0000FF", actual = element.content)
    }

    @Test
    fun `ensure Linear gradient round-trips correctly`() {
        // Arrange
        val original = ComposeBrush.Gradient.Linear(
            start = ComposeOffset(x = 0f, y = 0f),
            end = ComposeOffset(x = 100f, y = 100f),
            tileMode = null,
            colors = listOf(
                ComposeColor("FF0000"),
                ComposeColor("00FF00"),
            ),
            stops = null,
        )

        // Act
        val encoded = json.encodeToString(ComposeBrush.serializer(), original)
        val decoded = json.decodeFromString(ComposeBrush.serializer(), encoded)

        // Assert
        assertIs<ComposeBrush.Gradient.Linear>(decoded)
        assertEquals(expected = original.start, actual = decoded.start)
        assertEquals(expected = original.end, actual = decoded.end)
        assertEquals(expected = original.colors, actual = decoded.colors)
        assertEquals(expected = original.tileMode, actual = decoded.tileMode)
        assertEquals(expected = original.stops, actual = decoded.stops)
    }
}
