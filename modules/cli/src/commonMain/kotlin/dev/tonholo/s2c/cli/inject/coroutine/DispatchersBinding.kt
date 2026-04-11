package dev.tonholo.s2c.cli.inject.coroutine

import dev.tonholo.s2c.cli.inject.CliScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.Qualifier
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@BindingContainer
@ContributesTo(CliScope::class)
@SingleIn(CliScope::class)
internal object DispatchersBinding {
    @Provides
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}

@Qualifier
annotation class IoDispatcher

@Qualifier
annotation class DefaultDispatcher
