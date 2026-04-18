package dev.tonholo.s2c.parser

import dev.tonholo.s2c.domain.IconFileContents

/**
 * Parses raw string content (SVG or AVG markup) into an [IconFileContents] domain model.
 *
 * This is the content-based counterpart to [ImageParser], which operates on files.
 * Implementations handle a single file format each, following the Strategy pattern.
 */
fun interface ContentParser {
    /**
     * Parses the given [content] markup into an [IconFileContents] domain model.
     *
     * @param content Raw SVG or AVG markup string.
     * @param iconName Name used for the generated icon composable.
     * @param config Parser configuration controlling output options.
     */
    fun parse(
        content: String,
        iconName: String,
        config: ParserConfig,
    ): IconFileContents
}
