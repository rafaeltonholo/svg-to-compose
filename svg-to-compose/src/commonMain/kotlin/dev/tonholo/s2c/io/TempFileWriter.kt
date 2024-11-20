package dev.tonholo.s2c.io

import AppConfig.S2C_TEMP_FOLDER
import dev.tonholo.s2c.extensions.encodeToMd5
import dev.tonholo.s2c.logger.debugSection
import okio.Path
import okio.Path.Companion.toPath

class TempFileWriter(
    private val fileManager: FileManager,
) {
    private val tempFolder = S2C_TEMP_FOLDER.toPath()

    fun create(
        file: Path,
    ): Path = debugSection("Creating temporary file") {
        val tempDir = tempFolder / file.encodeToMd5()
        fileManager.createDirectories(dir = tempDir, mustCreate = true)

        val targetFile = tempDir / file.name

        fileManager.copy(file, targetFile)

        return@debugSection targetFile
    }

    fun clear() {
        debugSection("Deleting temp files") {
            fileManager.deleteRecursively(tempFolder)
        }
    }
}
