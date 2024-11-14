package dev.tonholo.s2c.gradle.dsl.source

import dev.tonholo.s2c.gradle.dsl.parser.IconParserConfiguration
import dev.tonholo.s2c.gradle.internal.Configuration
import org.gradle.api.Action
import org.gradle.api.file.Directory

/**
 * Configuration for a source set of vectors to be converted to `ImageVector`s.
 */
internal interface SourceConfiguration : Configuration {
    /**
     * Sets the source directory for the vectors.
     * @param directory The source directory.
     */
    fun from(directory: Directory)

    /**
     * Sets the destination package for the generated `ImageVector`s.
     * @param fullPackage The full package name.
     */
    fun destinationPackage(fullPackage: String)

    /**
     * Enables recursive scanning of the source directory.
     */
    fun recursive()

    /**
     * Sets the maximum depth for recursive scanning.
     * @param depth The maximum depth.
     */
    fun maxDepth(depth: Int)

    /**
     * Enables or disables optimization of the generated `ImageVector`s.
     *
     * @param enabled True to enable optimization, false to disable.
     * default: `true`
     */
    fun optimize(enabled: Boolean = true)

    /**
     * Configures the icon parser.
     * @param configure The configuration action.
     */
    fun icons(configure: Action<IconParserConfiguration>)
}
