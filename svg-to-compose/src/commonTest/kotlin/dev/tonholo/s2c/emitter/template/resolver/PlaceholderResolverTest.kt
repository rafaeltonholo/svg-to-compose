package dev.tonholo.s2c.emitter.template.resolver

import dev.tonholo.s2c.emitter.template.TemplateContext
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse

class PlaceholderResolverTest {
    private fun createContext(
        iconVars: Map<String, String?> = emptyMap(),
        definitions: Map<String, String> = emptyMap(),
        fragments: Map<String, String> = emptyMap(),
    ) = TemplateContext(
        iconVariables = iconVars,
        definitions = definitions,
        fragments = fragments,
    )

    @Test
    fun `resolves icon namespace variables`() {
        val ctx = createContext(iconVars = mapOf("name" to "MyIcon", "width" to "24.0"))
        val result = PlaceholderResolver.resolve(
            $$"val ${icon:name}: ImageVector // width=${icon:width}",
            ctx,
        )
        assertEquals("val MyIcon: ImageVector // width=24.0", result)
    }

    @Test
    fun `resolves path namespace variables`() {
        val ctx = createContext()
        val pathVars = mapOf("fill" to "SolidColor(Color.Black)", "fill_alpha" to "1.0f")
        val result = PlaceholderResolver.resolve(
            $$"fill = ${path:fill}, fillAlpha = ${path:fill_alpha}",
            ctx,
            nodeVariables = pathVars,
            nodeNamespace = "path",
        )
        assertEquals("fill = SolidColor(Color.Black), fillAlpha = 1.0f", result)
    }

    @Test
    fun `resolves group namespace variables`() {
        val ctx = createContext()
        val groupVars = mapOf("rotate" to "45.0f", "pivot_x" to "12.0f")
        val result = PlaceholderResolver.resolve(
            $$"rotate = ${group:rotate}, pivotX = ${group:pivot_x}",
            ctx,
            nodeVariables = groupVars,
            nodeNamespace = "group",
        )
        assertEquals("rotate = 45.0f, pivotX = 12.0f", result)
    }

    @Test
    fun `resolves def namespace and registers import`() {
        val ctx = createContext(definitions = mapOf("icon_builder" to "com.example.icons.icon"))
        val result = PlaceholderResolver.resolve($$"${def:icon_builder}()", ctx)
        assertEquals("icon()", result)
        assertContains(ctx.collectedImports, "com.example.icons.icon")
    }

    @Test
    fun `resolves template namespace with fragment`() {
        val ctx = createContext(
            iconVars = mapOf("name" to "TestIcon"),
            fragments = mapOf("icon_builder" to $$"Builder(name = ${icon:name})"),
        )
        val result = PlaceholderResolver.resolve($$"${template:icon_builder}", ctx)
        assertEquals("Builder(name = TestIcon)", result)
    }

    @Test
    fun `null variable trims entire line`() {
        val ctx = createContext(iconVars = mapOf("name" to "MyIcon", "receiver" to null))
        val result = PlaceholderResolver.resolve(
            $$"name = ${icon:name},$\n    receiver = ${icon:receiver},$\nend",
            ctx,
        )
        assertContains(result, "name = MyIcon,")
        assertFalse(result.contains("receiver"))
        assertContains(result, "end")
    }

    @Test
    fun `detects cycle in fragment resolution`() {
        val ctx = createContext(
            fragments = mapOf(
                "a" to $$"${template:b}",
                "b" to $$"${template:a}",
            ),
        )
        assertFailsWith<IllegalStateException> {
            PlaceholderResolver.resolve($$"${template:a}", ctx)
        }
    }

    @Test
    fun `errors on unknown fragment`() {
        val ctx = createContext()
        assertFailsWith<IllegalStateException> {
            PlaceholderResolver.resolve($$"${template:nonexistent}", ctx)
        }
    }

    @Test
    fun `errors on unknown definition key`() {
        val ctx = createContext()
        assertFailsWith<IllegalStateException> {
            PlaceholderResolver.resolve($$"${def:nonexistent}", ctx)
        }
    }

