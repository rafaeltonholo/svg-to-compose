package dev.tonholo.s2c.extensions

import okio.FileSystem
import okio.Path

val Path.isDirectory: Boolean
    get() {
        val metadata = FileSystem.SYSTEM.metadataOrNull(this)
        return metadata != null && metadata.isDirectory ||
                name.matches(".*\\.[a-zA-Z0-9]*$".toRegex()).not()
    }
