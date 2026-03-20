package dev.tonholo.s2c.serializer.domain.compose

import dev.tonholo.s2c.domain.compose.ComposeBrush
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/** Polymorphic [KSerializer] for the [ComposeBrush] sealed hierarchy. */
internal object ComposeBrushSerializer : KSerializer<ComposeBrush> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ComposeBrush")

    private val lenientJson = Json { ignoreUnknownKeys = true }

    override fun serialize(encoder: Encoder, value: ComposeBrush) {
        val jsonEncoder = encoder as JsonEncoder
        when (value) {
            is ComposeBrush.SolidColor -> jsonEncoder.encodeJsonElement(JsonPrimitive(value.value))

            is ComposeBrush.Gradient.Linear ->
                jsonEncoder.encodeJsonElement(
                    Json.encodeToJsonElement(
                        ComposeBrush.Gradient.Linear.serializer(),
                        value,
                    ).let { element ->
                        JsonObject(element.jsonObject + ("type" to JsonPrimitive("Linear")))
                    },
                )

            is ComposeBrush.Gradient.Radial ->
                jsonEncoder.encodeJsonElement(
                    Json.encodeToJsonElement(
                        ComposeBrush.Gradient.Radial.serializer(),
                        value,
                    ).let { element ->
                        JsonObject(element.jsonObject + ("type" to JsonPrimitive("Radial")))
                    },
                )

            is ComposeBrush.Gradient.Sweep ->
                jsonEncoder.encodeJsonElement(
                    Json.encodeToJsonElement(
                        ComposeBrush.Gradient.Sweep.serializer(),
                        value,
                    ).let { element ->
                        JsonObject(element.jsonObject + ("type" to JsonPrimitive("Sweep")))
                    },
                )
        }
    }

    override fun deserialize(decoder: Decoder): ComposeBrush {
        val jsonDecoder = decoder as JsonDecoder
        return when (val element = jsonDecoder.decodeJsonElement()) {
            is JsonPrimitive -> ComposeBrush.SolidColor(element.content)

            is JsonObject -> {
                val type = element["type"]?.jsonPrimitive?.content
                when (type) {
                    "Linear" -> lenientJson.decodeFromJsonElement(ComposeBrush.Gradient.Linear.serializer(), element)
                    "Radial" -> lenientJson.decodeFromJsonElement(ComposeBrush.Gradient.Radial.serializer(), element)
                    "Sweep" -> lenientJson.decodeFromJsonElement(ComposeBrush.Gradient.Sweep.serializer(), element)
                    else -> error("Unknown ComposeBrush gradient type: $type")
                }
            }

            else -> error("Unexpected JSON element for ComposeBrush: $element")
        }
    }
}
