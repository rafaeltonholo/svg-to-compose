package dev.tonholo.s2c.cli.update

import app.cash.burst.Burst
import app.cash.burst.burstValues
import kotlin.test.Test
import kotlin.test.assertEquals

class WrapperDetectorTest {

    @Test
    @Burst
    fun `given S2C_WRAPPER env var - when detect called - then returns expected result`(
        params: Pair<String?, Boolean> = burstValues(
            null to false,
            "" to false,
            "   " to false,
            "false" to false,
            "true" to true,
            "TRUE" to true,
            " true " to true,
            "\tTRUE\n" to true,
        ),
    ) {
        // Arrange
        val (envValue, expected) = params
        val detector = WrapperDetector(envReader = { envValue })

        // Act
        val actual = detector.isRunningFromWrapper()

        // Assert
        assertEquals(expected = expected, actual = actual)
    }
}
