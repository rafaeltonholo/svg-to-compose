package dev.tonholo.s2c.cli.output.tui.widget

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class ProgressCalculationsTest {

    // --- calculateEta ---

    @Test
    fun `given empty durations - when calculateEta - then returns null`() {
        // Arrange
        val durations = emptyList<Duration>()

        // Act
        val result = calculateEta(recentDurations = durations, remaining = 50)

        // Assert
        assertNull(result)
    }

    @Test
    fun `given 10 durations averaging 100ms and 50 remaining - when calculateEta - then returns 5s`() {
        // Arrange
        val durations = List(size = 10) { 100.milliseconds }

        // Act
        val result = calculateEta(recentDurations = durations, remaining = 50)

        // Assert
        assertEquals(expected = 5.seconds, actual = result)
    }

    @Test
    fun `given all files completed - when calculateEta - then returns zero`() {
        // Arrange
        val durations = List(size = 5) { 200.milliseconds }

        // Act
        val result = calculateEta(recentDurations = durations, remaining = 0)

        // Assert
        assertEquals(expected = Duration.ZERO, actual = result)
    }

    @Test
    fun `given mixed durations - when calculateEta - then uses rolling average`() {
        // Arrange
        // Average = (100 + 200 + 300) / 3 = 200ms, remaining = 10 -> ETA = 2s
        val durations = listOf(100.milliseconds, 200.milliseconds, 300.milliseconds)

        // Act
        val result = calculateEta(recentDurations = durations, remaining = 10)

        // Assert
        assertEquals(expected = 2.seconds, actual = result)
    }

    // --- calculateThroughput ---

    @Test
    fun `given zero elapsed - when calculateThroughput - then returns 0`() {
        // Act
        val result = calculateThroughput(completed = 10, elapsed = Duration.ZERO)

        // Assert
        assertEquals(expected = 0.0, actual = result)
    }

    @Test
    fun `given 100 files in 20 seconds - when calculateThroughput - then returns 5_0`() {
        // Act
        val result = calculateThroughput(completed = 100, elapsed = 20.seconds)

        // Assert
        assertEquals(expected = 5.0, actual = result)
    }

    @Test
    fun `given 1 file in 500ms - when calculateThroughput - then returns 2_0`() {
        // Act
        val result = calculateThroughput(completed = 1, elapsed = 500.milliseconds)

        // Assert
        assertEquals(expected = 2.0, actual = result)
    }

    @Test
    fun `given 0 completed - when calculateThroughput - then returns 0`() {
        // Act
        val result = calculateThroughput(completed = 0, elapsed = 10.seconds)

        // Assert
        assertEquals(expected = 0.0, actual = result)
    }
}
