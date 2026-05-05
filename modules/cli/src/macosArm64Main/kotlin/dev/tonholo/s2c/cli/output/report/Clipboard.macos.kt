package dev.tonholo.s2c.cli.output.report

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AppKit.NSPasteboard
import platform.AppKit.NSPasteboardTypeString

/**
 * Native macOS clipboard write via AppKit's `NSPasteboard`.
 *
 * AppKit bindings ship with the Kotlin/Native standard distribution, so no
 * custom cinterop definitions are required. The general pasteboard is shared
 * across the user session; we must clear previous contents before writing
 * (per Apple's guidance) or `setString:forType:` becomes a no-op on repeat
 * writes with the same declared type.
 */
@OptIn(ExperimentalForeignApi::class)
internal actual fun copyToClipboard(text: String): Boolean {
    val pasteboard = NSPasteboard.generalPasteboard()
    pasteboard.clearContents()
    return pasteboard.setString(text, forType = NSPasteboardTypeString)
}
