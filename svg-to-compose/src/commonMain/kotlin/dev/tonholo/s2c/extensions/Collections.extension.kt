package dev.tonholo.s2c.extensions

inline fun <reified T> Iterable<*>.firstInstanceOfOrNull(): T? =
    firstOrNull { it is T } as? T

inline fun <reified T> Iterable<*>.firstInstanceOf(): T =
    firstOrNull { it is T } as? T ?: throw NoSuchElementException("No element of type ${T::class.simpleName} found.")
