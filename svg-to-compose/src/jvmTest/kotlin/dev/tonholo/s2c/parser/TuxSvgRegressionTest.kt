package dev.tonholo.s2c.parser

import dev.tonholo.s2c.ConversionStep
import dev.tonholo.s2c.DefaultConverter
import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.emitter.CodeEmitterFactory
import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.logger.NoOpLogger
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import java.io.File
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Regression test for https://github.com/rafaeltonholo/svg-to-compose/issues/315.
 *
 * The Tux SVG combines forward `<use>` references and gradient inheritance via
 * `xlink:href`. Two failure modes were observed:
 * 1. `ClassCastException: XmlPendingParentElement cannot be cast to XmlChildNode`
 *    caused by variable shadowing in `XmlChildNode.rootParent`.
 * 2. `IllegalArgumentException: colors must have length of at least 2 if
 *    colorStops is omitted` from `<radialGradient>` inheriting stops via
 *    `xlink:href` not being resolved before brush construction.
 *
 * This is a JVM-only test because the fixture is loaded from the repo's
 * `samples/svg/tux.svg` file rather than embedded inline.
 */
class TuxSvgRegressionTest {
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
    fun `given Tux SVG with forward use references and gradient inheritance - when convert is called - then it completes successfully`() =
        runTest {
            // Arrange
            val tuxSvg = locateTuxSample()
            val svgContent = tuxSvg.readText()

            // Act
            val steps = converter.convert(
                content = svgContent,
                iconName = "Tux",
                config = testConfig,
                fileType = FileType.Svg,
                optimizer = null,
            ).toList()

            // Assert
            val errorStep = steps.filterIsInstance<ConversionStep.Error>().firstOrNull()
            assertNull(
                errorStep,
                "Expected conversion to succeed but got error: ${errorStep?.error}",
            )
            val complete = steps.filterIsInstance<ConversionStep.Complete>().firstOrNull()
            assertNotNull(complete, "Expected a Complete step")
            assertTrue(
                complete.result.kotlinCode.isNotBlank(),
                "Expected non-blank generated Kotlin code",
            )
        }

    private fun locateTuxSample(): File {
        var dir: File? = File(System.getProperty("user.dir") ?: ".").absoluteFile
        while (dir != null) {
            val candidate = File(dir, "samples/svg/tux.svg")
            if (candidate.isFile) return candidate
            dir = dir.parentFile
        }
        error("samples/svg/tux.svg not found by walking up from ${System.getProperty("user.dir")}")
    }
}
