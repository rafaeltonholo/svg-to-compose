package dev.tonholo.s2c

import AppConfig
import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.extensions.extension
import dev.tonholo.s2c.extensions.isDirectory
import dev.tonholo.s2c.extensions.isFile
import dev.tonholo.s2c.extensions.pascalCase
import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.io.IconWriter
import dev.tonholo.s2c.io.TempFileWriter
import dev.tonholo.s2c.logger.debug
import dev.tonholo.s2c.logger.debugSection
import dev.tonholo.s2c.logger.output
import dev.tonholo.s2c.logger.printEmpty
import dev.tonholo.s2c.logger.verbose
import dev.tonholo.s2c.logger.warn
import dev.tonholo.s2c.optimizer.Optimizer
import dev.tonholo.s2c.parser.ImageParser
import dev.tonholo.s2c.parser.ParserConfig
import dev.tonholo.s2c.parser.mapIconName
import okio.Path
import okio.Path.Companion.toPath

class Processor(
    private val fileManager: FileManager,
    private val iconWriter: IconWriter = IconWriter(fileManager),
    private val tempFileWriter: TempFileWriter = TempFileWriter(fileManager),
    private val optimizers: Optimizer.Factory = Optimizer.Factory(fileManager),
    private val parser: ImageParser.Companion = ImageParser(fileManager),
) {
    /**
     * Starts the processor execution.
     *
     * This function is the main entry point for the SVG and Android Vector
     * Drawable parser.
     *
     * It identifies whether the input path provided is a file or a directory.
     *
     * If it's a directory, it finds all SVG and XML files (not recursive)
     * within and processes each of them.
     *
     * If it's a file, the system ensures it has a Kotlin file extension and
     * proceeds with the processing.
     *
     * Any processing errors are captured and logged. Successful completion
     * is also logged.
     *
     * @param path The input path for the SVG or Android Vector Drawable file(s).
     * @param output The path where the output file(s) should be stored.
     * @param config The configuration parameters for the parser.
     * @throws ExitProgramException In case of an error while parsing the files,
     * or if no files are found at the specified path.
     */
    fun run(
        path: String,
        output: String,
        config: ParserConfig,
        recursive: Boolean,
        maxDepth: Int = AppDefaults.MAX_RECURSIVE_DEPTH,
    ) {
        if (config.silent) {
            AppConfig.silent = true
        }
        verbose("Start processor execution")
        val filePath = path.toPath()
        var outputPath = output.toPath()
        val isDirectory = try {
            fileManager.isDirectory(filePath)
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
        var runRecursively = recursive
        val files = if (isDirectory) {
            output("🔍 Directory detected")
            findSvgAndXmlFilesInDirectory(
                outputPath = outputPath,
                filePath = filePath,
                recursive = recursive,
                maxDepth = maxDepth,
                exclude = config.exclude,
            )
        } else {
            val isExcluded = config.exclude?.let(filePath.name::matches) ?: false
            if (isExcluded) {
                output("File in excluded list. Skipping parse.")
                return
            }

            output("🔍 File detected")
            outputPath = ensureKotlinFileExtension(outputPath, filePath, config)
            if (runRecursively) {
                warn("Recursive flag added to a file. Ignoring recursive search.")
                runRecursively = false
            }
            listOf(filePath)
        }

        val optimizers = createOptimizers(files, config)

        val errors = mutableListOf<Pair<Path, Exception>>()
        processFiles(
            files = files,
            optimizers = optimizers,
            outputPath = outputPath,
            config = config,
            runRecursively = runRecursively,
            filePath = filePath,
            errors = errors,
        )

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

    fun dispose() {
        tempFileWriter.clear()
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
    private fun ensureKotlinFileExtension(outputPath: Path, inputPath: Path, config: ParserConfig): Path {
        return when {
            outputPath.isDirectory -> {
                val filename = inputPath
                    .name
                    .removeSuffix(FileType.Svg.extension)
                    .removeSuffix(FileType.Avg.extension)
                    .let(config::mapIconName)
                    .pascalCase()
                output("Output path is a directory. Creating a Kotlin file based on the input file name.")
                outputPath / "$filename.kt"
            }

            outputPath.extension.isEmpty() || outputPath.extension.lowercase() != ".kt" -> {
                output("Output path is missing kotlin file extension. Appending it to the output.")
                "$outputPath.kt".toPath()
            }

            else -> {
                outputPath
            }
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
     * @param recursive Enable recursive directory search.
     * @param maxDepth Define how deep the recursive directory should go.
     * @throws ExitProgramException If an error occurs during processing.
     * @return A List containing all the discovered SVG/XML files.
     */
    private fun findSvgAndXmlFilesInDirectory(
        outputPath: Path,
        filePath: Path,
        recursive: Boolean,
        maxDepth: Int,
        exclude: Regex? = null,
    ): List<Path> = buildList {
        if (outputPath.isDirectory.not()) {
            printEmpty()
            throw ExitProgramException(
                errorCode = ErrorCode.OutputNotDirectoryError,
                message = """
                    |❌ ERROR: The specified output path is not a directory. 
                    |When the input is a directory, the output MUST also be a directory.
                    |
                    |If you pointed to a directory path, make sure the output directory already exists.
                    |
                """.trimMargin(),
            )
        }

        val imageFiles = fileManager.findFilesToProcess(
            from = filePath,
            recursive = recursive,
            maxDepth = maxDepth,
            exclude = exclude,
        )

        verbose("svg/xml files = $imageFiles")

        addAll(imageFiles)

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

    private fun createOptimizers(files: List<Path>, config: ParserConfig) = if (config.optimize) {
        verbose("Verifying optimization dependencies")
        optimizers.verifyDependency(
            hasSvg = files.any { it.extension == FileType.Svg.extension },
            hasAvg = files.any { it.extension == FileType.Avg.extension },
        )
        verbose("Finished verification")
        optimizers
    } else {
        null
    }

    /**
     * This function processes multiple files to convert SVG or Android Vector Drawable to
     * Jetpack Compose icons.
     *
     * @param files The list of files to process.
     * @param optimizers The optimizer factory.
     * @param outputPath The output path.
     * @param config The parser configuration.
     * @param runRecursively Whether to run recursively.
     * @param filePath The file path.
     * @param errors The list of errors.
     */
    private fun processFiles(
        files: List<Path>,
        optimizers: Optimizer.Factory?,
        outputPath: Path,
        config: ParserConfig,
        runRecursively: Boolean,
        filePath: Path,
        errors: MutableList<Pair<Path, Exception>>,
    ) {
        for (file in files) {
            try {
                processFile(
                    file = file,
                    optimizers = optimizers,
                    output = outputPath,
                    config = config,
                    recursive = runRecursively,
                    basePath = filePath,
                )
                printEmpty()
            } catch (e: ExitProgramException) {
                throw e
            } catch (
                e:
                @Suppress("TooGenericExceptionCaught")
                Exception,
            ) {
                printEmpty()
                // the generic exception is expected since we are going to exit the program with a failure later.
                output("Failed to parse $file to Jetpack Compose Icon. Error message: ${e.message}")
                if (AppConfig.debug) {
                    output(e.stackTraceToString())
                }
                printEmpty()
                errors.add(file to e)
            }
        }
    }

    /**
     * This function processes one file to convert SVG or Android Vector Drawable to
     * Jetpack Compose icons.
     *
     * @param file The path of the file that this function is processing.
     * It expects a Path object which contains the complete directory path of the
     * file to be converted.
     * @param optimizers An optional Optimizer Factory object that can be used to
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
        optimizers: Optimizer.Factory?,
        output: Path,
        config: ParserConfig,
        recursive: Boolean,
        basePath: Path,
    ) {
        output("⏳ Processing ${file.name}")

        val iconName = if (output.isFile) {
            output.segments.last().removeSuffix(output.extension)
        } else {
            file.name.removeSuffix(FileType.Svg.extension).removeSuffix(FileType.Avg.extension)
        }.let(config::mapIconName)
        val targetFile = tempFileWriter.create(
            file = file,
        )

        val finalFile = optimizers?.optimize(targetFile) ?: targetFile

        val relativePackage = buildRelativePackage(
            recursive = recursive,
            file = file,
            basePath = basePath,
            parent = file.parent,
        )

        val pkg = "${config.pkg}$relativePackage"

        output("👓 Parsing the ${finalFile.extension} file")
        val fileContents = parser.parse(
            file = finalFile,
            iconName = iconName,
            config = config.copy(
                pkg = pkg,
            ),
        )

        verbose("File contents = $fileContents")

        val iconOutput = if (recursive.not() || file == basePath) {
            output
        } else {
            output / relativePackage.removePrefix(".").replace(".", "/")
        }
        iconWriter.write(
            iconName = iconName,
            fileContents = fileContents,
            output = iconOutput,
        )

        if (config.keepTempFolder.not()) {
            tempFileWriter.clear()
        }
    }

    private fun buildRelativePackage(
        recursive: Boolean,
        file: Path,
        basePath: Path,
        parent: Path?,
    ) = if (recursive.not() || file == basePath) {
        ""
    } else {
        buildString {
            val stack = mutableListOf<String>()
            var currentParent = parent
            while (currentParent != null && currentParent != basePath) {
                stack += currentParent.name
                currentParent = currentParent.parent
            }
            while (stack.isNotEmpty()) {
                append(".${stack.removeLast()}")
            }
        }
    }
}
