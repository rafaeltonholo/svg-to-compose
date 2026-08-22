package dev.tonholo.s2c.remote.zip

import okio.FileSystem
import okio.Path
import okio.openZip

actual fun createZipExtractor(fileSystem: FileSystem): ZipExtractor = OkioZipExtractor(fileSystem)

private class OkioZipExtractor(private val fileSystem: FileSystem) : ZipExtractor {
    override suspend fun extract(zipPath: Path, outputDir: Path): List<Path> {
        val archive = fileSystem.openZip(zipPath)
        return archive.extractAllTo(target = fileSystem, outputDir = outputDir)
    }
}
