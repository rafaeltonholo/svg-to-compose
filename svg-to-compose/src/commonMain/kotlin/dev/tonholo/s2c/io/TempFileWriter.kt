package dev.tonholo.s2c.io

import AppConfig.S2C_TEMP_FOLDER
import dev.tonholo.s2c.extensions.extension
import dev.tonholo.s2c.logger.debugSection
import okio.Path
import okio.Path.Companion.toPath

private const val TARGET_FILENAME = "target"

class TempFileWriter(
    private val fileManager: FileManager,
) {
    private val tempFolder = S2C_TEMP_FOLDER.toPath()

    fun create(
        file: Path,
    ): Path = debugSection("Creating temporary file") {
        val tempDir = tempFolder / file.name.removeSuffix(file.extension)
        fileManager.createDirectories(dir = tempDir, mustCreate = false)

        val extension = file.extension
        val targetFile = tempDir / "${TARGET_FILENAME}$extension"

        fileManager.copy(file, targetFile)

        return@debugSection targetFile
    }

    fun clear() {
        debugSection("Deleting temp files") {
            fileManager.deleteRecursively(tempFolder)
        }
    }
}
