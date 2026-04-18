package dev.tonholo.s2c.cli.update

import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.OutputRenderer
import dev.zacsweers.metro.Inject

/**
 * Runs a version check and forwards an [ConversionEvent.UpdateAvailable] to
 * the given renderer when a newer release exists. Pairs the check result with
 * the wrapper-script invocation flag so the renderer can tailor the hint
 * (e.g. `s2c --upgrade` for wrapper users, a download URL otherwise).
 *
 * Designed to be called after the processor flow completes so the update
 * banner appears alongside the final run summary.
 */
@Inject
internal class UpdateNotifier(
    private val versionChecker: VersionChecker,
    private val wrapperDetector: WrapperDetector,
) {
    /**
     * Checks for an available update and, when found, emits a
     * [ConversionEvent.UpdateAvailable] through [renderer]. A [UpdateCheckResult.NoUpdate]
     * result is a no-op so callers can invoke this unconditionally.
     */
    suspend fun notifyIfUpdateAvailable(renderer: OutputRenderer) {
        val result = versionChecker.check()
        if (result !is UpdateCheckResult.UpdateAvailable) return

        renderer.onEvent(
            ConversionEvent.UpdateAvailable(
                current = result.current.toString(),
                latest = result.latest.toString(),
                releaseUrl = result.releaseUrl,
                isWrapper = wrapperDetector.isRunningFromWrapper(),
            ),
        )
    }
}
