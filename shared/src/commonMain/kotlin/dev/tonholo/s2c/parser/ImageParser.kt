package dev.tonholo.s2c.parser

import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.avg.AvgElementNode
import dev.tonholo.s2c.domain.avg.asNodes
import dev.tonholo.s2c.domain.defaultImports
import dev.tonholo.s2c.domain.groupImports
import dev.tonholo.s2c.domain.materialReceiverTypeImport
import dev.tonholo.s2c.domain.previewImports
import dev.tonholo.s2c.domain.svg.SvgElementNode
import dev.tonholo.s2c.domain.svg.asNodes
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.extensions.extension
import okio.FileSystem
import okio.Path

/**
 * A sealed class [ImageParser] that provides methods to parse SVG
 * and Android Vector Drawable Images and returns an [IconFileContents]
 * object.
 *
 * The class requires a [FileSystem] parameter.
 *
 * @property fileSystem a [FileSystem] instance that allows reading from the file system.
 * @constructor Creates an [ImageParser] object with the specified [FileSystem].
 *
 * @see [ImageParser.SvgParser]
 * @see [ImageParser.AndroidVectorParser]
 */
sealed class ImageParser(
    private val fileSystem: FileSystem,
) {
    abstract fun parse(
        file: Path,
        iconName: String,
        config: ParserConfig,
    ): IconFileContents

    protected fun readContent(file: Path): String {
        val content = fileSystem.read(file) {
            readUtf8()
        }

        return content
    }

    protected fun createImports(
        nodes: List<ImageVectorNode>,
        config: ParserConfig,
    ): Set<String> = buildSet {
        addAll(defaultImports)
        if (config.noPreview.not()) {
            addAll(previewImports)
        }
        if (nodes.any { it is ImageVectorNode.Group }) {
            addAll(groupImports)
        }
        if (config.addToMaterial) {
            addAll(materialReceiverTypeImport)
        }
        val pathImports = nodes
            .asSequence()
            .filterIsInstance<ImageVectorNode.Path>()
            .flatMap { it.pathImports() }
            .toSet()

        addAll(pathImports)
    }

    class SvgParser(
        fileSystem: FileSystem,
    ) : ImageParser(fileSystem) {
        override fun parse(
            file: Path,
            iconName: String,
            config: ParserConfig,
        ): IconFileContents {
            val content = readContent(file)

            val root = parse(content = content, fileType = FileType.Svg)
            val svg = root.children.single { it is SvgElementNode } as SvgElementNode
            val (_, _, viewportWidth, viewportHeight) = svg.viewBox
            val nodes = svg.asNodes(minified = config.minified)

            return IconFileContents(
                pkg = config.pkg,
                iconName = iconName,
                theme = config.theme,
                width = svg.width.toFloat(),
                height = svg.height.toFloat(),
                viewportWidth = viewportWidth,
                viewportHeight = viewportHeight,
                nodes = nodes,
                receiverType = config.receiverType,
                addToMaterial = config.addToMaterial,
                noPreview = config.noPreview,
                makeInternal = config.makeInternal,
                imports = createImports(nodes, config),
            )
        }
    }

    class AndroidVectorParser(
        fileSystem: FileSystem,
    ) : ImageParser(fileSystem) {
        override fun parse(
            file: Path,
            iconName: String,
            config: ParserConfig,
        ): IconFileContents {
            val content = readContent(file)

            val root = parse(content = content, fileType = FileType.Avg)
            val avg = root.children.single { it is AvgElementNode } as AvgElementNode
            val nodes = avg.asNodes(minified = config.minified)

            return IconFileContents(
                pkg = config.pkg,
                iconName = iconName,
                theme = config.theme,
                width = avg.width,
                height = avg.height,
                viewportWidth = avg.viewportWidth,
                viewportHeight = avg.viewportHeight,
                nodes = nodes,
                receiverType = config.receiverType,
                addToMaterial = config.addToMaterial,
                noPreview = config.noPreview,
                makeInternal = config.makeInternal,
                imports = createImports(nodes, config),
            )
        }
    }

    companion object {
        private const val SVG_EXTENSION = ".svg"
        private const val ANDROID_VECTOR_EXTENSION = ".xml"

        private lateinit var parsers: Map<String, ImageParser>

        operator fun invoke(fileSystem: FileSystem): Companion {
            parsers = mapOf(
                SVG_EXTENSION to SvgParser(fileSystem),
                ANDROID_VECTOR_EXTENSION to AndroidVectorParser(fileSystem),
            )

            return ImageParser // returning Companion to enable chain call.
        }

        fun parse(
            file: Path,
            iconName: String,
            config: ParserConfig,
        ): String {
            if (::parsers.isInitialized.not()) {
                error(
                    "Parsers not initialized. Call ImageParser(fileSystem) before calling ImageParser.parser()",
                )
            }

            val extension = file.extension
            return parsers[extension]?.parse(
                file = file,
                iconName = iconName,
                config = config,
            )?.materialize() ?: throw ExitProgramException(
                errorCode = ErrorCode.NotSupportedFileError,
                message = "invalid file extension ($extension)."
            )
        }
    }
}
