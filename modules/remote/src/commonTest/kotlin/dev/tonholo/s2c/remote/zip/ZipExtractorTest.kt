package dev.tonholo.s2c.remote.zip

import kotlinx.coroutines.test.runTest
import okio.FileNotFoundException
import okio.Path
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ZipExtractorTest {

    private val fileSystem = FakeFileSystem()
    private val outputDir = "/out".toPath()

    @Test
    fun `given zip with nested entries - when extract is called - then files are written preserving structure`() =
        runTest {
            if (!zipExtractionSupported) return@runTest
            // Arrange
            val zipPath = writeZip(
                "icons/home.svg" to "<svg>home</svg>",
                "star.xml" to "<vector>star</vector>",
            )
            val extractor = createZipExtractor(fileSystem)

            // Act
            val extracted = extractor.extract(zipPath = zipPath, outputDir = outputDir)

            // Assert
            val home = "/out/icons/home.svg".toPath()
            val star = "/out/star.xml".toPath()
            assertEquals(setOf(home, star), extracted.toSet())
            assertEquals("<svg>home</svg>", fileSystem.read(home) { readUtf8() })
            assertEquals("<vector>star</vector>", fileSystem.read(star) { readUtf8() })
        }

    @Test
    fun `given zip with hostile entry names - when extract is called - then nothing is written outside output dir`() =
        runTest {
            if (!zipExtractionSupported) return@runTest
            // Arrange
            val zipPath = writeZip(
                "/absolute.svg" to "<svg>absolute</svg>",
                "../escape.svg" to "<svg>escape</svg>",
                "nested/../inner.svg" to "<svg>inner</svg>",
                "safe/good.svg" to "<svg>good</svg>",
            )
            val extractor = createZipExtractor(fileSystem)

            // Act
            val extracted = extractor.extract(zipPath = zipPath, outputDir = outputDir)

            // Assert
            val strayFiles = fileSystem.listRecursively("/".toPath())
                .filter { path -> fileSystem.metadata(path).isRegularFile }
                .filterNot { path -> path.toString().startsWith("/out/") }
                .filterNot { path -> path.toString().startsWith("/downloads/") }
                .toList()
            assertEquals(emptyList(), strayFiles)
            assertTrue(extracted.all { path -> path.toString().startsWith("/out/") })
            assertEquals("<svg>good</svg>", fileSystem.read("/out/safe/good.svg".toPath()) { readUtf8() })
        }

    @Test
    fun `given empty archive - when extract is called - then returns no paths`() = runTest {
        if (!zipExtractionSupported) return@runTest
        // Arrange
        val zipPath = writeZip()
        val extractor = createZipExtractor(fileSystem)

        // Act
        val extracted = extractor.extract(zipPath = zipPath, outputDir = outputDir)

        // Assert
        assertEquals(emptyList(), extracted)
    }

    @Test
    fun `given missing archive file - when extract is called - then throws FileNotFoundException`() =
        runTest {
            if (!zipExtractionSupported) return@runTest
            // Arrange
            val extractor = createZipExtractor(fileSystem)

            // Act / Assert
            assertFailsWith<FileNotFoundException> {
                extractor.extract(zipPath = "/downloads/missing.zip".toPath(), outputDir = outputDir)
            }
        }

    private fun writeZip(vararg entries: Pair<String, String>): Path {
        val zipPath = "/downloads/icons.zip".toPath()
        fileSystem.createDirectories("/downloads".toPath())
        fileSystem.write(zipPath) { write(storedZip(entries.toMap())) }
        return zipPath
    }
}
