package dev.tonholo.s2c.remote

import dev.tonholo.s2c.remote.zip.createZipExtractor
import kotlinx.coroutines.test.runTest
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.Test
import kotlin.test.assertFailsWith

class UnsupportedPlatformTest {

    @Test
    fun `given wasm target - when zip extractor is used - then throws UnsupportedOperationException`() =
        runTest {
            // Arrange
            val extractor = createZipExtractor(FakeFileSystem())

            // Act / Assert
            assertFailsWith<UnsupportedOperationException> {
                extractor.extract(zipPath = "/downloads/icons.zip".toPath(), outputDir = "/out".toPath())
            }
        }
}
