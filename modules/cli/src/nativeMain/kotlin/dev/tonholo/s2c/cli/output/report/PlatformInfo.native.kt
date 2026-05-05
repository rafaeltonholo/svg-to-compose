package dev.tonholo.s2c.cli.output.report

import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.CpuArchitecture
import kotlin.native.OsFamily
import kotlin.native.Platform

@OptIn(ExperimentalNativeApi::class)
internal actual fun platformInfo(): String {
    val os = when (Platform.osFamily) {
        OsFamily.MACOSX -> "macOS"
        OsFamily.LINUX -> "Linux"
        OsFamily.WINDOWS -> "Windows"
        else -> Platform.osFamily.name.lowercase().replaceFirstChar { it.uppercase() }
    }
    val arch = when (Platform.cpuArchitecture) {
        CpuArchitecture.ARM64 -> "arm64"
        CpuArchitecture.X64 -> "x86_64"
        CpuArchitecture.X86 -> "x86"
        CpuArchitecture.ARM32 -> "arm32"
        else -> Platform.cpuArchitecture.name.lowercase()
    }
    return "$os $arch"
}
