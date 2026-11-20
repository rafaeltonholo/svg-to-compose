package dev.tonholo.s2c.gradle.internal.parser

import dev.tonholo.s2c.emitter.template.config.TEMPLATE_FILENAME
import java.io.File

/**
 * Finds the template the processor auto-discovers for [outputDirectory]: the closest
 * template file in the directory itself or in one of its parents.
 */
internal fun findDiscoveredTemplate(outputDirectory: File): File? =
    generateSequence(outputDirectory) { it.parentFile }
        .map { it.resolve(TEMPLATE_FILENAME) }
        .firstOrNull { it.isFile }
