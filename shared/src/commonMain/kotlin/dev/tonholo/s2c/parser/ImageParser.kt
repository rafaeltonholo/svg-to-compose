package dev.tonholo.s2c.parser

import dev.tonholo.s2c.domain.AndroidVector
import dev.tonholo.s2c.domain.AndroidVectorNode
import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.Svg
import dev.tonholo.s2c.domain.SvgNode
import dev.tonholo.s2c.domain.asNode
import dev.tonholo.s2c.domain.defaultImports
import dev.tonholo.s2c.domain.groupImports
import dev.tonholo.s2c.domain.materialContextProviderImport
import dev.tonholo.s2c.domain.previewImports
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.extensions.extension
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import nl.adaptivity.xmlutil.serialization.XML
import okio.FileSystem
import okio.Path

data class ParserConfig(
    val pkg: String,
    val theme: String,
    val optimize: Boolean,
    val contextProvider: String?,
    val addToMaterial: Boolean,
    val noPreview: Boolean,
    val makeInternal: Boolean,
    val minified: Boolean,
)

sealed class ImageParser(
    private val fileSystem: FileSystem,
) {
    val xmlParser by lazy {
        val module = SerializersModule {
            polymorphic(SvgNode::class) {
                subclass(SvgNode.Path::class, SvgNode.Path.serializer())
                subclass(SvgNode.Group::class, SvgNode.Group.serializer())
                subclass(SvgNode.Mask::class, SvgNode.Mask.serializer())
            }
            polymorphic(AndroidVectorNode::class) {
                subclass(AndroidVectorNode.Path::class, AndroidVectorNode.Path.serializer())
                subclass(AndroidVectorNode.Group::class, AndroidVectorNode.Group.serializer())
            }
        }
        XML(module) {
            defaultPolicy {
                autoPolymorphic = true
            }
        }
    }

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
            addAll(materialContextProviderImport)
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
            val svg = xmlParser.decodeFromString(
                deserializer = Svg.serializer(),
                string = content,
            )
            val viewBox = if (svg.viewBox != null) {
                svg.viewBox.split(" ").map { it.toFloat() }.toMutableList()
            } else {
                mutableListOf(svg.width.toFloat(), svg.height.toFloat())
            }
            val (viewportHeight, viewportWidth) = viewBox.removeLast() to viewBox.removeLast()
            val masks = svg.commands.filterIsInstance<SvgNode.Mask>()
            val nodes = svg.commands.mapNotNull {
                it.asNode(
                    width = svg.width.toFloat(),
                    height = svg.height.toFloat(),
                    minified = config.minified,
                    masks = masks,
                )
            }

            return IconFileContents(
                pkg = config.pkg,
                iconName = iconName,
                theme = config.theme,
                width = svg.width.toFloat(),
                height = svg.height.toFloat(),
                viewportWidth = viewportWidth,
                viewportHeight = viewportHeight,
                nodes = nodes,
                contextProvider = config.contextProvider,
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

            val androidVector = xmlParser.decodeFromString(
                deserializer = AndroidVector.serializer(),
                string = content,
            )

            val width = androidVector.width.removeSuffix("dp").toFloat()
            val height = androidVector.height.removeSuffix("dp").toFloat()
            val nodes = androidVector.nodes.map {
                it.asNode(
                    width = width,
                    height = height,
                    minified = config.minified,
                )
            }

            return IconFileContents(
                pkg = config.pkg,
                iconName = iconName,
                theme = config.theme,
                width = width,
                height = height,
                viewportWidth = androidVector.viewportWidth.toFloat(),
                viewportHeight = androidVector.viewportHeight.toFloat(),
                nodes = nodes,
                contextProvider = config.contextProvider,
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
                throw IllegalStateException(
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
