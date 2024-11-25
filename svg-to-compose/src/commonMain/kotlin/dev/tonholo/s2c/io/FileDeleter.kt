package dev.tonholo.s2c.io

import okio.IOException
import okio.Path

interface FileDeleter {
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

    @Throws(IOException::class)
    fun delete(fileOrDirectory: Path, mustExist: Boolean = false)
}
