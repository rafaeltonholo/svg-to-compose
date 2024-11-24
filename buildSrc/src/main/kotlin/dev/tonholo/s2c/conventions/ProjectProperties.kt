package dev.tonholo.s2c.conventions

import org.gradle.api.Project
import java.io.IOException
import java.util.Properties

internal abstract class ProjectProperties(
    private val propertiesName: String,
    private val ownerPluginName: String,
) {
    private var _properties: Properties? = null
    val properties
        get() = requireNotNull(_properties) {
            buildString {
                append("Missing $propertiesName initialization. ")
                append("Did you miss applying the '$ownerPluginName' in the current project?")
            }
        }

    open fun init(project: Project) {
        val propertiesFile = project
            .file(propertiesName)
            .takeIf { it.exists() }
        check(propertiesFile != null) {
            "The $propertiesName file is missing in the root project."
        }
        try {
            propertiesFile
                .reader()
                .use { reader ->
                    _properties = Properties().apply { load(reader) }
                }
        } catch (e: IOException) {
            throw IllegalStateException("Failed to read properties from app.properties", e)
        }
    }

    inline fun forEach(action: (Map.Entry<String, Any>) -> Unit) {
        properties
            .mapKeys { (key, _) -> key.toString() }
            .forEach(action)
    }
}
