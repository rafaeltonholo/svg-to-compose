package dev.tonholo.s2c.extensions

import okio.FileSystem
import okio.Path
import okio.fakefilesystem.FakeFileSystem

private val jsFileSystem: FileSystem = FakeFileSystem()

/** Returns a [FakeFileSystem] since real filesystem access is unavailable in JS. */
actual val Path.fileSystem: FileSystem
    get() = jsFileSystem
