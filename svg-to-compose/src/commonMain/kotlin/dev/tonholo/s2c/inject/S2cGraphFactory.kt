package dev.tonholo.s2c.inject

import dev.tonholo.s2c.logger.Logger
import dev.zacsweers.metro.createGraphFactory
import okio.FileSystem

/**
 * Creates and configures an [SvgToComposeGraph] dependency graph instance.
 *
 * This function serves as a factory method for creating an [SvgToComposeGraph] using
 * the Metro dependency injection framework. It initializes the dependency
 * graph with the provided logger and file system configuration.
 *
 * @param logger The logger instance used for logging operations throughout the
 *  S2C processing system.
 * @param fileSystem The file system abstraction used for file I/O operations.
 * @return A fully configured [SvgToComposeGraph] instance with all dependencies resolved
 *  and ready for use.
 */
fun createS2cGraph(
    logger: Logger,
    fileSystem: FileSystem,
): SvgToComposeGraph = createGraphFactory<SvgToComposeGraph.Factory>().create(
    logger = logger,
    fileSystem = fileSystem,
)
