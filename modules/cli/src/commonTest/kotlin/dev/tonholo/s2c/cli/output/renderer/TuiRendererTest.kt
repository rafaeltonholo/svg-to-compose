package dev.tonholo.s2c.cli.output.renderer

import com.github.ajalt.mordant.rendering.AnsiLevel
import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.terminal.TerminalRecorder
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.FileResult
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.output.RunStats
import dev.tonholo.s2c.parser.ParserConfig
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class TuiRendererTest {

    private val defaultParserConfig = ParserConfig(
        pkg = "com.example.icons",
        theme = "AppTheme",
        optimize = true,
        receiverType = null,
        addToMaterial = false,
        kmpPreview = false,
        noPreview = false,
        makeInternal = false,
        minified = false,
    )

    private val defaultRunConfig = RunConfig(
        inputPath = "./icons",
        outputPath = "./generated",
        parserConfig = defaultParserConfig,
        packageName = "com.example.icons",
        optimizationEnabled = true,
        parallel = 1,
        recursive = false,
    )

    private fun newTerminal(): Pair<Terminal, TerminalRecorder> {
        val recorder = TerminalRecorder(ansiLevel = AnsiLevel.NONE, width = 120)
        val terminal = Terminal(terminalInterface = recorder)
        return terminal to recorder
    }

    private fun runStarted(totalFiles: Int = 3, version: String = "2.2.0") =
        ConversionEvent.RunStarted(
            config = defaultRunConfig,
            totalFiles = totalFiles,
            version = version,
        )

    private fun runCompleted(
        total: Int = 3,
        succeeded: Int = 2,
        failed: Int = 1,
        errorCounts: Map<ErrorCode, Int> = mapOf(ErrorCode.ParseSvgError to 1),
    ) = ConversionEvent.RunCompleted(
        stats = RunStats(
            totalFiles = total,
            succeeded = succeeded,
            failed = failed,
            totalDuration = 3.seconds,
            errorCounts = errorCounts,
        ),
    )

    @Test
    fun `given renderer - when RunCompleted arrives - then summary printed to terminal`() {
        // Arrange
        val (terminal, recorder) = newTerminal()
        val renderer = TuiRenderer(terminal = terminal, stackTraceEnabled = false)
        val failed = ConversionEvent.FileCompleted(
            fileName = "ic_broken_gradient.svg",
            duration = 50.milliseconds,
            result = FileResult.Failed(
                errorCode = ErrorCode.ParseSvgError,
                message = "Unsupported gradient type: mesh-gradient",
            ),
        )

        // Act
        renderer.onEvent(runStarted())
        renderer.onEvent(
            ConversionEvent.FileCompleted(
                fileName = "ok1.svg",
                duration = 10.milliseconds,
                result = FileResult.Success,
            ),
        )
        renderer.onEvent(
            ConversionEvent.FileCompleted(
                fileName = "ok2.svg",
                duration = 12.milliseconds,
                result = FileResult.Success,
            ),
        )
        renderer.onEvent(failed)
        renderer.onEvent(runCompleted())

        // Assert
        val output = recorder.output()
        assertTrue(output.contains("svg-to-compose v2.2.0"))
        assertTrue(output.contains("2 succeeded"))
        assertTrue(output.contains("1 failed"))
        assertTrue(output.contains("Failed files (1)"))
        assertTrue(output.contains("ParseSvgError (1 file)"))
        assertTrue(output.contains("ic_broken_gradient.svg"))
        assertTrue(output.contains("Unsupported gradient type: mesh-gradient"))
    }

    @Test
    fun `given zero failures - when RunCompleted arrives - then Failed files section omitted`() {
        // Arrange
        val (terminal, recorder) = newTerminal()
        val renderer = TuiRenderer(terminal = terminal, stackTraceEnabled = false)

        // Act
        renderer.onEvent(runStarted(totalFiles = 1))
        renderer.onEvent(
            ConversionEvent.FileCompleted(
                fileName = "ok.svg",
                duration = 5.milliseconds,
                result = FileResult.Success,
            ),
        )
        renderer.onEvent(
            runCompleted(total = 1, succeeded = 1, failed = 0, errorCounts = emptyMap()),
        )

        // Assert
        val output = recorder.output()
        assertTrue(output.contains("1 succeeded"))
        assertTrue(output.contains("0 failed"))
        assertFalse(actual = output.contains("Failed files"))
    }

    @Test
    fun `given stackTraceEnabled - when RunCompleted arrives - then stack trace included`() {
        // Arrange
        val (terminal, recorder) = newTerminal()
        val renderer = TuiRenderer(terminal = terminal, stackTraceEnabled = true)

        // Act
        renderer.onEvent(runStarted(totalFiles = 1))
        renderer.onEvent(
            ConversionEvent.FileCompleted(
                fileName = "broken.svg",
                duration = 5.milliseconds,
                result = FileResult.Failed(
                    errorCode = ErrorCode.ParseSvgError,
                    message = "bad",
                    stackTrace = "java.lang.RuntimeException: boom\n  at foo.Bar.baz(Bar.kt:42)",
                ),
            ),
        )
        renderer.onEvent(runCompleted(total = 1, succeeded = 0, failed = 1))

        // Assert
        val output = recorder.output()
        assertTrue(output.contains("java.lang.RuntimeException: boom"))
        assertTrue(output.contains("at foo.Bar.baz(Bar.kt:42)"))
    }

    @Test
    fun `given stackTraceEnabled false - when RunCompleted arrives - then stack trace omitted`() {
        // Arrange
        val (terminal, recorder) = newTerminal()
        val renderer = TuiRenderer(terminal = terminal, stackTraceEnabled = false)

        // Act
        renderer.onEvent(runStarted(totalFiles = 1))
        renderer.onEvent(
            ConversionEvent.FileCompleted(
                fileName = "broken.svg",
                duration = 5.milliseconds,
                result = FileResult.Failed(
                    errorCode = ErrorCode.ParseSvgError,
                    message = "bad",
                    stackTrace = "java.lang.RuntimeException: boom",
                ),
            ),
        )
        renderer.onEvent(runCompleted(total = 1, succeeded = 0, failed = 1))

        // Assert
        val output = recorder.output()
        assertFalse(actual = output.contains("java.lang.RuntimeException"))
    }

    @Test
    fun `given two RunCompleted events - when onEvent - then summary printed only once`() {
        // Arrange
        val (terminal, recorder) = newTerminal()
        val renderer = TuiRenderer(terminal = terminal, stackTraceEnabled = false)

        // Act
        renderer.onEvent(runStarted(totalFiles = 1))
        renderer.onEvent(
            ConversionEvent.FileCompleted(
                fileName = "ok.svg",
                duration = 5.milliseconds,
                result = FileResult.Success,
            ),
        )
        renderer.onEvent(runCompleted(total = 1, succeeded = 1, failed = 0, errorCounts = emptyMap()))
        renderer.onEvent(runCompleted(total = 1, succeeded = 1, failed = 0, errorCounts = emptyMap()))

        // Assert
        val output = recorder.output()
        val summaryMarker = "svg-to-compose v2.2.0"
        val firstOccurrence = output.indexOf(summaryMarker)
        val secondOccurrence = output.indexOf(summaryMarker, startIndex = firstOccurrence + 1)
        assertTrue(firstOccurrence >= 0)
        assertEquals(expected = -1, actual = secondOccurrence)
    }

    @Test
    fun `given multiple grouped failures - when RunCompleted arrives - then groups rendered`() {
        // Arrange
        val (terminal, recorder) = newTerminal()
        val renderer = TuiRenderer(terminal = terminal, stackTraceEnabled = false)

        // Act
        renderer.onEvent(runStarted(totalFiles = 3))
        renderer.onEvent(
            ConversionEvent.FileCompleted(
                fileName = "a.svg",
                duration = 5.milliseconds,
                result = FileResult.Failed(
                    errorCode = ErrorCode.ParseSvgError,
                    message = "bad-a",
                ),
            ),
        )
        renderer.onEvent(
            ConversionEvent.FileCompleted(
                fileName = "b.svg",
                duration = 5.milliseconds,
                result = FileResult.Failed(
                    errorCode = ErrorCode.ParseSvgError,
                    message = "bad-b",
                ),
            ),
        )
        renderer.onEvent(
            ConversionEvent.FileCompleted(
                fileName = "c.svg",
                duration = 5.milliseconds,
                result = FileResult.Failed(
                    errorCode = ErrorCode.SvgoOptimizationError,
                    message = "bad-c",
                ),
            ),
        )
        renderer.onEvent(
            runCompleted(
                total = 3,
                succeeded = 0,
                failed = 3,
                errorCounts = mapOf(
                    ErrorCode.ParseSvgError to 2,
                    ErrorCode.SvgoOptimizationError to 1,
                ),
            ),
        )

        // Assert
        val output = recorder.output()
        assertTrue(output.contains("ParseSvgError (2 files)"))
        assertTrue(output.contains("SvgoOptimizationError (1 file)"))
        assertTrue(output.contains("a.svg"))
        assertTrue(output.contains("b.svg"))
        assertTrue(output.contains("c.svg"))
    }
}
