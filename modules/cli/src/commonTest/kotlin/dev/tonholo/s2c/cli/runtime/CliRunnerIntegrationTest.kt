package dev.tonholo.s2c.cli.runtime

import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okio.Path.Companion.toPath
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

/**
 * End-to-end coverage of the CLI output modes: a real conversion run drives
 * the renderers through [CliRunner], and the assertions look at what the CLI
 * actually printed.
 */
class CliRunnerIntegrationTest {

    private class Run(val harness: CliIntegrationHarness, val failure: Throwable?)

    private fun TestScope.harness(
        interactive: Boolean = false,
        silent: Boolean = false,
        vararg icons: Pair<String, String>,
    ): CliIntegrationHarness = CliIntegrationHarness(
        scheduler = testScheduler,
        interactive = interactive,
        silent = silent,
    ).also { harness ->
        icons.forEach { (path, content) -> harness.writeFile(path = path, content = content) }
        harness.createDirectory(path = "/output")
    }

    private suspend fun CliIntegrationHarness.runBatch(
        outputFormat: OutputFormat = OutputFormat.Text,
        noTui: Boolean = false,
    ): Throwable? = runCatching {
        run(input = "/icons", output = "/output", outputFormat = outputFormat, noTui = noTui)
    }.exceptionOrNull()

    private suspend fun TestScope.runWithOneBrokenIcon(): Run {
        val harness = harness(
            icons = arrayOf(
                "/icons/first.svg" to CliIntegrationHarness.validSvg,
                "/icons/second.svg" to CliIntegrationHarness.validSvg,
                "/icons/broken.svg" to CliIntegrationHarness.brokenSvg,
            ),
        )
        return Run(harness = harness, failure = harness.runBatch())
    }

    private suspend fun TestScope.runJsonBatch(): List<JsonObject> {
        val harness = harness(
            icons = arrayOf(
                "/icons/first.svg" to CliIntegrationHarness.validSvg,
                "/icons/second.svg" to CliIntegrationHarness.validSvg,
            ),
        )
        harness.runBatch(outputFormat = OutputFormat.Json)
        return harness.printedLines.map { line -> Json.parseToJsonElement(line).jsonObject }
    }

    private fun JsonObject.text(key: String): String = getValue(key).jsonPrimitive.content

    private fun List<JsonObject>.eventNames(): List<String> = map { it.text(key = "event") }

    @Test
    fun `given a batch with one broken icon - when the terminal is not a tty - then the run fails with an exit error`() =
        runTest {
            // Arrange & Act
            val run = runWithOneBrokenIcon()

            // Assert
            assertIs<ExitProgramException>(value = run.failure)
        }

    @Test
    fun `given a batch with one broken icon - when the terminal is not a tty - then only the broken file is tagged FAIL`() =
        runTest {
            // Arrange & Act
            val run = runWithOneBrokenIcon()

            // Assert
            val failedLines = run.harness.printedLines.filter { it.startsWith("[FAIL]") }
            assertTrue(
                actual = failedLines.size == 1 && failedLines.single().contains("broken.svg"),
                message = failedLines.toString(),
            )
        }

    @Test
    fun `given a batch with one broken icon - when the terminal is not a tty - then the valid files are tagged OK`() =
        runTest {
            // Arrange & Act
            val run = runWithOneBrokenIcon()

            // Assert
            assertEquals(expected = 2, actual = run.harness.printedLines.count { it.startsWith("[OK]") })
        }

    @Test
    fun `given a batch with one broken icon - when the terminal is not a tty - then the summary counts both outcomes`() =
        runTest {
            // Arrange & Act
            val run = runWithOneBrokenIcon()

            // Assert
            assertTrue(
                actual = run.harness.printedLines.any { it.startsWith("[DONE] 2 succeeded, 1 failed") },
                message = run.harness.printedLines.toString(),
            )
        }

    @Test
    fun `given a batch with one broken icon - when the run fails - then the valid icons are still written`() =
        runTest {
            // Arrange & Act
            val run = runWithOneBrokenIcon()

            // Assert
            val written = listOf("/output/First.kt", "/output/Second.kt").map { run.harness.exists(path = it) }
            assertEquals(expected = listOf(true, true), actual = written)
        }

    @Test
    fun `given a batch with one broken icon - when plain text is rendered - then the failure heading is printed once`() =
        runTest {
            // Arrange & Act
            val harness = runWithOneBrokenIcon().harness

            // Assert
            val console = harness.printedLines.joinToString(separator = "\n") + harness.terminalOutput
            assertEquals(expected = 1, actual = console.split("Failed:").size - 1, message = console)
        }

    @Test
    fun `given a batch with one broken icon - when plain text is rendered - then the broken file is listed once`() =
        runTest {
            // Arrange & Act
            val harness = runWithOneBrokenIcon().harness

            // Assert
            val console = harness.printedLines.joinToString(separator = "\n") + harness.terminalOutput
            assertEquals(
                expected = 1,
                actual = console.split("broken.svg: FailedToParseIconError").size - 1,
                message = console,
            )
        }

