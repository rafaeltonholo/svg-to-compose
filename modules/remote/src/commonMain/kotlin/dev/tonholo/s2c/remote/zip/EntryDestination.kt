package dev.tonholo.s2c.remote.zip

import okio.Path

/**
 * Resolves an archive [entryName] against [outputDir], returning the
 * destination path, or `null` when the entry would land outside [outputDir].
 */
internal fun containedDestination(outputDir: Path, entryName: String): Path? {
    val destination = (outputDir / entryName).normalized()
    val base = outputDir.normalized()
    val relative = runCatching { destination.relativeTo(base) }.getOrNull() ?: return null
    return destination.takeIf { relative.segments.firstOrNull() != ".." && relative.segments.isNotEmpty() }
}
