package dev.tonholo.s2c.cli.output.report

/**
 * Copies [text] to the host system clipboard.
 *
 * Implementations are best-effort: they return `false` when the platform
 * clipboard is unreachable (headless JVM, missing `xclip`/`xsel` on Linux,
 * Windows clipboard contention) rather than throwing. Callers surface the
 * return value to the user so they can fall back to copying from the saved
 * report file.
 *
 * Not suitable for secrets; the CLI never places proprietary content on the
 * clipboard, only user-facing error reports built from public data.
 *
 * @return `true` if the text was written, `false` otherwise.
 */
internal expect fun copyToClipboard(text: String): Boolean
