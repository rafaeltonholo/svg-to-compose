package dev.tonholo.s2c.gradle.internal.inject

import dev.tonholo.s2c.Processor
import dev.tonholo.s2c.gradle.internal.cache.CacheManager
import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.io.IconWriter
import dev.tonholo.s2c.io.TempFileWriter
import dev.tonholo.s2c.logger.Logger
import okio.FileSystem
import okio.Path.Companion.toOkioPath
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory
import java.io.File
import org.gradle.api.logging.Logger as GradleLogger

internal class DependencyModule(
    private val objectFactory: ObjectFactory,
    private val providerFactory: ProviderFactory,
    private val logger: GradleLogger,
    private val buildDirectory: DirectoryProperty,
    private val tempDirectory: File,
) {
    val providers = mapOf<Class<*>, () -> Provider<out Any>>(
        Logger::class.java to ::provideLogger,
        FileSystem::class.java to ::provideFileSystem,
        FileManager::class.java to ::provideFileManager,
        Processor::class.java to ::provideProcessor,
        CacheManager::class.java to ::provideCacheManager,
    )

    private fun provideLogger(): Provider<Logger> {
        return providerFactory
            .provider { dev.tonholo.s2c.gradle.internal.logger.Logger(logger) }
    }

    private fun provideFileSystem(): Provider<FileSystem> =
        providerFactory
            .provider { FileSystem.SYSTEM }

    private fun provideFileManager(): Provider<FileManager> =
        providerFactory
            .provider {
                FileManager(get(), get())
            }

    private fun provideProcessor(): Provider<Processor> = providerFactory
        .provider {
            Processor(
                logger = get(),
                fileManager = get(),
                iconWriter = IconWriter(get(), get()),
                tempFileWriter = TempFileWriter(get(), get(), tempDirectory.toOkioPath()),
            )
        }

    private fun provideCacheManager(): Provider<CacheManager> = providerFactory
        .provider {
            CacheManager(
                logger = get(),
                fileManager = get(),
                buildDirectory = buildDirectory,
            )
        }

    inline fun <reified T> get(): T = objectFactory
        .property(T::class.java)
        .apply {
            if (!isPresent) {
                val provider = providers[T::class.java]
                    ?: throw IllegalArgumentException("No provider found for ${T::class.java.simpleName}")
                val instance = provider.invoke().get()
                if (instance !is T) {
                    throw ClassCastException(
                        "Provider returned ${instance::class.java.simpleName} but expected ${T::class.java.simpleName}",
                    )
                }
                set(instance)
            }
        }
        .get()
}