    @Test
    fun `given a batch run - when json output is selected - then every line is tagged with an event`() =
        runTest {
            // Arrange & Act
            val events = runJsonBatch()

            // Assert
            assertEquals(expected = "start", actual = events.eventNames().first())
            assertEquals(expected = "done", actual = events.eventNames().last())
        }

    @Test
    fun `given a batch run - when json output is selected - then the events between start and done follow the file lifecycle`() =
        runTest {
            // Arrange & Act
            val events = runJsonBatch()

            // Assert
            assertEquals(
                expected = listOf("file_start", "file_step", "file_complete"),
                actual = events.eventNames().drop(n = 1).dropLast(n = 1).distinct(),
            )
        }

    @Test
    fun `given a batch run - when json output is selected - then the start event describes the run`() =
        runTest {
            // Arrange & Act
            val start = runJsonBatch().first()

            // Assert
            assertEquals(
                expected = listOf("/icons", "/output", "2"),
                actual = listOf(start.text("input"), start.text("output"), start.text("total_files")),
            )
        }

    @Test
    fun `given a batch run - when json output is selected - then every file completes with success`() =
        runTest {
            // Arrange & Act
            val completions = runJsonBatch().filter { it.text(key = "event") == "file_complete" }

            // Assert
            assertEquals(expected = listOf("success", "success"), actual = completions.map { it.text("status") })
        }

    @Test
    fun `given a batch run - when json output is selected - then the done event counts the outcomes`() =
        runTest {
            // Arrange & Act
            val done = runJsonBatch().last()

            // Assert
            assertEquals(expected = listOf("2", "0"), actual = listOf(done.text("succeeded"), done.text("failed")))
        }

    @Test
    fun `given an interactive terminal - when no-tui is set - then plain text is rendered instead of the dashboard`() =
        runTest {
            // Arrange
            val harness = harness(
                interactive = true,
                icons = arrayOf("/icons/first.svg" to CliIntegrationHarness.validSvg),
            )

            // Act
            harness.runBatch(noTui = true)

            // Assert
            assertTrue(
                actual = harness.printedLines.any { it.startsWith("[DONE] 1 succeeded, 0 failed") },
                message = harness.printedLines.toString(),
            )
        }

    @Test
    fun `given an interactive terminal - when no-tui is set - then the log path is printed to the terminal`() =
        runTest {
            // Arrange
            val harness = harness(
                interactive = true,
                icons = arrayOf("/icons/first.svg" to CliIntegrationHarness.validSvg),
            )

            // Act
            harness.runBatch(noTui = true)

            // Assert
            assertTrue(actual = harness.terminalOutput.contains("Full log: "), message = harness.terminalOutput)
        }

    @Test
    fun `given a single svg file - when converted - then the run reports exactly one file`() =
        runTest {
            // Arrange
            val harness = harness(icons = arrayOf("/icons/first.svg" to CliIntegrationHarness.validSvg))

            // Act
            harness.run(input = "/icons/first.svg", output = "/output/First.kt")

            // Assert
            assertEquals(expected = 1, actual = harness.printedLines.count { it.startsWith("[OK]") })
        }

    @Test
    fun `given a single svg file - when converted - then the icon is written to the output file`() =
        runTest {
            // Arrange
            val harness = harness(icons = arrayOf("/icons/first.svg" to CliIntegrationHarness.validSvg))

            // Act
            harness.run(input = "/icons/first.svg", output = "/output/First.kt")

            // Assert
            val generated = harness.readFile(path = "/output/First.kt")
            assertTrue(actual = generated.contains("val First: ImageVector"), message = generated)
        }

    @Test
    fun `given a directory with no icons - when run - then it fails with a file not found error`() =
        runTest {
            // Arrange
            val harness = harness()

            // Act
            val error = harness.runBatch()

            // Assert
            assertIs<ExitProgramException>(value = error)
            assertEquals(expected = ErrorCode.FileNotFoundError, actual = error.errorCode)
        }

    @Test
    fun `given a directory with no icons - when run - then no file lines are printed`() =
        runTest {
            // Arrange
            val harness = harness()

            // Act
            harness.runBatch()

            // Assert
            assertTrue(
                actual = harness.printedLines.none { it.startsWith("[OK]") || it.startsWith("[FAIL]") },
                message = harness.printedLines.toString(),
            )
        }

    @Test
    fun `given a directory with no icons - when run - then no error report is written`() =
        runTest {
            // Arrange
            val harness = harness()

            // Act
            harness.runBatch()

            // Assert
            assertTrue(actual = harness.fileSystem.list(dir = "/".toPath()).none { it.name.startsWith("s2c-errors") })
        }

    @Test
    fun `given every icon fails - when run - then the exit error carries the parse failure code`() =
        runTest {
            // Arrange
            val harness = harness(
                icons = arrayOf(
                    "/icons/first.svg" to CliIntegrationHarness.brokenSvg,
                    "/icons/second.svg" to CliIntegrationHarness.brokenSvg,
                ),
            )

            // Act
            val error = harness.runBatch()

            // Assert
            assertIs<ExitProgramException>(value = error)
            assertEquals(expected = ErrorCode.FailedToParseIconError, actual = error.errorCode)
        }

