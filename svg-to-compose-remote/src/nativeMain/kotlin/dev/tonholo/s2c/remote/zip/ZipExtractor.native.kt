package dev.tonholo.s2c.remote.zip

import okio.FileSystem
import okio.Path
import okio.openZip

actual class ZipExtractor actual constructor(
    private val fileSystem: FileSystem,
) {
    actual suspend fun extract(zipPath: Path, outputDir: Path): List<Path> =
        fileSystem.openZip(zipPath).extractAllTo(target = fileSystem, outputDir = outputDir)
}
