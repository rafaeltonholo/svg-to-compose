package dev.tonholo.s2c.remote.source

import app.cash.burst.Burst
import app.cash.burst.burstValues
import kotlinx.coroutines.test.runTest
import okio.Path.Companion.toPath
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DefaultSourceResolverTest {

    @Test
    @Burst
    fun `given source type without resolver - when resolve is called - then throws UnsupportedRemoteSourceException`(
        source: RemoteSource = burstValues(
            RemoteSource.Url("https://example.com/icon.svg"),
            RemoteSource.Archive("https://example.com/icons.zip"),
            RemoteSource.Font.Css("https://example.com/icons.css"),
            RemoteSource.Font.FileWithMapping(
                fontUrl = "https://example.com/icons.ttf",
                mappingUrl = "https://example.com/mapping.json",
            ),
        ),
    ) = runTest {
        // Arrange
        val resolver = DefaultSourceResolver()

        // Act
        val exception = assertFailsWith<UnsupportedRemoteSourceException> {
            resolver.resolve(source = source, outputDir = "/output".toPath())
        }

        // Assert
        assertEquals(source, exception.source)
    }
}
