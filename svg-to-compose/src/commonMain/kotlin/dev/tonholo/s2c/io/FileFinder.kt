package dev.tonholo.s2c.io

import okio.Path

interface FileFinder {
    /**
     * Finds SVG or XML files to process within a given directory.
     *
     * @param from The starting directory to search for files.
     * @param recursive If true, the search will be performed recursively through subdirectories.
     * @param maxDepth The maximum depth to search when recursive is true. Null means no limit.
     * @param exclude A regular expression to exclude files from the results. Null means no files are excluded.
     *
     * @return A list of Path objects representing the files found to process.
     */
    fun findFilesToProcess(
        from: Path,
        recursive: Boolean,
        maxDepth: Int?,
        exclude: Regex?,
    ): List<Path>
}
