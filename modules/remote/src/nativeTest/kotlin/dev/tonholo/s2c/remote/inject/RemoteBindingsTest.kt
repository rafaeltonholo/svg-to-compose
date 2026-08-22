package dev.tonholo.s2c.remote.inject

import dev.tonholo.s2c.inject.FileDispatcherBindings
import dev.tonholo.s2c.inject.SvgToComposeBindings
import dev.tonholo.s2c.remote.source.DefaultSourceResolver
import dev.tonholo.s2c.remote.source.SourceResolver
import dev.tonholo.s2c.remote.zip.ZipExtractor
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.createGraph
import okio.FileSystem
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertNotNull

@DependencyGraph(
    scope = AppScope::class,
    excludes = [SvgToComposeBindings::class, FileDispatcherBindings::class],
)
internal interface RemoteBindingsTestGraph {
    val sourceResolver: SourceResolver
    val zipExtractor: ZipExtractor

    @Provides
    fun provideFileSystem(): FileSystem = FakeFileSystem()
}

class RemoteBindingsTest {

    @Test
    fun `given app scope graph - when remote bindings are contributed - then resolver and extractor resolve`() {
        // Arrange / Act
        val graph = createGraph<RemoteBindingsTestGraph>()

        // Assert
        assertIs<DefaultSourceResolver>(graph.sourceResolver)
        assertNotNull(graph.zipExtractor)
    }
}
