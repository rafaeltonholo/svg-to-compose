package dev.tonholo.s2c.gradle.internal.cache

import dev.tonholo.s2c.gradle.dsl.ProcessorConfiguration
import dev.tonholo.s2c.gradle.tasks.GENERATED_FOLDER
import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.logger.Logger
import okio.ByteString.Companion.toByteString
import okio.Path
import okio.Path.Companion.toPath
import org.gradle.api.Project
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

internal class CacheManager(
    private val logger: Logger,
    private val fileManager: FileManager,
    private val project: Project,
) {
    private val cacheFile by lazy { project.layout.buildDirectory.file("$GENERATED_FOLDER/cache.bin").get().asFile }
    private val fileHashMap = mutableMapOf<Path, String>()
    private lateinit var configurations: Map<String, ProcessorConfiguration>

    fun initialize(configurations: Map<String, ProcessorConfiguration>) {
        if (::configurations.isInitialized.not()) {
            this.configurations = configurations
        }
        loadCache()
    }

    fun hasCacheChanged(path: Path): Boolean {
        val currentHash = calculateFileHash(path)
        val previousHash = fileHashMap[path]
        val hasChanged = previousHash != currentHash
        if (hasChanged) {
            fileHashMap[path] = currentHash
        }
        return hasChanged
    }

    fun saveCache() {
        logger.debug("Saving cache...")
        logger.debug("Generating extension configuration hashes")
        val configurations = configurations.mapValues { (_, configuration) ->
            configuration.calculateHash()
        }
        logger.debug("Generating file hashes")
        val files = fileHashMap.mapKeys { (path, _) -> path.toString() }
        logger.debug("Building Cache Data")
        val cacheData = CacheData(
            files = files,
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
            logger.debug("Error writing cache file: ${e.message}")
            e.printStackTrace()
        }
    }

    fun removeDeletedFilesFromCache() {
        val deletedFiles = fileHashMap.keys.filter { path -> path.toFile().exists().not() }
        deletedFiles.forEach { file ->
            fileHashMap.remove(file)
            // TODO: delete generated file.
        }
    }

    fun removeFromCache(path: Path) {
        fileHashMap.remove(path)
    }

    private fun loadCache() {
        if (cacheFile.exists()) {
            val cacheData = try {
                ObjectInputStream(FileInputStream(cacheFile)).use {
                    it.readObject() as CacheData
                }.also {
                    logger.debug("Cache file loaded")
                }
            } catch (e: IOException) {
                logger.debug("Error loading cache file: ${e.message}")
                e.printStackTrace()
                null
            }
            cacheData?.validate()
        }
    }

    private fun calculateFileHash(path: Path): String = fileManager
        .readBytes(path)
        .toByteString()
        .sha256()
        .hex()

    private fun removeCacheIfExists() {
        if (cacheFile.exists()) {
            cacheFile.delete()
        }
    }

    private fun CacheData.validate() {
        if (fileHashMap.isEmpty()) {
            fileHashMap.putAll(files.mapKeys { (path, _) -> path.toPath() })
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
