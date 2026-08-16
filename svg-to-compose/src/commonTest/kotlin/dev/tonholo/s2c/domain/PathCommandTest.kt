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
        expected: PathCommand = burstValues(
            PathCommand.ArcTo,
            PathCommand.CurveTo,
            PathCommand.HorizontalLineTo,
            PathCommand.LineTo,
            PathCommand.MoveTo,
            PathCommand.QuadTo,
            PathCommand.ReflectiveCurveTo,
            PathCommand.ReflectiveQuadTo,
            PathCommand.VerticalLineTo,
            PathCommand.Close,
        ),
    ) {
        // Arrange
        val char = expected.value
        // Act
        val actual = char.toPathCommand()
        // Assert
        assertEquals(expected, actual)
    }

    @Test
    @Burst
    fun `given uppercase command char - when toPathCommand is called - then matching command is returned`(
        expected: PathCommand = burstValues(
            PathCommand.ArcTo,
            PathCommand.CurveTo,
            PathCommand.HorizontalLineTo,
            PathCommand.LineTo,
            PathCommand.MoveTo,
            PathCommand.QuadTo,
            PathCommand.ReflectiveCurveTo,
            PathCommand.ReflectiveQuadTo,
            PathCommand.VerticalLineTo,
            PathCommand.Close,
        ),
    ) {
        // Arrange
        val char = expected.uppercaseChar()
        // Act
        val actual = char.toPathCommand()
        // Assert
        assertEquals(expected, actual)
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
