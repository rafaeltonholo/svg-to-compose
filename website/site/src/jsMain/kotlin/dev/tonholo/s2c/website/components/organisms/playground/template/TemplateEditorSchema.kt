package dev.tonholo.s2c.website.components.organisms.playground.template

import dev.tonholo.s2c.emitter.template.TemplateConstants
import dev.tonholo.s2c.website.domain.model.playground.template.TomlKeyInfo
import dev.tonholo.s2c.website.domain.model.playground.template.TomlSectionInfo
import dev.tonholo.s2c.website.domain.model.playground.template.TooltipInfo

private const val TRIPLE_QUOTE = "\"\"\""

/**
 * Static schema data for the s2c template TOML format.
 *
 * Used by autocomplete and tooltip features to provide contextual
 * suggestions and documentation.
 */
object TemplateEditorSchema {

    private val definitionsReceiverSection = TomlSectionInfo(
        header = "definitions.receiver",
        description = "Receiver type for the generated icon property. Auto-imported.",
        keys = listOf(
            TomlKeyInfo(
                key = "name",
                description = "Simple name of the receiver type (e.g. \"Icons\").",
                insertValue = "name = \"Icons\"",
            ),
            TomlKeyInfo(
                key = "package",
                description = "Full package of the receiver type.",
                insertValue = "package = \"com.example.icons\"",
            ),
        ),
    )

    private val definitionsImportsSection = TomlSectionInfo(
        header = "definitions.imports",
        description = "Keyed imports referenced in templates via " +
            $$"${$${TemplateConstants.Namespace.DEFINITIONS}:<key>}.",
        keys = listOf(
            TomlKeyInfo(
                key = "icon_builder",
                description = "Builder function import for icon construction.",
                insertValue = "icon_builder = \"com.example.icon\"",
            ),
            TomlKeyInfo(
                key = "<key>",
                description = "Generic import. The simple name is emitted; the full path is auto-imported.",
                insertValue = "my_import = \"com.example.ClassName\"",
            ),
        ),
    )

    private val definitionsColorMappingSection = TomlSectionInfo(
        header = "definitions.color_mapping",
        description = "Color mappings: replace Color(<value>) with <name> and auto-import.",
        keys = listOf(
            TomlKeyInfo(
                key = "name",
                description = "Replacement name for the color constant (e.g. \"BLACK\").",
                insertValue = "name = \"BLACK\"",
            ),
            TomlKeyInfo(
                key = "import",
                description = "Package to import the color constant from.",
                insertValue = "import = \"com.example.colors\"",
            ),
            TomlKeyInfo(
                key = "value",
                description = "Hex color value to match (e.g. \"0xFF121212\").",
                insertValue = "value = \"0xFF000000\"",
            ),
        ),
    )

    private val templatesSection = TomlSectionInfo(
        header = "templates",
        description = "Template strings for generated code. " +
            $$"Placeholders: ${icon:*}, ${template:*}.",
        keys = listOf(
            TomlKeyInfo(
                key = "file_header",
                description = $$"Optional file header prepended to output. Supports ${icon:package}.",
                // language=toml
                insertValue = $$"""
                    |file_header = $$TRIPLE_QUOTE
                    |package ${$${TemplateConstants.Namespace.ICON}:$${TemplateConstants.IconVar.PACKAGE}}
                    |$$TRIPLE_QUOTE
                """.trimMargin(),
            ),
            TomlKeyInfo(
                key = "icon_template",
                description = $$"Outer icon property template. Placeholders: ${icon:*}, ${template:*}.",
                insertValue = buildIconTemplateInsertValue(),
            ),
        ),
    )

    private val templatesPreviewSection = TomlSectionInfo(
        header = "templates.preview",
        description = "Custom @Preview function template. " +
            "Set template = \"\" to suppress.",
        keys = listOf(
            TomlKeyInfo(
                key = "template",
                description = $$"Preview composable template. Placeholders: ${icon:*}, ${def:*}.",
                // language=toml
                insertValue = $$"""
                    |template = $$TRIPLE_QUOTE
                    |@Preview
                    |@Composable
                    |private fun ${$${TemplateConstants.Namespace.ICON}:$${TemplateConstants.IconVar.NAME}}Preview() {
                    |    Icon(
                    |        imageVector = ${$${TemplateConstants.Namespace.ICON}:$${TemplateConstants.IconVar.PROPERTY_NAME}},
                    |        contentDescription = null,
                    |    )
                    |}
                    |$$TRIPLE_QUOTE
                """.trimMargin(),
            ),
        ),
    )

