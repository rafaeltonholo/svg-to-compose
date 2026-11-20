package dev.tonholo.s2c.cli.runtime

import com.github.ajalt.mordant.rendering.AnsiLevel
import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.terminal.TerminalRecorder
import dev.tonholo.s2c.cli.inject.CliTestGraph
import dev.tonholo.s2c.cli.output.report.InvocationCommand
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.parser.ParserConfig
import dev.zacsweers.metro.createGraphFactory
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import okio.Path
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem

private const val TERMINAL_WIDTH = 120

/**
 * Wires the real conversion pipeline to the real CLI runtime over an
 * in-memory file system and a recording terminal.
 *
 * The whole object graph comes from [CliTestGraph], which shares its bindings
 * with the production graph through [dev.tonholo.s2c.cli.inject.CliBindings].
 * Only the file system, the terminal, the output sink and the dispatchers are
 * swapped for test doubles, so a run exercises parsing, code generation, file
 * writing, run-log writing, and error reporting for real.
 *
 * @param scheduler the scheduler of the surrounding `runTest`, shared with
 *  the injected dispatchers so the run is deterministic.
 *
 * @param interactive whether the terminal reports itself as a TTY, which is
 *  what [CliRunner] uses to pick between the dashboard and the line-based
 *  renderers.
 * @param silent the value of the `--silent` flag for the run.
 */
internal class CliIntegrationHarness(
    scheduler: TestCoroutineScheduler,
    interactive: Boolean = false,
    silent: Boolean = false,
) {
    val fileSystem: FakeFileSystem = FakeFileSystem()

    /** Every line the CLI wrote to stdout, in order. */
    val printedLines: MutableList<String> = mutableListOf()

    private val recorder = TerminalRecorder(
        ansiLevel = AnsiLevel.NONE,
        width = TERMINAL_WIDTH,
        outputInteractive = interactive,
        inputInteractive = interactive,
    )
    private val dispatcher = StandardTestDispatcher(scheduler = scheduler)

    private val graph = createGraphFactory<CliTestGraph.Factory>().create(
        config = CliConfig(silent = silent, parallel = CliConfig.PARALLEL_DISABLED),
        command = InvocationCommand(value = "s2c"),
        fileSystem = fileSystem,
        terminal = Terminal(terminalInterface = recorder),
        lineWriter = { line -> printedLines += line },
        ioDispatcher = dispatcher,
        mainDispatcher = dispatcher,
    )

    /** The production command, wired by the test graph. */
    val client: Client = graph.client

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
        graph.runner.run(
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
        graph.processorFactory.create(tempDirectory = null)
            .runAsFlow(
                path = input,
                output = output,
                config = parserConfig,
                recursive = false,
            )
            .flowOn(dispatcher)
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
