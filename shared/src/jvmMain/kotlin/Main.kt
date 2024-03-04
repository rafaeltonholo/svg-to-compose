
import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.command.command
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.io.IconWriter
import dev.tonholo.s2c.io.TempFileWriter
import dev.tonholo.s2c.logger.output
import dev.tonholo.s2c.logger.printEmpty
import dev.tonholo.s2c.parser.ParserConfig
import java.nio.file.Paths
import okio.FileSystem
import kotlin.system.exitProcess

/**
 * This is only for debugging purposes.
 * Should never be used in production.
 */
fun main() {
    // forces to use node v18.14.2, which has installed, on my machine,
    // svgo, s2v and avocado.
    command(program = "nvm") {
        args("use", "18.14.2")
    }

    val currentDir = Paths.get("")
    val projectDirectory = currentDir.toAbsolutePath().parent

    val path = "${projectDirectory}/samples/compose-multiplatform.xml"
    val output = "${projectDirectory}/integrity-check/ComposeMultiplatform.xml.Optimized.kt"
    val config = ParserConfig(
        pkg = "dev.tonholo.composeicons.ui.icon",
        theme = "dev.tonholo.composeicons.ui.theme.ComposeIconsTheme",
        optimize = true,
        contextProvider = null,
        addToMaterial = false,
        noPreview = false,
        makeInternal = false,
        minified = false,
    )
    try {
        val fileSystem = FileSystem.SYSTEM
        Processor(
            fileSystem = fileSystem,
            iconWriter = IconWriter(
                fileSystem = fileSystem,
            ),
            tempFileWriter = TempFileWriter(
                fileSystem = fileSystem,
            ),
        ).run(
            path = path,
            output = output,
            config = config,
        )
    } catch (e: ExitProgramException) {
        printEmpty()
        output(e.message.orEmpty())
        exitProcess(e.errorCode.code)
    }
}
