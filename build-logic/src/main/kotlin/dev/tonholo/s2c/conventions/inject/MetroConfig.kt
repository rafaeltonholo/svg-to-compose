package dev.tonholo.s2c.conventions.inject

import dev.zacsweers.metro.gradle.DiagnosticSeverity
import dev.zacsweers.metro.gradle.MetroPluginExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.configureMetro() {
    configure<MetroPluginExtension> {
        debug.set(
            project.providers.environmentVariable("METRO_DEBUG")
                .map { it.toBoolean() }
                .orElse(false),
        )
        nonPublicContributionSeverity.set(DiagnosticSeverity.ERROR)
    }
}
