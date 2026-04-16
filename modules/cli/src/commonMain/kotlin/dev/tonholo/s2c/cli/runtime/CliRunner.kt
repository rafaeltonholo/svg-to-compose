package dev.tonholo.s2c.cli.runtime

import com.github.ajalt.mordant.input.coroutines.receiveKeyEventsFlow
import com.github.ajalt.mordant.input.isCtrlC
import com.github.ajalt.mordant.platform.MultiplatformSystem.exitProcess
import com.github.ajalt.mordant.terminal.Terminal
import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.SvgToComposeContext
import dev.tonholo.s2c.cli.inject.coroutine.IoDispatcher
import dev.tonholo.s2c.cli.inject.coroutine.MainDispatcher
import dev.tonholo.s2c.cli.output.log.FileCompletionEntry
import dev.tonholo.s2c.cli.output.log.RunLogWriter
import dev.tonholo.s2c.cli.output.renderer.JsonRenderer
import dev.tonholo.s2c.cli.output.renderer.PlainTextRenderer
import dev.tonholo.s2c.cli.output.renderer.TuiRenderer
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.FileResult
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.output.RunStats
import dev.tonholo.s2c.parser.IconMapperFn
import dev.tonholo.s2c.updateConfig
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import okio.IOException
import okio.Path

private const val SIGINT_EXIT_CODE = 130

