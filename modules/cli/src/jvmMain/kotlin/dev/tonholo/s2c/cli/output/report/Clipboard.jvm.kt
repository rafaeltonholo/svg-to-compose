package dev.tonholo.s2c.cli.output.report

import java.io.IOException
import java.util.concurrent.TimeUnit

internal actual fun copyToClipboard(text: String): Boolean =
    clipboardCommandsFor(osName = System.getProperty("os.name").orEmpty())
        .any { command -> runClipboardCommand(command = command, text = text) }

/**
 * Resolves the clipboard writer candidates for [osName], most preferred
 * first. The JVM fallback stays terminal-only by piping to the platform's
 * clipboard command instead of initializing AWT, which would bounce a Dock
 * icon on macOS.
 */
internal fun clipboardCommandsFor(osName: String): List<List<String>> {
    val normalized = osName.lowercase()
    return when {
        normalized.contains("mac") -> listOf(listOf("pbcopy"))
        normalized.contains("win") -> listOf(listOf("clip"))
        else -> listOf(
            listOf("xclip", "-selection", "clipboard"),
            listOf("xsel", "--clipboard", "--input"),
            listOf("wl-copy"),
        )
    }
}

private fun runClipboardCommand(command: List<String>, text: String): Boolean = try {
    val process = ProcessBuilder(command)
        .redirectErrorStream(true)
        .start()
    process.outputStream.use { stream -> stream.write(text.encodeToByteArray()) }
    if (process.waitFor(CLIPBOARD_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
        process.exitValue() == 0
    } else {
        process.destroyForcibly()
        false
    }
} catch (_: IOException) {
    false
} catch (_: InterruptedException) {
    Thread.currentThread().interrupt()
    false
}

private const val CLIPBOARD_TIMEOUT_SECONDS = 5L
