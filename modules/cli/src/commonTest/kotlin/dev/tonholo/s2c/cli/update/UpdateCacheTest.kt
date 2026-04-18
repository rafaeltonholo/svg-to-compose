package dev.tonholo.s2c.cli.update

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateCacheTest {
    private val fileSystem = FakeFileSystem()
    private val cacheDir = "/home/user/.s2c".toPath()
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    private fun createCache(fs: FakeFileSystem = fileSystem, dir: okio.Path = cacheDir) =
        UpdateCache(
            fileSystem = fs,
            cacheDir = dir,
            json = json,
            ioDispatcher = UnconfinedTestDispatcher(),
        )

    @AfterTest
    fun tearDown() {
        fileSystem.checkNoOpenFiles()
    }

    @Test
    fun `given no cache file - when readIfFresh is called - then returns null`() = runTest {
        // Arrange
        val cache = createCache()

        // Act
        val result = cache.readIfFresh(nowEpochMillis = BASE_TIME)

        // Assert
        assertNull(result)
    }

    @Test
    fun `given fresh cache - when read is called - then returns cached data`() = runTest {
        // Arrange
        val cache = createCache()
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
    fun `given expired cache - when readIfFresh is called - then returns null`() = runTest {
        // Arrange
        val cache = createCache()
        val entry = UpdateCacheEntry(
            latestVersion = "2.3.0",
            releaseUrl = "https://github.com/rafaeltonholo/svg-to-compose/releases/tag/v2.3.0",
            checkedAtEpochMillis = BASE_TIME - TWENTY_FIVE_HOURS_MILLIS,
        )
        cache.write(entry)

        // Act
        val result = cache.readIfFresh(nowEpochMillis = BASE_TIME)

        // Assert
        assertNull(result)
    }

    @Test
    fun `given corrupted cache - when read is called - then returns null`() = runTest {
        // Arrange
        fileSystem.createDirectories(cacheDir)
        val cachePath = cacheDir / UpdateCache.CACHE_FILE_NAME
        fileSystem.write(cachePath) {
            writeUtf8("this is not valid json {{{")
        }
        val cache = createCache()

        // Act
        val result = cache.read()

        // Assert
        assertNull(result)
    }

    @Test
    fun `given valid data - when write is called - then file is created`() = runTest {
        // Arrange
        val cache = createCache()
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
    fun `given fresh cache within 24h - when readIfFresh is called - then returns entry`() =
        runTest {
            // Arrange
            val cache = createCache()
            val entry = UpdateCacheEntry(
                latestVersion = "2.3.0",
                releaseUrl = "https://github.com/rafaeltonholo/svg-to-compose/releases/tag/v2.3.0",
                checkedAtEpochMillis = BASE_TIME - ONE_HOUR_MILLIS,
            )
            cache.write(entry)

            // Act
            val result = cache.readIfFresh(nowEpochMillis = BASE_TIME)

            // Assert
            assertEquals(expected = entry.latestVersion, actual = result?.latestVersion)
        }

    @Test
    fun `given cache dir does not exist - when write is called - then creates directory and file`() =
        runTest {
            // Arrange
            val nonExistentDir = "/home/user/.s2c-new".toPath()
            val cache = createCache(dir = nonExistentDir)
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

    @Test
    fun `given cache checked exactly 24h ago - when readIfFresh is called - then returns null`() =
        runTest {
            // Arrange
            val cache = createCache()
            val entry = UpdateCacheEntry(
                latestVersion = "2.3.0",
                releaseUrl = "https://github.com/rafaeltonholo/svg-to-compose/releases/tag/v2.3.0",
                checkedAtEpochMillis = BASE_TIME - UpdateCache.TTL_MILLIS,
            )
            cache.write(entry)

            // Act
            val result = cache.readIfFresh(nowEpochMillis = BASE_TIME)

            // Assert
            assertNull(result)
        }

    @Test
    fun `given write failure - when write is called - then does not throw`() = runTest {
        // Arrange
        val readOnlyFs = FakeFileSystem()
        readOnlyFs.createDirectories(cacheDir)
        readOnlyFs.createDirectories(cacheDir / UpdateCache.CACHE_FILE_NAME)
        val cache = createCache(fs = readOnlyFs)
        val entry = UpdateCacheEntry(
            latestVersion = "1.0.0",
            releaseUrl = "https://example.com",
            checkedAtEpochMillis = BASE_TIME,
        )

        // Act - should not throw
        cache.write(entry)

        // Assert - read should return null since write failed
        assertNull(cache.read())
    }

    @Test
    fun `given cache with future timestamp - when readIfFresh is called - then returns null`() =
        runTest {
            // Arrange
            val cache = createCache()
            val entry = UpdateCacheEntry(
                latestVersion = "2.3.0",
                releaseUrl = "https://github.com/rafaeltonholo/svg-to-compose/releases/tag/v2.3.0",
                checkedAtEpochMillis = BASE_TIME + ONE_HOUR_MILLIS,
            )
            cache.write(entry)

            // Act
            val result = cache.readIfFresh(nowEpochMillis = BASE_TIME)

            // Assert
            assertNull(result)
        }

    private companion object {
        private const val BASE_TIME = 1_712_160_600_000L // 2024-04-03T14:30:00 UTC approx
        private const val ONE_HOUR_MILLIS = 3_600_000L
        private const val TWENTY_FIVE_HOURS_MILLIS = UpdateCache.TTL_MILLIS + ONE_HOUR_MILLIS
    }
}
