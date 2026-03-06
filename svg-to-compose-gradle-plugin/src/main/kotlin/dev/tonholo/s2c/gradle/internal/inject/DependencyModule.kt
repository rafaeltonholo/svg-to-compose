package dev.tonholo.s2c.gradle.internal.inject

import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.gradle.internal.cache.CacheManager
import dev.tonholo.s2c.gradle.internal.logger.Logger
import dev.tonholo.s2c.inject.S2cGraph
import dev.tonholo.s2c.inject.createS2cGraph
import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.logger.Logger
import okio.FileSystem
import okio.Path.Companion.toOkioPath
import org.gradle.api.file.DirectoryProperty
import java.io.File
import org.gradle.api.logging.Logger as GradleLogger

/**
 * Internal dependency module that manages and provides access to svg-to-compose processing
 * components.
 *
 * This module acts as a centralized container for dependency injection and management
 * of core svg-to-compose processing components. It lazily initializes the dependency
 * graph and provides access to essential processing components such as the processor,
 * file manager, logger, and cache manager.
 *
 * The module bridges Gradle-specific dependencies (like [GradleLogger] and [DirectoryProperty])
 * with the svg-to-compose processing system's internal dependencies, adapting external
 * dependencies to the expected internal interfaces.
 *
 * @param logger The Gradle logger used for logging operations, which gets adapted to the
 *  internal Logger interface.
 * @param buildDirectory The Gradle build directory property used by the cache manager for
 *  storing cached artifacts.
 * @param tempDirectory The temporary directory for storing intermediate files during
 *  processing operations.
 */
internal class DependencyModule(
    private val logger: GradleLogger,
    private val buildDirectory: DirectoryProperty,
    private val tempDirectory: File,
) {
    private val graph: S2cGraph by lazy {
        createS2cGraph(
            logger = Logger(logger),
            fileSystem = FileSystem.SYSTEM,
            tempDirectory = tempDirectory.toOkioPath(),
        )
    }

    val s2cLogger: Logger get() = graph.logger
    val processor: Processor get() = graph.processor
    val fileManager: FileManager get() = graph.fileManager
    val cacheManager: CacheManager by lazy {
        CacheManager(
            logger = graph.logger,
            fileManager = graph.fileManager,
            buildDirectory = buildDirectory,
        )
    }
}
