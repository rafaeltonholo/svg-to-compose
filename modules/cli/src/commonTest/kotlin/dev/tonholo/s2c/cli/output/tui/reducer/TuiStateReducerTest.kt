package dev.tonholo.s2c.cli.output.tui.reducer

import dev.tonholo.s2c.cli.output.tui.state.HeaderState
import dev.tonholo.s2c.cli.output.tui.state.ProgressState
import dev.tonholo.s2c.cli.output.tui.state.TuiState
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
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TestTimeSource

class TuiStateReducerTest {
    private val testTimeSource = TestTimeSource()

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
    fun `given initial state - when RunStarted received - then header and progress initialized`() {
        // Arrange
        val state = TuiState()
        val event = ConversionEvent.RunStarted(
            config = defaultRunConfig,
            totalFiles = 100,
            version = "2.2.0",
        )

        // Act
        val result = reduce(state = state, event = event, timeSource = testTimeSource)

        // Assert
        assertEquals(expected = "2.2.0", actual = result.header.version)
        assertEquals(expected = defaultRunConfig, actual = result.header.config)
        assertEquals(expected = 100, actual = result.header.totalFiles)
        assertEquals(expected = 100, actual = result.progress.total)
        assertNotNull(result.progress.startTime)
    }

    @Test
    fun `given state with 5 completed - when FileCompleted received - then completed is 6 and duration appended`() {
        // Arrange
        val existingDurations = List(size = 5) { 100.milliseconds }
        val state = TuiState(
            progress = ProgressState(
                completed = 5,
                total = 100,
                recentDurations = existingDurations,
            ),
        )
        val event = ConversionEvent.FileCompleted(
            fileName = "icon.svg",
            duration = 200.milliseconds,
            result = FileResult.Success,
        )

        // Act
        val result = reduce(state = state, event = event)

        // Assert
        assertEquals(expected = 6, actual = result.progress.completed)
        assertEquals(expected = 6, actual = result.progress.recentDurations.size)
        assertEquals(expected = 200.milliseconds, actual = result.progress.recentDurations.last())
    }

    @Test
    fun `given 20 durations in window - when FileCompleted received - then window capped at 20`() {
        // Arrange
        val existingDurations = List(size = 20) { (it * 10).milliseconds }
        val state = TuiState(
            progress = ProgressState(
                completed = 20,
                total = 100,
                recentDurations = existingDurations,
            ),
        )
        val event = ConversionEvent.FileCompleted(
            fileName = "icon.svg",
            duration = 500.milliseconds,
            result = FileResult.Success,
        )

        // Act
        val result = reduce(state = state, event = event)

        // Assert
        assertEquals(expected = 21, actual = result.progress.completed)
        assertEquals(expected = 20, actual = result.progress.recentDurations.size)
        assertEquals(expected = 500.milliseconds, actual = result.progress.recentDurations.last())
        // First element (0ms) should be evicted
        assertEquals(expected = 10.milliseconds, actual = result.progress.recentDurations.first())
    }

    @Test
    fun `given in-progress state - when RunCompleted received - then finished is true`() {
        // Arrange
        val state = TuiState(
            progress = ProgressState(completed = 50, total = 50),
        )
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
        val result = reduce(state = state, event = event)

        // Assert
        assertTrue(result.progress.finished)
    }

    @Test
    fun `given any state - when FileStarted received - then state unchanged`() {
        // Arrange
        val state = TuiState(
            header = HeaderState(version = "1.0.0"),
            progress = ProgressState(completed = 5, total = 10),
        )
        val event = ConversionEvent.FileStarted(fileName = "icon.svg", index = 5)

        // Act
        val result = reduce(state = state, event = event)

        // Assert
        assertEquals(expected = state, actual = result)
    }

    @Test
    fun `given any state - when FileStepChanged received - then state unchanged`() {
        // Arrange
        val state = TuiState(
            header = HeaderState(version = "1.0.0"),
            progress = ProgressState(completed = 5, total = 10),
        )
        val event = ConversionEvent.FileStepChanged(
            fileName = "icon.svg",
            step = ConversionPhase.Parsing,
        )

        // Act
        val result = reduce(state = state, event = event)

        // Assert
        assertEquals(expected = state, actual = result)
    }

    @Test
    fun `given any state - when UpdateAvailable received - then state unchanged`() {
        // Arrange
        val state = TuiState()
        val event = ConversionEvent.UpdateAvailable(
            current = "1.0.0",
            latest = "2.0.0",
            releaseUrl = "https://github.com/example/releases/v2.0.0",
            isWrapper = false,
        )

        // Act
        val result = reduce(state = state, event = event)

        // Assert
        assertEquals(expected = state, actual = result)
    }

    @Test
    fun `given failed file - when FileCompleted with Failed result - then completed still increments`() {
        // Arrange
        val state = TuiState(
            progress = ProgressState(completed = 3, total = 10),
        )
        val event = ConversionEvent.FileCompleted(
            fileName = "broken.svg",
            duration = 50.milliseconds,
            result = FileResult.Failed(
                errorCode = ErrorCode.FailedToParseIconError,
                message = "Parse error",
            ),
        )

        // Act
        val result = reduce(state = state, event = event)

        // Assert
        assertEquals(expected = 4, actual = result.progress.completed)
        assertEquals(expected = 1, actual = result.progress.recentDurations.size)
    }
}
