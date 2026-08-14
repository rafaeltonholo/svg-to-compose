package dev.tonholo.s2c.remote.zip

import okio.FileSystem
import okio.Path

actual class ZipExtractor actual constructor(fileSystem: FileSystem) {
    actual suspend fun extract(zipPath: Path, outputDir: Path): List<Path> {
        throw UnsupportedOperationException("ZIP extraction is not supported on the WasmJS target yet.")
    }
}
