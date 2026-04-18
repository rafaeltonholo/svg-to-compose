package dev.tonholo.s2c.cli.output.tui.reducer

import dev.tonholo.s2c.cli.output.tui.state.CompletionState
import dev.tonholo.s2c.cli.output.tui.state.FailedFileEntry
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.ConversionPhase
import dev.tonholo.s2c.output.FileResult
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.output.RunStats
import dev.tonholo.s2c.parser.ParserConfig
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class CompletionReducerTest {

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
    fun `given initial state - when RunStarted received - then captures header metadata`() {
        // Arrange
        val state = CompletionState()
        val event = ConversionEvent.RunStarted(
            config = defaultRunConfig,
            totalFiles = 847,
            version = "2.2.0",
        )

        // Act
        val result = reduceCompletion(state = state, event = event)

        // Assert
        assertEquals(expected = "2.2.0", actual = result.version)
        assertEquals(expected = defaultRunConfig, actual = result.config)
        assertEquals(expected = 847, actual = result.totalFiles)
        assertEquals(expected = emptyList(), actual = result.failedFiles)
        assertNull(actual = result.stats)
    }

    @Test
    fun `given stale state - when RunStarted received - then failedFiles and stats reset`() {
        // Arrange
        val staleState = CompletionState(
            version = "1.0.0",
            config = defaultRunConfig,
            totalFiles = 10,
            failedFiles = listOf(
                FailedFileEntry(
                    fileName = "old.svg",
                    errorCode = ErrorCode.ParseSvgError,
                    message = "old",
                ),
            ),
            stats = RunStats(
                totalFiles = 10,
                succeeded = 9,
                failed = 1,
                totalDuration = 1.seconds,
                errorCounts = mapOf(ErrorCode.ParseSvgError to 1),
            ),
        )
        val event = ConversionEvent.RunStarted(
            config = defaultRunConfig,
            totalFiles = 5,
            version = "2.0.0",
        )

        // Act
        val result = reduceCompletion(state = staleState, event = event)

        // Assert
        assertEquals(expected = "2.0.0", actual = result.version)
        assertEquals(expected = 5, actual = result.totalFiles)
        assertEquals(expected = emptyList(), actual = result.failedFiles)
        assertNull(actual = result.stats)
    }

    @Test
    fun `given active state - when FileCompleted with Failed received - then appends failed entry`() {
        // Arrange
        val state = CompletionState(
            version = "2.2.0",
            config = defaultRunConfig,
            totalFiles = 3,
        )
        val event = ConversionEvent.FileCompleted(
            fileName = "ic_broken_gradient.svg",
            duration = 50.milliseconds,
            result = FileResult.Failed(
                errorCode = ErrorCode.ParseSvgError,
                message = "Unsupported gradient type: mesh-gradient",
                stackTrace = "java.lang.RuntimeException: boom",
            ),
        )

        // Act
        val result = reduceCompletion(state = state, event = event)

        // Assert
        assertEquals(expected = 1, actual = result.failedFiles.size)
        val entry = result.failedFiles[0]
        assertEquals(expected = "ic_broken_gradient.svg", actual = entry.fileName)
        assertEquals(expected = ErrorCode.ParseSvgError, actual = entry.errorCode)
        assertEquals(expected = "Unsupported gradient type: mesh-gradient", actual = entry.message)
        assertEquals(expected = "java.lang.RuntimeException: boom", actual = entry.stackTrace)
    }

    @Test
    fun `given active state - when FileCompleted with Success received - then state unchanged`() {
        // Arrange
        val state = CompletionState(
            version = "2.2.0",
            config = defaultRunConfig,
            totalFiles = 3,
        )
        val event = ConversionEvent.FileCompleted(
            fileName = "ok.svg",
            duration = 30.milliseconds,
            result = FileResult.Success,
        )

        // Act
        val result = reduceCompletion(state = state, event = event)

        // Assert
        assertEquals(expected = state, actual = result)
    }

    @Test
    fun `given state with failures - when RunCompleted received - then stats attached`() {
        // Arrange
        val failedEntry = FailedFileEntry(
            fileName = "ic_broken_gradient.svg",
            errorCode = ErrorCode.ParseSvgError,
            message = "Unsupported gradient type: mesh-gradient",
        )
        val state = CompletionState(
            version = "2.2.0",
            config = defaultRunConfig,
            totalFiles = 847,
            failedFiles = listOf(failedEntry),
        )
        val stats = RunStats(
            totalFiles = 847,
            succeeded = 846,
            failed = 1,
            totalDuration = 169.seconds,
            errorCounts = mapOf(ErrorCode.ParseSvgError to 1),
        )
        val event = ConversionEvent.RunCompleted(stats = stats)

        // Act
        val result = reduceCompletion(state = state, event = event)

        // Assert
        assertNotNull(actual = result.stats)
        assertEquals(expected = stats, actual = result.stats)
        assertEquals(expected = listOf(failedEntry), actual = result.failedFiles)
        assertTrue(actual = result.isCompleted)
    }

    @Test
    fun `given active state - when FileStarted received - then state unchanged`() {
        // Arrange
        val state = CompletionState(version = "2.2.0")
        val event = ConversionEvent.FileStarted(fileName = "icon.svg", index = 0)

        // Act
        val result = reduceCompletion(state = state, event = event)

        // Assert
        assertEquals(expected = state, actual = result)
    }

    @Test
    fun `given active state - when FileStepChanged received - then state unchanged`() {
        // Arrange
        val state = CompletionState(version = "2.2.0")
        val event = ConversionEvent.FileStepChanged(
            fileName = "icon.svg",
            step = ConversionPhase.Parsing,
        )

        // Act
        val result = reduceCompletion(state = state, event = event)

        // Assert
        assertEquals(expected = state, actual = result)
    }

    @Test
    fun `given active state - when UpdateAvailable received - then state unchanged`() {
        // Arrange
        val state = CompletionState(version = "2.2.0")
        val event = ConversionEvent.UpdateAvailable(
            current = "2.2.0",
            latest = "2.3.0",
            releaseUrl = "https://example.com/release",
            isWrapper = false,
        )

        // Act
        val result = reduceCompletion(state = state, event = event)

        // Assert
        assertEquals(expected = state, actual = result)
    }

    @Test
    fun `given multiple failures - when events replayed - then failedFiles ordered by arrival`() {
        // Arrange
        var state = CompletionState(
            version = "2.2.0",
            config = defaultRunConfig,
            totalFiles = 3,
        )
        val events = listOf(
            ConversionEvent.FileCompleted(
                fileName = "a.svg",
                duration = 10.milliseconds,
                result = FileResult.Failed(
                    errorCode = ErrorCode.ParseSvgError,
                    message = "bad A",
                ),
            ),
            ConversionEvent.FileCompleted(
                fileName = "b.svg",
                duration = 20.milliseconds,
                result = FileResult.Success,
            ),
            ConversionEvent.FileCompleted(
                fileName = "c.svg",
                duration = 30.milliseconds,
                result = FileResult.Failed(
                    errorCode = ErrorCode.SvgoOptimizationError,
                    message = "bad C",
                ),
            ),
        )

        // Act
        for (event in events) {
            state = reduceCompletion(state = state, event = event)
        }

        // Assert
        assertEquals(expected = 2, actual = state.failedFiles.size)
        assertEquals(expected = "a.svg", actual = state.failedFiles[0].fileName)
        assertEquals(expected = "c.svg", actual = state.failedFiles[1].fileName)
    }
}
