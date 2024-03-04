package dev.tonholo.s2c

import AppConfig
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.extensions.extension
import dev.tonholo.s2c.extensions.isDirectory
import dev.tonholo.s2c.extensions.isFile
import dev.tonholo.s2c.io.IconWriter
import dev.tonholo.s2c.io.TempFileWriter
import dev.tonholo.s2c.logger.debug
import dev.tonholo.s2c.logger.debugEndSection
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

        val files = mutableListOf(filePath)

        printEmpty()
        if (inputMetadata.isDirectory) {
            output("🔍 Directory detected")
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

            files.clear()
            val directoryFiles = fileSystem.list(filePath)
                .filter {
                    it.name.endsWith(".svg") || it.name.endsWith(".xml")
                }
            verbose("svg/xml files = $directoryFiles")
            files.addAll(
                directoryFiles,
            )
            if (files.isEmpty()) {
                throw ExitProgramException(
                    errorCode = ErrorCode.FileNotFoundError,
                    message = """
                    |❌ Failure to parse SVG/Android Vector Drawable to Jetpack Compose.
                    |No SVG or XML files detected in the directory.
                    |
                    """.trimMargin()
                )
            }
        } else {
            output("🔍 File detected")
            if (outputPath.extension.isEmpty()) {
                output("Output path is missing kotlin file extension. Appending it to the output.")
                outputPath = "$output.kt".toPath()
            }
        }

        val optimizer = if (config.optimize) {
            verbose("Verifying optimization dependencies")
            val optimizer = Optimizer.Factory(fileSystem)
            optimizer.verifyDependency()
            verbose("Finished verification")
            optimizer
        } else {
            null
        }

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
            debugSection("Full error messages")
            errors.filter { (_, exception) ->
                exception.message?.isNotEmpty() == true
            }.forEach { debug(it) }
            debugEndSection()

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
            file.name.removeSuffix(".svg").removeSuffix(".xml")
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
