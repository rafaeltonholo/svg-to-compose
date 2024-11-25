package dev.tonholo.s2c.io

import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.extensions.listRecursively
import dev.tonholo.s2c.logger.Logger
import okio.BufferedSink
import okio.FileSystem
import okio.IOException
import okio.Path

interface FileManager : FileFinder, FileReader, FileCreator, FileDeleter {
    /**
     * @return `true` if the path refers to a directory that contains 0 or more child paths.
     */
    @Throws(IOException::class)
    fun isDirectory(path: Path): Boolean

    /**
     * Creates a directory at the path identified by [dir].
     *
     * @throws IOException if [dir]'s parent does not exist, is not a directory, or cannot be written.
     *     A directory cannot be created if the current process doesn't have access, if there's a loop
     *     of symbolic links, or if any name is too long.
     */
    @Throws(IOException::class)
    fun createDirectory(dir: Path)

    /**
     * Creates a directory at the path identified by [dir], and any enclosing parent path directories,
     * recursively.
     *
     * @param mustCreate true to throw an [IOException] instead of overwriting an existing directory.
     * @throws IOException if any of the directories creation operation fails.
     */
    @Throws(IOException::class)
    fun createDirectories(dir: Path, mustCreate: Boolean = false)

    /**
     * Returns true if [path] identifies an object on this file system.
     *
     * @throws IOException if [path] cannot be accessed due to a connectivity problem, permissions
     *     problem, or other issue.
     */
    @Throws(IOException::class)
    fun exists(path: Path): Boolean
}

fun FileManager(
    fileSystem: FileSystem,
    logger: Logger,
): FileManager = object : FileManager {
    override fun isDirectory(path: Path): Boolean {
        val metadata = fileSystem.metadata(path)
        return metadata.isDirectory
    }

    override fun createDirectory(dir: Path) {
        fileSystem.createDirectory(dir)
    }

    override fun createDirectories(dir: Path, mustCreate: Boolean) {
        fileSystem.createDirectories(dir, mustCreate)
    }

    override fun exists(path: Path): Boolean =
        fileSystem.exists(path)

    override fun write(file: Path, mustCreate: Boolean, writerAction: BufferedSink.() -> Unit) {
        fileSystem.write(file, mustCreate, writerAction)
    }

    override fun findFilesToProcess(
        from: Path,
        recursive: Boolean,
        maxDepth: Int?,
        exclude: Regex?,
    ): List<Path> {
        val depth = if (recursive) {
            logger.debug("Recursive directory search is enabled. Verifying all directories until depth $maxDepth")
            maxDepth
        } else {
            0
        }
        return fileSystem
            .listRecursively(from, maxDepth = depth)
            .filter { path ->
                val isNotExcluded = exclude == null || !path.name.matches(exclude)
                isNotExcluded &&
                    (path.name.endsWith(FileType.Svg.extension) || path.name.endsWith(FileType.Avg.extension))
            }
            .toList()
    }

    override fun readContent(file: Path): String = fileSystem.read(file) {
        readUtf8()
    }

    override fun readBytes(file: Path): ByteArray = fileSystem.read(file) {
        readByteArray()
    }

    override fun copy(source: Path, target: Path) {
        fileSystem.copy(source, target)
    }

    override fun deleteRecursively(fileOrDirectory: Path, mustExist: Boolean) {
        fileSystem.deleteRecursively(fileOrDirectory, mustExist)
    }

    override fun delete(fileOrDirectory: Path, mustExist: Boolean) {
        fileSystem.delete(fileOrDirectory, mustExist)
    }
}
