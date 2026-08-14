package dev.tonholo.s2c.remote.zip

import kotlinx.coroutines.await
import okio.FileSystem
import okio.Path
import org.khronos.webgl.Int8Array
import org.khronos.webgl.Uint8Array

actual class ZipExtractor actual constructor(
    private val fileSystem: FileSystem,
) {
    actual suspend fun extract(zipPath: Path, outputDir: Path): List<Path> {
        val bytes = fileSystem.read(zipPath) { readByteArray() }
        val archive = JsZip.loadAsync(bytes.toUint8Array()).await()
        return archive.fileEntries().map { entry -> writeEntry(entry, outputDir) }
    }

    private suspend fun writeEntry(entry: JsZipEntry, outputDir: Path): Path {
        val data = entry.async("uint8array").await()
        val destination = outputDir / entry.name
        destination.parent?.let(fileSystem::createDirectories)
        fileSystem.write(destination) { write(data.toByteArray()) }
        return destination
    }
}

private fun JsZip.fileEntries(): List<JsZipEntry> {
    val entries = mutableListOf<JsZipEntry>()
    forEach { _, entry ->
        if (!entry.dir) {
            entries.add(entry)
        }
    }
    return entries
}

private fun ByteArray.toUint8Array(): Uint8Array {
    val int8Array = unsafeCast<Int8Array>()
    return Uint8Array(int8Array.buffer, int8Array.byteOffset, int8Array.length)
}

private fun Uint8Array.toByteArray(): ByteArray =
    Int8Array(buffer, byteOffset, length).unsafeCast<ByteArray>()
