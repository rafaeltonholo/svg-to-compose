package dev.tonholo.s2c.parser

import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.androidPreviewImports
import dev.tonholo.s2c.domain.avg.AvgRootNode
import dev.tonholo.s2c.domain.avg.asNodes
import dev.tonholo.s2c.domain.defaultImports
import dev.tonholo.s2c.domain.kmpPreviewImports
import dev.tonholo.s2c.domain.materialReceiverTypeImport
import dev.tonholo.s2c.domain.svg.SvgRootNode
import dev.tonholo.s2c.domain.svg.asNodes
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.extensions.extension
import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.parser.ImageParser.Companion.invoke
import dev.tonholo.s2c.parser.ImageParser.Companion.parse
import okio.Path

/**
 * A sealed class [ImageParser] that provides methods to parse SVG
 * and Android Vector Drawable Images and returns an [IconFileContents]
 * object.
 *
 * The class requires a [FileManager] parameter.
 *
 * @property fileManager a [FileManager] instance that allows reading from the file system.
 * @constructor Creates an [ImageParser] object with the specified [FileManager].
 *
 * @see [ImageParser.SvgParser]
 * @see [ImageParser.AndroidVectorParser]
 */
sealed class ImageParser(
    protected val fileManager: FileManager,
) {
    /**
     * Parse a SVG/AVG icon and creates a [IconFileContents] object containing
     * all the required information to generate the Jetpack Compose Icon.
     *
     * @param file This is a [Path] object that points towards the file that
     * needs to be parsed into an icon.
     * @param iconName This is a String parameter that serves as the identifier
     * for the icon that will be generated after parsing the file.
     * @param config An instance of the [ParserConfig] class, which contains
     * configurations required for parsing the file into an icon.
     * @return An [IconFileContents] object instance which contains information
     * about the icon parsed from the file.
     */
    abstract fun parse(
        file: Path,
        iconName: String,
        config: ParserConfig,
    ): IconFileContents

    /**
     * This function generates the Icon's imports for the provided list of
     * [ImageVectorNode] and configuration.
     *
     * It first adds all the default imports to the returned set.
     *
     * Then, based on the provided configuration and [ImageVectorNode] list,
     * other imports might also be added:
     * - If the [ParserConfig.noPreview] property of the [config] parameter
     * is `false`, the preview imports will be added too.
     * - If the [ImageVectorNode] list contains an [ImageVectorNode.Group],
     * the group imports are added.
     * - If the [ParserConfig.addToMaterial] property of the [config] object
     * is `true`, the material context provider import is included.
     * - For each [ImageVectorNode.Path] found in the [nodes] list, the
     * associated path imports are added.
     *
     * @param nodes a List of [ImageVectorNode]s to be processed for possible
     * additional imports.
     * @param config the parser configuration based on which additional
     * decisions for imports are taken.
     * @return a set of [String]s representing all the required imports.
     *
     * @see defaultImports
     * @see androidPreviewImports
     * @see materialReceiverTypeImport
     * @see ImageVectorNode.Path.imports
     * @see ImageVectorNode.Group.imports
     * @see createGroupImports
     * @see createChunkFunctionsImports
     */
    protected fun createImports(
        nodes: List<ImageVectorNode>,
        config: ParserConfig,
    ): Set<String> = buildSet {
        addAll(defaultImports)
        if (config.noPreview.not()) {
            addAll(if (config.kmpPreview) kmpPreviewImports else androidPreviewImports)
        }
        if (config.addToMaterial) {
            addAll(materialReceiverTypeImport)
        }
        val nodeImports = nodes
            .asSequence()
            .flatMap(::createChunkFunctionsImports)
            .flatMap(::createGroupImports)
            .toSet()

        addAll(nodeImports)
    }

    private fun createGroupImports(node: ImageVectorNode) = if (node is ImageVectorNode.Group) {
        node.imports + node.commands.flatMap { it.imports }
    } else {
        node.imports
    }

    private fun createChunkFunctionsImports(node: ImageVectorNode) = if (node is ImageVectorNode.ChunkFunction) {
        node.nodes
    } else {
        listOf(node)
    }

    /**
     * [SvgParser] is a subclass of [ImageParser].
     *
     * This class is responsible for parsing an SVG file type and creates
     * all the required information to generate a Jetpack Compose Icon.
     *
     * @constructor Takes a FileManager parameter.
     *
     * @param fileManager The Main tool that helps to manage files and allows
     *  reading data from the file system.
     */
    class SvgParser(
        fileManager: FileManager,
    ) : ImageParser(fileManager) {
        /**
         * Parses an SVG file into an [IconFileContents] object.
         *
         * The parsing procedure can be summed up as follows:
         * 1. Read the content of SVG file.
         * 2. Parses the content of file to a [SvgRootNode] object.
         * 3. Converts SVG element nodes to [ImageVectorNode] to get the list
         * of nodes.
         * 4. Lastly, an [IconFileContents] instance is assembled with the necessary
         * data needed to create the Jetpack Compose Icon and returns it.
         *
         * @param file This is a [Path] object that points to the file that needs
         * to be parsed into an icon.
         * @param iconName A string parameter that serves as the identifier for the
         * icon that will be generated after parsing the file.
         * @param config An instance of the [ParserConfig] class, which holds
         * configurations required for parsing the file into an icon.
         *
         * @return [IconFileContents] object instance which contains details about the
         * icon parsed from the file.
         */
        override fun parse(
            file: Path,
            iconName: String,
            config: ParserConfig,
        ): IconFileContents {
            val content = fileManager.readContent(file)

            val root = XmlParser.parse(content = content, fileType = FileType.Svg)
            val svg = root.children.single { it is SvgRootNode } as SvgRootNode
            svg.resolveUseNodes()
            val (_, _, viewportWidth, viewportHeight) = svg.viewBox
            val nodes = svg
                .asNodes(minified = config.minified)
                .map {
                    it.applyTransformation()
                }

            return IconFileContents(
                pkg = config.pkg,
                iconName = iconName,
                theme = config.theme,
                width = svg.width,
                height = svg.height,
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

    /**
     * [AndroidVectorParser] is a subclass of [ImageParser].
     *
     * This class is responsible for parsing an AVG file type and creates
     * all the required information to generate a Jetpack Compose Icon.
     *
     * @constructor Takes a FileManager parameter.
     *
     * @param fileManager The Main tool that helps to manage files and allows
     * reading data from the file system.
     */
    class AndroidVectorParser(
        fileManager: FileManager,
    ) : ImageParser(fileManager) {
        /**
         * Parses an AVG file into an [IconFileContents] object.
         *
         * The parsing procedure can be summed up as follows:
         * 1. Read the content of the file.
         * 2. Parses the file content into a [AvgRootNode] object.
         * 3. Converts the parsed AVG element nodes into [ImageVectorNode] to obtain
         * a list of nodes.
         * 4. Lastly, an [IconFileContents] instance is assembled with the necessary
         * data needed to create the Jetpack Compose Icon and returns it.
         *
         * @param file a [Path] object that leads to the file to be parsed into an icon.
         * @param iconName a String that will serve as the identifier for the icon that
         * will be generated after parsing the file.
         * @param config an instance of the [ParserConfig] class, which contains the
         * settings needed to parse the file into an icon.
         * @return an [IconFileContents] object instance that holds data about the parsed
         * icon.
         */
        override fun parse(
            file: Path,
            iconName: String,
            config: ParserConfig,
        ): IconFileContents {
            val content = fileManager.readContent(file)

            val root = XmlParser.parse(content = content, fileType = FileType.Avg)
            val avg = root.children.single { it is AvgRootNode } as AvgRootNode
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

        /**
         * An operator function [invoke] that oversees the creation of parsers
         * for SVG and Android Vector Drawable Images.
         *
         * The function accepts a [FileManager] parameter for file management and
         * then employs it in the creation of specific parser objects:
         * SVG ([SvgParser]) and Android Vector ([AndroidVectorParser]). Upon populating
         * the parsers object, the method returns the singleton instance of [ImageParser]
         * to enable a sequence of operations.
         *
         * @param fileManager a [FileManager] instance that allows reading from the file system.
         * @return a singleton instance of [ImageParser].
         */
        operator fun invoke(fileManager: FileManager): Companion {
            parsers = mapOf(
                SVG_EXTENSION to SvgParser(fileManager),
                ANDROID_VECTOR_EXTENSION to AndroidVectorParser(fileManager),
            )

            return this // returning Companion to enable chain call.
        }

        /**
         * A part of the sealed [ImageParser] companion object, [parse] is a function
         * that parses a [file] to a [String], with the help of a specified [ParserConfig].
         *
         * Before calling this function, you should initialize the ImageParse by calling
         * the [ImageParser.invoke] first.
         *
         * Supported extensions: `.xml`, `.svg`
         *
         * @param file A [Path] object that points towards the file that needs to be parsed.
         * @param iconName An identifier for the icon that gets generated after parsing
         * the file.
         * @param config An instance of the [ParserConfig] class, which contains
         * configurations required for parsing the file.
         * @throws UnsupportedOperationException if the parsers are not initialized.
         * @throws ExitProgramException if an unsupported file extension is provided.
         * @returns A string after parsing the mentioned file using the appropriate
         * parser based on the file extension.
         */
        fun parse(
            file: Path,
            iconName: String,
            config: ParserConfig,
        ): String {
            if (::parsers.isInitialized.not()) {
                error(
                    "Parsers not initialized. Call ImageParser(fileManager) before calling ImageParser.parse()",
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
