package dev.tonholo.s2c

import dev.tonholo.s2c.ConversionStep.Complete
import dev.tonholo.s2c.ConversionStep.Error
import dev.tonholo.s2c.ConversionStep.Generating
import dev.tonholo.s2c.ConversionStep.Optimizing
import dev.tonholo.s2c.ConversionStep.Parsing
import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.emitter.CodeEmitterFactory
import dev.tonholo.s2c.emitter.template.config.TemplateEmitterConfig
import dev.tonholo.s2c.optimizer.ContentOptimizer
import dev.tonholo.s2c.parser.ContentParser
import dev.tonholo.s2c.parser.ParserConfig
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Converts vector graphic content into Kotlin Compose ImageVector code.
 *
 * This class orchestrates the conversion process from raw vector file content (SVG or AVG)
 * to Kotlin code that can be used in Compose applications. The conversion process consists
 * of multiple steps: optional optimization, parsing the vector content into a domain model,
 * and generating the corresponding Kotlin code.
 *
 * The conversion process is exposed as a Flow that emits [ConversionStep] instances,
 * allowing consumers to track progress and handle intermediate states such as optimization,
 * parsing, code generation, completion, or errors.
 */
interface Converter {
    /**
     * Converts vector icon content into Kotlin Compose code by processing it through optimization,
     * parsing, and code generation stages.
     *
     * This function emits conversion progress steps through a Flow, allowing consumers to track
     * the conversion process. The conversion pipeline includes optional optimization of the input
     * content, parsing the vector format into an intermediate representation, and generating
     * the final Kotlin code for Compose ImageVector.
     *
     * The conversion process follows these stages:
     * 1. [Optimizing] (optional) - if an optimizer is provided, the content is optimized
     * 2. [Parsing] - the vector content is parsed according to the file type
     * 3. [Generating] - Kotlin code is generated from the parsed icon
     * 4. [Complete] - emits the final conversion result
     *
     * If any error occurs during the conversion process, an [Error] step is emitted containing
     * the exception details.
     *
     * @param content the raw string content of the vector icon file
     * @param iconName the name to be used for the generated icon in Kotlin code
     * @param config the parser configuration containing package name, theme, and other generation options
     * @param fileType the type of vector file being converted (defaults to SVG)
     * @param optimizer optional optimizer to preprocess the content before parsing
     * @param templateEmitterConfig optional template configuration for custom code generation output
     * @return a Flow of ConversionStep instances representing the progress and result of the conversion
     */
    fun convert(
        content: String,
        iconName: String,
        config: ParserConfig,
        fileType: FileType,
        optimizer: ContentOptimizer?,
        templateEmitterConfig: TemplateEmitterConfig? = null,
    ): Flow<ConversionStep>
}

@Inject
class DefaultConverter(
    private val contentParsers: Map<FileType, ContentParser>,
    private val codeEmitterFactory: CodeEmitterFactory,
) : Converter {
    override fun convert(
        content: String,
        iconName: String,
        config: ParserConfig,
        fileType: FileType,
        optimizer: ContentOptimizer?,
        templateEmitterConfig: TemplateEmitterConfig?,
    ): Flow<ConversionStep> = flow {
        try {
            val optimizedContent = if (optimizer != null) {
                emit(Optimizing("Optimizing ${fileType.extension}..."))
                optimizer.optimize(content, fileType)
            } else {
                content
            }

            emit(Parsing("Parsing ${fileType.extension}..."))
            val parser = contentParsers[fileType]
                ?: error("No content parser registered for file type: ${fileType.extension}")
            val iconContents = parser.parse(optimizedContent, iconName, config)

            emit(Generating("Generating Kotlin code..."))
            val emitter = codeEmitterFactory.create(templateEmitterConfig = templateEmitterConfig)
            val kotlinCode = emitter.emit(iconContents)

            emit(
                Complete(
                    ConversionResult(
                        kotlinCode = kotlinCode,
                        iconFileContents = iconContents,
                    ),
                ),
            )
        } catch (
            // Broad catch required: parsers and emitters can throw various unchecked
            // exceptions (IllegalStateException, NumberFormatException, etc.) that must
            // all surface as ConversionStep.Error rather than crashing the flow.
            @Suppress("TooGenericExceptionCaught")
            e: Exception,
        ) {
            emit(Error(e))
        }
    }
}

/**
 * Represents a step in the conversion process of transforming icon files into Kotlin code.
 *
 * This sealed interface defines the possible states during the conversion workflow,
 * allowing consumers to track progress and handle different stages of the conversion pipeline.
 * Each step provides specific information relevant to that stage of the process.
 */
sealed interface ConversionStep {
    /**
     * Represents the optimization step in the icon conversion process.
     *
     * This step occurs when the icon file is being optimized before code generation.
     * Optimization typically involves reducing file size, removing unnecessary metadata,
     * or simplifying vector paths while maintaining visual fidelity.
     *
     * @property message A descriptive message about the current optimization operation being performed
     */
    data class Optimizing(val message: String) : ConversionStep

    /**
     * Represents the parsing step in the icon conversion process.
     *
     * This step occurs when the icon file is being parsed to extract its structure and content.
     * Parsing involves reading and interpreting the icon file format (such as SVG or other vector formats)
     * to build an internal representation that can be further processed in subsequent conversion steps.
     *
     * @property message A descriptive message providing information about the current parsing operation.
     */
    data class Parsing(val message: String) : ConversionStep

    /**
     * Represents the code generation step in the icon conversion process.
     *
     * This step occurs when the parsed and optimized icon data is being transformed into
     * Kotlin code. During generation, the internal representation of the icon is converted
     * into executable Kotlin code that can be used to render the icon programmatically.
     *
     * @property message A descriptive message providing information about the current code generation operation.
     */
    data class Generating(val message: String) : ConversionStep

    /**
     * Represents the final, successful completion step of the icon conversion process.
     *
     * This step indicates that the icon has been successfully converted from its source format
     * to Kotlin code. It contains the complete conversion result including the generated code
     * and all associated metadata from the original icon file.
     *
     * @property result The successful conversion result containing the generated Kotlin code
     *                  and original icon file metadata.
     */
    data class Complete(val result: ConversionResult) : ConversionStep

    /**
     * Represents a conversion step that encountered an error during processing.
     *
     * This data class encapsulates a failure state in a conversion pipeline by wrapping
     * a Throwable that caused the conversion to fail. It implements the ConversionStep
     * interface to maintain consistency with the conversion workflow pattern.
     *
     * @property error The throwable that caused the conversion step to fail
     */
    data class Error(val error: Throwable) : ConversionStep
}

/**
 * Represents the result of converting an icon file to Kotlin code.
 *
 * This class encapsulates both the generated Kotlin source code and the original
 * icon file contents, providing convenient access to commonly used properties
 * from the icon metadata.
 *
 * @property kotlinCode The generated Kotlin source code representing the icon.
 * @property iconFileContents The original icon file contents and metadata.
 */
data class ConversionResult(val kotlinCode: String, val iconFileContents: IconFileContents) {
    /** Shortcut for [IconFileContents.nodes]. */
    val nodes get() = iconFileContents.nodes

    /** Shortcut for [IconFileContents.width]. */
    val width get() = iconFileContents.width

    /** Shortcut for [IconFileContents.height]. */
    val height get() = iconFileContents.height

    /** Shortcut for [IconFileContents.viewportWidth]. */
    val viewportWidth get() = iconFileContents.viewportWidth

    /** Shortcut for [IconFileContents.viewportHeight]. */
    val viewportHeight get() = iconFileContents.viewportHeight
}
