package dev.tonholo.s2c.remote.zip

import okio.FileSystem
import okio.Path

/**
 * Extracts ZIP archives into a target directory.
 */
interface ZipExtractor {
    /**
     * Extracts the archive at [zipPath] into [outputDir], preserving the
     * archive's directory structure.
     *
     * Entries whose names would place them outside [outputDir] are never
     * written; whether such entries are skipped or re-rooted under
     * [outputDir] may differ per platform.
     *
     * @return the paths of every extracted file.
     */
    suspend fun extract(zipPath: Path, outputDir: Path): List<Path>
}

/**
 * Creates the platform [ZipExtractor] operating on [fileSystem].
 */
expect fun createZipExtractor(fileSystem: FileSystem): ZipExtractor
