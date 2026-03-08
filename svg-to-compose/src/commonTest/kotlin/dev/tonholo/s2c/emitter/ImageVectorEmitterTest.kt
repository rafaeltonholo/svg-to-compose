package dev.tonholo.s2c.emitter

import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.defaultImports
import dev.tonholo.s2c.domain.compose.ComposeBrush
import dev.tonholo.s2c.emitter.imagevector.ImageVectorEmitter
import dev.tonholo.s2c.logger.Logger
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Regression tests verifying that [ImageVectorEmitter] produces
 * byte-identical output to the old `IconFileContents.materialize()`.
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
    }

    private val emitter = ImageVectorEmitter(noOpLogger)

    @Test
    fun `simple icon with single path produces identical output to materialize`() {
        val contents = createSimpleIconContents()
        val materializeOutput = contents.materialize()
        val emitterOutput = emitter.emit(contents)
        assertEquals(materializeOutput, emitterOutput)
    }

    @Test
    fun `icon with no preview produces identical output`() {
        val contents = createSimpleIconContents(noPreview = true)
        val materializeOutput = contents.materialize()
        val emitterOutput = emitter.emit(contents)
        assertEquals(materializeOutput, emitterOutput)
    }

    @Test
    fun `icon with internal visibility produces identical output`() {
        val contents = createSimpleIconContents(makeInternal = true)
        val materializeOutput = contents.materialize()
        val emitterOutput = emitter.emit(contents)
        assertEquals(materializeOutput, emitterOutput)
    }

    @Test
    fun `icon with receiver type produces identical output`() {
        val contents = createSimpleIconContents(receiverType = "MyIcons.Filled")
        val materializeOutput = contents.materialize()
        val emitterOutput = emitter.emit(contents)
        assertEquals(materializeOutput, emitterOutput)
    }

    @Test
    fun `icon with addToMaterial produces identical output`() {
        val contents = createSimpleIconContents(addToMaterial = true)
        val materializeOutput = contents.materialize()
        val emitterOutput = emitter.emit(contents)
        assertEquals(materializeOutput, emitterOutput)
    }

    @Test
    fun `icon with group node produces identical output`() {
        val contents = createGroupIconContents()
        val materializeOutput = contents.materialize()
        val emitterOutput = emitter.emit(contents)
        assertEquals(materializeOutput, emitterOutput)
    }

    @Test
    fun `minified icon produces identical output`() {
        val contents = createSimpleIconContents(minified = true)
        val materializeOutput = contents.materialize()
        val emitterOutput = emitter.emit(contents)
        assertEquals(materializeOutput, emitterOutput)
    }

    private fun createSimpleIconContents(
        noPreview: Boolean = true,
        makeInternal: Boolean = false,
        receiverType: String? = null,
        addToMaterial: Boolean = false,
        minified: Boolean = false,
    ): IconFileContents {
        val pathNodes = listOf(
            PathNodes.MoveTo(
                values = listOf("10", "20"),
                isRelative = false,
                minified = minified,
            ),
            PathNodes.LineTo(
                values = listOf("30", "40"),
                isRelative = false,
                minified = minified,
            ),
        )
        val wrapper = ImageVectorNode.NodeWrapper(
            normalizedPath = "M10 20 L30 40",
            nodes = pathNodes,
        )
        val path = ImageVectorNode.Path(
            params = ImageVectorNode.Path.Params(
                fill = ComposeBrush.SolidColor("Color.Black"),
            ),
            wrapper = wrapper,
            minified = minified,
        )

        return IconFileContents(
            pkg = "dev.test",
            iconName = "test-icon",
            theme = "TestTheme",
            width = 24f,
            height = 24f,
            viewportWidth = 24f,
            viewportHeight = 24f,
            nodes = listOf(path),
            receiverType = receiverType,
            addToMaterial = addToMaterial,
            noPreview = noPreview,
            makeInternal = makeInternal,
            imports = defaultImports + "androidx.compose.ui.graphics.vector.path",
        )
    }

    private fun createGroupIconContents(): IconFileContents {
        val pathNodes = listOf(
            PathNodes.MoveTo(
                values = listOf("5", "10"),
                isRelative = false,
                minified = false,
            ),
        )
        val wrapper = ImageVectorNode.NodeWrapper(
            normalizedPath = "M5 10",
            nodes = pathNodes,
        )
        val innerPath = ImageVectorNode.Path(
            params = ImageVectorNode.Path.Params(
                fill = ComposeBrush.SolidColor("Color.Red"),
            ),
            wrapper = wrapper,
            minified = false,
        )
        val group = ImageVectorNode.Group(
            commands = listOf(innerPath),
            minified = false,
            params = ImageVectorNode.Group.Params(
                rotate = 45f,
            ),
        )

        return IconFileContents(
            pkg = "dev.test",
            iconName = "group-icon",
            theme = "TestTheme",
            width = 24f,
            height = 24f,
            viewportWidth = 24f,
            viewportHeight = 24f,
            nodes = listOf(group),
            noPreview = true,
            imports = defaultImports +
                "androidx.compose.ui.graphics.vector.path" +
                "androidx.compose.ui.graphics.vector.group",
        )
    }
}
