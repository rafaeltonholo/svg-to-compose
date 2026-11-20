package dev.tonholo.s2c.gradle.dsl.parser

import org.gradle.api.file.RegularFile

/**
 * Template options of the `icons` block. They are part of [IconParserConfiguration],
 * so they are used the same way inside `icons { }`.
 */
interface IconTemplateConfiguration {
    /**
     * Sets the path to an `s2c.template.toml` file for output customization.
     *
     * The template file allows users to control the generated Kotlin code shape —
     * using custom builder functions, receiver types, imports, and property patterns.
     *
     * Usage:
     * ```kotlin
     * icons {
     *     templateFile(layout.projectDirectory.file("config/s2c.template.toml"))
     * }
     * ```
     *
     * @param path A [RegularFile] pointing to the template file, typically obtained
     *   via `layout.projectDirectory.file(...)`.
     */
    fun templateFile(path: RegularFile)

    /**
     * Generates the icons without any template, skipping the auto-discovery of
     * `s2c.template.toml` files.
     *
     * When neither [templateFile] nor this function is called, the configuration
     * inherits the template settings from the `common` configuration, or discovers
     * an `s2c.template.toml` file by walking up from the output directory.
     *
     * Usage:
     * ```kotlin
     * icons {
     *     noTemplate()
     * }
     * ```
     */
    fun noTemplate()
}
