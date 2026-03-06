package dev.tonholo.s2c.io

import AppConfig.S2C_TEMP_FOLDER
import dev.tonholo.s2c.extensions.encodeToMd5
import dev.tonholo.s2c.inject.TempDirectory
import dev.tonholo.s2c.logger.Logger
import dev.zacsweers.metro.Inject
import okio.Path
import okio.Path.Companion.toPath

@Inject
class TempFileWriter(
    private val logger: Logger,
    private val fileManager: FileManager,
    @TempDirectory baseDirectory: Path? = null,
) {
    private val tempFolder: Path = (baseDirectory ?: S2C_TEMP_FOLDER.toPath())

    fun create(
        file: Path,
    ): Path = logger.debugSection("Creating temporary file") {
        val tempDir = tempFolder / file.encodeToMd5()
        fileManager.createDirectories(dir = tempDir, mustCreate = false)

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