    private val fragmentsSection = TomlSectionInfo(
        header = "fragments",
        description = $$"Named template snippets referenced via ${template:<name>}.",
        keys = buildFragmentKeys(),
    )

    /** All known TOML sections, keyed by their header name. */
    val sections: Map<String, TomlSectionInfo> = listOf(
        definitionsReceiverSection,
        definitionsImportsSection,
        definitionsColorMappingSection,
        templatesSection,
        templatesPreviewSection,
        fragmentsSection,
    ).associateBy { it.header }

    /** Top-level section headers offered when cursor is before any section. */
    val topLevelSections: List<TomlKeyInfo> = buildTopLevelSections()

    /**
     * Returns tooltip info for a given token text.
     *
     * Matches section headers like `[templates]` and known keys like `icon_template`.
     */
    fun findTooltipInfo(token: String): TooltipInfo? {
        val trimmed = token.trim()

        // Check for section header match (with or without brackets)
        val headerName = trimmed.removeSurrounding("[", "]").removeSurrounding("[", "]").trim()
        sections[headerName]?.let { section ->
            return TooltipInfo(
                title = "[$headerName]",
                description = section.description,
                keys = section.keys.map { "${it.key} - ${it.description}" },
            )
        }

        // Check for key match across all sections
        for ((sectionHeader, section) in sections) {
            for (keyInfo in section.keys) {
                if (keyInfo.key == trimmed) {
                    return TooltipInfo(
                        title = trimmed,
                        description = keyInfo.description,
                        context = "Section: [$sectionHeader]",
                    )
                }
            }
        }

        // Check for placeholder match like ${icon:name}
        val placeholderMatch = PLACEHOLDER_REGEX.matchEntire(trimmed)
        if (placeholderMatch != null) {
            val namespace = placeholderMatch.groupValues[1]
            val key = placeholderMatch.groupValues[2]
            val desc = placeholderDescriptions["$namespace:$key"]
            if (desc != null) {
                return TooltipInfo(
                    title = $$"${$$namespace:$$key}",
                    description = desc,
                )
            }
        }

        return null
    }

    // Escape on `}` is required for JS platform (unicode flag disallows raw brackets).
    @Suppress("RegExpRedundantEscape")
    private val PLACEHOLDER_REGEX = Regex("""\$\{(\w+):(\w+)\}""")

    /** Common placeholder descriptions for tooltips. */
    private val placeholderDescriptions: Map<String, String> =
        buildPlaceholderDescriptions()
}

private fun placeholder(namespace: String, variable: String): String = $$"${$$namespace:$$variable}"

private fun iconPlaceholder(variable: String): String = placeholder(TemplateConstants.Namespace.ICON, variable)

private fun buildIconTemplateInsertValue(): String {
    val visibility = iconPlaceholder(TemplateConstants.IconVar.VISIBILITY)
    val propName = iconPlaceholder(TemplateConstants.IconVar.PROPERTY_NAME)
    val body = iconPlaceholder(TemplateConstants.IconVar.BODY)
    val builder = placeholder(
        TemplateConstants.Namespace.TEMPLATE,
        TemplateConstants.Fragment.ICON_BUILDER,
    )

    // language=TOML
    return $$"""
        |icon_template = $$TRIPLE_QUOTE
        |$$visibility val $$propName: ImageVector by lazy {
        |    $$builder {
        |        $$body
        |    }
        |}
        |$$TRIPLE_QUOTE
    """.trimMargin()
}

