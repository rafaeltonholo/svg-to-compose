package dev.tonholo.s2c

import AppConfig
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.extensions.isDirectory
import dev.tonholo.s2c.logger.output
import dev.tonholo.s2c.logger.verbose
import dev.tonholo.s2c.optimizer.Optimizer
import dev.tonholo.s2c.parser.ImageParser
import dev.tonholo.s2c.wirter.IconWriter
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

class Processor(
    private val iconWriter: IconWriter,
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

        println()
        if (inputMetadata.isDirectory) {
            println("🔍 Directory detected")
            if (outputPath.isDirectory.not()) {
                println()
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
            println("🔍 File detected")
        }

        if (optimize) {
            if (AppConfig.verbose) {
                println()
                println("Verifying optimization dependencies")
                println()
            }
            Optimizer.verifyDependency(files.any { it.name.endsWith(".xml") })
            if (AppConfig.verbose) {
                println()
                println("Finished verification")
                println()
            }
        }

        for (file in files) {
            processFile(
                file = file,
                optimize = optimize,
                pkg = pkg,
                theme = theme,
                contextProvider = contextProvider,
                addToMaterial = addToMaterial,
                output = outputPath,
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
        val fileContents = ImageParser.parse(
            file = file,
            optimize = optimize,
            pkg = pkg,
            theme = theme,
            contextProvider = contextProvider,
            addToMaterial = addToMaterial,
        )

        verbose("File contents = $fileContents")

        iconWriter.write(
            iconName = file.name.removeSuffix(".svg").removeSuffix(".xml"),
            fileContents = fileContents,
            output = output,
        )
    }
}