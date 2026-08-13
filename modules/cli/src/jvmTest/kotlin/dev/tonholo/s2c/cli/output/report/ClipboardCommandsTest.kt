package dev.tonholo.s2c.cli.output.report

import app.cash.burst.Burst
import app.cash.burst.burstValues
import kotlin.test.Test
import kotlin.test.assertEquals

class ClipboardCommandsTest {
    @Test
    @Burst
    fun `given an os name - when clipboardCommandsFor is called - then platform candidates are returned in order`(
        case: Pair<String, List<List<String>>> = burstValues(
            "Mac OS X" to listOf(listOf("pbcopy")),
            "Windows 11" to listOf(listOf("clip")),
            "Linux" to listOf(
                listOf("xclip", "-selection", "clipboard"),
                listOf("xsel", "--clipboard", "--input"),
                listOf("wl-copy"),
            ),
            "FreeBSD" to listOf(
                listOf("xclip", "-selection", "clipboard"),
                listOf("xsel", "--clipboard", "--input"),
                listOf("wl-copy"),
            ),
        ),
    ) {
        // Arrange
        val (osName, expected) = case

        // Act
        val commands = clipboardCommandsFor(osName = osName)

        // Assert
        assertEquals(expected = expected, actual = commands)
    }
}
