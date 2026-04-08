package dev.tonholo.s2c.gradle.internal.inject

import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.gradle.internal.GradleS2cConfig
import dev.tonholo.s2c.gradle.internal.cache.PersistentOutputRegistry
import dev.tonholo.s2c.gradle.tasks.PERSISTENT_REGISTRY_PATH
import dev.tonholo.s2c.gradle.tasks.ParseSvgToComposeIconTask
import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.runtime.S2cConfig
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import okio.FileSystem
import org.gradle.api.file.DirectoryProperty

/**
 * App-level dependency graph for the Gradle plugin.
 *
 * Receives core S2C bindings via the contributed
 * [dev.tonholo.s2c.inject.SvgToComposeBindings] container and adds
 * Gradle-specific bindings such as the [PersistentOutputRegistry].
 *
 * Created once per task execution. Workers share this graph's [Processor.Factory]
 * via [S2cWorkerBridge][dev.tonholo.s2c.gradle.internal.service.S2cWorkerBridge]
 * and create isolated [Processor] instances with per-worker temp directories.
 */
@DependencyGraph(AppScope::class)
internal interface GradlePluginGraph {
    val processorFactory: Processor.Factory
    val persistentOutputRegistry: PersistentOutputRegistry

    /** Binds the concrete [GradleS2cConfig] to the [S2cConfig] interface. */
    @Binds
    val GradleS2cConfig.bindsConfig: S2cConfig

    @Provides
    @SingleIn(AppScope::class)
    fun providePersistentOutputRegistry(@BuildDirectory buildDirectory: DirectoryProperty): PersistentOutputRegistry {
        val registryFile = buildDirectory
            .file(PERSISTENT_REGISTRY_PATH)
            .get()
            .asFile
        return PersistentOutputRegistry(registryFile)
    }

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(
            @Provides config: GradleS2cConfig,
            @Provides logger: Logger,
            @Provides fileSystem: FileSystem,
            @Provides @BuildDirectory buildDirectory: DirectoryProperty,
        ): GradlePluginGraph
    }

    fun inject(task: ParseSvgToComposeIconTask)
}
