package dev.tonholo.s2c.conventions

private const val GROUP = "dev.tonholo.s2c"

internal object AppProperties : ProjectProperties(
    propertiesName = "app.properties",
    ownerPluginName = "dev.tonholo.s2c.conventions.common",
) {
    val group: String get() = GROUP
    val version: String
        get() = requireNotNull(properties["VERSION"]?.toString()) {
            "VERSION property not found in app.properties"
        }
}
