package dev.tonholo.s2c.parser

import dev.tonholo.s2c.domain.*
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
            addToMaterial: Boolean
        ): IconFileContents {
            val content = readContent(file)
            val svg = xmlParser.decodeFromString(
                deserializer = Svg.serializer(),
                string = content,
            )
            val viewBox = svg.viewBox.split(" ").toMutableList()
            val (viewportHeight, viewportWidth) = viewBox.removeLast() to viewBox.removeLast()
            val imports = defaultImports +
                    (if (svg.commands.any { it is SvgNode.Group }) groupImports else setOf()) +
                    if (addToMaterial) materialContextProviderImport else setOf()

            return IconFileContents(
                pkg = pkg,
                iconName = iconName,
                theme = theme,
                width = svg.width.toFloat(),
                height = svg.height.toFloat(),
                viewportWidth = viewportWidth.toFloat(),
                viewportHeight = viewportHeight.toFloat(),
                nodes = svg.commands.mapNotNull { it.asNode(svg) },
                contextProvider = contextProvider,
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
            addToMaterial: Boolean
        ): IconFileContents {
            val content = readContent(file)

            val androidVector = xmlParser.decodeFromString(
                deserializer = AndroidVector.serializer(),
                string = content,
            )
            val imports = defaultImports +
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
            addToMaterial: Boolean
        ): String {
            val extension = file.extension
            return parsers[extension]?.parse(
                file = file,
                iconName = iconName,
                pkg = pkg,
                theme = theme,
                contextProvider = contextProvider,
                addToMaterial = addToMaterial,
            )?.materialize() ?: throw ExitProgramException(
                errorCode = ErrorCode.NotSupportedFileError,
                message = "invalid file extension ($extension)."
            )
        }
    }
}
