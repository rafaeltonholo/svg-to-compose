package dev.tonholo.s2c.dispatching

import kotlinx.cinterop.ExperimentalForeignApi
import platform.posix._SC_NPROCESSORS_ONLN
import platform.posix.sysconf

@OptIn(ExperimentalForeignApi::class)
actual fun availableProcessors(): Int = sysconf(_SC_NPROCESSORS_ONLN).toInt().coerceAtLeast(1)
