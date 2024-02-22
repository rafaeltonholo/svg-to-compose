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

sealed class ImageParser {
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
        pkg: String,
        theme: String,
        contextProvider: String?,
        addToMaterial: Boolean,
        noPreview: Boolean,
    ): IconFileContents

    protected fun readContent(file: Path): String {
        val content = FileSystem.SYSTEM.read(file) {
            readUtf8()
        }

        return content
    }

    data object SvgParser : ImageParser() {
        override fun parse(
            file: Path,
            iconName: String,
            pkg: String,
            theme: String,
            contextProvider: String?,
            addToMaterial: Boolean,
            noPreview: Boolean,
        ): IconFileContents {
            val content = readContent(file)
            val svg = xmlParser.decodeFromString(
                deserializer = Svg.serializer(),
                string = content,
            )
            val viewBox = if (svg.viewBox != null ) {
                svg.viewBox.split(" ").map { it.toFloat() }.toMutableList()
            } else {
                mutableListOf(svg.width.toFloat(), svg.height.toFloat())
            }
            val (viewportHeight, viewportWidth) = viewBox.removeLast() to viewBox.removeLast()
            val imports = defaultImports +
                    (if (noPreview) setOf() else previewImports) +
                    (if (svg.commands.any { it is SvgNode.Group }) groupImports else setOf()) +
                    if (addToMaterial) materialContextProviderImport else setOf()
            val masks = svg.commands.filterIsInstance<SvgNode.Mask>()

            return IconFileContents(
                pkg = pkg,
                iconName = iconName,
                theme = theme,
                width = svg.width.toFloat(),
                height = svg.height.toFloat(),
                viewportWidth = viewportWidth,
                viewportHeight = viewportHeight,
                nodes = svg.commands.mapNotNull { it.asNode(masks = masks) },
                contextProvider = contextProvider,
                addToMaterial = addToMaterial,
                noPreview = noPreview,
                imports = imports,
            )
        }
    }

    data object AndroidVectorParser : ImageParser() {
        override fun parse(
            file: Path,
            iconName: String,
            pkg: String,
            theme: String,
            contextProvider: String?,
            addToMaterial: Boolean,
            noPreview: Boolean,
        ): IconFileContents {
            val content = readContent(file)

            val androidVector = xmlParser.decodeFromString(
                deserializer = AndroidVector.serializer(),
                string = content,
            )
            val imports = defaultImports +
                    (if (noPreview) setOf() else previewImports) +
                    (if (androidVector.nodes.any { it is AndroidVectorNode.Group }) groupImports else setOf()) +
                    if (addToMaterial) materialContextProviderImport else setOf()

            return IconFileContents(
                pkg = pkg,
                iconName = iconName,
                theme = theme,
                width = androidVector.width.removeSuffix("dp").toFloat(),
                height = androidVector.height.removeSuffix("dp").toFloat(),
                viewportWidth = androidVector.viewportWidth.toFloat(),
                viewportHeight = androidVector.viewportHeight.toFloat(),
                nodes = androidVector.nodes.map { it.asNode() },
                contextProvider = contextProvider,
                addToMaterial = addToMaterial,
                noPreview = noPreview,
                imports = imports,
            )
        }

    }

    companion object {
        private const val SVG_EXTENSION = ".svg"
        private const val ANDROID_VECTOR_EXTENSION = ".xml"

        private val parsers = mapOf(
            SVG_EXTENSION to SvgParser,
            ANDROID_VECTOR_EXTENSION to AndroidVectorParser
        )

        fun parse(
            file: Path,
            iconName: String,
            pkg: String,
            theme: String,
            contextProvider: String?,
            addToMaterial: Boolean,
            noPreview: Boolean,
        ): String {
            val extension = file.extension
            return parsers[extension]?.parse(
                file = file,
                iconName = iconName,
                pkg = pkg,
                theme = theme,
                contextProvider = contextProvider,
                addToMaterial = addToMaterial,
                noPreview = noPreview,
            )?.materialize() ?: throw ExitProgramException(
                errorCode = ErrorCode.NotSupportedFileError,
                message = "invalid file extension ($extension)."
            )
        }
    }
}
