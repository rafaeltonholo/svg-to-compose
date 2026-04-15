package dev.tonholo.s2c.cli.dispatching

import dev.tonholo.s2c.dispatching.FileDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import okio.Path

/**
 * Processes files concurrently using coroutines with a concurrency cap.
 *
 * @param parallelism maximum number of files processed simultaneously.
 * @param dispatcher coroutine dispatcher for parallel execution.
 */
internal class ParallelFileDispatcher(
    private val parallelism: Int,
    private val dispatcher: CoroutineDispatcher,
) : FileDispatcher {
    override fun <R> dispatch(items: List<Path>, action: (Int, Path) -> R): List<R> =
        runBlocking(dispatcher) {
            val semaphore = Semaphore(parallelism)
            items.mapIndexed { index, item ->
                async {
                    semaphore.withPermit { action(index, item) }
                }
            }.awaitAll()
        }
}
