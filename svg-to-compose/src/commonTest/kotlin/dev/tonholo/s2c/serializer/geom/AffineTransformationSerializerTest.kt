package dev.tonholo.s2c.serializer.geom

import dev.tonholo.s2c.geom.AffineTransformation
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertIs

class AffineTransformationSerializerTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `ensure Identity round-trips correctly`() {
        // Arrange
        val original = AffineTransformation.Identity

        // Act
        val encoded = json.encodeToString(AffineTransformation.serializer(), original)
        val decoded = json.decodeFromString(AffineTransformation.serializer(), encoded)

        // Assert
        assertIs<AffineTransformation.Identity>(decoded)
    }

    @Test
    fun `ensure Translate round-trips correctly`() {
        // Arrange
        val original = AffineTransformation.Translate(tx = 10.0, ty = 20.0)

        // Act
        val encoded = json.encodeToString(AffineTransformation.serializer(), original)
        val decoded = json.decodeFromString(AffineTransformation.serializer(), encoded)

        // Assert
        assertIs<AffineTransformation.Translate>(decoded)
        assertEquals(expected = 10.0, actual = decoded.tx)
        assertEquals(expected = 20.0, actual = decoded.ty)
    }

    @Test
    fun `ensure Translate with negative values round-trips correctly`() {
        // Arrange
        val original = AffineTransformation.Translate(tx = -5.5, ty = -12.3)

        // Act
        val encoded = json.encodeToString(AffineTransformation.serializer(), original)
        val decoded = json.decodeFromString(AffineTransformation.serializer(), encoded)

        // Assert
        assertIs<AffineTransformation.Translate>(decoded)
        assertEquals(expected = -5.5, actual = decoded.tx)
        assertEquals(expected = -12.3, actual = decoded.ty)
    }

    @Test
    fun `ensure Rotate round-trips correctly`() {
        // Arrange
        val original = AffineTransformation.Rotate(angle = 45.0, centerX = 12.0, centerY = 12.0)

        // Act
        val encoded = json.encodeToString(AffineTransformation.serializer(), original)
        val decoded = json.decodeFromString(AffineTransformation.serializer(), encoded)

        // Assert
        assertIs<AffineTransformation.Rotate>(decoded)
        assertEquals(expected = 45.0, actual = decoded.angle)
        assertEquals(expected = 12.0, actual = decoded.centerX)
        assertEquals(expected = 12.0, actual = decoded.centerY)
    }

    @Test
    fun `ensure Rotate with default center round-trips correctly`() {
        // Arrange
        val original = AffineTransformation.Rotate(angle = 90.0)

        // Act
        val encoded = json.encodeToString(AffineTransformation.serializer(), original)
        val decoded = json.decodeFromString(AffineTransformation.serializer(), encoded)

        // Assert
        assertIs<AffineTransformation.Rotate>(decoded)
        assertEquals(expected = 90.0, actual = decoded.angle)
        assertEquals(expected = 0.0, actual = decoded.centerX)
        assertEquals(expected = 0.0, actual = decoded.centerY)
    }

    @Test
    fun `ensure Scale round-trips correctly`() {
        // Arrange
        val original = AffineTransformation.Scale(sx = 2.0, sy = 3.0)

        // Act
        val encoded = json.encodeToString(AffineTransformation.serializer(), original)
        val decoded = json.decodeFromString(AffineTransformation.serializer(), encoded)

        // Assert
        assertIs<AffineTransformation.Scale>(decoded)
        assertEquals(expected = 2.0, actual = decoded.sx)
        assertEquals(expected = 3.0, actual = decoded.sy)
    }

    @Test
    fun `ensure Scale with uniform scaling round-trips correctly`() {
        // Arrange
        val original = AffineTransformation.Scale(sx = 1.5, sy = 1.5)

        // Act
        val encoded = json.encodeToString(AffineTransformation.serializer(), original)
        val decoded = json.decodeFromString(AffineTransformation.serializer(), encoded)

        // Assert
        assertIs<AffineTransformation.Scale>(decoded)
        assertEquals(expected = 1.5, actual = decoded.sx)
        assertEquals(expected = 1.5, actual = decoded.sy)
    }

    @Test
    fun `ensure SkewX round-trips correctly`() {
        // Arrange
        val original = AffineTransformation.SkewX(angle = 30.0)

        // Act
        val encoded = json.encodeToString(AffineTransformation.serializer(), original)
        val decoded = json.decodeFromString(AffineTransformation.serializer(), encoded)

        // Assert
        assertIs<AffineTransformation.SkewX>(decoded)
        assertEquals(expected = 30.0, actual = decoded.angle)
    }

    @Test
    fun `ensure SkewY round-trips correctly`() {
        // Arrange
        val original = AffineTransformation.SkewY(angle = 60.0)

        // Act
        val encoded = json.encodeToString(AffineTransformation.serializer(), original)
        val decoded = json.decodeFromString(AffineTransformation.serializer(), encoded)

        // Assert
        assertIs<AffineTransformation.SkewY>(decoded)
        assertEquals(expected = 60.0, actual = decoded.angle)
    }

    @Test
    fun `ensure Matrix round-trips correctly`() {
        // Arrange
        val original = AffineTransformation.Matrix(
            doubleArrayOf(1.0, 0.5, 10.0),
            doubleArrayOf(0.0, 1.0, 20.0),
            doubleArrayOf(0.0, 0.0, 1.0),
        )

        // Act
        val encoded = json.encodeToString(AffineTransformation.serializer(), original)
        val decoded = json.decodeFromString(AffineTransformation.serializer(), encoded)

        // Assert
        assertIs<AffineTransformation.Matrix>(decoded)
        assertContentEquals(expected = doubleArrayOf(1.0, 0.5, 10.0), actual = decoded.matrix[0])
        assertContentEquals(expected = doubleArrayOf(0.0, 1.0, 20.0), actual = decoded.matrix[1])
        assertContentEquals(expected = doubleArrayOf(0.0, 0.0, 1.0), actual = decoded.matrix[2])
    }

    @Test
    fun `ensure Matrix with fractional values round-trips correctly`() {
        // Arrange
        val original = AffineTransformation.Matrix(
            doubleArrayOf(0.866, -0.5, 0.0),
            doubleArrayOf(0.5, 0.866, 0.0),
            doubleArrayOf(0.0, 0.0, 1.0),
        )

        // Act
        val encoded = json.encodeToString(AffineTransformation.serializer(), original)
        val decoded = json.decodeFromString(AffineTransformation.serializer(), encoded)

        // Assert
        assertIs<AffineTransformation.Matrix>(decoded)
        assertContentEquals(expected = doubleArrayOf(0.866, -0.5, 0.0), actual = decoded.matrix[0])
        assertContentEquals(expected = doubleArrayOf(0.5, 0.866, 0.0), actual = decoded.matrix[1])
        assertContentEquals(expected = doubleArrayOf(0.0, 0.0, 1.0), actual = decoded.matrix[2])
    }

    @Test
    fun `ensure Identity serialized JSON contains type field`() {
        // Arrange
        val original = AffineTransformation.Identity

        // Act
        val encoded = json.encodeToString(AffineTransformation.serializer(), original)

        // Assert
        val jsonElement = json.parseToJsonElement(encoded)
        val type = jsonElement.jsonObject["type"]?.jsonPrimitive?.content
        assertEquals(expected = "Identity", actual = type)
    }

    @Test
    fun `ensure Translate serialized JSON contains tx and ty fields`() {
        // Arrange
        val original = AffineTransformation.Translate(tx = 5.0, ty = 7.0)

        // Act
        val encoded = json.encodeToString(AffineTransformation.serializer(), original)

        // Assert
        val jsonElement = json.parseToJsonElement(encoded)
        val obj = jsonElement.jsonObject
        assertEquals(expected = "Translate", actual = obj["type"]?.jsonPrimitive?.content)
        assertEquals(expected = 5.0, actual = obj["tx"]?.jsonPrimitive?.double)
        assertEquals(expected = 7.0, actual = obj["ty"]?.jsonPrimitive?.double)
    }
}
