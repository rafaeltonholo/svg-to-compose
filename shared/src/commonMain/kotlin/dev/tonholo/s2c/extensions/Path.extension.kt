package dev.tonholo.s2c.extensions

import okio.Path

val Path.extension: String
    get() = name.substring(name.lastIndexOf("."), name.length)
