package dev.tonholo.s2c.extensions

import okio.FileSystem
import okio.IOException
import okio.Path

/**
 * Lists all paths within [dir] recursively with an optional [maxDepth] limit.
 *
 * Uses [lazySequence] backed by a stack-based [Iterator] instead of Kotlin's
 * [sequence] builder.  This avoids coroutine bytecode that references
 * `kotlin.coroutines.jvm.internal.SpillingKt` - a class absent from the Kotlin
 * stdlib bundled with Gradle 8.x, which would cause [ClassNotFoundException] at
 * runtime inside the Gradle plugin.
 *
 * @param maxDepth maximum directory depth to descend into. `null` means
 *   unlimited. Depth 0 returns only the direct children of [dir].
 */
internal fun FileSystem.listRecursively(
    dir: Path,
    followSymlinks: Boolean = false,
    maxDepth: Int? = null,
): Sequence<Path> {
    val fileSystem = this
    val rootDepth = dir.segments.size

    return lazySequence(seeds = list(dir)) { path ->
        val depth = path.segments.size - rootDepth - 1
        if (maxDepth != null && depth >= maxDepth) return@lazySequence null

        val children = fileSystem.listOrNull(path).orEmpty()
        children.takeIf { it.isNotEmpty() }?.also {
            val symlinkTarget = fileSystem.symlinkTarget(path)
            if (symlinkTarget != null && followSymlinks) {
                // Following symlinks: resolve the full chain and check for chain cycles.
                fileSystem.resolveSymlinks(path)
            }
        }
    }
}

/**
 * Resolves the full symlink chain for [path], detecting chain cycles.
 *
 * Uses a local visited set per call so that two independent symlinks pointing
 * to the same target are **not** falsely flagged as a cycle.
 *
 * @return the resolved real path.
 * @throws IOException if the symlink chain forms a loop.
 */
@Throws(IOException::class)
private fun FileSystem.resolveSymlinks(path: Path): Path {
    val visited = mutableSetOf<Path>()
    var current = path
    while (true) {
        if (!visited.add(current)) {
            throw IOException("symlink cycle at $path")
        }
        current = symlinkTarget(current) ?: break
    }
    return current
}

/**
 * Recursively deletes all children of [fileOrDirectory] then the directory
 * itself, using [lazySequence] to enumerate paths (preorder) and deleting in
 * reverse (postorder) to satisfy the filesystem constraint that a directory
 * must be empty before deletion.
 *
 * Avoids Okio's [FileSystem.deleteRecursively] which internally uses Kotlin's
 * [sequence] builder
 *
 * @see [listRecursively] KDoc for why.
 */
internal fun FileSystem.deleteRecursivelyCompat(fileOrDirectory: Path, mustExist: Boolean = false) {
    if (!exists(fileOrDirectory)) {
        if (mustExist) throw IOException("$fileOrDirectory does not exist")
        return
    }
    val allPaths = lazySequence(seeds = listOf(fileOrDirectory)) { path ->
        listOrNull(path)
    }.toList()

    // Delete in reverse order (deepest children first).
    for (path in allPaths.asReversed()) {
        delete(path, mustExist = false)
    }
}

/** Resolves a symlink target relative to its parent, or returns null if not a symlink. */
@Throws(IOException::class)
private fun FileSystem.symlinkTarget(path: Path): Path? {
    val target = metadata(path).symlinkTarget ?: return null
    val parent = checkNotNull(path.parent) { "Cannot resolve symlink for root path: $path" }
    return parent.div(target)
}
