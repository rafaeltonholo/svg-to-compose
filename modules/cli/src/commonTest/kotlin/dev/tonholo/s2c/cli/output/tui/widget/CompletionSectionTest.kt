package dev.tonholo.s2c.cli.output.tui.widget

import dev.tonholo.s2c.cli.output.tui.state.CompletionState
import dev.tonholo.s2c.cli.output.tui.state.FailedFileEntry
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.output.RunStats
import dev.tonholo.s2c.parser.ParserConfig
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class CompletionSectionTest {

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

    @Test
    fun `given completed run with successes only - when buildCompletionLines - then omits failed section`() {
        // Arrange
        val state = CompletionState(
            version = "2.2.0",
            config = defaultRunConfig,
            totalFiles = 847,
            failedFiles = emptyList(),
            stats = RunStats(
                totalFiles = 847,
                succeeded = 847,
                failed = 0,
                totalDuration = 169.seconds,
                errorCounts = emptyMap(),
            ),
        )

        // Act
        val lines = buildCompletionLines(state = state, stackTraceEnabled = false)
        val joined = lines.joinToString(separator = "\n")

        // Assert
        assertTrue(joined.contains("svg-to-compose v2.2.0"))
        assertTrue(joined.contains("input: ./icons"))
        assertTrue(joined.contains("output: ./generated"))
        assertTrue(joined.contains("files: 847"))
        assertTrue(joined.contains("optimize: on"))
        assertTrue(joined.contains("847/847"))
        assertTrue(joined.contains("100%"))
        assertTrue(joined.contains("Completed in 2m 49s"))
        assertTrue(joined.contains("5.0 icons/sec"))
        assertTrue(joined.contains("847 succeeded"))
        assertTrue(joined.contains("0 failed"))
        assertFalse(actual = joined.contains("Failed files"))
    }

    @Test
    fun `given run with zero failures - when buildCompletionLines - then omits failed section header`() {
        // Arrange
        val state = CompletionState(
            version = "2.2.0",
            config = defaultRunConfig,
            totalFiles = 10,
            failedFiles = emptyList(),
            stats = RunStats(
                totalFiles = 10,
                succeeded = 10,
                failed = 0,
                totalDuration = 3.seconds,
                errorCounts = emptyMap(),
            ),
        )

        // Act
        val lines = buildCompletionLines(state = state, stackTraceEnabled = false)

        // Assert
        assertTrue(lines.none { it.contains("Failed files") })
    }

    @Test
    fun `given run with grouped failures - when buildCompletionLines - then groups by ErrorCode with counts`() {
        // Arrange
        val failures = listOf(
            FailedFileEntry(
                fileName = "ic_broken_gradient.svg",
                errorCode = ErrorCode.ParseSvgError,
                message = "Unsupported gradient type: mesh-gradient",
            ),
            FailedFileEntry(
                fileName = "ic_invalid_path.svg",
                errorCode = ErrorCode.ParseSvgError,
                message = "Invalid path data at position 42: unexpected 'Q' after 'Z'",
            ),
            FailedFileEntry(
                fileName = "ic_huge_icon.svg",
                errorCode = ErrorCode.SvgoOptimizationError,
                message = "svgo exited with code 1: file exceeds max complexity",
            ),
        )
        val state = CompletionState(
            version = "2.2.0",
            config = defaultRunConfig,
            totalFiles = 847,
            failedFiles = failures,
            stats = RunStats(
                totalFiles = 847,
                succeeded = 844,
                failed = 3,
                totalDuration = 169.seconds,
                errorCounts = mapOf(
                    ErrorCode.ParseSvgError to 2,
                    ErrorCode.SvgoOptimizationError to 1,
                ),
            ),
        )

        // Act
        val lines = buildCompletionLines(state = state, stackTraceEnabled = false)
        val joined = lines.joinToString(separator = "\n")

        // Assert
        assertTrue(joined.contains("Failed files (3)"))
        assertTrue(joined.contains("ParseSvgError (2 files)"))
        assertTrue(joined.contains("SvgoOptimizationError (1 file)"))
        assertTrue(joined.contains("ic_broken_gradient.svg"))
        assertTrue(joined.contains("Unsupported gradient type: mesh-gradient"))
        assertTrue(joined.contains("ic_invalid_path.svg"))
        assertTrue(joined.contains("ic_huge_icon.svg"))
    }

    @Test
    fun `given failure with multi-line message - when buildCompletionLines - then shows only first line`() {
        // Arrange
        val failure = FailedFileEntry(
            fileName = "broken.svg",
            errorCode = ErrorCode.ParseSvgError,
            message = "First line\nSecond line should not appear\nThird line",
        )
        val state = CompletionState(
            version = "2.2.0",
            config = defaultRunConfig,
            totalFiles = 1,
            failedFiles = listOf(failure),
            stats = RunStats(
                totalFiles = 1,
                succeeded = 0,
                failed = 1,
                totalDuration = 1.seconds,
                errorCounts = mapOf(ErrorCode.ParseSvgError to 1),
            ),
        )

        // Act
        val lines = buildCompletionLines(state = state, stackTraceEnabled = false)
        val joined = lines.joinToString(separator = "\n")

        // Assert
        assertTrue(joined.contains("First line"))
        assertFalse(actual = joined.contains("Second line should not appear"))
        assertFalse(actual = joined.contains("Third line"))
    }

    @Test
    fun `given failure with stackTrace - when stackTraceEnabled - then renders stack trace`() {
        // Arrange
        val failure = FailedFileEntry(
            fileName = "broken.svg",
            errorCode = ErrorCode.ParseSvgError,
            message = "Invalid path",
            stackTrace = "java.lang.RuntimeException: boom\n  at foo.Bar.baz(Bar.kt:42)",
        )
        val state = CompletionState(
            version = "2.2.0",
            config = defaultRunConfig,
            totalFiles = 1,
            failedFiles = listOf(failure),
            stats = RunStats(
                totalFiles = 1,
                succeeded = 0,
                failed = 1,
                totalDuration = 1.seconds,
                errorCounts = mapOf(ErrorCode.ParseSvgError to 1),
            ),
        )

        // Act
        val lines = buildCompletionLines(state = state, stackTraceEnabled = true)
        val joined = lines.joinToString(separator = "\n")

        // Assert
        assertTrue(joined.contains("java.lang.RuntimeException: boom"))
        assertTrue(joined.contains("at foo.Bar.baz(Bar.kt:42)"))
    }

    @Test
    fun `given failure with stackTrace - when stackTraceEnabled is false - then omits stack trace`() {
        // Arrange
        val failure = FailedFileEntry(
            fileName = "broken.svg",
            errorCode = ErrorCode.ParseSvgError,
            message = "Invalid path",
            stackTrace = "java.lang.RuntimeException: boom\n  at foo.Bar.baz(Bar.kt:42)",
        )
        val state = CompletionState(
            version = "2.2.0",
            config = defaultRunConfig,
            totalFiles = 1,
            failedFiles = listOf(failure),
            stats = RunStats(
                totalFiles = 1,
                succeeded = 0,
                failed = 1,
                totalDuration = 1.seconds,
                errorCounts = mapOf(ErrorCode.ParseSvgError to 1),
            ),
        )

        // Act
        val lines = buildCompletionLines(state = state, stackTraceEnabled = false)
        val joined = lines.joinToString(separator = "\n")

        // Assert
        assertFalse(actual = joined.contains("java.lang.RuntimeException"))
        assertFalse(actual = joined.contains("at foo.Bar.baz"))
    }

    @Test
    fun `given throughput with sub-second duration - when buildCompletionLines - then computes safe throughput`() {
        // Arrange
        val state = CompletionState(
            version = "2.2.0",
            config = defaultRunConfig,
            totalFiles = 5,
            failedFiles = emptyList(),
            stats = RunStats(
                totalFiles = 5,
                succeeded = 5,
                failed = 0,
                totalDuration = 100.milliseconds,
                errorCounts = emptyMap(),
            ),
        )

        // Act
        val lines = buildCompletionLines(state = state, stackTraceEnabled = false)
        val joined = lines.joinToString(separator = "\n")

        // Assert
        assertTrue(joined.contains("Completed in 0s"))
        // Sub-second throughput falls back to 0.0 to avoid divide-by-zero noise.
        assertTrue(joined.contains("0.0 icons/sec"))
    }

    @Test
    fun `given incomplete state - when buildCompletionLines - then returns empty list`() {
        // Arrange
        val state = CompletionState(
            version = "2.2.0",
            config = defaultRunConfig,
            totalFiles = 10,
            failedFiles = emptyList(),
            stats = null,
        )

        // Act
        val lines = buildCompletionLines(state = state, stackTraceEnabled = false)

        // Assert
        assertEquals(expected = emptyList(), actual = lines)
    }

    @Test
    fun `given optimization disabled - when buildCompletionLines - then optimize shows off`() {
        // Arrange
        val config = defaultRunConfig.copy(optimizationEnabled = false)
        val state = CompletionState(
            version = "2.2.0",
            config = config,
            totalFiles = 1,
            failedFiles = emptyList(),
            stats = RunStats(
                totalFiles = 1,
                succeeded = 1,
                failed = 0,
                totalDuration = 1.seconds,
                errorCounts = emptyMap(),
            ),
        )

        // Act
        val lines = buildCompletionLines(state = state, stackTraceEnabled = false)
        val joined = lines.joinToString(separator = "\n")

        // Assert
        assertTrue(joined.contains("optimize: off"))
    }

    @Test
    fun `given failed counts above 1 and 1 file - when buildCompletionLines - then uses files or file label correctly`() {
        // Arrange
        val failures = listOf(
            FailedFileEntry(
                fileName = "one.svg",
                errorCode = ErrorCode.ParseSvgError,
                message = "m1",
            ),
            FailedFileEntry(
                fileName = "two.svg",
                errorCode = ErrorCode.SvgoOptimizationError,
                message = "m2",
            ),
            FailedFileEntry(
                fileName = "three.svg",
                errorCode = ErrorCode.ParseSvgError,
                message = "m3",
            ),
        )
        val state = CompletionState(
            version = "2.2.0",
            config = defaultRunConfig,
            totalFiles = 3,
            failedFiles = failures,
            stats = RunStats(
                totalFiles = 3,
                succeeded = 0,
                failed = 3,
                totalDuration = 1.seconds,
                errorCounts = mapOf(
                    ErrorCode.ParseSvgError to 2,
                    ErrorCode.SvgoOptimizationError to 1,
                ),
            ),
        )

        // Act
        val lines = buildCompletionLines(state = state, stackTraceEnabled = false)
        val joined = lines.joinToString(separator = "\n")

        // Assert
        assertTrue(joined.contains("ParseSvgError (2 files)"))
        assertTrue(joined.contains("SvgoOptimizationError (1 file)"))
    }
}
