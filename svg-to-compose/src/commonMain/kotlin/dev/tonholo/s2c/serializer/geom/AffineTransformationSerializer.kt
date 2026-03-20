package dev.tonholo.s2c.serializer.geom

import dev.tonholo.s2c.geom.AffineTransformation
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put

/**
 * [KSerializer] for [AffineTransformation] that persists each subtype as a
 * JSON object with a discriminating `type` field and subtype-specific properties.
 */
internal object AffineTransformationSerializer : KSerializer<AffineTransformation> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("AffineTransformation") {
        element<String>("type")
    }

    override fun serialize(encoder: Encoder, value: AffineTransformation) {
        val jsonEncoder = encoder as JsonEncoder
        val json = buildJsonObject {
            when (value) {
                is AffineTransformation.Identity -> put("type", "Identity")

                is AffineTransformation.Translate -> {
                    put("type", "Translate")
                    put("tx", value.tx)
                    put("ty", value.ty)
                }

                is AffineTransformation.Rotate -> {
                    put("type", "Rotate")
                    put("angle", value.angle)
                    put("centerX", value.centerX)
                    put("centerY", value.centerY)
                }

                is AffineTransformation.Scale -> {
                    put("type", "Scale")
                    put("sx", value.sx)
                    put("sy", value.sy)
                }

                is AffineTransformation.SkewX -> {
                    put("type", "SkewX")
                    put("angle", value.angle)
                }

                is AffineTransformation.SkewY -> {
                    put("type", "SkewY")
                    put("angle", value.angle)
                }

                is AffineTransformation.Matrix -> {
                    put("type", "Matrix")
                    put(
                        "matrix",
                        buildJsonArray {
                            for (row in value.matrix) {
                                add(buildJsonArray { for (v in row) add(kotlinx.serialization.json.JsonPrimitive(v)) })
                            }
                        },
                    )
                }
            }
        }
        jsonEncoder.encodeJsonElement(json)
    }

    override fun deserialize(decoder: Decoder): AffineTransformation {
        val jsonDecoder = decoder as JsonDecoder
        val json = jsonDecoder.decodeJsonElement().jsonObject
        return when (val type = json["type"]?.jsonPrimitive?.content) {
            "Identity" -> AffineTransformation.Identity

            "Translate" -> AffineTransformation.Translate(
                tx = json.getValue("tx").jsonPrimitive.double,
                ty = json.getValue("ty").jsonPrimitive.double,
            )

            "Rotate" -> AffineTransformation.Rotate(
                angle = json.getValue("angle").jsonPrimitive.double,
                centerX = json.getValue("centerX").jsonPrimitive.double,
                centerY = json.getValue("centerY").jsonPrimitive.double,
            )

            "Scale" -> AffineTransformation.Scale(
                sx = json.getValue("sx").jsonPrimitive.double,
                sy = json.getValue("sy").jsonPrimitive.double,
            )

            "SkewX" -> AffineTransformation.SkewX(
                angle = json.getValue("angle").jsonPrimitive.double,
            )

            "SkewY" -> AffineTransformation.SkewY(
                angle = json.getValue("angle").jsonPrimitive.double,
            )

            "Matrix" -> {
                val rows = json.getValue("matrix").jsonArray.map { row ->
                    row.jsonArray.map { it.jsonPrimitive.double }.toDoubleArray()
                }
                @Suppress("SpreadOperator")
                AffineTransformation.Matrix(*rows.toTypedArray())
            }

            else -> error("Unknown AffineTransformation type: $type")
        }
    }
}
