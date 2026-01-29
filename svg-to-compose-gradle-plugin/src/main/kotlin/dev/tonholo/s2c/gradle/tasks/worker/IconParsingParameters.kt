package dev.tonholo.s2c.gradle.tasks.worker

import org.gradle.api.provider.Property
import org.gradle.workers.WorkParameters

/**
 * Defines the parameters for the icon parsing worker.
 * This interface is used by Gradle's Worker API to pass configuration from the S2C task
 * to the isolated worker process that performs the actual SVG/XML to Compose conversion.
 */
internal interface IconParsingParameters : WorkParameters {
    val inputFilePath: Property<String>
    val outputDirPath: Property<String>
    val tempDirPath: Property<String>
    val destinationPackage: Property<String>
    val optimize: Property<Boolean>
    val minified: Property<Boolean>
    val theme: Property<String>
    val receiverType: Property<String?>
    val addToMaterial: Property<Boolean>
    val noPreview: Property<Boolean>
    val makeInternal: Property<Boolean>
    val excludePattern: Property<String?>
    val kmpPreview: Property<Boolean>
    val recursive: Property<Boolean>
    val maxDepth: Property<Int>
    val resultFilePath: Property<String>
}
