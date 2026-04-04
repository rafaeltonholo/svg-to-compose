package dev.tonholo.s2c.website.components.organisms.docs

internal data class CliOption(val flag: String, val type: String, val description: String) {
    companion object {
        val options = setOf(
            CliOption(flag = "-o, --output", type = "String", description = "Output file or directory path"),
            CliOption(flag = "-p, --package", type = "String", description = "Kotlin package name for generated code"),
            CliOption(flag = "-t, --theme", type = "String", description = "Fully-qualified theme class name"),
            CliOption(
                flag = "-opt, --optimize",
                type = "Boolean",
                description = "Enable SVG/AVG optimization (default: `true`)",
            ),
            CliOption(
                flag = "-rt, --receiver-type",
                type = "String",
                description = "Receiver type (e.g. `Icons.Filled`)",
            ),
            CliOption(flag = "--add-to-material", type = "Boolean", description = "Add as extension to Material Icons"),
            CliOption(
                flag = "-np, --no-preview",
                type = "Boolean",
                description = "Skip generating `@Preview` composables",
            ),
            CliOption(
                flag = "--kmp",
                type = "Boolean",
                description = "Ensure output is KMP-compatible (default: `false`)",
            ),
            CliOption(flag = "--make-internal", type = "Boolean", description = "Mark generated symbols as internal"),
            CliOption(
                flag = "--minified",
                type = "Boolean",
                description = "Remove comments and inline method parameters",
            ),
            CliOption(flag = "-r, --recursive", type = "Boolean", description = "Recursively process sub-directories"),
            CliOption(
                flag = "--recursive-depth, --depth",
                type = "Int",
                description = "Depth level for recursive search (default: `10`)",
            ),
            CliOption(
                flag = "--exclude",
                type = "String...",
                description = "Regex pattern to exclude icons from processing",
            ),
            CliOption(
                flag = "--map-icon-name-from-to",
                type = "Pair...",
                description = "Replace icon name pattern (old to new)",
            ),
            CliOption(
                flag = "--template",
                type = "String",
                description = "Path to `s2c.template.toml` configuration file. When provided, the template " +
                    "is used to customise the generated Kotlin code.",
            ),
            CliOption(
                flag = "--no-template",
                type = "Boolean",
                description = "Disable template auto-discovery. By default, `s2c` walks up from the output " +
                    "directory looking for `s2c.template.toml`.",
            ),
        )
    }
}
