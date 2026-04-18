package dev.tonholo.s2c.emitter.template

import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.emitter.template.TemplateConstants.GroupVar
import dev.tonholo.s2c.emitter.template.TemplateConstants.IconVar
import dev.tonholo.s2c.emitter.template.TemplateConstants.PathVar
import dev.tonholo.s2c.emitter.template.config.ColorMappingDefinition
import dev.tonholo.s2c.emitter.template.config.TemplateEmitterConfig
import dev.tonholo.s2c.extensions.pascalCase

/**
 * Holds variable maps and accumulated state during template resolution.
 *
 * **Thread-safety:** This class is **not** thread-safe. Imports are accumulated
 * during placeholder resolution and color mapping via [addImport]/[addImports].
 * Each [TemplateContext] instance must be confined to a single thread.
 *
 * @property iconVariables Variables in the `icon` namespace.
 * @property chunkVariables Variables in the `chunk` namespace (chunk function contexts only).
 * @property definitions Import definitions from the template config, keyed by import key.
 * @property fragments Template fragments from the config, keyed by fragment name.
 * @property colorMappings Color mappings from the template config definitions.
 */
internal class TemplateContext(
    val iconVariables: Map<String, String?>,
    val chunkVariables: Map<String, String?> = emptyMap(),
    val definitions: Map<String, String>,
    val fragments: Map<String, String>,
    initialImports: Set<String> = emptySet(),
    val colorMappings: List<ColorMappingDefinition> = emptyList(),
) {
    private val _collectedImports: MutableSet<String> = initialImports.toMutableSet()

    /** Immutable snapshot of the accumulated imports. */
    val collectedImports: Set<String> get() = _collectedImports.toSet()

    /** Registers a single import path. */
    fun addImport(importPath: String) {
        _collectedImports.add(importPath)
    }

    /** Registers multiple import paths. */
    fun addImports(importPaths: Iterable<String>) {
        _collectedImports.addAll(importPaths)
    }

    /**
     * Applies color mappings to a resolved variable value.
     *
     * When a value contains `Color(<hex>)` matching a mapping's value,
     * it is replaced with the mapping's name and the corresponding
     * import is registered via [addImports].
     *
     * @param value The resolved variable value (e.g., `"SolidColor(Color(0xFF121212))"`).
     * @return The value with color substitutions applied.
     */
    fun applyColorMappings(value: String?): String? {
        if (value == null || colorMappings.isEmpty()) return value
        val (result, imports) = resolveColorMappings(value, colorMappings)
        addImports(imports)
        return result
    }

    companion object {
        /**
         * Builds a [TemplateContext] for icon-level template resolution.
         */
        fun forIcon(
            contents: IconFileContents,
            config: TemplateEmitterConfig,
            iconBody: String,
        ): TemplateContext {
            val receiverName = config.definitions.receiver?.name
            val iconPropertyName = when {
                !contents.receiverType.isNullOrEmpty() -> {
                    val receiverType = contents.receiverType.removeSuffix(".")
                    "$receiverType.${contents.iconName.pascalCase()}"
                }

                receiverName != null -> "$receiverName.${contents.iconName.pascalCase()}"

                contents.addToMaterial -> "Icons.Filled.${contents.iconName.pascalCase()}"

                else -> contents.iconName.pascalCase()
            }

            val visibility = if (contents.makeInternal) "internal" else ""

            val iconVars = mapOf(
                IconVar.NAME to contents.iconName.pascalCase(),
                IconVar.RECEIVER to (contents.receiverType ?: receiverName),
                IconVar.THEME to contents.theme,
                IconVar.WIDTH to contents.width.toString(),
                IconVar.HEIGHT to contents.height.toString(),
                IconVar.VIEWPORT_WIDTH to "${contents.viewportWidth}f",
                IconVar.VIEWPORT_HEIGHT to "${contents.viewportHeight}f",
                IconVar.BODY to iconBody,
                IconVar.PACKAGE to contents.pkg,
                IconVar.PROPERTY_NAME to iconPropertyName,
                IconVar.VISIBILITY to visibility,
            )

            val defs = config.definitions.imports
            val frags = config.fragments

            return TemplateContext(
                iconVariables = iconVars,
                definitions = defs,
                fragments = frags,
                initialImports = contents.imports.toSet(),
                colorMappings = config.definitions.colorMapping,
            ).also { ctx ->
                config.definitions.receiver?.let { receiver ->
                    ctx.addImport("${receiver.packageName}.${receiver.name}")
                }
            }
        }

        /**
         * Builds a variable map for a path node.
         */
        fun pathVariables(params: ImageVectorNode.Path.Params): Map<String, String?> = mapOf(
            PathVar.FILL to params.fill?.toCompose(),
            PathVar.FILL_ALPHA to params.fillAlpha?.let { "${it}f" },
            PathVar.FILL_TYPE to params.pathFillType?.toCompose(),
            PathVar.STROKE to params.stroke?.toCompose(),
            PathVar.STROKE_ALPHA to params.strokeAlpha?.let { "${it}f" },
            PathVar.STROKE_LINE_CAP to params.strokeLineCap?.toCompose(),
            PathVar.STROKE_LINE_JOIN to params.strokeLineJoin?.toCompose(),
            PathVar.STROKE_MITER_LIMIT to params.strokeMiterLimit?.let { "${it}f" },
            PathVar.STROKE_LINE_WIDTH to params.strokeLineWidth?.let { "${it}f" },
        )

        /**
         * Builds a variable map for a group node.
         */
        fun groupVariables(params: ImageVectorNode.Group.Params): Map<String, String?> = mapOf(
            GroupVar.ROTATE to params.rotate?.let { "${it}f" },
            GroupVar.PIVOT_X to params.pivotX?.let { "${it}f" },
            GroupVar.PIVOT_Y to params.pivotY?.let { "${it}f" },
            GroupVar.SCALE_X to params.scaleX?.let { "${it}f" },
            GroupVar.SCALE_Y to params.scaleY?.let { "${it}f" },
            GroupVar.TRANSLATION_X to params.translationX?.let { "${it}f" },
            GroupVar.TRANSLATION_Y to params.translationY?.let { "${it}f" },
        )
    }
}

/**
 * Pure function that applies [colorMappings] to a [value] string.
 *
 * Replaces `Color(<hex>)` expressions matching a mapping's value with the
 * mapping's name. Returns the transformed string and the set of imports
 * that should be registered.
 */
private fun resolveColorMappings(
    value: String,
    colorMappings: List<ColorMappingDefinition>,
): Pair<String, Set<String>> {
    var result = value
    val imports = mutableSetOf<String>()
    for (mapping in colorMappings) {
        val colorExpr = "Color(${mapping.value})"
        if (colorExpr in result) {
            result = result.replace(colorExpr, mapping.name)
            imports.add("${mapping.importPackage}.${mapping.name}")
        }
    }
    return result to imports
}
