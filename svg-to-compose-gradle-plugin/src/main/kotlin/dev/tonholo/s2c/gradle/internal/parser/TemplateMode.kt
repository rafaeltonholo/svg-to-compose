package dev.tonholo.s2c.gradle.internal.parser

/**
 * How the icon parser resolves the template that shapes the generated code.
 */
internal enum class TemplateMode {
    /** Nothing was configured: an `s2c.template.toml` is discovered from the output directory. */
    Auto,

    /** Templates are turned off, including discovery. */
    Disabled,

    /** A template file was configured explicitly. */
    ExplicitFile,
}
