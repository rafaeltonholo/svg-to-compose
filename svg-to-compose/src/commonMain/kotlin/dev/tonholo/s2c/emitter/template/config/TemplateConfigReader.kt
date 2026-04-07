package dev.tonholo.s2c.emitter.template.config

import com.rsicarelli.fakt.Fake
import dev.tonholo.s2c.emitter.editorconfig.EditorConfigReader
import dev.tonholo.s2c.io.FileManager
import dev.zacsweers.metro.Inject
import okio.Path

private const val TEMPLATE_FILENAME = "s2c.template.toml"

/**
 * Reads `s2c.template.toml` files by explicit path or auto-discovery.
 *
 * Auto-discovery walks up from the output directory looking for the
 * template file, following the same pattern as [EditorConfigReader].
 */
@Fake
interface TemplateConfigReader {
    /**
     * Reads and parses a template config from an explicit path.
     *
     * @param templatePath The path to the `s2c.template.toml` file.
     * @return The parsed [TemplateEmitterConfig].
     */
    fun resolve(templatePath: Path): TemplateEmitterConfig

    /**
     * Discovers a template config by walking up from [outputPath].
     *
     * @param outputPath The output file or directory path to start searching from.
     * @return The parsed [TemplateEmitterConfig], or `null` if no template file is found.
     */
    fun discover(outputPath: Path): TemplateEmitterConfig?
}

/**
 * Default implementation of [TemplateConfigReader].
 *
 * @property fileManager The file manager for checking file existence and reading content.
 */
@Inject
class DefaultTemplateConfigReader(private val fileManager: FileManager) : TemplateConfigReader {
    override fun resolve(templatePath: Path): TemplateEmitterConfig {
        val content = fileManager.readContent(templatePath)
        return TemplateConfigParser.parse(content)
    }

    override fun discover(outputPath: Path): TemplateEmitterConfig? {
        var dir: Path? = if (isDirectory(outputPath)) outputPath else outputPath.parent

        while (dir != null) {
            val configFile = dir / TEMPLATE_FILENAME
            if (fileManager.exists(configFile)) {
                val content = fileManager.readContent(configFile)
                return TemplateConfigParser.parse(content)
            }
            dir = dir.parent
        }

        return null
    }

    private fun isDirectory(path: Path): Boolean = try {
        fileManager.isDirectory(path)
    } catch (_: okio.IOException) {
        false
    }
}
