package dev.tonholo.s2c.dispatching

import okio.Path

/**
 * Strategy for iterating over a list of files and applying an action to each.
 *
 * Implementations control the concurrency model (sequential, parallel, etc.)
 * while the caller defines the per-file action via the [action] lambda.
 */
interface FileDispatcher {
    /**
     * Applies [action] to each file in [items] and returns the collected results.
     *
     * @param R the result type produced by each action invocation.
     * @param items the files to process.
     * @param action the per-file action receiving the index and file path.
     * @return results in the same order as [items].
     */
    fun <R> dispatch(items: List<Path>, action: (index: Int, file: Path) -> R): List<R>
}
