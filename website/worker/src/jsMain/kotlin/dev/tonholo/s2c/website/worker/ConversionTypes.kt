package dev.tonholo.s2c.website.worker

import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.optimizer.ContentOptimizer
import dev.tonholo.s2c.parser.ParserConfig
import kotlinx.serialization.Serializable

/**
 * Input payload sent from the UI to the conversion worker.
 *
 * @property svgContent Raw SVG or AVG markup to convert.
 * @property iconName Name used for the generated Kotlin icon.
 * @property fileType Format identifier (`"svg"` or `"avg"`).
 * @property optimize Whether to run SVGO optimisation before parsing.
 * @property pkg Kotlin package name for the generated code.
 * @property theme Fully-qualified theme class reference.
 * @property noPreview If `true`, omits `@Preview` annotation from generated code.
 * @property makeInternal If `true`, marks the generated icon `internal`.
 * @property minified If `true`, emits compact code without extra formatting.
 * @property kmpPreview If `true`, emits KMP-compatible preview annotations.
 * @property receiverType Optional receiver type (e.g. `Icons.Filled`).
 */
@Serializable
data class ConversionInput(
    val svgContent: String,
    val iconName: String,
    val fileType: String,
    val optimize: Boolean,
    val pkg: String,
    val theme: String,
    val noPreview: Boolean,
    val makeInternal: Boolean,
    val minified: Boolean,
    val kmpPreview: Boolean = false,
    val receiverType: String? = null,
)

/**
 * Output messages emitted by the conversion worker back to the UI.
 */
@Serializable
sealed interface ConversionOutput {
    @Serializable
    data class Progress(val stage: String, val percent: Int) : ConversionOutput

    @Serializable
    data class Success(
        val kotlinCode: String,
        val iconFileContentsJson: String? = null,
    ) : ConversionOutput

    @Serializable
    data class Error(val message: String) : ConversionOutput
}

/** Maps [ConversionInput] fields to a [ParserConfig]. */
internal fun ConversionInput.toParserConfig(): ParserConfig = ParserConfig(
    pkg = pkg,
    theme = theme,
    optimize = optimize,
    receiverType = receiverType?.takeIf { it.isNotBlank() },
    addToMaterial = false,
    kmpPreview = kmpPreview,
    noPreview = noPreview,
    makeInternal = makeInternal,
    minified = minified,
)

/** Resolves the string [ConversionInput.fileType] to a [FileType] enum entry. */
internal fun ConversionInput.resolveFileType(): FileType =
    FileType.entries.find { it.name.equals(fileType, ignoreCase = true) } ?: FileType.Svg

/** Returns [JsContentOptimizer] when [ConversionInput.optimize] is true, null otherwise. */
internal fun ConversionInput.resolveOptimizer(): ContentOptimizer? =
    if (optimize) JsContentOptimizer else null
