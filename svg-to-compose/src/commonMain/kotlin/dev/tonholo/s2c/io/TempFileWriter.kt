package dev.tonholo.s2c.io

import AppConfig.S2C_TEMP_FOLDER
import dev.tonholo.s2c.extensions.encodeToMd5
import dev.tonholo.s2c.logger.Logger
import okio.Path
import okio.Path.Companion.toPath

class TempFileWriter(
    private val logger: Logger,
    private val fileManager: FileManager,
) {
    private val tempFolder = S2C_TEMP_FOLDER.toPath()

    fun create(
        file: Path,
    ): Path = logger.debugSection("Creating temporary file") {
        val tempDir = tempFolder / file.encodeToMd5()
        fileManager.createDirectories(dir = tempDir, mustCreate = true)

        val targetFile = tempDir / file.name

        fileManager.copy(file, targetFile)

        return@debugSection targetFile
    }

    fun clear() {
        logger.debugSection("Deleting temp files") {
            fileManager.deleteRecursively(tempFolder)
        }
    }
}
