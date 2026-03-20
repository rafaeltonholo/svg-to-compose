package dev.tonholo.s2c.serializer.domain.compose

import dev.tonholo.s2c.domain.compose.StrokeJoin
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/** String-based [KSerializer] for [StrokeJoin]. */
internal object StrokeJoinSerializer : KSerializer<StrokeJoin> {
    override val descriptor = PrimitiveSerialDescriptor("StrokeJoin", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: StrokeJoin) = encoder.encodeString(value.value)
    override fun deserialize(decoder: Decoder): StrokeJoin =
        StrokeJoin(decoder.decodeString()) ?: error("Invalid StrokeJoin")
}
