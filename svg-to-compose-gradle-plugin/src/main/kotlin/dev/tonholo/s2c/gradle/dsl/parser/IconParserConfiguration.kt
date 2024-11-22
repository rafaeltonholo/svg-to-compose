package dev.tonholo.s2c.gradle.dsl.parser

import dev.tonholo.s2c.annotations.DelicateSvg2ComposeApi
import dev.tonholo.s2c.gradle.dsl.ProcessorConfiguration
import dev.tonholo.s2c.gradle.dsl.IconVisibility
import org.gradle.api.provider.Property

/**
 * Configuration for parsing an icon. This interface provides
 * options to customize the generation process of icon resources.
 */
interface IconParserConfiguration {
    /**
     * The visibility modifier of the generated icon class.
     */
    val iconVisibility: Property<IconVisibility>

    /**
     * Adds the `internal` modifier to the generated `ImageVector`.
     */
    fun makeInternal()

    /**
     * Sets the receiver type for the generated `ImageVector` property.
     *
     * @param fullQualifier The fully qualified name of the receiver type.
     */
    fun receiverType(fullQualifier: String)

    /**
     * Adds the Material `Icons` object as the receiver type of the
     * generated `ImageVector` property.
     */
    fun addToMaterialIcons()

    /**
     * Minifies the generated `ImageVector` property by removing the
     * explanation comments.
     */
    fun minify()

    /**
     * Disables the generation of preview images for the icons.
     */
    fun noPreview()

    /**
     * Sets the theme to be used for the generated preview images.
     *
     * Remarks:
     *  - The theme is required unless you remove the preview generation.
     *
     * @param fullQualifier The fully qualified name of the theme.
     */
    fun theme(fullQualifier: String)

    /**
     * Maps the original icon name to a custom name. Useful when the icon's
     * filename contains recurring names.
     *
     * E.g:
     * A file named `my_awesome_logo_filled.svg`, without the `mapIconNameTo()`
     * function would generated a ImageVector named `MyAwesomeLogoFilled`.
     *
     * By applying the `mapIconNameTo()` function, as shown bellow:
     * ```kotlin
     * svgToCompose.processor {
     *     val filled by creating {
     *          val themePackage = "my.awesome.app.theme"
     *          from(rootProject.layout.projectDirectory.dir("assets/icons"))
     *          destinationPackage("$themePackage.icons.filled")
     *          optimize(false)
     *          icons {
     *              theme("$themePackage.MyAwesomeAppTheme")
     *              mapIconNameTo { iconName ->
     *                  iconName.replace("_filled".toRegex(), "")
     *              }
     *          }
     *     }
     * }
     * ```
     * Whenever a file inside the `assets/icons` folder contains `_filled`,
     * the `Filled` won't be present in the generated `ImageVector` property's
     * name. Thus, the same `my_awesome_logo_filled.svg` would generated an
     * `ImageVector` named `MyAwesomeLogo` instead.
     *
     * @param mapper A function that takes the original icon name
     * and returns the custom name.
     */
    fun mapIconNameTo(mapper: (String) -> String)

    /**
     * Excludes icons based on their file names matching the given patterns
     * from the generation process.
     *
     * Considering the following plugin configuration:
     * ```kotlin
     * svgToCompose {
     *     processor {
     *         val outlined by creating {
     *              val themePackage = "my.awesome.app.theme"
     *              from(rootProject.layout.projectDirectory.dir("assets/icons"))
     *              destinationPackage("$themePackage.icons.outlined")
     *              optimize(false)
     *              icons {
     *                  theme("$themePackage.MyAwesomeAppTheme")
     *                  exclude("""[^/]+_filled(_*)[^/]+""".toRegex())
     *              }
     *         }
     *     }
     * }
     * ```
     * In this example, any SVG or XML file inside the `assets/icons` folder
     * containing `_filled` in its name will be excluded from the generation process.
     * Files like `ic_home_filled.svg` would be excluded, while files like `ic_home.svg`
     * or `ic_search_outlined.svg` would be included and considered as 'outlined' icons.
     *
     * @param patterns Regex patterns used to match against SVG or XML file names to
     * exclude them from the generation process.
     */
    fun exclude(vararg patterns: Regex)

    /**
     * Persists all generated files inside the project's source set, using the given
     * [ProcessorConfiguration.destinationPackage] instead of the build generated folder.
     *
     * By default, generated files are placed in the build directory. This function
     * allows you to change this behavior and store the generated files directly within
     * your project's source code.
     *
     * **Note:**
     * - Using this option might require you to manually refresh or rebuild your project
     * in your IDE to recognize the newly generated files. It's also important to consider
     * potential implications for version control and build reproducibility.
     *
     * **IMPORTANT:**
     * - By marking a icon generation as persistent, you understand that any changes made
     * in the target files are going to be overridden by the plugin.
     */
    @DelicateSvg2ComposeApi
    fun persist()
}
