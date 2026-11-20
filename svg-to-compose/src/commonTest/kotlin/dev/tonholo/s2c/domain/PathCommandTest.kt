package dev.tonholo.s2c.domain

import app.cash.burst.Burst
import app.cash.burst.burstValues
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PathCommandTest {
    @Test
    @Burst
    fun `given lowercase command char - when toPathCommand is called - then matching command is returned`(
        letter: SvgCommandLetter,
    ) {
        // Arrange
        val char = letter.lowercase
        // Act
        val actual = char.toPathCommand()
        // Assert
        assertEquals(letter.command, actual)
    }

    @Test
    @Burst
    fun `given uppercase command char - when toPathCommand is called - then matching command is returned`(
        letter: SvgCommandLetter,
    ) {
        // Arrange
        val char = letter.uppercase
        // Act
        val actual = char.toPathCommand()
        // Assert
        assertEquals(letter.command, actual)
    }

    @Test
    @Burst
    fun `given unknown command char - when toPathCommand is called - then null is returned`(
        char: Char = burstValues('x', 'X', 'e', 'E', 'b', 'B', '1', '-', '.', ' '),
    ) {
        // Act
        val actual = char.toPathCommand()
        // Assert
        assertNull(actual)
    }
}

enum class SvgCommandLetter(val lowercase: Char, val uppercase: Char, val command: PathCommand) {
    Arc('a', 'A', PathCommand.ArcTo),
    Curve('c', 'C', PathCommand.CurveTo),
    Horizontal('h', 'H', PathCommand.HorizontalLineTo),
    Line('l', 'L', PathCommand.LineTo),
    Move('m', 'M', PathCommand.MoveTo),
    Quad('q', 'Q', PathCommand.QuadTo),
    ReflectiveCurve('s', 'S', PathCommand.ReflectiveCurveTo),
    ReflectiveQuad('t', 'T', PathCommand.ReflectiveQuadTo),
    Vertical('v', 'V', PathCommand.VerticalLineTo),
    Close('z', 'Z', PathCommand.Close),
}
