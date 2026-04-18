package dev.tonholo.s2c.cli.update

import okio.Path.Companion.toPath
import kotlin.test.Test
import kotlin.test.assertEquals

class CacheDirResolverTest {

    @Test
    fun `given XDG_CACHE_HOME set - when resolve called - then returns XDG path with s2c suffix`() {
        // Arrange
        val env = mapOf("XDG_CACHE_HOME" to "/custom/xdg/cache")
        val resolver = CacheDirResolver(envReader = env::get)

        // Act
        val actual = resolver.resolve()

        // Assert
        assertEquals(expected = "/custom/xdg/cache/s2c".toPath(), actual = actual)
    }

    @Test
    fun `given XDG_CACHE_HOME blank and HOME set - when resolve called - then returns HOME cache path`() {
        // Arrange
        val env = mapOf("XDG_CACHE_HOME" to "   ", "HOME" to "/home/user")
        val resolver = CacheDirResolver(envReader = env::get)

        // Act
        val actual = resolver.resolve()

        // Assert
        assertEquals(expected = "/home/user/.cache/s2c".toPath(), actual = actual)
    }

    @Test
    fun `given HOME set and XDG_CACHE_HOME absent - when resolve called - then returns HOME cache path`() {
        // Arrange
        val env = mapOf("HOME" to "/home/user")
        val resolver = CacheDirResolver(envReader = env::get)

        // Act
        val actual = resolver.resolve()

        // Assert
        assertEquals(expected = "/home/user/.cache/s2c".toPath(), actual = actual)
    }

    @Test
    fun `given only USERPROFILE set - when resolve called - then returns USERPROFILE cache path`() {
        // Arrange
        val env = mapOf("USERPROFILE" to "C:\\Users\\alice")
        val resolver = CacheDirResolver(envReader = env::get)

        // Act
        val actual = resolver.resolve()

        // Assert
        assertEquals(expected = "C:\\Users\\alice/.cache/s2c".toPath(), actual = actual)
    }

    @Test
    fun `given no environment variables set - when resolve called - then falls back to relative dir`() {
        // Arrange
        val resolver = CacheDirResolver(envReader = { null })

        // Act
        val actual = resolver.resolve()

        // Assert
        assertEquals(expected = ".s2c/cache".toPath(), actual = actual)
    }

    @Test
    fun `given XDG_CACHE_HOME takes precedence over HOME - when resolve called - then returns XDG path`() {
        // Arrange
        val env = mapOf(
            "XDG_CACHE_HOME" to "/xdg/cache",
            "HOME" to "/home/user",
            "USERPROFILE" to "C:\\Users\\alice",
        )
        val resolver = CacheDirResolver(envReader = env::get)

        // Act
        val actual = resolver.resolve()

        // Assert
        assertEquals(expected = "/xdg/cache/s2c".toPath(), actual = actual)
    }
}
