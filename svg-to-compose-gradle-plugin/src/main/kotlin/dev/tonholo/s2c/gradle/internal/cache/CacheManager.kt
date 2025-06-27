package dev.tonholo.s2c.gradle.internal.cache

import dev.tonholo.s2c.gradle.dsl.ProcessorConfiguration
import dev.tonholo.s2c.gradle.tasks.GENERATED_FOLDER
import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.logger.Logger
import okio.Path
import okio.Path.Companion.toPath
import org.gradle.api.file.DirectoryProperty
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InvalidClassException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

internal class CacheManager(
    private val logger: Logger,
    private val fileManager: FileManager,
    private val buildDirectory: DirectoryProperty,
) {
    private val cacheFile by lazy { buildDirectory.file("$GENERATED_FOLDER/cache.bin").get().asFile }
    private val fileHashMap = mutableListOf<IconCacheData>()
    private lateinit var configurations: Map<String, ProcessorConfiguration>

    fun initialize(configurations: Map<String, ProcessorConfiguration>) {
        if (::configurations.isInitialized.not()) {
            this.configurations = configurations
        }
        loadCache()
    }

    private operator fun List<IconCacheData>.get(origin: Path): IconCacheData? =
        firstOrNull { it.origin == origin.toString() }

    fun hasCacheChanged(path: Path): Boolean {
        val currentHash = calculateFileHash(path)
        val prev = fileHashMap.get(origin = path)
        val hasChanged = prev?.hash != currentHash
        if (hasChanged && prev != null) {
            fileHashMap.remove(prev)
            fileHashMap.add(prev.copy(hash = currentHash))
        }
        return hasChanged
    }

    fun saveCache(outputFiles: MutableMap<Path, Path>) {
        logger.debug("Saving cache...")
        logger.debug("Generating extension configuration hashes")
        val configurations = configurations.mapValues { (_, configuration) ->
            configuration.calculateHash()
        }
        outputFiles.map { (origin, output) ->
            val cache = fileHashMap[origin]
                ?.also { cache ->
                    fileHashMap.remove(cache)
                }
                ?: IconCacheData(
                    origin = origin.toString(),
                    hash = calculateFileHash(origin),
                    output = output.toString(),
                )

            fileHashMap.add(cache)
        }
        logger.debug("Building Cache Data")
        val cacheData = CacheData(
            files = fileHashMap,
            extensionConfiguration = configurations,
        )
        logger.debug("Writing cache file")
        try {
            ObjectOutputStream(FileOutputStream(cacheFile))
                .use {
                    it.writeObject(cacheData)
                }
            logger.debug("Cache file written")
        } catch (e: IOException) {
            logger.error("Error writing cache file: ${e.message}", e)
        }
    }

    fun removeDeletedFilesFromCache() {
        val deletedFiles = fileHashMap.filter { cache ->
            fileManager.exists(cache.origin.toPath()).not()
        }
        if (deletedFiles.isNotEmpty()) {
            deletedFiles.forEach { file ->
                logger.output("Deleted origin file detected. Deleting generated file ${file.output}.")
                fileHashMap.remove(file)
                fileManager.delete(requireNotNull(file.output).toPath())
            }
        }
    }

    fun removeFromCache(path: Path) {
        val key = fileHashMap.firstOrNull { it.origin == path.toString() }
        fileHashMap.remove(key)
    }

    private fun loadCache() {
        if (cacheFile.exists()) {
            val cacheData = try {
                ObjectInputStream(FileInputStream(cacheFile)).use {
                    it.readObject() as CacheData
                }.also {
                    logger.debug("Cache file loaded")
                }
            } catch (e: InvalidClassException) {
                logger.warn("Cache serial version probably changed. Removing cache.", e)
                removeCacheIfExists()
                null
            } catch (e: IOException) {
                logger.error("Error loading cache file: ${e.message}", e)
                null
            }
            cacheData?.validate()
        }
    }

    private fun calculateFileHash(path: Path): Sha256Hash = fileManager
        .readBytes(path)
        .sha256()

    private fun removeCacheIfExists() {
        if (cacheFile.exists()) {
            cacheFile.delete()
        }
    }

    private fun CacheData.validate() {
        if (fileHashMap.isEmpty()) {
            fileHashMap.addAll(files)
        } else {
            // validate that the files are the same
        }

        for ((key, configuration) in configurations) {
            val cachedConfig = extensionConfiguration[key]
            if (cachedConfig != null && cachedConfig != configuration.calculateHash()) {
                fileHashMap.clear()
                removeCacheIfExists()
                break
            }
        }
    }
}
