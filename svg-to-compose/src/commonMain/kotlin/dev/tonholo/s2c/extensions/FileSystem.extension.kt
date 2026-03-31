package dev.tonholo.s2c.extensions

import okio.FileSystem
import okio.IOException
import okio.Path

/**
 * Lists all paths within [dir] recursively with an optional [maxDepth] limit.
 *
 * Symlinks are **not** followed. Symlink entries are emitted as regular paths
 * but their targets are not traversed.
 *
 * Uses [lazySequence] backed by a stack-based [Iterator] instead of Kotlin's
 * [sequence] builder. This avoids coroutine bytecode that references
 * `kotlin.coroutines.jvm.internal.SpillingKt`, a class absent from the Kotlin
 * stdlib bundled with Gradle 8.x, which would cause [ClassNotFoundException] at
 * runtime inside the Gradle plugin.
 *
 * @param maxDepth maximum directory depth to descend into. `null` means
 *   unlimited. Depth 0 returns only the direct children of [dir].
 */
internal fun FileSystem.listRecursively(dir: Path, maxDepth: Int? = null): Sequence<Path> {
    val rootDepth = dir.segments.size

    return lazySequence(seeds = list(dir)) { path ->
        // Depth is relative to `dir`: direct children are depth 0, their
        // children are depth 1, etc.  `segments.size` counts every component
        // of the absolute path, so subtracting `rootDepth` (the segment count
        // of `dir`) gives the nesting level, minus 1 because `dir` itself is
        // not part of the result.
        val depth = path.segments.size - rootDepth - 1
        if (maxDepth != null && depth >= maxDepth) return@lazySequence null

        listOrNull(path)?.ifEmpty { null }
    }
}

/**
 * Recursively deletes all children of [fileOrDirectory] then the directory
 * itself, using [lazySequence] to enumerate paths (preorder) and deleting in
 * reverse (postorder) to satisfy the filesystem constraint that a directory
 * must be empty before deletion.
 *
 * Avoids Okio's [FileSystem.deleteRecursively] which internally uses Kotlin's
 * [sequence] builder.
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
