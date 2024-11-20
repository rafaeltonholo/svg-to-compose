package dev.tonholo.s2c.extensions

import okio.ByteString.Companion.toByteString
import okio.FileSystem
import okio.Path

expect val Path.fileSystem: FileSystem

val Path.isDirectory: Boolean
    get() {
        val metadata = fileSystem.metadataOrNull(this)
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

fun Path.encodeToMd5(): String =
    toString().encodeToByteArray().toByteString().md5().hex()

val Path.filename
    get() = segments
        .last()
        .dropLastWhile { it != '.' }
        .dropLast(n = 1)
