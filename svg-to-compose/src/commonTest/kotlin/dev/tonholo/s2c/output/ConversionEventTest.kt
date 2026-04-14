package dev.tonholo.s2c.output

import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.output.ConversionEventTest.Companion.TEST_PARSER_CONFIG
import dev.tonholo.s2c.parser.ParserConfig
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import kotlin.test.assertNotEquals
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class ConversionEventTest {
    companion object {
        val TEST_PARSER_CONFIG = ParserConfig(
            pkg = "com.example",
            theme = "TestTheme",
            optimize = false,
            receiverType = null,
            addToMaterial = false,
            kmpPreview = false,
            noPreview = false,
            makeInternal = false,
            minified = false,
        )
    }

    // region RunConfig
    @Test
    fun `given two RunConfigs with same values - when compared - then they are equal`() {
        // Arrange
        val config1 = RunConfig(
            inputPath = "/input",
            outputPath = "/output",
            parserConfig = TEST_PARSER_CONFIG,
            packageName = "com.example",
            optimizationEnabled = true,
            parallel = 1,
            recursive = false,
        )
        val config2 = RunConfig(
            inputPath = "/input",
            outputPath = "/output",
            parserConfig = TEST_PARSER_CONFIG,
            packageName = "com.example",
            optimizationEnabled = true,
            parallel = 1,
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
            parserConfig = TEST_PARSER_CONFIG,
            packageName = "com.example",
            optimizationEnabled = true,
            parallel = 1,
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
            parserConfig = TEST_PARSER_CONFIG,
            packageName = "com.example",
            optimizationEnabled = true,
            parallel = 1,
            recursive = false,
        )
        // Act
        val result = config.toString()
        // Assert
        assertEquals(expected = true, actual = result.contains("RunConfig"))
    }

    @Test
    fun `given a ParserConfig - when RunConfig from is called - then fields are mapped correctly`() {
        // Arrange
        val parserConfig = ParserConfig(
            pkg = "com.example.icons",
            theme = "MyTheme",
            optimize = true,
            receiverType = null,
            addToMaterial = false,
            kmpPreview = false,
            noPreview = false,
            makeInternal = false,
            minified = false,
        )
        // Act
        val runConfig = RunConfig.from(
            config = parserConfig,
            inputPath = "/input/icons",
            outputPath = "/output/generated",
            recursive = true,
        )
        // Assert
        assertEquals(expected = "/input/icons", actual = runConfig.inputPath)
        assertEquals(expected = "/output/generated", actual = runConfig.outputPath)
        assertEquals(expected = "com.example.icons", actual = runConfig.packageName)
        assertEquals(expected = true, actual = runConfig.optimizationEnabled)
        assertEquals(expected = true, actual = runConfig.recursive)
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
            errorCounts = mapOf(ErrorCode.ParseSvgError to 2),
        )
        val stats2 = RunStats(
            totalFiles = 10,
            succeeded = 8,
            failed = 2,
            totalDuration = 5.seconds,
            errorCounts = mapOf(ErrorCode.ParseSvgError to 2),
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

    @Test
    fun `given negative totalFiles - when RunStats is created - then it throws IllegalArgumentException`() {
        // Arrange & Act & Assert
        assertFailsWith<IllegalArgumentException> {
            RunStats(
                totalFiles = -1,
                succeeded = 0,
                failed = 0,
                totalDuration = 1.seconds,
                errorCounts = emptyMap(),
            )
        }
    }

    @Test
    fun `given negative succeeded - when RunStats is created - then it throws IllegalArgumentException`() {
        // Arrange & Act & Assert
        assertFailsWith<IllegalArgumentException> {
            RunStats(
                totalFiles = 0,
                succeeded = -1,
                failed = 0,
                totalDuration = 1.seconds,
                errorCounts = emptyMap(),
            )
        }
    }

    @Test
    fun `given negative failed - when RunStats is created - then it throws IllegalArgumentException`() {
        // Arrange & Act & Assert
        assertFailsWith<IllegalArgumentException> {
            RunStats(
                totalFiles = 0,
                succeeded = 0,
                failed = -1,
                totalDuration = 1.seconds,
                errorCounts = emptyMap(),
            )
        }
    }

    @Test
    fun `given succeeded plus failed not equal to totalFiles - when RunStats is created - then it throws IllegalArgumentException`() {
        // Arrange & Act & Assert
        assertFailsWith<IllegalArgumentException> {
            RunStats(
                totalFiles = 10,
                succeeded = 5,
                failed = 3,
                totalDuration = 1.seconds,
                errorCounts = emptyMap(),
            )
        }
    }
    // endregion

    // region ConversionPhase
    @Test
    fun `given ConversionPhase enum - when entries are listed - then they follow the processing pipeline order`() {
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
            errorCode = ErrorCode.ParseSvgError,
            message = "Unable to parse SVG file",
        )
        // Act & Assert
        assertIs<FileResult.Failed>(result)
        assertEquals(expected = ErrorCode.ParseSvgError, actual = result.errorCode)
        assertEquals(expected = "Unable to parse SVG file", actual = result.message)
    }

    @Test
    fun `given two identical FileResult Failed - when compared - then they are equal`() {
        // Arrange
        val result1 = FileResult.Failed(errorCode = ErrorCode.FileNotFoundError, message = "msg")
        val result2 = FileResult.Failed(errorCode = ErrorCode.FileNotFoundError, message = "msg")
        // Act & Assert
        assertEquals(expected = result1, actual = result2)
    }
    // endregion

    // region ConversionEvent.RunStarted
    @Test
    fun `given RunStarted event - when created - then it holds config and totalFiles and version`() {
        // Arrange
        val config = RunConfig(
            inputPath = "/in",
            outputPath = "/out",
            parserConfig = TEST_PARSER_CONFIG,
            packageName = "pkg",
            optimizationEnabled = false,
            parallel = 1,
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
            errorCode = ErrorCode.ParseSvgError,
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
        assertEquals(expected = ErrorCode.ParseSvgError, actual = failed.errorCode)
    }

    @Test
    fun `given FileCompleted event with Duration ZERO - when created - then duration is zero`() {
        // Arrange & Act
        val event = ConversionEvent.FileCompleted(
            fileName = "instant.svg",
            duration = Duration.ZERO,
            result = FileResult.Success,
        )
        // Assert
        assertEquals(expected = Duration.ZERO, actual = event.duration)
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
            errorCounts = mapOf(ErrorCode.ParseSvgError to 1),
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

    @Test
    fun `given UpdateAvailable event with isWrapper true - when created - then isWrapper is true`() {
        // Arrange & Act
        val event = ConversionEvent.UpdateAvailable(
            current = "1.0.0",
            latest = "1.1.0",
            releaseUrl = "https://github.com/example/releases/v1.1.0",
            isWrapper = true,
        )
        // Assert
        assertIs<ConversionEvent>(event)
        assertEquals(expected = true, actual = event.isWrapper)
    }
    // endregion
}
