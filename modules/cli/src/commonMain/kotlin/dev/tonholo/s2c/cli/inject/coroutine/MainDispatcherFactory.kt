package dev.tonholo.s2c.cli.inject.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Provides a serialized [CoroutineDispatcher] used as a "main" dispatcher for
 * TUI animation and state updates.
 *
 * Implementation notes:
 *  - [Dispatchers.Default] is used as the underlying pool so no OS thread is
 *    owned by the CLI. This avoids the thread leak inherent to
 *    `newSingleThreadContext`, which the calling code is not guaranteed to
 *    close on process exit (native targets do not run shutdown hooks, and JVM
 *    exit happens before dispatchers can be cooperatively released).
 *  - [CoroutineDispatcher.limitedParallelism] with `parallelism = 1` serializes
 *    execution so state updates remain ordered, matching the behaviour the
 *    TUI reducers expect.
 */
@OptIn(ExperimentalCoroutinesApi::class)
internal fun mainCoroutineDispatcher(): CoroutineDispatcher =
    Dispatchers.Default.limitedParallelism(parallelism = 1)
