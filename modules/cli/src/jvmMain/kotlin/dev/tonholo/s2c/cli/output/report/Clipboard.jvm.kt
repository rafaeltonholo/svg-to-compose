package dev.tonholo.s2c.cli.output.report

import java.awt.HeadlessException
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

internal actual fun copyToClipboard(text: String): Boolean = try {
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    clipboard.setContents(StringSelection(text), null)
    true
} catch (_: HeadlessException) {
    // The JVM is running without a display; AWT cannot reach a system clipboard.
    // Common in CI, Docker containers, and some remote SSH sessions.
    false
} catch (_: IllegalStateException) {
    // Another process currently owns the clipboard (Windows racing writer, etc.).
    false
} catch (_: SecurityException) {
    // A SecurityManager denied `AWTPermission("accessClipboard")`.
    false
}
