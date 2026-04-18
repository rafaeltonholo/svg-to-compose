package dev.tonholo.s2c.cli.inject

import com.github.ajalt.mordant.platform.MultiplatformSystem
import com.github.ajalt.mordant.terminal.Terminal
import dev.tonholo.s2c.SvgToComposeContext
import dev.tonholo.s2c.cli.config.BuildConfig
import dev.tonholo.s2c.cli.dispatching.DeferredFileDispatcher
import dev.tonholo.s2c.cli.inject.coroutine.IoDispatcher
import dev.tonholo.s2c.cli.logger.CliLogger
import dev.tonholo.s2c.cli.runtime.CliConfig
import dev.tonholo.s2c.cli.runtime.Client
import dev.tonholo.s2c.cli.update.GitHubReleaseFetcher
import dev.tonholo.s2c.cli.update.UpdateCache
import dev.tonholo.s2c.cli.update.VersionChecker
import dev.tonholo.s2c.cli.update.WrapperDetector
import dev.tonholo.s2c.dispatching.FileDispatcher
import dev.tonholo.s2c.inject.FileDispatcherBindings
import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.runtime.S2cConfig
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import kotlin.time.Clock

/**
 * Root dependency graph for the CLI application.
 *
 * Receives core S2C bindings (e.g. [dev.tonholo.s2c.io.FileManager],
 * content parsers) via the contributed [dev.tonholo.s2c.inject.SvgToComposeBindings]
 * container and adds CLI-specific bindings for [FileSystem], [Logger],
 * and the [CliConfig]-to-[S2cConfig] binding.
 *
 * Created once per CLI invocation via [Factory.create].
 */
@DependencyGraph(
    scope = AppScope::class,
    additionalScopes = [CliScope::class],
    excludes = [FileDispatcherBindings::class],
)
internal interface CliGraph {
    /** The Clikt command entry point, fully injected with all dependencies. */
    val client: Client

    @Binds
    val CliLogger.logger: Logger

    /** Binds the concrete [CliConfig] to the [S2cConfig] interface. */
    @Binds
    val CliConfig.bindsConfig: S2cConfig

    /** Provides the native system [FileSystem] for file I/O operations. */
    @Provides
    fun provideFileSystem(): FileSystem = FileSystem.SYSTEM

    /** Provides a [Terminal] instance for TUI rendering and raw input handling. */
    @Provides
    fun provideTerminal(): Terminal = Terminal()

    /**
     * Provides a [FileDispatcher] that defers strategy selection until dispatch
     * time, so it observes the [S2cConfig.parallel] flag after [Client.run]
     * updates the configuration.
     *
     * Overrides the default [dev.tonholo.s2c.dispatching.SequentialFileDispatcher] binding
     * contributed by [dev.tonholo.s2c.inject.FileDispatcherBindings].
     */
    @Provides
    fun provideFileDispatcher(
        context: SvgToComposeContext,
        @IoDispatcher dispatcher: CoroutineDispatcher,
    ): FileDispatcher = DeferredFileDispatcher(context, dispatcher)

    /**
     * Provides a [WrapperDetector] that reads the [WrapperDetector.ENV_VAR_NAME]
     * environment variable through Mordant's cross-platform system helper.
     */
    @Provides
    fun provideWrapperDetector(): WrapperDetector = WrapperDetector(
        envReader = { MultiplatformSystem.readEnvironmentVariable(WrapperDetector.ENV_VAR_NAME) },
    )

    /**
     * Provides a no-op [GitHubReleaseFetcher]. Replace this binding with a real
     * HTTP-backed implementation to activate update checks end-to-end.
     */
    @Provides
    @SingleIn(CliScope::class)
    fun provideGitHubReleaseFetcher(): GitHubReleaseFetcher = GitHubReleaseFetcher { null }

    /** Shared [Json] configuration for the update-check cache. */
    @Provides
    @SingleIn(CliScope::class)
    fun provideUpdateJson(): Json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    /**
     * Provides the [UpdateCache] rooted at [DEFAULT_CACHE_DIR]. I/O runs on the
     * injected [IoDispatcher] to keep file access off the main thread.
     */
    @Provides
    @SingleIn(CliScope::class)
    fun provideUpdateCache(
        fileSystem: FileSystem,
        json: Json,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): UpdateCache = UpdateCache(
        fileSystem = fileSystem,
        cacheDir = DEFAULT_CACHE_DIR.toPath(),
        json = json,
        ioDispatcher = ioDispatcher,
    )

    /**
     * Provides the [VersionChecker] configured with the running CLI version
     * from [BuildConfig] and a wall-clock time source.
     */
    @Provides
    fun provideVersionChecker(cache: UpdateCache, fetcher: GitHubReleaseFetcher): VersionChecker =
        VersionChecker(
            currentVersion = BuildConfig.VERSION,
            cache = cache,
            fetcher = fetcher,
            nowEpochMillis = { Clock.System.now().toEpochMilliseconds() },
        )

    /**
     * Factory for creating the [CliGraph].
     */
    @DependencyGraph.Factory
    fun interface Factory {
        /**
         * Creates a new [CliGraph] instance.
         *
         * @param config The mutable [CliConfig] instance that will be updated
         *  by [Client] once CLI flags are parsed.
         */
        fun create(@Provides config: CliConfig): CliGraph
    }
}

internal abstract class CliScope

private const val DEFAULT_CACHE_DIR = ".s2c/cache"
