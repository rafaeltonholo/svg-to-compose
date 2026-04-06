package dev.tonholo.s2c.output

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotEquals
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class ConversionEventTest {

    // region RunConfig
    @Test
    fun `given two RunConfigs with same values - when compared - then they are equal`() {
        // Arrange
        val config1 = RunConfig(
            inputPath = "/input",
            outputPath = "/output",
            packageName = "com.example",
            optimizationEnabled = true,
            recursive = false,
        )
        val config2 = RunConfig(
            inputPath = "/input",
            outputPath = "/output",
            packageName = "com.example",
            optimizationEnabled = true,
            recursive = false,
        )
        // Act & Assert
        assertEquals(expected = config1, actual = config2)
    }

    @Test
    fun `given a RunConfig - when copy changes a field - then the copy differs`() {
        // Arrange
        val config = RunConfig(
            inputPath = "/input",
            outputPath = "/output",
            packageName = "com.example",
            optimizationEnabled = true,
            recursive = false,
        )
        // Act
        val modified = config.copy(optimizationEnabled = false)
        // Assert
        assertNotEquals(illegal = config, actual = modified)
        assertEquals(expected = false, actual = modified.optimizationEnabled)
    }

    @Test
    fun `given a RunConfig - when toString called - then it contains class name`() {
        // Arrange
        val config = RunConfig(
            inputPath = "/input",
            outputPath = "/output",
            packageName = "com.example",
            optimizationEnabled = true,
            recursive = false,
        )
        // Act
        val result = config.toString()
        // Assert
        assert(result.contains("RunConfig"))
    }
    // endregion

    // region RunStats
    @Test
    fun `given two RunStats with same values - when compared - then they are equal`() {
        // Arrange
        val stats1 = RunStats(
            totalFiles = 10,
            succeeded = 8,
            failed = 2,
            totalDuration = 5.seconds,
            errorCounts = mapOf("ParseSvgError" to 2),
        )
        val stats2 = RunStats(
            totalFiles = 10,
            succeeded = 8,
            failed = 2,
            totalDuration = 5.seconds,
            errorCounts = mapOf("ParseSvgError" to 2),
        )
        // Act & Assert
        assertEquals(expected = stats1, actual = stats2)
    }

    @Test
    fun `given a RunStats - when copy changes a field - then the copy differs`() {
        // Arrange
        val stats = RunStats(
            totalFiles = 10,
            succeeded = 8,
            failed = 2,
            totalDuration = 5.seconds,
            errorCounts = emptyMap(),
        )
        // Act
        val modified = stats.copy(succeeded = 10, failed = 0)
        // Assert
        assertNotEquals(illegal = stats, actual = modified)
        assertEquals(expected = 10, actual = modified.succeeded)
        assertEquals(expected = 0, actual = modified.failed)
    }
    // endregion

    // region ConversionPhase
    @Test
    fun `given ConversionPhase enum - when values called - then all four phases are present`() {
        // Arrange & Act
        val phases = ConversionPhase.entries
        // Assert
        assertEquals(expected = 4, actual = phases.size)
        assertEquals(expected = ConversionPhase.Optimizing, actual = phases[0])
        assertEquals(expected = ConversionPhase.Parsing, actual = phases[1])
        assertEquals(expected = ConversionPhase.Generating, actual = phases[2])
        assertEquals(expected = ConversionPhase.Writing, actual = phases[3])
    }
    // endregion

    // region FileResult
    @Test
    fun `given FileResult Success - when checked - then it is Success type`() {
        // Arrange
        val result: FileResult = FileResult.Success
        // Act & Assert
        assertIs<FileResult.Success>(result)
    }

    @Test
    fun `given FileResult Failed - when created - then it holds error code and message`() {
        // Arrange
        val result = FileResult.Failed(
            errorCode = "ParseSvgError",
            message = "Unable to parse SVG file",
        )
        // Act & Assert
        assertIs<FileResult.Failed>(result)
        assertEquals(expected = "ParseSvgError", actual = result.errorCode)
        assertEquals(expected = "Unable to parse SVG file", actual = result.message)
    }

    @Test
    fun `given two identical FileResult Failed - when compared - then they are equal`() {
        // Arrange
        val result1 = FileResult.Failed(errorCode = "E1", message = "msg")
        val result2 = FileResult.Failed(errorCode = "E1", message = "msg")
        // Act & Assert
        assertEquals(expected = result1, actual = result2)
    }
    // endregion

    // region ConversionEvent.RunStarted
    @Test
    fun `given RunStarted event - when created - then it holds config, totalFiles, and version`() {
        // Arrange
        val config = RunConfig(
            inputPath = "/in",
            outputPath = "/out",
            packageName = "pkg",
            optimizationEnabled = false,
            recursive = false,
        )
        // Act
        val event = ConversionEvent.RunStarted(
            config = config,
            totalFiles = 5,
            version = "1.0.0",
        )
        // Assert
        assertIs<ConversionEvent>(event)
        assertEquals(expected = config, actual = event.config)
        assertEquals(expected = 5, actual = event.totalFiles)
        assertEquals(expected = "1.0.0", actual = event.version)
    }
    // endregion

    // region ConversionEvent.FileStarted
    @Test
    fun `given FileStarted event - when created - then it holds fileName and index`() {
        // Arrange & Act
        val event = ConversionEvent.FileStarted(
            fileName = "icon.svg",
            index = 3,
        )
        // Assert
        assertIs<ConversionEvent>(event)
        assertEquals(expected = "icon.svg", actual = event.fileName)
        assertEquals(expected = 3, actual = event.index)
    }
    // endregion

    // region ConversionEvent.FileStepChanged
    @Test
    fun `given FileStepChanged event - when created - then it holds fileName and step`() {
        // Arrange & Act
        val event = ConversionEvent.FileStepChanged(
            fileName = "icon.svg",
            step = ConversionPhase.Parsing,
        )
        // Assert
        assertIs<ConversionEvent>(event)
        assertEquals(expected = "icon.svg", actual = event.fileName)
        assertEquals(expected = ConversionPhase.Parsing, actual = event.step)
    }
    // endregion

    // region ConversionEvent.FileCompleted
    @Test
    fun `given FileCompleted event with Success result - when created - then it holds correct values`() {
        // Arrange & Act
        val event = ConversionEvent.FileCompleted(
            fileName = "icon.svg",
            duration = 150.milliseconds,
            result = FileResult.Success,
        )
        // Assert
        assertIs<ConversionEvent>(event)
        assertEquals(expected = "icon.svg", actual = event.fileName)
        assertEquals(expected = 150.milliseconds, actual = event.duration)
        assertIs<FileResult.Success>(event.result)
    }

    @Test
    fun `given FileCompleted event with Failed result - when created - then it holds error info`() {
        // Arrange
        val failResult = FileResult.Failed(
            errorCode = "ParseSvgError",
            message = "bad svg",
        )
        // Act
        val event = ConversionEvent.FileCompleted(
            fileName = "bad.svg",
            duration = 200.milliseconds,
            result = failResult,
        )
        // Assert
        val failed = assertIs<FileResult.Failed>(event.result)
        assertEquals(expected = "ParseSvgError", actual = failed.errorCode)
    }
    // endregion

    // region ConversionEvent.RunCompleted
    @Test
    fun `given RunCompleted event - when created - then it holds stats`() {
        // Arrange
        val stats = RunStats(
            totalFiles = 10,
            succeeded = 9,
            failed = 1,
            totalDuration = 3.seconds,
            errorCounts = mapOf("E1" to 1),
        )
        // Act
        val event = ConversionEvent.RunCompleted(stats = stats)
        // Assert
        assertIs<ConversionEvent>(event)
        assertEquals(expected = stats, actual = event.stats)
    }
    // endregion

    // region ConversionEvent.UpdateAvailable
    @Test
    fun `given UpdateAvailable event - when created - then it holds version info`() {
        // Arrange & Act
        val event = ConversionEvent.UpdateAvailable(
            current = "1.0.0",
            latest = "2.0.0",
            releaseUrl = "https://github.com/example/releases/v2.0.0",
            isWrapper = false,
        )
        // Assert
        assertIs<ConversionEvent>(event)
        assertEquals(expected = "1.0.0", actual = event.current)
        assertEquals(expected = "2.0.0", actual = event.latest)
        assertEquals(expected = "https://github.com/example/releases/v2.0.0", actual = event.releaseUrl)
        assertEquals(expected = false, actual = event.isWrapper)
    }
    // endregion
}
