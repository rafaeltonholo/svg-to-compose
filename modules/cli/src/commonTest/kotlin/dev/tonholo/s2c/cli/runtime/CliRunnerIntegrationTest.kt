package dev.tonholo.s2c.cli.runtime

import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okio.Path.Companion.toPath
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * End-to-end coverage of the CLI output modes: a real conversion run drives
 * the renderers through [CliRunner], and the assertions look at what the CLI
 * actually printed.
 */
class CliRunnerIntegrationTest {

    @Test
    fun `given a batch with one broken icon - when the terminal is not a tty - then only failed files are tagged FAIL`() =
        runTest {
            // Arrange
            val harness = CliIntegrationHarness(scheduler = testScheduler)
            harness.writeFile(path = "/icons/first.svg", content = CliIntegrationHarness.validSvg)
            harness.writeFile(path = "/icons/second.svg", content = CliIntegrationHarness.validSvg)
            harness.writeFile(path = "/icons/broken.svg", content = CliIntegrationHarness.brokenSvg)
            harness.createDirectory(path = "/output")

            // Act
            val failure = runCatching { harness.run(input = "/icons", output = "/output") }

            // Assert
            assertTrue(
                actual = failure.exceptionOrNull() is ExitProgramException,
                message = "Expected the run to fail, was ${failure.exceptionOrNull()}",
            )
            val failedLines = harness.printedLines.filter { it.startsWith("[FAIL]") }
            assertEquals(expected = 1, actual = failedLines.size)
            assertTrue(
                actual = failedLines.single().contains("broken.svg"),
                message = failedLines.single(),
            )
            val successLines = harness.printedLines.filter { it.startsWith("[OK]") }
            assertEquals(expected = 2, actual = successLines.size)
            assertTrue(
                actual = harness.printedLines.any { it.startsWith("[DONE] 2 succeeded, 1 failed") },
                message = harness.printedLines.toString(),
            )
            assertTrue(actual = harness.exists(path = "/output/First.kt"))
            assertTrue(actual = harness.exists(path = "/output/Second.kt"))
        }

    @Test
    fun `given a batch run - when json output is selected - then every line is a json object tagged with an event`() =
        runTest {
            // Arrange
            val harness = CliIntegrationHarness(scheduler = testScheduler)
            harness.writeFile(path = "/icons/first.svg", content = CliIntegrationHarness.validSvg)
            harness.writeFile(path = "/icons/second.svg", content = CliIntegrationHarness.validSvg)
            harness.createDirectory(path = "/output")

            // Act
            harness.run(input = "/icons", output = "/output", outputFormat = OutputFormat.Json)

            // Assert
            val events = harness.printedLines.map { line ->
                Json.parseToJsonElement(line).jsonObject
            }
            val names = events.map { it.getValue(key = "event").jsonPrimitive.content }
            assertEquals(expected = "start", actual = names.first())
            assertEquals(expected = "done", actual = names.last())
            assertEquals(
                expected = listOf("file_start", "file_step", "file_complete"),
                actual = names.drop(n = 1).dropLast(n = 1).distinct(),
            )
            val start = events.first()
            assertEquals(expected = "/icons", actual = start.getValue(key = "input").jsonPrimitive.content)
            assertEquals(expected = "/output", actual = start.getValue(key = "output").jsonPrimitive.content)
            assertEquals(expected = "2", actual = start.getValue(key = "total_files").jsonPrimitive.content)
            val completions = events.filter { it.getValue(key = "event").jsonPrimitive.content == "file_complete" }
            assertEquals(expected = 2, actual = completions.size)
            assertTrue(
                actual = completions.all { it.getValue(key = "status").jsonPrimitive.content == "success" },
                message = completions.toString(),
            )
            val done = events.last()
            assertEquals(expected = "2", actual = done.getValue(key = "succeeded").jsonPrimitive.content)
            assertEquals(expected = "0", actual = done.getValue(key = "failed").jsonPrimitive.content)
        }

    @Test
    fun `given an interactive terminal - when no-tui is set - then plain text is rendered instead of the dashboard`() =
        runTest {
            // Arrange
            val harness = CliIntegrationHarness(scheduler = testScheduler, interactive = true)
            harness.writeFile(path = "/icons/first.svg", content = CliIntegrationHarness.validSvg)
            harness.createDirectory(path = "/output")

            // Act
            harness.run(input = "/icons", output = "/output", noTui = true)

            // Assert
            assertTrue(
                actual = harness.printedLines.any { it.startsWith("[INFO] svg-to-compose v") },
                message = harness.printedLines.toString(),
            )
            assertTrue(
                actual = harness.printedLines.any { it.startsWith("[DONE] 1 succeeded, 0 failed") },
                message = harness.printedLines.toString(),
            )
            assertTrue(
                actual = harness.terminalOutput.contains("Full log: "),
                message = harness.terminalOutput,
            )
            assertTrue(actual = harness.exists(path = "/output/First.kt"))
        }

