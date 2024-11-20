package dev.tonholo.s2c.conventions

import org.gradle.api.Project
import java.io.IOException
import java.util.Properties

private const val GROUP = "dev.tonholo.s2c"

internal object AppProperties {
    private var _appProperties: Properties? = null
    private val appProperties
        get() = requireNotNull(_appProperties) {
            "Missing app.properties initialization. " +
                "Did you miss applying the 'dev.tonholo.s2c.conventions.common' in the current project?"
        }
    val group: String get() = GROUP
    val version: String
        get() = requireNotNull(appProperties["VERSION"]?.toString()) {
            "VERSION property not found in app.properties"
        }

    fun init(project: Project) {
        val propertiesFile = project
            .file("app.properties")
            .takeIf { it.exists() }
        check(propertiesFile != null) {
            "The app.properties file is missing in the root project."
        }
        try {
            propertiesFile
                .reader()
                .use { reader ->
                    _appProperties = Properties().apply { load(reader) }
                }
        } catch (e: IOException) {
            throw IllegalStateException("Failed to read properties from app.properties", e)
        }
    }

    inline fun forEach(action: (Map.Entry<String, Any>) -> Unit) {
        appProperties
            .mapKeys { (key, _) -> key.toString() }
            .forEach(action)
    }
}
