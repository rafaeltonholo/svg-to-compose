package dev.tonholo.s2c.website.worker

import com.varabyte.kobweb.serialization.createIOSerializer
import com.varabyte.kobweb.worker.OutputDispatcher
import com.varabyte.kobweb.worker.WorkerFactory
import com.varabyte.kobweb.worker.WorkerStrategy
import dev.tonholo.s2c.ConversionStep
import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.emitter.template.config.TemplateConfigParser
import dev.tonholo.s2c.emitter.template.config.TemplateEmitterConfig
import dev.tonholo.s2c.optimizer.ContentOptimizer
import dev.tonholo.s2c.parser.ParserConfig
import dev.tonholo.s2c.website.worker.inject.WorkerGraph
import dev.zacsweers.metro.createGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

/**
 * Factory class responsible for creating workers that convert SVG/AVG icon files into Kotlin Compose ImageVector code.
 *
 * This factory implements the WorkerFactory interface to provide icon conversion capabilities in a worker context.
 * It manages the conversion pipeline by coordinating between input parsing, icon conversion, and output serialization.
 * The factory maintains a dependency graph for accessing required components like the converter and JSON serializer,
 * and uses a coroutine scope with Main dispatcher and SupervisorJob for handling asynchronous conversion operations.
 *
 * The created workers process ConversionInput data containing SVG/AVG content and configuration parameters,
 * executing the conversion through multiple stages (optimizing, parsing, generating) and emitting progress
 * updates as ConversionOutput. The conversion results include the generated Kotlin code and optional icon
 * file contents in JSON format.
 *
 * The factory automatically handles the translation of conversion inputs into appropriate parser configurations,
 * file type resolution, and optimizer setup based on the provided input parameters.
 */
internal class IconConvertWorkerFactory : WorkerFactory<ConversionInput, ConversionOutput> {
    private val graph = createGraph<WorkerGraph>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun createStrategy(postOutput: OutputDispatcher<ConversionOutput>) =
        WorkerStrategy<ConversionInput> { input ->
            val config = input.toParserConfig()
            val fileType = input.resolveFileType()
            val optimizer = input.resolveOptimizer()
            val templateConfig = input.templateToml
                ?.takeIf { it.isNotBlank() }
                ?.let { toml ->
                    runCatching { TemplateConfigParser.parse(toml) }
                        .onFailure { e ->
                            postOutput(ConversionOutput.Error("Template error: ${e.message}"))
                        }
                        .getOrNull() ?: return@WorkerStrategy
                }

            coroutineScope.launch {
                if (templateConfig != null) {
                    convertWithTemplate(input, config, fileType, optimizer, templateConfig, postOutput)
                } else {
                    graph.converter.convert(
                        content = input.svgContent,
                        iconName = input.iconName,
                        config = config,
                        fileType = fileType,
                        optimizer = optimizer,
                    ).collect { step ->
                        postOutput(step.toConversionOutput(graph.json))
                    }
                }
            }
        }

    private suspend fun convertWithTemplate(
        input: ConversionInput,
        config: ParserConfig,
        fileType: FileType,
        optimizer: ContentOptimizer?,
        templateConfig: TemplateEmitterConfig,
        postOutput: OutputDispatcher<ConversionOutput>,
    ) {
        runCatching {
            convertWithTemplateInternal(input, config, fileType, optimizer, templateConfig, postOutput)
        }.onFailure { e ->
            postOutput(ConversionOutput.Error(e.message ?: "Unknown error"))
        }
    }

    private suspend fun convertWithTemplateInternal(
        input: ConversionInput,
        config: ParserConfig,
        fileType: FileType,
        optimizer: ContentOptimizer?,
        templateConfig: TemplateEmitterConfig,
        postOutput: OutputDispatcher<ConversionOutput>,
    ) {
        val optimizedContent = if (optimizer != null) {
            postOutput(ConversionOutput.Progress("Optimizing...", percent = PROGRESS_OPTIMIZING))
            optimizer.optimize(input.svgContent, fileType)
        } else {
            input.svgContent
        }

        postOutput(ConversionOutput.Progress("Parsing...", percent = PROGRESS_PARSING))
        val parser = graph.contentParsers[fileType]
            ?: error("No content parser registered for file type: ${fileType.extension}")
        val iconContents = parser.parse(optimizedContent, input.iconName, config)

        postOutput(ConversionOutput.Progress("Generating...", percent = PROGRESS_GENERATING))
        val emitter = graph.codeEmitterFactory.create(templateEmitterConfig = templateConfig)
        val kotlinCode = emitter.emit(iconContents)

        postOutput(
            ConversionOutput.Success(
                kotlinCode = kotlinCode,
                iconFileContentsJson = graph.json.encodeToString(
                    IconFileContents.serializer(),
                    iconContents,
                ),
            ),
        )
    }

    override fun createIOSerializer() = Json.createIOSerializer<ConversionInput, ConversionOutput>()
}

/** Progress percentage reported during the optimisation stage. */
private const val PROGRESS_OPTIMIZING = 20

/** Progress percentage reported during the parsing stage. */
private const val PROGRESS_PARSING = 50

/** Progress percentage reported during the code-generation stage. */
private const val PROGRESS_GENERATING = 80

/** Maps a [ConversionStep] to a [ConversionOutput] for the UI. */
private fun ConversionStep.toConversionOutput(json: Json): ConversionOutput = when (this) {
    is ConversionStep.Optimizing -> ConversionOutput.Progress(message, percent = PROGRESS_OPTIMIZING)

    is ConversionStep.Parsing -> ConversionOutput.Progress(message, percent = PROGRESS_PARSING)

    is ConversionStep.Generating -> ConversionOutput.Progress(message, percent = PROGRESS_GENERATING)

    is ConversionStep.Complete -> ConversionOutput.Success(
        kotlinCode = result.kotlinCode,
        iconFileContentsJson = json.encodeToString(
            IconFileContents.serializer(),
            result.iconFileContents,
        ),
    )

    is ConversionStep.Error -> {
        console.error("Conversion error:", error)
        ConversionOutput.Error(error.message ?: "Unknown error")
    }
}
