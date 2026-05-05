package dev.tonholo.s2c.cli.output.report

/**
 * Returns a short, human-readable description of the host platform used to
 * populate bug report metadata. Format is "<OS> <arch>" (e.g. "macOS arm64",
 * "Linux x86_64", "Windows x86_64", "JVM 17 on macOS").
 *
 * Values are best-effort and not intended for programmatic comparison.
 */
internal expect fun platformInfo(): String
