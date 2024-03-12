import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.command.command
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.io.IconWriter
import dev.tonholo.s2c.io.TempFileWriter
import dev.tonholo.s2c.logger.output
import dev.tonholo.s2c.logger.printEmpty
import dev.tonholo.s2c.parser.ParserConfig
import java.nio.file.Path
import java.nio.file.Paths
import okio.FileSystem
import kotlin.system.exitProcess

/**
 * This is only for debugging purposes.
 * Should never be used in production.
 */
fun main() {
    AppConfig.debug = false
    // forces to use node v18.14.2, which has installed, on my machine,
    // svgo, s2v and avocado.
    command(program = "nvm") {
        args("use", "18.14.2")
    }

    val suffix = "NewXmlParse"
    val (_, path, output) = SampleFile.Svg.DashArrayCircle(suffix)
    val config = ParserConfig(
        pkg = "dev.tonholo.composeicons.ui.icon",
        theme = "dev.tonholo.composeicons.ui.theme.ComposeIconsTheme",
        optimize = false,
        receiverType = null,
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

private sealed class SampleFile(
    input: String,
    output: String,
) {
    private val projectDirectory: Path = Paths.get("").toAbsolutePath().parent
    val input = "$projectDirectory/$input"
    val output = "$projectDirectory/$output"

    operator fun component2(): String = input
    operator fun component3(): String = output

    sealed interface Svg {
        data class ShieldSolid(val suffix: String) : SampleFile(
            input = "samples/shield-halved-solid.svg",
            output = "integrity-check/ShieldSolid.svg.$suffix.kt",
        )

        data class Illustration(val suffix: String) : SampleFile(
            input = "samples/illustration.svg",
            output = "integrity-check/Illustration.svg.$suffix.kt",
        )

        data class Rects(val suffix: String) : SampleFile(
            input = "samples/rects/rects.svg",
            output = "integrity-check/Rects.svg.$suffix.kt",
        )

        data class ComplexRects(val suffix: String) : SampleFile(
            input = "samples/rects/complex-rects.svg",
            output = "integrity-check/ComplexRects.svg.$suffix.kt",
        )

        data class RoundedRect(val suffix: String) : SampleFile(
            input = "samples/rects/rounded-rect.svg",
            output = "integrity-check/RoundedRect.svg.$suffix.kt",
        )
        data class SimpleCircle(val suffix: String) : SampleFile(
            input = "samples/circle/simple-circle.svg",
            output = "integrity-check/SimpleCircle.svg.$suffix.kt",
        )
        data class DashArrayCircle(val suffix: String) : SampleFile(
            input = "samples/circle/dasharray-circle.svg",
            output = "integrity-check/DashArrayCircle.svg.$suffix.kt",
        )
    }

    sealed interface Avg {
        data class ShieldSolid(val suffix: String) : SampleFile(
            input = "samples/shield-halved-solid.xml",
            output = "integrity-check/ShieldSolid.xml.$suffix.kt",
        )

        data class Illustration(val suffix: String) : SampleFile(
            input = "samples/illustration.xml",
            output = "integrity-check/Illustration.xml.$suffix.kt",
        )

    }
}