private fun buildFragmentKeys(): List<TomlKeyInfo> {
    val ns = TemplateConstants.Namespace
    val frag = TemplateConstants.Fragment
    val pathFill = placeholder(ns.PATH, TemplateConstants.PathVar.FILL)
    val groupRotate = placeholder(
        ns.GROUP,
        TemplateConstants.GroupVar.ROTATE,
    )
    val iconName = iconPlaceholder(TemplateConstants.IconVar.NAME)
    val defBuilder = placeholder(ns.DEFINITIONS, "icon_builder")
    val chunkIndex = placeholder(
        ns.CHUNK,
        TemplateConstants.ChunkVar.INDEX,
    )
    val chunkName = placeholder(
        ns.CHUNK,
        TemplateConstants.ChunkVar.NAME,
    )
    val chunkBody = placeholder(
        ns.CHUNK,
        TemplateConstants.ChunkVar.BODY,
    )
    return listOf(
        TomlKeyInfo(
            key = frag.PATH_BUILDER,
            description = $$"""Per-path-node fragment. Placeholders: ${$${ns.PATH}:*}, ${$${ns.DEFINITIONS}:*}.""",
            insertValue = "${frag.PATH_BUILDER} = \"path(fill = $pathFill)\"",
        ),
        TomlKeyInfo(
            key = frag.GROUP_BUILDER,
            description = $$"Per-group-node fragment. Placeholders: ${$${ns.GROUP}:*}.",
            insertValue = "${frag.GROUP_BUILDER} = \"group(rotate = $groupRotate)\"",
        ),
        TomlKeyInfo(
            key = frag.ICON_BUILDER,
            description = $$"Icon builder fragment. Placeholders: ${$${ns.DEFINITIONS}:*}, ${$${ns.ICON}:*}.",
            insertValue = "${frag.ICON_BUILDER} = \"$defBuilder(name = \\\"$iconName\\\")\"",
        ),
        TomlKeyInfo(
            key = frag.CHUNK_FUNCTION_NAME,
            description = "Naming pattern for chunk helper functions. " +
                "Placeholders: $iconName, $chunkIndex.",
            insertValue = "${frag.CHUNK_FUNCTION_NAME} = \"${iconName}Part$chunkIndex\"",
        ),
        TomlKeyInfo(
            key = frag.CHUNK_FUNCTION_DEFINITION,
            description = "Body of the chunk helper function. Placeholders: $chunkName, $chunkBody.",
            insertValue = "${frag.CHUNK_FUNCTION_DEFINITION} = " +
                "\"private fun ImageVector.Builder.$chunkName() {\\n$chunkBody\\n}\"",
        ),
    )
}

private fun buildTopLevelSections(): List<TomlKeyInfo> = buildDefinitionSections() + buildTemplateSections()

private fun buildDefinitionSections(): List<TomlKeyInfo> = listOf(
    TomlKeyInfo(
        key = "[definitions.receiver]",
        description = "Define the receiver type for generated icon properties.",
        // language=TOML
        insertValue = """
            |[definitions.receiver]
            |name = "Icons"
            |package = "com.example.icons
        """.trimMargin(),
    ),
    TomlKeyInfo(
        key = "[definitions.imports]",
        description = $$"Keyed imports referenced in templates via ${def:<key>}.",
        // language=TOML
        insertValue = """
            |[definitions.imports]
            |icon_builder = "com.example.icon
        """.trimMargin(),
    ),
    TomlKeyInfo(
        key = "[[definitions.color_mapping]]",
        description = "Color mapping: replace Color(<value>) " +
            "with a named constant.",
        // language=TOML
        insertValue = """
            |[[definitions.color_mapping]]
            |name = "BLACK"
            |import = "com.example.colors"
            |value = "0xFF000000
        """.trimMargin(),
    ),
)

private fun buildTemplateSections(): List<TomlKeyInfo> = listOf(
    TomlKeyInfo(
        key = "[templates]",
        description = "Template strings for generated icon code.",
        // language=TOML
        insertValue = $$"""
            |[templates]
            |icon_template = $$TRIPLE_QUOTE
            |${icon:visibility} val ${icon:property_name}: ImageVector by lazy {
            |    ${template:icon_builder} {
            |       ${icon:body}
            |    }
            |}
            |$$TRIPLE_QUOTE
        """.trimMargin(),
    ),
    TomlKeyInfo(
        key = "[templates.preview]",
        description = "Custom @Preview function template.",
        // language=TOML
        insertValue = $$"""
            |[templates.preview]
            |template = $$TRIPLE_QUOTE
            |@Preview
            |@Composable
            |private fun ${icon:name}Preview() {
            |
            |}
            |$$TRIPLE_QUOTE
        """.trimMargin(),
    ),
    TomlKeyInfo(
        key = "[fragments]",
        description = $$"Named template snippets referenced via ${template:<name>}.",
        // language=TOML
        insertValue = $$"""
            |[fragments]
            |icon_builder = "${def:icon_builder}(name = \"${icon:name}\")
        """.trimMargin(),
    ),
)

