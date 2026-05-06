package dev.tonholo.s2c.parser

import dev.tonholo.s2c.ConversionStep
import dev.tonholo.s2c.DefaultConverter
import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.emitter.CodeEmitterFactory
import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.logger.NoOpLogger
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Regression test for SVG `<line>` elements being silently dropped because the
 * SVG parser had no handler for them. The line element fell through to the
 * generic `XmlElementNode` branch, which `SvgNode.asNodes` returned null for,
 * so icons that relied on `<line>` (chevrons, plus, magnifying glasses, etc.)
 * rendered with their strokes missing.
 */
class SvgLineElementRegressionTest {
    private val logger: Logger = NoOpLogger
    private val parser = SvgContentParser(logger)
    private val converter = DefaultConverter(
        contentParsers = mapOf(FileType.Svg to parser),
        codeEmitterFactory = CodeEmitterFactory(logger),
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

    @Test
    fun `given chevron-down svg with line and polyline - when convert is called - then output contains line stroke path`() =
        runTest {
            // Arrange
            val svg = """
                <svg viewBox="0 0 256 256" xmlns="http://www.w3.org/2000/svg">
                    <rect fill="none" height="256" width="256" />
                    <line fill="none" stroke="#000000" stroke-linecap="round" stroke-linejoin="round"
                        stroke-width="16" x1="128" x2="128" y1="40" y2="216" />
                    <polyline fill="none" points="56 144 128 216 200 144" stroke="#000000"
                        stroke-linecap="round" stroke-linejoin="round" stroke-width="16" />
                </svg>
            """.trimIndent()

            // Act
            val complete = convert(svg, "ChevronDown")

            // Assert
            // Both the vertical line and the polyline must show up. The vertical line maps to a
            // moveTo(128, 40) followed by lineTo(128, 216).
            val code = complete.result.kotlinCode
            assertTrue(
                code.contains("moveTo(x = 128.0f, y = 40.0f)") ||
                    code.contains("moveTo(128.0f, 40.0f)"),
                "Expected line moveTo(128, 40) to be emitted, but got:\n$code",
            )
            assertTrue(
                code.contains("lineTo(x = 128.0f, y = 216.0f)") ||
                    code.contains("lineTo(128.0f, 216.0f)"),
                "Expected line lineTo(128, 216) to be emitted, but got:\n$code",
            )
        }

    @Test
    fun `given plus svg with two line elements - when convert is called - then output emits both lines`() = runTest {
        // Arrange
        val svg = """
            <svg viewBox="0 0 256 256" xmlns="http://www.w3.org/2000/svg">
                <rect fill="none" height="256" width="256" />
                <line fill="none" stroke="#000000" stroke-linecap="round" stroke-linejoin="round"
                    stroke-width="16" x1="40" x2="216" y1="128" y2="128" />
                <line fill="none" stroke="#000000" stroke-linecap="round" stroke-linejoin="round"
                    stroke-width="16" x1="128" x2="128" y1="40" y2="216" />
            </svg>
        """.trimIndent()

        // Act
        val complete = convert(svg, "Plus")

        // Assert
        val code = complete.result.kotlinCode
        assertTrue(
            code.contains("moveTo(x = 40.0f, y = 128.0f)") ||
                code.contains("moveTo(40.0f, 128.0f)"),
            "Expected horizontal line moveTo(40, 128) to be emitted, but got:\n$code",
        )
        assertTrue(
            code.contains("moveTo(x = 128.0f, y = 40.0f)") ||
                code.contains("moveTo(128.0f, 40.0f)"),
            "Expected vertical line moveTo(128, 40) to be emitted, but got:\n$code",
        )
    }

    private suspend fun convert(svg: String, name: String): ConversionStep.Complete {
        val steps = converter.convert(
            content = svg,
            iconName = name,
            config = testConfig,
            fileType = FileType.Svg,
            optimizer = null,
        ).toList()

        val errorStep = steps.filterIsInstance<ConversionStep.Error>().firstOrNull()
        assertNull(
            errorStep,
            "Expected conversion to succeed but got error: ${errorStep?.error}",
        )
        val complete = steps.filterIsInstance<ConversionStep.Complete>().firstOrNull()
        assertNotNull(complete, "Expected a Complete step")
        return complete
    }
}
