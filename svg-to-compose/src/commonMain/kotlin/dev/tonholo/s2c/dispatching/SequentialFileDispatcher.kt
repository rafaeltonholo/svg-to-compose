package dev.tonholo.s2c.dispatching

import okio.Path

/**
 * Processes files one at a time in order. Default strategy.
 */
object SequentialFileDispatcher : FileDispatcher {
    override fun <R> dispatch(items: List<Path>, action: (Int, Path) -> R): List<R> = items.mapIndexed(action)
}
