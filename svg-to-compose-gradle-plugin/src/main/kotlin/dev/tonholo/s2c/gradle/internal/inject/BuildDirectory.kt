package dev.tonholo.s2c.gradle.internal.inject

import dev.zacsweers.metro.Qualifier

/**
 * Qualifier annotation used to distinguish the Gradle build directory
 * [DirectoryProperty][org.gradle.api.file.DirectoryProperty] dependency
 * in the [GradlePluginGraph].
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY)
internal annotation class BuildDirectory