    @Test
    fun `nested fragment with def resolution`() {
        val ctx = createContext(
            iconVars = mapOf("name" to "Icon"),
            definitions = mapOf("builder" to "com.example.Builder"),
            fragments = mapOf("my_builder" to $$"${def:builder}(name = ${icon:name})"),
        )
        val result = PlaceholderResolver.resolve($$"${template:my_builder}", ctx)
        assertEquals("Builder(name = Icon)", result)
        assertContains(ctx.collectedImports, "com.example.Builder")
    }

    @Test
    fun `preserves non-placeholder text`() {
        val ctx = createContext(iconVars = mapOf("name" to "MyIcon"))
        val result = PlaceholderResolver.resolve(
            $$"val ${icon:name}: ImageVector get() = builder.build()",
            ctx,
        )
        assertEquals("val MyIcon: ImageVector get() = builder.build()", result)
    }

    // --- Inline null trimming tests ---

    @Test
    fun `null at start of inline param list`() {
        val ctx = createContext()
        val pathVars = mapOf("fill_alpha" to null, "stroke" to "Color.Red")
        val result = PlaceholderResolver.resolve(
            $$"fn(fillAlpha = ${path:fill_alpha}, stroke = ${path:stroke})",
            ctx,
            nodeVariables = pathVars,
            nodeNamespace = "path",
        )
        assertEquals("fn(stroke = Color.Red)", result)
    }

    @Test
    fun `null at end of inline param list`() {
        val ctx = createContext()
        val pathVars = mapOf("fill" to "Color.Red", "fill_alpha" to null)
        val result = PlaceholderResolver.resolve(
            $$"fn(fill = ${path:fill}, fillAlpha = ${path:fill_alpha})",
            ctx,
            nodeVariables = pathVars,
            nodeNamespace = "path",
        )
        assertEquals("fn(fill = Color.Red)", result)
    }

    @Test
    fun `null in middle of inline param list`() {
        val ctx = createContext()
        val pathVars = mapOf(
            "fill" to "Color.Red",
            "fill_alpha" to null,
            "stroke" to "Color.Blue",
        )
        val result = PlaceholderResolver.resolve(
            $$"fn(fill = ${path:fill}, fillAlpha = ${path:fill_alpha}, stroke = ${path:stroke})",
            ctx,
            nodeVariables = pathVars,
            nodeNamespace = "path",
        )
        assertEquals("fn(fill = Color.Red, stroke = Color.Blue)", result)
    }

    @Test
    fun `all params null on single line`() {
        val ctx = createContext()
        val pathVars = mapOf("fill" to null, "fill_alpha" to null)
        val result = PlaceholderResolver.resolve(
            $$"fn(fill = ${path:fill}, fillAlpha = ${path:fill_alpha})",
            ctx,
            nodeVariables = pathVars,
            nodeNamespace = "path",
        )
        assertEquals("fn()", result)
    }

    @Test
    fun `multiple nulls in middle of inline param list`() {
        val ctx = createContext()
        val pathVars = mapOf(
            "fill" to "Color.Red",
            "fill_alpha" to null,
            "stroke_alpha" to null,
            "stroke" to "Color.Blue",
        )
        val result = PlaceholderResolver.resolve(
            $$"fn(fill = ${path:fill}, fillAlpha = ${path:fill_alpha}, " +
                $$"strokeAlpha = ${path:stroke_alpha}, stroke = ${path:stroke})",
            ctx,
            nodeVariables = pathVars,
            nodeNamespace = "path",
        )
        assertEquals("fn(fill = Color.Red, stroke = Color.Blue)", result)
    }

    @Test
    fun `multi-line params with null lines dropped`() {
        val ctx = createContext()
        val pathVars = mapOf(
            "fill" to "SolidColor(Color.Black)",
            "fill_alpha" to null,
            "fill_type" to "EvenOdd",
        )
        val template = $$"""
            |path(
            |    fill = ${path:fill},
            |    fillAlpha = ${path:fill_alpha},
            |    pathFillType = ${path:fill_type},
            |)
        """.trimMargin()
        val result = PlaceholderResolver.resolve(
            template,
            ctx,
            nodeVariables = pathVars,
            nodeNamespace = "path",
        )
        assertContains(result, "fill = SolidColor(Color.Black),")
        assertFalse(result.contains("fillAlpha"))
        assertContains(result, "pathFillType = EvenOdd,")
    }
}
