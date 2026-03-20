package dev.tonholo.s2c.serializer.domain.compose

import dev.tonholo.s2c.domain.compose.PathFillType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/** String-based [KSerializer] for [PathFillType]. */
internal object PathFillTypeSerializer : KSerializer<PathFillType> {
    override val descriptor = PrimitiveSerialDescriptor("PathFillType", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: PathFillType) = encoder.encodeString(value.value)
    override fun deserialize(decoder: Decoder): PathFillType =
        PathFillType(decoder.decodeString()) ?: error("Invalid PathFillType")
}
