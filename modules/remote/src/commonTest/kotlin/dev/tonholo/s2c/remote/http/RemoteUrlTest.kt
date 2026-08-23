package dev.tonholo.s2c.remote.http

import app.cash.burst.Burst
import app.cash.burst.burstValues
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RemoteUrlTest {

    @Test
    @Burst
    fun `given https url - when validated - then passes`(
        url: String = burstValues(
            "https://example.com/icon.svg",
            "HTTPS://EXAMPLE.COM/icon.svg",
        ),
    ) {
        validateRemoteUrl(url)
    }

    @Test
    @Burst
    fun `given http url with insecure opt-in - when validated - then passes`(
        url: String = burstValues(
            "http://example.com/icon.svg",
            "HTTP://example.com/icon.svg",
        ),
    ) {
        validateRemoteUrl(url, allowInsecure = true)
    }

    @Test
    @Burst
    fun `given unsupported url - when validated - then throws with url in message`(
        url: String = burstValues(
            "http://example.com/icon.svg",
            "ftp://example.com/icon.svg",
            "example.com/icon.svg",
            "file:///downloads/https://example.com/icon.svg",
        ),
    ) {
        // Act
        val exception = assertFailsWith<IllegalArgumentException> {
            validateRemoteUrl(url)
        }

        // Assert
        assertContains(checkNotNull(exception.message), url)
    }

    @Test
    fun `given ftp url with insecure opt-in - when validated - then message offers https or http`() {
        // Act
        val exception = assertFailsWith<IllegalArgumentException> {
            validateRemoteUrl("ftp://example.com/icon.svg", allowInsecure = true)
        }

        // Assert
        assertEquals(
            "Unsupported remote source URL 'ftp://example.com/icon.svg': expected a https or http URL",
            exception.message,
        )
    }
}
