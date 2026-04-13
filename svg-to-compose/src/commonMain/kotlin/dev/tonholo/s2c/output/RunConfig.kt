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
 * @property recursiveDepth the maximum depth for recursive directory traversal.
 * @property noTui whether TUI (terminal user interface) is disabled.
 */
data class RunConfig(
    val inputPath: String,
    val outputPath: String,
    val parserConfig: ParserConfig,
    val packageName: String,
    val optimizationEnabled: Boolean,
    val recursive: Boolean,
    val recursiveDepth: Int = 0,
    val noTui: Boolean = false,
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
         * @param recursiveDepth the maximum depth for recursive directory traversal.
         */
        fun from(
            config: ParserConfig,
            inputPath: String,
            outputPath: String,
            recursive: Boolean,
            recursiveDepth: Int = 0,
        ): RunConfig = RunConfig(
            inputPath = inputPath,
            outputPath = outputPath,
            parserConfig = config,
            packageName = config.pkg,
            optimizationEnabled = config.optimize,
            recursive = recursive,
            recursiveDepth = recursiveDepth,
        )
    }
}
