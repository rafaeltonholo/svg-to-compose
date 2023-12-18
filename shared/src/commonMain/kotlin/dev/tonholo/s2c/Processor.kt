import dev.tonholo.s2c.parser.ImageParser
import okio.FileSystem
import okio.IOException
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
        if (inputMetadata.isDirectory) {
            println("🔍 Directory detected")
            if (outputMetadata.isDirectory.not()) {
                println()
                throw IOException(
                    """❌ ERROR: when the input is a directory, the output MUST be directory too.
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
        }

        for (file in files) {
            ImageParser.parse(
                file = file,
                optimize = optimize,
                pkg = pacakge,
                theme = theme,
                contextProvider = contextProvider,
                addToMaterial = addToMaterial,
            )
        }
    }
}