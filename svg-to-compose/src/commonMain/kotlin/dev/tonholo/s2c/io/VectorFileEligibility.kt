package dev.tonholo.s2c.io

import dev.tonholo.s2c.domain.FileType
import okio.Path

/**
 * Returns true when this path ends with a supported vector file extension,
 * [FileType.Svg] or [FileType.Avg]. Matching is case-sensitive unless
 * [ignoreCase] is true.
 */
fun Path.hasVectorFileExtension(ignoreCase: Boolean = false): Boolean =
    name.endsWith(FileType.Svg.extension, ignoreCase = ignoreCase) ||
        name.endsWith(FileType.Avg.extension, ignoreCase = ignoreCase)

/**
 * Decides whether a vector file is eligible for conversion. Both the full
 * directory scan and the Gradle plugin's incremental change scan apply this
 * same rule set so the two paths cannot drift.
 *
 * @param root The scan root. Depth and [excludeDir] are evaluated against the
 * file's path relative to [root]; [root] itself is never matched.
 * @param recursive Whether nested directories are scanned. When false, only
 * files directly inside [root] are eligible.
 * @param maxDepth The maximum nesting depth when [recursive] is true. Files
 * directly inside [root] are at depth 0. Null means unlimited.
 * @param exclude A regex excluding files by name. Null excludes nothing.
 * @param excludeDir A regex matched against each directory segment of the
 * file's path relative to [root]. Null excludes nothing.
 */
fun Path.isEligibleForProcessing(
    root: Path,
    recursive: Boolean,
    maxDepth: Int?,
    exclude: Regex?,
    excludeDir: Regex?,
): Boolean {
    val isWithinDepthLimit = isWithinDepthLimit(root = root, recursive = recursive, maxDepth = maxDepth)
    val isNotExcluded = exclude == null || !name.matches(exclude)
    val isNotInExcludedDir = excludeDir == null || !isInExcludedDir(root = root, excludeDir = excludeDir)
    return hasVectorFileExtension() && isWithinDepthLimit && isNotExcluded && isNotInExcludedDir
}

private fun Path.isWithinDepthLimit(
    root: Path,
    recursive: Boolean,
    maxDepth: Int?,
): Boolean {
    val depthLimit = if (recursive) maxDepth else 0
    return depthLimit == null || depthRelativeTo(root) <= depthLimit
}

private fun Path.depthRelativeTo(root: Path): Int = segments.size - root.segments.size - 1

private fun Path.isInExcludedDir(root: Path, excludeDir: Regex): Boolean {
    var current = parent
    while (current != null && current != root) {
        if (current.name.matches(excludeDir)) return true
        current = current.parent
    }
    return false
}
