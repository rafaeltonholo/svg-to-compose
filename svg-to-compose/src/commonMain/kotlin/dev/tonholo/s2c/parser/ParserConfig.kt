package dev.tonholo.s2c.parser

/**
 * Configuration parameters for the Parser class.
 *
 * @property pkg The package name that the generated code is going to be in
 * @property theme The Compose theme. If the full qualifier is not provided,
 * you'll need to add the import to your code after generated.
 * E.g: `dev.tonholo.myapp.theme.MyAppTheme`
 * @property optimize if `true`, executes an SVG/AVG optimization before generating
 * the Compose Icon code
 * @property receiverType The type of the class or object that will be used as a receiver
 * for the extension Icon property in the generated code.
 *
 * E.g.: When passed a value as `MyIcons.Filled`, the icon result will be:
 * ```kotlin
 * val MyIcons.Filled.IconName: ImageVector get() = <implementation>
 * ```
 * For best results, add the full qualifier on this option, otherwise you'll need to add
 * the import to the generated code since we can't identify which is the package of the
 * receiver type.
 * @property addToMaterial if `true`, icons will be added to the Material-UI icon set
 * E.g:
 * ```kotlin
 * import androidx.compose.material.icons.Icons
 * val Icons.Filled.MyIcon: ImageVector get() = <implementation>
 * ```
 * @property kmpPreview if `true`, a KMP compatible `@Preview Composable` function will be
 * generated for the parsed icon.
 * @property noPreview if `true`, no `@Preview Composable` function will be generated for
 * the parsed icon.
 * @property makeInternal if `true`, the generated icon will be set as `internal`, hiding it
 * from outside modules
 * @property minified if `true`, minifies the output removing all generated comments and
 * inlining the path functions parameters
 * @property exclude regex to exclude some icons from the parsing
 * @property silent if `true`, no console output will be displayed.
 * @property keepTempFolder if `true`, the [dev.tonholo.s2c.Processor] won't request to
 * delete the temp folder. Useful when running parallel execution.
 */
data class ParserConfig(
    val pkg: String,
    val theme: String,
    val optimize: Boolean,
    val receiverType: String?,
    val addToMaterial: Boolean,
    val kmpPreview: Boolean,
    val noPreview: Boolean,
    val makeInternal: Boolean,
    val minified: Boolean,
    val exclude: Regex? = null,
    val silent: Boolean = false,
    val keepTempFolder: Boolean = false,
)
