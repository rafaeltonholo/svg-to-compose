package dev.tonholo.s2c.cli.update

import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.OutputRenderer
import dev.zacsweers.metro.Inject
import kotlinx.serialization.SerializationException
import okio.IOException

/**
 * Runs a version check and forwards an [ConversionEvent.UpdateAvailable] to
 * the given renderer when a newer release exists. Pairs the check result with
 * the wrapper-script invocation flag so the renderer can tailor the hint
 * (e.g. `s2c --upgrade` for wrapper users, a download URL otherwise).
 *
 * Designed to be called after the processor flow completes so the update
 * banner appears alongside the final run summary.
 *
 * The update check is best-effort: I/O and deserialization failures are
 *  swallowed, so a broken or unreachable release endpoint never aborts the
 * primary CLI flow.
 */
@Inject
internal class UpdateNotifier(
    private val versionChecker: VersionChecker,
    private val wrapperDetector: WrapperDetector,
    private val logger: Logger,
) {
    /**
     * Checks for an available update and, when found, emits a
     * [ConversionEvent.UpdateAvailable] through [renderer]. A [UpdateCheckResult.NoUpdate]
     * result is a no-op so callers can invoke this unconditionally.
     *
     * Any [IOException] or [SerializationException] raised while checking
     * for updates is logged at debug level and swallowed so callers can invoke
     * this unconditionally without wrapping the call themselves.
     */
    suspend fun notifyIfUpdateAvailable(renderer: OutputRenderer) {
        val result = try {
            versionChecker.check()
        } catch (e: IOException) {
            logger.debug("Update check failed (I/O): ${e.message}")
            return
        } catch (e: SerializationException) {
            logger.debug("Update check failed (parse): ${e.message}")
            return
        }
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
