package dev.tonholo.s2c.extensions

import okio.FileSystem
import okio.Path

val Path.isDirectory: Boolean
    get() {
        val metadata = FileSystem.SYSTEM.metadataOrNull(this)
        return metadata != null && metadata.isDirectory ||
                name.matches(".*\\.[a-zA-Z0-9]*$".toRegex()).not()
    }

val Path.extension: String
    get() = segments
        .last()
        .let { name ->
            val indexOf = name.lastIndexOf(".")
            if (indexOf == -1) return@let ""

            name.substring(name.lastIndexOf("."), name.length)
        }

val Path.isFile: Boolean
    get() = extension.isNotEmpty()
