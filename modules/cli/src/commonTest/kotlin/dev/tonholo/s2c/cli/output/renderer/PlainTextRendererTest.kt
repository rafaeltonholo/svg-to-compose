package dev.tonholo.s2c.cli.output.renderer

import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.ConversionPhase
import dev.tonholo.s2c.output.FileResult
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.output.RunStats
import dev.tonholo.s2c.parser.ParserConfig
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class PlainTextRendererTest {

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
        outputPath = "./gen",
        parserConfig = defaultParserConfig,
        packageName = "com.example.icons",
        optimizationEnabled = true,
        parallel = 0,
        recursive = false,
    )

    private fun collectOutput(block: (PlainTextRenderer) -> Unit): List<String> {
        val lines = mutableListOf<String>()
        val renderer = PlainTextRenderer(writer = lines::add)
        block(renderer)
        return lines
    }

    @Test
    fun `given RunStarted event - when onEvent called - then outputs INFO lines with version and config`() {
        // Arrange
        val event = ConversionEvent.RunStarted(
            config = defaultRunConfig,
            totalFiles = 847,
            version = "2.2.0",
        )

        // Act
        val lines = collectOutput { it.onEvent(event) }

        // Assert
        assertEquals(expected = 2, actual = lines.size)
        assertTrue(lines[0].startsWith("[INFO]"))
        assertTrue(lines[0].contains("v2.2.0"))
        assertTrue(lines[1].startsWith("[INFO]"))
        assertTrue(lines[1].contains("./icons"))
        assertTrue(lines[1].contains("./gen"))
        assertTrue(lines[1].contains("847"))
        assertTrue(lines[1].contains("on"))
    }

    @Test
    fun `given RunStarted with optimization off - when onEvent called - then optimize shows off`() {
        // Arrange
        val config = defaultRunConfig.copy(optimizationEnabled = false)
        val event = ConversionEvent.RunStarted(
            config = config,
            totalFiles = 10,
            version = "1.0.0",
        )

        // Act
        val lines = collectOutput { it.onEvent(event) }

        // Assert
        assertTrue(lines[1].contains("off"))
    }

    @Test
    fun `given FileCompleted Success - when onEvent called - then outputs OK line with filename and duration`() {
        // Arrange
        val event = ConversionEvent.FileCompleted(
            fileName = "ic_add_circle_24.svg",
            duration = 142.milliseconds,
            result = FileResult.Success,
        )

        // Act
        val lines = collectOutput { it.onEvent(event) }

        // Assert
        assertEquals(expected = 1, actual = lines.size)
        assertTrue(lines[0].startsWith("[OK]"))
        assertTrue(lines[0].contains("ic_add_circle_24.svg"))
        assertTrue(lines[0].contains("142ms"))
    }

    @Test
    fun `given FileCompleted Failed - when onEvent called - then outputs FAIL line with error info`() {
        // Arrange
        val event = ConversionEvent.FileCompleted(
            fileName = "ic_broken_gradient.svg",
            duration = 50.milliseconds,
            result = FileResult.Failed(
                errorCode = ErrorCode.ParseSvgError,
                message = "Unsupported gradient type",
            ),
        )

        // Act
        val lines = collectOutput { it.onEvent(event) }

        // Assert
        assertEquals(expected = 1, actual = lines.size)
        assertTrue(lines[0].startsWith("[FAIL]"))
        assertTrue(lines[0].contains("ic_broken_gradient.svg"))
        assertTrue(lines[0].contains("ParseSvgError"))
        assertTrue(lines[0].contains("Unsupported gradient type"))
    }

    @Test
    fun `given RunCompleted with failures - when onEvent called - then outputs DONE summary and Failed section`() {
        // Arrange
        // First send some failed file events to populate the failure list
        val lines = mutableListOf<String>()
        val renderer = PlainTextRenderer(writer = lines::add)

        renderer.onEvent(
            ConversionEvent.FileCompleted(
                fileName = "ic_broken.svg",
                duration = 50.milliseconds,
                result = FileResult.Failed(
                    errorCode = ErrorCode.ParseSvgError,
                    message = "Unsupported gradient type: mesh-gradient",
                ),
            ),
        )
        lines.clear() // Clear the [FAIL] line, we only want to check RunCompleted output

        val event = ConversionEvent.RunCompleted(
            stats = RunStats(
                totalFiles = 847,
                succeeded = 844,
                failed = 3,
                totalDuration = 169.seconds,
                errorCounts = mapOf(ErrorCode.ParseSvgError to 3),
            ),
        )

        // Act
        renderer.onEvent(event)

        // Assert
        assertTrue(lines.any { it.startsWith("[DONE]") })
        assertTrue(lines.any { it.contains("844 succeeded") })
        assertTrue(lines.any { it.contains("3 failed") })
        assertTrue(lines.any { it.contains("Failed:") })
        assertTrue(lines.any { it.contains("ic_broken.svg") })
    }

    @Test
    fun `given RunCompleted with zero failures - when onEvent called - then outputs DONE without Failed section`() {
        // Arrange
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
        val lines = collectOutput { it.onEvent(event) }

        // Assert
        assertTrue(lines.any { it.startsWith("[DONE]") })
        assertTrue(lines.any { it.contains("10 succeeded") })
        assertTrue(lines.any { it.contains("0 failed") })
        assertFalse(lines.any { it.contains("Failed:") })
    }

    @Test
    fun `given UpdateAvailable event - when onEvent called - then outputs UPDATE line`() {
        // Arrange
        val event = ConversionEvent.UpdateAvailable(
            current = "2.2.0",
            latest = "2.3.0",
            releaseUrl = "https://github.com/rafaeltonholo/svg-to-compose/releases/v2.3.0",
            isWrapper = false,
        )

        // Act
        val lines = collectOutput { it.onEvent(event) }

        // Assert
        assertEquals(expected = 1, actual = lines.size)
        assertTrue(lines[0].startsWith("[UPDATE]"))
        assertTrue(lines[0].contains("v2.3.0"))
    }

    @Test
    fun `given FileStarted event - when onEvent called - then produces no output`() {
        // Arrange
        val event = ConversionEvent.FileStarted(
            fileName = "icon.svg",
            index = 0,
        )

        // Act
        val lines = collectOutput { it.onEvent(event) }

        // Assert
        assertTrue(lines.isEmpty())
    }

    @Test
    fun `given FileStepChanged event - when onEvent called - then produces no output`() {
        // Arrange
        val event = ConversionEvent.FileStepChanged(
            fileName = "icon.svg",
            step = ConversionPhase.Parsing,
        )

        // Act
        val lines = collectOutput { it.onEvent(event) }

        // Assert
        assertTrue(lines.isEmpty())
    }

    @Test
    fun `given output text - when checked for ANSI codes - then no escape sequences present`() {
        // Arrange
        val events = listOf(
            ConversionEvent.RunStarted(config = defaultRunConfig, totalFiles = 1, version = "1.0.0"),
            ConversionEvent.FileCompleted(
                fileName = "test.svg",
                duration = 50.milliseconds,
                result = FileResult.Failed(ErrorCode.ParseSvgError, "bad path"),
            ),
            ConversionEvent.RunCompleted(
                stats = RunStats(
                    totalFiles = 1,
                    succeeded = 0,
                    failed = 1,
                    totalDuration = 50.milliseconds,
                    errorCounts = mapOf(ErrorCode.ParseSvgError to 1),
                ),
            ),
            ConversionEvent.UpdateAvailable(
                current = "1.0.0",
                latest = "2.0.0",
                releaseUrl = "https://example.com",
                isWrapper = false,
            ),
        )

        // Act
        val lines = collectOutput { renderer ->
            events.forEach { renderer.onEvent(it) }
        }

        // Assert
        val ansiPattern = Regex("\u001b\\[")
        lines.forEach { line ->
            assertFalse(ansiPattern.containsMatchIn(line), "ANSI escape found in: $line")
        }
    }

    @Test
    fun `given RunCompleted - when duration over a minute - then formats as minutes and seconds`() {
        // Arrange
        val event = ConversionEvent.RunCompleted(
            stats = RunStats(
                totalFiles = 100,
                succeeded = 100,
                failed = 0,
                totalDuration = 2.minutes + 49.seconds,
                errorCounts = emptyMap(),
            ),
        )

        // Act
        val lines = collectOutput { it.onEvent(event) }

        // Assert
        val doneLine = lines.first { it.startsWith("[DONE]") }
        assertTrue(doneLine.contains("2m 49s"))
    }

    @Test
    fun `given RunCompleted - when throughput calculable - then shows icons per second`() {
        // Arrange
        val event = ConversionEvent.RunCompleted(
            stats = RunStats(
                totalFiles = 100,
                succeeded = 100,
                failed = 0,
                totalDuration = 20.seconds,
                errorCounts = emptyMap(),
            ),
        )

        // Act
        val lines = collectOutput { it.onEvent(event) }

        // Assert
        val doneLine = lines.first { it.startsWith("[DONE]") }
        assertTrue(doneLine.contains("5.0 icons/sec"))
    }
}
