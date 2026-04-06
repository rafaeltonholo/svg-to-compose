package dev.tonholo.s2c.cli.update

/**
 * Represents a semantic version with major, minor, and patch components.
 *
 * Supports parsing from both `vX.Y.Z` and `X.Y.Z` formats.
 */
data class SemVer(
    val major: Int,
    val minor: Int,
    val patch: Int,
) : Comparable<SemVer> {

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

        /**
         * Parses a version string in `vX.Y.Z` or `X.Y.Z` format.
         *
         * @param version the version string to parse.
         * @return a [SemVer] instance, or null if the format is invalid.
         */
        fun parse(version: String): SemVer? {
            val match = VERSION_REGEX.matchEntire(version.trim()) ?: return null
            val (major, minor, patch) = match.destructured
            return SemVer(
                major = major.toIntOrNull() ?: return null,
                minor = minor.toIntOrNull() ?: return null,
                patch = patch.toIntOrNull() ?: return null,
            )
        }
    }
}
