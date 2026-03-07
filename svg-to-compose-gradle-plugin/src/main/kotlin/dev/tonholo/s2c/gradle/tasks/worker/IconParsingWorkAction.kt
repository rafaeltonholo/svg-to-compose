package dev.tonholo.s2c.gradle.tasks.worker

import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.error.ParserException
import dev.tonholo.s2c.gradle.internal.service.S2cWorkerBridge
import dev.tonholo.s2c.parser.ParserConfig
import okio.Path.Companion.toPath
import org.gradle.workers.WorkAction
import java.util.UUID

/**
 * A Gradle [WorkAction] responsible for parsing a single SVG/XML icon
 * file and converting it into a Jetpack Compose `ImageVector`.
 *
 * This action is designed to be executed in parallel by Gradle workers
 * using [noIsolation][org.gradle.workers.WorkerExecutor.noIsolation] mode.
 *
 * Each worker obtains a shared [Processor.Factory][dev.tonholo.s2c.Processor.Factory]
 * from [S2cWorkerBridge] and creates an isolated [Processor][dev.tonholo.s2c.Processor]
 * with its own temporary directory to avoid file races between parallel executions.
 *
 * @see IconParsingParameters for the input parameters.
 * @see IconParsingWorkActionResult for the structure of the output result.
 */
internal abstract class IconParsingWorkAction : WorkAction<IconParsingParameters> {
    override fun execute() {
        val isolatedTempRoot = parameters.tempDirPath.get().toPath() /
            (".s2c/temp/gradle-worker/" + UUID.randomUUID().toString()).toPath()
        val factory = S2cWorkerBridge.get(parameters.bridgeToken.get())
        val processor = factory.create(tempDirectory = isolatedTempRoot)

        val origin = parameters.inputFilePath.get().toPath()
        val outputDir = parameters.outputDirPath.get().toPath()

        val result = try {
            val processed = processor.run(
                path = origin.toString(),
                output = outputDir.toString(),
                config = parameters.toConfig(),
                recursive = parameters.recursive.get(),
                maxDepth = parameters.maxDepth.get(),
                mapIconName = null, // handled by precomputed output file name
            )

            val processedFilePath = processed.singleOrNull()
            if (processedFilePath != null) {
                IconParsingWorkActionResult.success(
                    origin = origin.toString(),
                    output = processedFilePath.toString(),
                )
            } else {
                IconParsingWorkActionResult.error(
                    origin = origin.toString(),
                    message = "No output produced for $origin"
                )
            }
        } catch (e: ExitProgramException) {
            IconParsingWorkActionResult.error(origin = origin.toString(), message = e.message ?: "$e")
        } catch (e: ParserException) {
            IconParsingWorkActionResult.error(origin = origin.toString(), message = e.message ?: "$e")
        } finally {
            processor.dispose()
        }
        result.store(resultFilePath = parameters.resultFilePath)
    }

    private fun IconParsingParameters.toConfig(): ParserConfig = ParserConfig(
        pkg = destinationPackage.get(),
        optimize = optimize.get(),
        minified = minified.get(),
        theme = theme.get(),
        receiverType = receiverType.orNull,
        addToMaterial = addToMaterial.get(),
        noPreview = noPreview.get(),
        makeInternal = makeInternal.get(),
        exclude = excludePattern.orNull?.toRegex(),
        kmpPreview = kmpPreview.get(),
        // Keep plugin silent; CLI/library handle their own output
        silent = true,
        keepTempFolder = true,
    )
}
