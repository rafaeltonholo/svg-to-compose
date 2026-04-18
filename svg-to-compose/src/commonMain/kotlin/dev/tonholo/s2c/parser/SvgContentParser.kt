package dev.tonholo.s2c.parser

import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.domain.svg.SvgRootNode
import dev.tonholo.s2c.domain.svg.asNodes
import dev.tonholo.s2c.logger.Logger
import dev.zacsweers.metro.Inject

/**
 * [ContentParser] implementation that parses SVG content into [IconFileContents].
 *
 * Resolves `<use>` reference nodes and applies geometric transforms before
 * producing the final node tree.
 */
@Inject
class SvgContentParser(private val logger: Logger) : ContentParser {
    override fun parse(
        content: String,
        iconName: String,
        config: ParserConfig,
    ): IconFileContents = with(logger) {
        val root = XmlParser.parse(content = content, fileType = FileType.Svg)
        val svg = root.children.single { it is SvgRootNode } as SvgRootNode
        svg.resolveUseNodes()

        val (_, _, viewportWidth, viewportHeight) = svg.viewBox
        val nodes = svg
            .asNodes(computedRules = svg.rules, minified = config.minified)
            .map { it.applyTransformation() }

        IconFileContents(
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
            imports = createIconImports(nodes, config),
        )
    }
}
