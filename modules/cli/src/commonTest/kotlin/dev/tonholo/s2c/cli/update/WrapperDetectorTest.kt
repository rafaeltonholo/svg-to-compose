package dev.tonholo.s2c.cli.update

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class WrapperDetectorTest {

    @Test
    fun `given S2C_WRAPPER env var is true - when detect called - then returns true`() {
        // Arrange
        val detector = WrapperDetector(envReader = { "true" })

        // Act
        val result = detector.isRunningFromWrapper()

        // Assert
        assertTrue(result)
    }

    @Test
    fun `given S2C_WRAPPER env var is absent - when detect called - then returns false`() {
        // Arrange
        val detector = WrapperDetector(envReader = { null })

        // Act
        val result = detector.isRunningFromWrapper()

        // Assert
        assertFalse(result)
    }

    @Test
    fun `given S2C_WRAPPER env var is empty string - when detect called - then returns false`() {
        // Arrange
        val detector = WrapperDetector(envReader = { "" })

        // Act
        val result = detector.isRunningFromWrapper()

        // Assert
        assertFalse(result)
    }

    @Test
    fun `given S2C_WRAPPER env var is false - when detect called - then returns false`() {
        // Arrange
        val detector = WrapperDetector(envReader = { "false" })

        // Act
        val result = detector.isRunningFromWrapper()

        // Assert
        assertFalse(result)
    }

    @Test
    fun `given S2C_WRAPPER env var is TRUE uppercase - when detect called - then returns true`() {
        // Arrange
        val detector = WrapperDetector(envReader = { "TRUE" })

        // Act
        val result = detector.isRunningFromWrapper()

        // Assert
        assertTrue(result)
    }
}
