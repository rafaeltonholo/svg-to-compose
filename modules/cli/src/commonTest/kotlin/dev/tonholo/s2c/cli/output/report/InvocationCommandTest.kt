package dev.tonholo.s2c.cli.output.report

import kotlin.test.Test
import kotlin.test.assertEquals

class InvocationCommandTest {
    @Test
    fun `given plain arguments - when formatCommandLine is called - then they are joined after the binary name`() {
        // Arrange
        val args = listOf("-p", "com.example.icons", "-o", "/tmp/out", "input.svg")

        // Act
        val command = formatCommandLine(args = args)

        // Assert
        assertEquals(expected = "s2c -p com.example.icons -o /tmp/out input.svg", actual = command)
    }

    @Test
    fun `given an argument with spaces - when formatCommandLine is called - then it is wrapped in double quotes`() {
        // Arrange
        val args = listOf("-o", "/tmp/my icons", "input.svg")

        // Act
        val command = formatCommandLine(args = args)

        // Assert
        assertEquals(expected = "s2c -o \"/tmp/my icons\" input.svg", actual = command)
    }

    @Test
    fun `given an argument with a double quote - when formatCommandLine is called - then the quote is escaped`() {
        // Arrange
        val args = listOf("--theme", "App\"Theme")

        // Act
        val command = formatCommandLine(args = args)

        // Assert
        assertEquals(expected = "s2c --theme \"App\\\"Theme\"", actual = command)
    }

    @Test
    fun `given no arguments - when formatCommandLine is called - then only the binary name is returned`() {
        // Arrange
        val args = emptyList<String>()

        // Act
        val command = formatCommandLine(args = args)

        // Assert
        assertEquals(expected = "s2c", actual = command)
    }
}