    @Test
    fun `given a single svg file - when converted - then the run reports exactly one file`() =
        runTest {
            // Arrange
            val harness = CliIntegrationHarness(scheduler = testScheduler)
            harness.writeFile(path = "/icons/first.svg", content = CliIntegrationHarness.validSvg)
            harness.createDirectory(path = "/output")

            // Act
            harness.run(input = "/icons/first.svg", output = "/output/First.kt")

            // Assert
            assertTrue(
                actual = harness.printedLines.any { it.contains("Files: 1") },
                message = harness.printedLines.toString(),
            )
            assertEquals(
                expected = 1,
                actual = harness.printedLines.count { it.startsWith("[OK]") },
            )
            assertTrue(
                actual = harness.printedLines.any { it.startsWith("[DONE] 1 succeeded, 0 failed") },
                message = harness.printedLines.toString(),
            )
            assertTrue(
                actual = harness.readFile(path = "/output/First.kt").contains("val First: ImageVector"),
                message = harness.readFile(path = "/output/First.kt"),
            )
        }

    @Test
    fun `given a directory with no icons - when run - then it fails with a file not found error`() =
        runTest {
            // Arrange
            val harness = CliIntegrationHarness(scheduler = testScheduler)
            harness.createDirectory(path = "/icons")
            harness.createDirectory(path = "/output")

            // Act
            val failure = runCatching { harness.run(input = "/icons", output = "/output") }

            // Assert
            val error = failure.exceptionOrNull()
            assertTrue(
                actual = error is ExitProgramException,
                message = "Expected ExitProgramException, was $error",
            )
            assertEquals(expected = ErrorCode.FileNotFoundError, actual = error.errorCode)
            assertTrue(
                actual = harness.printedLines.none { it.startsWith("[OK]") || it.startsWith("[FAIL]") },
                message = harness.printedLines.toString(),
            )
            assertTrue(actual = harness.fileSystem.list(dir = "/".toPath()).none { it.name.startsWith("s2c-errors") })
        }

    @Test
    fun `given every icon fails - when run - then all files are reported and an error report is written`() =
        runTest {
            // Arrange
            val harness = CliIntegrationHarness(scheduler = testScheduler)
            harness.writeFile(path = "/icons/first.svg", content = CliIntegrationHarness.brokenSvg)
            harness.writeFile(path = "/icons/second.svg", content = CliIntegrationHarness.brokenSvg)
            harness.createDirectory(path = "/output")

            // Act
            val failure = runCatching { harness.run(input = "/icons", output = "/output") }

            // Assert
            val error = failure.exceptionOrNull()
            assertTrue(
                actual = error is ExitProgramException,
                message = "Expected ExitProgramException, was $error",
            )
            assertEquals(expected = ErrorCode.FailedToParseIconError, actual = error.errorCode)
            assertEquals(
                expected = 2,
                actual = harness.printedLines.count { it.startsWith("[FAIL]") },
            )
            assertTrue(
                actual = harness.printedLines.any { it.startsWith("[DONE] 0 succeeded, 2 failed") },
                message = harness.printedLines.toString(),
            )
            assertTrue(
                actual = harness.printedLines.any { it.startsWith("[REPORT] Error report saved to: ") },
                message = harness.printedLines.toString(),
            )
            assertTrue(
                actual = harness.fileSystem.list(dir = "/".toPath()).any { it.name.startsWith("s2c-errors") },
                message = harness.fileSystem.list(dir = "/".toPath()).toString(),
            )
            assertTrue(actual = harness.terminalOutput.contains("Full log: "), message = harness.terminalOutput)
        }

    @Test
    fun `given a batch with one broken icon - when plain text is rendered - then the failure list is printed once`() =
        runTest {
            // Arrange
            val harness = CliIntegrationHarness(scheduler = testScheduler)
            harness.writeFile(path = "/icons/first.svg", content = CliIntegrationHarness.validSvg)
            harness.writeFile(path = "/icons/broken.svg", content = CliIntegrationHarness.brokenSvg)
            harness.createDirectory(path = "/output")

            // Act
            runCatching { harness.run(input = "/icons", output = "/output") }

            // Assert
            val console = harness.printedLines.joinToString(separator = "\n") + harness.terminalOutput
            assertEquals(
                expected = 1,
                actual = console.split("Failed:").size - 1,
                message = console,
            )
            assertEquals(
                expected = 1,
                actual = console.split("broken.svg: FailedToParseIconError").size - 1,
                message = console,
            )
        }

    @Test
    fun `given a silent run with failures - when the terminal is not a tty - then nothing is printed`() =
        runTest {
            // Arrange
            val harness = CliIntegrationHarness(scheduler = testScheduler, silent = true)
            harness.writeFile(path = "/icons/first.svg", content = CliIntegrationHarness.validSvg)
            harness.writeFile(path = "/icons/broken.svg", content = CliIntegrationHarness.brokenSvg)
            harness.createDirectory(path = "/output")

            // Act
            runCatching { harness.run(input = "/icons", output = "/output") }

            // Assert
            assertEquals(expected = emptyList(), actual = harness.printedLines)
            assertEquals(expected = "", actual = harness.terminalOutput)
            assertTrue(actual = harness.exists(path = "/output/First.kt"))
        }

    @Test
    fun `given a silent run in an interactive terminal - when files convert - then no dashboard is rendered`() =
        runTest {
            // Arrange
            val harness = CliIntegrationHarness(scheduler = testScheduler, interactive = true, silent = true)
            harness.writeFile(path = "/icons/first.svg", content = CliIntegrationHarness.validSvg)
            harness.createDirectory(path = "/output")

            // Act
            harness.run(input = "/icons", output = "/output")

            // Assert
            assertEquals(expected = emptyList(), actual = harness.printedLines)
            assertEquals(expected = "", actual = harness.terminalOutput)
            assertTrue(actual = harness.exists(path = "/output/First.kt"))
        }
}
