package dev.tonholo.s2c.cli.update

import okio.Path
import okio.Path.Companion.toPath

private const val CACHE_LEAF = "s2c"
private const val XDG_CACHE_HOME = "XDG_CACHE_HOME"
private const val HOME = "HOME"
private const val USERPROFILE = "USERPROFILE"
private const val DOT_CACHE = ".cache"
private const val FALLBACK_CACHE_DIR = ".s2c/cache"

/**
 * Resolves the directory where the update-check cache should live.
 *
 * Resolution order, picking the first non-blank variable:
 *
 * 1. `XDG_CACHE_HOME/s2c` (Linux/macOS XDG spec).
 * 2. `HOME/.cache/s2c` (POSIX home).
 * 3. `USERPROFILE/.cache/s2c` (Windows home).
 * 4. `.s2c/cache` relative to the current working directory, used only when
 *    none of the variables above are available.
 *
 * Keeping resolution behind an injectable [envReader] lets tests exercise
 * every branch without touching the real environment.
 */
class CacheDirResolver(private val envReader: (String) -> String?) {
    fun resolve(): Path {
        val xdg = envReader(XDG_CACHE_HOME).nonBlankOrNull()
        if (xdg != null) return xdg.toPath() / CACHE_LEAF

        val home = envReader(HOME).nonBlankOrNull()
            ?: envReader(USERPROFILE).nonBlankOrNull()
        if (home != null) return home.toPath() / DOT_CACHE / CACHE_LEAF

        return FALLBACK_CACHE_DIR.toPath()
    }

    private fun String?.nonBlankOrNull(): String? = this?.trim()?.takeIf { it.isNotEmpty() }
}
