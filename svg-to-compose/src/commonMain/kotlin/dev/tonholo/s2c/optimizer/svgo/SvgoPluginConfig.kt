package dev.tonholo.s2c.optimizer.svgo

/**
 * Platform-agnostic description of an SVGO configuration.
 *
 * This is the single source of truth for all SVGO settings used across
 * the project. Platform-specific code renders it to the appropriate
 * format:
 * - CLI path: [toEsmModuleContent] → JS module string for the `svgo` CLI
 * - Browser path: `toBrowserConfig()` (jsMain) → dynamic JS object for `svgo/browser`
 *
 * @property js2svg output formatting options.
 * @property plugins ordered list of SVGO plugins to apply.
 */
data class SvgoPluginConfig(val js2svg: Js2SvgConfig = Js2SvgConfig(), val plugins: List<SvgoPlugin>) {
    data class Js2SvgConfig(val pretty: Boolean = true)
}

/**
 * Describes a single SVGO plugin and its parameters.
 *
 * Each variant maps to a specific SVGO plugin. The [name] property
 * matches the plugin identifier expected by SVGO.
 */
sealed interface SvgoPlugin {
    val name: String

    /**
     * The built-in `preset-default` plugin bundle with specific overrides.
     *
     * @property overrides map of plugin names to enable/disable within the preset.
     */
    data class PresetDefault(val overrides: Map<String, Boolean>) : SvgoPlugin {
        override val name: String = "preset-default"
    }

    /**
     * Configures path data optimization.
     *
     * @property leadingZero whether to keep leading zeros in decimal numbers.
     * @property floatPrecision number of decimal places for floating point values.
     */
    data class ConvertPathData(val leadingZero: Boolean, val floatPrecision: Int) : SvgoPlugin {
        override val name: String = "convertPathData"
    }

    /**
     * Custom plugin that adds `fill="#000"` to shapes missing an explicit fill.
     *
     * SVG shapes default to black fill per spec, but some tools omit the attribute.
     * This plugin makes the default explicit so downstream parsing doesn't lose it.
     *
     * @property shapes element names to target (e.g. "rect", "circle").
     */
    data class AddDefaultFillToShapes(val shapes: List<String>) : SvgoPlugin {
        override val name: String = "addDefaultFillToShapes"
        val description: String = "Adds a default filling color for shapes"
    }

    /**
     * Overrides the built-in `convertShapeToPath` plugin to skip shapes
     * with `stroke-dasharray` (which s2c doesn't support on paths).
     *
     * In Node.js, this plugin dynamically loads SVGO's built-in implementation
     * via `createRequire` and wraps it with the dasharray check. In browser
     * environments this plugin is skipped since `createRequire` is unavailable.
     *
     * @property convertArcs whether to convert arc commands.
     * @property floatPrecision decimal precision for coordinates.
     */
    data class OverrideConvertShapeToPath(val convertArcs: Boolean, val floatPrecision: Int) : SvgoPlugin {
        override val name: String = "convertShapeToPath"
        val description: String =
            "Overrides the convertShapeToPath plugin by ignoring elements with stroke-dasharray"
    }
}

/**
 * The default SVGO configuration used by s2c for both CLI and browser paths.
 */
val DefaultSvgoConfig = SvgoPluginConfig(
    plugins = listOf(
        SvgoPlugin.PresetDefault(
            overrides = mapOf(
                "convertShapeToPath" to false,
                "convertPathData" to false,
            ),
        ),
        SvgoPlugin.ConvertPathData(
            leadingZero = false,
            floatPrecision = 2,
        ),
        SvgoPlugin.AddDefaultFillToShapes(
            shapes = listOf("rect", "circle", "line", "polygon", "polyline", "ellipse"),
        ),
        SvgoPlugin.OverrideConvertShapeToPath(
            convertArcs = true,
            floatPrecision = 2,
        ),
    ),
)
