package dev.tonholo.s2c.remote.zip

import kotlinx.coroutines.test.runTest
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.Test
import kotlin.test.assertEquals

class ZipExtractorTest {

    @Test
    fun `given zip with nested entries - when extract is called - then files are written preserving structure`() =
        runTest {
            // Arrange
            val fileSystem = FakeFileSystem()
            val zipPath = "/downloads/icons.zip".toPath()
            fileSystem.createDirectories("/downloads".toPath())
            fileSystem.write(zipPath) {
                write(
                    storedZip(
                        entries = mapOf(
                            "icons/home.svg" to "<svg>home</svg>",
                            "star.xml" to "<vector>star</vector>",
                        ),
                    ),
                )
            }
            val outputDir = "/out".toPath()
            val extractor = ZipExtractor(fileSystem)

            // Act
            val extracted = extractor.extract(zipPath = zipPath, outputDir = outputDir)

            // Assert
            val home = "/out/icons/home.svg".toPath()
            val star = "/out/star.xml".toPath()
            assertEquals(setOf(home, star), extracted.toSet())
            assertEquals("<svg>home</svg>", fileSystem.read(home) { readUtf8() })
            assertEquals("<vector>star</vector>", fileSystem.read(star) { readUtf8() })
        }
}
