package dev.tonholo.s2c.extensions

/**
 * Converts a [Float] to its string representation, ensuring that whole
 * numbers always include a decimal point (e.g., `10.0` instead of `10`).
 *
 * This is needed because [Float.toString] behaves differently across
 * Kotlin/JVM and Kotlin/JS for whole-number values:
 * - JVM: `10f.toString()` returns `"10.0"`
 * - JS:  `10f.toString()` returns `"10"`
 *
 * @return A string representation that always contains a decimal point
 * for whole-number values.
 */
fun Float.toStringConsistent(): String {
    val value = toString()
    return when {
        '.' in value || 'E' in value || 'e' in value || value == "NaN" || value.contains("Infinity") -> value
        else -> "$value.0"
    }
}
