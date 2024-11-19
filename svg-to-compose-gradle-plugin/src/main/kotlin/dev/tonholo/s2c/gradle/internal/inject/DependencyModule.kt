package dev.tonholo.s2c.gradle.internal.inject

import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.gradle.internal.cache.CacheManager
import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.io.IconWriter
import dev.tonholo.s2c.io.TempFileWriter
import dev.tonholo.s2c.logger.Logger
import okio.FileSystem
import org.gradle.api.Project
import org.gradle.api.provider.Provider

internal class DependencyModule(
    private val project: Project,
) {
    val providers = mapOf<Class<*>, () -> Provider<out Any>>(
        Logger::class.java to ::provideLogger,
        FileSystem::class.java to ::provideFileSystem,
        FileManager::class.java to ::provideFileManager,
        Processor::class.java to ::provideProcessor,
        CacheManager::class.java to ::provideCacheManager,
    )

    private fun provideLogger(): Provider<Logger> {
        return project
            .providers
            .provider { dev.tonholo.s2c.gradle.internal.logger.Logger(project.logger) }
    }

    private fun provideFileSystem(): Provider<FileSystem> =
        project
            .providers
            .provider { FileSystem.SYSTEM }

    private fun provideFileManager(): Provider<FileManager> =
        project
            .providers
            .provider {
                FileManager(get(), get())
            }

    private fun provideProcessor(): Provider<Processor> = project
        .providers
        .provider {
            Processor(
                fileManager = FileManager(get(), get()),
                iconWriter = IconWriter(get()),
                tempFileWriter = TempFileWriter(get()),
            )
        }

    private fun provideCacheManager(): Provider<CacheManager> = project
        .providers
        .provider {
            CacheManager(
                logger = get(),
                fileManager = get(),
                project = project,
            )
        }

    inline fun <reified T> get(): T = project
        .objects
        .property(T::class.java)
        .apply {
            if (!isPresent) {
                set(providers[T::class.java]?.invoke()?.get() as T)
            }
        }
        .get()
}
