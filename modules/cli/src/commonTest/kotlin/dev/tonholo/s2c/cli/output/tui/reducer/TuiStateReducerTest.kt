package dev.tonholo.s2c.cli.output.tui.reducer

import dev.tonholo.s2c.cli.output.tui.state.HeaderState
import dev.tonholo.s2c.cli.output.tui.state.ProgressState
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.ConversionPhase
import dev.tonholo.s2c.output.FileResult
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.output.RunStats
import dev.tonholo.s2c.parser.ParserConfig
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class TuiStateReducerTest {

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
        recursive = false,
    )

    @Test
    fun `given initial state - when RunStarted received - then header initialized`() {
        // Arrange
        val state = HeaderState()
        val event = ConversionEvent.RunStarted(
            config = defaultRunConfig,
            totalFiles = 100,
            version = "2.2.0",
        )

        // Act
        val result = reduceHeader(state = state, event = event)

        // Assert
        assertEquals(expected = "2.2.0", actual = result.version)
        assertEquals(expected = defaultRunConfig, actual = result.config)
        assertEquals(expected = 100, actual = result.totalFiles)
    }

    @Test
    fun `given stale header - when RunStarted received - then header fields reset`() {
        // Arrange
        val staleState = HeaderState(
            version = "1.0.0",
            config = defaultRunConfig,
            totalFiles = 50,
        )
        val event = ConversionEvent.RunStarted(
            config = defaultRunConfig.copy(inputPath = "./new-icons"),
            totalFiles = 200,
            version = "3.0.0",
        )

        // Act
        val result = reduceHeader(state = staleState, event = event)

        // Assert
        assertEquals(expected = "3.0.0", actual = result.version)
        assertEquals(expected = "./new-icons", actual = result.config?.inputPath)
        assertEquals(expected = 200, actual = result.totalFiles)
    }

    @Test
    fun `given any state - when FileStarted received - then state unchanged`() {
        // Arrange
        val state = HeaderState(version = "1.0.0")
        val event = ConversionEvent.FileStarted(fileName = "icon.svg", index = 5)

        // Act
        val result = reduceHeader(state = state, event = event)

        // Assert
        assertEquals(expected = state, actual = result)
    }

    @Test
    fun `given any state - when FileStepChanged received - then state unchanged`() {
        // Arrange
        val state = HeaderState(version = "1.0.0")
        val event = ConversionEvent.FileStepChanged(
            fileName = "icon.svg",
            step = ConversionPhase.Parsing,
        )

        // Act
        val result = reduceHeader(state = state, event = event)

        // Assert
        assertEquals(expected = state, actual = result)
    }

    @Test
    fun `given any state - when FileCompleted received - then state unchanged`() {
        // Arrange
        val state = HeaderState(version = "1.0.0")
        val event = ConversionEvent.FileCompleted(
            fileName = "icon.svg",
            duration = 100.milliseconds,
            result = FileResult.Success,
        )

        // Act
        val result = reduceHeader(state = state, event = event)

        // Assert
        assertEquals(expected = state, actual = result)
    }

    @Test
    fun `given any state - when RunCompleted received - then state unchanged`() {
        // Arrange
        val state = HeaderState(version = "1.0.0")
        val event = ConversionEvent.RunCompleted(
            stats = RunStats(
                totalFiles = 50,
                succeeded = 48,
                failed = 2,
                totalDuration = 30.seconds,
                errorCounts = mapOf(ErrorCode.FailedToParseIconError to 2),
            ),
        )

        // Act
        val result = reduceHeader(state = state, event = event)

        // Assert
        assertEquals(expected = state, actual = result)
    }

    @Test
    fun `given any state - when UpdateAvailable received - then state unchanged`() {
        // Arrange
        val state = HeaderState()
        val event = ConversionEvent.UpdateAvailable(
            current = "1.0.0",
            latest = "2.0.0",
            releaseUrl = "https://github.com/example/releases/v2.0.0",
            isWrapper = false,
        )

        // Act
        val result = reduceHeader(state = state, event = event)

        // Assert
        assertEquals(expected = state, actual = result)
    }

    @Test
    fun `given expanded header - when RunStarted received - then expanded state preserved`() {
        // Arrange
        val state = HeaderState(expanded = true)
        val event = ConversionEvent.RunStarted(
            config = defaultRunConfig,
            totalFiles = 10,
            version = "1.0.0",
        )

        // Act
        val result = reduceHeader(state = state, event = event)

        // Assert
        assertEquals(expected = true, actual = result.expanded)
    }

    // --- reduceProgress tests ---

    @Test
    fun `given null state - when RunStarted received - then progress initialized`() {
        // Arrange
        val event = ConversionEvent.RunStarted(
            config = defaultRunConfig,
            totalFiles = 10,
            version = "1.0.0",
        )

        // Act
        val result = reduceProgress(state = null, event = event)

        // Assert
        assertEquals(expected = 10L, actual = result.total)
        assertEquals(expected = 10L, actual = result.pending)
        assertEquals(expected = 0L, actual = result.completed)
        assertEquals(expected = 0L, actual = result.failed)
        assertEquals(expected = emptyList(), actual = result.errors)
    }

    @Test
    fun `given active state - when FileCompleted with Success - then completed incremented and pending decremented`() {
        // Arrange
        val state = ProgressState(total = 5, pending = 3, completed = 2)
        val event = ConversionEvent.FileCompleted(
            fileName = "icon.svg",
            duration = 100.milliseconds,
            result = FileResult.Success,
        )

        // Act
        val result = reduceProgress(state = state, event = event)

        // Assert
        assertEquals(expected = 3L, actual = result.completed)
        assertEquals(expected = 2L, actual = result.pending)
        assertEquals(expected = 0L, actual = result.failed)
    }

    @Test
    fun `given active state - when FileCompleted with Failed - then failed incremented and error appended`() {
        // Arrange
        val state = ProgressState(total = 5, pending = 3, completed = 2)
        val event = ConversionEvent.FileCompleted(
            fileName = "broken.svg",
            duration = 50.milliseconds,
            result = FileResult.Failed(
                errorCode = ErrorCode.FailedToParseIconError,
                message = "Invalid path data",
            ),
        )

        // Act
        val result = reduceProgress(state = state, event = event)

        // Assert
        assertEquals(expected = 1L, actual = result.failed)
        assertEquals(expected = 2L, actual = result.pending)
        assertEquals(expected = listOf("Invalid path data"), actual = result.errors)
    }

    @Test
    fun `given active state - when FileStarted received - then state unchanged`() {
        // Arrange
        val state = ProgressState(total = 10, pending = 8, completed = 2)
        val event = ConversionEvent.FileStarted(fileName = "icon.svg", index = 3)

        // Act
        val result = reduceProgress(state = state, event = event)

        // Assert
        assertEquals(expected = state, actual = result)
    }

    @Test
    fun `given active state - when FileStepChanged received - then state unchanged`() {
        // Arrange
        val state = ProgressState(total = 10, pending = 8, completed = 2)
        val event = ConversionEvent.FileStepChanged(
            fileName = "icon.svg",
            step = ConversionPhase.Parsing,
        )

        // Act
        val result = reduceProgress(state = state, event = event)

        // Assert
        assertEquals(expected = state, actual = result)
    }

    @Test
    fun `given active state - when RunCompleted received - then state unchanged`() {
        // Arrange
        val state = ProgressState(total = 10, completed = 10, pending = 0)
        val event = ConversionEvent.RunCompleted(
            stats = RunStats(
                totalFiles = 10,
                succeeded = 10,
                failed = 0,
                totalDuration = 5.seconds,
                errorCounts = emptyMap(),
            ),
        )

        // Act
        val result = reduceProgress(state = state, event = event)

        // Assert
        assertEquals(expected = state, actual = result)
    }

    @Test
    fun `given null state - when FileCompleted received - then returns empty ProgressState`() {
        // Arrange
        val event = ConversionEvent.FileCompleted(
            fileName = "icon.svg",
            duration = 100.milliseconds,
            result = FileResult.Success,
        )

        // Act
        val result = reduceProgress(state = null, event = event)

        // Assert
        assertEquals(expected = ProgressState(), actual = result)
    }

    @Test
    fun `given active state - when multiple failures - then errors accumulate`() {
        // Arrange
        var state: ProgressState? = ProgressState(total = 3, pending = 3)

        val events = listOf(
            ConversionEvent.FileCompleted(
                fileName = "a.svg",
                duration = 50.milliseconds,
                result = FileResult.Failed(ErrorCode.FailedToParseIconError, "Bad path in a"),
            ),
            ConversionEvent.FileCompleted(
                fileName = "b.svg",
                duration = 60.milliseconds,
                result = FileResult.Failed(ErrorCode.FailedToParseIconError, "Bad path in b"),
            ),
        )

        // Act
        for (event in events) {
            state = reduceProgress(state = state, event = event)
        }

        // Assert
        assertEquals(expected = 2L, actual = state!!.failed)
        assertEquals(expected = 1L, actual = state.pending)
        assertEquals(
            expected = listOf("Bad path in a", "Bad path in b"),
            actual = state.errors,
        )
    }
}
