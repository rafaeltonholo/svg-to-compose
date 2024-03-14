package dev.tonholo.s2c.extensions

import okio.FileSystem
import okio.IOException
import okio.Path

/**
 * Okio has a nice API for listing directories recursively;
 * however, it doesn't provide a way to limit for how much
 * deep the recursive directory search goes.
 *
 * Having that in mind, and to avoid CLI searching forever
 * in the filesystem in case of user input a root directory
 * like `/`, I have copied their implementation of
 * [FileSystem.listRecursively], extending it to receive a
 * [maxDepth] parameter to limit that search, in case of
 * specified.
 *
 * If [maxDepth] is not specified, it forwards to the Okio API.
 *
 * Refer to [FileSystem.listRecursively] documentation to
 * understand about this API.
 */
internal fun FileSystem.listRecursively(
    dir: Path,
    followSymlinks: Boolean = false,
    maxDepth: Int? = null
): Sequence<Path> = if (maxDepth == null) {
    listRecursively(dir, followSymlinks)
} else {
    sequence {
        val stack = ArrayDeque<Path>()
        stack.addLast(dir)
        for (child in list(dir)) {
            collectRecursively(
                fileSystem = this@listRecursively,
                stack = stack,
                path = child,
                followSymlinks = followSymlinks,
                postorder = false,
                depth = 0,
                maxDepth = maxDepth,
            )
        }
    }
}

@Suppress("CognitiveComplexMethod", "NestedBlockDepth")
private suspend fun SequenceScope<Path>.collectRecursively(
    fileSystem: FileSystem,
    stack: ArrayDeque<Path>,
    path: Path,
    followSymlinks: Boolean,
    postorder: Boolean,
    depth: Int,
    maxDepth: Int,
) {
    // stop when deep overpass the maximum deep.
    if (depth > maxDepth) return

    // For listRecursively, visit enclosing directory first.
    if (!postorder) {
        yield(path)
    }

    val children = fileSystem.listOrNull(path) ?: listOf()
    if (children.isNotEmpty()) {
        // Figure out if the path is a symlink and detect symlink cycles.
        var symlinkPath = path
        var symlinkCount = 0
        while (true) {
            if (followSymlinks && symlinkPath in stack) throw IOException("symlink cycle at $path")
            symlinkPath = fileSystem.symlinkTarget(symlinkPath) ?: break
            symlinkCount++
        }

        // Recursively visit children.
        if (followSymlinks || symlinkCount == 0) {
            stack.addLast(symlinkPath)
            try {
                for (child in children) {
                    collectRecursively(
                        fileSystem = fileSystem,
                        stack = stack,
                        path = child,
                        followSymlinks = followSymlinks,
                        postorder = postorder,
                        depth = depth + 1,
                        maxDepth = maxDepth,
                    )
                }
            } finally {
                stack.removeLast()
            }
        }
    }

    // For deleteRecursively, visit enclosing directory last.
    if (postorder) {
        yield(path)
    }
}

/** Returns a resolved path to the symlink target, resolving it if necessary. */
@Throws(IOException::class)
private fun FileSystem.symlinkTarget(path: Path): Path? {
    val target = metadata(path).symlinkTarget ?: return null
    return path.parent!!.div(target)
}
