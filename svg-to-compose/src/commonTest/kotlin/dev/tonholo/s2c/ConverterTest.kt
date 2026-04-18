package dev.tonholo.s2c

import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.emitter.DefaultCodeEmitterFactory
import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.logger.NoOpLogger
import dev.tonholo.s2c.optimizer.ContentOptimizer
import dev.tonholo.s2c.parser.AvgContentParser
import dev.tonholo.s2c.parser.ContentParser
import dev.tonholo.s2c.parser.ParserConfig
import dev.tonholo.s2c.parser.SvgContentParser
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class ConverterTest {
    private val logger: Logger = NoOpLogger

    private val contentParsers: Map<FileType, ContentParser> = mapOf(
        FileType.Svg to SvgContentParser(logger),
        FileType.Avg to AvgContentParser(logger),
    )

    private val testConfig = ParserConfig(
        pkg = "dev.test",
        theme = "dev.test.Theme",
        optimize = false,
        receiverType = null,
        addToMaterial = false,
        kmpPreview = false,
        noPreview = true,
        makeInternal = false,
        minified = false,
    )

    private val converter = DefaultConverter(
        contentParsers = contentParsers,
        codeEmitterFactory = DefaultCodeEmitterFactory(logger),
    )

    @Test
    fun `ensure convert emits Parsing then Generating then Complete for SVG without optimizer`() =
        runTest {
            // Arrange
            val svgContent = """<svg viewBox="0 0 24 24"><path d="M10 10L20 20"/></svg>"""

            // Act
            val steps = converter.convert(
                content = svgContent,
                iconName = "TestIcon",
                config = testConfig,
                fileType = FileType.Svg,
                optimizer = null,
            ).toList()

            // Assert
            assertEquals(expected = 3, actual = steps.size)
            assertIs<ConversionStep.Parsing>(steps[0])
            assertIs<ConversionStep.Generating>(steps[1])
            val complete = assertIs<ConversionStep.Complete>(steps[2])
            assertTrue(
                complete.result.kotlinCode.isNotEmpty(),
                "Expected generated Kotlin code to be non-empty",
            )
        }

    @Test
    fun `ensure convert emits Optimizing step when optimizer is provided`() = runTest {
        // Arrange
        val svgContent = """<svg viewBox="0 0 24 24"><path d="M10 10L20 20"/></svg>"""
        val passthroughOptimizer = ContentOptimizer { content, _ -> content }

        // Act
        val steps = converter.convert(
            content = svgContent,
            iconName = "TestIcon",
            config = testConfig,
            fileType = FileType.Svg,
            optimizer = passthroughOptimizer,
        ).toList()

        // Assert
        assertEquals(expected = 4, actual = steps.size)
        assertIs<ConversionStep.Optimizing>(steps[0])
        assertIs<ConversionStep.Parsing>(steps[1])
        assertIs<ConversionStep.Generating>(steps[2])
        assertIs<ConversionStep.Complete>(steps[3])
    }

    @Test
    fun `ensure convert emits Error step for invalid content`() = runTest {
        // Arrange
        val invalidContent = "not xml"

        // Act
        val steps = converter.convert(
            content = invalidContent,
            iconName = "TestIcon",
            config = testConfig,
            fileType = FileType.Svg,
            optimizer = null,
        ).toList()

        // Assert
        assertTrue(steps.isNotEmpty(), "Expected at least one step")
        assertIs<ConversionStep.Error>(steps.last())
    }

    @Test
    fun `ensure convert emits Error for unsupported file type`() = runTest {
        // Arrange
        val svgContent = """<svg viewBox="0 0 24 24"><path d="M10 10L20 20"/></svg>"""
        val emptyParsersConverter = DefaultConverter(
            contentParsers = emptyMap(),
            codeEmitterFactory = DefaultCodeEmitterFactory(logger),
        )

        // Act
        val steps = emptyParsersConverter.convert(
            content = svgContent,
            iconName = "TestIcon",
            config = testConfig,
            fileType = FileType.Svg,
            optimizer = null,
        ).toList()

        // Assert
        assertTrue(steps.isNotEmpty(), "Expected at least one step")
        val errorStep = assertIs<ConversionStep.Error>(steps.last())
        assertTrue(
            errorStep.error.message.orEmpty().contains("No content parser registered"),
            "Expected error message about no parser registered, got: ${errorStep.error.message}",
        )
    }

    @Test
    fun `ensure convert works with AVG file type`() = runTest {
        // Arrange
        val avgContent = """
            <vector xmlns:android="http://schemas.android.com/apk/res/android"
                android:width="24dp"
                android:height="24dp"
                android:viewportWidth="24"
                android:viewportHeight="24">
                <path android:pathData="M10,10L20,20" android:fillColor="#000000"/>
            </vector>
        """.trimIndent()

        // Act
        val steps = converter.convert(
            content = avgContent,
            iconName = "TestIcon",
            config = testConfig,
            fileType = FileType.Avg,
            optimizer = null,
        ).toList()

        // Assert
        val complete = steps.filterIsInstance<ConversionStep.Complete>().firstOrNull()
        assertTrue(complete != null, "Expected a Complete step")
        assertTrue(
            complete.result.kotlinCode.isNotEmpty(),
            "Expected generated Kotlin code to be non-empty",
        )
    }
}
