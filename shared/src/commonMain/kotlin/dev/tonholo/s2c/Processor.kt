import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.optimizer.Optimizer
import dev.tonholo.s2c.parser.ImageParser
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

object Processor {
    fun run(
        path: String,
        pacakge: String,
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
        val outputMetadata = fileSystem.metadata(outputPath)

        val files = mutableListOf(filePath)

        println()
        if (inputMetadata.isDirectory) {
            println("🔍 Directory detected")
            if (outputMetadata.isDirectory.not()) {
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
                pkg = pacakge,
                theme = theme,
                contextProvider = contextProvider,
                addToMaterial = addToMaterial,
            )
        }
    }

    private fun processFile(
        file: Path,
        optimize: Boolean,
        pkg: String,
        theme: String,
        contextProvider: String?,
        addToMaterial: Boolean
    ) {
        println("⏳ Processing ${file.name}")
        val fileContents = ImageParser.parse(
            file = file,
            optimize = optimize,
            pkg = pkg,
            theme = theme,
            contextProvider = contextProvider,
            addToMaterial = addToMaterial,
        )

        if (AppConfig.verbose) {
            println("File contents = $fileContents")
        }
    }
}