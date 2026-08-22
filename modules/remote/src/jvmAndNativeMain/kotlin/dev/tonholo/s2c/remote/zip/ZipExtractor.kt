package dev.tonholo.s2c.remote.zip

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.openZip

actual fun createZipExtractor(fileSystem: FileSystem): ZipExtractor = OkioZipExtractor(fileSystem)

private class OkioZipExtractor(private val fileSystem: FileSystem) : ZipExtractor {
    override suspend fun extract(zipPath: Path, outputDir: Path): List<Path> {
        val archive = fileSystem.openZip(zipPath)
        return archive.copyArchiveContentsTo(target = fileSystem, outputDir = outputDir)
    }
}

private fun FileSystem.copyArchiveContentsTo(target: FileSystem, outputDir: Path): List<Path> {
    val archiveRoot = "/".toPath()
    return listRecursively(archiveRoot)
        .filter { path -> metadata(path).isRegularFile }
        .map { entry ->
            copyArchiveEntryTo(
                entry = entry,
                archiveRoot = archiveRoot,
                target = target,
                outputDir = outputDir,
            )
        }
        .toList()
}

private fun FileSystem.copyArchiveEntryTo(
    entry: Path,
    archiveRoot: Path,
    target: FileSystem,
    outputDir: Path,
): Path {
    val destination = outputDir / entry.relativeTo(archiveRoot)
    destination.parent?.let(target::createDirectories)
    read(entry) {
        target.write(destination) { writeAll(this@read) }
    }
    return destination
}
