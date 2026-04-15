package dev.tonholo.s2c.cli.dispatching

import dev.tonholo.s2c.SvgToComposeContext
import dev.tonholo.s2c.cli.runtime.CliConfig
import dev.tonholo.s2c.dispatching.FileDispatcher
import dev.tonholo.s2c.dispatching.SequentialFileDispatcher
import dev.tonholo.s2c.dispatching.availableProcessors
import kotlinx.coroutines.CoroutineDispatcher
import okio.Path

/**
 * [FileDispatcher] that defers strategy selection until [dispatch] is called.
 *
 * The DI graph is constructed before CLI flags are parsed, so reading
 * [dev.tonholo.s2c.runtime.S2cConfig.parallel] at injection time always
 * sees the default (disabled). This wrapper reads the config snapshot at
 * dispatch time, after [dev.tonholo.s2c.cli.runtime.Client.run] has
 * updated the configuration.
 *
 * @param context shared context holding the current config snapshot.
 * @param dispatcher coroutine dispatcher forwarded to [ParallelFileDispatcher].
 */
internal class DeferredFileDispatcher(
    private val context: SvgToComposeContext,
    private val dispatcher: CoroutineDispatcher,
) : FileDispatcher {
    override fun <R> dispatch(items: List<Path>, action: (Int, Path) -> R): List<R> {
        val config = context.configSnapshot
        val parallelism = when (config.parallel) {
            CliConfig.PARALLEL_DISABLED -> return SequentialFileDispatcher.dispatch(items, action)
            CliConfig.PARALLEL_CPU_CORES -> availableProcessors()
            else -> config.parallel
        }
        val delegate = if (parallelism <= 1) {
            SequentialFileDispatcher
        } else {
            ParallelFileDispatcher(parallelism, dispatcher.limitedParallelism(parallelism))
        }
        return delegate.dispatch(items, action)
    }
}
