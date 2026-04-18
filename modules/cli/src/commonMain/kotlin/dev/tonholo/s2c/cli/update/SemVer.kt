package dev.tonholo.s2c.cli.update

/**
 * Represents a semantic version with major, minor, and patch components.
 *
 * Supports parsing from both `vX.Y.Z` and `X.Y.Z` formats.
 */
data class SemVer(val major: Int, val minor: Int, val patch: Int) : Comparable<SemVer> {

    override fun compareTo(other: SemVer): Int {
        val majorDiff = major.compareTo(other.major)
        if (majorDiff != 0) return majorDiff

        val minorDiff = minor.compareTo(other.minor)
        if (minorDiff != 0) return minorDiff

        return patch.compareTo(other.patch)
    }

    override fun toString(): String = "$major.$minor.$patch"

    companion object {
        private val VERSION_REGEX = Regex("""^v?(\d+)\.(\d+)\.(\d+)$""")
        private val PRE_RELEASE_REGEX = Regex("""^v?\d+\.\d+\.\d+[+-]""")

        /**
         * Returns true if the version string contains a pre-release suffix
         * (e.g. `-SNAPSHOT`, `-rc.1`) or build metadata (e.g. `+build.456`).
         */
        fun isPreRelease(version: String): Boolean =
            PRE_RELEASE_REGEX.containsMatchIn(version.trim())

        /**
         * Parses a version string in `vX.Y.Z` or `X.Y.Z` format.
         *
         * Pre-release suffixes (e.g. `-SNAPSHOT`, `-rc.1`) and build
         * metadata (e.g. `+build.456`) are stripped before parsing.
         *
         * @param version the version string to parse.
         * @return a [SemVer] instance, or null if the format is invalid.
         */
        fun parse(version: String): SemVer? {
            val normalized = version.trim().substringBefore('-').substringBefore('+')
            val match = VERSION_REGEX.matchEntire(normalized) ?: return null
            val (majorStr, minorStr, patchStr) = match.destructured
            val major = majorStr.toIntOrNull()
            val minor = minorStr.toIntOrNull()
            val patch = patchStr.toIntOrNull()
            if (major == null || minor == null || patch == null) return null
            return SemVer(major = major, minor = minor, patch = patch)
        }
    }
}
