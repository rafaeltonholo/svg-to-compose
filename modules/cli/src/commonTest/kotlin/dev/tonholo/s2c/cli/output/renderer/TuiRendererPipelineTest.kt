package dev.tonholo.s2c.cli.output.renderer

import com.github.ajalt.mordant.rendering.AnsiLevel
import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.terminal.TerminalRecorder
import dev.tonholo.s2c.cli.output.tui.state.SingleFileCompletion
import dev.tonholo.s2c.cli.output.tui.state.TuiMode
import dev.tonholo.s2c.cli.runtime.CliIntegrationHarness
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private const val TERMINAL_WIDTH = 120

/**
 * Feeds the TUI renderer with the events of an actual conversion run, so the
 * dashboard is exercised against real file processing rather than
 * hand-written event sequences.
 */
class TuiRendererPipelineTest {

    private fun newTerminal(): Pair<Terminal, TerminalRecorder> {
        val recorder = TerminalRecorder(
            ansiLevel = AnsiLevel.NONE,
            width = TERMINAL_WIDTH,
            outputInteractive = true,
            inputInteractive = true,
        )
        return Terminal(terminalInterface = recorder) to recorder
    }

    @Test
    fun `given a batch of real icons - when the run drives the tui - then the dashboard summarises the run`() =
        runTest {
            // Arrange
            val harness = CliIntegrationHarness(interactive = true)
            harness.writeFile(path = "/icons/first.svg", content = CliIntegrationHarness.validSvg)
            harness.writeFile(path = "/icons/second.svg", content = CliIntegrationHarness.validSvg)
            harness.writeFile(path = "/icons/broken.svg", content = CliIntegrationHarness.brokenSvg)
            harness.createDirectory(path = "/output")
            val (terminal, recorder) = newTerminal()
            val renderer = TuiRenderer(
                terminal = terminal,
                stackTraceEnabled = false,
                fileSystem = harness.fileSystem,
            )

            // Act
            val events = harness.collectEvents(input = "/icons", output = "/output")
            for (event in events) {
                renderer.onEvent(event)
            }

            // Assert
            val output = recorder.output()
            assertEquals(expected = TuiMode.Batch, actual = renderer.snapshotState().mode)
            assertTrue(actual = output.contains("2 succeeded"), message = output)
            assertTrue(actual = output.contains("1 failed"), message = output)
            assertTrue(actual = output.contains("Failed files (1)"), message = output)
            assertTrue(actual = output.contains("broken.svg"), message = output)
            assertTrue(actual = harness.exists(path = "/output/First.kt"))
            assertTrue(actual = harness.exists(path = "/output/Second.kt"))
        }

    @Test
    fun `given a single real icon - when the run drives the tui - then the single file view is used`() =
        runTest {
            // Arrange
            val harness = CliIntegrationHarness(interactive = true)
            harness.writeFile(path = "/icons/first.svg", content = CliIntegrationHarness.validSvg)
            harness.createDirectory(path = "/output")
            val (terminal, recorder) = newTerminal()
            val renderer = TuiRenderer(
                terminal = terminal,
                stackTraceEnabled = false,
                fileSystem = harness.fileSystem,
            )

            // Act
            val events = harness.collectEvents(input = "/icons/first.svg", output = "/output/First.kt")
            for (event in events) {
                renderer.onEvent(event)
            }

            // Assert
            val state = renderer.snapshotState()
            assertEquals(expected = TuiMode.Single, actual = state.mode)
            assertTrue(
                actual = state.singleFileCompletion is SingleFileCompletion.Success,
                message = "Expected a successful single file completion, was ${state.singleFileCompletion}",
            )
            assertTrue(actual = recorder.output().contains("1 succeeded"), message = recorder.output())
            assertTrue(actual = harness.exists(path = "/output/First.kt"))
        }
}
