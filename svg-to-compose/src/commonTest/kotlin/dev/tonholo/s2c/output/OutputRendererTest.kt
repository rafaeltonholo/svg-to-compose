package dev.tonholo.s2c.output

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds

class OutputRendererTest {

    @Test
    fun `given an OutputRenderer lambda - when onEvent is called - then the lambda receives the event`() {
        // Arrange
        val receivedEvents = mutableListOf<ConversionEvent>()
        val renderer = OutputRenderer { event -> receivedEvents.add(event) }
        val config = RunConfig(
            inputPath = "/in",
            outputPath = "/out",
            packageName = "pkg",
            optimizationEnabled = false,
            recursive = false,
        )
        val event = ConversionEvent.RunStarted(
            config = config,
            totalFiles = 1,
            version = "1.0.0",
        )
        // Act
        renderer.onEvent(event)
        // Assert
        assertEquals(expected = 1, actual = receivedEvents.size)
        assertEquals(expected = event, actual = receivedEvents.first())
    }

    @Test
    fun `given an OutputRenderer lambda - when multiple events are sent - then all events are received`() {
        // Arrange
        val receivedEvents = mutableListOf<ConversionEvent>()
        val renderer = OutputRenderer { event -> receivedEvents.add(event) }
        val config = RunConfig(
            inputPath = "/in",
            outputPath = "/out",
            packageName = "pkg",
            optimizationEnabled = false,
            recursive = false,
        )
        val events = listOf(
            ConversionEvent.RunStarted(config = config, totalFiles = 2, version = "1.0.0"),
            ConversionEvent.FileStarted(fileName = "a.svg", index = 0),
            ConversionEvent.FileStepChanged(fileName = "a.svg", step = ConversionPhase.Parsing),
            ConversionEvent.FileCompleted(
                fileName = "a.svg",
                duration = 1.seconds,
                result = FileResult.Success,
            ),
            ConversionEvent.RunCompleted(
                stats = RunStats(
                    totalFiles = 2,
                    succeeded = 2,
                    failed = 0,
                    totalDuration = 2.seconds,
                    errorCounts = emptyMap(),
                ),
            ),
        )
        // Act
        events.forEach(renderer::onEvent)
        // Assert
        assertEquals(expected = events.size, actual = receivedEvents.size)
        assertEquals(expected = events, actual = receivedEvents)
    }

    @Test
    fun `given an OutputRenderer class implementation - when onEvent is called - then it works`() {
        // Arrange
        var lastEvent: ConversionEvent? = null
        val renderer = object : OutputRenderer {
            override fun onEvent(event: ConversionEvent) {
                lastEvent = event
            }
        }
        val event = ConversionEvent.FileStarted(fileName = "test.svg", index = 0)
        // Act
        renderer.onEvent(event)
        // Assert
        assertEquals(expected = event, actual = lastEvent)
    }
}
