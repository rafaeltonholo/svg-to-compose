package dev.tonholo.s2c.cli.runtime

import com.github.ajalt.clikt.command.parse
import com.github.ajalt.clikt.core.UsageError
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ClientOutputFlagsTest {
    private val requiredArguments = listOf("-p", "com.example", "-t", "AppTheme", "-o", "/output", "/icons")

    @Test
    fun `given silent and json flags - when the command is parsed - then a usage error is raised`() =
        runTest {
            // Arrange
            val harness = CliIntegrationHarness(scheduler = testScheduler)

            // Act
            val error = runCatching {
                harness.client.parse(argv = listOf("--silent", "--json") + requiredArguments)
            }.exceptionOrNull()

            // Assert
            assertTrue(actual = error is UsageError, message = "Expected a UsageError, was $error")
        }

    @Test
    fun `given silent and json flags - when the command is parsed - then the error names both flags`() =
        runTest {
            // Arrange
            val harness = CliIntegrationHarness(scheduler = testScheduler)

            // Act
            val error = assertFailsWith<UsageError> {
                harness.client.parse(argv = listOf("--silent", "--json") + requiredArguments)
            }

            // Assert
            assertEquals(
                expected = "--silent cannot be combined with --json",
                actual = error.message,
            )
        }

    @Test
    fun `given silent and json flags - when the command is parsed - then nothing is converted or printed`() =
        runTest {
            // Arrange
            val harness = CliIntegrationHarness(scheduler = testScheduler)
            harness.writeFile(path = "/icons/first.svg", content = CliIntegrationHarness.validSvg)

            // Act
            runCatching { harness.client.parse(argv = listOf("--json", "--silent") + requiredArguments) }

            // Assert
            assertEquals(expected = emptyList(), actual = harness.printedLines)
            assertEquals(expected = false, actual = harness.exists(path = "/output/First.kt"))
        }
}
