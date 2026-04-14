package dev.tonholo.s2c.website.worker.inject

import dev.tonholo.s2c.Converter
import dev.tonholo.s2c.DefaultConverter
import dev.tonholo.s2c.emitter.CodeEmitterFactory
import dev.tonholo.s2c.inject.SvgToComposeBindings
import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.website.worker.ConsoleLogger
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import kotlinx.serialization.json.Json

/** Metro dependency graph providing [Converter], [CodeEmitterFactory], and [Json] instances for the worker. */
@DependencyGraph
internal interface WorkerGraph : SvgToComposeBindings {
    val converter: Converter
    val json: Json

    @Binds
    val ConsoleLogger.bind: Logger

    @Binds
    val DefaultConverter.bind: Converter

    @Provides
    @EnableDebugQualifier
    val enableDebug: Boolean get() = false

    @Provides
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }
}
