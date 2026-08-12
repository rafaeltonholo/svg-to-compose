package dev.tonholo.s2c.cli.output.report

import kotlin.test.Test
import kotlin.test.assertEquals

class ClipboardCommandsTest {
    @Test
    fun `given a macos os name - when clipboardCommandsFor is called - then pbcopy is the only candidate`() {
        // Arrange
        val osName = "Mac OS X"

        // Act
        val commands = clipboardCommandsFor(osName = osName)

        // Assert
        assertEquals(expected = listOf(listOf("pbcopy")), actual = commands)
    }

    @Test
    fun `given a windows os name - when clipboardCommandsFor is called - then clip is the only candidate`() {
        // Arrange
        val osName = "Windows 11"

        // Act
        val commands = clipboardCommandsFor(osName = osName)

        // Assert
        assertEquals(expected = listOf(listOf("clip")), actual = commands)
    }

    @Test
    fun `given a linux os name - when clipboardCommandsFor is called - then xclip xsel and wl-copy are tried in order`() {
        // Arrange
        val osName = "Linux"

        // Act
        val commands = clipboardCommandsFor(osName = osName)

        // Assert
        assertEquals(
            expected = listOf(
                listOf("xclip", "-selection", "clipboard"),
                listOf("xsel", "--clipboard", "--input"),
                listOf("wl-copy"),
            ),
            actual = commands,
        )
    }
}
