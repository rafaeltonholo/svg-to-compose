package dev.tonholo.s2c.inject

import dev.tonholo.s2c.logger.Logger
import dev.zacsweers.metro.createGraphFactory
import okio.FileSystem
import okio.Path

/**
 * Creates and configures an [S2cGraph] dependency graph instance.
 *
 * This function serves as a factory method for creating an [S2cGraph] using
 * the Metro dependency injection framework. It initializes the dependency
 * graph with the provided logger, file system, and optional temporary directory
 * configuration.
 *
 * @param logger The logger instance used for logging operations throughout the
 *  S2C processing system.
 * @param fileSystem The file system abstraction used for file I/O operations.
 * @param tempDirectory An optional path to a temporary directory for storing
 *  intermediate files during processing.
 * @return A fully configured [S2cGraph] instance with all dependencies resolved
 *  and ready for use.
 */
fun createS2cGraph(
    logger: Logger,
    fileSystem: FileSystem,
    tempDirectory: Path? = null,
): S2cGraph = createGraphFactory<S2cGraph.Factory>().create(
    logger = logger,
    fileSystem = fileSystem,
    tempDirectory = tempDirectory,
)
