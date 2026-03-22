package dev.tonholo.s2c.website.worker.inject

import dev.tonholo.s2c.Converter
import dev.tonholo.s2c.DefaultConverter
import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.emitter.CodeEmitterFactory
import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.parser.AvgContentParser
import dev.tonholo.s2c.parser.ContentParser
import dev.tonholo.s2c.parser.SvgContentParser
import dev.tonholo.s2c.website.worker.ConsoleLogger
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import kotlinx.serialization.json.Json

/** Metro dependency graph providing [Converter], [CodeEmitterFactory], and [Json] instances for the worker. */
@DependencyGraph
internal interface WorkerGraph {
    val converter: Converter
    val codeEmitterFactory: CodeEmitterFactory
    val contentParsers: Map<FileType, ContentParser>
    val json: Json

    @Binds
    val ConsoleLogger.bind: Logger

    @Binds
    val DefaultConverter.bind: Converter

    @Provides
    @EnableDebugQualifier
    val enableDebug: Boolean get() = false

    @Provides
    fun provideContentParsers(svgParser: SvgContentParser, avgParser: AvgContentParser): Map<FileType, ContentParser> =
        mapOf(
            FileType.Svg to svgParser,
            FileType.Avg to avgParser,
        )

    @Provides
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }
}
