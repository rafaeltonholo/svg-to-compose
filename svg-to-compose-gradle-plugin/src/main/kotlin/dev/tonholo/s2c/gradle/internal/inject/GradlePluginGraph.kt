package dev.tonholo.s2c.gradle.internal.inject

import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.gradle.internal.cache.CacheManager
import dev.tonholo.s2c.inject.SvgToComposeGraph
import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.logger.Logger
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Includes
import dev.zacsweers.metro.Provides
import org.gradle.api.file.DirectoryProperty

/**
 * App-level dependency graph for the Gradle plugin.
 *
 * This graph includes the core [SvgToComposeGraph] via [@Includes][Includes] and adds
 * Gradle-specific bindings such as the [CacheManager].
 *
 * Created once per task execution. Workers share this graph's [Processor.Factory]
 * via [S2cWorkerBridge][dev.tonholo.s2c.gradle.internal.service.S2cWorkerBridge]
 * and create isolated [Processor] instances with per-worker temp directories.
 */
@DependencyGraph
internal interface GradlePluginGraph {
    val processorFactory: Processor.Factory
    val fileManager: FileManager
    val logger: Logger
    val cacheManager: CacheManager

    @Provides
    fun provideCacheManager(
        logger: Logger,
        fileManager: FileManager,
        @BuildDirectory buildDirectory: DirectoryProperty,
    ): CacheManager = CacheManager(logger, fileManager, buildDirectory)

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(
            @Includes svgToComposeGraph: SvgToComposeGraph,
            @Provides @BuildDirectory buildDirectory: DirectoryProperty,
        ): GradlePluginGraph
    }
}
