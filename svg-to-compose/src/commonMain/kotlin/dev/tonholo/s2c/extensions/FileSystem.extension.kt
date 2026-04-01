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
 * Uses [depthFirstSequence] backed by a stack-based [Iterator] instead of Kotlin's
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

    return depthFirstSequence(seeds = list(dir)) { path ->
        // Depth is relative to `dir`: direct children are depth 0, their
        // children are depth 1, etc.  `segments.size` counts every component
        // of the absolute path, so subtracting `rootDepth` (the segment count
        // of `dir`) gives the nesting level, minus 1 because `dir` itself is
        // not part of the result.
        val depth = path.segments.size - rootDepth - 1
        if (maxDepth != null && depth >= maxDepth) return@depthFirstSequence null

        listOrNull(path)?.ifEmpty { null }
    }
}

/**
 * Recursively deletes [fileOrDirectory] and all of its contents using a
 * stack-based post-order traversal. Each directory's children are deleted
 * before the directory itself, satisfying the filesystem constraint that a
 * directory must be empty before deletion. Files are deleted immediately
 * as they are visited, so memory usage is proportional to the tree depth,
 * not total node count.
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
    // Stack holds directories whose children still need to be visited.
    // Once all children of a directory are processed, the directory itself
    // is deleted.
    val stack = ArrayDeque<Path>()
    stack.addLast(fileOrDirectory)

    while (stack.isNotEmpty()) {
        val current = stack.last()
        val children = listOrNull(current)
        if (children.isNullOrEmpty()) {
            // Leaf file or now-empty directory: delete and pop.
            stack.removeLast()
            delete(current, mustExist = false)
        } else {
            // Push children so they are processed before the parent.
            for (child in children) {
                stack.addLast(child)
            }
        }
    }
}
