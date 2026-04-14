package dev.tonholo.s2c.inject

import dev.tonholo.s2c.dispatching.FileDispatcher
import dev.tonholo.s2c.dispatching.SequentialFileDispatcher
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

/**
 * Default [FileDispatcher] binding container contributed to [AppScope].
 *
 * Provides [SequentialFileDispatcher] as the default implementation.
 * Consumers can exclude this container via [dev.zacsweers.metro.DependencyGraph.excludes]
 * and supply their own [FileDispatcher] binding to override the default behaviour.
 */
@ContributesTo(AppScope::class)
@BindingContainer
interface FileDispatcherBindings {
    companion object {
        @Provides
        fun provideFileDispatcher(): FileDispatcher = SequentialFileDispatcher
    }
}
