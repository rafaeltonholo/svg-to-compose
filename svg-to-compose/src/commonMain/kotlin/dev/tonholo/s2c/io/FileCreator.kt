package dev.tonholo.s2c.io

import okio.BufferedSink
import okio.IOException
import okio.Path

interface FileCreator {
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
}
