package dev.tonholo.s2c.cli.update

import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem

class VersionCheckerTest {
    private val fileSystem = FakeFileSystem()
    private val cacheDir = "/home/user/.s2c".toPath()

    @AfterTest
    fun tearDown() {
        fileSystem.checkNoOpenFiles()
    }

    @Test
    fun `given fresh cache with newer version - when check is called - then returns UpdateAvailable`() {
        // Arrange
        val cache = UpdateCache(fileSystem = fileSystem, cacheDir = cacheDir)
        cache.write(
            UpdateCacheEntry(
                latestVersion = "2.3.0",
                releaseUrl = "https://github.com/rafaeltonholo/svg-to-compose/releases/tag/v2.3.0",
                checkedAtEpochMillis = BASE_TIME - ONE_HOUR_MILLIS,
            ),
        )
        val checker = VersionChecker(
            currentVersion = "2.0.0",
            cache = cache,
            fetcher = GitHubReleaseFetcher { null },
            nowEpochMillis = { BASE_TIME },
        )

        // Act
        val result = checker.check()

        // Assert
        assertIs<UpdateCheckResult.UpdateAvailable>(result)
        assertEquals(expected = "2.0.0", actual = result.current.toString())
        assertEquals(expected = "2.3.0", actual = result.latest.toString())
        assertEquals(
            expected = "https://github.com/rafaeltonholo/svg-to-compose/releases/tag/v2.3.0",
            actual = result.releaseUrl,
        )
    }

    @Test
    fun `given fresh cache with same version - when check is called - then returns NoUpdate`() {
        // Arrange
        val cache = UpdateCache(fileSystem = fileSystem, cacheDir = cacheDir)
        cache.write(
            UpdateCacheEntry(
                latestVersion = "2.0.0",
                releaseUrl = "https://github.com/rafaeltonholo/svg-to-compose/releases/tag/v2.0.0",
                checkedAtEpochMillis = BASE_TIME - ONE_HOUR_MILLIS,
            ),
        )
        val checker = VersionChecker(
            currentVersion = "2.0.0",
            cache = cache,
            fetcher = GitHubReleaseFetcher { null },
            nowEpochMillis = { BASE_TIME },
        )

        // Act
        val result = checker.check()

        // Assert
        assertIs<UpdateCheckResult.NoUpdate>(result)
    }

    @Test
    fun `given expired cache - when check is called - then fetches from remote`() {
        // Arrange
        val cache = UpdateCache(fileSystem = fileSystem, cacheDir = cacheDir)
        cache.write(
            UpdateCacheEntry(
                latestVersion = "1.0.0",
                releaseUrl = "https://example.com/old",
                checkedAtEpochMillis = BASE_TIME - UpdateCache.TTL_MILLIS - ONE_HOUR_MILLIS,
            ),
        )
        val fetchedRelease = LatestReleaseInfo(
            tagName = "v3.0.0",
            releaseUrl = "https://github.com/rafaeltonholo/svg-to-compose/releases/tag/v3.0.0",
        )
        val checker = VersionChecker(
            currentVersion = "2.0.0",
            cache = cache,
            fetcher = GitHubReleaseFetcher { fetchedRelease },
            nowEpochMillis = { BASE_TIME },
        )

        // Act
        val result = checker.check()

        // Assert
        assertIs<UpdateCheckResult.UpdateAvailable>(result)
        assertEquals(expected = "3.0.0", actual = result.latest.toString())
    }

    @Test
    fun `given fetch failure - when check is called - then returns NoUpdate`() {
        // Arrange
        val cache = UpdateCache(fileSystem = fileSystem, cacheDir = cacheDir)
        val checker = VersionChecker(
            currentVersion = "2.0.0",
            cache = cache,
            fetcher = GitHubReleaseFetcher { null },
            nowEpochMillis = { BASE_TIME },
        )

        // Act
        val result = checker.check()

        // Assert
        assertIs<UpdateCheckResult.NoUpdate>(result)
    }

