package dev.tonholo.s2c.io

import dev.tonholo.s2c.domain.FileType
import okio.Path

/**
 * Returns true when this path ends with a supported vector file extension,
 * [FileType.Svg] or [FileType.Avg]. Matching is case-sensitive.
 */
fun Path.hasVectorFileExtension(): Boolean =
    name.endsWith(FileType.Svg.extension) || name.endsWith(FileType.Avg.extension)

/**
 * Decides whether a vector file is eligible for conversion. Both the full
 * directory scan and the Gradle plugin's incremental change scan apply this
 * same rule set so the two paths cannot drift.
 *
 * @param root The scan root. Directory segments between [root] and the file
 * are matched against [excludeDir]; [root] itself is never matched.
 * @param exclude A regex excluding files by name. Null excludes nothing.
 * @param excludeDir A regex matched against each directory segment of the
 * file's path relative to [root]. Null excludes nothing.
 */
fun Path.isEligibleForProcessing(
    root: Path,
    exclude: Regex?,
    excludeDir: Regex?,
): Boolean {
    val isNotExcluded = exclude == null || !name.matches(exclude)
    val isNotInExcludedDir = excludeDir == null || !isInExcludedDir(root = root, excludeDir = excludeDir)
    return hasVectorFileExtension() && isNotExcluded && isNotInExcludedDir
}

private fun Path.isInExcludedDir(root: Path, excludeDir: Regex): Boolean {
    var current = parent
    while (current != null && current != root) {
        if (current.name.matches(excludeDir)) return true
        current = current.parent
    }
    return false
}
