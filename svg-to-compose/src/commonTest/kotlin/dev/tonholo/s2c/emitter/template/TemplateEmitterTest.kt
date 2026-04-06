package dev.tonholo.s2c.emitter.template

import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.compose.ComposeBrush
import dev.tonholo.s2c.domain.defaultImports
import dev.tonholo.s2c.emitter.FormatConfig
import dev.tonholo.s2c.emitter.imagevector.ImageVectorEmitter
import dev.tonholo.s2c.emitter.template.config.ColorMappingDefinition
import dev.tonholo.s2c.emitter.template.config.PreviewConfig
import dev.tonholo.s2c.emitter.template.config.ReceiverDefinition
import dev.tonholo.s2c.emitter.template.config.TemplateDefinitions
import dev.tonholo.s2c.emitter.template.config.TemplateEmitterConfig
import dev.tonholo.s2c.emitter.template.config.TemplateSection
import dev.tonholo.s2c.emitter.template.resolver.PlaceholderResolver
import dev.tonholo.s2c.logger.Logger
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TemplateEmitterTest {
    private val noOpLogger = object : Logger {
        override fun debug(message: Any) = Unit
        override fun <T> debugSection(title: String, block: () -> T): T = block()
        override fun <T> verboseSection(title: String, block: () -> T): T = block()
        override fun verbose(message: String) = Unit
        override fun warn(message: String, throwable: Throwable?) = Unit
        override fun info(message: String) = Unit
        override fun output(message: String) = Unit
        override fun error(message: String, throwable: Throwable?) = Unit
        override fun printEmpty() = Unit
    }

    private val formatConfig = FormatConfig()

    @Test
    fun `delegates to fallback when no icon template`() {
        val config = TemplateEmitterConfig()
        val fallback = ImageVectorEmitter(noOpLogger, formatConfig)
        val emitter = TemplateEmitter(noOpLogger, formatConfig, config, fallback)

        val output = emitter.emit(createSimpleIcon())
        // Should produce default ImageVector.Builder pattern
        assertContains(output, "ImageVector.Builder(")
    }

    @Test
    fun `uses template when icon_template is present`() {
        val config = TemplateEmitterConfig(
            templates = TemplateSection(
                iconTemplate = $$"val ${icon:name}: ImageVector by lazy {$\n    ${icon:body}$\n}",
            ),
        )
        val fallback = ImageVectorEmitter(noOpLogger, formatConfig)
        val emitter = TemplateEmitter(noOpLogger, formatConfig, config, fallback)

        val output = emitter.emit(createSimpleIcon())
        assertContains(output, "by lazy {")
        assertContains(output, "TestIcon")
    }

    @Test
    fun `template with receiver definition adds import`() {
        val config = TemplateEmitterConfig(
            definitions = TemplateDefinitions(
                receiver = ReceiverDefinition(name = "Icons", packageName = "com.example.icons"),
            ),
            templates = TemplateSection(
                iconTemplate = $$"val ${icon:property_name}: ImageVector by lazy {$\n    ${icon:body}$\n}",
            ),
        )
        val fallback = ImageVectorEmitter(noOpLogger, formatConfig)
        val emitter = TemplateEmitter(noOpLogger, formatConfig, config, fallback)

        val output = emitter.emit(createSimpleIcon())
        assertContains(output, "Icons.TestIcon")
        assertContains(output, "import com.example.icons.Icons")
    }

    @Test
    fun `template with def resolves simple name and registers import`() {
        val config = TemplateEmitterConfig(
            definitions = TemplateDefinitions(
                imports = mapOf(
                    "builder" to "com.example.icon",
                ),
            ),
            templates = TemplateSection(
                iconTemplate = $$"val ${icon:name}: ImageVector by lazy {$\n" +
                    $$"    ${template:icon_builder} {$\n        ${icon:body}$\n    }$\n}",
            ),
            fragments = mapOf(
                "icon_builder" to $$"${def:builder}(name = ${icon:name})",
            ),
        )
        val fallback = ImageVectorEmitter(noOpLogger, formatConfig)
        val emitter = TemplateEmitter(noOpLogger, formatConfig, config, fallback)

        val output = emitter.emit(createSimpleIcon())
        assertContains(output, "icon(name = TestIcon)")
        assertContains(output, "import com.example.icon")
    }

    @Test
    fun `preview template suppressed when empty`() {
        val config = TemplateEmitterConfig(
            templates = TemplateSection(
                iconTemplate = $$"val ${icon:name}: ImageVector by lazy {$\n    ${icon:body}$\n}",
                preview = PreviewConfig(template = ""),
            ),
        )
        val fallback = ImageVectorEmitter(noOpLogger, formatConfig)
        val emitter = TemplateEmitter(noOpLogger, formatConfig, config, fallback)

        val output = emitter.emit(createSimpleIcon(noPreview = false))
        assertFalse(output.contains("@Preview"))
    }

    @Test
    fun `default preview delegated from fallback when no preview template config`() {
        val config = TemplateEmitterConfig(
            templates = TemplateSection(
                iconTemplate = $$"val ${icon:name}: ImageVector by lazy {$\n    ${icon:body}$\n}",
            ),
        )
        val fallback = ImageVectorEmitter(noOpLogger, formatConfig)
        val emitter = TemplateEmitter(noOpLogger, formatConfig, config, fallback)

        val output = emitter.emit(createSimpleIcon(noPreview = false))
        assertContains(output, "@Preview")
        assertContains(output, "IconPreview")
        assertContains(output, "Image(")
    }

    @Test
    fun `custom preview template is used`() {
        val config = TemplateEmitterConfig(
            templates = TemplateSection(
                iconTemplate = $$"val ${icon:name}: ImageVector by lazy {$\n    ${icon:body}$\n}",
                preview = PreviewConfig(
                    template = $$"@Preview$\n@Composable$\nprivate fun ${icon:name}Preview() {}",
                ),
            ),
        )
        val fallback = ImageVectorEmitter(noOpLogger, formatConfig)
        val emitter = TemplateEmitter(noOpLogger, formatConfig, config, fallback)

        val output = emitter.emit(createSimpleIcon(noPreview = false))
        assertContains(output, "TestIconPreview")
    }

    @Test
    fun `visibility variable resolves to internal when makeInternal is true`() {
        val config = TemplateEmitterConfig(
            templates = TemplateSection(
                iconTemplate = $$"${icon:visibility} val ${icon:name}: ImageVector by lazy {$\n    ${icon:body}$\n}",
            ),
        )
        val fallback = ImageVectorEmitter(noOpLogger, formatConfig)
        val emitter = TemplateEmitter(noOpLogger, formatConfig, config, fallback)

        val output = emitter.emit(createSimpleIcon(makeInternal = true))
        assertContains(output, "internal val TestIcon")
    }

    @Test
    fun `visibility variable resolves to empty and leading space is trimmed`() {
        val config = TemplateEmitterConfig(
            templates = TemplateSection(
                iconTemplate = $$"${icon:visibility} val ${icon:name}: ImageVector by lazy {$\n    ${icon:body}$\n}",
            ),
        )
        val fallback = ImageVectorEmitter(noOpLogger, formatConfig)
        val emitter = TemplateEmitter(noOpLogger, formatConfig, config, fallback)

        val output = emitter.emit(createSimpleIcon(makeInternal = false))
        assertContains(output, "val TestIcon: ImageVector by lazy")
        assertFalse(output.contains("internal val"))
    }

    @Test
    fun `file header is emitted before package statement`() {
        val config = TemplateEmitterConfig(
            templates = TemplateSection(
                fileHeader = "// Copyright 2026\n@file:Suppress(\"ktlint\")",
                iconTemplate = $$"val ${icon:name}: ImageVector by lazy {$\n    ${icon:body}$\n}",
            ),
        )
        val fallback = ImageVectorEmitter(noOpLogger, formatConfig)
        val emitter = TemplateEmitter(noOpLogger, formatConfig, config, fallback)

        val output = emitter.emit(createSimpleIcon())
        assertTrue(output.startsWith("// Copyright 2026"))
        val headerEnd = output.indexOf("@file:Suppress(\"ktlint\")")
        val packageStart = output.indexOf("package dev.test")
        assertTrue(headerEnd < packageStart, "File header should appear before package statement")
    }

    @Test
    fun `file header supports placeholders`() {
        val config = TemplateEmitterConfig(
            templates = TemplateSection(
                fileHeader = $$"// Generated icon: ${icon:name}",
                iconTemplate = $$"val ${icon:name}: ImageVector by lazy {$\n    ${icon:body}$\n}",
            ),
        )
        val fallback = ImageVectorEmitter(noOpLogger, formatConfig)
        val emitter = TemplateEmitter(noOpLogger, formatConfig, config, fallback)

        val output = emitter.emit(createSimpleIcon())
        assertContains(output, "// Generated icon: TestIcon")
    }

    @Test
    fun `path_builder fragment is used for path nodes`() {
        val config = TemplateEmitterConfig(
            definitions = TemplateDefinitions(
                imports = mapOf(
                    "icon_path" to "com.example.iconPath",
                ),
            ),
            templates = TemplateSection(
                iconTemplate = $$"val ${icon:name}: ImageVector by lazy {$\n    ${icon:body}$\n}",
            ),
            fragments = mapOf(
                "path_builder" to $$"${def:icon_path}($\n    fill = ${path:fill},$\n)",
            ),
        )
        val fallback = ImageVectorEmitter(noOpLogger, formatConfig)
        val emitter = TemplateEmitter(noOpLogger, formatConfig, config, fallback)

        val output = emitter.emit(createSimpleIcon())
        assertContains(output, "iconPath(")
        assertContains(output, "import com.example.iconPath")
    }

    @Test
    fun `color mapping replaces Color expression with named constant`() {
        val config = TemplateEmitterConfig(
            definitions = TemplateDefinitions(
                imports = mapOf(
                    "icon_path" to "com.example.iconPath",
                ),
                colorMapping = listOf(
                    ColorMappingDefinition(
                        name = "BLACK",
                        importPackage = "com.example.colors",
                        value = "0xFF121212",
                    ),
                ),
            ),
            templates = TemplateSection(
                iconTemplate = $$"val ${icon:name}: ImageVector by lazy {$\n    ${icon:body}$\n}",
            ),
            fragments = mapOf(
                "path_builder" to $$"${def:icon_path}(fill = ${path:fill})",
            ),
        )
        val fallback = ImageVectorEmitter(noOpLogger, formatConfig)
        val emitter = TemplateEmitter(noOpLogger, formatConfig, config, fallback)

        val output = emitter.emit(createSimpleIcon(fillColor = "#121212"))
        assertContains(output, "SolidColor(BLACK)")
        assertContains(output, "import com.example.colors.BLACK")
        assertFalse(output.contains("Color(0xFF121212)"))
    }

    @Test
    fun `chunk_function_name fragment customizes chunk function names`() {
        // Verify the fragment resolution works by testing PlaceholderResolver directly
        // with the same context TemplateEmitter would create for chunk naming
        val config = TemplateEmitterConfig(
            fragments = mapOf(
                "chunk_function_name" to $$"${icon:name}Part${chunk:index}",
            ),
        )
        val context = TemplateContext(
            iconVariables = mapOf(
                "name" to "testIcon",
            ),
            chunkVariables = mapOf(
                "index" to "3",
            ),
            definitions = config.definitions.imports,
            fragments = config.fragments,
        )
        val fragment = requireNotNull(config.fragments["chunk_function_name"]) {
            "chunk_function_name fragment must be present in config"
        }
        val result = PlaceholderResolver.resolve(fragment, context)
        assertEquals("testIconPart3", result)
    }

    @Test
    fun `emitter produces package and imports`() {
        val config = TemplateEmitterConfig(
            templates = TemplateSection(
                iconTemplate = $$"val ${icon:name}: ImageVector by lazy {$\n    ${icon:body}$\n}",
            ),
        )
        val fallback = ImageVectorEmitter(noOpLogger, formatConfig)
        val emitter = TemplateEmitter(noOpLogger, formatConfig, config, fallback)

        val output = emitter.emit(createSimpleIcon())
        assertTrue(output.startsWith("package dev.test"))
        assertContains(output, "import androidx.compose.ui.graphics.vector.ImageVector")
    }

    private fun createSimpleIcon(
        noPreview: Boolean = true,
        makeInternal: Boolean = false,
        fillColor: String = "Color.Black",
    ): IconFileContents {
        val pathNodes = listOf(
            PathNodes.MoveTo(values = listOf("10", "20"), isRelative = false, minified = false),
            PathNodes.LineTo(values = listOf("30", "40"), isRelative = false, minified = false),
        )
        val wrapper = ImageVectorNode.NodeWrapper(normalizedPath = "M10 20 L30 40", nodes = pathNodes)
        val path = ImageVectorNode.Path(
            params = ImageVectorNode.Path.Params(fill = ComposeBrush.SolidColor(fillColor)),
            wrapper = wrapper,
            minified = false,
        )
        return IconFileContents(
            pkg = "dev.test",
            iconName = "test-icon",
            theme = "TestTheme",
            width = 24f,
            height = 24f,
            nodes = listOf(path),
            noPreview = noPreview,
            makeInternal = makeInternal,
            imports = defaultImports + "androidx.compose.ui.graphics.vector.path",
        )
    }
}