@Inject
internal class CliRunner(
    private val processorFactory: Processor.Factory,
    private val terminal: Terminal,
    private val context: SvgToComposeContext,
    private val fileManager: FileManager,
    @param:IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    @param:MainDispatcher
    private val mainDispatcher: CoroutineDispatcher,
) {
    suspend fun run(
        config: RunConfig,
        mapIconNameTo: IconMapperFn,
        outputFormat: OutputFormat = OutputFormat.Text,
        logDir: Path = RunLogWriter.DEFAULT_LOG_DIR,
    ) {
        val processor = processorFactory.create(tempDirectory = null)
        try {
            val useTui = terminal.terminalInfo.interactive && !config.noTui

            if (useTui) {
                runWithTui(
                    processor = processor,
                    config = config,
                    mapIconNameTo = mapIconNameTo,
                    logDir = logDir,
                )
            } else {
                runWithoutTui(
                    processor = processor,
                    config = config,
                    mapIconNameTo = mapIconNameTo,
                    outputFormat = outputFormat,
                    logDir = logDir,
                )
            }
        } finally {
            processor.dispose()
        }
    }

    private suspend fun runWithTui(
        processor: Processor,
        config: RunConfig,
        mapIconNameTo: IconMapperFn,
        logDir: Path,
    ) {
        val previousSilent = context.configSnapshot.silent

        // Avoiding logger leaking info to the TUI by silencing it here.
        context.updateConfig<CliConfig> { it.copy(silent = true) }

        val renderer = TuiRenderer(terminal = terminal)
        var stats: RunStats? = null
        val completedFiles = mutableListOf<FileCompletionEntry>()
        val scope = CoroutineScope(SupervisorJob() + mainDispatcher)

        try {
            scope.launch { renderer.run() }

            with(renderer) { scope.launchInputHandler() }

            val processorDeferred = scope.async(context = ioDispatcher) {
                processor.runAsFlow(
                    path = config.inputPath,
                    output = config.outputPath,
                    config = config.parserConfig,
                    recursive = config.recursive,
                    maxDepth = config.recursiveDepth,
                    mapIconName = mapIconNameTo,
                ).collect { event ->
                    renderer.onEvent(event)
                    if (event is ConversionEvent.FileCompleted) {
                        completedFiles.add(
                            FileCompletionEntry(
                                fileName = event.fileName,
                                duration = event.duration,
                                result = event.result,
                            ),
                        )
                    }
                    if (event is ConversionEvent.RunCompleted) {
                        stats = event.stats
                    }
                }
            }

            processorDeferred.await()
        } finally {
            scope.cancel()
            renderer.stop()
            context.updateConfig<CliConfig> { it.copy(silent = previousSilent) }
        }

        // `stats == null` means the processor never emitted `RunCompleted`.
        // The only path that reaches here today is `Processor.run` returning
        // early when the single-file input matches `--exclude`. Treating it
        // as a no-op is correct: no files processed, no log to write.
        val runStats = stats ?: return
        finalizeRun(
            config = config,
            stats = runStats,
            completedFiles = completedFiles,
            logDir = logDir,
            printSummary = true,
        )
    }

    /**
     * Launches a coroutine that listens for keyboard events via
     * Mordant's [receiveKeyEventsFlow]. The flow handles raw mode
     * setup/teardown internally and integrates with Mordant's
     * signal handling.
     *
     * On Ctrl+C the TUI is stopped and the process exits immediately
     * with code 130 (128 + SIGINT). A graceful coroutine cancellation
     * is not possible here because [Processor.run] blocks the thread
     * on native I/O and cannot be interrupted cooperatively.
     */
    context(renderer: TuiRenderer)
    private fun CoroutineScope.launchInputHandler(): Job = launch(ioDispatcher) {
        val parent = this
        terminal.receiveKeyEventsFlow()
            .takeWhile { event ->
                if (event.isCtrlC) {
                    renderer.stop()
                    exitProcess(SIGINT_EXIT_CODE)
                }
                true
            }
            .onCompletion {
                parent.cancel()
            }
            .collect { event ->
                renderer.handleKeyEvent(event)
            }
    }

    private fun runWithoutTui(
        processor: Processor,
        config: RunConfig,
        mapIconNameTo: IconMapperFn,
        outputFormat: OutputFormat,
        logDir: Path,
    ) {
        val isSilent = context.configSnapshot.silent
        val renderer = when (outputFormat) {
            OutputFormat.Json -> JsonRenderer()
            OutputFormat.Text -> PlainTextRenderer()
        }

        // Silence logger when JSON output is active. JSON events carry all
        // progress information; logger text would break the JSONL stream.
        // Silent is intentionally never restored: in JSON mode the process
        // exits via exitProcess() after Client.run() handles the exception,
        // so restoring would only re-enable plain-text output that
        // contaminates the JSONL stream.
        if (outputFormat == OutputFormat.Json) {
            context.updateConfig<CliConfig> { it.copy(silent = true) }
        }

        var stats: RunStats? = null
        val completedFiles = mutableListOf<FileCompletionEntry>()
        processor.run(
            path = config.inputPath,
            output = config.outputPath,
            config = config.parserConfig,
            recursive = config.recursive,
            maxDepth = config.recursiveDepth,
            mapIconName = mapIconNameTo,
            onEvent = { event ->
                if (outputFormat == OutputFormat.Json || !isSilent) {
                    renderer.onEvent(event)
                }
                if (event is ConversionEvent.FileCompleted) {
                    completedFiles.add(
                        FileCompletionEntry(
                            fileName = event.fileName,
                            duration = event.duration,
                            result = event.result,
                        ),
                    )
                }
                if (event is ConversionEvent.RunCompleted) {
                    stats = event.stats
                }
            },
        )

        val runStats = stats
        if (runStats != null) {
            // In JSON mode the failure summary would contaminate the JSONL
            // stream, so the log file is still written but no extra lines
            // are printed to the terminal.
            finalizeRun(
                config = config,
                stats = runStats,
                completedFiles = completedFiles,
                logDir = logDir,
                printSummary = outputFormat != OutputFormat.Json,
            )
        }

        val failedCount = runStats?.failed ?: 0
        if (failedCount > 0) {
            throw ExitProgramException(
                errorCode = ErrorCode.FailedToParseIconError,
                message = "Failure to parse ($failedCount) file(s). See output for details.",
            )
        }
    }

    /**
     * Writes the run log to [logDir] and, when [printSummary] is true,
     * prints a failure summary + log path to the terminal.
     *
     * Log-write failures are intentionally non-fatal: they are reported to
     * the terminal as a warning, but the failure summary and the
     * [ExitProgramException] path still run so the user always sees why the
     * run failed. This mirrors the TUI path's expectations.
     */
    private fun finalizeRun(
        config: RunConfig,
        stats: RunStats,
        completedFiles: List<FileCompletionEntry>,
        logDir: Path,
        printSummary: Boolean,
    ) {
        if (completedFiles.isEmpty()) {
            if (printSummary) {
                maybePrintFailureSummary(completedFiles = completedFiles, logPath = null)
            }
            maybeThrowFailure(stats = stats)
            return
        }

        val logWriter = RunLogWriter(fileManager = fileManager, logDir = logDir)
        val logPath = try {
            logWriter.write(config = config, stats = stats, entries = completedFiles)
        } catch (e: IOException) {
            if (printSummary) {
                terminal.println()
                terminal.println("Warning: could not write run log to $logDir: ${e.message}")
            }
            null
        }

        if (printSummary) {
            maybePrintFailureSummary(completedFiles = completedFiles, logPath = logPath)
        }

        maybeThrowFailure(stats = stats)
    }

    private fun maybePrintFailureSummary(
        completedFiles: List<FileCompletionEntry>,
        logPath: Path?,
    ) {
        val failedEntries = completedFiles.filter { it.result is FileResult.Failed }
        if (failedEntries.isNotEmpty()) {
            terminal.println()
            terminal.println("Failed:")
            for (entry in failedEntries) {
                val result = entry.result as FileResult.Failed
                terminal.println("  ${entry.fileName}: ${result.errorCode.name} - ${result.message}")
            }
        }
        if (logPath != null) {
            terminal.println()
            terminal.println("Full log: $logPath")
        }
    }

    private fun maybeThrowFailure(stats: RunStats) {
        if (stats.failed > 0) {
            throw ExitProgramException(
                errorCode = ErrorCode.FailedToParseIconError,
                message = "Failure to parse (${stats.failed}) file(s). See log for details.",
            )
        }
    }
}
