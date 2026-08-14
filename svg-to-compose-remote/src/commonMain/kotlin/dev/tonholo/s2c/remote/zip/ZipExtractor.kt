package dev.tonholo.s2c.remote.zip

import okio.FileSystem
import okio.Path

/**
 * Extracts ZIP archives into a target directory on [fileSystem].
 */
expect class ZipExtractor(fileSystem: FileSystem) {
    /**
     * Extracts the archive at [zipPath] into [outputDir], preserving the
     * archive's directory structure.
     *
     * @return the paths of every extracted file.
     */
    suspend fun extract(zipPath: Path, outputDir: Path): List<Path>
}
