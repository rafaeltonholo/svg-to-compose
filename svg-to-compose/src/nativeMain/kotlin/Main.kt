import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.installMordantMarkdown
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.output.MordantHelpFormatter
import com.github.ajalt.clikt.output.MordantMarkdownHelpFormatter
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.eagerOption
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.pair
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.boolean
import com.github.ajalt.clikt.parameters.types.int
import dev.tonholo.s2c.AppDefaults
import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.config.BuildConfig
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.logger.CommonLogger
import dev.tonholo.s2c.logger.printEmpty
import dev.tonholo.s2c.parser.ParserConfig
import okio.FileSystem
import platform.posix.exit

private const val MANUAL_LINE_BREAK = "\u0085"

fun main(args: Array<String>) = Client()
    .main(args)

class Client : CliktCommand(name = "s2c") {

    init {
        eagerOption("-v", "--version", help = "Show this CLI version and exit") {
            throw PrintMessage("SVG to Compose version: ${BuildConfig.VERSION}")
        }

        context {
            helpFormatter = { context ->
                MordantMarkdownHelpFormatter(
                    context = context,
                    showDefaultValues = true,
                    requiredOptionMarker = "*",
                )
            }
        }
    }

    private val path by argument(
        name = "path",
        help = "file *.svg | *.xml | directory",
    )

    private val pkg by option(
        names = arrayOf("-p", "--package"),
        help = "Specify icons' package. This will replace package at the top of the icon file",
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
        help = "Enable SVG/AVG optimization before parsing to Jetpack Compose icon. The optimization process uses " +
            "the following programs: svgo, avocado from NPM Registry",
    ).boolean().default(true)

    private val receiverType by option(
        names = arrayOf("-rt", "--receiver-type"),
    ).help {
        """
        |Adds a receiver type to the Icon definition. This will generate the Icon as a extension of the passed argument.
        |${MANUAL_LINE_BREAK}E.g.: `s2c <args> -o MyIcon.kt -rt Icons.Filled my-icon.svg` will creates the Compose Icon:
        |$MANUAL_LINE_BREAK`val Icons.Filled.MyIcon: ImageVector`.
        """.trimMargin()
    }

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

    private val stackTrace by option(
        names = arrayOf("--stacktrace"),
        help = "Print the stacktrace when an error happens.",
    ).flag()

    private val noPreview by option(
        names = arrayOf("-np", "--no-preview"),
        help = "Removes the preview function from the file.",
    ).flag()

    private val isKmp by option(
        names = arrayOf("--kmp"),
        help = "Ensures the output is compatible with KMP.",
    ).boolean().default(false)

    private val makeInternal by option(
        names = arrayOf("--make-internal"),
        help = "Mark the icon as internal",
    ).flag()

    private val minified by option(
        names = arrayOf("--minified"),
        help = "Remove all comments explaining the path logic creation and inline all method parameters.",
    ).flag()

    private val recursive by option(
        names = arrayOf("-r", "--recursive"),
        help = "Enables parsing of all files in the input directory, including those in subdirectories up " +
            "to a maximum depth of ${AppDefaults.MAX_RECURSIVE_DEPTH}",
    ).flag()

    private val recursiveDepth by option(
        names = arrayOf("--recursive-depth", "--depth"),
        help = "The depth level for recursive file search within directory. " +
            "The default value is ${AppDefaults.MAX_RECURSIVE_DEPTH}."
    ).int().default(AppDefaults.MAX_RECURSIVE_DEPTH)

    private val silent by option(
        names = arrayOf("--silent"),
        help = "Enable silent run mode. This will suppress all the output logs this CLI provides.",
    ).flag()

    private val exclude by option(
        names = arrayOf("--exclude"),
        help = "A regex used to exclude some icons from the parsing.",
    )

    private val mapIconNameTo by option(
        names = arrayOf("--map-icon-name-from-to", "--from-to", "--rename"),
        help = """Replace the icon's name first value of this parameter with the second. 
            |This is useful when you want to remove part of the icon's name from the output icon.
            |
            |For example, running the following command:
            |```
            |    s2c <args> \
            |        -o ./my-app/src/my/pkg/icons \
            |        -rt Icons.Filled \
            |        --map-icon-name-from-to "_filled" ""
            |        ./my-app/assets/svgs
            |```
            |
            |An icon named `bright_sun_filled.svg` will create a `ImageVector` named `BrightSun`.
        """.trimMargin(),
    ).pair()

    private val logger = CommonLogger()

    override fun run() {
        logger.verbose("Args:")
        logger.verbose("   path = $path")
        logger.verbose("   pacakge = $pkg")
        logger.verbose("   theme = $theme")
        logger.verbose("   output = $output")
        logger.verbose("   optimize = $optimize")
        logger.verbose("   receiverType = $receiverType")
        logger.verbose("   addToMaterial = $addToMaterial")
        logger.verbose("   debug = $debug")
        logger.verbose("   verbose = $verbose")
        logger.verbose("   stackTrace = $stackTrace")
        logger.verbose("   noPreview = $noPreview")
        logger.verbose("   isKmp = $isKmp")
        logger.verbose("   makeInternal = $makeInternal")
        logger.verbose("   minified = $minified")
        logger.verbose("   recursive = $recursive")
        logger.verbose("   recursiveDepth = $recursiveDepth")
        logger.verbose("   silent = $silent")
        logger.verbose("   exclude = $exclude")
        logger.verbose("   mapIconNameTo = $mapIconNameTo")

        AppConfig.verbose = verbose
        AppConfig.debug = verbose || debug
        AppConfig.stackTrace = stackTrace || AppConfig.debug
        AppConfig.silent = silent

        try {
            val fileSystem = FileSystem.SYSTEM
            Processor(
                logger = logger,
                fileManager = FileManager(fileSystem, logger),
            ).run(
                path = path,
                output = output,
                config = ParserConfig(
                    pkg = pkg,
                    theme = theme,
                    optimize = optimize,
                    receiverType = receiverType,
                    addToMaterial = addToMaterial,
                    noPreview = noPreview,
                    makeInternal = makeInternal,
                    minified = minified,
                    silent = silent,
                    kmpPreview = isKmp,
                    exclude = exclude?.let(::Regex),
                ),
                recursive = recursive,
                maxDepth = recursiveDepth,
                mapIconName = mapIconNameTo?.let { (from, to) ->
                    { iconName -> iconName.replace(from, to) }
                },
            )
        } catch (e: ExitProgramException) {
            printEmpty()
            if (AppConfig.stackTrace) {
                logger.error(e.message.orEmpty(), e)
            } else {
                logger.output(e.message.orEmpty())
            }
            exit(e.errorCode.code)
        }
    }
}
