package dev.tonholo.s2c.serializer.domain

import dev.tonholo.s2c.domain.PathCommand
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/** [KSerializer] for [PathCommand], encoding each command by its enum name. */
internal object PathCommandSerializer : KSerializer<PathCommand> {
    override val descriptor = PrimitiveSerialDescriptor("PathCommand", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: PathCommand) = encoder.encodeString(value.name)
    override fun deserialize(decoder: Decoder): PathCommand = PathCommand.valueOf(decoder.decodeString())
}
