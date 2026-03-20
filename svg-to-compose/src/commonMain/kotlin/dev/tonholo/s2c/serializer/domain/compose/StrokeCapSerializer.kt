package dev.tonholo.s2c.serializer.domain.compose

import dev.tonholo.s2c.domain.compose.StrokeCap
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/** String-based [KSerializer] for [StrokeCap]. */
internal object StrokeCapSerializer : KSerializer<StrokeCap> {
    override val descriptor = PrimitiveSerialDescriptor("StrokeCap", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: StrokeCap) = encoder.encodeString(value.value)
    override fun deserialize(decoder: Decoder): StrokeCap =
        StrokeCap(decoder.decodeString()) ?: error("Invalid StrokeCap")
}
