package dev.tonholo.s2c.parser

import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.domain.avg.AvgRootNode
import dev.tonholo.s2c.domain.avg.asNodes
import dev.tonholo.s2c.logger.Logger
import dev.zacsweers.metro.Inject

/**
 * [ContentParser] implementation that parses AVG (Android Vector Graphics)
 * XML content into [IconFileContents] using the [XmlParser].
 */
@Inject
class AvgContentParser(private val logger: Logger) : ContentParser {
    override fun parse(
        content: String,
        iconName: String,
        config: ParserConfig,
    ): IconFileContents = with(logger) {
        val root = XmlParser.parse(content = content, fileType = FileType.Avg)
        val avg = root.children.single { it is AvgRootNode } as AvgRootNode
        val nodes = avg.asNodes(minified = config.minified)

        IconFileContents(
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
            imports = createIconImports(nodes, config),
        )
    }
}
