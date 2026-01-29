package dev.tonholo.s2c.gradle.tasks.worker

import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.error.ParserException
import dev.tonholo.s2c.gradle.internal.logger.Logger
import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.io.IconWriter
import dev.tonholo.s2c.io.TempFileWriter
import dev.tonholo.s2c.parser.ParserConfig
import okio.Path.Companion.toPath
import org.gradle.api.logging.Logging
import org.gradle.workers.WorkAction
import java.util.UUID

/**
 * A Gradle [WorkAction] responsible for parsing a single SVG/XML icon
 * file and converting it into a Jetpack Compose `ImageVector`.
 *
 * This action is designed to be executed in parallel by Gradle workers,
 * processing one icon file per execution.
 * It utilizes the core `s2c` [Processor] to perform the conversion,
 * configured with parameters provided by [IconParsingParameters].
 *
 * The action handles setting up the necessary environment, including a
 * logger, file manager, and an isolated temporary directory for file
 * operations to prevent conflicts between parallel executions.
 *
 * Upon completion, it writes the result of the operation (either success
 * with the output file path or an error with a message) to a designated
 * result file. This allows the main task to aggregate results from all
 * worker actions.
 *
 * @see IconParsingParameters for the input parameters.
 * @see IconParsingWorkActionResult for the structure of the output result.
 */
internal abstract class IconParsingWorkAction : WorkAction<IconParsingParameters> {
    override fun execute() {
        val gradleLogger = Logging.getLogger(IconParsingWorkAction::class.simpleName)
        val logger = Logger(gradleLogger)
        val fileManager = FileManager(okio.FileSystem.SYSTEM, logger)
        // Use an isolated temp directory per work item to avoid races between workers
        val isolatedTempRoot = parameters.tempDirPath.get().toPath() /
            (".s2c/temp/gradle-worker/" + UUID.randomUUID().toString()).toPath()
        val processor = Processor(
            logger = logger,
            fileManager = fileManager,
            iconWriter = IconWriter(logger, fileManager),
            tempFileWriter = TempFileWriter(logger, fileManager, isolatedTempRoot),
        )
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
