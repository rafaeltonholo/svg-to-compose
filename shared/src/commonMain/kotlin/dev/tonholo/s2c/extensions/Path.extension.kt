package dev.tonholo.s2c.extensions

import okio.Path

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
