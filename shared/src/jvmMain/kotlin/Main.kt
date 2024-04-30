
import dev.tonholo.s2c.Processor
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
    val suffix = "v4"
    val (pkg, path, output) = SampleFile.Directory(
        SampleAppPackage("dev.tonholo.sampleApp.ui.icon"),
        suffix,
    )
    val config = ParserConfig(
        pkg = pkg.value,
        theme = "dev.tonholo.sampleApp.ui.theme.SampleAppTheme",
        // When enabling the optimize flag,
        // make sure your default node has installed
        // svgo and avocado.
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

private fun SampleAppPackage.svg(): SampleAppPackage = this + ".svg"
private fun SampleAppPackage.avg(): SampleAppPackage = this + ".avg"


private sealed class SampleFile(
    val sampleAppPackage: SampleAppPackage,
    input: String,
    output: String,
) {
    companion object {
        const val ROOT_SAMPLE_APP_PATH = "sample-app/src/main/kotlin"
    }

    private val projectDirectory: Path = Paths.get("").toAbsolutePath().parent
    val input = "$projectDirectory/$input"
    val output = "$projectDirectory/$output"

    operator fun component1(): SampleAppPackage = sampleAppPackage
    operator fun component2(): String = input
    operator fun component3(): String = output

    class Directory(
        sampleAppPackage: SampleAppPackage,
        ignored: String,
    ) : SampleFile(
        sampleAppPackage = sampleAppPackage,
        input = "samples/",
        output = "${ROOT_SAMPLE_APP_PATH}/${sampleAppPackage.toDirectory()}"
    )

    sealed interface Svg {
        companion object {
            private const val BASE_PATH = "samples/svg/"
        }

        class All(
            sampleAppPackage: SampleAppPackage,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage.svg(),
            input = BASE_PATH,
            output = "${ROOT_SAMPLE_APP_PATH}/${sampleAppPackage.svg().toDirectory()}",
        )

        class Brasil(
            sampleAppPackage: SampleAppPackage,
            suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage.svg(),
            input = "$BASE_PATH/brasil.svg",
            output = "$ROOT_SAMPLE_APP_PATH/${sampleAppPackage.svg().toDirectory()}/Brasil.$suffix.kt",
        )

        class ShieldSolid(
            sampleAppPackage: SampleAppPackage,
            suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage.svg(),
            input = "$BASE_PATH/shield-halved-solid.svg",
            output = "$ROOT_SAMPLE_APP_PATH/${sampleAppPackage.svg().toDirectory()}/ShieldSolid.$suffix.kt",
        )

        class Illustration(
            sampleAppPackage: SampleAppPackage,
            suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage.svg(),
            input = "$BASE_PATH/illustration.svg",
            output = "$ROOT_SAMPLE_APP_PATH/${sampleAppPackage.svg().toDirectory()}/Illustration.$suffix.kt",
        )

        class Rects(
            sampleAppPackage: SampleAppPackage,
            suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage.svg(),
            input = "$BASE_PATH/rects/rects.svg",
            output = "$ROOT_SAMPLE_APP_PATH/${sampleAppPackage.svg().toDirectory()}/Rects.$suffix.kt",
        )
        class DashArrayRect(
            sampleAppPackage: SampleAppPackage,
            suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage.svg(),
            input = "$BASE_PATH/rects/dash-array-rect.svg",
            output = "$ROOT_SAMPLE_APP_PATH/${sampleAppPackage.svg().toDirectory()}/DashArrayRect.$suffix.kt",
        )

        class ComplexRects(
            sampleAppPackage: SampleAppPackage,
            suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage.svg(),
            input = "$BASE_PATH/rects/complex-rects.svg",
            output = "$ROOT_SAMPLE_APP_PATH/${sampleAppPackage.svg().toDirectory()}/ComplexRects.$suffix.kt",
        )

        class RoundedRect(
            sampleAppPackage: SampleAppPackage,
            suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage.svg(),
            input = "$BASE_PATH/rects/rounded-rect.svg",
            output = "$ROOT_SAMPLE_APP_PATH/${sampleAppPackage.svg().toDirectory()}/RoundedRect.$suffix.kt",
        )

        class SimpleCircle(
            sampleAppPackage: SampleAppPackage,
            suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage.svg(),
            input = "$BASE_PATH/circle/simple-circle.svg",
            output = "$ROOT_SAMPLE_APP_PATH/${sampleAppPackage.svg().toDirectory()}/SimpleCircle.$suffix.kt",
        )

        class DashArrayCircle(
            sampleAppPackage: SampleAppPackage,
            suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage.svg(),
            input = "$BASE_PATH/circle/dasharray-circle.svg",
            output = "$ROOT_SAMPLE_APP_PATH/${sampleAppPackage.svg().toDirectory()}/DashArrayCircle.$suffix.kt",
        )

        class AndroidDevelopers(
            sampleAppPackage: SampleAppPackage,
            suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage.svg(),
            input = "$BASE_PATH/android-developers.svg",
            output = "$ROOT_SAMPLE_APP_PATH/${sampleAppPackage.svg().toDirectory()}/AndroidDevelopers.$suffix.kt",
        )
    }

    sealed interface Avg {
        companion object {
            private const val BASE_PATH = "samples/avg/"
        }

        class All(
            sampleAppPackage: SampleAppPackage,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage.avg(),
            input = BASE_PATH,
            output = "${ROOT_SAMPLE_APP_PATH}/${sampleAppPackage.avg().toDirectory()}",
        )

        class ShieldSolid(
            sampleAppPackage: SampleAppPackage,
            suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage.avg(),
            input = "$BASE_PATH/shield-halved-solid.xml",
            output = "$ROOT_SAMPLE_APP_PATH/${sampleAppPackage.avg().toDirectory()}/ShieldSolid.$suffix.kt",
        )

        class Illustration(
            sampleAppPackage: SampleAppPackage,
            suffix: String,
        ) : SampleFile(
            sampleAppPackage = sampleAppPackage.avg(),
            input = "$BASE_PATH/illustration.xml",
            output = "$ROOT_SAMPLE_APP_PATH/${sampleAppPackage.avg().toDirectory()}/Illustration.$suffix.kt",
        )
    }
}
