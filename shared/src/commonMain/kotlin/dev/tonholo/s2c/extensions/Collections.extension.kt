package dev.tonholo.s2c.extensions

inline fun <reified T> Iterable<*>.firstInstanceOfOrNull(): T? =
    firstOrNull { it is T } as? T
