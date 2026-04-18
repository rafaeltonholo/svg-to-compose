package dev.tonholo.s2c.cli.output.tui.reducer

import dev.tonholo.s2c.cli.output.tui.state.SingleFileCompletion
import dev.tonholo.s2c.cli.output.tui.state.TuiMode
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.ConversionPhase
import dev.tonholo.s2c.output.FileResult
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.output.RunStats
import dev.tonholo.s2c.parser.ParserConfig
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class TuiModeReducerTest {

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
        inputPath = "./ic_home.svg",
        outputPath = "./IcHome.kt",
        parserConfig = defaultParserConfig,
        packageName = "com.example.icons",
        optimizationEnabled = true,
        parallel = 1,
        recursive = false,
    )

    // --- reduceMode tests ---

    @Test
    fun `given batch mode - when RunStarted with totalFiles equals 1 - then mode is Single`() {
        // Arrange
        val event = ConversionEvent.RunStarted(
            config = defaultRunConfig,
            totalFiles = 1,
            version = "2.2.0",
        )

        // Act
        val result = reduceMode(state = TuiMode.Batch, event = event)

        // Assert
        assertEquals(expected = TuiMode.Single, actual = result)
    }

    @Test
    fun `given batch mode - when RunStarted with totalFiles greater than 1 - then mode stays Batch`() {
        // Arrange
        val event = ConversionEvent.RunStarted(
            config = defaultRunConfig,
            totalFiles = 42,
            version = "2.2.0",
        )

        // Act
        val result = reduceMode(state = TuiMode.Batch, event = event)

        // Assert
        assertEquals(expected = TuiMode.Batch, actual = result)
    }

    @Test
    fun `given batch mode - when RunStarted with totalFiles of zero - then mode stays Batch`() {
        // Arrange
        val event = ConversionEvent.RunStarted(
            config = defaultRunConfig,
            totalFiles = 0,
            version = "2.2.0",
        )

        // Act
        val result = reduceMode(state = TuiMode.Batch, event = event)

        // Assert
        assertEquals(expected = TuiMode.Batch, actual = result)
    }

    @Test
    fun `given single mode - when FileStarted received - then mode unchanged`() {
        // Arrange
        val event = ConversionEvent.FileStarted(fileName = "ic_home.svg", index = 0)

        // Act
        val result = reduceMode(state = TuiMode.Single, event = event)

        // Assert
        assertEquals(expected = TuiMode.Single, actual = result)
    }

    @Test
    fun `given single mode - when FileStepChanged received - then mode unchanged`() {
        // Arrange
        val event = ConversionEvent.FileStepChanged(
            fileName = "ic_home.svg",
            step = ConversionPhase.Parsing,
        )

        // Act
        val result = reduceMode(state = TuiMode.Single, event = event)

        // Assert
        assertEquals(expected = TuiMode.Single, actual = result)
    }

    @Test
    fun `given single mode - when FileCompleted received - then mode unchanged`() {
        // Arrange
        val event = ConversionEvent.FileCompleted(
            fileName = "ic_home.svg",
            duration = 312.milliseconds,
            result = FileResult.Success,
        )

        // Act
        val result = reduceMode(state = TuiMode.Single, event = event)

        // Assert
        assertEquals(expected = TuiMode.Single, actual = result)
    }

    @Test
    fun `given batch mode - when UpdateAvailable received - then mode unchanged`() {
        // Arrange
        val event = ConversionEvent.UpdateAvailable(
            current = "2.1.0",
            latest = "2.2.0",
            releaseUrl = "https://example.com/release",
            isWrapper = false,
        )

        // Act
        val result = reduceMode(state = TuiMode.Batch, event = event)

        // Assert
        assertEquals(expected = TuiMode.Batch, actual = result)
    }

    // --- reduceSingleFileCompletion tests ---

    @Test
    fun `given single mode and null completion - when FileCompleted Success - then Success completion set`() {
        // Arrange
        val event = ConversionEvent.FileCompleted(
            fileName = "ic_home.svg",
            duration = 312.milliseconds,
            result = FileResult.Success,
        )

        // Act
        val result = reduceSingleFileCompletion(
            state = null,
            mode = TuiMode.Single,
            event = event,
        )

        // Assert
        assertEquals(
            expected = SingleFileCompletion.Success(elapsedMs = 312),
            actual = result,
        )
    }

    @Test
    fun `given single mode and null completion - when FileCompleted Failed - then Failure completion set`() {
        // Arrange
        val event = ConversionEvent.FileCompleted(
            fileName = "ic_broken.svg",
            duration = 50.milliseconds,
            result = FileResult.Failed(
                errorCode = ErrorCode.ParseSvgError,
                message = "Unsupported gradient type: mesh-gradient",
            ),
        )

        // Act
        val result = reduceSingleFileCompletion(
            state = null,
            mode = TuiMode.Single,
            event = event,
        )

        // Assert
        assertEquals(
            expected = SingleFileCompletion.Failure(
                errorCode = ErrorCode.ParseSvgError,
                message = "Unsupported gradient type: mesh-gradient",
            ),
            actual = result,
        )
    }

    @Test
    fun `given single mode - when FileCompleted Failed with multiline message - then only first line retained`() {
        // Arrange
        val event = ConversionEvent.FileCompleted(
            fileName = "ic_broken.svg",
            duration = 50.milliseconds,
            result = FileResult.Failed(
                errorCode = ErrorCode.ParseSvgError,
                message = "Unsupported gradient type: mesh-gradient\nStack trace follows\nAt line 42",
            ),
        )

        // Act
        val result = reduceSingleFileCompletion(
            state = null,
            mode = TuiMode.Single,
            event = event,
        )

        // Assert
        assertEquals(
            expected = SingleFileCompletion.Failure(
                errorCode = ErrorCode.ParseSvgError,
                message = "Unsupported gradient type: mesh-gradient",
            ),
            actual = result,
        )
    }

    @Test
    fun `given batch mode - when FileCompleted Success - then completion stays null`() {
        // Arrange
        val event = ConversionEvent.FileCompleted(
            fileName = "icon.svg",
            duration = 120.milliseconds,
            result = FileResult.Success,
        )

        // Act
        val result = reduceSingleFileCompletion(
            state = null,
            mode = TuiMode.Batch,
            event = event,
        )

        // Assert
        assertNull(result)
    }

    @Test
    fun `given single mode - when RunStarted received - then completion cleared`() {
        // Arrange
        val existing = SingleFileCompletion.Success(elapsedMs = 100)
        val event = ConversionEvent.RunStarted(
            config = defaultRunConfig,
            totalFiles = 1,
            version = "2.2.0",
        )

        // Act
        val result = reduceSingleFileCompletion(
            state = existing,
            mode = TuiMode.Single,
            event = event,
        )

        // Assert
        assertNull(result)
    }

    @Test
    fun `given single mode - when RunCompleted received - then completion unchanged`() {
        // Arrange
        val existing = SingleFileCompletion.Success(elapsedMs = 100)
        val event = ConversionEvent.RunCompleted(
            stats = RunStats(
                totalFiles = 1,
                succeeded = 1,
                failed = 0,
                totalDuration = 1.seconds,
                errorCounts = emptyMap(),
            ),
        )

        // Act
        val result = reduceSingleFileCompletion(
            state = existing,
            mode = TuiMode.Single,
            event = event,
        )

        // Assert
        assertEquals(expected = existing, actual = result)
    }

    @Test
    fun `given single mode - when FileStepChanged received - then completion unchanged`() {
        // Arrange
        val event = ConversionEvent.FileStepChanged(
            fileName = "ic_home.svg",
            step = ConversionPhase.Writing,
        )

        // Act
        val result = reduceSingleFileCompletion(
            state = null,
            mode = TuiMode.Single,
            event = event,
        )

        // Assert
        assertNull(result)
    }
}
