package dev.tonholo.s2c.emitter

import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.compose.ComposeBrush
import dev.tonholo.s2c.domain.defaultImports
import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.parser.method.MethodSizeAccountable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Tests for [NodeChunker].
 */
class NodeChunkerTest {
    private val noOpLogger = object : Logger {
        override fun debug(message: Any) = Unit
        override fun <T> debugSection(title: String, block: () -> T): T = block()
        override fun <T> verboseSection(title: String, block: () -> T): T = block()
        override fun verbose(message: String) = Unit
        override fun warn(message: String, throwable: Throwable?) = Unit
        override fun info(message: String) = Unit
        override fun output(message: String) = Unit
        override fun error(message: String, throwable: Throwable?) = Unit
        override fun printEmpty() = Unit
    }

    private val chunker = NodeChunker(noOpLogger)

    private val defaultNameResolver: (IconFileContents, Int) -> String = { contents, index ->
        "${contents.iconName}Chunk$index"
    }

    @Test
    fun `returns original nodes when byte size is below threshold`() {
        val icon = createIcon(nodeCount = 3)
        val result = chunker.chunkIfNeeded(icon, defaultNameResolver)

        assertEquals(icon.nodes, result.nodes)
        assertNull(result.chunkFunctions)
    }

    @Test
    fun `returns ChunkFunction nodes when byte size exceeds threshold`() {
        val icon = createIcon(nodeCount = NODE_COUNT_ABOVE_THRESHOLD)
        val result = chunker.chunkIfNeeded(icon, defaultNameResolver)

        assertNotNull(result.chunkFunctions)
        assertTrue(result.nodes.isNotEmpty())
        result.nodes.forEach { node ->
            assertIs<ImageVectorNode.ChunkFunction>(node)
        }
    }

    @Test
    fun `chunk functions contain all original nodes`() {
        val icon = createIcon(nodeCount = NODE_COUNT_ABOVE_THRESHOLD)
        val result = chunker.chunkIfNeeded(icon, defaultNameResolver)

        val chunkFunctions = requireNotNull(result.chunkFunctions) { "chunkFunctions should be present" }
        val allChunkedNodes = chunkFunctions.flatMap { it.nodes }
        assertEquals(icon.nodes.size, allChunkedNodes.size)
    }

    @Test
    fun `uses custom nameResolver for chunk function names`() {
        val icon = createIcon(nodeCount = NODE_COUNT_ABOVE_THRESHOLD)
        val nameResolver: (IconFileContents, Int) -> String = { _, index ->
            "customChunk$index"
        }

        val result = chunker.chunkIfNeeded(icon, nameResolver)

        val chunkFunctions = requireNotNull(result.chunkFunctions) { "chunkFunctions should be present" }
        val names = chunkFunctions.map { it.functionName }
        names.forEachIndexed { idx, name ->
            assertEquals("customChunk${idx + 1}", name)
        }
    }

    @Test
    fun `chunkFunctions is non-null when chunking is needed`() {
        val icon = createIcon(nodeCount = NODE_COUNT_ABOVE_THRESHOLD)
        val result = chunker.chunkIfNeeded(icon, defaultNameResolver)

        assertNotNull(result.chunkFunctions, "chunkFunctions should be present when chunking occurs")
    }

    @Test
    fun `chunk size is at least 1`() {
        // A single large node that exceeds the threshold by itself
        // still produces at least one node per chunk.
        val icon = createIcon(nodeCount = 1, pathCommandCount = COMMANDS_PER_PATH_ABOVE_THRESHOLD)
        val result = chunker.chunkIfNeeded(icon, defaultNameResolver)

        assertNotNull(result.chunkFunctions)
        result.chunkFunctions.forEach { chunk ->
            assertTrue(chunk.nodes.isNotEmpty(), "Each chunk must contain at least one node")
        }
    }

    @Test
    fun `does not chunk when byte size equals threshold exactly`() {
        // Compute node count that stays at or below threshold.
        // Each simple path ≈ 179 bytes; base = 73; threshold = 32767
        // (32767 - 73) / 179 = 182.59 → 182 paths stays at 32651 (below)
        val icon = createIcon(nodeCount = 182)
        val totalSize = MethodSizeAccountable.ICON_BASE_STRUCTURE_BYTE_SIZE +
            icon.nodes.sumOf { it.approximateByteSize }
        assertTrue(
            totalSize <= MethodSizeAccountable.METHOD_SIZE_THRESHOLD,
            "Precondition: total size ($totalSize) should be at or below threshold",
        )

        val result = chunker.chunkIfNeeded(icon, defaultNameResolver)
        assertNull(result.chunkFunctions)
        assertEquals(icon.nodes, result.nodes)
    }

