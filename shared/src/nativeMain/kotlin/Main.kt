import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.eagerOption
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.boolean
import dev.tonholo.s2c.BuildKonfig
import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.io.IconWriter
import dev.tonholo.s2c.io.TempFileWriter
import dev.tonholo.s2c.logger.output
import dev.tonholo.s2c.logger.printEmpty
import dev.tonholo.s2c.logger.verbose
import dev.tonholo.s2c.parser.ParserConfig
import okio.FileSystem
import platform.posix.exit

fun main(args: Array<String>) = Client()
    .main(args)

class Client : CliktCommand() {

    init {
        eagerOption("-v", "--version", help = "Show this CLI version") {
            throw PrintMessage("SVG to Compose version: ${BuildKonfig.VERSION}")
        }
    }

    private val path by argument(
        name = "path",
        help = "file *.svg | *.xml | directory",
    )

    private val pkg by option(
        names = arrayOf("-p", "--package"),
        help = "Specify icons's package. This will replace package at the top of the icon file",
    ).required()

    private val theme by option(
        names = arrayOf("-t", "--theme"),
        help = "Specify project's theme name. This will take place in the Icon Preview composable function and in " +
            "the ImageVector Builder's names.",
    ).required()

    private val output by option(
        names = arrayOf("-o", "--output"),
        help = "output filename; if no .kt extension specified, it will be automatically added. In case of the input " +
            "is a directory, output MUST also be a directory.",
    ).required()

    private val optimize by option(
        names = arrayOf("-opt", "--optimize"),
        help = "Enable svg optimization before parsing to Jetpack Compose icon. The optimization process uses the " +
            "following programs: svgo, svg2vectordrawable, avocado from NPM Registry",
    ).boolean().default(true)

    private val contextProvider by option(
        names = arrayOf("-cp", "--context-provider"),
        help = """
        Adds a custom context provider to the Icon definition.
        E.g.: s2c <args> -o MyIcon.kt -cp Icons.Filled my-icon.svg will creates the Compose Icon:
        val Icons.Filled.MyIcon: ImageVector.
        """.trimIndent(),
    )

    private val addToMaterial by option(
        names = arrayOf("--add-to-material"),
        help = "Add the icon to the Material Icons context provider.",
    ).flag()

    private val debug by option(
        names = arrayOf("--debug"),
        help = "Enable debug log.",
    ).flag()

    private val verbose by option(
        names = arrayOf("--verbose"),
        help = "Enable verbose log.",
    ).flag()

    private val noPreview by option(
        names = arrayOf("-np", "--no-preview", "--kmp"),
        help = "Removes the preview function from the file. It is very useful if you are generating the icons for " +
            "KMP, since KMP doesn't support previews yet.",
    ).flag()

    private val makeInternal by option(
        names = arrayOf("--make-internal"),
        help = "Mark the icon as internal",
    ).flag()

    private val minified by option(
        names = arrayOf("--minified"),
        help = "Remove all comments explaining the path logic creation and inline all method parameters.",
    ).flag()

    override fun run() {
        verbose("Args:")
        verbose("   path = $path")
        verbose("   pacakge = $pkg")
        verbose("   theme = $theme")
        verbose("   output = $output")
        verbose("   optimize = $optimize")
        verbose("   contextProvider = $contextProvider")
        verbose("   addToMaterial = $addToMaterial")
        verbose("   debug = $debug")
        verbose("   verbose = $verbose")
        verbose("   noPreview = $noPreview")
        verbose("   makeInternal = $makeInternal")
        verbose("   minified = $minified")

        AppConfig.verbose = verbose
        AppConfig.debug = verbose || debug

        try {
            val fileSystem = FileSystem.SYSTEM
            Processor(
                iconWriter = IconWriter(
                    fileSystem = fileSystem,
                ),
                tempFileWriter = TempFileWriter(
                    fileSystem = fileSystem,
                ),
            ).run(
                path = path,
                output = output,
                config = ParserConfig(
                    pkg = pkg,
                    theme = theme,
                    optimize = optimize,
                    contextProvider = contextProvider,
                    addToMaterial = addToMaterial,
                    noPreview = noPreview,
                    makeInternal = makeInternal,
                    minified = minified,
                )
            )
        } catch (e: ExitProgramException) {
            printEmpty()
            output(e.message.orEmpty())
            exit(e.errorCode.code)
        }
    }

}
