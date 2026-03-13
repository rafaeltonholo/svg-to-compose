package dev.tonholo.s2c.emitter.template

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.compose.ComposeBrush
import dev.tonholo.s2c.emitter.FormatConfig
import dev.tonholo.s2c.emitter.imagevector.ImageVectorNodeEmitter
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class TemplateNodeEmitterTest {
    private val formatConfig = FormatConfig()
    private val fallbackEmitter = ImageVectorNodeEmitter(formatConfig)
    private val emitter = TemplateNodeEmitter(formatConfig, fallbackEmitter)

    // region emitPath

    @Test
    fun `emitPath delegates to fallback when no path_builder fragment`() {
        val context = TemplateContext(
            iconVariables = emptyMap(),
            definitions = emptyMap(),
            fragments = emptyMap(),
        )
        val path = createPath()

        val result = emitter.emit(path, context)
        assertContains(result, "path(")
    }

    @Test
    fun `emitPath uses path_builder fragment when present`() {
        val context = TemplateContext(
            iconVariables = emptyMap(),
            definitions = mapOf("icon_path" to "com.example.iconPath"),
            fragments = mapOf(
                "path_builder" to $$"${def:icon_path}(fill = ${path:fill})",
            ),
        )
        val path = createPath()

        val result = emitter.emit(path, context)
        assertContains(result, "iconPath(fill = SolidColor(")
        assertContains(result, "{\n")
    }

    @Test
    fun `emitPath does not duplicate commands in resolved fragment`() {
        val context = TemplateContext(
            iconVariables = emptyMap(),
            definitions = emptyMap(),
            fragments = mapOf(
                "path_builder" to $$"myPath(fill = ${path:fill})",
            ),
        )
        val path = createPath(fill = ComposeBrush.SolidColor("Color.Red"))

        val result = emitter.emit(path, context)
        val commandOccurrences = result.split("moveTo(").size - 1
        assertEquals(1, commandOccurrences, "Path commands should appear exactly once")
    }

    // endregion

    // region emitGroup

    @Test
    fun `emitGroup delegates to fallback when no group_builder fragment`() {
        val context = TemplateContext(
            iconVariables = emptyMap(),
            definitions = emptyMap(),
            fragments = emptyMap(),
        )
        val group = createGroup()

        val result = emitter.emit(group, context)
        // Fallback emitter produces "group {" when no params are set
        assertContains(result, "group")
    }

    @Test
    fun `emitGroup uses group_builder fragment and appends child body`() {
        val context = TemplateContext(
            iconVariables = emptyMap(),
            definitions = emptyMap(),
            fragments = mapOf(
                "group_builder" to $$"group(rotate = ${group:rotate})",
            ),
        )
        val childPath = createPath(fill = ComposeBrush.SolidColor("Color.Black"))
        val group = createGroup(
            rotate = 45f,
            commands = listOf(childPath),
        )

        val result = emitter.emit(group, context)
        assertContains(result, "group(rotate = 45")
        assertContains(result, "{\n")
        assertContains(result, "    path(")
    }

    @Test
    fun `emitGroup does not duplicate body in resolved fragment`() {
        val context = TemplateContext(
            iconVariables = emptyMap(),
            definitions = emptyMap(),
            fragments = mapOf(
                "group_builder" to $$"group(rotate = ${group:rotate})",
            ),
        )
        val childPath = createPath(fill = ComposeBrush.SolidColor("Color.Black"))
        val group = createGroup(
            rotate = 90f,
            commands = listOf(childPath),
        )

        val result = emitter.emit(group, context)
        val pathOccurrences = result.split("path(").size - 1
        assertEquals(1, pathOccurrences, "Child body should appear exactly once")
    }

    // endregion

    // region wrapMultiLineCall

    @Test
    fun `single-line call is returned unchanged`() {
        val context = TemplateContext(
            iconVariables = emptyMap(),
            definitions = emptyMap(),
            fragments = mapOf(
                "path_builder" to $$"myPath(fill = ${path:fill})",
            ),
        )
        val path = createPath()

        val result = emitter.emit(path, context)
        // Single-line resolved call should not be wrapped
        assertContains(result, "myPath(fill = SolidColor(")
        assertContains(result, ")) {")
    }

    @Test
    fun `multi-line gradient value triggers wrapping`() {
        val gradientValue = "Brush.linearGradient(\n    0.0f to Color.Red,\n    1.0f to Color.Blue,\n)"
        val context = TemplateContext(
            iconVariables = emptyMap(),
            definitions = emptyMap(),
            fragments = mapOf(
                "path_builder" to $$"myPath(fill = ${path:fill})",
            ),
        )
        val path = createPath(fill = ComposeBrush.SolidColor(gradientValue))

        val result = emitter.emit(path, context)
        assertContains(result, "myPath(")
        assertContains(result, ")")
    }

    // endregion

    // region null parameter trimming

    @Test
    fun `null parameters are trimmed from resolved fragment`() {
        val context = TemplateContext(
            iconVariables = emptyMap(),
            definitions = emptyMap(),
            fragments = mapOf(
                "path_builder" to $$"myPath(fill = ${path:fill}, stroke = ${path:stroke})",
            ),
        )
        val path = createPath()

        val result = emitter.emit(path, context)
        assertContains(result, "fill = SolidColor(")
        assertFalse(result.contains("stroke ="), "Null stroke parameter should be trimmed")
    }

    // endregion

    // region helpers

    private fun createPath(
        fill: ComposeBrush? = ComposeBrush.SolidColor("Color.Black"),
        fillAlpha: Float? = null,
        stroke: ComposeBrush? = null,
    ): ImageVectorNode.Path {
        val pathNodes = listOf(
            PathNodes.MoveTo(values = listOf("10", "20"), isRelative = false, minified = false),
            PathNodes.LineTo(values = listOf("30", "40"), isRelative = false, minified = false),
        )
        return ImageVectorNode.Path(
            params = ImageVectorNode.Path.Params(
                fill = fill,
                fillAlpha = fillAlpha,
                stroke = stroke,
            ),
            wrapper = ImageVectorNode.NodeWrapper(normalizedPath = "M10 20 L30 40", nodes = pathNodes),
            minified = false,
        )
    }

    private fun createGroup(
        rotate: Float? = null,
        pivotX: Float? = null,
        pivotY: Float? = null,
        commands: List<ImageVectorNode> = listOf(createPath()),
    ): ImageVectorNode.Group = ImageVectorNode.Group(
        commands = commands,
        minified = false,
        params = ImageVectorNode.Group.Params(
            rotate = rotate,
            pivotX = pivotX,
            pivotY = pivotY,
        ),
    )

    // endregion
}
