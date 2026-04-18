package dev.tonholo.s2c.cli.output.renderer

import com.github.ajalt.mordant.input.KeyboardEvent
import com.github.ajalt.mordant.rendering.AnsiLevel
import com.github.ajalt.mordant.terminal.Terminal
import dev.tonholo.s2c.cli.output.tui.state.SingleFileCompletion
import dev.tonholo.s2c.cli.output.tui.state.TuiMode
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.FileResult
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.parser.ParserConfig
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.milliseconds

class TuiRendererSingleFileTest {

    private val testTerminal = Terminal(
        ansiLevel = AnsiLevel.NONE,
        width = 80,
        height = 24,
        interactive = false,
    )

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

    @Test
    fun `given fresh renderer - when RunStarted with totalFiles equals 1 - then mode becomes Single`() {
        // Arrange
        val renderer = TuiRenderer(terminal = testTerminal)

        // Act
        renderer.onEvent(
            event = ConversionEvent.RunStarted(
                config = defaultRunConfig,
                totalFiles = 1,
                version = "2.2.0",
            ),
        )

        // Assert
        assertEquals(expected = TuiMode.Single, actual = renderer.snapshotState().mode)
    }

    @Test
    fun `given fresh renderer - when RunStarted with totalFiles greater than 1 - then mode stays Batch`() {
        // Arrange
        val renderer = TuiRenderer(terminal = testTerminal)

        // Act
        renderer.onEvent(
            event = ConversionEvent.RunStarted(
                config = defaultRunConfig,
                totalFiles = 50,
                version = "2.2.0",
            ),
        )

        // Assert
        assertEquals(expected = TuiMode.Batch, actual = renderer.snapshotState().mode)
    }

    @Test
    fun `given single mode - when 'h' key pressed - then header expanded stays false`() {
        // Arrange
        val renderer = TuiRenderer(terminal = testTerminal)
        renderer.onEvent(
            event = ConversionEvent.RunStarted(
                config = defaultRunConfig,
                totalFiles = 1,
                version = "2.2.0",
            ),
        )

        // Act
        renderer.handleKeyEvent(event = KeyboardEvent(key = "h"))

        // Assert
        assertEquals(expected = false, actual = renderer.snapshotState().header.expanded)
    }

    @Test
    fun `given batch mode - when 'h' key pressed - then header expanded flipped`() {
        // Arrange
        val renderer = TuiRenderer(terminal = testTerminal)
        renderer.onEvent(
            event = ConversionEvent.RunStarted(
                config = defaultRunConfig,
                totalFiles = 20,
                version = "2.2.0",
            ),
        )
        val initial = renderer.snapshotState().header.expanded

        // Act
        renderer.handleKeyEvent(event = KeyboardEvent(key = "h"))

        // Assert
        assertNotEquals(illegal = initial, actual = renderer.snapshotState().header.expanded)
    }

    @Test
    fun `given single mode - when FileCompleted Success - then completion set to Success with elapsed ms`() {
        // Arrange
        val renderer = TuiRenderer(terminal = testTerminal)
        renderer.onEvent(
            event = ConversionEvent.RunStarted(
                config = defaultRunConfig,
                totalFiles = 1,
                version = "2.2.0",
            ),
        )
        renderer.onEvent(event = ConversionEvent.FileStarted(fileName = "ic_home.svg", index = 0))

        // Act
        renderer.onEvent(
            event = ConversionEvent.FileCompleted(
                fileName = "ic_home.svg",
                duration = 312.milliseconds,
                result = FileResult.Success,
            ),
        )

        // Assert
        assertEquals(
            expected = SingleFileCompletion.Success(elapsedMs = 312),
            actual = renderer.snapshotState().singleFileCompletion,
        )
    }

    @Test
    fun `given single mode - when FileCompleted Failed - then completion set to Failure`() {
        // Arrange
        val renderer = TuiRenderer(terminal = testTerminal)
        renderer.onEvent(
            event = ConversionEvent.RunStarted(
                config = defaultRunConfig,
                totalFiles = 1,
                version = "2.2.0",
            ),
        )
        renderer.onEvent(event = ConversionEvent.FileStarted(fileName = "ic_broken.svg", index = 0))

        // Act
        renderer.onEvent(
            event = ConversionEvent.FileCompleted(
                fileName = "ic_broken.svg",
                duration = 50.milliseconds,
                result = FileResult.Failed(
                    errorCode = ErrorCode.ParseSvgError,
                    message = "Unsupported gradient type: mesh-gradient",
                ),
            ),
        )

        // Assert
        val completion = renderer.snapshotState().singleFileCompletion
        assertTrue(actual = completion is SingleFileCompletion.Failure)
        assertEquals(expected = ErrorCode.ParseSvgError, actual = completion.errorCode)
        assertEquals(expected = "Unsupported gradient type: mesh-gradient", actual = completion.message)
    }

    @Test
    fun `given batch mode - when FileCompleted Success - then singleFileCompletion stays null`() {
        // Arrange
        val renderer = TuiRenderer(terminal = testTerminal)
        renderer.onEvent(
            event = ConversionEvent.RunStarted(
                config = defaultRunConfig,
                totalFiles = 5,
                version = "2.2.0",
            ),
        )
        renderer.onEvent(event = ConversionEvent.FileStarted(fileName = "a.svg", index = 0))

        // Act
        renderer.onEvent(
            event = ConversionEvent.FileCompleted(
                fileName = "a.svg",
                duration = 100.milliseconds,
                result = FileResult.Success,
            ),
        )

        // Assert
        assertEquals(expected = null, actual = renderer.snapshotState().singleFileCompletion)
    }
}
