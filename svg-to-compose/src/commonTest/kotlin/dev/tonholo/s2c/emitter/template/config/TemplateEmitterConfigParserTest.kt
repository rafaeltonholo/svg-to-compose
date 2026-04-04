package dev.tonholo.s2c.emitter.template.config

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TemplateEmitterConfigParserTest {
    @Test
    fun `parses valid TOML with all sections`() {
        val toml = $$"""
            [definitions]
            receiver = { name = "Icons", package = "com.example.icons" }

            [definitions.imports]
            icon_builder = "com.example.icons.icon"
            icon_path = "com.example.icons.iconPath"

            [templates]
            icon_template = "val ${icon:name}: ImageVector"

            [fragments]
            icon_builder = "${def:icon_builder}(name = ${icon:name})"
            path_builder = "${def:icon_path}(fill = ${path:fill})"
        """.trimIndent()

        val config = TemplateConfigParser.parse(toml)

        val receiver = assertNotNull(config.definitions.receiver)
        assertEquals("Icons", receiver.name)
        assertEquals("com.example.icons", receiver.packageName)
        assertEquals(2, config.definitions.imports.size)
        assertEquals("com.example.icons.icon", config.definitions.imports["icon_builder"])
        assertEquals("icon", config.definitions.imports["icon_builder"]?.substringAfterLast('.'))
        assertNotNull(config.templates.iconTemplate)
        assertEquals(2, config.fragments.size)
    }

    @Test
    fun `parses minimal TOML with only definitions`() {
        val toml = """
            [definitions]
            receiver = { name = "MyIcons", package = "com.example" }
        """.trimIndent()

        val config = TemplateConfigParser.parse(toml)

        assertNotNull(config.definitions.receiver)
        assertNull(config.templates.iconTemplate)
        assertTrue(config.fragments.isEmpty())
    }

    @Test
    fun `parses TOML with missing optional sections`() {
        val toml = """
            [definitions]

            [templates]
        """.trimIndent()

        val config = TemplateConfigParser.parse(toml)

        assertNull(config.definitions.receiver)
        assertTrue(config.definitions.imports.isEmpty())
        assertNull(config.templates.iconTemplate)
    }

    @Test
    fun `rejects undefined def reference`() {
        val toml = $$"""
            [definitions]

            [templates]
            icon_template = "${def:undefined_key}"
        """.trimIndent()

        val error = assertFailsWith<IllegalArgumentException> {
            TemplateConfigParser.parse(toml)
        }
        val message = requireNotNull(error.message) { "Expected error message to be non-null" }
        assertTrue(message.contains("undefined_key"))
    }

    @Test
    fun `parses preview config`() {
        val toml = """
            [definitions]

            [templates]

            [templates.preview]
            template = "@Preview fun Preview() {}"
        """.trimIndent()

        val config = TemplateConfigParser.parse(toml)
        assertNotNull(config.templates.preview)
        assertEquals("@Preview fun Preview() {}", config.templates.preview.template)
    }

    @Test
    fun `parses color mapping definitions`() {
        val toml = """
            [definitions.receiver]
            name = "Icons"
            package = "com.example.icons"

            [definitions.imports]
            icon_builder = "com.example.icons.icon"

            [[definitions.color_mapping]]
            name = "BLACK"
            import = "com.example.colors"
            value = "0xFF121212"

            [[definitions.color_mapping]]
            name = "PRIMARY"
            import = "com.example.colors"
            value = "0xFF0066FF"
        """.trimIndent()

        val config = TemplateConfigParser.parse(toml)

        assertEquals(2, config.definitions.colorMapping.size)
        val black = config.definitions.colorMapping[0]
        assertEquals("BLACK", black.name)
        assertEquals("com.example.colors", black.importPackage)
        assertEquals("0xFF121212", black.value)
        val primary = config.definitions.colorMapping[1]
        assertEquals("PRIMARY", primary.name)
        assertEquals("0xFF0066FF", primary.value)
    }

    @Test
    fun `parses multiline template strings with placeholders`() {
        val toml = """
            [definitions]
            [definitions.imports]
            [templates]
            file_header = ${"\"\"\""}
            package ${"$"}{icon:package}
            ${"\"\"\""}
            icon_template = ${"\"\"\""}
            ${"$"}{icon:visibility} val ${"$"}{icon:property_name}: ImageVector by lazy {
                ${"$"}{icon:body}
            }
            ${"\"\"\""}
            [templates.preview]
            [fragments]
        """.trimIndent()

        val config = TemplateConfigParser.parse(toml)
        val fileHeader = requireNotNull(config.templates.fileHeader)
        val iconTemplate = requireNotNull(config.templates.iconTemplate)
        assertTrue(fileHeader.contains("icon:package"))
        assertTrue(iconTemplate.contains("icon:body"))
    }

    @Test
    fun `strips empty color_mapping array-of-tables before parsing`() {
        val toml = """
            [definitions]
            [definitions.imports]
            [[definitions.color_mapping]]
            # comment only
            [templates]
            icon_template = "val Icon: ImageVector"
            [templates.preview]
            [fragments]
        """.trimIndent()

        val config = TemplateConfigParser.parse(toml)
        assertTrue(config.definitions.colorMapping.isEmpty())
        assertNotNull(config.templates.iconTemplate)
    }

    @Test
    fun `parses playground default scaffold with user-added templates`() {
        val toml = buildString {
            appendLine("[definitions]")
            appendLine()
            appendLine("# Insert here your custom definitions")
            appendLine()
            appendLine("[definitions.imports]")
            appendLine()
            appendLine("# Insert here your custom imports")
            appendLine()
            appendLine("[[definitions.color_mapping]]")
            appendLine()
            appendLine("# Use [[definitions.color_mapping]] to define each entry of your custom colour mapping")
            appendLine()
            appendLine("[templates]")
            appendLine("file_header = \"\"\"")
            appendLine("package \${icon:package}")
            appendLine("\"\"\"")
            appendLine()
            appendLine("icon_template = \"\"\"")
            appendLine("\${icon:visibility} val \${icon:property_name}: ImageVector by lazy {")
            appendLine("    \${template:icon_builder} {")
            appendLine("        \${icon:body}")
            appendLine("    }")
            appendLine("}")
            appendLine("\"\"\"")
            appendLine("# Insert here your custom templates")
            appendLine()
            appendLine("[templates.preview]")
            appendLine()
            appendLine("# Set here your desired preview template")
            appendLine()
            appendLine("[fragments]")
            appendLine()
            appendLine("# Insert here your custom fragments")
        }

        val config = TemplateConfigParser.parse(toml)
        assertTrue(config.definitions.colorMapping.isEmpty())
        assertNotNull(config.templates.fileHeader)
        assertNotNull(config.templates.iconTemplate)
        assertTrue(config.fragments.isEmpty())
    }

    @Test
    fun `empty preview template suppresses preview`() {
        val toml = """
            [definitions]

            [templates]

            [templates.preview]
            template = ""
        """.trimIndent()

        val config = TemplateConfigParser.parse(toml)
        assertNotNull(config.templates.preview)
        assertEquals("", config.templates.preview.template)
    }
}
