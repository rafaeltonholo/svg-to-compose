package dev.tonholo.s2c

import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.emitter.CodeEmitter
import dev.tonholo.s2c.emitter.FormatConfig
import dev.tonholo.s2c.emitter.fakeCodeEmitterFactory
import dev.tonholo.s2c.emitter.editorconfig.fakeEditorConfigReader
import dev.tonholo.s2c.emitter.template.config.fakeTemplateConfigReader
import dev.tonholo.s2c.io.fakeFileManager
import dev.tonholo.s2c.io.fakeIconWriter
import dev.tonholo.s2c.logger.fakeLogger
import dev.tonholo.s2c.optimizer.fakeOptimizerFactory
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.ConversionPhase
import dev.tonholo.s2c.output.FileResult
import dev.tonholo.s2c.parser.ParserConfig
import dev.tonholo.s2c.parser.fakeImageParser
import dev.tonholo.s2c.runtime.fakeS2cConfig
import okio.Path.Companion.toPath
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration

class ProcessorEventTest {
    private val events = mutableListOf<ConversionEvent>()
    private val onEvent: (ConversionEvent) -> Unit = { events.add(it) }

    private fun testParserConfig(optimize: Boolean = false) = ParserConfig(
        pkg = "dev.test",
        theme = "TestTheme",
        optimize = optimize,
        receiverType = null,
        addToMaterial = false,
        kmpPreview = false,
        noPreview = true,
        makeInternal = false,
        minified = false,
    )

    private fun minimalIconFileContents() = IconFileContents(
        pkg = "dev.test",
        iconName = "TestIcon",
        theme = "TestTheme",
        width = 24f,
        height = 24f,
        viewportWidth = 24f,
        viewportHeight = 24f,
        nodes = emptyList<ImageVectorNode>(),
    )

    private val stubCodeEmitter = object : CodeEmitter {
        override fun emit(contents: IconFileContents): String = "// generated code"
    }

    private fun createLogger() = fakeLogger {
        debugSection<Any?> { _, block -> block() }
        verboseSection<Any?> { _, block -> block() }
    }

    private fun createProcessor(
        isDirectory: Boolean = false,
        filesToProcess: List<String> = emptyList(),
        parseResult: (okio.Path, String, ParserConfig) -> IconFileContents = { _, _, _ -> minimalIconFileContents() },
        optimizerFactory: dev.tonholo.s2c.optimizer.OptimizerFactory = fakeOptimizerFactory {
            optimize { file -> file }
        },
        existsResult: Boolean = true,
    ): Processor {
        val inputPath = "/input/icon.svg".toPath()
        val outputPath = "/output/Icon.kt".toPath()

        val fileManager = fakeFileManager {
            isDirectory { path ->
                if (path == inputPath) isDirectory else false
            }
            exists { existsResult }
            findFilesToProcess { _, _, _, _ ->
                filesToProcess.map { it.toPath() }
            }
            copy { _, _ -> }
        }

        val iconWriter = fakeIconWriter {
            write { _, _, output -> output }
        }

        val parser = fakeImageParser {
            parseToModel(parseResult)
        }

        val codeEmitterFactory = fakeCodeEmitterFactory {
            create { _, _, _ -> stubCodeEmitter }
        }

        val editorConfigReader = fakeEditorConfigReader {
            resolve { _, defaults -> defaults }
        }

        val templateConfigReader = fakeTemplateConfigReader {
            discover { null }
        }

        val config = fakeS2cConfig()
        val logger = createLogger()

        return Processor(
            config = config,
            logger = logger,
            fileManager = fileManager,
            iconWriter = iconWriter,
            tempDirectory = null,
            optimizers = optimizerFactory,
            parser = parser,
            codeEmitterFactory = codeEmitterFactory,
            editorConfigReader = editorConfigReader,
            templateConfigReader = templateConfigReader,
        )
    }

    @Test
    fun `given single svg file - when run is called - then emits correct event sequence`() {
        // Arrange
        val processor = createProcessor()

        // Act
        processor.run(
            path = "/input/icon.svg",
            output = "/output/Icon.kt",
            config = testParserConfig(optimize = false),
            recursive = false,
            onEvent = onEvent,
        )

        // Assert
        val eventTypes = events.map { it::class.simpleName }
        assertEquals(
            expected = listOf(
                "RunStarted",
                "FileStarted",
                "FileStepChanged",
                "FileStepChanged",
                "FileStepChanged",
                "FileCompleted",
                "RunCompleted",
            ),
            actual = eventTypes,
        )
        val steps = events.filterIsInstance<ConversionEvent.FileStepChanged>().map { it.step }
        assertEquals(
            expected = listOf(ConversionPhase.Parsing, ConversionPhase.Generating, ConversionPhase.Writing),
            actual = steps,
        )
    }

