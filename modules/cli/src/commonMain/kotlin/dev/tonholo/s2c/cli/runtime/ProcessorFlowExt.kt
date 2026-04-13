package dev.tonholo.s2c.cli.runtime

import dev.tonholo.s2c.AppDefaults
import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.parser.IconMapperFn
import dev.tonholo.s2c.parser.ParserConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.runBlocking

/**
 * Suspend-aware bridge for [Processor.run].
 *
 * Core's [Processor.run] takes a non-suspend `onEvent` callback because the
 * core module avoids a `kotlinx.coroutines` dependency (Gradle plugin compat).
 * This extension bridges that callback to a [suspend] lambda via [runBlocking],
 * which is safe here because [Processor.run] is already blocking the thread.
 *
 * The caller must ensure this is invoked on an appropriate dispatcher since
 * [Processor.run] is blocking.
 *
 * When parallel file processing is added, this function's internals can be
 * replaced with a coroutine-per-file implementation while keeping the same
 * API surface.
 */
internal fun Processor.runSuspending(
    path: String,
    output: String,
    config: ParserConfig,
    recursive: Boolean,
    maxDepth: Int = AppDefaults.MAX_RECURSIVE_DEPTH,
    mapIconName: IconMapperFn? = null,
    onEvent: suspend (ConversionEvent) -> Unit,
) {
    run(
        path = path,
        output = output,
        config = config,
        recursive = recursive,
        maxDepth = maxDepth,
        mapIconName = mapIconName,
        onEvent = { event -> runBlocking { onEvent(event) } },
    )
}

/**
 * Wraps [Processor.run] as a cold [Flow] of [ConversionEvent]s.
 *
 * Delegates to [runSuspending] so events are delivered via [send] with
 * backpressure support.
 */
internal fun Processor.runAsFlow(
    path: String,
    output: String,
    config: ParserConfig,
    recursive: Boolean,
    maxDepth: Int = AppDefaults.MAX_RECURSIVE_DEPTH,
    mapIconName: IconMapperFn? = null,
): Flow<ConversionEvent> = channelFlow {
    runSuspending(
        path = path,
        output = output,
        config = config,
        recursive = recursive,
        maxDepth = maxDepth,
        mapIconName = mapIconName,
        onEvent = { event -> send(event) },
    )
}
