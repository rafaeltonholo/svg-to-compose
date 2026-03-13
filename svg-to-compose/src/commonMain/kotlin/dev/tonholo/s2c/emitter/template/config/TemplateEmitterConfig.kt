package dev.tonholo.s2c.emitter.template.config

import dev.tonholo.s2c.emitter.imagevector.ImageVectorEmitter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Root configuration parsed from an `s2c.template.toml` file.
 *
 * @property definitions Optional definitions for receiver types and keyed imports.
 * @property templates Optional template overrides for icon and preview.
 * @property fragments Named template fragments for builder customization, keyed by fragment name.
 *   Each value is the template string with placeholders.
 */
@Serializable
data class TemplateEmitterConfig(
    val definitions: TemplateDefinitions = TemplateDefinitions(),
    val templates: TemplateSection = TemplateSection(),
    val fragments: Map<String, String> = emptyMap(),
)

/**
 * Definitions section of the template configuration.
 *
 * @property receiver Optional receiver type definition for the icon property.
 * @property imports Keyed import definitions referenced via `${def:<key>}` in templates.
 *   Each key maps to a fully qualified import path. The simple name is derived
 *   from the last segment (e.g., `"com.example.icon"` -> `icon`).
 * @property colorMapping Optional list of color mappings. When a generated color
 *   expression (e.g., `Color(0xFF121212)`) matches a mapping's [ColorMappingDefinition.value],
 *   it is replaced with the mapping's [ColorMappingDefinition.name] and the corresponding
 *   import is registered.
 */
@Serializable
data class TemplateDefinitions(
    val receiver: ReceiverDefinition? = null,
    val imports: Map<String, String> = emptyMap(),
    @SerialName("color_mapping") val colorMapping: List<ColorMappingDefinition> = emptyList(),
)

/**
 * Defines a receiver type for the generated icon property.
 *
 * @property name The receiver name (e.g., `Icons`).
 * @property packageName The package to auto-import (e.g., `com.example.icons`).
 */
@Serializable
data class ReceiverDefinition(val name: String, @SerialName("package") val packageName: String)

/**
 * Templates section of the configuration.
 *
 * @property fileHeader Optional file header (e.g., license, `@file:` annotations).
 *   Placed before the `package` statement. Resolved with icon-level placeholders.
 * @property iconTemplate The outer icon property template. If null, the default
 *   backing-field pattern from [ImageVectorEmitter] is used.
 * @property preview Optional preview template configuration.
 */
@Serializable
data class TemplateSection(
    @SerialName("file_header") val fileHeader: String? = null,
    @SerialName("icon_template") val iconTemplate: String? = null,
    val preview: PreviewConfig? = null,
)

/**
 * Preview template configuration.
 *
 * @property template The full preview function template. When `null`, default
 *   preview behavior is used (controlled by CLI flags). When empty string,
 *   preview generation is suppressed.
 */
@Serializable
data class PreviewConfig(val template: String? = null)

/**
 * Defines a color mapping that replaces generated `Color(...)` expressions
 * with a named constant and registers the corresponding import.
 *
 * @property name The constant name to use in generated code (e.g., `BLACK`).
 * @property importPackage The package or object that contains [name]. The full import
 *   path is constructed as `"$importPackage.$name"`. This can be a package
 *   (e.g., `com.example.colors` -> `import com.example.colors.BLACK`) or a class/object
 *   (e.g., `com.example.Colors` -> `import com.example.Colors.BLACK`).
 * @property value The hex color value to match (e.g., `0xFF121212`).
 */
@Serializable
data class ColorMappingDefinition(val name: String, @SerialName("import") val importPackage: String, val value: String)
