package dev.tonholo.s2c.cli.output.renderer

import com.github.ajalt.mordant.input.KeyboardEvent
import com.github.ajalt.mordant.terminal.Terminal
import dev.tonholo.s2c.cli.output.report.copyToClipboard
import dev.tonholo.s2c.cli.output.tui.animation.AnimationController
import dev.tonholo.s2c.cli.output.tui.layout.ProgressBarLayouts
import dev.tonholo.s2c.cli.output.tui.reducer.reduceCompletion
import dev.tonholo.s2c.cli.output.tui.reducer.reduceCurrentFiles
import dev.tonholo.s2c.cli.output.tui.reducer.reduceHeader
import dev.tonholo.s2c.cli.output.tui.reducer.reduceMode
import dev.tonholo.s2c.cli.output.tui.reducer.reduceProgress
import dev.tonholo.s2c.cli.output.tui.reducer.reduceRecentFiles
import dev.tonholo.s2c.cli.output.tui.reducer.reduceSingleFileCompletion
import dev.tonholo.s2c.cli.output.tui.reducer.reduceUpdateNotification
import dev.tonholo.s2c.cli.output.tui.state.TuiMode
import dev.tonholo.s2c.cli.output.tui.state.TuiState
import dev.tonholo.s2c.cli.output.tui.widget.buildCompletionSummary
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.OutputRenderer
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import okio.FileSystem
import okio.IOException
import okio.Path
import okio.Path.Companion.toPath
import okio.SYSTEM
import kotlin.concurrent.Volatile

/**
 * TUI renderer that drives the live Mordant animation and, after the run
 * completes, prints the static completion summary and error report.
 *
 * Post-run interactivity: when the runner dispatches a
 * [ConversionEvent.ErrorReportGenerated] event, the renderer enters a
 * waiting mode where the input handler listens for `[c]` (copy report to
 * system clipboard) or any other key (exit). The [awaitUserExit] method
 * suspends until that key arrives so the runner can gate process shutdown
 * on the user's interaction.
 *
 * @param terminal Mordant terminal to render into.
 * @param stackTraceEnabled whether to include full stack traces in the
 *  failure breakdown.
 * @param fileSystem used to read the saved error report when the user
 *  presses `[c]`. Defaults to the real system file system.
 * @param clipboardWriter writes text to the host clipboard. Defaults to
 *  the platform-specific `copyToClipboard` expect/actual. Injected for
 *  tests so we do not touch the real pasteboard during a unit test.
 */
