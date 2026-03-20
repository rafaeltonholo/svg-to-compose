package dev.tonholo.s2c.parser

import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.logger.NoOpLogger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SvgContentParserTest {
    private val logger: Logger = NoOpLogger
    private val parser = SvgContentParser(logger)

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
    fun `ensure parse returns correct dimensions from viewBox`() {
        // Arrange
        val svgContent = """<svg viewBox="0 0 48 48"><path d="M0 0"/></svg>"""

        // Act
        val result = parser.parse(svgContent, iconName = "TestIcon", config = testConfig)

        // Assert
        assertEquals(expected = 48f, actual = result.width)
        assertEquals(expected = 48f, actual = result.height)
        assertEquals(expected = 48f, actual = result.viewportWidth)
        assertEquals(expected = 48f, actual = result.viewportHeight)
    }

    @Test
    fun `ensure parse sets iconName from parameter`() {
        // Arrange
        val svgContent = """<svg viewBox="0 0 24 24"><path d="M0 0"/></svg>"""

        // Act
        val result = parser.parse(svgContent, iconName = "TestIcon", config = testConfig)

        // Assert
        assertEquals(expected = "TestIcon", actual = result.iconName)
    }

    @Test
    fun `ensure parse sets package from config`() {
        // Arrange
        val svgContent = """<svg viewBox="0 0 24 24"><path d="M0 0"/></svg>"""

        // Act
        val result = parser.parse(svgContent, iconName = "TestIcon", config = testConfig)

        // Assert
        assertEquals(expected = testConfig.pkg, actual = result.pkg)
    }

    @Test
    fun `ensure parse produces non-empty node list for SVG with path`() {
        // Arrange
        val svgContent = """<svg viewBox="0 0 24 24"><path d="M10 10L20 20"/></svg>"""

        // Act
        val result = parser.parse(svgContent, iconName = "TestIcon", config = testConfig)

        // Assert
        assertTrue(result.nodes.isNotEmpty(), "Expected non-empty node list for SVG with path")
    }
}
