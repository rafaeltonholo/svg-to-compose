package dev.tonholo.s2c.cli.dispatching

import dev.tonholo.s2c.dispatching.FileDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import okio.Path

/**
 * Processes files concurrently using coroutines with a concurrency cap.
 *
 * @param parallelism maximum number of files processed simultaneously.
 */
internal class ParallelFileDispatcher(private val parallelism: Int) : FileDispatcher {
    override fun <R> dispatch(items: List<Path>, action: (Int, Path) -> R): List<R> =
        runBlocking(Dispatchers.Default) {
            val semaphore = Semaphore(parallelism)
            items.mapIndexed { index, item ->
                async {
                    semaphore.withPermit { action(index, item) }
                }
            }.map { it.await() }
        }
}
