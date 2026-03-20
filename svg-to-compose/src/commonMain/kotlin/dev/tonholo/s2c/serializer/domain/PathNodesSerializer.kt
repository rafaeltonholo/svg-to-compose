package dev.tonholo.s2c.serializer.domain

import dev.tonholo.s2c.domain.PathCommand
import dev.tonholo.s2c.domain.PathNodes
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

private const val INDEX_COMMAND = 0
private const val INDEX_IS_RELATIVE = 1
private const val INDEX_MINIFIED = 2
private const val INDEX_VALUES = 3

/** Polymorphic [KSerializer] for the [PathNodes] sealed hierarchy. */
internal object PathNodesSerializer : KSerializer<PathNodes> {
    private val valuesSerializer = ListSerializer(String.serializer())

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("PathNodes") {
        element<String>("command")
        element<Boolean>("isRelative")
        element<Boolean>("minified")
        element("values", valuesSerializer.descriptor)
    }

    override fun serialize(encoder: Encoder, value: PathNodes) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, INDEX_COMMAND, value.command.name)
            encodeBooleanElement(descriptor, INDEX_IS_RELATIVE, value.isRelative)
            encodeBooleanElement(descriptor, INDEX_MINIFIED, value.minified)
            encodeSerializableElement(descriptor, INDEX_VALUES, valuesSerializer, value.values)
        }
    }

    override fun deserialize(decoder: Decoder): PathNodes {
        var commandName = ""
        var isRelative = false
        var minified = false
        var values: List<String> = emptyList()
        decoder.decodeStructure(descriptor) {
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    INDEX_COMMAND -> commandName = decodeStringElement(descriptor, INDEX_COMMAND)
                    INDEX_IS_RELATIVE -> isRelative = decodeBooleanElement(descriptor, INDEX_IS_RELATIVE)
                    INDEX_MINIFIED -> minified = decodeBooleanElement(descriptor, INDEX_MINIFIED)
                    INDEX_VALUES -> values = decodeSerializableElement(descriptor, INDEX_VALUES, valuesSerializer)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
        }
        val command = PathCommand.valueOf(commandName)
        return command.createNode(values, isRelative, minified)
    }
}
