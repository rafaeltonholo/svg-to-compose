package dev.tonholo.s2c.dispatching

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.windows.GetSystemInfo
import platform.windows.SYSTEM_INFO

@OptIn(ExperimentalForeignApi::class)
actual fun availableProcessors(): Int = memScoped {
    val sysInfo = alloc<SYSTEM_INFO>()
    GetSystemInfo(sysInfo.ptr)
    sysInfo.dwNumberOfProcessors.toInt().coerceAtLeast(1)
}
