package dev.tonholo.s2c.emitter

import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.parser.method.MethodSizeAccountable
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * Result of [NodeChunker.chunkIfNeeded].
 *
 * @property nodes The (possibly chunked) list of nodes to emit.
 * @property chunkFunctions The chunk function nodes extracted from [nodes],
 *   or `null` when no chunking was needed.
 */
internal data class ChunkResult(
    val nodes: List<ImageVectorNode>,
    val chunkFunctions: List<ImageVectorNode.ChunkFunction>?,
)

/**
 * Splits an icon's nodes into [ImageVectorNode.ChunkFunction]s when the
 * estimated bytecode size exceeds [MethodSizeAccountable.METHOD_SIZE_THRESHOLD].
 *
 * This is a shared concern used by both the default and template-based emitters
 * to avoid the JVM 64 KiB method size limit.
 *
 * @property logger The logger instance for warning output.
 */
internal class NodeChunker(private val logger: Logger) {
    /**
     * Splits [contents]' nodes into chunks when the estimated byte size exceeds
     * the method size threshold.
     *
     * @param contents The icon file contents whose nodes may need chunking.
     * @param nameResolver Provides the function name for a chunk given the
     *   icon contents and the 1-based chunk index.
     * @return A [ChunkResult] with the (possibly chunked) nodes and extracted
     *   chunk functions.
     */
    fun chunkIfNeeded(
        contents: IconFileContents,
        nameResolver: (contents: IconFileContents, index: Int) -> String,
    ): ChunkResult {
        val byteSize = MethodSizeAccountable.ICON_BASE_STRUCTURE_BYTE_SIZE + contents.nodes
            .sumOf { it.approximateByteSize }
        val shouldChunk = byteSize > MethodSizeAccountable.METHOD_SIZE_THRESHOLD

        if (!shouldChunk) {
            return ChunkResult(nodes = contents.nodes, chunkFunctions = null)
        }

        var i = 1
        val chunks = ceil(byteSize.toFloat() / MethodSizeAccountable.METHOD_SIZE_THRESHOLD)
            .roundToInt()
        val chunkSize = max(1, contents.nodes.size / chunks)

        logger.warn(
            "Potential large icon detected. Splitting icon's content in $chunks chunks to avoid " +
                "compilation issues. However, that won't affect the performance of displaying this icon.",
        )

        val chunkFunctions = contents.nodes.chunked(chunkSize) { chunk ->
            ImageVectorNode.ChunkFunction(
                functionName = nameResolver(contents, i++),
                nodes = chunk.toList(),
            )
        }

        return ChunkResult(nodes = chunkFunctions, chunkFunctions = chunkFunctions)
    }
}
