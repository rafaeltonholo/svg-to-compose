package dev.tonholo.s2c.cli.runtime

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.MordantMarkdownHelpFormatter
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.eagerOption
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.pair
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.boolean
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.mordant.platform.MultiplatformSystem.exitProcess
import dev.tonholo.s2c.AppDefaults
import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.SvgToComposeContext
import dev.tonholo.s2c.SvgToComposeContextProvider
import dev.tonholo.s2c.cli.config.BuildConfig
import dev.tonholo.s2c.emitter.FormatConfig
import dev.tonholo.s2c.emitter.IndentStyle
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.parser.ParserConfig
import dev.tonholo.s2c.parser.config.TemplateConfig
import dev.tonholo.s2c.updateConfig
import dev.zacsweers.metro.Inject
import okio.Path.Companion.toPath

private const val MANUAL_LINE_BREAK = "\u0085"

/**
 * Clikt command that serves as the CLI entry point for SVG-to-Compose.
 *
 * Injected by Metro with a [CliConfig], [Logger], and [Processor.Factory].
 * At the start of [run], the parsed CLI flags are written back to
 * [config] so that the logger and all downstream components observe the
 * correct verbosity and debug settings.
 */
@Inject
internal class Client(
    private val context: SvgToComposeContext,
    private val logger: Logger,
    private val runner: CliRunner,
) : SuspendingCliktCommand(name = "s2c") {
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
    ).boolean().default(value = true)

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
    ).boolean().default(value = false)

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
            "The default value is ${AppDefaults.MAX_RECURSIVE_DEPTH}.",
    ).int().default(value = AppDefaults.MAX_RECURSIVE_DEPTH)

    private val silent by option(
        names = arrayOf("--silent"),
        help = "Enable silent run mode. This will suppress all the output logs this CLI provides.",
    ).flag()

    private val exclude by option(
        names = arrayOf("--exclude"),
        help = "A regex used to exclude some icons from the parsing.",
    ).validate { pattern ->
        try {
            Regex(pattern)
        } catch (e: IllegalArgumentException) {
            fail("Invalid regex pattern: \"$pattern\". ${e.message}")
        }
    }

    private val indentSize by option(
        names = arrayOf("--indent-size"),
        help = "Number of indent characters per level in generated code. " +
            "Overrides .editorconfig if specified.",
    ).int()

    private val indentStyle by option(
        names = arrayOf("--indent-style"),
        help = "Indentation style for generated code: 'space' or 'tab'. " +
            "Overrides .editorconfig if specified.",
    ).choice("space" to IndentStyle.SPACE, "tab" to IndentStyle.TAB)

    private val noEditorConfig by option(
        names = arrayOf("--no-editorconfig"),
        help = "Disable .editorconfig resolution. Uses default formatting unless " +
            "--indent-size or --indent-style are specified.",
    ).flag()

    private val template by option(
        names = arrayOf("--template"),
        help = "Path to a template file for output customisation. When omitted, auto-discovers by " +
            "walking up from the output directory.",
    )
    private val noTemplate by option(
        names = arrayOf("--no-template"),
        help = "Disable auto-discovery of s2c.template.toml files.",
    ).flag()

    private val noTui by option(
        names = arrayOf("--no-tui"),
        help = "Disable the TUI dashboard and use plain text output.",
    ).flag(default = false)

    private val json by option(
        names = arrayOf("--json"),
        help = "Output conversion progress as JSONL (one JSON object per line). Implies --no-tui.",
    ).flag(default = false)

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

    private val effectiveDebug get() = verbose || debug
    private val effectiveStackTrace get() = stackTrace || effectiveDebug

    private val config get() = context.configSnapshot

    override suspend fun run() {
        context.updateConfig<CliConfig> { config ->
            config.copy(
                debug = effectiveDebug,
                verbose = verbose,
                silent = silent,
                stackTrace = effectiveStackTrace,
            )
        }

        logArgs()

        SvgToComposeContextProvider.initialize(context)
        try {
            val outputFormat = if (json) OutputFormat.Json else OutputFormat.Text
            val runConfig = RunConfig(
                inputPath = path,
                outputPath = output,
                packageName = pkg,
                optimizationEnabled = optimize,
                recursive = recursive,
                recursiveDepth = recursiveDepth,
                noTui = noTui || json,
                parserConfig = buildParserConfig(),
            )
            runner.run(
                config = runConfig,
                mapIconNameTo = { iconName ->
                    mapIconNameTo?.let { (from, to) ->
                        iconName.replace(from, to)
                    } ?: iconName
                },
                outputFormat = outputFormat,
            )
        } catch (e: ExitProgramException) {
            logger.printEmpty()
            if (effectiveStackTrace) {
                logger.error(e.message.orEmpty(), e)
            } else {
                logger.output(e.message.orEmpty())
            }
            exitProcess(e.errorCode.code)
        } finally {
            SvgToComposeContextProvider.reset()
        }
    }

    private fun logArgs() {
        logger.verbose(
            """
                |Args:
                |   path = $path
                |   package = $pkg
                |   theme = $theme
                |   output = $output
                |   optimize = $optimize
                |   receiverType = $receiverType
                |   addToMaterial = $addToMaterial
                |   debug = $debug
                |   verbose = $verbose
                |   stackTrace = $stackTrace
                |   noPreview = $noPreview
                |   isKmp = $isKmp
                |   makeInternal = $makeInternal
                |   minified = $minified
                |   recursive = $recursive
                |   recursiveDepth = $recursiveDepth
                |   silent = ${config.silent}
                |   exclude = $exclude
                |   mapIconNameTo = $mapIconNameTo
                |   indentSize = $indentSize
                |   indentStyle = $indentStyle
                |   noEditorConfig = $noEditorConfig
            """.trimMargin(),
        )
    }

    private fun buildParserConfig(): ParserConfig = ParserConfig(
        pkg = pkg,
        theme = theme,
        optimize = optimize,
        receiverType = receiverType,
        addToMaterial = addToMaterial,
        noPreview = noPreview,
        makeInternal = makeInternal,
        minified = minified,
        kmpPreview = isKmp,
        exclude = exclude?.let(::Regex),
        formatConfig = buildFormatConfig(),
        formatOverrides = buildFormatOverrides(),
        template = template?.let { TemplateConfig(configPath = it.toPath(), noDiscovery = noTemplate) },
    )

    /**
     * Builds a full [FormatConfig] when `.editorconfig` resolution is
     * disabled via `--no-editorconfig`. Returns `null` otherwise so
     * the Processor resolves formatting from `.editorconfig`.
     */
    private fun buildFormatConfig(): FormatConfig? {
        if (!noEditorConfig) return null
        val defaults = FormatConfig()
        return FormatConfig(
            indentSize = indentSize ?: defaults.indentSize,
            indentStyle = indentStyle ?: defaults.indentStyle,
            maxLineLength = defaults.maxLineLength,
            insertFinalNewline = defaults.insertFinalNewline,
        )
    }

    /**
     * Builds partial [FormatConfig.Overrides] from CLI flags.
     *
     * Returns `null` when no CLI formatting flags are specified.
     * Non-null overrides are merged on top of the resolved config
     * (from `.editorconfig` or defaults) by the Processor.
     */
    private fun buildFormatOverrides(): FormatConfig.Overrides? {
        if (indentSize == null && indentStyle == null) return null
        return FormatConfig.Overrides(
            indentSize = indentSize,
            indentStyle = indentStyle,
        )
    }
}