private fun buildPlaceholderDescriptions(): Map<String, String> = buildMap {
    putAll(iconPlaceholderDescriptions())
    putAll(pathPlaceholderDescriptions())
    putAll(groupPlaceholderDescriptions())
    putAll(chunkAndTemplatePlaceholderDescriptions())
}

private fun iconPlaceholderDescriptions(): Map<String, String> {
    val ns = TemplateConstants.Namespace.ICON
    val v = TemplateConstants.IconVar
    return mapOf(
        "$ns:${v.NAME}" to "Icon name (PascalCase).",
        "$ns:${v.RECEIVER}" to "Receiver type (e.g. Icons.Filled).",
        "$ns:${v.THEME}" to "Icon theme (Filled, Outlined, etc.).",
        "$ns:${v.WIDTH}" to "Intrinsic width in dp.",
        "$ns:${v.HEIGHT}" to "Intrinsic height in dp.",
        "$ns:${v.VIEWPORT_WIDTH}" to "SVG viewport width.",
        "$ns:${v.VIEWPORT_HEIGHT}" to "SVG viewport height.",
        "$ns:${v.BODY}" to "Generated icon body (path/group nodes).",
        "$ns:${v.PACKAGE}" to "Target package name.",
        "$ns:${v.PROPERTY_NAME}" to "Property name (camelCase).",
        "$ns:${v.VISIBILITY}" to "Visibility modifier (public/internal).",
    )
}

private fun pathPlaceholderDescriptions(): Map<String, String> {
    val ns = TemplateConstants.Namespace.PATH
    val v = TemplateConstants.PathVar
    return mapOf(
        "$ns:${v.FILL}" to "Path fill color.",
        "$ns:${v.FILL_ALPHA}" to "Path fill alpha (0..1).",
        "$ns:${v.FILL_TYPE}" to "Path fill type (EvenOdd/NonZero).",
        "$ns:${v.STROKE}" to "Path stroke color.",
        "$ns:${v.STROKE_ALPHA}" to "Path stroke alpha.",
        "$ns:${v.STROKE_LINE_CAP}" to "Stroke line cap style.",
        "$ns:${v.STROKE_LINE_JOIN}" to "Stroke line join style.",
        "$ns:${v.STROKE_MITER_LIMIT}" to "Stroke miter limit.",
        "$ns:${v.STROKE_LINE_WIDTH}" to "Stroke line width.",
    )
}

private fun groupPlaceholderDescriptions(): Map<String, String> {
    val ns = TemplateConstants.Namespace.GROUP
    val v = TemplateConstants.GroupVar
    return mapOf(
        "$ns:${v.ROTATE}" to "Group rotation angle.",
        "$ns:${v.PIVOT_X}" to "Group pivot X coordinate.",
        "$ns:${v.PIVOT_Y}" to "Group pivot Y coordinate.",
        "$ns:${v.SCALE_X}" to "Group scale X factor.",
        "$ns:${v.SCALE_Y}" to "Group scale Y factor.",
        "$ns:${v.TRANSLATION_X}" to "Group translation X.",
        "$ns:${v.TRANSLATION_Y}" to "Group translation Y.",
    )
}

private fun chunkAndTemplatePlaceholderDescriptions(): Map<String, String> {
    val ns = TemplateConstants.Namespace
    val chunk = TemplateConstants.ChunkVar
    val frag = TemplateConstants.Fragment
    return mapOf(
        "${ns.CHUNK}:${chunk.NAME}" to
            "Resolved chunk function name.",
        "${ns.CHUNK}:${chunk.BODY}" to
            "Emitted node code for the chunk.",
        "${ns.CHUNK}:${chunk.INDEX}" to
            "0-based chunk index.",
        "${ns.TEMPLATE}:icon_builder" to
            "Resolved icon_builder fragment.",
        "${ns.TEMPLATE}:${frag.PATH_BUILDER}" to
            "Resolved path_builder fragment.",
        "${ns.TEMPLATE}:${frag.GROUP_BUILDER}" to
            "Resolved group_builder fragment.",
        "${ns.DEFINITIONS}:icon_builder" to
            "Resolved import simple name for icon_builder.",
    )
}
