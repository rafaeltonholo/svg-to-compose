package dev.tonholo.s2c.parser

import dev.tonholo.s2c.domain.AndroidVector
import dev.tonholo.s2c.domain.AndroidVectorNode
import dev.tonholo.s2c.domain.IconFileContents
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
            val imports = defaultImports +
                (if (config.noPreview) setOf() else previewImports) +
                (if (svg.commands.any { it is SvgNode.Group }) groupImports else setOf()) +
                if (config.addToMaterial) materialContextProviderImport else setOf()
            val masks = svg.commands.filterIsInstance<SvgNode.Mask>()

            return IconFileContents(
                pkg = config.pkg,
                iconName = iconName,
                theme = config.theme,
                width = svg.width.toFloat(),
                height = svg.height.toFloat(),
                viewportWidth = viewportWidth,
                viewportHeight = viewportHeight,
                nodes = svg.commands.mapNotNull {
                    it.asNode(
                        width = svg.width.toFloat(),
                        height = svg.height.toFloat(),
                        minified = config.minified,
                        masks = masks,
                    )
                },
                contextProvider = config.contextProvider,
                addToMaterial = config.addToMaterial,
                noPreview = config.noPreview,
                makeInternal = config.makeInternal,
                imports = imports,
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
            val imports = defaultImports +
                (if (config.noPreview) setOf() else previewImports) +
                (if (androidVector.nodes.any { it is AndroidVectorNode.Group }) groupImports else setOf()) +
                if (config.addToMaterial) materialContextProviderImport else setOf()

            return IconFileContents(
                pkg = config.pkg,
                iconName = iconName,
                theme = config.theme,
                width = androidVector.width.removeSuffix("dp").toFloat(),
                height = androidVector.height.removeSuffix("dp").toFloat(),
                viewportWidth = androidVector.viewportWidth.toFloat(),
                viewportHeight = androidVector.viewportHeight.toFloat(),
                nodes = androidVector.nodes.map { it.asNode(minified = config.minified) },
                contextProvider = config.contextProvider,
                addToMaterial = config.addToMaterial,
                noPreview = config.noPreview,
                makeInternal = config.makeInternal,
                imports = imports,
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
