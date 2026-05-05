package dev.tonholo.s2c.cli.output.report

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UShortVar
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.set
import platform.windows.CF_UNICODETEXT
import platform.windows.CloseClipboard
import platform.windows.EmptyClipboard
import platform.windows.GMEM_MOVEABLE
import platform.windows.GlobalAlloc
import platform.windows.GlobalFree
import platform.windows.GlobalLock
import platform.windows.GlobalUnlock
import platform.windows.OpenClipboard
import platform.windows.SetClipboardData

private const val WIDE_CHAR_BYTES = 2

/**
 * Native Windows clipboard write via Win32's `OpenClipboard` +
 * `SetClipboardData(CF_UNICODETEXT, ...)` sequence.
 *
 * The clipboard takes ownership of the `GlobalAlloc`-d buffer on a
 * successful `SetClipboardData` call, so we must only call `GlobalFree`
 * when that call fails; otherwise we would free memory the OS now owns.
 */
@OptIn(ExperimentalForeignApi::class)
internal actual fun copyToClipboard(text: String): Boolean {
    if (OpenClipboard(hWndNewOwner = null) == 0) return false
    return try {
        writeWithClipboardOpen(text = text)
    } finally {
        CloseClipboard()
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun writeWithClipboardOpen(text: String): Boolean {
    EmptyClipboard()
    val codeUnits = text.length + 1
    val byteCount = (codeUnits * WIDE_CHAR_BYTES).toULong()
    val handle = GlobalAlloc(uFlags = GMEM_MOVEABLE.toUInt(), dwBytes = byteCount) ?: return false
    val locked = GlobalLock(hMem = handle)
    if (locked == null) {
        GlobalFree(hMem = handle)
        return false
    }
    writeCodeUnits(pointer = locked.reinterpret(), text = text)
    GlobalUnlock(hMem = handle)
    if (SetClipboardData(uFormat = CF_UNICODETEXT.toUInt(), hMem = handle) == null) {
        GlobalFree(hMem = handle)
        return false
    }
    return true
}

@OptIn(ExperimentalForeignApi::class)
private fun writeCodeUnits(pointer: CPointer<UShortVar>, text: String) {
    for (index in text.indices) {
        pointer[index] = text[index].code.toUShort()
    }
    pointer[text.length] = 0u
}
