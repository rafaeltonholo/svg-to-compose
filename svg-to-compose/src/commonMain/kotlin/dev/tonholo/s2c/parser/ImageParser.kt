package dev.tonholo.s2c.parser

import com.rsicarelli.fakt.Fake
import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.androidPreviewImports
import dev.tonholo.s2c.domain.defaultImports
import dev.tonholo.s2c.domain.kmpPreviewImports
import dev.tonholo.s2c.domain.materialReceiverTypeImport
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.extensions.extension
import dev.tonholo.s2c.io.FileManager
import dev.zacsweers.metro.Inject
import okio.Path

/**
 * Parses SVG and Android Vector Drawable files into [IconFileContents].
 *
 * Composes file I/O (via [FileManager]) with content parsing
 * (via [ContentParser]) to convert vector files on disk into domain models.
 * The appropriate parser is selected from the injected [contentParsers] map
 * based on the file's [FileType].
 *
 * @property fileManager reads file content from the file system.
 * @property contentParsers maps each [FileType] to its [ContentParser] strategy.
 */
@Fake
interface ImageParser {
    /**
     * Parses a file into an [IconFileContents] domain model.
     *
     * Selects the appropriate [ContentParser] based on the file extension,
     * reads the file content, and delegates parsing.
     *
     * @param file path to the vector file.
     * @param iconName identifier for the generated icon.
     * @param config parser configuration.
     * @throws ExitProgramException if the file extension is not supported.
     * @return the parsed [IconFileContents].
     */
    fun parseToModel(file: Path, iconName: String, config: ParserConfig): IconFileContents
}

@Inject
class DefaultImageParser(
    private val fileManager: FileManager,
    private val contentParsers: Map<FileType, ContentParser>,
) : ImageParser {
    override fun parseToModel(file: Path, iconName: String, config: ParserConfig): IconFileContents {
        val extension = file.extension
        val fileType = FileType.entries.find { it.extension == extension }
        val parser = fileType?.let(contentParsers::get)
            ?: throw ExitProgramException(
                errorCode = ErrorCode.NotSupportedFileError,
                message = "invalid file extension ($extension).",
            )
        val content = fileManager.readContent(file)
        return parser.parse(content, iconName, config)
    }
}

internal fun createIconImports(nodes: List<ImageVectorNode>, config: ParserConfig): Set<String> = buildSet {
    addAll(defaultImports)
    addAll(previewImportsFor(config))
    if (config.addToMaterial) addAll(materialReceiverTypeImport)
    addAll(collectNodeImports(nodes))
}

private fun previewImportsFor(config: ParserConfig): Set<String> = when {
    config.noPreview -> emptySet()
    config.kmpPreview -> kmpPreviewImports
    else -> androidPreviewImports
}

private fun collectNodeImports(nodes: List<ImageVectorNode>): Set<String> = nodes
    .asSequence()
    .flatMap { node ->
        if (node is ImageVectorNode.ChunkFunction) node.nodes else listOf(node)
    }
    .flatMap { node ->
        if (node is ImageVectorNode.Group) {
            node.imports + node.commands.flatMap { it.imports }
        } else {
            node.imports
        }
    }
    .toSet()
