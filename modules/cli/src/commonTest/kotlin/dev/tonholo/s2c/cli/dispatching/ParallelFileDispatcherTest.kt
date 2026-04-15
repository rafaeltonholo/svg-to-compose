package dev.tonholo.s2c.cli.dispatching

import kotlinx.coroutines.Dispatchers
import okio.Path.Companion.toPath
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ParallelFileDispatcherTest {
    @Test
    fun `dispatch processes all files`() {
        val files = (1..20).map { "$it.svg".toPath() }
        val dispatcher = ParallelFileDispatcher(parallelism = 4, dispatcher = Dispatchers.Default)

        val results = dispatcher.dispatch(files) { index, file -> "$index:${file.name}" }

        assertEquals(20, results.size)
        assertEquals("0:1.svg", results[0])
        assertEquals("19:20.svg", results[19])
    }

    @Test
    fun `dispatch preserves result order`() {
        val files = (1..50).map { "$it.svg".toPath() }
        val dispatcher = ParallelFileDispatcher(parallelism = 8, dispatcher = Dispatchers.Default)

        val results = dispatcher.dispatch(files) { index, _ -> index }

        assertEquals((0 until 50).toList(), results)
    }

    @Test
    fun `dispatch with empty list returns empty`() {
        val dispatcher = ParallelFileDispatcher(parallelism = 4, dispatcher = Dispatchers.Default)
        val results = dispatcher.dispatch(emptyList()) { _, file -> file.name }

        assertTrue(results.isEmpty())
    }

    @Test
    fun `dispatch with parallelism 1 behaves like sequential`() {
        val files = listOf("a.svg", "b.svg").map { it.toPath() }
        val dispatcher = ParallelFileDispatcher(parallelism = 1, dispatcher = Dispatchers.Default)

        val results = dispatcher.dispatch(files) { index, file -> "$index:${file.name}" }

        assertEquals(listOf("0:a.svg", "1:b.svg"), results)
    }
}
