package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.AnsiLevel
import com.github.ajalt.mordant.terminal.Terminal
import dev.tonholo.s2c.cli.output.tui.TuiTestFixtures
import dev.tonholo.s2c.cli.output.tui.state.CurrentFileState
import dev.tonholo.s2c.cli.output.tui.state.HeaderState
import dev.tonholo.s2c.cli.output.tui.state.SingleFileCompletion
import dev.tonholo.s2c.cli.output.tui.state.TuiMode
import dev.tonholo.s2c.cli.output.tui.state.TuiState
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.output.ConversionPhase
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SingleFileSectionTest {

    private val testTerminal = Terminal(
        ansiLevel = AnsiLevel.NONE,
        width = 80,
        height = 24,
        interactive = false,
    )

    private val defaultRunConfig = TuiTestFixtures.defaultRunConfig

    private fun singleModeState(
        currentFiles: Map<String, CurrentFileState> = emptyMap(),
        completion: SingleFileCompletion? = null,
        optimizationEnabled: Boolean = true,
    ): TuiState = TuiState(
        mode = TuiMode.Single,
        header = HeaderState(
            version = "2.2.0",
            config = defaultRunConfig.copy(optimizationEnabled = optimizationEnabled),
            totalFiles = 1,
        ),
        currentFiles = currentFiles,
        singleFileCompletion = completion,
    )

    @Test
    fun `given single mode state - when singleFileLayout rendered - then version line present`() {
        // Arrange
        val state = singleModeState()

        // Act
        val rendered = testTerminal.render(widget = singleFileLayout(state = state, contentWidth = 76))

        // Assert
        assertTrue(
            actual = rendered.contains(other = "svg-to-compose"),
            message = "Expected version line, got: $rendered",
        )
        assertTrue(
            actual = rendered.contains(other = "v2.2.0"),
            message = "Expected version number, got: $rendered",
        )
    }

    @Test
    fun `given single mode state - when singleFileLayout rendered - then input and output paths present`() {
        // Arrange
        val state = singleModeState()

        // Act
        val rendered = testTerminal.render(widget = singleFileLayout(state = state, contentWidth = 76))

        // Assert
        assertTrue(
            actual = rendered.contains(other = "input:"),
            message = "Expected input label, got: $rendered",
        )
        assertTrue(
            actual = rendered.contains(other = "./ic_home.svg"),
            message = "Expected input path, got: $rendered",
        )
        assertTrue(
            actual = rendered.contains(other = "output:"),
            message = "Expected output label, got: $rendered",
        )
        assertTrue(
            actual = rendered.contains(other = "./IcHome.kt"),
            message = "Expected output path, got: $rendered",
        )
    }

    @Test
    fun `given single mode state - when singleFileLayout rendered - then no expand or collapse hint shown`() {
        // Arrange
        val state = singleModeState()

        // Act
        val rendered = testTerminal.render(widget = singleFileLayout(state = state, contentWidth = 76))

        // Assert
        assertFalse(
            actual = rendered.contains(other = "[h]"),
            message = "Expected no header keybinding hint in single mode, got: $rendered",
        )
    }

    @Test
    fun `given file in Writing phase - when singleFileLayout rendered - then all phase names present`() {
        // Arrange
        val fileState = CurrentFileState(
            fileName = "ic_home.svg",
            currentPhase = ConversionPhase.Writing,
            completedPhases = setOf(
                ConversionPhase.Optimizing,
                ConversionPhase.Parsing,
                ConversionPhase.Generating,
            ),
        )
        val state = singleModeState(currentFiles = mapOf("ic_home.svg" to fileState))

        // Act
        val rendered = testTerminal.render(widget = singleFileLayout(state = state, contentWidth = 76))

        // Assert
        assertTrue(actual = rendered.contains(other = "Optimizing"))
        assertTrue(actual = rendered.contains(other = "Parsing"))
        assertTrue(actual = rendered.contains(other = "Generating"))
        assertTrue(actual = rendered.contains(other = "Writing"))
    }

    @Test
    fun `given optimization disabled - when singleFileLayout rendered - then Optimizing phase not shown`() {
        // Arrange
        val fileState = CurrentFileState(
            fileName = "ic_home.svg",
            currentPhase = ConversionPhase.Parsing,
            optimizationEnabled = false,
        )
        val state = singleModeState(
            currentFiles = mapOf("ic_home.svg" to fileState),
            optimizationEnabled = false,
        )

        // Act
        val rendered = testTerminal.render(widget = singleFileLayout(state = state, contentWidth = 76))

        // Assert
        assertFalse(
            actual = rendered.contains(other = "Optimizing"),
            message = "Expected no Optimizing phase when disabled, got: $rendered",
        )
        assertTrue(actual = rendered.contains(other = "Parsing"))
    }

    @Test
    fun `given config opt disabled with no file started - when singleFileLayout rendered - then Optimizing phase not shown`() {
        // Arrange
        val state = singleModeState(optimizationEnabled = false)

        // Act
        val rendered = testTerminal.render(widget = singleFileLayout(state = state, contentWidth = 76))

        // Assert
        assertFalse(
            actual = rendered.contains(other = "Optimizing"),
            message = "Expected no Optimizing phase when config disables it, got: $rendered",
        )
        assertTrue(actual = rendered.contains(other = "Parsing"))
    }

    @Test
    fun `given success completion - when singleFileLayout rendered - then Done line with elapsed time shown`() {
        // Arrange
        val state = singleModeState(
            completion = SingleFileCompletion.Success(elapsedMs = 312),
        )

        // Act
        val rendered = testTerminal.render(widget = singleFileLayout(state = state, contentWidth = 76))

        // Assert
        assertTrue(
            actual = rendered.contains(other = "Done"),
            message = "Expected Done word, got: $rendered",
        )
        assertTrue(
            actual = rendered.contains(other = "312ms"),
            message = "Expected elapsed time, got: $rendered",
        )
    }

    @Test
    fun `given failure completion - when singleFileLayout rendered - then Failed line with error code and message shown`() {
        // Arrange
        val state = singleModeState(
            completion = SingleFileCompletion.Failure(
                errorCode = ErrorCode.ParseSvgError,
                message = "Unsupported gradient type: mesh-gradient",
            ),
        )

        // Act
        val rendered = testTerminal.render(widget = singleFileLayout(state = state, contentWidth = 76))

        // Assert
        assertTrue(
            actual = rendered.contains(other = "Failed"),
            message = "Expected Failed word, got: $rendered",
        )
        assertTrue(
            actual = rendered.contains(other = "ParseSvgError"),
            message = "Expected error code name, got: $rendered",
        )
        assertTrue(
            actual = rendered.contains(other = "Unsupported gradient type: mesh-gradient"),
            message = "Expected failure message, got: $rendered",
        )
    }

    @Test
    fun `given no completion - when singleFileLayout rendered - then no Done or Failed shown`() {
        // Arrange
        val fileState = CurrentFileState(
            fileName = "ic_home.svg",
            currentPhase = ConversionPhase.Optimizing,
        )
        val state = singleModeState(currentFiles = mapOf("ic_home.svg" to fileState))

        // Act
        val rendered = testTerminal.render(widget = singleFileLayout(state = state, contentWidth = 76))

        // Assert
        assertFalse(
            actual = rendered.contains(other = "Done"),
            message = "Expected no Done line before completion, got: $rendered",
        )
        assertFalse(
            actual = rendered.contains(other = "Failed"),
            message = "Expected no Failed line before completion, got: $rendered",
        )
    }

    @Test
    fun `given single mode state - when singleFileLayout rendered - then progress bar labels absent`() {
        // Arrange
        val state = singleModeState()

        // Act
        val rendered = testTerminal.render(widget = singleFileLayout(state = state, contentWidth = 76))

        // Assert
        assertFalse(
            actual = rendered.contains(other = "Progress:"),
            message = "Expected no progress bar in single mode, got: $rendered",
        )
        assertFalse(
            actual = rendered.contains(other = "in progress"),
            message = "Expected no summary counters in single mode, got: $rendered",
        )
        assertFalse(
            actual = rendered.contains(other = "Recent"),
            message = "Expected no recent files in single mode, got: $rendered",
        )
        assertFalse(
            actual = rendered.contains(other = "Throughput:"),
            message = "Expected no stats row in single mode, got: $rendered",
        )
    }

    @Test
    fun `given pipeline emits phase transitions - when intermediate state rendered - then icons reflect phase progress`() {
        // Arrange
        val fileState = CurrentFileState(
            fileName = "ic_home.svg",
            currentPhase = ConversionPhase.Generating,
            completedPhases = setOf(ConversionPhase.Optimizing, ConversionPhase.Parsing),
        )
        val state = singleModeState(currentFiles = mapOf("ic_home.svg" to fileState))

        // Act
        val rendered = testTerminal.render(widget = singleFileLayout(state = state, contentWidth = 76))

        // Assert
        assertTrue(
            actual = rendered.contains(other = "Optimizing"),
            message = "Expected Optimizing phase, got: $rendered",
        )
        assertTrue(
            actual = rendered.contains(other = "Parsing"),
            message = "Expected Parsing phase, got: $rendered",
        )
        assertTrue(
            actual = rendered.contains(other = "Generating"),
            message = "Expected Generating phase, got: $rendered",
        )
        assertTrue(
            actual = rendered.contains(other = "Writing"),
            message = "Expected Writing phase, got: $rendered",
        )
    }
}
