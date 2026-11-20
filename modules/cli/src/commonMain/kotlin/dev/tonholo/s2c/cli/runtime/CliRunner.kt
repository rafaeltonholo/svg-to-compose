package dev.tonholo.s2c.cli.runtime

import com.github.ajalt.mordant.input.coroutines.receiveKeyEventsFlow
import com.github.ajalt.mordant.input.isCtrlC
import com.github.ajalt.mordant.platform.MultiplatformSystem.exitProcess
import com.github.ajalt.mordant.terminal.Terminal
import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.SvgToComposeContext
import dev.tonholo.s2c.cli.inject.coroutine.IoDispatcher
import dev.tonholo.s2c.cli.inject.coroutine.MainDispatcher
import dev.tonholo.s2c.cli.output.LineWriter
import dev.tonholo.s2c.cli.output.log.FileCompletionEntry
import dev.tonholo.s2c.cli.output.log.RunLogWriter
import dev.tonholo.s2c.cli.output.renderer.JsonRenderer
import dev.tonholo.s2c.cli.output.renderer.PlainTextRenderer
import dev.tonholo.s2c.cli.output.renderer.TuiRenderer
import dev.tonholo.s2c.cli.output.report.BugReportFailure
import dev.tonholo.s2c.cli.output.report.BugReportUrlBuilder
import dev.tonholo.s2c.cli.output.report.ErrorReportWriter
import dev.tonholo.s2c.cli.output.report.InvocationCommand
import dev.tonholo.s2c.cli.output.report.platformInfo
import dev.tonholo.s2c.cli.update.UpdateNotifier
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.FileResult
import dev.tonholo.s2c.output.OutputRenderer
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
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import okio.FileSystem
import okio.IOException
import okio.Path
import okio.Path.Companion.toPath
import kotlin.time.Clock

private const val SIGINT_EXIT_CODE = 130

