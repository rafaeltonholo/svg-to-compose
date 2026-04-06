package dev.tonholo.s2c.cli.update

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem

class UpdateCacheTest {
    private val fileSystem = FakeFileSystem()
    private val cacheDir = "/home/user/.s2c".toPath()

    @Test
    fun `given no cache file - when isFresh is called - then returns false`() {
        // Arrange
        val cache = UpdateCache(
            fileSystem = fileSystem,
            cacheDir = cacheDir,
        )

        // Act
        val result = cache.isFresh(nowEpochMillis = BASE_TIME)

        // Assert
        assertFalse(result)
    }

    @Test
    fun `given fresh cache - when read is called - then returns cached data`() {
        // Arrange
        val cache = UpdateCache(
            fileSystem = fileSystem,
            cacheDir = cacheDir,
        )
        val entry = UpdateCacheEntry(
            latestVersion = "2.3.0",
            releaseUrl = "https://github.com/rafaeltonholo/svg-to-compose/releases/tag/v2.3.0",
            checkedAtEpochMillis = BASE_TIME,
        )
        cache.write(entry)

        // Act
        val result = cache.read()

        // Assert
        assertEquals(expected = entry.latestVersion, actual = result?.latestVersion)
        assertEquals(expected = entry.releaseUrl, actual = result?.releaseUrl)
    }

    @Test
    fun `given expired cache - when isFresh is called - then returns false`() {
        // Arrange
        val cache = UpdateCache(
            fileSystem = fileSystem,
            cacheDir = cacheDir,
        )
        val entry = UpdateCacheEntry(
            latestVersion = "2.3.0",
            releaseUrl = "https://github.com/rafaeltonholo/svg-to-compose/releases/tag/v2.3.0",
            checkedAtEpochMillis = BASE_TIME - TWENTY_FIVE_HOURS_MILLIS,
        )
        cache.write(entry)

        // Act
        val result = cache.isFresh(nowEpochMillis = BASE_TIME)

        // Assert
        assertFalse(result)
    }

    @Test
    fun `given corrupted cache - when read is called - then returns null`() {
        // Arrange
        fileSystem.createDirectories(cacheDir)
        val cachePath = cacheDir / UpdateCache.CACHE_FILE_NAME
        fileSystem.write(cachePath) {
            writeUtf8("this is not valid json {{{")
        }
        val cache = UpdateCache(
            fileSystem = fileSystem,
            cacheDir = cacheDir,
        )

        // Act
        val result = cache.read()

        // Assert
        assertNull(result)
    }

    @Test
    fun `given valid data - when write is called - then file is created`() {
        // Arrange
        val cache = UpdateCache(
            fileSystem = fileSystem,
            cacheDir = cacheDir,
        )
        val entry = UpdateCacheEntry(
            latestVersion = "2.3.0",
            releaseUrl = "https://github.com/rafaeltonholo/svg-to-compose/releases/tag/v2.3.0",
            checkedAtEpochMillis = BASE_TIME,
        )

        // Act
        cache.write(entry)

        // Assert
        val cachePath = cacheDir / UpdateCache.CACHE_FILE_NAME
        assertTrue(fileSystem.exists(cachePath))
    }

    @Test
    fun `given fresh cache within 24h - when isFresh is called - then returns true`() {
        // Arrange
        val cache = UpdateCache(
            fileSystem = fileSystem,
            cacheDir = cacheDir,
        )
        val entry = UpdateCacheEntry(
            latestVersion = "2.3.0",
            releaseUrl = "https://github.com/rafaeltonholo/svg-to-compose/releases/tag/v2.3.0",
            checkedAtEpochMillis = BASE_TIME - ONE_HOUR_MILLIS,
        )
        cache.write(entry)

        // Act
        val result = cache.isFresh(nowEpochMillis = BASE_TIME)

        // Assert
        assertTrue(result)
    }

    @Test
    fun `given cache dir does not exist - when write is called - then creates directory and file`() {
        // Arrange
        val nonExistentDir = "/home/user/.s2c-new".toPath()
        val cache = UpdateCache(
            fileSystem = fileSystem,
            cacheDir = nonExistentDir,
        )
        val entry = UpdateCacheEntry(
            latestVersion = "1.0.0",
            releaseUrl = "https://example.com",
            checkedAtEpochMillis = BASE_TIME,
        )

        // Act
        cache.write(entry)

        // Assert
        assertTrue(fileSystem.exists(nonExistentDir))
        assertTrue(fileSystem.exists(nonExistentDir / UpdateCache.CACHE_FILE_NAME))
    }

    private companion object {
        private const val BASE_TIME = 1_712_160_600_000L // 2024-04-03T14:30:00 UTC approx
        private const val ONE_HOUR_MILLIS = 3_600_000L
        private const val TWENTY_FIVE_HOURS_MILLIS = 90_000_000L
    }
}
