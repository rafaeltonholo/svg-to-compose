package dev.tonholo.s2c.io

import AppConfig.S2C_TEMP_FOLDER
import dev.tonholo.s2c.extensions.extension
import dev.tonholo.s2c.logger.debugSection
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

private const val TARGET_FILENAME = "target"

class TempFileWriter(
    private val fileSystem: FileSystem,
) {
    private val tempFolder = S2C_TEMP_FOLDER.toPath()

    fun create(
        file: Path,
        optimize: Boolean,
    ): Path {
        val targetFile = debugSection("Creating temporary file") {
            fileSystem.createDirectories(dir = tempFolder, mustCreate = false)

            val extension = file.extension
            val targetFile = tempFolder / "${TARGET_FILENAME}$extension"

            fileSystem.copy(file, targetFile)

            return@debugSection targetFile
        }

        // When optimize is enabled, we always end up with an XML file.
        return if (optimize) tempFolder / "${TARGET_FILENAME}.xml" else targetFile
    }

    fun clear() {
        debugSection("Deleting temp files") {
            fileSystem.deleteRecursively(tempFolder)
        }
    }
}
