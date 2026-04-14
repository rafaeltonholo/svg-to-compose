package dev.tonholo.s2c.dispatching

import okio.Path.Companion.toPath
import kotlin.test.Test
import kotlin.test.assertEquals

class SequentialFileDispatcherTest {
    @Test
    fun `dispatch maps items sequentially with index`() {
        val files = listOf("a.svg", "b.svg", "c.svg").map { it.toPath() }
        val dispatcher: FileDispatcher = SequentialFileDispatcher

        val results = dispatcher.dispatch(files) { index, file ->
            "$index:${file.name}"
        }

        assertEquals(listOf("0:a.svg", "1:b.svg", "2:c.svg"), results)
    }

    @Test
    fun `dispatch with empty list returns empty`() {
        val results = SequentialFileDispatcher.dispatch(emptyList()) { _, file ->
            file.name
        }

        assertEquals(emptyList(), results)
    }

    @Test
    fun `dispatch preserves order`() {
        val files = (1..100).map { "$it.svg".toPath() }
        val results = SequentialFileDispatcher.dispatch(files) { index, _ -> index }

        assertEquals((0 until 100).toList(), results)
    }
}
