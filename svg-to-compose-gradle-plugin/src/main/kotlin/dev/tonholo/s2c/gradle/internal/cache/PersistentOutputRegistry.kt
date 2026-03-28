package dev.tonholo.s2c.gradle.internal.cache

import java.io.File
import java.io.IOException
import java.util.Properties
import java.util.logging.Logger

/**
 * Lightweight registry that tracks which source files produced which output files
 * in persistent mode (output written to `src/` instead of `build/`).
 *
 * Uses [Properties] for storage to avoid adding serialization dependencies.
 * The registry file is managed as Gradle `@LocalState` - preserved across builds
 * but excluded from cache keys.
 */
internal class PersistentOutputRegistry(private val registryFile: File) {
    private val entries: MutableMap<String, String> = mutableMapOf()

    init {
        if (registryFile.exists()) {
            try {
                val props = Properties()
                registryFile.inputStream().use { props.load(it) }
                props.stringPropertyNames().forEach { key ->
                    entries[key] = props.getProperty(key)
                }
            } catch (e: IOException) {
                logger.warning(
                    "Failed to load persistent output registry from ${registryFile.path}: " +
                        "${e.message}. Starting with empty registry.",
                )
            }
        }
    }

    fun register(origin: String, output: String) {
        entries[origin] = output
    }

    fun getOutput(origin: String): String? = entries[origin]

    fun remove(origin: String) {
        entries.remove(origin)
    }

    fun allEntries(): Map<String, String> = entries.toMap()

    fun clear() {
        entries.clear()
    }

    fun save() {
        registryFile.parentFile?.mkdirs()
        val props = Properties()
        entries.forEach { (key, value) -> props.setProperty(key, value) }
        registryFile.outputStream().use { props.store(it, "S2C Persistent Output Registry") }
    }

    companion object {
        private val logger: Logger = Logger.getLogger(PersistentOutputRegistry::class.java.name)
    }
}
