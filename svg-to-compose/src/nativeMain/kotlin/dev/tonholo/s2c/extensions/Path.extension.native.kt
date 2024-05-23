package dev.tonholo.s2c.extensions

import okio.FileSystem
import okio.Path

actual val Path.fileSystem: FileSystem
    get() = FileSystem.SYSTEM
