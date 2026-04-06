package dev.tonholo.s2c.emitter

import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.compose.ComposeBrush
import dev.tonholo.s2c.domain.defaultImports
import dev.tonholo.s2c.emitter.imagevector.ImageVectorEmitter
import dev.tonholo.s2c.logger.Logger
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Tests for [ImageVectorEmitter] code generation.
 */
class ImageVectorEmitterTest {
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

    private val emitter = ImageVectorEmitter(noOpLogger)

    @Test
    fun `emits package declaration`() {
        val output = emitter.emit(createSimpleIcon())
        assertContains(output, "package dev.test")
    }

    @Test
    fun `emits sorted imports`() {
        val output = emitter.emit(createSimpleIcon())
        val imageVectorImport = "import androidx.compose.ui.graphics.vector.ImageVector"
        val dpImport = "import androidx.compose.ui.unit.dp"
        val imageVectorIndex = output.indexOf(imageVectorImport)
        val dpIndex = output.indexOf(dpImport)
        assertTrue(imageVectorIndex >= 0, "Expected output to contain '$imageVectorImport'")
        assertTrue(dpIndex >= 0, "Expected output to contain '$dpImport'")
        assertTrue(
            imageVectorIndex < dpIndex,
            "Expected '$imageVectorImport' to appear before '$dpImport' (imports should be sorted)",
        )
    }

    @Test
    fun `emits ImageVector Builder pattern`() {
        val output = emitter.emit(createSimpleIcon())
        assertContains(output, "ImageVector.Builder(")
        assertContains(output, "name = \"TestTheme.TestIcon\"")
        assertContains(output, "defaultWidth = 24.0.dp")
        assertContains(output, "defaultHeight = 24.0.dp")
        assertContains(output, "viewportWidth = 24.0f")
        assertContains(output, "viewportHeight = 24.0f")
    }

    @Test
    fun `emits path nodes`() {
        val output = emitter.emit(createSimpleIcon())
        assertContains(output, "path(")
        // SolidColor wraps the color value
        assertTrue(output.contains("fill = SolidColor(") || output.contains("fill = Color"), output)
        assertContains(output, "moveTo(")
        assertContains(output, "lineTo(")
    }

    @Test
    fun `emits caching backing field`() {
        val output = emitter.emit(createSimpleIcon())
        assertContains(output, "private var _testIcon: ImageVector? = null")
    }

    @Test
    fun `emits internal visibility modifier`() {
        val output = emitter.emit(createSimpleIcon(makeInternal = true))
        assertContains(output, "internal val TestIcon: ImageVector")
    }

    @Test
    fun `does not emit preview when noPreview is true`() {
        val output = emitter.emit(createSimpleIcon(noPreview = true))
        assertFalse(output.contains("@Preview"))
        assertFalse(output.contains("IconPreview"))
    }

    @Test
    fun `emits preview when noPreview is false`() {
        val output = emitter.emit(createSimpleIcon(noPreview = false))
        assertContains(output, "@Preview")
        assertContains(output, "IconPreview")
    }

    @Test
    fun `emits receiver type in property name`() {
        val output = emitter.emit(createSimpleIcon(receiverType = "MyIcons.Filled"))
        assertContains(output, "val MyIcons.Filled.TestIcon: ImageVector")
    }

    @Test
    fun `emits material icons property name when addToMaterial`() {
        val output = emitter.emit(createSimpleIcon(addToMaterial = true))
        assertContains(output, "val Icons.Filled.TestIcon: ImageVector")
    }

    @Test
    fun `emits group node`() {
        val output = emitter.emit(createGroupIcon())
        assertContains(output, "group(")
        assertContains(output, "rotate = 45.0f")
    }

    @Test
    fun `minified output omits path comments`() {
        val output = emitter.emit(createSimpleIcon(minified = true))
        assertFalse(output.contains("// M"))
    }

    @Test
    fun `emitter produces consistent output across calls`() {
        val icon = createSimpleIcon()
        val first = emitter.emit(icon)
        val second = emitter.emit(icon)
        assertTrue(first == second, "Emitter should produce identical output for the same input")
    }

    private fun createSimpleIcon(
        noPreview: Boolean = false,
        makeInternal: Boolean = false,
        receiverType: String? = null,
        addToMaterial: Boolean = false,
        minified: Boolean = false,
    ): IconFileContents {
        val pathNodes = listOf(
            PathNodes.MoveTo(values = listOf("10", "20"), isRelative = false, minified = minified),
            PathNodes.LineTo(values = listOf("30", "40"), isRelative = false, minified = minified),
        )
        val wrapper = ImageVectorNode.NodeWrapper(normalizedPath = "M10 20 L30 40", nodes = pathNodes)
        val path = ImageVectorNode.Path(
            params = ImageVectorNode.Path.Params(fill = ComposeBrush.SolidColor("Color.Black")),
            wrapper = wrapper,
            minified = minified,
        )
        return IconFileContents(
            pkg = "dev.test",
            iconName = "test-icon",
            theme = "TestTheme",
            width = 24f,
            height = 24f,
            nodes = listOf(path),
            receiverType = receiverType,
            addToMaterial = addToMaterial,
            noPreview = noPreview,
            makeInternal = makeInternal,
            imports = defaultImports + "androidx.compose.ui.graphics.vector.path",
        )
    }

    private fun createGroupIcon(): IconFileContents {
        val pathNodes = listOf(
            PathNodes.MoveTo(values = listOf("5", "10"), isRelative = false, minified = false),
        )
        val wrapper = ImageVectorNode.NodeWrapper(normalizedPath = "M5 10", nodes = pathNodes)
        val innerPath = ImageVectorNode.Path(
            params = ImageVectorNode.Path.Params(fill = ComposeBrush.SolidColor("Color.Red")),
            wrapper = wrapper,
            minified = false,
        )
        val group = ImageVectorNode.Group(
            commands = listOf(innerPath),
            minified = false,
            params = ImageVectorNode.Group.Params(rotate = 45f),
        )
        return IconFileContents(
            pkg = "dev.test",
            iconName = "group-icon",
            theme = "TestTheme",
            width = 24f,
            height = 24f,
            nodes = listOf(group),
            noPreview = true,
            imports = defaultImports + setOf(
                "androidx.compose.ui.graphics.vector.path",
                "androidx.compose.ui.graphics.vector.group",
            ),
        )
    }
}
