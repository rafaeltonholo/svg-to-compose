package dev.tonholo.s2c.serializer.domain.compose

import dev.tonholo.s2c.domain.compose.GradientTileMode
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/** String-based [KSerializer] for [GradientTileMode]. */
internal object GradientTileModeSerializer : KSerializer<GradientTileMode> {
    override val descriptor = PrimitiveSerialDescriptor("GradientTileMode", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: GradientTileMode) = encoder.encodeString(value.value)
    override fun deserialize(decoder: Decoder): GradientTileMode =
        GradientTileMode(decoder.decodeString()) ?: error("Invalid GradientTileMode")
}
