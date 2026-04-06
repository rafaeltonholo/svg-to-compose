package dev.tonholo.s2c.inject

import dev.tonholo.s2c.SvgToComposeContext
import dev.tonholo.s2c.SvgToComposeContextImpl
import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.parser.AvgContentParser
import dev.tonholo.s2c.parser.ContentParser
import dev.tonholo.s2c.parser.SvgContentParser
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import okio.FileSystem

/**
 * Reusable binding container for the S2C core processing system.
 *
 * Provides default bindings for [FileManager] and content parsers.
 * Contributed to [AppScope] so any [dev.zacsweers.metro.DependencyGraph]
 * scoped to [AppScope] automatically receives these bindings and can
 * override them if needed.
 */
@ContributesTo(AppScope::class)
@BindingContainer
interface SvgToComposeBindings {
    @Binds
    val SvgToComposeContextImpl.context: SvgToComposeContext

    companion object {
        @Provides
        @SingleIn(AppScope::class)
        fun provideFileManager(fileSystem: FileSystem, logger: Logger): FileManager = FileManager(fileSystem, logger)

        @Provides
        fun provideContentParsers(
            svgParser: SvgContentParser,
            avgParser: AvgContentParser,
        ): Map<FileType, ContentParser> = mapOf(
            FileType.Svg to svgParser,
            FileType.Avg to avgParser,
        )
    }
}