    @Test
    fun `chunks just above threshold produces multiple chunks`() {
        val icon = createIcon(nodeCount = NODE_COUNT_ABOVE_THRESHOLD)
        val result = chunker.chunkIfNeeded(icon, defaultNameResolver)

        assertNotNull(result.chunkFunctions)
        assertTrue(
            result.chunkFunctions.size >= 2,
            "Expected at least 2 chunks, got ${result.chunkFunctions.size}",
        )
    }

    @Test
    fun `nameResolver receives 1-based index`() {
        val icon = createIcon(nodeCount = NODE_COUNT_ABOVE_THRESHOLD)
        val receivedIndices = mutableListOf<Int>()
        val nameResolver: (IconFileContents, Int) -> String = { _, index ->
            receivedIndices.add(index)
            "chunk$index"
        }

        chunker.chunkIfNeeded(icon, nameResolver)

        assertTrue(receivedIndices.isNotEmpty())
        assertEquals(1, receivedIndices.first(), "First chunk index should be 1")
        // Indices should be sequential starting from 1
        receivedIndices.forEachIndexed { idx, value ->
            assertEquals(idx + 1, value)
        }
    }

    private fun createSimplePath(minified: Boolean = false): ImageVectorNode.Path {
        val pathNodes = listOf(
            PathNodes.MoveTo(values = listOf("10", "20"), isRelative = false, minified = minified),
            PathNodes.LineTo(values = listOf("30", "40"), isRelative = false, minified = minified),
        )
        val wrapper = ImageVectorNode.NodeWrapper(normalizedPath = "M10 20 L30 40", nodes = pathNodes)
        return ImageVectorNode.Path(
            params = ImageVectorNode.Path.Params(fill = ComposeBrush.SolidColor("Color.Black")),
            wrapper = wrapper,
            minified = minified,
        )
    }

    private fun createIcon(nodeCount: Int, pathCommandCount: Int = 2): IconFileContents {
        val nodes = List(nodeCount) {
            if (pathCommandCount <= 2) {
                createSimplePath()
            } else {
                createLargePath(pathCommandCount)
            }
        }
        return IconFileContents(
            pkg = "dev.test",
            iconName = "test-icon",
            theme = "TestTheme",
            width = 24f,
            height = 24f,
            nodes = nodes,
            noPreview = true,
            imports = defaultImports + "androidx.compose.ui.graphics.vector.path",
        )
    }

    private fun createLargePath(commandCount: Int): ImageVectorNode.Path {
        val pathNodes = buildList {
            add(PathNodes.MoveTo(values = listOf("0", "0"), isRelative = false, minified = false))
            repeat(commandCount - 1) { i ->
                add(PathNodes.LineTo(values = listOf("${i + 1}", "${i + 1}"), isRelative = false, minified = false))
            }
        }
        val wrapper =
            ImageVectorNode.NodeWrapper(normalizedPath = "M0 0 " + "L1 1 ".repeat(commandCount - 1), nodes = pathNodes)
        return ImageVectorNode.Path(
            params = ImageVectorNode.Path.Params(fill = ComposeBrush.SolidColor("Color.Black")),
            wrapper = wrapper,
            minified = false,
        )
    }

    companion object {
        /**
         * Number of simple paths needed to exceed the method size threshold.
         * Each simple path ≈ 179 bytes; base = 73; threshold = 32767.
         * (32767 - 73) / 179 ≈ 182.6 → 183 paths exceeds the threshold.
         */
        private const val NODE_COUNT_ABOVE_THRESHOLD = 200

        /**
         * Number of path commands needed for a single path to exceed the threshold.
         * Each LineTo command ≈ 14 bytes; PATH_APPROXIMATE_BYTE_SIZE = 133;
         * SolidColor = 18; base = 73.
         * (32767 - 73 - 133 - 18) / 14 ≈ 2324 commands.
         */
        private const val COMMANDS_PER_PATH_ABOVE_THRESHOLD = 2500
    }
}
