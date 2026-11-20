package dev.tonholo.s2c.cli.runtime

import app.cash.burst.Burst
import app.cash.burst.burstValues
import com.github.ajalt.clikt.command.parse
import com.github.ajalt.clikt.core.PrintMessage
import dev.tonholo.s2c.cli.config.BuildConfig
import dev.tonholo.s2c.cli.inject.CliGraph
import dev.tonholo.s2c.cli.output.report.InvocationCommand
import dev.zacsweers.metro.createGraphFactory
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse

class ClientTest {

    @Test
    @Burst
    fun `given version flag - when parsed without required arguments - then prints the version message`(
        flag: String = burstValues("--version", "-v"),
    ) = runTest {
        // Arrange
        val client = createClient(flag)

        // Act
        val result = assertFailsWith<PrintMessage> { client.parse(listOf(flag)) }

        // Assert
        assertEquals(expected = "SVG to Compose version: ${BuildConfig.VERSION}", actual = result.message)
    }

    @Test
    @Burst
    fun `given version flag - when parsed without required arguments - then exits with status code zero`(
        flag: String = burstValues("--version", "-v"),
    ) = runTest {
        // Arrange
        val client = createClient(flag)

        // Act
        val result = assertFailsWith<PrintMessage> { client.parse(listOf(flag)) }

        // Assert
        assertEquals(expected = 0, actual = result.statusCode)
    }

    @Test
    @Burst
    fun `given version flag - when parsed without required arguments - then does not print as an error`(
        flag: String = burstValues("--version", "-v"),
    ) = runTest {
        // Arrange
        val client = createClient(flag)

        // Act
        val result = assertFailsWith<PrintMessage> { client.parse(listOf(flag)) }

        // Assert
        assertFalse(result.printError)
    }

    private fun createClient(flag: String): Client =
        createGraphFactory<CliGraph.Factory>().create(
            config = CliConfig(),
            command = InvocationCommand(value = "s2c $flag"),
        ).client
}
