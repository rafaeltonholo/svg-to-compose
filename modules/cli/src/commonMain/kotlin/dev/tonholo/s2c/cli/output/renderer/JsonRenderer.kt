package dev.tonholo.s2c.cli.output.renderer

import dev.tonholo.s2c.cli.output.renderer.json.JsonEvent
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.OutputRenderer
import kotlinx.serialization.json.Json

/**
 * Renders conversion events as JSONL (one JSON object per line).
 *
 * Designed for CI pipelines and programmatic consumers that need
 * structured, machine-parseable output. Each line is a self-contained
 * JSON object that can be parsed independently.
 *
 * @param writer function that receives each serialized JSON line.
 *               Defaults to [println] for stdout output.
 */
internal class JsonRenderer(private val writer: (String) -> Unit = ::println) : OutputRenderer {

    private val json = Json {
        encodeDefaults = false
    }

    override fun onEvent(event: ConversionEvent) {
        val jsonEvent = JsonEvent.from(event)
        val line = json.encodeToString(JsonEvent.serializer(), jsonEvent)
        writer(line)
    }
}
