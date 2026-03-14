package dev.tonholo.s2c.emitter.editorconfig

import dev.tonholo.s2c.emitter.FormatConfig
import dev.tonholo.s2c.emitter.editorconfig.EditorConfigParser.toFormatConfig
import dev.tonholo.s2c.io.FileManager
import dev.zacsweers.metro.Inject
import okio.Path

private const val EDITOR_CONFIG_FILENAME = ".editorconfig"

/**
 * Reads and merges `.editorconfig` files by walking up from a given output
 * directory to the filesystem root.
 *
 * Files closer to the output directory take precedence (child overrides
 * parent). The walk stops when a file declaring `root = true` is found
 * or the filesystem root is reached.
 *
 * @property fileManager The file manager used to check file existence and read content.
 */
@Inject
class EditorConfigReader(private val fileManager: FileManager) {
    /**
     * Resolves a [FormatConfig] by walking up from [outputPath] and merging
     * all `.editorconfig` files found along the way.
     *
     * @param outputPath The output file or directory path to start searching from.
     * @param defaults Fallback values for any properties not specified in `.editorconfig`.
     * @return A [FormatConfig] derived from the merged `.editorconfig` chain,
     *         or [defaults] if no `.editorconfig` files are found.
     */
    fun resolve(outputPath: Path, defaults: FormatConfig = FormatConfig()): FormatConfig {
        val configs = mutableListOf<EditorConfigParser.ParsedConfig>()
        var dir: Path? = if (isDirectory(outputPath)) outputPath else outputPath.parent

        while (dir != null) {
            val configFile = dir / EDITOR_CONFIG_FILENAME
            if (fileManager.exists(configFile)) {
                val content = fileManager.readContent(configFile)
                val parsed = EditorConfigParser.parse(content)
                configs.add(parsed)
                if (parsed.isRoot) break
            }
            dir = dir.parent
        }

        if (configs.isEmpty()) return defaults

        // Merge from root (last) to closest (first) so child overrides parent.
        val merged = configs.foldRight(EditorConfigParser.ParsedConfig()) { child, parent ->
            EditorConfigParser.merge(parent, child)
        }

        return merged.toFormatConfig(defaults)
    }

    private fun isDirectory(path: Path): Boolean = try {
        fileManager.isDirectory(path)
    } catch (_: okio.IOException) {
        false
    }
}
