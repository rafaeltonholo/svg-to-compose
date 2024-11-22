package dev.tonholo.s2c.io

import okio.IOException
import okio.Path

interface FileReader {
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
}
