package dev.tonholo.s2c

import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.extensions.extension
import dev.tonholo.s2c.extensions.isDirectory
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
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

class Processor(
    private val iconWriter: IconWriter,
    private val tempFileWriter: TempFileWriter,
) {
    fun run(
        path: String,
        pkg: String,
        theme: String,
        output: String,
        optimize: Boolean,
        contextProvider: String?,
        addToMaterial: Boolean,
    ) {
        val filePath = path.toPath()
        val outputPath = output.toPath()
        val fileSystem = FileSystem.SYSTEM
        val inputMetadata = fileSystem.metadata(filePath)

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
                        |""".trimMargin(),
                )
            }

            files.clear()
            files.addAll(
                fileSystem.list(filePath).filter {
                    it.name.endsWith(".svg") || it.name.endsWith(".xml")
                },
            )
        } else {
            output("🔍 File detected")
        }

        if (optimize) {
            verbose("Verifying optimization dependencies")
            Optimizer.verifyDependency(files.any { it.name.endsWith(".xml") })
            verbose("Finished verification")
        }

        val errors = mutableListOf<Exception>()
        for (file in files) {
            try {
                processFile(
                    file = file,
                    optimize = optimize,
                    pkg = pkg,
                    theme = theme,
                    contextProvider = contextProvider,
                    addToMaterial = addToMaterial,
                    output = outputPath,
                )
                printEmpty()
            } catch (e: ExitProgramException) {
                throw e
            } catch (e: @Suppress("TooGenericExceptionCaught") Exception) {
                // the generic exception is expected since we are going to exit the program with a failure later.
                output("Failed to parse $file to Jetpack Compose Icon. Error message: ${e.message}")
                errors.add(e)
            }
        }

        if (errors.isEmpty()) {
            output("🎉 SVG/Android Vector Drawable parsed to Jetpack Compose icon with success 🎉")
        } else {
            debugSection("Full error messages")
            errors.filter { exception ->
                exception.message?.isNotEmpty() == true
            }.forEach { debug(it) }
            debugEndSection()

            throw ExitProgramException(
                errorCode = ErrorCode.FailedToParseIconError,
                message = """
                    |❌ Failure to parse SVG/Android Vector Drawable to Jetpack Compose.
                    |Please see the logs for more information.
                    |
                    """.trimMargin()
            )
        }
    }

    private fun processFile(
        file: Path,
        optimize: Boolean,
        pkg: String,
        theme: String,
        contextProvider: String?,
        addToMaterial: Boolean,
        output: Path,
    ) {
        output("⏳ Processing ${file.name}")

        val iconName = file.name.removeSuffix(".svg").removeSuffix(".xml")
        val targetFile = tempFileWriter.create(
            file = file,
            optimize = optimize,
        )

        output("👓 Parsing the ${file.extension} file")
        val fileContents = ImageParser.parse(
            file = targetFile,
            iconName = iconName,
            pkg = pkg,
            theme = theme,
            contextProvider = contextProvider,
            addToMaterial = addToMaterial,
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
