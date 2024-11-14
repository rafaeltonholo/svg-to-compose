package dev.tonholo.s2c.gradle

import dev.tonholo.s2c.gradle.dsl.SvgToComposeExtension
import dev.tonholo.s2c.gradle.tasks.registerParseSvgToComposeIconTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class Svg2ComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create(
            "svgToCompose",
            SvgToComposeExtension::class.java,
        )
        target.configure(extension)
    }

    private fun Project.configure(extension: SvgToComposeExtension) {
        tasks.registerParseSvgToComposeIconTask(this, extension)
    }
}
