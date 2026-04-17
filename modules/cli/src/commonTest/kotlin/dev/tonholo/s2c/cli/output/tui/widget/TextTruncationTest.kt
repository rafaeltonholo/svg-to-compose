package dev.tonholo.s2c.cli.output.tui.widget

import kotlin.test.Test
import kotlin.test.assertEquals

class TextTruncationTest {
    @Test
    fun `given text shorter than maxWidth - when truncateWithEllipsis - then returns original text`() {
        // Arrange
        val input = "icon.svg"

        // Act
        val result = truncateWithEllipsis(text = input, maxWidth = 20)

        // Assert
        assertEquals(expected = "icon.svg", actual = result)
    }

    @Test
    fun `given text equal to maxWidth - when truncateWithEllipsis - then returns original text`() {
        // Arrange
        val input = "icon.svg"

        // Act
        val result = truncateWithEllipsis(text = input, maxWidth = input.length)

        // Assert
        assertEquals(expected = input, actual = result)
    }

    @Test
    fun `given text longer than maxWidth - when truncateWithEllipsis - then trims and appends ellipsis`() {
        // Arrange
        val input = "a-very-long-icon-name.svg"

        // Act
        val result = truncateWithEllipsis(text = input, maxWidth = 10)

        // Assert
        assertEquals(expected = "a-very-lo\u2026", actual = result)
        assertEquals(expected = 10, actual = result.length)
    }

    @Test
    fun `given maxWidth of one - when truncateWithEllipsis - then returns only ellipsis`() {
        // Arrange
        val input = "icon.svg"

        // Act
        val result = truncateWithEllipsis(text = input, maxWidth = 1)

        // Assert
        assertEquals(expected = "\u2026", actual = result)
    }

    @Test
    fun `given maxWidth of zero - when truncateWithEllipsis - then returns empty string`() {
        // Arrange
        val input = "icon.svg"

        // Act
        val result = truncateWithEllipsis(text = input, maxWidth = 0)

        // Assert
        assertEquals(expected = "", actual = result)
    }

    @Test
    fun `given negative maxWidth - when truncateWithEllipsis - then returns empty string`() {
        // Arrange
        val input = "icon.svg"

        // Act
        val result = truncateWithEllipsis(text = input, maxWidth = -5)

        // Assert
        assertEquals(expected = "", actual = result)
    }
}
