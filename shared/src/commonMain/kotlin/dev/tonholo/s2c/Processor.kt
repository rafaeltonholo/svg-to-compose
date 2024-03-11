package dev.tonholo.s2c

import AppConfig
import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.extensions.extension
import dev.tonholo.s2c.extensions.isDirectory
import dev.tonholo.s2c.extensions.isFile
import dev.tonholo.s2c.io.IconWriter
import dev.tonholo.s2c.io.TempFileWriter
import dev.tonholo.s2c.logger.debug
import dev.tonholo.s2c.logger.debugSection
import dev.tonholo.s2c.logger.output
import dev.tonholo.s2c.logger.printEmpty
import dev.tonholo.s2c.logger.verbose
import dev.tonholo.s2c.optimizer.Optimizer
import dev.tonholo.s2c.parser.ImageParser
import dev.tonholo.s2c.parser.ParserConfig
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

class Processor(
    private val fileSystem: FileSystem,
    private val iconWriter: IconWriter,
    private val tempFileWriter: TempFileWriter,
) {
    fun run(
        path: String,
        output: String,
        config: ParserConfig,
    ) {
        verbose("Start processor execution")
        val filePath = path.toPath()
        var outputPath = output.toPath()
        val inputMetadata = try {
            fileSystem.metadata(filePath)
        } catch (e: okio.IOException) {
            throw ExitProgramException(
                errorCode = ErrorCode.FileNotFoundError,
                message = """
                    |❌ Failure to parse SVG/Android Vector Drawable to Jetpack Compose.
                    |No SVG or XML file detected on the specified path.
                    |
                """.trimMargin(),
                throwable = e,
            )
        }

        printEmpty()
        val files = if (inputMetadata.isDirectory) {
            output("🔍 Directory detected")
            findSvgAndXmlFilesInDirectory(outputPath, filePath)
        } else {
            output("🔍 File detected")
            outputPath = ensureKotlinFileExtension(outputPath)
            listOf(filePath)
        }

        val optimizer = createOptimizer(config)

        val errors = mutableListOf<Pair<Path, Exception>>()
        for (file in files) {
            try {
                processFile(
                    file = file,
                    optimizer = optimizer,
                    output = outputPath,
                    config = config,
                )
                printEmpty()
            } catch (e: ExitProgramException) {
                throw e
            } catch (
                e:
                @Suppress("TooGenericExceptionCaught")
                Exception
            ) {
                printEmpty()
                // the generic exception is expected since we are going to exit the program with a failure later.
                output("Failed to parse $file to Jetpack Compose Icon. Error message: ${e.message}")
                if (AppConfig.debug) {
                    e.printStackTrace()
                }
                printEmpty()
                errors.add(file to e)
            }
        }

        if (errors.isEmpty()) {
            output("🎉 SVG/Android Vector Drawable parsed to Jetpack Compose icon with success 🎉")
        } else {
            debugSection("Full error messages") {
                errors.filter { (_, exception) ->
                    exception.message?.isNotEmpty() == true
                }.forEach { debug(it) }
            }

            throw ExitProgramException(
                errorCode = ErrorCode.FailedToParseIconError,
                message = """
                    |❌ Failure to parse (${errors.size}) SVG(s)/Android Vector Drawable(s) to Jetpack Compose.
                    |Please see the logs for more information.
                    |
                    |Files failed to parse:
                    |${errors.map { it.first }.joinToString("\n") { "    - $it" }}
                """.trimMargin()
            )
        }
    }

    /**
     * Ensures the output path has a Kotlin file extension (.kt).
     *
     * This function checks if the provided output path has a file extension.
     * If the extension is missing, it appends ".kt". Otherwise, the original
     * output path is returned.
     *
     * @param outputPath The intended output path.
     * @return The validated output path with a guaranteed Kotlin file extension (.kt).
     */
    private fun ensureKotlinFileExtension(outputPath: Path): Path {
        return if (outputPath.extension.isEmpty() || outputPath.extension != ".kt") {
            output("Output path is missing kotlin file extension. Appending it to the output.")
            "$outputPath.kt".toPath()
        } else {
            outputPath
        }
    }

    /**
     * Finds SVG and XML files within a directory.
     *
     * This function searches for files with the extensions ".svg" and ".xml" in the
     * specified directory.
     *
     * - It throws an [ExitProgramException] with [ErrorCode.OutputNotDirectoryError]
     * if the output path is not a directory.
     * - It throws an [ExitProgramException] with [ErrorCode.FileNotFoundError] if no
     * SVG or XML files are found in the directory.
     *
     * @param outputPath The path to the directory to search for files.
     * @param filePath The path of the current directory being processed.
     * @throws ExitProgramException If an error occurs during processing.
     * @return A List containing all the discovered SVG/XML files.
     */
    private fun findSvgAndXmlFilesInDirectory(outputPath: Path, filePath: Path): List<Path> = buildList {
        if (outputPath.isDirectory.not()) {
            printEmpty()
            throw ExitProgramException(
                errorCode = ErrorCode.OutputNotDirectoryError,
                message = """❌ ERROR: when the input is a directory, the output MUST be directory too.
                            |If you pointed to a directory path, make sure the output directory already exists.
                            |
                """.trimMargin(),
            )
        }

        val directoryFiles = fileSystem.list(filePath)
            .filter {
                it.name.endsWith(".svg") || it.name.endsWith(".xml")
            }
        verbose("svg/xml files = $directoryFiles")
        addAll(
            directoryFiles,
        )

        if (isEmpty()) {
            throw ExitProgramException(
                errorCode = ErrorCode.FileNotFoundError,
                message = """
                        |❌ Failure to parse SVG/Android Vector Drawable to Jetpack Compose.
                        |No SVG or XML files detected in the directory.
                        |
                """.trimMargin()
            )
        }
    }

    private fun createOptimizer(config: ParserConfig) = if (config.optimize) {
        verbose("Verifying optimization dependencies")
        val optimizer = Optimizer.Factory(fileSystem)
        optimizer.verifyDependency()
        verbose("Finished verification")
        optimizer
    } else {
        null
    }

    /**
     * This function processes files to convert SVG or Android Vector Drawable to
     * Jetpack Compose icons.
     *
     * @param file The path of the file that this function is processing.
     * It expects a Path object which contains the complete directory path of the
     * file to be converted.
     * @param optimizer An optional Optimizer Factory object that can be used to
     * optimize the SVG.  If an Optimizer object is passed, the SVG will be optimized
     * before conversion. If set to null, the SVG will not be optimized.
     * @param output The path location where the result files are expected to be stored
     * after SVG conversion. It expects a Path object which contains the complete
     * directory path where files will be saved.
     * @param config The Parser Configuration. This contains settings for the conversion
     * process. This function raises an exception if something goes wrong during the process.
     */
    private fun processFile(
        file: Path,
        optimizer: Optimizer.Factory?,
        output: Path,
        config: ParserConfig,
    ) {
        output("⏳ Processing ${file.name}")

        val iconName = if (output.isFile) {
            output.segments.last().removeSuffix(output.extension)
        } else {
            file.name.removeSuffix(FileType.Svg.extension).removeSuffix(FileType.Avg.extension)
        }
        val targetFile = tempFileWriter.create(
            file = file,
            optimize = optimizer != null,
        )

        optimizer?.optimize(file)

        output("👓 Parsing the ${file.extension} file")
        val fileContents = ImageParser(fileSystem).parse(
            file = targetFile,
            iconName = iconName,
            config = config,
        )

        verbose("File contents = $fileContents")

        iconWriter.write(
            iconName = iconName,
            fileContents = fileContents,
            output = output,
        )

        tempFileWriter.clear()
    }
}
