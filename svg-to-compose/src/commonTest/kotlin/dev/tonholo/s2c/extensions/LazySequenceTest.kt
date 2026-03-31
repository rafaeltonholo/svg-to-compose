package dev.tonholo.s2c.extensions

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LazySequenceTest {
    @Test
    fun `given empty seeds, when iterating, then returns empty sequence`() {
        // Arrange
        val seeds = emptyList<String>()
        // Act
        val result = lazySequence(seeds) { null }.toList()
        // Assert
        assertTrue(result.isEmpty())
    }

    @Test
    fun `given flat seeds with no children, when iterating, then returns seeds in order`() {
        // Arrange
        val seeds = listOf("a", "b", "c")
        // Act
        val result = lazySequence(seeds) { null }.toList()
        // Assert
        assertEquals(listOf("a", "b", "c"), result)
    }

    @Test
    fun `given tree structure, when iterating, then returns depth-first preorder`() {
        // Arrange
        // Tree:  a -> [a1, a2], a1 -> [a1x], b -> [b1]
        val tree = mapOf(
            "a" to listOf("a1", "a2"),
            "a1" to listOf("a1x"),
            "b" to listOf("b1"),
        )
        val seeds = listOf("a", "b")
        // Act
        val result = lazySequence(seeds) { node -> tree[node] }.toList()
        // Assert
        assertEquals(listOf("a", "a1", "a1x", "a2", "b", "b1"), result)
    }

    @Test
    fun `given tree structure, when iterating lazily, then only visits as needed`() {
        // Arrange
        var childrenCalls = 0
        val tree = mapOf(
            "a" to listOf("a1", "a2"),
            "a1" to listOf("a1x"),
        )
        val seeds = listOf("a")
        // Act - only take first 2
        val result = lazySequence(seeds) { node ->
            childrenCalls++
            tree[node]
        }.take(2).toList()
        // Assert
        assertEquals(listOf("a", "a1"), result)
        // childrenOf called for "a" (returns children) and "a1" (returns children),
        // but NOT for "a1x" or "a2" since we stopped at 2
        assertEquals(2, childrenCalls)
    }
}