    @Test
    fun `given every icon fails - when run - then all files are reported as failed`() =
        runTest {
            // Arrange
            val harness = harness(
                icons = arrayOf(
                    "/icons/first.svg" to CliIntegrationHarness.brokenSvg,
                    "/icons/second.svg" to CliIntegrationHarness.brokenSvg,
                ),
            )

            // Act
            harness.runBatch()

            // Assert
            assertEquals(expected = 2, actual = harness.printedLines.count { it.startsWith("[FAIL]") })
        }

    @Test
    fun `given every icon fails - when run - then the summary says none succeeded`() =
        runTest {
            // Arrange
            val harness = harness(
                icons = arrayOf(
                    "/icons/first.svg" to CliIntegrationHarness.brokenSvg,
                    "/icons/second.svg" to CliIntegrationHarness.brokenSvg,
                ),
            )

            // Act
            harness.runBatch()

            // Assert
            assertTrue(
                actual = harness.printedLines.any { it.startsWith("[DONE] 0 succeeded, 2 failed") },
                message = harness.printedLines.toString(),
            )
        }

    @Test
    fun `given every icon fails - when run - then the error report path is printed`() =
        runTest {
            // Arrange
            val harness = harness(
                icons = arrayOf(
                    "/icons/first.svg" to CliIntegrationHarness.brokenSvg,
                    "/icons/second.svg" to CliIntegrationHarness.brokenSvg,
                ),
            )

            // Act
            harness.runBatch()

            // Assert
            assertTrue(
                actual = harness.printedLines.any { it.startsWith("[REPORT] Error report saved to: ") },
                message = harness.printedLines.toString(),
            )
        }

    @Test
    fun `given every icon fails - when run - then the error report file is written`() =
        runTest {
            // Arrange
            val harness = harness(
                icons = arrayOf(
                    "/icons/first.svg" to CliIntegrationHarness.brokenSvg,
                    "/icons/second.svg" to CliIntegrationHarness.brokenSvg,
                ),
            )

            // Act
            harness.runBatch()

            // Assert
            val files = harness.fileSystem.list(dir = "/".toPath())
            assertTrue(actual = files.any { it.name.startsWith("s2c-errors") }, message = files.toString())
        }

    @Test
    fun `given every icon fails - when run - then the log path is printed to the terminal`() =
        runTest {
            // Arrange
            val harness = harness(icons = arrayOf("/icons/first.svg" to CliIntegrationHarness.brokenSvg))

            // Act
            harness.runBatch()

            // Assert
            assertTrue(actual = harness.terminalOutput.contains("Full log: "), message = harness.terminalOutput)
        }

    @Test
    fun `given a silent run with failures - when the terminal is not a tty - then nothing is printed to stdout`() =
        runTest {
            // Arrange
            val harness = harness(silent = true, icons = silentBatch)

            // Act
            harness.runBatch()

            // Assert
            assertEquals(expected = emptyList(), actual = harness.printedLines)
        }

    @Test
    fun `given a silent run with failures - when the terminal is not a tty - then nothing is printed to the terminal`() =
        runTest {
            // Arrange
            val harness = harness(silent = true, icons = silentBatch)

            // Act
            harness.runBatch()

            // Assert
            assertEquals(expected = "", actual = harness.terminalOutput)
        }

    @Test
    fun `given a silent run with failures - when the terminal is not a tty - then the valid icon is still written`() =
        runTest {
            // Arrange
            val harness = harness(silent = true, icons = silentBatch)

            // Act
            harness.runBatch()

            // Assert
            assertTrue(actual = harness.exists(path = "/output/First.kt"))
        }

    @Test
    fun `given a silent run in an interactive terminal - when files convert - then no dashboard is rendered`() =
        runTest {
            // Arrange
            val harness = harness(
                interactive = true,
                silent = true,
                icons = arrayOf("/icons/first.svg" to CliIntegrationHarness.validSvg),
            )

            // Act
            harness.runBatch()

            // Assert
            assertEquals(expected = "", actual = harness.terminalOutput)
        }

    @Test
    fun `given a silent run in an interactive terminal - when files convert - then nothing is printed to stdout`() =
        runTest {
            // Arrange
            val harness = harness(
                interactive = true,
                silent = true,
                icons = arrayOf("/icons/first.svg" to CliIntegrationHarness.validSvg),
            )

            // Act
            harness.runBatch()

            // Assert
            assertEquals(expected = emptyList(), actual = harness.printedLines)
        }

    @Test
    fun `given a silent run in an interactive terminal - when files convert - then the icon is written`() =
        runTest {
            // Arrange
            val harness = harness(
                interactive = true,
                silent = true,
                icons = arrayOf("/icons/first.svg" to CliIntegrationHarness.validSvg),
            )

            // Act
            harness.runBatch()

            // Assert
            assertTrue(actual = harness.exists(path = "/output/First.kt"))
        }

    private val silentBatch = arrayOf(
        "/icons/first.svg" to CliIntegrationHarness.validSvg,
        "/icons/broken.svg" to CliIntegrationHarness.brokenSvg,
    )
}
