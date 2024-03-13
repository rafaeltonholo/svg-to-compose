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
    val (pkg, _, path, output) = SampleFile.Svg.All(
        SampleAppPackage("dev.tonholo.sampleApp.ui.icon"),
    )
    val config = ParserConfig(
        pkg = pkg.value,
        theme = "dev.tonholo.sampleApp.ui.theme.SampleAppTheme",
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

@JvmInline
private value class SampleAppPackage(val value: String) {
    override fun toString(): String = value
    fun toDirectory(): String = value.replace(".", "/")

    operator fun plus(value: String): SampleAppPackage =
        SampleAppPackage(this.value + value)
}

private sealed class SampleFile(
    open val sampleAppPackage: SampleAppPackage,
    input: String,
    output: String,
) {
    companion object {
        const val ROOT_SAMPLE_APP_PATH = "sample-app/src/main/kotlin"
    }

    private val projectDirectory: Path = Paths.get("").toAbsolutePath().parent
    val input = "$projectDirectory/$input"
    val output = "$projectDirectory/$output"

    operator fun component3(): String = input
    operator fun component4(): String = output

    sealed interface Svg {
        class All(
            sampleAppPackage: SampleAppPackage,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage.plus(".svg"),
            input = "samples/svg/",
            output = "${ROOT_SAMPLE_APP_PATH}/${sampleAppPackage.plus(".svg").toDirectory()}",
        ) {
            operator fun component1(): SampleAppPackage = sampleAppPackage
            operator fun component2(): String = ""
        }

        data class ShieldSolid(
            override val sampleAppPackage: SampleAppPackage,
            val suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage,
            input = "samples/shield-halved-solid.svg",
            output = "$ROOT_SAMPLE_APP_PATH/$sampleAppPackage/ShieldSolid.svg.$suffix.kt",
        )

        data class Illustration(
            override val sampleAppPackage: SampleAppPackage,
            val suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage,
            input = "samples/illustration.svg",
            output = "$ROOT_SAMPLE_APP_PATH/$sampleAppPackage/Illustration.svg.$suffix.kt",
        )

        data class Rects(
            override val sampleAppPackage: SampleAppPackage,
            val suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage,
            input = "samples/rects/rects.svg",
            output = "$ROOT_SAMPLE_APP_PATH/$sampleAppPackage/Rects.svg.$suffix.kt",
        )

        data class ComplexRects(
            override val sampleAppPackage: SampleAppPackage,
            val suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage,
            input = "samples/rects/complex-rects.svg",
            output = "$ROOT_SAMPLE_APP_PATH/$sampleAppPackage/ComplexRects.svg.$suffix.kt",
        )

        data class RoundedRect(
            override val sampleAppPackage: SampleAppPackage,
            val suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage,
            input = "samples/rects/rounded-rect.svg",
            output = "$ROOT_SAMPLE_APP_PATH/$sampleAppPackage/RoundedRect.svg.$suffix.kt",
        )

        data class SimpleCircle(
            override val sampleAppPackage: SampleAppPackage,
            val suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage,
            input = "samples/circle/simple-circle.svg",
            output = "$ROOT_SAMPLE_APP_PATH/$sampleAppPackage/SimpleCircle.svg.$suffix.kt",
        )

        data class DashArrayCircle(
            override val sampleAppPackage: SampleAppPackage,
            val suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage,
            input = "samples/circle/dasharray-circle.svg",
            output = "$ROOT_SAMPLE_APP_PATH/$sampleAppPackage/DashArrayCircle.svg.$suffix.kt",
        )
    }

    sealed interface Avg {
        class All(
            sampleAppPackage: SampleAppPackage,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage.plus(".avg"),
            input = "samples/xml/",
            output = "${ROOT_SAMPLE_APP_PATH}/${sampleAppPackage.plus(".avg").toDirectory()}",
        ) {
            operator fun component1(): SampleAppPackage = sampleAppPackage
            operator fun component2(): String = ""
        }

        data class ShieldSolid(
            override val sampleAppPackage: SampleAppPackage,
            val suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage,
            input = "samples/shield-halved-solid.xml",
            output = "$ROOT_SAMPLE_APP_PATH/$sampleAppPackage/ShieldSolid.xml.$suffix.kt",
        )

        data class Illustration(
            override val sampleAppPackage: SampleAppPackage,
            val suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage,
            input = "samples/illustration.xml",
            output = "$ROOT_SAMPLE_APP_PATH/$sampleAppPackage/Illustration.xml.$suffix.kt",
        )
    }
}
