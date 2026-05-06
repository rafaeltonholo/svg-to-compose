package dev.tonholo.s2c.extensions

import app.cash.burst.Burst
import app.cash.burst.burstValues
import kotlin.test.Test
import kotlin.test.assertEquals

class StringExtensionTest {

    @Test
    @Burst
    fun `given segment when sanitizing then result is valid Kotlin identifier`(
        case: Pair<String, String> = burstValues(
            "radial-focal-tests" to "radial_focal_tests",
            "valid_segment" to "valid_segment",
            "MixedCase" to "MixedCase",
            "with space" to "with_space",
            "dot.in.name" to "dot_in_name",
            "123abc" to "_123abc",
            "9" to "_9",
            "_leading_underscore" to "_leading_underscore",
            "" to "_",
            "---" to "___",
            "kebab-case-name" to "kebab_case_name",
        ),
    ) {
        // Arrange
        val (input, expected) = case
        // Act
        val actual = input.toKotlinPackageSegment()
        // Assert
        assertEquals(expected, actual)
    }
}
