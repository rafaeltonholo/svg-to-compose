package dev.tonholo.s2c.gradle.internal

import dev.tonholo.s2c.AppDefaults
import dev.tonholo.s2c.gradle.internal.parser.ParserConfiguration
import dev.tonholo.s2c.gradle.internal.source.SourceConfiguration
import org.gradle.api.file.Directory
import javax.inject.Inject

abstract class ProcessorConfiguration @Inject constructor() : SourceConfiguration, ParserConfiguration {
    override lateinit var origin: Directory
    override lateinit var destinationPackage: String
    override lateinit var theme: String
    override var recursive: Boolean = AppDefaults.RECURSIVE
    override var maxDepth: Int = AppDefaults.MAX_RECURSIVE_DEPTH
    override var optimize: Boolean = AppDefaults.OPTIMIZE
    override var receiverType: String? = null
    override var addToMaterial: Boolean = AppDefaults.ADD_TO_MATERIAL_ICONS
    override var noPreview: Boolean = AppDefaults.NO_PREVIEW
    override var makeInternal: Boolean = AppDefaults.MAKE_INTERNAL
    override var minified: Boolean = AppDefaults.MINIFIED
    override var exclude: Regex? = null
    override var iconNameMapper: ((String) -> String)? = null

    override fun toString(): String {
        return "ProcessorConfiguration(origin = $origin, " +
            "destinationPackage = $destinationPackage, " +
            "recursive = $recursive, " +
            "maxDepth = $maxDepth, " +
            "theme = $theme, " +
            "optimize = $optimize, " +
            "receiverType = $receiverType, " +
            "addToMaterial = $addToMaterial, " +
            "noPreview = $noPreview, " +
            "makeInternal = $makeInternal, " +
            "minified = $minified, " +
            "exclude = $exclude)"
    }

    internal fun validate(): List<String> {
        val errors = mutableListOf<String>()

        if (::theme.isInitialized.not() || theme.isBlank()) {
            errors.add("Configuration '$name': Theme name cannot be empty.")
        }

        origin.takeIf { ::origin.isInitialized }
            ?.asFile
            ?.let { origin ->
                if (origin.exists().not()) {
                    errors.add("Configuration '$name': The specified origin should exist.")
                }

                if (origin.isDirectory.not()) {
                    errors.add("Configuration '$name': The specified origin should be a directory.")
                }
            } ?: errors.add("Configuration '$name': Origin cannot be null.")

        if (::destinationPackage.isInitialized.not() || destinationPackage.isBlank()) {
            errors.add("Configuration '$name': Destination package cannot be empty.")
        }

        return errors
    }
}
