package dev.tonholo.s2c.io

import okio.Path.Companion.toPath
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class VectorFileEligibilityTest {
    private val root = "/icons".toPath()

    @Test
    fun `given svg file without filters - when isEligibleForProcessing - then returns true`() {
        // Arrange
        val path = "/icons/icon.svg".toPath()

        // Act
        val eligible = path.isEligibleForProcessing(root = root, exclude = null, excludeDir = null)

        // Assert
        assertTrue(eligible)
    }

    @Test
    fun `given xml file without filters - when isEligibleForProcessing - then returns true`() {
        // Arrange
        val path = "/icons/icon.xml".toPath()

        // Act
        val eligible = path.isEligibleForProcessing(root = root, exclude = null, excludeDir = null)

        // Assert
        assertTrue(eligible)
    }

    @Test
    fun `given uppercase extension - when isEligibleForProcessing - then returns false`() {
        // Arrange
        val path = "/icons/ICON.SVG".toPath()

        // Act
        val eligible = path.isEligibleForProcessing(root = root, exclude = null, excludeDir = null)

        // Assert
        assertFalse(eligible)
    }

    @Test
    fun `given unsupported extension - when isEligibleForProcessing - then returns false`() {
        // Arrange
        val path = "/icons/icon.png".toPath()

        // Act
        val eligible = path.isEligibleForProcessing(root = root, exclude = null, excludeDir = null)

        // Assert
        assertFalse(eligible)
    }

    @Test
    fun `given file name matching exclude - when isEligibleForProcessing - then returns false`() {
        // Arrange
        val path = "/icons/icon_fill.svg".toPath()
        val exclude = ".*_fill\\.svg".toRegex()

        // Act
        val eligible = path.isEligibleForProcessing(root = root, exclude = exclude, excludeDir = null)

        // Assert
        assertFalse(eligible)
    }

    @Test
    fun `given file name not matching exclude - when isEligibleForProcessing - then returns true`() {
        // Arrange
        val path = "/icons/icon.svg".toPath()
        val exclude = ".*_fill\\.svg".toRegex()

        // Act
        val eligible = path.isEligibleForProcessing(root = root, exclude = exclude, excludeDir = null)

        // Assert
        assertTrue(eligible)
    }

    @Test
    fun `given file inside excluded dir - when isEligibleForProcessing - then returns false`() {
        // Arrange
        val path = "/icons/drafts/icon.svg".toPath()
        val excludeDir = "drafts".toRegex()

        // Act
        val eligible = path.isEligibleForProcessing(root = root, exclude = null, excludeDir = excludeDir)

        // Assert
        assertFalse(eligible)
    }

    @Test
    fun `given file inside nested excluded dir - when isEligibleForProcessing - then returns false`() {
        // Arrange
        val path = "/icons/outlined/drafts/deep/icon.svg".toPath()
        val excludeDir = "drafts".toRegex()

        // Act
        val eligible = path.isEligibleForProcessing(root = root, exclude = null, excludeDir = excludeDir)

        // Assert
        assertFalse(eligible)
    }

    @Test
    fun `given file in non-excluded nested dir - when isEligibleForProcessing - then returns true`() {
        // Arrange
        val path = "/icons/outlined/icon.svg".toPath()
        val excludeDir = "drafts".toRegex()

        // Act
        val eligible = path.isEligibleForProcessing(root = root, exclude = null, excludeDir = excludeDir)

        // Assert
        assertTrue(eligible)
    }

    @Test
    fun `given root name matching excludeDir - when isEligibleForProcessing - then returns true`() {
        // Arrange
        val path = "/icons/icon.svg".toPath()
        val excludeDir = "icons".toRegex()

        // Act
        val eligible = path.isEligibleForProcessing(root = root, exclude = null, excludeDir = excludeDir)

        // Assert
        assertTrue(eligible)
    }

    @Test
    fun `given svg and xml names - when hasVectorFileExtension - then only case-sensitive matches return true`() {
        // Arrange
        val svg = "/icons/icon.svg".toPath()
        val xml = "/icons/icon.xml".toPath()
        val upperSvg = "/icons/ICON.SVG".toPath()
        val png = "/icons/icon.png".toPath()

        // Act & Assert
        assertTrue(svg.hasVectorFileExtension())
        assertTrue(xml.hasVectorFileExtension())
        assertFalse(upperSvg.hasVectorFileExtension())
        assertFalse(png.hasVectorFileExtension())
    }
}