@Inject
internal class CliRunner(
    private val processorFactory: Processor.Factory,
    private val terminal: Terminal,
    private val context: SvgToComposeContext,
    private val fileManager: FileManager,
    private val fileSystem: FileSystem,
    private val updateNotifier: UpdateNotifier,
    private val invocationCommand: InvocationCommand,
    @param:IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    @param:MainDispatcher
    private val mainDispatcher: CoroutineDispatcher,
    private val lineWriter: LineWriter,
) {
    suspend fun run(
        config: RunConfig,
        mapIconNameTo: IconMapperFn,
        outputFormat: OutputFormat = OutputFormat.Text,
        logDir: Path = RunLogWriter.DEFAULT_LOG_DIR,
    ) {
        val processor = processorFactory.create(tempDirectory = null)
        try {
            val useTui = terminal.terminalInfo.interactive &&
                !config.noTui &&
                !context.configSnapshot.silent

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

        val renderer = TuiRenderer(
            terminal = terminal,
            stackTraceEnabled = context.configSnapshot.stackTrace,
            fileSystem = fileSystem,
        )
        val record = RunRecord()
        val scope = CoroutineScope(SupervisorJob() + mainDispatcher)

        try {
            scope.launch { renderer.run() }

            with(renderer) { scope.launchInputHandler() }

            val processorDeferred = scope.async {
                processor.runAsFlow(
                    path = config.inputPath,
                    output = config.outputPath,
                    config = config.parserConfig,
                    recursive = config.recursive,
                    maxDepth = config.recursiveDepth,
                    mapIconName = mapIconNameTo,
                )
                    .flowOn(ioDispatcher)
                    .collect { event ->
                        renderer.onEvent(event)
                        record.record(event)
                    }
            }

            processorDeferred.await()
            emitErrorReportIfNeeded(
                renderer = renderer,
                record = record,
                config = config,
                stackTraceEnabled = context.configSnapshot.stackTrace,
                suppressTerminalWarnings = false,
            )
            updateNotifier.notifyIfUpdateAvailable(renderer = renderer)
            renderer.awaitUserExit()
        } finally {
            scope.cancel()
            renderer.stop()
            context.updateConfig<CliConfig> { it.copy(silent = previousSilent) }
        }

        val runStats = record.stats ?: return
        finalizeRun(
            config = config,
            stats = runStats,
            completedFiles = record.completedFiles,
            logDir = logDir,
            quiet = false,
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
                renderer.signalInputClosed()
                parent.cancel()
            }
            .collect { event ->
                renderer.handleKeyEvent(event)
            }
    }

    private suspend fun runWithoutTui(
        processor: Processor,
        config: RunConfig,
        mapIconNameTo: IconMapperFn,
        outputFormat: OutputFormat,
        logDir: Path,
    ) {
        val isSilent = context.configSnapshot.silent
        val quiet = isSilent || outputFormat == OutputFormat.Json
        val renderer = when (outputFormat) {
            OutputFormat.Json -> JsonRenderer(writer = lineWriter::write)
            OutputFormat.Text -> PlainTextRenderer(writer = lineWriter::write)
        }

        if (outputFormat == OutputFormat.Json) {
            context.updateConfig<CliConfig> { it.copy(silent = true) }
        }

        val record = RunRecord()
        val eventSink: OutputRenderer = if (isSilent) OutputRenderer { } else renderer
        processor.runAsFlow(
            path = config.inputPath,
            output = config.outputPath,
            config = config.parserConfig,
            recursive = config.recursive,
            maxDepth = config.recursiveDepth,
            mapIconName = mapIconNameTo,
        )
            .flowOn(ioDispatcher)
            .collect { event ->
                eventSink.onEvent(event)
                record.record(event)
            }
        emitErrorReportIfNeeded(
            renderer = eventSink,
            record = record,
            config = config,
            stackTraceEnabled = context.configSnapshot.stackTrace,
            suppressTerminalWarnings = quiet,
        )
        updateNotifier.notifyIfUpdateAvailable(renderer = eventSink)

        val runStats = record.stats ?: return
        finalizeRun(
            config = config,
            stats = runStats,
            completedFiles = record.completedFiles,
            logDir = logDir,
            quiet = quiet,
        )
    }

    /**
     * Writes the run log to [logDir] and points the user at it.
     *
     * The renderers already print the failure breakdown, so the only thing
     * left to surface here is the log path.
     *
     * Log-write failures are intentionally non-fatal: they are reported to the
     * terminal as a warning, and the [ExitProgramException] path still runs so
     * the user always sees why the run failed.
     *
     * @param quiet when `true`, nothing is written to the terminal. JSON output
     *  owns stdout and would be corrupted by stray text, and `--silent` runs
     *  must not print at all.
     */
    private fun finalizeRun(
        config: RunConfig,
        stats: RunStats,
        completedFiles: List<FileCompletionEntry>,
        logDir: Path,
        quiet: Boolean,
    ) {
        if (completedFiles.isEmpty()) {
            maybeThrowFailure(stats = stats, logPath = null)
            return
        }

        val logWriter = RunLogWriter(fileManager = fileManager, logDir = logDir)
        val logPath = try {
            logWriter.write(config = config, stats = stats, entries = completedFiles)
        } catch (e: IOException) {
            if (!quiet) {
                terminal.println()
                terminal.println("Warning: could not write run log to $logDir: ${e.message}")
            }
            null
        }

        if (!quiet) {
            printLogPath(logPath = logPath)
        }

        maybeThrowFailure(stats = stats, logPath = logPath)
    }

    private fun printLogPath(logPath: Path?) {
        if (logPath != null) {
            terminal.println()
            terminal.println("Full log: $logPath")
        }
    }

    /**
     * Writes an auto-saved error report file and dispatches a
     * [ConversionEvent.ErrorReportGenerated] through [renderer] so the TUI,
     * plain-text, and JSON surfaces all display the report path and a
     * pre-filled bug report URL.
     *
     * A no-op when the run had no failures, when the processor never emitted
     * [ConversionEvent.RunCompleted], or when the file write fails; failures
     * to write the report are surfaced as a terminal warning and intentionally
     * do not crash the run.
     */
    private fun emitErrorReportIfNeeded(
        renderer: OutputRenderer,
        record: RunRecord,
        config: RunConfig,
        stackTraceEnabled: Boolean,
        suppressTerminalWarnings: Boolean,
    ) {
        val runStats = record.stats ?: return
        if (runStats.failed == 0) return
        val failures = record.completedFiles.toBugReportFailures()
        if (failures.isEmpty()) return

        val reportPath = writeErrorReport(
            version = record.version,
            config = config,
            stats = runStats,
            failures = failures,
            stackTraceEnabled = stackTraceEnabled,
            suppressTerminalWarnings = suppressTerminalWarnings,
        ) ?: return

        renderer.onEvent(
            ConversionEvent.ErrorReportGenerated(
                reportPath = reportPath.toString(),
                bugReportUrl = BugReportUrlBuilder().build(
                    version = record.version,
                    platform = platformInfo(),
                    commandLine = invocationCommand.value,
                    totalFiles = runStats.totalFiles,
                    succeeded = runStats.succeeded,
                    failedFiles = failures,
                ),
            ),
        )
    }

    private fun List<FileCompletionEntry>.toBugReportFailures(): List<BugReportFailure> =
        mapNotNull { entry ->
            val result = entry.result as? FileResult.Failed ?: return@mapNotNull null
            BugReportFailure(
                fileName = entry.fileName,
                errorCode = result.errorCode,
                message = result.message,
                stackTrace = result.stackTrace,
            )
        }

    /**
     * Writes the error report file via [ErrorReportWriter] and returns its
     * path, or `null` if the write failed. I/O failures are reported to the
     * terminal as a warning and swallowed: a broken report is not worth
     * crashing the run over.
     *
     * @param suppressTerminalWarnings when `true`, the I/O-failure warning is
     *  not printed to the terminal. JSON output mode passes `true` so the
     *  JSONL stream stays parseable; mixing raw text into stdout would
     *  corrupt the protocol the same way a stray run-log warning would.
     */
    private fun writeErrorReport(
        version: String,
        config: RunConfig,
        stats: RunStats,
        failures: List<BugReportFailure>,
        stackTraceEnabled: Boolean,
        suppressTerminalWarnings: Boolean,
    ): Path? {
        val workingDir = ".".toPath()
        val writer = ErrorReportWriter(
            fileSystem = fileSystem,
            workingDir = workingDir,
            nowEpochMillis = { Clock.System.now().toEpochMilliseconds() },
            platformInfoProvider = ::platformInfo,
        )
        return try {
            writer.write(
                version = version,
                config = config,
                totalFiles = stats.totalFiles,
                succeeded = stats.succeeded,
                failedFiles = failures,
                stackTraceEnabled = stackTraceEnabled,
            )
        } catch (e: IOException) {
            if (!suppressTerminalWarnings) {
                terminal.println()
                terminal.println("Warning: could not write error report to $workingDir: ${e.message}")
            }
            null
        }
    }

    private fun maybeThrowFailure(stats: RunStats, logPath: Path?) {
        if (stats.failed > 0) {
            val detail = if (logPath != null) "See log for details." else "See output for details."
            throw ExitProgramException(
                errorCode = ErrorCode.FailedToParseIconError,
                message = "Failure to parse (${stats.failed}) file(s). $detail",
            )
        }
    }
}

/**
 * Accumulates the parts of a run that the finalizer needs after the event
 * stream ends: the tool version, the per-file outcomes, and the run
 * statistics.
 */
private class RunRecord {
    private val entries = mutableListOf<FileCompletionEntry>()

    var version: String = ""
        private set

    /**
     * Statistics of the finished run, or `null` when the processor never
     * emitted [ConversionEvent.RunCompleted]. That happens when the processor
     * returns early, for example when the only input file matches `--exclude`:
     * no files were processed, so there is nothing to log.
     */
    var stats: RunStats? = null
        private set

    val completedFiles: List<FileCompletionEntry>
        get() = entries

    fun record(event: ConversionEvent) {
        when (event) {
            is ConversionEvent.RunStarted -> version = event.version

            is ConversionEvent.FileCompleted -> entries += FileCompletionEntry(
                fileName = event.fileName,
                duration = event.duration,
                result = event.result,
            )

            is ConversionEvent.RunCompleted -> stats = event.stats

            else -> Unit
        }
    }
}
