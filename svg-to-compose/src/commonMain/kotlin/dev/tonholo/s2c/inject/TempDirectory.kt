package dev.tonholo.s2c.inject

import dev.zacsweers.metro.Qualifier

/**
 * Qualifier annotation used to distinguish a temporary directory Path dependency in dependency injection.
 *
 * This qualifier is used to mark a Path parameter or property that represents a temporary directory
 * location. It allows the dependency injection framework to differentiate between different Path
 * instances and inject the appropriate temporary directory path when needed.
 *
 * The temporary directory is typically used for intermediate file operations, caching, or storing
 * transient data during processing operations. When applied to a nullable Path parameter, it indicates
 * that the temporary directory is optional and may not be provided in all contexts.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY)
annotation class TempDirectory
