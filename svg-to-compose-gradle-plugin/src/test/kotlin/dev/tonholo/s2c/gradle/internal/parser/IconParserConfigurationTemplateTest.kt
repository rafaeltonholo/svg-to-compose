package dev.tonholo.s2c.gradle.internal.parser

import org.gradle.api.Project
import org.gradle.api.file.RegularFile
import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class IconParserConfigurationTemplateTest {
    private val project: Project = ProjectBuilder.builder().build()

    private fun createConfiguration(): IconParserConfigurationImpl = IconParserConfigurationImpl(
        objectFactory = project.objects,
        parentName = "svgToCompose.processor",
    )

    private fun templateFile(name: String): RegularFile = project.layout.projectDirectory.file(name)

    @Test
    fun `given no template configured - when merged without common - then mode is auto`() {
        // Arrange
        val configuration = createConfiguration()

        // Act
        configuration.merge(common = null)

        // Assert
        assertEquals(TemplateMode.Auto, configuration.templateMode.get())
        assertFalse(configuration.templateFile.isPresent)
    }

    @Test
    fun `given template file configured - when merged without common - then mode is explicit file`() {
        // Arrange
        val configuration = createConfiguration()
        val file = templateFile("s2c.template.toml")
        configuration.templateFile(file)

        // Act
        configuration.merge(common = null)

        // Assert
        assertEquals(TemplateMode.ExplicitFile, configuration.templateMode.get())
        assertEquals(file, configuration.templateFile.get())
    }

    @Test
    fun `given noTemplate called - when merged without common - then mode is disabled`() {
        // Arrange
        val configuration = createConfiguration()
        configuration.noTemplate()

        // Act
        configuration.merge(common = null)

        // Assert
        assertEquals(TemplateMode.Disabled, configuration.templateMode.get())
        assertFalse(configuration.templateFile.isPresent)
    }

    @Test
    fun `given common with template file - when child has no template configured - then child inherits the file`() {
        // Arrange
        val common = createConfiguration()
        val file = templateFile("common.s2c.template.toml")
        common.templateFile(file)
        val configuration = createConfiguration()

        // Act
        configuration.merge(common = common)

        // Assert
        assertEquals(TemplateMode.ExplicitFile, configuration.templateMode.get())
        assertEquals(file, configuration.templateFile.get())
    }

    @Test
    fun `given common with noTemplate - when child has no template configured - then child inherits disabled`() {
        // Arrange
        val common = createConfiguration()
        common.noTemplate()
        val configuration = createConfiguration()

        // Act
        configuration.merge(common = common)

        // Assert
        assertEquals(TemplateMode.Disabled, configuration.templateMode.get())
        assertFalse(configuration.templateFile.isPresent)
    }

    @Test
    fun `given common with template file - when child calls noTemplate - then child stays disabled`() {
        // Arrange
        val common = createConfiguration()
        common.templateFile(templateFile("common.s2c.template.toml"))
        val configuration = createConfiguration()
        configuration.noTemplate()

        // Act
        configuration.merge(common = common)

        // Assert
        assertEquals(TemplateMode.Disabled, configuration.templateMode.get())
        assertFalse(configuration.templateFile.isPresent)
    }

    @Test
    fun `given common with noTemplate - when child sets a template file - then child keeps its own file`() {
        // Arrange
        val common = createConfiguration()
        common.noTemplate()
        val configuration = createConfiguration()
        val file = templateFile("child.s2c.template.toml")
        configuration.templateFile(file)

        // Act
        configuration.merge(common = common)

        // Assert
        assertEquals(TemplateMode.ExplicitFile, configuration.templateMode.get())
        assertEquals(file, configuration.templateFile.get())
    }

    @Test
    fun `given template file configured - when noTemplate is called afterwards - then the file is cleared`() {
        // Arrange
        val configuration = createConfiguration()
        configuration.templateFile(templateFile("s2c.template.toml"))

        // Act
        configuration.noTemplate()

        // Assert
        assertEquals(TemplateMode.Disabled, configuration.templateMode.get())
        assertFalse(configuration.templateFile.isPresent)
    }
}
