package dev.tonholo.s2c.conventions

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.the

// Workaround for using version catalog in Kotlin script convention plugins
// https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
internal val Project.libs: LibrariesForLibs get() = extensions.getByType()