internal class TuiRenderer(
    private val terminal: Terminal,
    private val stackTraceEnabled: Boolean = false,
    private val fileSystem: FileSystem = FileSystem.SYSTEM,
    private val clipboardWriter: (String) -> Boolean = ::copyToClipboard,
) : OutputRenderer {
    private val state = MutableStateFlow(TuiState())
    private val controller = AnimationController(
        terminal = terminal,
        layouts = ProgressBarLayouts(terminal),
        state = state,
    )

    @Volatile
    private var completionPrinted = false

    @Volatile
    private var lastErrorReport: ConversionEvent.ErrorReportGenerated? = null

    private val userExitSignal = CompletableDeferred<Unit>()

    override fun onEvent(event: ConversionEvent) {
        state.update { current ->
            val nextMode = reduceMode(state = current.mode, event = event)
            val optimizationEnabled = current.header.config?.optimizationEnabled ?: true
            current
                .withMode { nextMode }
                .withHeader { reduceHeader(state = it, event = event) }
                .withProgress { reduceProgress(state = it, event = event) }
                .withCurrentFiles {
                    reduceCurrentFiles(
                        state = it,
                        event = event,
                        mode = nextMode,
                        optimizationEnabled = optimizationEnabled,
                    )
                }
                .withRecentFiles { reduceRecentFiles(state = it, event = event) }
                .withUpdateNotification { reduceUpdateNotification(state = it, event = event) }
                .withSingleFileCompletion {
                    reduceSingleFileCompletion(state = it, mode = nextMode, event = event)
                }
                .withCompletion { reduceCompletion(state = it, event = event) }
        }
        state.value.progress?.let { controller.sync(it) }
        when (event) {
            is ConversionEvent.RunCompleted -> finalizeCompletion()
            is ConversionEvent.ErrorReportGenerated -> printErrorReport(event = event)
            else -> Unit
        }
    }

    internal fun handleKeyEvent(event: KeyboardEvent) {
        when {
            event.ctrl && event.key == "c" -> stop()

            lastErrorReport != null && !userExitSignal.isCompleted ->
                handlePostReportKey(event = event)

            event.key == "h" && state.value.mode == TuiMode.Batch ->
                state.update { it.withHeader { h -> h.copy(expanded = !h.expanded) } }
        }
    }

    internal fun snapshotState(): TuiState = state.value

    suspend fun run() = controller.run()

    fun stop() = controller.stop()

    /**
     * Suspends until the user dismisses the post-run prompt, or returns
     * immediately if no error report was displayed (so non-interactive runs
     * and clean runs are not blocked on a keypress that will never arrive).
     */
    suspend fun awaitUserExit() {
        if (lastErrorReport == null) return
        userExitSignal.await()
    }

    /**
     * Releases [awaitUserExit] when the input flow terminates without a
     * keypress (e.g. stdin EOF, terminal pipe closed). Without this, the
     * runner would suspend forever in [awaitUserExit] after the input
     * handler shut down, since the only other path that completes
     * [userExitSignal] is a real key event from the user.
     */
    internal fun signalInputClosed() {
        userExitSignal.complete(value = Unit)
    }

    /**
     * Stops the live animation and prints the final completion summary as
     * static text. Called once, when [ConversionEvent.RunCompleted] arrives.
     * Guarded by [completionPrinted] so accidental double-dispatch cannot
     * produce a duplicated summary.
     */
    private fun finalizeCompletion() {
        if (completionPrinted) return
        completionPrinted = true
        controller.stop()
        terminal.println(
            buildCompletionSummary(
                state = state.value.completion,
                stackTraceEnabled = stackTraceEnabled,
            ),
        )
    }

    /**
     * Appends the error report file path, pre-filled bug URL, and the
     * interactive `[c]` hint. Called when a
     * [ConversionEvent.ErrorReportGenerated] arrives from the runner
     * post-RunCompleted.
     */
    private fun printErrorReport(event: ConversionEvent.ErrorReportGenerated) {
        lastErrorReport = event
        terminal.println()
        terminal.println("Error report saved to: ${event.reportPath}")
        terminal.println("Report a bug: ${event.bugReportUrl}")
        terminal.println()
        terminal.println("Press [c] to copy the error report to clipboard, or any other key to exit.")
    }

    /**
     * Handles key presses after the error report banner was shown. `[c]`
     * triggers a clipboard write using the saved report file contents;
     * every other key silently dismisses the prompt. In both cases the
     * exit signal is completed so the runner can continue shutdown.
     */
    private fun handlePostReportKey(event: KeyboardEvent) {
        val report = lastErrorReport ?: return
        if (event.key == "c") {
            performClipboardCopy(report = report)
        }
        userExitSignal.complete(Unit)
    }

    private fun performClipboardCopy(report: ConversionEvent.ErrorReportGenerated) {
        val path = report.reportPath.toPath()
        val contents = readReportContents(path = path)
        if (contents == null) {
            terminal.println("Could not read error report at ${report.reportPath}; copy it manually.")
            return
        }
        val copied = clipboardWriter(contents)
        if (copied) {
            terminal.println("Error report copied to clipboard.")
        } else {
            terminal.println(
                "Clipboard is unreachable on this platform; copy manually from ${report.reportPath}.",
            )
        }
    }

    private fun readReportContents(path: Path): String? = try {
        fileSystem.read(file = path) { readUtf8() }
    } catch (_: IOException) {
        null
    }
}
