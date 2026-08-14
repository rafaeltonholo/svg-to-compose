package dev.tonholo.s2c.cli.output.report

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.posix.SIGPIPE
import platform.posix.SIG_IGN
import platform.posix.fwrite
import platform.posix.pclose
import platform.posix.popen
import platform.posix.signal

/**
 * Linux clipboard write via a child process.
 *
 * X11 does not expose a POSIX clipboard API; there is only the ICCCM
 * selection protocol over Xlib, which we do not link. The conventional
 * workaround on headless-capable Linux distributions is `xclip` or
 * `xsel`. The user is expected to have one installed; when neither is
 * present the function returns `false` so the caller can tell them to
 * copy from the saved report file.
 *
 * We write through `popen(..., "w")` so the command consumes stdin,
 * avoiding shell-escaping bugs that would appear if we embedded the
 * report body into the command string.
 *
 * SIGPIPE is ignored before writing: when the helper binary is missing,
 * the shell exits before consuming stdin and the flush would otherwise
 * kill the whole process instead of falling through to the next command.
 */
@OptIn(ExperimentalForeignApi::class)
internal actual fun copyToClipboard(text: String): Boolean {
    signal(SIGPIPE, SIG_IGN)
    for (command in CLIPBOARD_COMMANDS) {
        if (runClipboardCommand(command = command, text = text)) return true
    }
    return false
}

@OptIn(ExperimentalForeignApi::class)
private fun runClipboardCommand(command: String, text: String): Boolean {
    val pipe = popen(command, "w") ?: return false
    val bytes = text.encodeToByteArray()
    val written = bytes.usePinned { pinned ->
        if (bytes.isEmpty()) {
            0uL
        } else {
            fwrite(pinned.addressOf(0), 1u, bytes.size.toULong(), pipe)
        }
    }
    val exitStatus = pclose(pipe)
    return exitStatus == 0 && written.toInt() == bytes.size
}

private val CLIPBOARD_COMMANDS = listOf(
    "xclip -selection clipboard 2>/dev/null",
    "xsel --clipboard --input 2>/dev/null",
    "wl-copy 2>/dev/null",
)
