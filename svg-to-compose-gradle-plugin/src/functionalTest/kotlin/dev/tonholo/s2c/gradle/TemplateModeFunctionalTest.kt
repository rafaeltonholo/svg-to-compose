package dev.tonholo.s2c.gradle

import dev.tonholo.s2c.gradle.common.GradleFunctionalTest
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Functional tests covering how the plugin resolves the template that shapes the
 * generated code: auto-discovery when nothing is configured, and no template at
 * all when the DSL turns it off.
 */
class TemplateModeFunctionalTest : GradleFunctionalTest() {

    override val tempDirPrefix: String = "s2c-template-mode-test"

    companion object {
        private const val TASK_NAME = "parseSvgToComposeIcon"
        private const val TASK_PATH = ":$TASK_NAME"
        private const val PACKAGE = "dev.tonholo.s2c.test.template"
        private const val TEMPLATE_MARKER = "TemplateDiscoveryMarker"
        private const val EDITED_TEMPLATE_MARKER = "EditedTemplateDiscoveryMarker"

        // language=svg
        private val SIMPLE_SVG = """
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
              <circle cx="12" cy="12" r="10" fill="black"/>
            </svg>
        """.trimIndent()

        private val TEMPLATE_TOML = """
            [templates]
            icon_template = "val ${'$'}{icon:property_name}: ImageVector = $TEMPLATE_MARKER()"
        """.trimIndent()
    }

    private fun setupBuildGradle(iconOptions: String = "") {
        projectDir.resolve("build.gradle.kts").writeText(
            // language=kotlin
            """
            plugins {
                kotlin("multiplatform")
                id("dev.tonholo.s2c")
            }
            repositories {
                mavenCentral()
            }
            kotlin {
                jvm()
            }
            svgToCompose {
                processor {
                    val icons by creating {
                        from(layout.projectDirectory.dir("icons"))
                        destinationPackage("$PACKAGE")
                        icons {
                            noPreview()
                            $iconOptions
                        }
                    }
                }
            }
            """.trimIndent(),
        )
    }

    private fun writeDiscoverableTemplate() {
        projectDir.resolve("s2c.template.toml").writeText(TEMPLATE_TOML)
    }

    private fun readGeneratedIcon(): String {
        val generatedDir = generatedBuildDir(PACKAGE)
        assertTrue(generatedDir.exists(), "Generated directory does not exist: $generatedDir")
        val generated = generatedDir.walkTopDown().firstOrNull { it.isFile && it.extension == "kt" }
        assertNotNull(generated, "No generated .kt file found in $generatedDir")
        return generated.readText()
    }

    @Test
    fun `given a discoverable template - when no template is configured - then the template shapes the output`() {
        // Arrange
        setupBuildGradle()
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG)
        writeDiscoverableTemplate()

        // Act
        val result = runGradle(TASK_NAME)

        // Assert
        assertTaskOutcome(result, TASK_PATH, TaskOutcome.SUCCESS)
        assertTrue(
            readGeneratedIcon().contains(TEMPLATE_MARKER),
            "Expected the discovered template to shape the generated icon",
        )
    }

    @Test
    fun `given a discovered template - when the template is edited - then the task runs again`() {
        // Arrange
        setupBuildGradle()
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG)
        writeDiscoverableTemplate()
        runGradle(TASK_NAME)

        // Act
        val editedTemplate = TEMPLATE_TOML.replace(TEMPLATE_MARKER, EDITED_TEMPLATE_MARKER)
        projectDir.resolve("s2c.template.toml").writeText(editedTemplate)
        val result = runGradle(TASK_NAME)

        // Assert
        assertTaskOutcome(result, TASK_PATH, TaskOutcome.SUCCESS)
    }

    @Test
    fun `given a discoverable template - when noTemplate is configured - then the template is ignored`() {
        // Arrange
        setupBuildGradle(iconOptions = "noTemplate()")
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG)
        writeDiscoverableTemplate()

        // Act
        val result = runGradle(TASK_NAME)

        // Assert
        assertTaskOutcome(result, TASK_PATH, TaskOutcome.SUCCESS)
        assertFalse(
            readGeneratedIcon().contains(TEMPLATE_MARKER),
            "Expected the template to be ignored when noTemplate() is configured",
        )
    }
}
