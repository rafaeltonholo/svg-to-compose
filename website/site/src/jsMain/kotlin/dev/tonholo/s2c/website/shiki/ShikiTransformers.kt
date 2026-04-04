package dev.tonholo.s2c.website.shiki

/**
 * Kotlin/JS wrappers for `@shikijs/transformers`.
 *
 * These functions call into the transformers module loaded at runtime
 * via `globalThis.shikiTransformers` (set up in [Shiki.initialize]).
 *
 * For TOML files, notation-based transformers use `#` comments instead
 * of the default `//` comments. A custom regex is passed to override
 * the default comment pattern.
 */
object ShikiTransformers {
    private val transformersModule: dynamic
        get() = js("globalThis.shikiTransformers")

    /**
     * Highlights lines annotated with `# [!code error]`, `# [!code warning]`.
     */
    fun notationErrorLevel(): dynamic {
        val opts = js("({})")
        opts.matchAlgorithm = "v3"
        return transformersModule.transformerNotationErrorLevel(opts)
    }

    /** Renders whitespace characters as visible dots/arrows. */
    fun renderWhitespace(): dynamic = transformersModule.transformerRenderWhitespace()

    /** Renders vertical indent guide lines. */
    fun renderIndentGuides(): dynamic = transformersModule.transformerRenderIndentGuides()

    // Adds a CSS class to a HAST node's properties.
    // HAST stores classes as a string in `properties.class`.
    private fun addClassToNode(node: dynamic, className: String) {
        if (node.properties == null || node.properties == js("undefined")) {
            node.properties = js("({})")
        }
        val existing: String? = node.properties["class"]?.toString()
        node.properties["class"] = if (existing.isNullOrEmpty()) className else "$existing $className"
    }

    /**
     * Custom transformer that:
     * 1. Adds `highlighted` class to section header lines
     * 2. Appends a clickable info icon to lines with tooltip documentation
     *
     * Uses `enforce: "post"` to run after notation transformers.
     *
     * @param tomlLines The raw TOML lines (used to resolve content per line).
     * @param isSectionHeader Returns true if the line text is a known section header.
     * @param tooltipLookup Returns title+description for lines with tooltip info, or null.
     */
    fun infoIconTransformer(
        tomlLines: List<String>,
        isSectionHeader: (String) -> Boolean,
        tooltipLookup: (String) -> Pair<String, String>?,
    ): dynamic {
        val transformer = js("({})")
        transformer.name = "s2c-template-editor"
        transformer.enforce = "post"
        val lineHook: (dynamic, Int) -> dynamic = lineHook@{ node, lineNumber ->
            val lineIndex = lineNumber - 1
            val lineText = if (lineIndex in tomlLines.indices) tomlLines[lineIndex].trim() else ""

            // Add highlighted class to section headers
            if (isSectionHeader(lineText)) {
                addClassToNode(node, "highlighted")
            }

            // Append info icon for lines with tooltip content
            val tooltip = tooltipLookup(lineText)
            if (tooltip != null) {
                val (title, description) = tooltip
                val iconNode = js("({})")
                iconNode.type = "element"
                iconNode.tagName = "span"
                val iconProps = js("({})")
                iconProps["class"] = "s2c-info-icon"
                iconProps["data-tooltip-title"] = title
                iconProps["data-tooltip-desc"] = description
                iconNode.properties = iconProps
                val textNode = js("({})")
                textNode.type = "text"
                textNode.value = "\u24D8"
                iconNode.children = arrayOf(textNode)

                val children = node.children
                if (children != js("undefined")) {
                    node.children = children.concat(arrayOf(iconNode))
                }
            }
            return@lineHook node
        }
        transformer.line = lineHook
        return transformer
    }
}
