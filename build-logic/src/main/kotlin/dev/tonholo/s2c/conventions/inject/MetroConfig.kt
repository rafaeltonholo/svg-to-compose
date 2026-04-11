package dev.tonholo.s2c.conventions.inject

import dev.zacsweers.metro.gradle.DelicateMetroGradleApi
import dev.zacsweers.metro.gradle.DiagnosticSeverity
import dev.zacsweers.metro.gradle.ExperimentalMetroGradleApi
import dev.zacsweers.metro.gradle.MetroPluginExtension
import dev.zacsweers.metro.gradle.RequiresIdeSupport
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

@OptIn(DelicateMetroGradleApi::class, RequiresIdeSupport::class, ExperimentalMetroGradleApi::class)
fun Project.configureMetro() {
    configure<MetroPluginExtension> {
        debug.set(
            project.providers.environmentVariable("METRO_DEBUG")
                .map { it.toBoolean() }
                .orElse(false),
        )
        nonPublicContributionSeverity.set(DiagnosticSeverity.ERROR)

        // Top-level function injection is incompatible with Kotlin/JS
        // incremental compilation and no module in this project uses it.
        enableTopLevelFunctionInjection.set(false)

        // Restrict FIR contribution hint generation to platforms that
        // support it with incremental compilation. JS and WASM are
        // excluded because they do not support generating top-level
        // declarations from compiler plugins with IC enabled
        // (KT-82395, KT-82989).
        supportedHintContributionPlatforms.set(
            setOf(
                KotlinPlatformType.jvm,
                KotlinPlatformType.androidJvm,
                KotlinPlatformType.native,
            ),
        )
    }
}
