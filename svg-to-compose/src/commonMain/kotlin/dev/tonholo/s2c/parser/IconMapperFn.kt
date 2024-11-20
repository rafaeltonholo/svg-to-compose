package dev.tonholo.s2c.parser

/**
 * A typealias for a mapper function to change the icon name before parsing.
 */
typealias IconMapperFn = (String) -> String

/**
 * @return This [IconMapperFn] if it's not null, otherwise returns a default
 * mapper function that simply returns the input icon name.
 */
fun IconMapperFn?.orDefault(): IconMapperFn =
    this ?: { iconName -> iconName }
