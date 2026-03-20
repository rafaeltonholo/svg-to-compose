package dev.tonholo.s2c.website.state.playground

/** Holds the full mutable state for the playground section. */
internal data class PlaygroundState(
    val selectedSample: Int = 0,
    val inputCode: String = samples[0].svgCode,
    val activePanel: Int = 0,
    val inputMode: String = "paste",
    val extension: String = "svg",
    val options: PlaygroundOptions = PlaygroundOptions(),
    val convertedKotlinCode: String = "",
    val iconFileContentsJson: String? = null,
    val isConverting: Boolean = false,
    val conversionProgress: String = "",
    val conversionError: String? = null,
) {
    companion object {
        // region Sample Data
        internal data class SampleData(
            val name: String,
            val svgCode: String,
        )

        @Suppress("MaxLineLength")
        internal val samples = listOf(
            SampleData(
                name = "Heart",
                svgCode = """<svg viewBox="0 0 24 24">
  <path fill="#E91E63"
    d="M12 21.35l-1.45-1.32
       C5.4 15.36 2 12.28
       2 8.5 2 5.42 4.42 3
       7.5 3c1.74 0 3.41.81
       4.5 2.09C13.09 3.81
       14.76 3 16.5 3 19.58
       3 22 5.42 22 8.5c0
       3.78-3.4 6.86-8.55
       11.54L12 21.35z"/>
</svg>""",
            ),
            SampleData(
                name = "Star",
                svgCode = """<svg viewBox="0 0 24 24">
  <path fill="#FFD700"
    d="M12 2l3.09 6.26L22
       9.27l-5 4.87L18.18
       22 12 18.27 5.82 22
       6.91 14.14 2 9.27l6.91
       -1.01L12 2z"/>
</svg>""",
            ),
            SampleData(
                name = "Android",
                svgCode = """<svg viewBox="0 0 24 24">
  <path fill="#3DDC84"
    d="M6 18c0 .55.45 1 1
       1h1v3.5c0
       .83.67 1.5 1.5 1.5s1.5
       -.67 1.5-1.5V19h2v3.5c0
       .83.67 1.5 1.5 1.5s1.5
       -.67 1.5-1.5V19h1c.55
       0 1-.45 1-1V8H6v10z"/>
</svg>""",
            ),
            SampleData(
                name = "Kotlin",
                svgCode = """<svg viewBox="0 0 24 24">
  <polygon fill="#7F52FF"
    points="0,0 12,0 0,12"/>
  <polygon fill="#7F52FF"
    points="0,12 12,0 24,0
            24,12 12,24 0,24"/>
</svg>""",
            ),
        )

        // endregion
    }
}
