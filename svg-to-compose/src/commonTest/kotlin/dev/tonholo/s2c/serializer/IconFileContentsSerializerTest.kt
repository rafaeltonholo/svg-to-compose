package dev.tonholo.s2c.serializer

import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.defaultImports
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

class IconFileContentsSerializerTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `ensure IconFileContents with empty nodes round-trips correctly`() {
        // Arrange
        val original = IconFileContents(
            width = 24f,
            height = 24f,
            nodes = emptyList(),
        )

        // Act
        val encoded = json.encodeToString(IconFileContents.serializer(), original)
        val decoded = json.decodeFromString(IconFileContents.serializer(), encoded)

        // Assert
        assertEquals(expected = 24f, actual = decoded.width)
        assertEquals(expected = 24f, actual = decoded.height)
        assertEquals(expected = 24f, actual = decoded.viewportWidth)
        assertEquals(expected = 24f, actual = decoded.viewportHeight)
        assertTrue(decoded.nodes.isEmpty())
    }

    @Test
    fun `ensure IconFileContents with path node round-trips correctly`() {
        // Arrange
        val moveTo = PathNodes.MoveTo(
            values = listOf("10.0", "20.0"),
            isRelative = false,
            minified = false,
        )
        val lineTo = PathNodes.LineTo(
            values = listOf("30.0", "40.0"),
            isRelative = false,
            minified = false,
        )
        val wrapper = ImageVectorNode.NodeWrapper(
            normalizedPath = "M 10.0 20.0 L 30.0 40.0",
            nodes = listOf(moveTo, lineTo),
        )
        val pathNode = ImageVectorNode.Path(
            params = ImageVectorNode.Path.Params(),
            wrapper = wrapper,
            minified = false,
        )
        val original = IconFileContents(
            pkg = "com.example",
            iconName = "TestIcon",
            theme = "Filled",
            width = 48f,
            height = 48f,
            viewportWidth = 96f,
            viewportHeight = 96f,
            nodes = listOf(pathNode),
        )

        // Act
        val encoded = json.encodeToString(IconFileContents.serializer(), original)
        val decoded = json.decodeFromString(IconFileContents.serializer(), encoded)

        // Assert
        assertEquals(expected = 48f, actual = decoded.width)
        assertEquals(expected = 48f, actual = decoded.height)
        assertEquals(expected = 96f, actual = decoded.viewportWidth)
        assertEquals(expected = 96f, actual = decoded.viewportHeight)
        assertEquals(expected = 1, actual = decoded.nodes.size)
        val decodedPath = decoded.nodes.first()
        assertIs<ImageVectorNode.Path>(decodedPath)
        assertEquals(expected = 2, actual = decodedPath.wrapper.nodes.size)
        assertIs<PathNodes.MoveTo>(decodedPath.wrapper.nodes[0])
        assertIs<PathNodes.LineTo>(decodedPath.wrapper.nodes[1])
    }

    @Test
    fun `ensure transient fields default after deserialization`() {
        // Arrange
        val original = IconFileContents(
            pkg = "com.example",
            iconName = "TestIcon",
            theme = "Filled",
            width = 24f,
            height = 24f,
            nodes = emptyList(),
            receiverType = "Icons.Filled",
            addToMaterial = true,
            noPreview = true,
            makeInternal = true,
        )

        // Act
        val encoded = json.encodeToString(IconFileContents.serializer(), original)
        val decoded = json.decodeFromString(IconFileContents.serializer(), encoded)

        // Assert
        assertEquals(expected = "", actual = decoded.pkg)
        assertEquals(expected = "", actual = decoded.iconName)
        assertEquals(expected = "", actual = decoded.theme)
        assertNull(decoded.receiverType)
        assertEquals(expected = false, actual = decoded.addToMaterial)
        assertEquals(expected = false, actual = decoded.noPreview)
        assertEquals(expected = false, actual = decoded.makeInternal)
        assertEquals(expected = defaultImports, actual = decoded.imports)
    }
}
