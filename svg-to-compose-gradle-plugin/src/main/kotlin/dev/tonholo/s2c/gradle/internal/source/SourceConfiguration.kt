package dev.tonholo.s2c.gradle.internal.source

import org.gradle.api.Named
import org.gradle.api.file.Directory

interface SourceConfiguration : Named {
    var origin: Directory
    var destinationPackage: String
    var recursive: Boolean
    var maxDepth: Int
}
