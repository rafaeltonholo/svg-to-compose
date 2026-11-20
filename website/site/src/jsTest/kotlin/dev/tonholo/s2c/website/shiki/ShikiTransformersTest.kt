package dev.tonholo.s2c.website.shiki

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ShikiTransformersTest {
    private fun tooltipTransformer(): dynamic = ShikiTransformers.infoIconTransformer(
        tomlLines = listOf("icon_template = \"\""),
        isSectionHeader = { false },
        tooltipLookup = { "Title" to "Description" },
    )

    @Test
    fun `given null value - when isNullOrUndefined is called - then it returns true`() {
        // Arrange
        val value = js("null")

        // Act
        val result = isNullOrUndefined(value)

        // Assert
        assertTrue(result)
    }

    @Test
    fun `given undefined value - when isNullOrUndefined is called - then it returns true`() {
        // Arrange
        val value = js("({}).missing")

        // Act
        val result = isNullOrUndefined(value)

        // Assert
        assertTrue(result)
    }

    @Test
    fun `given empty array - when isNullOrUndefined is called - then it returns false`() {
        // Arrange
        val value = js("([])")

        // Act
        val result = isNullOrUndefined(value)

        // Assert
        assertFalse(result)
    }

    @Test
    fun `given empty string - when isNullOrUndefined is called - then it returns false`() {
        // Arrange
        val value = js("''")

        // Act
        val result = isNullOrUndefined(value)

        // Assert
        assertFalse(result)
    }

    @Test
    fun `given zero - when isNullOrUndefined is called - then it returns false`() {
        // Arrange
        val value = js("0")

        // Act
        val result = isNullOrUndefined(value)

        // Assert
        assertFalse(result)
    }

    @Test
    fun `given node with array children - when tooltip line is transformed - then info icon is appended`() {
        // Arrange
        val transformer = tooltipTransformer()
        val node = js("({ children: [] })")

        // Act
        transformer.line(node, 1)

        // Assert
        assertEquals(expected = 1, actual = node.children.length.unsafeCast<Int>())
        assertEquals(
            expected = "s2c-info-icon",
            actual = node.children[0].properties["class"].unsafeCast<String>(),
        )
    }

    @Test
    fun `given node with null children - when tooltip line is transformed - then children stay untouched`() {
        // Arrange
        val transformer = tooltipTransformer()
        val node = js("({ children: null })")

        // Act
        transformer.line(node, 1)

        // Assert
        assertNull(node.children)
    }

    @Test
    fun `given node with undefined children - when tooltip line is transformed - then children stay untouched`() {
        // Arrange
        val transformer = tooltipTransformer()
        val node = js("({})")

        // Act
        transformer.line(node, 1)

        // Assert
        assertNull(node.children)
    }

    @Test
    fun `given null properties - when section header is transformed - then highlighted class is set`() {
        // Arrange
        val transformer = ShikiTransformers.infoIconTransformer(
            tomlLines = listOf("[templates]"),
            isSectionHeader = { true },
            tooltipLookup = { null },
        )
        val node = js("({ properties: null, children: [] })")

        // Act
        transformer.line(node, 1)

        // Assert
        assertEquals(expected = "highlighted", actual = node.properties["class"].unsafeCast<String>())
    }

    @Test
    fun `given undefined properties - when section header is transformed - then highlighted class is set`() {
        // Arrange
        val transformer = ShikiTransformers.infoIconTransformer(
            tomlLines = listOf("[templates]"),
            isSectionHeader = { true },
            tooltipLookup = { null },
        )
        val node = js("({ children: [] })")

        // Act
        transformer.line(node, 1)

        // Assert
        assertEquals(expected = "highlighted", actual = node.properties["class"].unsafeCast<String>())
    }
}
