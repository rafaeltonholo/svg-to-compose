package dev.tonholo.s2c.remote.http

import kotlin.test.Test
import kotlin.test.assertNotNull

class CreateHttpTransportTest {

    @Test
    fun `given platform - when createHttpTransport is called - then a transport is created`() {
        // Act
        val transport = createHttpTransport()

        // Assert
        assertNotNull(transport)
    }
}
