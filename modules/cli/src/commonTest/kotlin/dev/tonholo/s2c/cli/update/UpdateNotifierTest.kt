package dev.tonholo.s2c.cli.update

import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.OutputRenderer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateNotifierTest {
    private val fileSystem = FakeFileSystem()
    private val cacheDir = "/home/user/.s2c".toPath()
    private val json = Json { ignoreUnknownKeys = true }

    @AfterTest
    fun tearDown() {
        fileSystem.checkNoOpenFiles()
    }

    private fun buildChecker(
        currentVersion: String = "2.0.0",
        latestVersion: String = "2.3.0",
        releaseUrl: String = "https://example.com/v2.3.0",
    ): VersionChecker {
        val cache = UpdateCache(
            fileSystem = fileSystem,
            cacheDir = cacheDir,
            json = json,
            ioDispatcher = UnconfinedTestDispatcher(),
        )
        val fetcher = GitHubReleaseFetcher { LatestReleaseInfo(tagName = latestVersion, releaseUrl = releaseUrl) }
        return VersionChecker(
            currentVersion = currentVersion,
            cache = cache,
            fetcher = fetcher,
            nowEpochMillis = { 0L },
        )
    }

    @Test
    fun `given update available and running from wrapper - when notify - then emits UpdateAvailable with isWrapper true`() =
        runTest {
            // Arrange
            val checker = buildChecker()
            val detector = WrapperDetector(envReader = { "true" })
            val renderer = RecordingRenderer()
            val notifier = UpdateNotifier(versionChecker = checker, wrapperDetector = detector)

            // Act
            notifier.notifyIfUpdateAvailable(renderer = renderer)

            // Assert
            assertEquals(expected = 1, actual = renderer.events.size)
            val event = renderer.events.single() as ConversionEvent.UpdateAvailable
            assertEquals(expected = "2.0.0", actual = event.current)
            assertEquals(expected = "2.3.0", actual = event.latest)
            assertEquals(expected = "https://example.com/v2.3.0", actual = event.releaseUrl)
            assertTrue(event.isWrapper)
        }

    @Test
    fun `given update available and not running from wrapper - when notify - then emits UpdateAvailable with isWrapper false`() =
        runTest {
            // Arrange
            val checker = buildChecker()
            val detector = WrapperDetector(envReader = { null })
            val renderer = RecordingRenderer()
            val notifier = UpdateNotifier(versionChecker = checker, wrapperDetector = detector)

            // Act
            notifier.notifyIfUpdateAvailable(renderer = renderer)

            // Assert
            val event = renderer.events.single() as ConversionEvent.UpdateAvailable
            assertEquals(expected = false, actual = event.isWrapper)
        }

    @Test
    fun `given current version equals latest - when notify - then no event is emitted`() = runTest {
        // Arrange
        val checker = buildChecker(currentVersion = "2.3.0", latestVersion = "2.3.0")
        val detector = WrapperDetector(envReader = { "true" })
        val renderer = RecordingRenderer()
        val notifier = UpdateNotifier(versionChecker = checker, wrapperDetector = detector)

        // Act
        notifier.notifyIfUpdateAvailable(renderer = renderer)

        // Assert
        assertEquals(expected = 0, actual = renderer.events.size)
    }

    @Test
    fun `given fetcher returns null - when notify - then no event is emitted`() = runTest {
        // Arrange
        val cache = UpdateCache(
            fileSystem = fileSystem,
            cacheDir = cacheDir,
            json = json,
            ioDispatcher = UnconfinedTestDispatcher(),
        )
        val checker = VersionChecker(
            currentVersion = "2.0.0",
            cache = cache,
            fetcher = { null },
            nowEpochMillis = { 0L },
        )
        val detector = WrapperDetector(envReader = { "true" })
        val renderer = RecordingRenderer()
        val notifier = UpdateNotifier(versionChecker = checker, wrapperDetector = detector)

        // Act
        notifier.notifyIfUpdateAvailable(renderer = renderer)

        // Assert
        assertEquals(expected = 0, actual = renderer.events.size)
    }

    private class RecordingRenderer : OutputRenderer {
        val events = mutableListOf<ConversionEvent>()
        override fun onEvent(event: ConversionEvent) {
            events += event
        }
    }
}
