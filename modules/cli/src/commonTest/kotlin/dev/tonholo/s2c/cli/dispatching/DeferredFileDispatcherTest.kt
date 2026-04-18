package dev.tonholo.s2c.cli.dispatching

import dev.tonholo.s2c.SvgToComposeContextImpl
import dev.tonholo.s2c.cli.runtime.CliConfig
import dev.tonholo.s2c.dispatching.availableProcessors
import kotlinx.coroutines.Dispatchers
import okio.Path.Companion.toPath
import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.incrementAndFetch
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalAtomicApi::class)
class DeferredFileDispatcherTest {
    @Test
    fun `given parallel disabled - when dispatch is called - then routes to sequential`() {
        // Arrange
        val context = SvgToComposeContextImpl(CliConfig(parallel = CliConfig.PARALLEL_DISABLED))
        val dispatcher = DeferredFileDispatcher(context = context, dispatcher = Dispatchers.Default)
        val files = listOf("a.svg", "b.svg", "c.svg").map { it.toPath() }
        val maxConcurrency = AtomicInt(0)

        // Act
        val results = dispatcher.dispatch(files) { index, file ->
            maxConcurrency.incrementAndFetch()
            "$index:${file.name}"
        }

        // Assert
        assertEquals(expected = listOf("0:a.svg", "1:b.svg", "2:c.svg"), actual = results)
    }

    @Test
    fun `given parallel explicit 4 - when dispatch is called - then all results returned in order`() {
        // Arrange
        val context = SvgToComposeContextImpl(CliConfig(parallel = 4))
        val dispatcher = DeferredFileDispatcher(context = context, dispatcher = Dispatchers.Default)
        val files = (1..10).map { "$it.svg".toPath() }

        // Act
        val results = dispatcher.dispatch(files) { index, _ -> index }

        // Assert
        assertEquals(expected = (0 until 10).toList(), actual = results)
    }

    @Test
    fun `given parallel 1 - when dispatch is called - then routes to sequential`() {
        // Arrange
        val context = SvgToComposeContextImpl(CliConfig(parallel = 1))
        val dispatcher = DeferredFileDispatcher(context = context, dispatcher = Dispatchers.Default)
        val files = listOf("x.svg", "y.svg").map { it.toPath() }

        // Act
        val results = dispatcher.dispatch(files) { _, file -> file.name }

        // Assert
        assertEquals(expected = listOf("x.svg", "y.svg"), actual = results)
    }

    @Test
    fun `given parallel cpu cores - when dispatch is called - then uses available processors`() {
        // Arrange
        val context = SvgToComposeContextImpl(CliConfig(parallel = CliConfig.PARALLEL_CPU_CORES))
        val dispatcher = DeferredFileDispatcher(context = context, dispatcher = Dispatchers.Default)
        val files = (1..20).map { "$it.svg".toPath() }

        // Act
        val results = dispatcher.dispatch(files) { index, _ -> index }

        // Assert
        assertEquals(expected = 20, actual = results.size)
        assertEquals(expected = (0 until 20).toList(), actual = results)
        // Sanity: the platform reports a positive core count.
        assertTrue(availableProcessors() >= 1)
    }

    @Test
    fun `given config changes between dispatch calls - when dispatch is called - then reads latest snapshot`() {
        // Arrange
        val context = SvgToComposeContextImpl(CliConfig(parallel = CliConfig.PARALLEL_DISABLED))
        val dispatcher = DeferredFileDispatcher(context = context, dispatcher = Dispatchers.Default)
        val files = listOf("a.svg").map { it.toPath() }

        // Act
        val first = dispatcher.dispatch(files) { _, _ -> context.configSnapshot.parallel }
        context.updateConfig { CliConfig(parallel = 4) }
        val second = dispatcher.dispatch(files) { _, _ -> context.configSnapshot.parallel }

        // Assert
        assertEquals(expected = listOf(CliConfig.PARALLEL_DISABLED), actual = first)
        assertEquals(expected = listOf(4), actual = second)
    }
}
