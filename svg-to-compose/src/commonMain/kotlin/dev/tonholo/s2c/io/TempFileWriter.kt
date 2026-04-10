package dev.tonholo.s2c.io

import com.rsicarelli.fakt.Fake
import dev.tonholo.s2c.extensions.encodeToMd5
import dev.tonholo.s2c.logger.Logger
import okio.Path
import okio.Path.Companion.toPath

@Fake
interface TempFileWriter {
    fun create(file: Path): Path
    fun clear()

    companion object {
        const val S2C_TEMP_FOLDER = ".s2c/temp"
    }
}

class DefaultTempFileWriter(
    private val logger: Logger,
    private val fileManager: FileManager,
    baseDirectory: Path? = null,
) : TempFileWriter {
    private val tempFolder: Path = (baseDirectory ?: TempFileWriter.S2C_TEMP_FOLDER.toPath())

    override fun create(file: Path): Path = logger.debugSection("Creating temporary file") {
        val tempDir = tempFolder / file.encodeToMd5()
        fileManager.createDirectories(dir = tempDir, mustCreate = false)

        val targetFile = tempDir / file.name

        fileManager.copy(file, targetFile)

        return@debugSection targetFile
    }

    override fun clear() {
        logger.debugSection("Deleting temp files") {
            fileManager.deleteRecursively(tempFolder)
        }
    }
}
