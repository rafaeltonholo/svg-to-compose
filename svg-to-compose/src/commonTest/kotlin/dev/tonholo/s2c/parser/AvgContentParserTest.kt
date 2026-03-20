package dev.tonholo.s2c.parser

import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.logger.NoOpLogger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AvgContentParserTest {
    private val logger: Logger = NoOpLogger
    private val parser = AvgContentParser(logger)

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

    private val avgContent = """
        <vector xmlns:android="http://schemas.android.com/apk/res/android"
            android:width="24dp"
            android:height="24dp"
            android:viewportWidth="24"
            android:viewportHeight="24">
            <path android:pathData="M10,10L20,20" android:fillColor="#000000"/>
        </vector>
    """.trimIndent()

    @Test
    fun `ensure parse returns correct dimensions from AVG`() {
        // Arrange / Act
        val result = parser.parse(avgContent, iconName = "TestIcon", config = testConfig)

        // Assert
        assertEquals(expected = 24f, actual = result.width)
        assertEquals(expected = 24f, actual = result.height)
        assertEquals(expected = 24f, actual = result.viewportWidth)
        assertEquals(expected = 24f, actual = result.viewportHeight)
    }

    @Test
    fun `ensure parse produces non-empty node list for AVG with path`() {
        // Arrange / Act
        val result = parser.parse(avgContent, iconName = "TestIcon", config = testConfig)

        // Assert
        assertTrue(result.nodes.isNotEmpty(), "Expected non-empty node list for AVG with path")
    }
}
