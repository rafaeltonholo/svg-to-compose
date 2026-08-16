package dev.tonholo.s2c.remote.inject

import dev.tonholo.s2c.remote.source.DefaultSourceResolver
import dev.tonholo.s2c.remote.source.SourceResolver
import dev.tonholo.s2c.remote.zip.ZipExtractor
import dev.tonholo.s2c.remote.zip.createZipExtractor
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import okio.FileSystem

/**
 * Reusable binding container for remote source resolution.
 *
 * Contributed to [AppScope] so any [dev.zacsweers.metro.DependencyGraph]
 * scoped to [AppScope] automatically receives the [SourceResolver] and
 * [ZipExtractor] bindings, provided the graph supplies a [FileSystem].
 *
 * Contribution discovery only works on JVM and native targets; JS and WasmJS
 * graphs must list this container in `bindingContainers` explicitly because
 * Metro hint generation is disabled for those platforms (see KT-82395).
 */
@ContributesTo(AppScope::class)
@BindingContainer
interface RemoteBindings {
    @Binds
    val DefaultSourceResolver.sourceResolver: SourceResolver

    companion object {
        @Provides
        @SingleIn(AppScope::class)
        fun provideZipExtractor(fileSystem: FileSystem): ZipExtractor =
            createZipExtractor(fileSystem)
    }
}
