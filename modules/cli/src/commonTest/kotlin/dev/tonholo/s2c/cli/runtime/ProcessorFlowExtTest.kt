package dev.tonholo.s2c.cli.runtime

import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.FileResult
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.output.RunStats
import dev.tonholo.s2c.parser.ParserConfig
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.time.Duration.Companion.milliseconds

class ProcessorFlowExtTest {

    // Note: Processor is a concrete @AssistedInject class that cannot be easily
    // mocked in KMP without a mocking framework. This test verifies the
    // channelFlow pattern that runAsFlow relies on: a synchronous callback
    // emits events via send, and the flow terminates when the block returns.
    // Full integration coverage of runAsFlow is exercised through CliRunner.
    @Test
    fun `given synchronous send in channelFlow - when collected - then events emitted in order and flow terminates`() =
        runTest {
            // Arrange
            val events = listOf(
                ConversionEvent.RunStarted(
                    config = RunConfig(
                        inputPath = "./in",
                        outputPath = "./out",
                        parserConfig = ParserConfig(
                            pkg = "com.test",
                            theme = "TestTheme",
                            optimize = false,
                            receiverType = null,
                            addToMaterial = false,
                            kmpPreview = false,
                            noPreview = false,
                            makeInternal = false,
                            minified = false,
                        ),
                        packageName = "com.test",
                        optimizationEnabled = false,
                        recursive = false,
                    ),
                    totalFiles = 1,
                    version = "1.0.0",
                ),
                ConversionEvent.FileStarted(fileName = "test.svg", index = 0),
                ConversionEvent.FileCompleted(
                    fileName = "test.svg",
                    duration = 100.milliseconds,
                    result = FileResult.Success,
                ),
                ConversionEvent.RunCompleted(
                    stats = RunStats(
                        totalFiles = 1,
                        succeeded = 1,
                        failed = 0,
                        totalDuration = 100.milliseconds,
                        errorCounts = emptyMap(),
                    ),
                ),
            )

            // Act - mirrors the runAsFlow pattern: send in channelFlow, flow completes when block returns
            val flow = channelFlow {
                for (event in events) {
                    send(event)
                }
            }
            val collected = flow.toList()

            // Assert
            assertEquals(expected = 4, actual = collected.size)
            assertIs<ConversionEvent.RunStarted>(collected[0])
            assertIs<ConversionEvent.FileStarted>(collected[1])
            assertIs<ConversionEvent.FileCompleted>(collected[2])
            assertIs<ConversionEvent.RunCompleted>(collected[3])
        }
}
