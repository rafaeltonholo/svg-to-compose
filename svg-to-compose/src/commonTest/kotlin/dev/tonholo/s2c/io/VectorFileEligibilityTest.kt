package dev.tonholo.s2c.io

import okio.Path
import okio.Path.Companion.toPath
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class VectorFileEligibilityTest {
    private val iconsRoot = "/icons".toPath()

    private fun Path.isEligibleUnlimited(
        exclude: Regex? = null,
        excludeDir: Regex? = null,
    ): Boolean = isEligibleForProcessing(
        root = iconsRoot,
        recursive = true,
        maxDepth = null,
        exclude = exclude,
        excludeDir = excludeDir,
    )

    @Test
    fun `given svg file without filters - when isEligibleForProcessing - then returns true`() {
        // Arrange
        val path = "/icons/icon.svg".toPath()

        // Act
        val eligible = path.isEligibleUnlimited()

        // Assert
        assertTrue(eligible)
    }

    @Test
    fun `given xml file without filters - when isEligibleForProcessing - then returns true`() {
        // Arrange
        val path = "/icons/icon.xml".toPath()

        // Act
        val eligible = path.isEligibleUnlimited()

        // Assert
        assertTrue(eligible)
    }

    @Test
    fun `given uppercase extension - when isEligibleForProcessing - then returns false`() {
        // Arrange
        val path = "/icons/ICON.SVG".toPath()

        // Act
        val eligible = path.isEligibleUnlimited()

        // Assert
        assertFalse(eligible)
    }

    @Test
    fun `given unsupported extension - when isEligibleForProcessing - then returns false`() {
        // Arrange
        val path = "/icons/icon.png".toPath()

        // Act
        val eligible = path.isEligibleUnlimited()

        // Assert
        assertFalse(eligible)
    }

    @Test
    fun `given file name matching exclude - when isEligibleForProcessing - then returns false`() {
        // Arrange
        val path = "/icons/icon_fill.svg".toPath()
        val exclude = ".*_fill\\.svg".toRegex()

        // Act
        val eligible = path.isEligibleUnlimited(exclude = exclude)

        // Assert
        assertFalse(eligible)
    }

    @Test
    fun `given file name not matching exclude - when isEligibleForProcessing - then returns true`() {
        // Arrange
        val path = "/icons/icon.svg".toPath()
        val exclude = ".*_fill\\.svg".toRegex()

        // Act
        val eligible = path.isEligibleUnlimited(exclude = exclude)

        // Assert
        assertTrue(eligible)
    }

    @Test
    fun `given file inside excluded dir - when isEligibleForProcessing - then returns false`() {
        // Arrange
        val path = "/icons/drafts/icon.svg".toPath()
        val excludeDir = "drafts".toRegex()

        // Act
        val eligible = path.isEligibleUnlimited(excludeDir = excludeDir)

        // Assert
        assertFalse(eligible)
    }

    @Test
    fun `given file inside nested excluded dir - when isEligibleForProcessing - then returns false`() {
        // Arrange
        val path = "/icons/outlined/drafts/deep/icon.svg".toPath()
        val excludeDir = "drafts".toRegex()

        // Act
        val eligible = path.isEligibleUnlimited(excludeDir = excludeDir)

        // Assert
        assertFalse(eligible)
    }

    @Test
    fun `given file in non-excluded nested dir - when isEligibleForProcessing - then returns true`() {
        // Arrange
        val path = "/icons/outlined/icon.svg".toPath()
        val excludeDir = "drafts".toRegex()

        // Act
        val eligible = path.isEligibleUnlimited(excludeDir = excludeDir)

        // Assert
        assertTrue(eligible)
    }

    @Test
    fun `given root name matching excludeDir - when isEligibleForProcessing - then returns true`() {
        // Arrange
        val path = "/icons/icon.svg".toPath()
        val excludeDir = "icons".toRegex()

        // Act
        val eligible = path.isEligibleUnlimited(excludeDir = excludeDir)

        // Assert
        assertTrue(eligible)
    }

    @Test
    fun `given recursive disabled - when file is directly in root - then returns true`() {
        // Arrange
        val path = "/icons/icon.svg".toPath()

        // Act
        val eligible = path.isEligibleForProcessing(
            root = iconsRoot,
            recursive = false,
            maxDepth = null,
            exclude = null,
            excludeDir = null,
        )

        // Assert
        assertTrue(eligible)
    }

    @Test
    fun `given recursive disabled - when file is in nested dir - then returns false`() {
        // Arrange
        val path = "/icons/nested/icon.svg".toPath()

        // Act
        val eligible = path.isEligibleForProcessing(
            root = iconsRoot,
            recursive = false,
            maxDepth = null,
            exclude = null,
            excludeDir = null,
        )

        // Assert
        assertFalse(eligible)
    }

    @Test
    fun `given recursive disabled with maxDepth set - when file is in nested dir - then returns false`() {
        // Arrange
        val path = "/icons/nested/icon.svg".toPath()

        // Act
        val eligible = path.isEligibleForProcessing(
            root = iconsRoot,
            recursive = false,
            maxDepth = 5,
            exclude = null,
            excludeDir = null,
        )

        // Assert
        assertFalse(eligible)
    }

    @Test
    fun `given maxDepth 1 - when file is at depth equal to limit - then returns true`() {
        // Arrange
        val path = "/icons/nested/icon.svg".toPath()

        // Act
        val eligible = path.isEligibleForProcessing(
            root = iconsRoot,
            recursive = true,
            maxDepth = 1,
            exclude = null,
            excludeDir = null,
        )

        // Assert
        assertTrue(eligible)
    }

    @Test
    fun `given maxDepth 1 - when file is beyond limit - then returns false`() {
        // Arrange
        val path = "/icons/nested/deep/icon.svg".toPath()

        // Act
        val eligible = path.isEligibleForProcessing(
            root = iconsRoot,
            recursive = true,
            maxDepth = 1,
            exclude = null,
            excludeDir = null,
        )

        // Assert
        assertFalse(eligible)
    }

    @Test
    fun `given recursive with null maxDepth - when file is deeply nested - then returns true`() {
        // Arrange
        val path = "/icons/a/b/c/d/icon.svg".toPath()

        // Act
        val eligible = path.isEligibleUnlimited()

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
