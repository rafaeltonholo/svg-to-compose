import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.boolean

class Client : CliktCommand() {

    init {
        eagerOption("-v", "--version", help = "Show this CLI version") {
            throw PrintMessage("SVG to Compose version: 1.0.0-alpha01")
        }
    }

    private val path by argument(
        name = "path",
        help = "file *.svg | *.xml | directory",
    )

    private val pacakge by option(
        names = arrayOf("-p", "--package"),
        help = "Specify icons's package. This will replace package at the top of the icon file",
    ).required()

    private val theme by option(
        names = arrayOf("-t", "--theme"),
        help = "Specify project's theme name. This will take place in the Icon Preview composable function and in the ImageVector Builder's names.",
    ).required()

    private val output by option(
        names = arrayOf("-o", "--output"),
        help = "output filename; if no .kt extension specified, it will be automatically added. In case of the input is a directory, output MUST also be a directory.",
    ).required()

    private val optimize by option(
        names = arrayOf("-opt", "--optmize"),
        help = "Enable svg optimization before parsing to Jetpack Compose icon. The optimization process uses the following programs: svgo, svg2vectordrawable, avocado from NPM Registry",
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

    override fun run() {
        println("Args:")
        println("   path = $path")
        println("   pacakge = $pacakge")
        println("   theme = $theme")
        println("   output = $output")
        println("   optimize = $optimize")
        println("   contextProvider = $contextProvider")
        println("   addToMaterial = $addToMaterial")
        println("   debug = $debug")
    }

}


fun main(args: Array<String>) = Client()
    .main(args)
