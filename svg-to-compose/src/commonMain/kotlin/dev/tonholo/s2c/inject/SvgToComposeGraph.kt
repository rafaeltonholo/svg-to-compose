package dev.tonholo.s2c.inject

import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.parser.AvgContentParser
import dev.tonholo.s2c.parser.ContentParser
import dev.tonholo.s2c.parser.SvgContentParser
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import okio.FileSystem

/**
 * Dependency graph interface for the S2C (Source-to-Code) processing system.
 *
 * The graph uses the @[DependencyGraph] annotation to enable automatic dependency
 * resolution and injection for the components it declares. Dependencies are lazily
 * initialized and provided through factory methods when needed.
 *
 * Use the nested @[DependencyGraph.Factory] interface to instantiate this dependency
 * graph with the required external dependencies such as logger, file system, and
 * optional temporary directory path.
 */
@DependencyGraph(AppScope::class)
interface SvgToComposeGraph {
    val processorFactory: Processor.Factory
    val fileManager: FileManager
    val logger: Logger

    @Provides
    @SingleIn(AppScope::class)
    fun provideFileManager(fileSystem: FileSystem, logger: Logger): FileManager = FileManager(fileSystem, logger)

    @Provides
    fun provideContentParsers(svgParser: SvgContentParser, avgParser: AvgContentParser): Map<FileType, ContentParser> =
        mapOf(
            FileType.Svg to svgParser,
            FileType.Avg to avgParser,
        )

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(@Provides logger: Logger, @Provides fileSystem: FileSystem): SvgToComposeGraph
    }
}
