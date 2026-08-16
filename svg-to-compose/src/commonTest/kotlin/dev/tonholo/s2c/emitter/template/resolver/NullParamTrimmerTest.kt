package dev.tonholo.s2c.emitter.template.resolver

import app.cash.burst.Burst
import kotlin.test.Test
import kotlin.test.assertEquals

class NullParamTrimmerTest {
    @Test
    @Burst
    fun `given comma artifact inside a string literal - when assembling - then the literal is untouched`(
        case: StringLiteralCase,
    ) {
        // Arrange
        val tokens = listOf(
            ResolvedToken.Text("""fn(label = "${case.literal}", fill = Color.Red, alpha = """),
            ResolvedToken.Null,
            ResolvedToken.Text(")"),
        )
        // Act
        val actual = NullParamTrimmer.assemble(tokens)
        // Assert
        assertEquals(
            expected = """fn(label = "${case.literal}", fill = Color.Red)""",
            actual = actual,
        )
    }

    @Test
    fun `given null as the last parameter - when assembling - then the trailing comma is removed`() {
        // Arrange
        val tokens = listOf(
            ResolvedToken.Text("fn(fill = Color.Red, alpha = "),
            ResolvedToken.Null,
            ResolvedToken.Text(")"),
        )
        // Act
        val actual = NullParamTrimmer.assemble(tokens)
        // Assert
        assertEquals(expected = "fn(fill = Color.Red)", actual = actual)
    }

    @Test
    fun `given null as the first parameter - when assembling - then the leading comma is removed`() {
        // Arrange
        val tokens = listOf(
            ResolvedToken.Text("fn("),
            ResolvedToken.Null,
            ResolvedToken.Text(""),
            ResolvedToken.Text(", stroke = Color.Blue)"),
        )
        // Act
        val actual = NullParamTrimmer.assemble(tokens)
        // Assert
        assertEquals(expected = "fn(stroke = Color.Blue)", actual = actual)
    }

    @Test
    fun `given null between two parameters - when assembling - then the duplicated comma is removed`() {
        // Arrange
        val tokens = listOf(
            ResolvedToken.Text("fn(fill = Color.Red, "),
            ResolvedToken.Null,
            ResolvedToken.Text(""),
            ResolvedToken.Text(", stroke = Color.Blue)"),
        )
        // Act
        val actual = NullParamTrimmer.assemble(tokens)
        // Assert
        assertEquals(expected = "fn(fill = Color.Red, stroke = Color.Blue)", actual = actual)
    }

    @Test
    fun `given consecutive null parameters at the end - when assembling - then the trailing comma is removed`() {
        // Arrange
        val tokens = listOf(
            ResolvedToken.Text("fn(fill = Color.Red, "),
            ResolvedToken.Null,
            ResolvedToken.Text(", "),
            ResolvedToken.Null,
            ResolvedToken.Text(")"),
        )
        // Act
        val actual = NullParamTrimmer.assemble(tokens)
        // Assert
        assertEquals(expected = "fn(fill = Color.Red)", actual = actual)
    }

    @Test
    fun `given every parameter is null - when assembling - then only the call parentheses remain`() {
        // Arrange
        val tokens = listOf(
            ResolvedToken.Text("fn(fill = "),
            ResolvedToken.Null,
            ResolvedToken.Text(", alpha = "),
            ResolvedToken.Null,
            ResolvedToken.Text(")"),
        )
        // Act
        val actual = NullParamTrimmer.assemble(tokens)
        // Assert
        assertEquals(expected = "fn()", actual = actual)
    }

    @Suppress("unused", "EnumEntryName")
    enum class StringLiteralCase(val literal: String) {
        `double comma`(literal = "a,,b"),
        `open parenthesis then comma`(literal = "x(,y"),
        `comma then close parenthesis`(literal = "x,)y"),
    }
}
