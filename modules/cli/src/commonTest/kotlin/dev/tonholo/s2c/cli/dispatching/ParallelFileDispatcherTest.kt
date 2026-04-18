package dev.tonholo.s2c.cli.dispatching

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okio.Path.Companion.toPath
import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.decrementAndFetch
import kotlin.concurrent.atomics.incrementAndFetch
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import kotlin.time.TimeSource

@OptIn(ExperimentalAtomicApi::class, ExperimentalCoroutinesApi::class)
class ParallelFileDispatcherTest {
    @Test
    fun `given 20 files and parallelism 4 - when dispatch is called - then all files are processed in order`() {
        // Arrange
        val files = (1..20).map { "$it.svg".toPath() }
        val dispatcher = ParallelFileDispatcher(parallelism = 4, dispatcher = Dispatchers.Default)

        // Act
        val results = dispatcher.dispatch(files) { index, file -> "$index:${file.name}" }

        // Assert
        assertEquals(expected = 20, actual = results.size)
        assertEquals(expected = "0:1.svg", actual = results[0])
        assertEquals(expected = "19:20.svg", actual = results[19])
    }

    @Test
    fun `given 50 files - when dispatch is called - then result order matches input order`() {
        // Arrange
        val files = (1..50).map { "$it.svg".toPath() }
        val dispatcher = ParallelFileDispatcher(parallelism = 8, dispatcher = Dispatchers.Default)

        // Act
        val results = dispatcher.dispatch(files) { index, _ -> index }

        // Assert
        assertEquals(expected = (0 until 50).toList(), actual = results)
    }

    @Test
    fun `given empty file list - when dispatch is called - then returns empty list`() {
        // Arrange
        val dispatcher = ParallelFileDispatcher(parallelism = 4, dispatcher = Dispatchers.Default)

        // Act
        val results = dispatcher.dispatch(emptyList()) { _, file -> file.name }

        // Assert
        assertTrue(results.isEmpty())
    }

    @Test
    fun `given parallelism 1 - when dispatch is called - then behaves sequentially`() {
        // Arrange
        val files = listOf("a.svg", "b.svg").map { it.toPath() }
        val dispatcher = ParallelFileDispatcher(parallelism = 1, dispatcher = Dispatchers.Default)

        // Act
        val results = dispatcher.dispatch(files) { index, file -> "$index:${file.name}" }

        // Assert
        assertEquals(expected = listOf("0:a.svg", "1:b.svg"), actual = results)
    }

    @Test
    fun `given parallelism 4 - when actions block - then at least 2 actions run concurrently`() {
        // Arrange
        val files = (1..8).map { "$it.svg".toPath() }
        val executionDispatcher = Dispatchers.Default.limitedParallelism(parallelism = 4)
        val dispatcher = ParallelFileDispatcher(parallelism = 4, dispatcher = executionDispatcher)
        val running = AtomicInt(0)
        val maxObserved = AtomicInt(0)

        // Act
        dispatcher.dispatch(files) { _, _ ->
            val now = running.incrementAndFetch()
            updateMax(maxObserved, now)
            // Busy-wait briefly to force overlap without relying on a
            // cross-platform sleep primitive.
            val mark = TimeSource.Monotonic.markNow()
            while (mark.elapsedNow().inWholeMilliseconds < CONCURRENCY_HOLD_MS) {
                // spin
            }
            running.decrementAndFetch()
        }

        // Assert
        val observed = maxObserved.load()
        assertTrue(
            actual = observed >= 2,
            message = "Expected at least 2 concurrent actions, observed $observed",
        )
    }

    @Test
    fun `given an action that throws - when dispatch is called - then the exception propagates`() {
        // Arrange
        val files = (1..4).map { "$it.svg".toPath() }
        val dispatcher = ParallelFileDispatcher(parallelism = 2, dispatcher = Dispatchers.Default)

        // Act / Assert
        assertFailsWith<IllegalStateException> {
            dispatcher.dispatch(files) { index, _ ->
                if (index == 2) error("boom")
            }
        }
    }

    private fun updateMax(target: AtomicInt, candidate: Int) {
        while (true) {
            val current = target.load()
            if (candidate <= current) return
            if (target.compareAndSet(current, candidate)) return
        }
    }

    private companion object {
        const val CONCURRENCY_HOLD_MS = 50L
    }
}
