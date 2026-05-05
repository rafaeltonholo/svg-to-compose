package dev.tonholo.s2c.cli.output.report

internal actual fun platformInfo(): String {
    val osName = System.getProperty("os.name") ?: "unknown"
    val osArch = System.getProperty("os.arch") ?: "unknown"
    val javaVersion = System.getProperty("java.version") ?: "unknown"
    return "JVM $javaVersion on $osName $osArch"
}
