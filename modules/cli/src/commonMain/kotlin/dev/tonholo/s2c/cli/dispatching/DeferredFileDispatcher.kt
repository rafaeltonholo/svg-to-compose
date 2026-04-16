package dev.tonholo.s2c.cli.dispatching

import dev.tonholo.s2c.SvgToComposeContext
import dev.tonholo.s2c.dispatching.FileDispatcher
import dev.tonholo.s2c.dispatching.SequentialFileDispatcher
import dev.tonholo.s2c.dispatching.availableProcessors
import dev.tonholo.s2c.runtime.S2cConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okio.Path

/**
 * [FileDispatcher] that defers strategy selection until [dispatch] is called.
 *
 * The DI graph is constructed before CLI flags are parsed, so reading
 * [S2cConfig.parallel] at injection time always sees the default
 * (disabled). This wrapper reads the config snapshot at dispatch time,
 * after [dev.tonholo.s2c.cli.runtime.Client.run] has updated the
 * configuration.
 *
 * @param context shared context holding the current config snapshot.
 * @param dispatcher coroutine dispatcher forwarded to [ParallelFileDispatcher].
 */
internal class DeferredFileDispatcher(
    private val context: SvgToComposeContext,
    private val dispatcher: CoroutineDispatcher,
) : FileDispatcher {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun <R> dispatch(items: List<Path>, action: (Int, Path) -> R): List<R> {
        val config = context.configSnapshot
        val parallelism = when (config.parallel) {
            S2cConfig.PARALLEL_DISABLED -> return SequentialFileDispatcher.dispatch(items, action)
            S2cConfig.PARALLEL_CPU_CORES -> availableProcessors()
            else -> config.parallel
        }
        if (parallelism <= 1) {
            return SequentialFileDispatcher.dispatch(items, action)
        }

        // `limitedParallelism(n)` on a shared dispatcher (e.g. Dispatchers.Default / Dispatchers.IO)
        // returns a view over that dispatcher. The view owns no extra resources, so it must not
        // be closed. Calling close() on it delegates to the backing dispatcher, which throws
        // `UnsupportedOperationException` for the built-in shared pools.
        val limited = dispatcher.limitedParallelism(parallelism)
        return ParallelFileDispatcher(parallelism = parallelism, dispatcher = limited).dispatch(items, action)
    }
}
