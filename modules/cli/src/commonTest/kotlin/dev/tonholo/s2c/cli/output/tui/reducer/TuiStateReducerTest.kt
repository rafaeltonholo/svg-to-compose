package dev.tonholo.s2c.cli.output.tui.reducer

import dev.tonholo.s2c.cli.output.tui.state.HeaderState
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.ConversionPhase
import dev.tonholo.s2c.output.FileResult
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.output.RunStats
import dev.tonholo.s2c.error.ErrorCode
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
}
