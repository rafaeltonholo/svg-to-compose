package dev.tonholo.s2c.extensions

/**
 * Creates a lazy [Sequence] that performs a depth-first traversal over a tree
 * structure, starting from [seeds] and expanding each node via [childrenOf].
 *
 * This utility avoids Kotlin's [sequence] builder (and its underlying coroutine
 * machinery) to prevent [ClassNotFoundException] for
 * `kotlin.coroutines.jvm.internal.SpillingKt` when running inside a Gradle
 * plugin under Gradle versions that bundle an older Kotlin stdlib (pre-2.1.20).
 *
 * @param seeds the initial items to iterate.
 * @param childrenOf returns the children of a node, or `null`/empty if the node
 *   is a leaf.
 */
internal fun <T> depthFirstSequence(
    seeds: Iterable<T>,
    childrenOf: (T) -> Iterable<T>?,
): Sequence<T> = Sequence { DepthFirstIterator(seeds, childrenOf) }

/**
 * Stack-based iterator that yields nodes in depth-first pre-order.
 *
 * Each level of the tree is represented by an [Iterator] on the [stack].
 * When a node is emitted its children (if any) are pushed so they are
 * visited before remaining siblings.
 *
 * **Not thread-safe.** A new instance is created per [Sequence.iterator] call,
 * so concurrent iteration of the same [Sequence] instance is safe as long as
 * each consumer obtains its own iterator. Sharing a single iterator across
 * threads will corrupt the internal stack.
 */
private class DepthFirstIterator<T>(
    seeds: Iterable<T>,
    private val childrenOf: (T) -> Iterable<T>?,
) : Iterator<T> {
    private val stack = ArrayDeque<Iterator<T>>()

    init {
        seeds.iterator().pushIfHasNext()
    }

    override fun hasNext(): Boolean {
        pruneExhausted()
        return stack.isNotEmpty()
    }

    override fun next(): T {
        if (!hasNext()) throw NoSuchElementException()
        val item = stack.last().next()
        pushChildren(item)
        return item
    }

    private fun pushChildren(item: T) {
        val children = childrenOf(item) ?: return
        children.iterator().pushIfHasNext()
    }

    private fun Iterator<T>.pushIfHasNext() {
        if (hasNext()) {
            stack.addLast(this)
        }
    }

    private fun pruneExhausted() {
        while (stack.isNotEmpty() && !stack.last().hasNext()) {
            stack.removeLast()
        }
    }
}