    @Test
    fun `given single svg file with optimization - when run is called - then emits Optimizing step`() {
        // Arrange
        val processor = createProcessor()

        // Act
        processor.run(
            path = "/input/icon.svg",
            output = "/output/Icon.kt",
            config = testParserConfig(optimize = true),
            recursive = false,
            onEvent = onEvent,
        )

        // Assert
        val steps = events.filterIsInstance<ConversionEvent.FileStepChanged>().map { it.step }
        assertEquals(
            expected = listOf(
                ConversionPhase.Optimizing,
                ConversionPhase.Parsing,
                ConversionPhase.Generating,
                ConversionPhase.Writing,
            ),
            actual = steps,
        )
    }

    @Test
    fun `given directory with two files - when run is called - then emits FileStarted for each file`() {
        // Arrange
        val processor = createProcessor(
            isDirectory = true,
            filesToProcess = listOf("/input/icon1.svg", "/input/icon2.svg"),
        )

        // Act
        processor.run(
            path = "/input",
            output = "/output",
            config = testParserConfig(),
            recursive = false,
            onEvent = onEvent,
        )

        // Assert
        val runStarted = events.filterIsInstance<ConversionEvent.RunStarted>().single()
        assertEquals(expected = 2, actual = runStarted.totalFiles)

        val fileStartedEvents = events.filterIsInstance<ConversionEvent.FileStarted>()
        assertEquals(expected = 2, actual = fileStartedEvents.size)
        assertEquals(expected = 0, actual = fileStartedEvents[0].index)
        assertEquals(expected = 1, actual = fileStartedEvents[1].index)
    }

    @Test
    fun `given file that fails to parse - when run is called - then emits FileCompleted with Failed result`() {
        // Arrange
        val processor = createProcessor(
            parseResult = { _, _, _ -> throw RuntimeException("parse error") },
        )

        // Act
        processor.run(
            path = "/input/icon.svg",
            output = "/output/Icon.kt",
            config = testParserConfig(),
            recursive = false,
            onEvent = onEvent,
        )

        // Assert
        val fileCompleted = events.filterIsInstance<ConversionEvent.FileCompleted>().single()
        assertTrue(
            actual = fileCompleted.result is FileResult.Failed,
            message = "Expected FileResult.Failed but was ${fileCompleted.result}",
        )

        val runCompleted = events.filterIsInstance<ConversionEvent.RunCompleted>().single()
        assertEquals(expected = 1, actual = runCompleted.stats.failed)
    }

    @Test
    fun `given batch with mixed success and failure - when run completes - then RunStats counts are correct`() {
        // Arrange
        var callCount = 0
        val processor = createProcessor(
            isDirectory = true,
            filesToProcess = listOf("/input/icon1.svg", "/input/icon2.svg"),
            parseResult = { _, _, _ ->
                callCount++
                if (callCount == 2) throw RuntimeException("parse error")
                minimalIconFileContents()
            },
        )

        // Act
        processor.run(
            path = "/input",
            output = "/output",
            config = testParserConfig(),
            recursive = false,
            onEvent = onEvent,
        )

        // Assert
        val runCompleted = events.filterIsInstance<ConversionEvent.RunCompleted>().single()
        assertEquals(expected = 2, actual = runCompleted.stats.totalFiles)
        assertEquals(expected = 1, actual = runCompleted.stats.succeeded)
        assertEquals(expected = 1, actual = runCompleted.stats.failed)
    }

    @Test
    fun `given successful run - when run completes - then RunCompleted duration is non-negative`() {
        // Arrange
        val processor = createProcessor()

        // Act
        processor.run(
            path = "/input/icon.svg",
            output = "/output/Icon.kt",
            config = testParserConfig(),
            recursive = false,
            onEvent = onEvent,
        )

        // Assert
        val runCompleted = events.filterIsInstance<ConversionEvent.RunCompleted>().single()
        assertTrue(
            actual = runCompleted.stats.totalDuration >= Duration.ZERO,
            message = "Expected non-negative duration but was ${runCompleted.stats.totalDuration}",
        )
    }

    @Test
    fun `given single file - when run is called - then RunStarted has totalFiles equal to 1`() {
        // Arrange
        val processor = createProcessor()

        // Act
        processor.run(
            path = "/input/icon.svg",
            output = "/output/Icon.kt",
            config = testParserConfig(),
            recursive = false,
            onEvent = onEvent,
        )

        // Assert
        val runStarted = events.filterIsInstance<ConversionEvent.RunStarted>().single()
        assertEquals(expected = 1, actual = runStarted.totalFiles)
    }
}
