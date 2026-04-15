package dev.tonholo.s2c.cli.inject.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext

/**
 * Provides a main-thread-like [CoroutineDispatcher] for TUI animation.
 */
@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
internal fun mainCoroutineDispatcher(): CoroutineDispatcher =
    newSingleThreadContext("Main")
