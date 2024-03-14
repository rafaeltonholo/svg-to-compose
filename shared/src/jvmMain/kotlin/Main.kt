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
    }.also { println(it) }

    val suffix = "NewXmlParse"
    val (pkg, _, path, output) = SampleFile.Svg.AndroidDevelopers(
        SampleAppPackage("dev.tonholo.sampleApp.ui.icon"),
        suffix = suffix,
    )
    val config = ParserConfig(
        pkg = pkg.value,
        theme = "dev.tonholo.sampleApp.ui.theme.SampleAppTheme",
        optimize = true,
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
            recursive = true,
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

    class Directory(
        sampleAppPackage: SampleAppPackage,
    ) : SampleFile(
        sampleAppPackage = sampleAppPackage,
        input = "samples/",
        output = "${ROOT_SAMPLE_APP_PATH}/${sampleAppPackage.toDirectory()}"
    ) {
        operator fun component1(): SampleAppPackage = sampleAppPackage
        operator fun component2(): String = ""
    }

    sealed interface Svg {
        companion object {
            private const val BASE_PATH = "samples/svg/"
        }
        class All(
            sampleAppPackage: SampleAppPackage,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage.plus(".svg"),
            input = BASE_PATH,
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
            input = "$BASE_PATH/shield-halved-solid.svg",
            output = "$ROOT_SAMPLE_APP_PATH/$sampleAppPackage/ShieldSolid.svg.$suffix.kt",
        )

        data class Illustration(
            override val sampleAppPackage: SampleAppPackage,
            val suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage,
            input = "$BASE_PATH/illustration.svg",
            output = "$ROOT_SAMPLE_APP_PATH/$sampleAppPackage/Illustration.svg.$suffix.kt",
        )

        data class Rects(
            override val sampleAppPackage: SampleAppPackage,
            val suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage,
            input = "$BASE_PATH/rects/rects.svg",
            output = "$ROOT_SAMPLE_APP_PATH/$sampleAppPackage/Rects.svg.$suffix.kt",
        )

        data class ComplexRects(
            override val sampleAppPackage: SampleAppPackage,
            val suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage,
            input = "$BASE_PATH/rects/complex-rects.svg",
            output = "$ROOT_SAMPLE_APP_PATH/$sampleAppPackage/ComplexRects.svg.$suffix.kt",
        )

        data class RoundedRect(
            override val sampleAppPackage: SampleAppPackage,
            val suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage,
            input = "$BASE_PATH/rects/rounded-rect.svg",
            output = "$ROOT_SAMPLE_APP_PATH/$sampleAppPackage/RoundedRect.svg.$suffix.kt",
        )

        data class SimpleCircle(
            override val sampleAppPackage: SampleAppPackage,
            val suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage,
            input = "$BASE_PATH/circle/simple-circle.svg",
            output = "$ROOT_SAMPLE_APP_PATH/$sampleAppPackage/SimpleCircle.svg.$suffix.kt",
        )

        data class DashArrayCircle(
            override val sampleAppPackage: SampleAppPackage,
            val suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage,
            input = "$BASE_PATH/circle/dasharray-circle.svg",
            output = "$ROOT_SAMPLE_APP_PATH/$sampleAppPackage/DashArrayCircle.svg.$suffix.kt",
        )

        data class AndroidDevelopers(
            override val sampleAppPackage: SampleAppPackage,
            val suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage,
            input = "$BASE_PATH/android-developers.svg",
            output = "$ROOT_SAMPLE_APP_PATH/$sampleAppPackage/AndroidDevelopers.svg.$suffix.kt",
        )
    }

    sealed interface Avg {
        companion object {
            private const val BASE_PATH = "samples/avg/"
        }
        class All(
            sampleAppPackage: SampleAppPackage,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage.plus(".avg"),
            input = BASE_PATH,
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
            input = "$BASE_PATH/shield-halved-solid.xml",
            output = "$ROOT_SAMPLE_APP_PATH/$sampleAppPackage/ShieldSolid.xml.$suffix.kt",
        )

        data class Illustration(
            override val sampleAppPackage: SampleAppPackage,
            val suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage,
            input = "$BASE_PATH/illustration.xml",
            output = "$ROOT_SAMPLE_APP_PATH/$sampleAppPackage/Illustration.xml.$suffix.kt",
        )
    }
}
