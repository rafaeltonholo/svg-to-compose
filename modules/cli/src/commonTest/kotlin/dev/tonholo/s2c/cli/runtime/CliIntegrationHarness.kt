package dev.tonholo.s2c.cli.runtime

import com.github.ajalt.mordant.rendering.AnsiLevel
import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.terminal.TerminalRecorder
import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.SvgToComposeContextImpl
import dev.tonholo.s2c.cli.logger.CliLogger
import dev.tonholo.s2c.cli.output.LineWriter
import dev.tonholo.s2c.cli.output.report.InvocationCommand
import dev.tonholo.s2c.cli.update.GitHubReleaseFetcher
import dev.tonholo.s2c.cli.update.UpdateCache
import dev.tonholo.s2c.cli.update.UpdateNotifier
import dev.tonholo.s2c.cli.update.VersionChecker
import dev.tonholo.s2c.cli.update.WrapperDetector
import dev.tonholo.s2c.dispatching.SequentialFileDispatcher
import dev.tonholo.s2c.domain.FileType
import dev.tonholo.s2c.emitter.DefaultCodeEmitterFactory
import dev.tonholo.s2c.emitter.editorconfig.DefaultEditorConfigReader
import dev.tonholo.s2c.emitter.template.config.DefaultTemplateConfigReader
import dev.tonholo.s2c.io.DefaultIconWriter
import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.optimizer.Optimizer
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.parser.AvgContentParser
import dev.tonholo.s2c.parser.DefaultImageParser
import dev.tonholo.s2c.parser.ParserConfig
import dev.tonholo.s2c.parser.SvgContentParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.json.Json
import okio.Path
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem

private const val TERMINAL_WIDTH = 120

/**
 * Wires the real conversion pipeline to the real CLI runtime over an
 * in-memory file system and a recording terminal.
 *
 * The core [Processor] is built from the same implementations the
 * production dependency graph provides, so a run exercises parsing, code
 * generation, file writing, run-log writing, and error reporting for real.
 * Only the file system and the output sinks are swapped for test doubles.
 *
 * @param interactive whether the terminal reports itself as a TTY, which is
 *  what [CliRunner] uses to pick between the dashboard and the line-based
 *  renderers.
 * @param silent the value of the `--silent` flag for the run.
 */
internal class CliIntegrationHarness(interactive: Boolean = false, silent: Boolean = false) {
    val fileSystem: FakeFileSystem = FakeFileSystem()

    /** Every line the CLI wrote to stdout, in order. */
    val printedLines: MutableList<String> = mutableListOf()

    private val recorder = TerminalRecorder(
        ansiLevel = AnsiLevel.NONE,
        width = TERMINAL_WIDTH,
        outputInteractive = interactive,
        inputInteractive = interactive,
    )
    private val terminal = Terminal(terminalInterface = recorder)
    private val context = SvgToComposeContextImpl(initial = CliConfig(silent = silent))
    private val lineWriter = LineWriter { line -> printedLines += line }
    private val logger = CliLogger(context = context, writer = lineWriter)
    private val fileManager = FileManager(fileSystem = fileSystem, logger = logger)

    private val processorFactory = object : Processor.Factory {
        override fun create(tempDirectory: Path?): Processor = Processor(
            config = context.configSnapshot,
            logger = logger,
            fileManager = fileManager,
            iconWriter = DefaultIconWriter(logger = logger, fileManager = fileManager),
            tempDirectory = tempDirectory,
            optimizers = Optimizer.Factory(
                logger = logger,
                fileManager = fileManager,
                context = context,
            ),
            parser = DefaultImageParser(
                fileManager = fileManager,
                contentParsers = mapOf(
                    FileType.Svg to SvgContentParser(logger = logger),
                    FileType.Avg to AvgContentParser(logger = logger),
                ),
            ),
            codeEmitterFactory = DefaultCodeEmitterFactory(logger = logger),
            editorConfigReader = DefaultEditorConfigReader(fileManager = fileManager),
            templateConfigReader = DefaultTemplateConfigReader(fileManager = fileManager),
            fileDispatcher = SequentialFileDispatcher,
        )
    }

