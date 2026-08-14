package dev.tonholo.s2c.remote.zip

import okio.FileSystem
import okio.Path

actual fun createZipExtractor(fileSystem: FileSystem): ZipExtractor = UnsupportedZipExtractor

private object UnsupportedZipExtractor : ZipExtractor {
    override suspend fun extract(
        zipPath: Path,
        outputDir: Path,
    ): List<Path> = throw UnsupportedOperationException("ZIP extraction is not supported on the WasmJS target yet.")
}
