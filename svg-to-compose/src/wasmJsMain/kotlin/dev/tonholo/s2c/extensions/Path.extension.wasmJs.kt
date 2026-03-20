package dev.tonholo.s2c.extensions

import okio.FileSystem
import okio.Path
import okio.fakefilesystem.FakeFileSystem

private val wasmJsFileSystem: FileSystem = FakeFileSystem()

/** Returns a [FakeFileSystem] since real filesystem access is unavailable in WasmJs. */
actual val Path.fileSystem: FileSystem
    get() = wasmJsFileSystem