    private val runner = CliRunner(
        processorFactory = processorFactory,
        terminal = terminal,
        context = context,
        fileManager = fileManager,
        fileSystem = fileSystem,
        updateNotifier = UpdateNotifier(
            versionChecker = VersionChecker(
                currentVersion = "3.0.0",
                cache = UpdateCache(
                    fileSystem = fileSystem,
                    cacheDir = "/cache".toPath(),
                    json = Json,
                    ioDispatcher = Dispatchers.Default,
                ),
                fetcher = GitHubReleaseFetcher { null },
                nowEpochMillis = { 0L },
            ),
            wrapperDetector = WrapperDetector(envReader = { null }),
            logger = logger,
        ),
        invocationCommand = InvocationCommand(value = "s2c"),
        ioDispatcher = Dispatchers.Default,
        mainDispatcher = Dispatchers.Default,
        lineWriter = lineWriter,
    )

    /** Everything [CliRunner] wrote straight to the terminal. */
    val terminalOutput: String
        get() = recorder.output()

    fun writeFile(path: String, content: String) {
        val target = path.toPath()
        target.parent?.let { fileSystem.createDirectories(dir = it, mustCreate = false) }
        fileSystem.write(file = target) { writeUtf8(content) }
    }

    fun createDirectory(path: String) {
        fileSystem.createDirectories(dir = path.toPath(), mustCreate = false)
    }

    fun exists(path: String): Boolean = fileSystem.exists(path.toPath())

    fun readFile(path: String): String = fileSystem.read(file = path.toPath()) { readUtf8() }

    suspend fun run(
        input: String,
        output: String,
        outputFormat: OutputFormat = OutputFormat.Text,
        noTui: Boolean = false,
        recursive: Boolean = false,
    ) {
        runner.run(
            config = runConfig(input = input, output = output, noTui = noTui, recursive = recursive),
            mapIconNameTo = { iconName -> iconName },
            outputFormat = outputFormat,
            logDir = LOG_DIR,
        )
    }

    /**
     * Runs the conversion pipeline without any renderer attached and returns
     * the events it emitted, so renderer-level tests can replay a real run.
     */
    suspend fun collectEvents(input: String, output: String): List<ConversionEvent> =
        processorFactory.create(tempDirectory = null)
            .runAsFlow(
                path = input,
                output = output,
                config = parserConfig,
                recursive = false,
            )
            .flowOn(Dispatchers.Default)
            .toList()

    private fun runConfig(
        input: String,
        output: String,
        noTui: Boolean,
        recursive: Boolean,
    ): RunConfig = RunConfig(
        inputPath = input,
        outputPath = output,
        parserConfig = parserConfig,
        packageName = parserConfig.pkg,
        optimizationEnabled = parserConfig.optimize,
        parallel = CliConfig.PARALLEL_DISABLED,
        recursive = recursive,
        noTui = noTui,
    )

    companion object {
        val LOG_DIR: Path = "/logs".toPath()

        val parserConfig: ParserConfig = ParserConfig(
            pkg = "com.example.icons",
            theme = "AppTheme",
            optimize = false,
            receiverType = null,
            addToMaterial = false,
            kmpPreview = false,
            noPreview = true,
            makeInternal = false,
            minified = false,
        )

        val validSvg: String = """
            |<svg width="24" height="24" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
            |  <path d="M12 2L22 12L12 22L2 12Z" fill="#000000"/>
            |</svg>
        """.trimMargin()

        val brokenSvg: String = """
            |<svg width="not-a-number" height="24" xmlns="http://www.w3.org/2000/svg">
            |  <path d="M12 2L22 12L12 22L2 12Z" fill="#000000"/>
            |</svg>
        """.trimMargin()
    }
}
