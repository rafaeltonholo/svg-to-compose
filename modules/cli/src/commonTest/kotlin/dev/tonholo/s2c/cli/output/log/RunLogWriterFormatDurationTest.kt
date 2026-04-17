package dev.tonholo.s2c.cli.output.log

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.Duration.Companion.seconds

class RunLogWriterFormatDurationTest {

    @Test
    fun `given exactly one hour - when formatDuration called - then returns 1h`() {
        // Arrange
        val duration = 1.hours

        // Act
        val result = RunLogWriter.formatDuration(duration)

        // Assert
        assertEquals("1h", result)
    }

    @Test
    fun `given one hour fifty minutes - when formatDuration called - then returns 1h 50m`() {
        // Arrange
        val duration = 1.hours + 50.minutes

        // Act
        val result = RunLogWriter.formatDuration(duration)

        // Assert
        assertEquals("1h 50m", result)
    }

    @Test
    fun `given one hour twenty three minutes thirty four seconds - when formatDuration called - then returns 1h 23m 34s`() {
        // Arrange
        val duration = 1.hours + 23.minutes + 34.seconds

        // Act
        val result = RunLogWriter.formatDuration(duration)

        // Assert
        assertEquals("1h 23m 34s", result)
    }

    @Test
    fun `given thirty minutes one second - when formatDuration called - then returns 30m 1s`() {
        // Arrange
        val duration = 30.minutes + 1.seconds

        // Act
        val result = RunLogWriter.formatDuration(duration)

        // Assert
        assertEquals("30m 1s", result)
    }

    @Test
    fun `given exactly thirty minutes - when formatDuration called - then returns 30m`() {
        // Arrange
        val duration = 30.minutes

        // Act
        val result = RunLogWriter.formatDuration(duration)

        // Assert
        assertEquals("30m", result)
    }

    @Test
    fun `given ten seconds - when formatDuration called - then returns 10s`() {
        // Arrange
        val duration = 10.seconds

        // Act
        val result = RunLogWriter.formatDuration(duration)

        // Assert
        assertEquals("10s", result)
    }

    @Test
    fun `given one hundred fifty milliseconds - when formatDuration called - then returns 150ms`() {
        // Arrange
        val duration = 150.milliseconds

        // Act
        val result = RunLogWriter.formatDuration(duration)

        // Assert
        assertEquals("150ms", result)
    }

    @Test
    fun `given one hundred nanoseconds - when formatDuration called - then returns 100ns`() {
        // Arrange
        val duration = 100.nanoseconds

        // Act
        val result = RunLogWriter.formatDuration(duration)

        // Assert
        assertEquals("100ns", result)
    }

    @Test
    fun `given zero duration - when formatDuration called - then returns 0ns`() {
        // Arrange
        val duration = Duration.ZERO

        // Act
        val result = RunLogWriter.formatDuration(duration)

        // Assert
        assertEquals("0ns", result)
    }
}
