package dev.tonholo.s2c.cli.output.tui.reducer

import dev.tonholo.s2c.cli.output.tui.state.CurrentFileState
import dev.tonholo.s2c.cli.output.tui.state.ProgressState
import dev.tonholo.s2c.cli.output.tui.state.RecentFileEntry
import dev.tonholo.s2c.cli.output.tui.state.RecentFilesState
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
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class CurrentFileReducerTest {

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
        parallel = 1,
        recursive = false,
    )

    // --- reduceCurrentFiles tests ---

    @Test
    fun `given empty map - when FileStarted received - then file state created`() {
        // Arrange
        val event = ConversionEvent.FileStarted(fileName = "icon.svg", index = 0)

        // Act
        val result = reduceCurrentFiles(
            state = linkedMapOf(),
            event = event,
            optimizationEnabled = true,
        )

        // Assert
        val fileState = result["icon.svg"]
        checkNotNull(fileState)
        assertEquals(expected = "icon.svg", actual = fileState.fileName)
        assertEquals(expected = ConversionPhase.Optimizing, actual = fileState.currentPhase)
        assertTrue(fileState.completedPhases.isEmpty())
        assertEquals(expected = true, actual = fileState.optimizationEnabled)
    }

    @Test
    fun `given empty map - when FileStarted received with optimization disabled - then starts at Parsing`() {
        // Arrange
        val event = ConversionEvent.FileStarted(fileName = "icon.svg", index = 0)

        // Act
        val result = reduceCurrentFiles(
            state = linkedMapOf(),
            event = event,
            optimizationEnabled = false,
        )

        // Assert
        assertEquals(expected = ConversionPhase.Parsing, actual = result["icon.svg"]?.currentPhase)
        assertEquals(expected = false, actual = result["icon.svg"]?.optimizationEnabled)
    }

    @Test
    fun `given file at Optimizing - when FileStepChanged to Parsing - then phase updated and Optimizing completed`() {
        // Arrange
        val fileState = CurrentFileState(
            fileName = "icon.svg",
            currentPhase = ConversionPhase.Optimizing,
        )
        val state = linkedMapOf("icon.svg" to fileState)
        val event = ConversionEvent.FileStepChanged(
            fileName = "icon.svg",
            step = ConversionPhase.Parsing,
        )

        // Act
        val result = reduceCurrentFiles(state = state, event = event, optimizationEnabled = true)

        // Assert
        assertEquals(expected = ConversionPhase.Parsing, actual = result["icon.svg"]?.currentPhase)
        assertTrue(result["icon.svg"]?.completedPhases?.contains(ConversionPhase.Optimizing) ?: false)
    }

    @Test
    fun `given file in map - when FileCompleted Success - then file removed from map`() {
        // Arrange
        val fileState = CurrentFileState(
            fileName = "icon.svg",
            currentPhase = ConversionPhase.Writing,
        )
        val state = linkedMapOf("icon.svg" to fileState)
        val event = ConversionEvent.FileCompleted(
            fileName = "icon.svg",
            duration = 100.milliseconds,
            result = FileResult.Success,
        )

        // Act
        val result = reduceCurrentFiles(state = state, event = event, optimizationEnabled = true)

        // Assert
        assertTrue(result.isEmpty())
    }

    @Test
    fun `given file in map - when FileCompleted Failed - then file removed from map`() {
        // Arrange
        val fileState = CurrentFileState(
            fileName = "icon.svg",
            currentPhase = ConversionPhase.Parsing,
        )
        val state = linkedMapOf("icon.svg" to fileState)
        val event = ConversionEvent.FileCompleted(
            fileName = "icon.svg",
            duration = 50.milliseconds,
            result = FileResult.Failed(
                errorCode = ErrorCode.ParseSvgError,
                message = "Bad path",
            ),
        )

        // Act
        val result = reduceCurrentFiles(state = state, event = event, optimizationEnabled = true)

        // Assert
        assertTrue(result.isEmpty())
    }

    @Test
    fun `given any state - when RunStarted received - then state unchanged`() {
        // Arrange
        val fileState = CurrentFileState(fileName = "icon.svg", currentPhase = ConversionPhase.Parsing)
        val state = linkedMapOf("icon.svg" to fileState)
        val event = ConversionEvent.RunStarted(
            config = defaultRunConfig,
            totalFiles = 10,
            version = "1.0.0",
        )

        // Act
        val result = reduceCurrentFiles(state = state, event = event, optimizationEnabled = true)

        // Assert
        assertEquals(expected = state, actual = result)
    }

    @Test
    fun `given multiple files - when one completes - then only that file removed`() {
        // Arrange
        val state = linkedMapOf(
            "a.svg" to CurrentFileState(fileName = "a.svg", currentPhase = ConversionPhase.Parsing),
            "b.svg" to CurrentFileState(fileName = "b.svg", currentPhase = ConversionPhase.Writing),
        )
        val event = ConversionEvent.FileCompleted(
            fileName = "a.svg",
            duration = 100.milliseconds,
            result = FileResult.Success,
        )

        // Act
        val result = reduceCurrentFiles(state = state, event = event, optimizationEnabled = true)

        // Assert
        assertEquals(expected = 1, actual = result.size)
        assertNull(result["a.svg"])
        assertEquals(expected = ConversionPhase.Writing, actual = result["b.svg"]?.currentPhase)
    }

    @Test
    fun `given file in map - when FileStepChanged for different file - then original unchanged`() {
        // Arrange
        val fileState = CurrentFileState(fileName = "a.svg", currentPhase = ConversionPhase.Optimizing)
        val state = linkedMapOf("a.svg" to fileState)
        val event = ConversionEvent.FileStepChanged(
            fileName = "b.svg",
            step = ConversionPhase.Parsing,
        )

        // Act
        val result = reduceCurrentFiles(state = state, event = event, optimizationEnabled = true)

        // Assert
        assertEquals(expected = state, actual = result)
    }

    @Test
    fun `given file at Parsing - when FileStepChanged to same phase - then state unchanged`() {
        // Arrange
        val fileState = CurrentFileState(fileName = "icon.svg", currentPhase = ConversionPhase.Parsing)
        val state = linkedMapOf("icon.svg" to fileState)
        val event = ConversionEvent.FileStepChanged(
            fileName = "icon.svg",
            step = ConversionPhase.Parsing,
        )

        // Act
        val result = reduceCurrentFiles(state = state, event = event, optimizationEnabled = true)

        // Assert
        assertEquals(expected = state, actual = result)
        assertEquals(expected = ConversionPhase.Parsing, actual = result["icon.svg"]?.currentPhase)
        assertTrue(result["icon.svg"]?.completedPhases?.isEmpty() ?: false)
    }

    // --- reduceProgress tests ---

    @Test
    fun `given progress state - when FileStarted - then pending decremented`() {
        // Arrange
        val state = ProgressState(total = 10, pending = 10)
        val event = ConversionEvent.FileStarted(fileName = "icon.svg", index = 0)

        // Act
        val result = reduceProgress(state = state, event = event)

        // Assert
        assertEquals(expected = 9, actual = result.pending)
        assertEquals(expected = 0, actual = result.completed)
    }

    @Test
    fun `given pending zero - when FileStarted - then pending clamped at zero`() {
        // Arrange
        val state = ProgressState(total = 5L, pending = 0L)
        val event = ConversionEvent.FileStarted(fileName = "extra.svg", index = 6)

        // Act
        val result = reduceProgress(state = state, event = event)

        // Assert
        assertEquals(expected = 0L, actual = result.pending)
    }

    // --- reduceRecentFiles tests ---

    @Test
    fun `given empty recent files - when FileCompleted Success - then entry added`() {
        // Arrange
        val state = RecentFilesState()
        val event = ConversionEvent.FileCompleted(
            fileName = "icon.svg",
            duration = 142.milliseconds,
            result = FileResult.Success,
        )

        // Act
        val result = reduceRecentFiles(state = state, event = event)

        // Assert
        assertEquals(expected = 1, actual = result.entries.size)
        assertEquals(expected = "icon.svg", actual = result.entries.first().fileName)
        assertEquals(expected = FileResult.Success, actual = result.entries.first().result)
    }

    @Test
    fun `given empty recent files - when FileCompleted Failed - then entry added with failed result`() {
        // Arrange
        val state = RecentFilesState()
        val event = ConversionEvent.FileCompleted(
            fileName = "broken.svg",
            duration = 50.milliseconds,
            result = FileResult.Failed(
                errorCode = ErrorCode.ParseSvgError,
                message = "Invalid path",
            ),
        )

        // Act
        val result = reduceRecentFiles(state = state, event = event)

        // Assert
        assertEquals(expected = 1, actual = result.entries.size)
        assertTrue(result.entries.first().result is FileResult.Failed)
    }

    @Test
    fun `given recent files one below capacity - when FileCompleted arrives - then entry appended without eviction`() {
        // Arrange
        val entries = (1..2).map { i ->
            RecentFileEntry(
                fileName = "file$i.svg",
                result = FileResult.Success,
                duration = (i * 10).milliseconds,
            )
        }
        val state = RecentFilesState(entries = entries, maxEntries = 3)
        val event = ConversionEvent.FileCompleted(
            fileName = "file3.svg",
            duration = 30.milliseconds,
            result = FileResult.Success,
        )

        // Act
        val result = reduceRecentFiles(state = state, event = event)

        // Assert
        assertEquals(expected = 3, actual = result.entries.size)
        assertEquals(expected = "file1.svg", actual = result.entries.first().fileName)
        assertEquals(expected = "file3.svg", actual = result.entries.last().fileName)
    }

    @Test
    fun `given recent files at max capacity - when new entry added - then oldest evicted`() {
        // Arrange
        val entries = (1..3).map { i ->
            RecentFileEntry(
                fileName = "file$i.svg",
                result = FileResult.Success,
                duration = (i * 10).milliseconds,
            )
        }
        val state = RecentFilesState(entries = entries, maxEntries = 3)
        val event = ConversionEvent.FileCompleted(
            fileName = "file4.svg",
            duration = 40.milliseconds,
            result = FileResult.Success,
        )

        // Act
        val result = reduceRecentFiles(state = state, event = event)

        // Assert
        assertEquals(expected = 3, actual = result.entries.size)
        assertEquals(expected = "file2.svg", actual = result.entries.first().fileName)
        assertEquals(expected = "file4.svg", actual = result.entries.last().fileName)
    }

    @Test
    fun `given recent files - when non-FileCompleted event - then state unchanged`() {
        // Arrange
        val entries = listOf(
            RecentFileEntry(fileName = "a.svg", result = FileResult.Success, duration = 10.milliseconds),
        )
        val state = RecentFilesState(entries = entries)
        val event = ConversionEvent.FileStarted(fileName = "b.svg", index = 1)

        // Act
        val result = reduceRecentFiles(state = state, event = event)

        // Assert
        assertEquals(expected = state, actual = result)
    }

    @Test
    fun `given recent files - when RunCompleted received - then state unchanged`() {
        // Arrange
        val state = RecentFilesState()
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
        val result = reduceRecentFiles(state = state, event = event)

        // Assert
        assertEquals(expected = state, actual = result)
    }
}
