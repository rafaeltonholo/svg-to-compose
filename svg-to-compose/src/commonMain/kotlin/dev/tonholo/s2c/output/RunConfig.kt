package dev.tonholo.s2c.output

import dev.tonholo.s2c.parser.ParserConfig

/**
 * Display-oriented snapshot of the parameters for a conversion run.
 *
 * This is intentionally a separate type from [dev.tonholo.s2c.runtime.S2cConfig]
 * and [ParserConfig]. While those types control runtime behavior and may be mutable
 * or extended over time, [RunConfig] captures exactly the information that output
 * renderers need to display in headers and summaries. Keeping it decoupled avoids
 * leaking internal configuration details into the rendering layer.
 *
 * @property inputPath the source file or directory path.
 * @property outputPath the destination file or directory path.
 * @property packageName the Kotlin package for generated code.
 * @property optimizationEnabled whether SVG/AVG optimization is active.
 * @property recursive whether directory traversal is recursive.
 */
data class RunConfig(
    val inputPath: String,
    val outputPath: String,
    val packageName: String,
    val optimizationEnabled: Boolean,
    val recursive: Boolean,
) {
    companion object {
        /**
         * Creates a [RunConfig] from the runtime configuration objects.
         *
         * This is the single translation site between internal config types and the
         * display-oriented [RunConfig].
         *
         * @param config the parser configuration for this run.
         * @param inputPath the source file or directory path.
         * @param outputPath the destination file or directory path.
         * @param recursive whether directory traversal is recursive.
         */
        fun from(config: ParserConfig, inputPath: String, outputPath: String, recursive: Boolean): RunConfig =
            RunConfig(
                inputPath = inputPath,
                outputPath = outputPath,
                packageName = config.pkg,
                optimizationEnabled = config.optimize,
                recursive = recursive,
            )
    }
}