    @Test
    fun `given current version newer than latest - when check is called - then returns NoUpdate`() {
        // Arrange
        val cache = UpdateCache(fileSystem = fileSystem, cacheDir = cacheDir)
        cache.write(
            UpdateCacheEntry(
                latestVersion = "1.0.0",
                releaseUrl = "https://example.com/old",
                checkedAtEpochMillis = BASE_TIME - ONE_HOUR_MILLIS,
            ),
        )
        val checker = VersionChecker(
            currentVersion = "2.0.0",
            cache = cache,
            fetcher = GitHubReleaseFetcher { null },
            nowEpochMillis = { BASE_TIME },
        )

        // Act
        val result = checker.check()

        // Assert
        assertIs<UpdateCheckResult.NoUpdate>(result)
    }

    @Test
    fun `given no cache and successful fetch - when check is called - then caches result`() {
        // Arrange
        val cache = UpdateCache(fileSystem = fileSystem, cacheDir = cacheDir)
        val fetchedRelease = LatestReleaseInfo(
            tagName = "v3.0.0",
            releaseUrl = "https://github.com/rafaeltonholo/svg-to-compose/releases/tag/v3.0.0",
        )
        val checker = VersionChecker(
            currentVersion = "2.0.0",
            cache = cache,
            fetcher = GitHubReleaseFetcher { fetchedRelease },
            nowEpochMillis = { BASE_TIME },
        )

        // Act
        checker.check()

        // Assert - cache should now have data
        val cachedEntry = cache.read()
        assertEquals(expected = "3.0.0", actual = cachedEntry?.latestVersion)
    }

    @Test
    fun `given invalid current version - when check is called - then returns NoUpdate`() {
        // Arrange
        val cache = UpdateCache(fileSystem = fileSystem, cacheDir = cacheDir)
        val checker = VersionChecker(
            currentVersion = "invalid",
            cache = cache,
            fetcher = GitHubReleaseFetcher { null },
            nowEpochMillis = { BASE_TIME },
        )

        // Act
        val result = checker.check()

        // Assert
        assertIs<UpdateCheckResult.NoUpdate>(result)
    }

    @Test
    fun `given current version is SNAPSHOT - when latest is same base version - then returns NoUpdate`() {
        // Arrange
        val cache = UpdateCache(fileSystem = fileSystem, cacheDir = cacheDir)
        cache.write(
            UpdateCacheEntry(
                latestVersion = "3.0.0",
                releaseUrl = "https://github.com/rafaeltonholo/svg-to-compose/releases/tag/v3.0.0",
                checkedAtEpochMillis = BASE_TIME - ONE_HOUR_MILLIS,
            ),
        )
        val checker = VersionChecker(
            currentVersion = "3.0.0-SNAPSHOT",
            cache = cache,
            fetcher = GitHubReleaseFetcher { null },
            nowEpochMillis = { BASE_TIME },
        )

        // Act
        val result = checker.check()

        // Assert
        assertIs<UpdateCheckResult.NoUpdate>(result)
    }

    @Test
    fun `given GitHub returns pre-release tag - when check is called - then returns NoUpdate`() {
        // Arrange
        val cache = UpdateCache(fileSystem = fileSystem, cacheDir = cacheDir)
        val preReleaseInfo = LatestReleaseInfo(
            tagName = "v4.0.0-rc.1",
            releaseUrl = "https://github.com/rafaeltonholo/svg-to-compose/releases/tag/v4.0.0-rc.1",
        )
        val checker = VersionChecker(
            currentVersion = "3.0.0",
            cache = cache,
            fetcher = GitHubReleaseFetcher { preReleaseInfo },
            nowEpochMillis = { BASE_TIME },
        )

        // Act
        val result = checker.check()

        // Assert
        assertIs<UpdateCheckResult.NoUpdate>(result)
    }

    private companion object {
        private const val BASE_TIME = 1_712_160_600_000L
        private const val ONE_HOUR_MILLIS = 3_600_000L
    }
}
