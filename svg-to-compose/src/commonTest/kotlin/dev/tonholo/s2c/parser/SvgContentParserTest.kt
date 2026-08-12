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

    @Test
    fun `given use element with forward reference to element defined later - when parse is called - then no exception is thrown`() {
        // Arrange
        // Regression test for https://github.com/rafaeltonholo/svg-to-compose/issues/315
        // <use> referencing an element that is defined LATER in the document order
        // forces SvgParser.findReplacementNode to create a placeholder element with
        // parent=XmlPendingParentElement. Variable shadowing in XmlChildNode.rootParent
        // captured the constructor parameter at construction time, so the lazy traversal
        // still saw the pending sentinel after attachParentIfNeeded() updated the property.
        val svgContent = """
            <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 24 24">
              <g>
                <use href="#shape" xlink:href="#shape"/>
              </g>
              <defs>
                <linearGradient id="grad1">
                  <stop offset="0" stop-color="#ff0000"/>
                  <stop offset="1" stop-color="#0000ff"/>
                </linearGradient>
                <path id="shape" d="M0 0L10 10" fill="url(#grad1)" stroke-width="1"/>
              </defs>
            </svg>
        """.trimIndent()

        // Act
        val result = parser.parse(svgContent, iconName = "TestIcon", config = testConfig)

        // Assert
        assertTrue(
            result.nodes.isNotEmpty(),
            "Expected non-empty node list when <use> forward-references a later element",
        )
    }
}
