package dev.tonholo.s2c.io

import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.extensions.listRecursively
import dev.tonholo.s2c.logger.Logger
import okio.BufferedSink
import okio.FileSystem
import okio.IOException
import okio.Path

interface FileManager {
    /**
     * Finds SVG or XML files to process within a given directory.
     *
     * @param from The starting directory to search for files.
     * @param recursive If true, the search will be performed recursively through subdirectories.
     * @param maxDepth The maximum depth to search when recursive is true. Null means no limit.
     * @param exclude A regular expression to exclude files from the results. Null means no files are excluded.
     *
     * @return A list of Path objects representing the files found to process.
     */
    fun findFilesToProcess(
        from: Path,
        recursive: Boolean,
        maxDepth: Int?,
        exclude: Regex?,
    ): List<Path>

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

    /**
     * Creates a sink to write [file], executes [writerAction] to write it, and then closes the sink.
     * This is a compact way to write a file.
     *
     * @param mustCreate true to throw an [IOException] instead of overwriting an existing file.
     *     This is equivalent to `O_EXCL` on POSIX and `CREATE_NEW` on Windows.
     */
    @Throws(IOException::class)
    fun write(
        file: Path,
        mustCreate: Boolean = false,
        writerAction: BufferedSink.() -> Unit,
    )

    /**
     * Read the content of [file]
     *
     * @return file's content as [String].
     * @throws IOException if file doesn't exists or if something went wrong while reading the file.
     */
    @Throws(IOException::class)
    fun readContent(file: Path): String

    /**
     * Reads the entire content of a file into a byte array.
     *
     * @param file The path to the file to be read.
     * @return A byte array containing the entire content of the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    @Throws(IOException::class)
    fun readBytes(file: Path): ByteArray

    /**
     * Copies all the bytes from the file at [source] to the file at [target]. This does not copy
     * file metadata like last modified time, permissions, or extended attributes.
     *
     * This function is not atomic; a failure may leave [target] in an inconsistent state. For
     * example, [target] may be empty or contain only a prefix of [source].
     *
     * @throws IOException if [source] cannot be read or if [target] cannot be written.
     */
    @Throws(IOException::class)
    fun copy(source: Path, target: Path)

    /**
     * Recursively deletes all children of [fileOrDirectory] if it is a directory, then deletes
     * [fileOrDirectory] itself.
     *
     * This function does not defend against race conditions. For example, if child files are created
     * or deleted in [fileOrDirectory] while this function is executing, this may fail with an
     * [IOException].
     *
     * @param mustExist true to throw an [IOException] if there is nothing at [fileOrDirectory] to
     *     delete.
     * @throws IOException if any directory delete operation fails.
     */
    @Throws(IOException::class)
    fun deleteRecursively(fileOrDirectory: Path, mustExist: Boolean = false)
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
}
