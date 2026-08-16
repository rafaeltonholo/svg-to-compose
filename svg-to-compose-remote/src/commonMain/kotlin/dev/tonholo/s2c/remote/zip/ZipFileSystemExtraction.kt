package dev.tonholo.s2c.remote.zip

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

internal fun FileSystem.extractAllTo(target: FileSystem, outputDir: Path): List<Path> {
    val root = "/".toPath()
    return listRecursively(root)
        .filter { path -> metadata(path).isRegularFile }
        .map { path -> copyEntryTo(entry = path, root = root, target = target, outputDir = outputDir) }
        .toList()
}

private fun FileSystem.copyEntryTo(
    entry: Path,
    root: Path,
    target: FileSystem,
    outputDir: Path,
): Path {
    val destination = outputDir / entry.relativeTo(root)
    destination.parent?.let(target::createDirectories)
    read(entry) {
        target.write(destination) { writeAll(this@read) }
    }
    return destination
}
