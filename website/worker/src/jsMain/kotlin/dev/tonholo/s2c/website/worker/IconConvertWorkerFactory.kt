package dev.tonholo.s2c.website.worker

import com.varabyte.kobweb.serialization.createIOSerializer
import com.varabyte.kobweb.worker.OutputDispatcher
import com.varabyte.kobweb.worker.WorkerFactory
import com.varabyte.kobweb.worker.WorkerStrategy
import dev.tonholo.s2c.ConversionStep
import dev.tonholo.s2c.domain.IconFileContents
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

            coroutineScope.launch {
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

    is ConversionStep.Error -> ConversionOutput.Error(error.message ?: "Unknown error")
}
