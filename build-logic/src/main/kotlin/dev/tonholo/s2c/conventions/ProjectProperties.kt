package dev.tonholo.s2c.conventions

import org.gradle.api.Project
import java.io.File
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
        val propertiesFile = findPropertiesFile(project.rootDir)
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

    /**
     * Walks up from [startDir] looking for [propertiesName].
     * This allows composite builds (e.g. modules/cli) to locate the
     * properties file that lives in the repository root.
     */
    private fun findPropertiesFile(startDir: File): File? {
        var dir: File? = startDir
        while (dir != null) {
            val candidate = File(dir, propertiesName)
            if (candidate.isFile) return candidate
            dir = dir.parentFile
        }
        return null
    }

    inline fun forEach(action: (Map.Entry<String, Any>) -> Unit) {
        properties
            .mapKeys { (key, _) -> key.toString() }
            .forEach(action)
    }
}
