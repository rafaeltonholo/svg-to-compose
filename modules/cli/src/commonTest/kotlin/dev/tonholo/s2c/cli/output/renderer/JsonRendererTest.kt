package dev.tonholo.s2c.cli.output.renderer

import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.ConversionPhase
import dev.tonholo.s2c.output.FileResult
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.output.RunStats
import dev.tonholo.s2c.parser.ParserConfig
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.double
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class JsonRendererTest {

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
        parallel = 0,
        recursive = false,
    )

    private val json = Json { ignoreUnknownKeys = true }

    private fun collectOutput(block: (JsonRenderer) -> Unit): List<String> {
        val lines = mutableListOf<String>()
        val renderer = JsonRenderer(writer = lines::add)
        block(renderer)
        return lines
    }

    @Test
    fun `given RunStarted event - when onEvent called - then outputs valid start JSON`() {
        // Arrange
        val event = ConversionEvent.RunStarted(
            config = defaultRunConfig,
            totalFiles = 847,
            version = "2.2.0",
        )

        // Act
        val lines = collectOutput { it.onEvent(event) }

        // Assert
        assertEquals(expected = 1, actual = lines.size)
        val obj = json.decodeFromString<JsonObject>(lines.first())
        assertEquals(expected = "start", actual = obj["event"]?.jsonPrimitive?.content)
        assertEquals(expected = "2.2.0", actual = obj["version"]?.jsonPrimitive?.content)
        assertEquals(expected = "./icons", actual = obj["input"]?.jsonPrimitive?.content)
        assertEquals(expected = "./generated", actual = obj["output"]?.jsonPrimitive?.content)
        assertEquals(expected = 847, actual = obj["total_files"]?.jsonPrimitive?.int)
    }

    @Test
    fun `given FileStarted event - when onEvent called - then outputs file_start JSON`() {
        // Arrange
        val event = ConversionEvent.FileStarted(
            fileName = "ic_add_circle_24.svg",
            index = 1,
        )

        // Act
        val lines = collectOutput { it.onEvent(event) }

        // Assert
        assertEquals(expected = 1, actual = lines.size)
        val obj = json.decodeFromString<JsonObject>(lines.first())
        assertEquals(expected = "file_start", actual = obj["event"]?.jsonPrimitive?.content)
        assertEquals(expected = "ic_add_circle_24.svg", actual = obj["file"]?.jsonPrimitive?.content)
        assertEquals(expected = 1, actual = obj["index"]?.jsonPrimitive?.int)
    }

    @Test
    fun `given FileStepChanged event - when onEvent called - then outputs file_step JSON`() {
        // Arrange
        val event = ConversionEvent.FileStepChanged(
            fileName = "ic_add_circle_24.svg",
            step = ConversionPhase.Optimizing,
        )

        // Act
        val lines = collectOutput { it.onEvent(event) }

        // Assert
        assertEquals(expected = 1, actual = lines.size)
        val obj = json.decodeFromString<JsonObject>(lines.first())
        assertEquals(expected = "file_step", actual = obj["event"]?.jsonPrimitive?.content)
        assertEquals(expected = "ic_add_circle_24.svg", actual = obj["file"]?.jsonPrimitive?.content)
        assertEquals(expected = "optimizing", actual = obj["step"]?.jsonPrimitive?.content)
    }

    @Test
    fun `given FileCompleted Success - when onEvent called - then outputs file_complete JSON with status success`() {
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
        val obj = json.decodeFromString<JsonObject>(lines.first())
        assertEquals(expected = "file_complete", actual = obj["event"]?.jsonPrimitive?.content)
        assertEquals(expected = "ic_add_circle_24.svg", actual = obj["file"]?.jsonPrimitive?.content)
        assertEquals(expected = "success", actual = obj["status"]?.jsonPrimitive?.content)
        assertEquals(expected = 142L, actual = obj["duration_ms"]?.jsonPrimitive?.long)
        assertNull(obj["error_code"])
        assertNull(obj["message"])
    }

    @Test
    fun `given FileCompleted Failed - when onEvent called - then outputs file_complete JSON with error info`() {
        // Arrange
        val event = ConversionEvent.FileCompleted(
            fileName = "ic_broken.svg",
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
        val obj = json.decodeFromString<JsonObject>(lines.first())
        assertEquals(expected = "file_complete", actual = obj["event"]?.jsonPrimitive?.content)
        assertEquals(expected = "ic_broken.svg", actual = obj["file"]?.jsonPrimitive?.content)
        assertEquals(expected = "failed", actual = obj["status"]?.jsonPrimitive?.content)
        assertEquals(expected = "ParseSvgError", actual = obj["error_code"]?.jsonPrimitive?.content)
        assertEquals(expected = "Unsupported gradient type", actual = obj["message"]?.jsonPrimitive?.content)
    }

    @Test
    fun `given RunCompleted - when onEvent called - then outputs done JSON with aggregate stats`() {
        // Arrange
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
        val lines = collectOutput { it.onEvent(event) }

        // Assert
        assertEquals(expected = 1, actual = lines.size)
        val obj = json.decodeFromString<JsonObject>(lines.first())
        assertEquals(expected = "done", actual = obj["event"]?.jsonPrimitive?.content)
        assertEquals(expected = 844, actual = obj["succeeded"]?.jsonPrimitive?.int)
        assertEquals(expected = 3, actual = obj["failed"]?.jsonPrimitive?.int)
        assertEquals(expected = 169000L, actual = obj["duration_ms"]?.jsonPrimitive?.long)
        assertTrue(obj["throughput"]?.jsonPrimitive?.double?.let { it > 0.0 } ?: false)
    }

    @Test
    fun `given UpdateAvailable - when onEvent called - then outputs update_available JSON`() {
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
        val obj = json.decodeFromString<JsonObject>(lines.first())
        assertEquals(expected = "update_available", actual = obj["event"]?.jsonPrimitive?.content)
        assertEquals(expected = "2.2.0", actual = obj["current"]?.jsonPrimitive?.content)
        assertEquals(expected = "2.3.0", actual = obj["latest"]?.jsonPrimitive?.content)
        assertEquals(
            expected = "https://github.com/rafaeltonholo/svg-to-compose/releases/v2.3.0",
            actual = obj["url"]?.jsonPrimitive?.content,
        )
    }

    @Test
    fun `given multiple events - when onEvent called for each - then each line is independently parseable JSON`() {
        // Arrange
        val events = listOf(
            ConversionEvent.RunStarted(
                config = defaultRunConfig,
                totalFiles = 2,
                version = "2.2.0",
            ),
            ConversionEvent.FileStarted(fileName = "a.svg", index = 0),
            ConversionEvent.FileCompleted(
                fileName = "a.svg",
                duration = 100.milliseconds,
                result = FileResult.Success,
            ),
            ConversionEvent.RunCompleted(
                stats = RunStats(
                    totalFiles = 2,
                    succeeded = 2,
                    failed = 0,
                    totalDuration = 200.milliseconds,
                    errorCounts = emptyMap(),
                ),
            ),
        )

        // Act
        val lines = collectOutput { renderer ->
            events.forEach { renderer.onEvent(it) }
        }

        // Assert
        assertEquals(expected = 4, actual = lines.size)
        lines.forEach { line ->
            val obj = json.decodeFromString<JsonObject>(line)
            assertTrue(obj.containsKey("event"))
        }
    }

    @Test
    fun `given output lines - when checked for ANSI codes - then no escape sequences present`() {
        // Arrange
        val events = listOf(
            ConversionEvent.RunStarted(config = defaultRunConfig, totalFiles = 1, version = "1.0.0"),
            ConversionEvent.FileCompleted(
                fileName = "test.svg",
                duration = 50.milliseconds,
                result = FileResult.Failed(ErrorCode.ParseSvgError, "bad path"),
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
}
